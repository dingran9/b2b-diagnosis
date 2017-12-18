package com.eeduspace.report.service.impl.open;

import com.eeduspace.b2b.report.enumerate.ReportCategoryEnum;
import com.eeduspace.b2b.report.exception.ReportException;
import com.eeduspace.b2b.report.model.ReleaseExamDto;
import com.eeduspace.b2b.report.model.StudentAnswerResultDto;
import com.eeduspace.b2b.report.model.StudentQuestionAnswerResultDto;
import com.eeduspace.b2b.report.model.StudentReportDto;
import com.eeduspace.b2b.report.model.question.KnowledgeModel;
import com.eeduspace.b2b.report.model.question.Questions;
import com.eeduspace.b2b.report.model.report.WrongKnowledgeRankModel;
import com.eeduspace.b2b.report.service.B2BReportOpenService;
import com.eeduspace.report.model.DealWithAnswerResultModel;
import com.eeduspace.report.model.TeacherModel;
import com.eeduspace.report.po.*;
import com.eeduspace.report.service.*;
import com.eeduspace.report.third.client.model.request.ReportRequestModel;
import com.eeduspace.report.third.client.model.response.Colorcolumnar;
import com.eeduspace.report.third.client.model.response.Columnar;
import com.eeduspace.report.third.client.model.response.Radar;
import com.eeduspace.report.third.client.model.response.ReportModel;
import com.eeduspace.report.third.client.service.BaseResourceClient;
import com.eeduspace.report.util.GsonUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.validation.*;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>描述 报告对外接口实现类</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/16 16:00
**/
@Slf4j
@Service("reportOpenServiceImpl")
@com.alibaba.dubbo.config.annotation.Service
public class B2BReportOpenServiceImpl implements B2BReportOpenService {
    @Autowired
    private BaseResourceClient baseResourceClient;
    @Autowired
    private ReportService reportService;
    @Autowired
    private ReleaseExamService releaseExamService;
    @Autowired
    private ExamService examService;
    @Autowired
    private ThreeColorService threeColorService;
    @Autowired
    private SubjectAbilityService subjectAbilityService;
    @Autowired
    private KnowledgeService knowledgeService;
    @Autowired
    private KnowledgeModuleService knowledgeModuleService;
    @Autowired
    private UserAnswerResultService userAnswerResultService;
    @Autowired
    private StudentSubjectScoreService studentSubjectScoreService;
    @Autowired
    private TeacherReportService teacherReportService;
    private Gson gson=new Gson();
    /**
     * 学生答卷记录code
     *
     * @param makePaperRecord
     * @return StudentReportDto 学生报告
     * @throws Exception
     */
    @Override
    public StudentReportDto getStudentReport(String makePaperRecord) throws Exception {
        if (StringUtils.isEmpty(makePaperRecord)) throw new Exception("makePaperRecord is null");
        ReportPo reportPo=reportService.findByMakePaperRecordCode(makePaperRecord);
        if (reportPo==null) throw new Exception("makePaperRecord:"+makePaperRecord+"--- no report");
        StudentReportDto studentReportDto=new StudentReportDto();
        BeanUtils.copyProperties(reportPo,studentReportDto);
        return studentReportDto;
    }

    /**
     * 生成学生报告
     *
     * @param studentAnswerResultDto
     * @return StudentReportDto 学生报告
     * @throws Exception
     */
    @Override
    @Transactional
    public StudentReportDto generateStudentReport(final @Valid StudentAnswerResultDto studentAnswerResultDto) throws ReportException,ValidationException {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        //验证Bean参数，并返回验证结果信息

        Set<ConstraintViolation<StudentAnswerResultDto>> validators = validator.validate(studentAnswerResultDto);
        validators.stream().forEach(constraintViolation->{
            throw new ValidationException(constraintViolation.getMessage());
        });
        ReleaseExamPo releaseExam = releaseExamService.findByReleaseExamCode(studentAnswerResultDto.getReleaseCode());
        if(releaseExam==null){
            throw new NullPointerException("------考试发布记录查询不到！----------");
        }
        final DealWithAnswerResultModel dealWithAnswerResultModel=dealWithStudentAnswerResult(studentAnswerResultDto);
        ReportModel reportModel = getReportModel(studentAnswerResultDto,dealWithAnswerResultModel.getQuestionses());
        if (reportModel==null)throw new ReportException("10001","generateStudentReport fail");
        //过滤后的知识点信息
        List<KnowledgeModel> knowledgeModels = filterKnowledge(reportModel.getColumnar(), dealWithAnswerResultModel.getKnowledgeModelList());
        //处理错误知识点排行
        List<WrongKnowledgeRankModel> wrongKnowledgeRank = getWrongKnowledgeRank(knowledgeModels);
        dealWithAnswerResultModel.setWrongKnowledgeRank(wrongKnowledgeRank);
        final StudentReportDto studentReportDto = getStudentReportDto(studentAnswerResultDto, dealWithAnswerResultModel, reportModel);
        //1 保存报告信息到数据库
        final Map<String,Object> result = saveReportInfo(releaseExam,studentAnswerResultDto, studentReportDto);
        studentReportDto.setExamCode(Integer.valueOf(result.get("examCode").toString()));
        // 2异步 处理报告分解信息
        Thread thread= new Thread(() -> analysisReport(result,
                studentReportDto,knowledgeModels,
                studentAnswerResultDto.getStudentQuestionAnswerResultDtos()));
        thread.start();

        return studentReportDto;
    }

    /**
     * 分析报告
     * @param resultMap 处理结果code
     * @param studentReportDto 学生报告信息
     */
    public  void analysisReport(Map<String,Object> resultMap,StudentReportDto studentReportDto,List<KnowledgeModel> knowledgeModelList,List<StudentQuestionAnswerResultDto> studentQuestionAnswerResultDtos){
        Integer examCode= Integer.valueOf(resultMap.get("examCode").toString());
        List<Colorcolumnar> threeColor=gson.fromJson(studentReportDto.getModuleScoreIncrease(),new TypeToken<ArrayList<Colorcolumnar>>(){}.getType());
        List<ThreeColorPo> threeColorPos=Lists.newArrayList();//三色图
        List<KnowledgeModulePo> knowledgeModulePoList=Lists.newArrayList();//知识点模块
        List<KnowledgePo> knowledgePoList= Lists.newArrayList();//知识点
        List<SubjectAbilityPo> subjectAbilityPoList= Lists.newArrayList();//学科能力
        List<UserAnswerResultPo> userAnswerResultPoList= Lists.newArrayList();//结果信息
        List<Radar> radarList=gson.fromJson(studentReportDto.getSubjectAbility(),new TypeToken<ArrayList<Radar>>(){}.getType());
        List<Columnar> columnarList=gson.fromJson(studentReportDto.getKnowledgeGrasp(),new TypeToken<ArrayList<Columnar>>(){}.getType());
        //保存三色图信息
        {
            if (threeColor != null ) {
                for (Colorcolumnar colorcolumnar : threeColor) {
                    ThreeColorPo threeColorPo = new ThreeColorPo();
                    threeColorPo.setExamCode(examCode);
                    threeColorPo.setBuleScore(colorcolumnar.getBlue());
                    threeColorPo.setGrayScore(colorcolumnar.getGray());
                    threeColorPo.setModuleName(colorcolumnar.getCtb_Name());
                    threeColorPo.setOrangeScore(colorcolumnar.getYellow());
                    threeColorPo.setModuleCode(colorcolumnar.getCtb_code());
                    threeColorPos.add(threeColorPo);
                }
                threeColorService.batchSave(threeColorPos);
            }
        }
        //保存学习能力
        {
            if (radarList != null) {
                for (Radar radar : radarList) {
                    SubjectAbilityPo subjectAbilityPo=new SubjectAbilityPo();
                    subjectAbilityPo.setExamCode(examCode);
                    subjectAbilityPo.setCreateTime(new Timestamp(new Date().getTime()));
                    subjectAbilityPo.setAbilityCode(radar.getCapacityCode());
                    subjectAbilityPo.setAbilityName(radar.getCapacityName());
                    subjectAbilityPo.setAbilityScore(Double.valueOf(radar.getScore()));
                    subjectAbilityPoList.add(subjectAbilityPo);
                }
                subjectAbilityService.batchSave(subjectAbilityPoList);
            }
        }

        //知识点信息统计
        {
            for (KnowledgeModel knowledgeModel : knowledgeModelList) {
                KnowledgePo knowledgePo = new KnowledgePo();
                knowledgePo.setExamCode(examCode);
                knowledgePo.setCreateTime(new Timestamp(new Date().getTime()));
                knowledgePo.setIsRight(knowledgeModel.getIsTrue());
                knowledgePo.setKnowledgeCode(knowledgeModel.getKnowledgeCode());
                knowledgePo.setKnowledgeName(knowledgeModel.getKnowledgeName());
                knowledgePoList.add(knowledgePo);
            }

            knowledgeService.batchSave(knowledgePoList);
        }
        //知识点模块信息统计
        {
            if (columnarList != null) {
                for (Columnar columnar : columnarList) {
                    KnowledgeModulePo knowledgeModulePo=new KnowledgeModulePo();
                    knowledgeModulePo.setCreateTime(new Timestamp(new Date().getTime()));
                    knowledgeModulePo.setExamCode(examCode);
                    knowledgeModulePo.setKnowledgeModuleCode(columnar.getChildrenCtbCode());
                    knowledgeModulePo.setKnowledgeModuleScore((double) columnar.getScore());
                    knowledgeModulePo.setKnowledgeModuleName(columnar.getChildrenCtbCodeName());
                    knowledgeModulePoList.add(knowledgeModulePo);
                }
                knowledgeModuleService.batchSave(knowledgeModulePoList);
            }
        }
        //保存用户答题结果信息
        {
            for (StudentQuestionAnswerResultDto studentQuestionAnswerResultDto : studentQuestionAnswerResultDtos) {
                UserAnswerResultPo userAnswerResultPo=new UserAnswerResultPo();
                userAnswerResultPo.setExamCode(examCode);
                userAnswerResultPo.setAnswerResult(studentQuestionAnswerResultDto.getAnswerResult());
                userAnswerResultPo.setComplexQuestionCode(studentQuestionAnswerResultDto.getComplexQuestionCode());
                userAnswerResultPo.setIsComplex(studentQuestionAnswerResultDto.getIsComplex());
                userAnswerResultPo.setKnowledgeJson(GsonUtils.toJson(studentQuestionAnswerResultDto.getKnowledgeModelList()));
                userAnswerResultPo.setProductionJson(GsonUtils.toJson(studentQuestionAnswerResultDto.getProductionmodelsList()));
                userAnswerResultPo.setQuestionCode(studentQuestionAnswerResultDto.getQuestionCode());
                userAnswerResultPo.setQuestionSn(studentQuestionAnswerResultDto.getQuestionSn());
                userAnswerResultPo.setScore(studentQuestionAnswerResultDto.getScore());
                userAnswerResultPoList.add(userAnswerResultPo);
            }
            userAnswerResultService.batchSave(userAnswerResultPoList);
        }
        //学生学科考试结果统计
        {
            StudentSubjectScorePo studentSubjectScorePo=new StudentSubjectScorePo();
            studentSubjectScorePo.setScore(studentReportDto.getScore());
            studentSubjectScorePo.setCreateTime(new Timestamp(new Date().getTime()));
            studentSubjectScorePo.setExamCode(examCode);
            studentSubjectScorePo.setPaperScore(Double.valueOf(resultMap.get("paperScore").toString()));
            studentSubjectScorePo.setStandardScore(Double.valueOf(resultMap.get("standardScore").toString()));
            studentSubjectScoreService.save(studentSubjectScorePo);
        }


    }

    /**
     * 保存报告信息到数据看库
     * @param studentAnswerResultDto
     * @param studentReportDto
     */
    @Transactional
    private Map<String,Object> saveReportInfo(ReleaseExamPo releaseExamPo,StudentAnswerResultDto studentAnswerResultDto, StudentReportDto studentReportDto) {
        ReportPo reportPo=new ReportPo();
        Integer releaseExamCode=releaseExamPo.getCode();
        // 1.2保存 考试记录
        ExamPo examPo=new ExamPo();
        BeanUtils.copyProperties(studentAnswerResultDto,examPo);
        examPo.setReleaseExamCode(releaseExamCode);
        examPo.setCreateTime(new Timestamp(new Date().getTime()));
        examPo.setUserMakePaperCode(studentAnswerResultDto.getMarkPaperRecordCode());
        examPo=examService.save(examPo);
        // 1.3保存报告数据到数据库

        BeanUtils.copyProperties(studentReportDto,reportPo);
        reportPo.setCreateTime(new Timestamp(new Date().getTime()));
        reportPo.setExamCode(examPo.getCode());
        reportPo.setGradeCode(studentAnswerResultDto.getGradeCode());
        reportPo.setPaperName(releaseExamPo.getReleaseName());
        reportService.save(reportPo);
        Map<String,Object> map=Maps.newHashMap();
        map.put("examCode",examPo.getCode());
        map.put("releaseExamCode",releaseExamCode);
        map.put("reportCode",reportPo.getCode());
        map.put("standardScore",studentAnswerResultDto.getPaperStandardScore());
        map.put("paperScore",studentAnswerResultDto.getPaperScore());
        return map;
    }

    /**
     * 构造报告信息
     * @param studentAnswerResultDto
     * @param dealWithAnswerResultModel
     * @param reportModel
     * @return
     */
    private StudentReportDto getStudentReportDto(StudentAnswerResultDto studentAnswerResultDto, DealWithAnswerResultModel dealWithAnswerResultModel, ReportModel reportModel) {
        StudentReportDto studentReportDto=new StudentReportDto();
        studentReportDto.setKnowledgeGrasp(GsonUtils.toJson(reportModel.getColumnar()));
        studentReportDto.setPaperScore(studentAnswerResultDto.getPaperScore());
        studentReportDto.setHistoricalScore(studentAnswerResultDto.getHistoricalScore());//历史分数波动需要查询
        studentReportDto.setMakePaperRecordCode(studentAnswerResultDto.getMarkPaperRecordCode());
        studentReportDto.setMakePaperTime(studentAnswerResultDto.getMakePaperTime());
        studentReportDto.setModuleScoreIncrease(GsonUtils.toJson(reportModel.getColorcolumnar()));
        studentReportDto.setPaperCode(studentAnswerResultDto.getPaperCode());
        studentReportDto.setUseTime(studentAnswerResultDto.getUseTime());
        studentReportDto.setPaperName(studentAnswerResultDto.getPaperName());
        studentReportDto.setWrongCount(studentAnswerResultDto.getWrongCount());
        studentReportDto.setRightCount(studentAnswerResultDto.getRightCount());
        studentReportDto.setWrongQuestionSn(GsonUtils.toJson(dealWithAnswerResultModel.getWrongQuestionSn()));//错误题题号  运算
        studentReportDto.setScore(studentAnswerResultDto.getScore());
        studentReportDto.setSubjectAbility(GsonUtils.toJson(reportModel.getRadar()));
        studentReportDto.setDiagnosisPaperCode(studentAnswerResultDto.getDiagnosisPaperCode());
        studentReportDto.setWrongKnowledgeRank(GsonUtils.toJson(dealWithAnswerResultModel.getWrongKnowledgeRank()));//错误知识点排行榜 需要计算
        return studentReportDto;
    }

    /**
     * 获取报告
     * @param studentAnswerResultDto
     * @return
     */
    private ReportModel getReportModel(StudentAnswerResultDto studentAnswerResultDto,List<Questions> questionsList) {
        ReportRequestModel reportRequestModel=new ReportRequestModel();
        reportRequestModel.setBookType(studentAnswerResultDto.getBookTypeVersionCode());
        reportRequestModel.setGradeCode(studentAnswerResultDto.getGradeCode());
        reportRequestModel.setPaperId(studentAnswerResultDto.getPaperCode());
        reportRequestModel.setSubjectCode(studentAnswerResultDto.getSubjectCode());
        reportRequestModel.setQuestions(questionsList);
        return baseResourceClient.getSingelReportFromResource(reportRequestModel);
    }

    /**
     * 处理用户单个单题结果信息
     * @param studentAnswerResultDto
     * @return DealWithAnswerResultModel 处理后结果信息
     */
    private DealWithAnswerResultModel dealWithStudentAnswerResult(StudentAnswerResultDto studentAnswerResultDto) {
        DealWithAnswerResultModel dealWithAnswerResultModel=new DealWithAnswerResultModel();
        List<Questions> questionsList= Lists.newArrayList();
        List<String> wrongQuestionSn=Lists.newArrayList();
        List<KnowledgeModel> allKnowledge=Lists.newArrayList();
        for (StudentQuestionAnswerResultDto studentQuestionAnswerResultDto :
                studentAnswerResultDto.getStudentQuestionAnswerResultDtos()) {
            Questions questions=new Questions();
            if(studentQuestionAnswerResultDto.getAnswerResult().equals(0)){
                wrongQuestionSn.add(studentQuestionAnswerResultDto.getQuestionSn());
            }
            if(studentQuestionAnswerResultDto.getIsComplex().equals(1)){
                questions.setQuestionId(studentQuestionAnswerResultDto.getComplexQuestionCode());
            }else{
                questions.setQuestionId(studentQuestionAnswerResultDto.getQuestionCode());
            }
            questions.setScore(studentQuestionAnswerResultDto.getScore());
            questions.setProductionModels(studentQuestionAnswerResultDto.getProductionmodelsList());
            questionsList.add(questions);
            allKnowledge.addAll(studentQuestionAnswerResultDto.getKnowledgeModelList());
        }
        dealWithAnswerResultModel.setKnowledgeModelList(allKnowledge);
        dealWithAnswerResultModel.setQuestionses(questionsList);
        //dealWithAnswerResultModel.setWrongKnowledgeRank(wrongKnowledgeRank);
        dealWithAnswerResultModel.setWrongQuestionSn(wrongQuestionSn);
        return dealWithAnswerResultModel;
    }
    /**
     * 获取错误知识点排行榜
     */
    public List<WrongKnowledgeRankModel> getWrongKnowledgeRank(List<KnowledgeModel> knowledgeModelList){
        List<WrongKnowledgeRankModel> wrongKnowledgeRankModels=Lists.newArrayList();
        Map<String, List<KnowledgeModel>> collectKnowledgeModel = knowledgeModelList.stream().filter(knowledgeModel -> knowledgeModel.getIsTrue().equals(0))
                .collect(Collectors.groupingBy(KnowledgeModel::getKnowledgeCode));
        for (Map.Entry<String, List<KnowledgeModel>> stringListEntry : collectKnowledgeModel.entrySet()) {
            WrongKnowledgeRankModel wrongKnowledgeRankModel=new WrongKnowledgeRankModel();
            wrongKnowledgeRankModel.setWrongCount(stringListEntry.getValue().size());
            wrongKnowledgeRankModel.setKnowledgeCode(stringListEntry.getKey());
            wrongKnowledgeRankModel.setKnowledgeName(stringListEntry.getValue().get(0).getKnowledgeName());
            wrongKnowledgeRankModels.add(wrongKnowledgeRankModel);
        }
        wrongKnowledgeRankModels.sort((o1, o2) -> o2.getWrongCount()-o1.getWrongCount());
//        for (KnowledgeModel knowledgeModel : knowledgeModelList) {
//            if (knowledgeModel.getIsTrue().equals(0)){
//                if (knowledgeMap.get(knowledgeModel.getKnowledgeName())==null){
//                    knowledgeMap.put(knowledgeModel.getKnowledgeName(),1);
//                }else {
//                    knowledgeMap.put(knowledgeModel.getKnowledgeName(),knowledgeMap.get(knowledgeModel.getKnowledgeName())+1);
//                }
//            }
//        }
        return wrongKnowledgeRankModels;
    }

    /**
     * 生成教师报告或校长报告
     *
     * @param releaseExamRecordCode 考试发布记录code
     * @param reportCategoryEnum    报告类别枚举
     */
    @Override
    public void generateTeacherOrPrincipalReport(String releaseExamRecordCode, ReportCategoryEnum reportCategoryEnum) throws Exception{
        switch (reportCategoryEnum) {
            case TEACHER_REPORT:
                generateTeacherReport(releaseExamRecordCode);
                break;
            case PRINCIPAL_REPORT:
                break;
            case ALL_REPORT:
                break;
        }
    }

    /**
     * 生成教师报告
     * @param releaseExamRecordCode
     */
    private void generateTeacherReport(final String releaseExamRecordCode) throws Exception{
        new Thread(()->{
            List<TeacherModel> teacherModels  = null;
            try {
                teacherModels = examService.getTeacherByReleaseExamCode(releaseExamRecordCode);
            } catch (Exception e) {
                e.printStackTrace();
            }
            teacherModels.forEach((teacherModel)->{
                try {
                    teacherReportService.generateTeacherReport(releaseExamRecordCode,teacherModel.getTeacherCode(),teacherModel.getSubjectCode());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            //回调告诉报告已经生成
            //TODO
        }).start();

    }

    /**
     * 获取教师报告
     *
     * @param releaseExamRecordCode 发布考试记录code
     * @throws Exception
     */
    @Override
    public void getTeacherReport(String releaseExamRecordCode) throws Exception {

    }

    /**
     * 获取校长报告
     *
     * @param releaseExamRecordCode 发布考试记录code
     * @throws Exception
     */
    @Override
    public void getPrincipalReport(String releaseExamRecordCode) throws Exception {

    }

    /**
     * 发布考试记录
     *
     * @param releaseExamDto 发布考试记录实体
     * @return
     * @throws Exception
     */
    @Override
    public Boolean releaseExam( @Valid ReleaseExamDto releaseExamDto) throws Exception {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        //验证Bean参数，并返回验证结果信息

        Set<ConstraintViolation<ReleaseExamDto>> validators = validator.validate(releaseExamDto);
        validators.stream().forEach(constraintViolation->{
            throw new ValidationException(constraintViolation.getMessage());
        });

        ReleaseExamPo releaseExamPo=new ReleaseExamPo();
        releaseExamPo.setSemester(releaseExamDto.getSemester());
        releaseExamPo.setExamType(releaseExamDto.getExamType());
        releaseExamPo.setCreateTime(new Timestamp(new Date().getTime()));
        releaseExamPo.setReleaseCode(releaseExamDto.getReleaseCode());
        releaseExamPo.setReleaseName(releaseExamDto.getReleaseName());
        releaseExamPo.setTotalScore(releaseExamDto.getTotalScore());
        try {
            releaseExamService.save(releaseExamPo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }



    /**
     * 根据诊断节点过滤试卷知识点范围
     * @param columnars 试卷诊断节点
     * @param paperKnowledges 试卷上知识点
     * @return
     */
    public List<KnowledgeModel> filterKnowledge(List<Columnar> columnars,List<KnowledgeModel> paperKnowledges){
       return paperKnowledges.stream().map(knowledgeModel -> {
            KnowledgeModel k = new KnowledgeModel();
            columnars.stream().forEach(cl -> {
                if (knowledgeModel.getKnowledgeCode().contains(cl.getChildrenCtbCode())) {
                    BeanUtils.copyProperties(knowledgeModel, k);
                }
            });
            return k;
        }).filter(knowledgeModel -> !StringUtils.isEmpty(knowledgeModel.getKnowledgeCode())).collect(Collectors.toList());
    }
}
