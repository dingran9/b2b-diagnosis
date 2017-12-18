package com.eedu.diagnosis.protal.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eedu.auth.beans.AuthGroupBean;
import com.eedu.auth.beans.AuthSubjectBean;
import com.eedu.auth.service.AuthGroupService;
import com.eedu.diagnosis.common.enumration.DiagnosisTypeEnum;
import com.eedu.diagnosis.common.enumration.ExamTypeEnum;
import com.eedu.diagnosis.common.exception.DiagnosisException;
import com.eedu.diagnosis.common.exception.exceptionCode.ExceptionCodeEnum;
import com.eedu.diagnosis.common.model.paperEntity.*;
import com.eedu.diagnosis.common.service.redisService.RedisClientTemplate;
import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.common.utils.PageHelperUtil;
import com.eedu.diagnosis.exam.api.dto.*;
import com.eedu.diagnosis.exam.api.enumeration.AnswerResultEnum;
import com.eedu.diagnosis.exam.api.enumeration.DiagnosisPaperEnum;
import com.eedu.diagnosis.exam.api.enumeration.DiagnosisStausEnum;
import com.eedu.diagnosis.exam.api.openService.*;
import com.eedu.diagnosis.paper.api.dto.DiagnosisFavoriteDto;
import com.eedu.diagnosis.paper.api.dto.DiagnosisPaperDto;
import com.eedu.diagnosis.paper.api.openService.BasePaperOpenService;
import com.eedu.diagnosis.paper.api.openService.ResourceOpenService;
import com.eedu.diagnosis.protal.model.AnswerAndMarkQuestionModel;
import com.eedu.diagnosis.protal.model.DiagnosisPaperModel;
import com.eedu.diagnosis.protal.model.HistoryScoreModel;
import com.eedu.diagnosis.protal.model.request.AnswerSheetModel;
import com.eedu.diagnosis.protal.model.request.StudentModel;
import com.eedu.diagnosis.protal.model.response.DiagnosisRecordStudentModel;
import com.eedu.diagnosis.protal.service.StudentDiagnosisService;
import com.eeduspace.b2b.report.exception.ReportException;
import com.eeduspace.b2b.report.model.StudentAnswerResultDto;
import com.eeduspace.b2b.report.model.StudentQuestionAnswerResultDto;
import com.eeduspace.b2b.report.model.StudentReportDto;
import com.eeduspace.b2b.report.model.question.KnowledgeModel;
import com.eeduspace.b2b.report.model.question.Productionmodels;
import com.eeduspace.b2b.report.model.question.Sons;
import com.eeduspace.b2b.report.model.question.TeachingMaterialsProductionModel;
import com.eeduspace.b2b.report.service.B2BReportOpenService;
import com.eeduspace.uuims.comm.util.base.DateUtils;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by dqy on 2017/3/20.
 */
@Service
public class StudentDiagnosisServiceImpl implements StudentDiagnosisService {
    @Autowired
    private DiagnosisRecordStudentOpenService diagnosisRecordStudentOpenService;
    @Autowired
    private DiagnosisStudentAnswerRecordOpenService diagnosisStudentAnswerRecordOpenService;
    @Autowired
    private RedisClientTemplate redisClientTemplate;
    @Autowired
    private BasePaperOpenService basePaperOpenService;
    @Autowired
    private ResourceOpenService resourceOpenService;
    @Autowired
    private B2BReportOpenService b2BReportOpenService;
    @Autowired
    private AuthGroupService authGroupService;
    @Autowired
    private DiagnosisClassRelationOpenService diagnosisClassRelationOpenService;
    @Autowired
    private DiagnosisWrongQuestionOpenService diagnosisWrongQuestionOpenService;
    @Autowired
    private MarkQuestionRecordOpenService markQuestionRecordOpenService;

    @Override
    public PageInfo<DiagnosisRecordStudentModel> examList(StudentModel model) throws Exception {
        DiagnosisRecordStudentDto dto = new DiagnosisRecordStudentDto();
        dto.setStudentCode(model.getUserId());
        dto.setSubjectCode(model.getSubjectCode());
        dto.setDiagnosisType(DiagnosisTypeEnum.SINGLE.getValue());
        model.setPageSize(Integer.MAX_VALUE);
        Order order = new Order("create_time", Order.Direction.desc);
        PageInfo<DiagnosisRecordStudentDto> pageInfo = diagnosisRecordStudentOpenService.getList(dto, model.getPageSize(), model.getPageNum(), order);
        PageInfo<DiagnosisRecordStudentModel> diagnosisRecordStudentModelPageInfo = PageHelperUtil.pageInfoConverter(pageInfo, DiagnosisRecordStudentModel.class);


        List<DiagnosisRecordStudentModel> list = diagnosisRecordStudentModelPageInfo.getList();
        Iterator<DiagnosisRecordStudentModel> iterator = list.iterator();
        while (iterator.hasNext()) {
            //不是当前班级的考试 且 还未参加考试的数据 过滤掉
            DiagnosisRecordStudentModel next = iterator.next();
            if (!model.getClassCode().equals(next.getClassCode()) && DiagnosisStausEnum.RELEASE.getValue().equals(next.getDiagnosisStatus())) {
                iterator.remove();
            }
        }
        if (null != list && !list.isEmpty()) {
            List<String> diagnosisPaperCodes = new ArrayList<>();
            list.forEach(diagnosisRecordStudentModel -> {
                if (!diagnosisPaperCodes.contains(diagnosisRecordStudentModel.getDiagnosisPaperCode()))
                    diagnosisPaperCodes.add(diagnosisRecordStudentModel.getDiagnosisPaperCode());
                if (diagnosisRecordStudentModel.getEndTime().before(new Date())) {
                    diagnosisRecordStudentModel.setIsExpired(1);
                }
            });

            List<DiagnosisPaperDto> paperDtos = basePaperOpenService.getDiagnosisPaperList(diagnosisPaperCodes);
            if (null != paperDtos && !paperDtos.isEmpty()) {
                for (DiagnosisRecordStudentModel dr : list) {
                    for (DiagnosisPaperDto dto1 : paperDtos) {
                        if (dto1.getCode().equals(dr.getDiagnosisPaperCode())) {
                            dr.setUnitCode(dto1.getUnitCode());
                            dr.setUnitName(dto1.getUnitName());
                            break;
                        }
                    }
                }
                diagnosisRecordStudentModelPageInfo.setList(list);
                return diagnosisRecordStudentModelPageInfo;
            }
        }

        return null;
    }


    @Override
    public PaperSystem paperDetail(DiagnosisPaperModel model) throws Exception {
        Integer[] type = {4, 5};
        PaperSystem paper = getPaper(model);
        if (null != paper) {
            // 判断试卷内试题收藏与否
            List<String> questionCodes = new ArrayList<>();
            boolean needImg = false;
            for (QuestionSet qset : paper.getPaperSystemQusetionType()) {
                for (BigQuestionSystem bigquestionSystem : qset.getTypeList()) {
                    for (SmallQuestionSystem smallQuestion : bigquestionSystem.getList()) {
                        //如果是复合题
                        if (CollectionUtils.isNotEmpty(smallQuestion.getComponentQuestions())) {
                            for (ComponentQuestion componentQuestion : smallQuestion.getComponentQuestions()) {
                                if (Arrays.asList(type).contains(Integer.parseInt(componentQuestion.getType())))
                                    needImg = true;
                                questionCodes.add(componentQuestion.getId());
                            }
                        } else {
                            if (Arrays.asList(type).contains(Integer.parseInt(smallQuestion.getType()))) needImg = true;
                            questionCodes.add(smallQuestion.getId());
                        }
                    }
                }
            }
            DiagnosisFavoriteDto df = new DiagnosisFavoriteDto();
            df.setList(questionCodes);
            df.setSubjectCode(paper.getSubjectCode());
            df.setUserCode(model.getStudentCode().toString());
            PageInfo<DiagnosisFavoriteDto> diagnosisFavoritePaper = basePaperOpenService.getDiagnosisFavoritePaper(df, null, null, null);
            List<DiagnosisFavoriteDto> list = diagnosisFavoritePaper.getList();

            if (null != list && !list.isEmpty()) {
                for (QuestionSet qset : paper.getPaperSystemQusetionType()) {
                    for (BigQuestionSystem bigquestionSystem : qset.getTypeList()) {
                        for (SmallQuestionSystem smallQuestion : bigquestionSystem.getList()) {
                            //如果是复合题
                            if (!CollectionUtils.isEmpty(smallQuestion.getComponentQuestions())) {
                                for (ComponentQuestion componentQuestion : smallQuestion.getComponentQuestions()) {
                                    a:
                                    for (DiagnosisFavoriteDto dfdto : list) {
                                        if (dfdto.getQuestionCode().equals(componentQuestion.getId())) {
                                            componentQuestion.setIsFavorite(1);
                                            break a;
                                        }
                                    }
                                }
                            } else {
                                a:
                                for (DiagnosisFavoriteDto dfdto : list) {
                                    if (dfdto.getQuestionCode().equals(smallQuestion.getId())) {
                                        smallQuestion.setIsFavorite(1);
                                        break a;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            paper.setNeedImg(needImg);
            return paper;
        }
        return null;
    }

    private PaperSystem getPaper(DiagnosisPaperModel model) throws Exception {
        DiagnosisPaperDto dto = new DiagnosisPaperDto();
        dto.setCode(model.getCode());
        List<DiagnosisPaperDto> diagnosisPaperList = basePaperOpenService.getDiagnosisPaperList(dto);
        if (null != diagnosisPaperList && !diagnosisPaperList.isEmpty()) {
            String responseJson = resourceOpenService.getResourcePaperInfo(diagnosisPaperList.get(0).getResourcePaperCode());
            if (StringUtils.isBlank(responseJson)) {
                return null;
            }
            return JSONObject.parseObject(responseJson, PaperSystem.class);
        }
        return null;
    }

    @Override
    public Map<String, Object> submit(AnswerSheetModel model) throws Exception {
        DiagnosisRecordStudentDto drdto = new DiagnosisRecordStudentDto();
        drdto.setCode(model.getCode());
        List<DiagnosisRecordStudentDto> diagnosisRecordStudents = diagnosisRecordStudentOpenService.getAll(drdto, null);
        if (!diagnosisRecordStudents.isEmpty() && !DiagnosisStausEnum.RELEASE.getValue().equals(diagnosisRecordStudents.get(0).getDiagnosisStatus())) {
            throw new DiagnosisException(ExceptionCodeEnum.RESOURCE_DUPLICATE.getCode(), "该次测试已经提交，请勿重复操作。");
        }
        AnswerSheetDto dto = new AnswerSheetDto();
        BeanUtils.copyProperties(model, dto);
        AnswerSheetDto asd = diagnosisStudentAnswerRecordOpenService.submit(dto);
        Map result = new HashMap();
        //如果 诊断需要立即生成报告
        if (DiagnosisPaperEnum.NeedmarkEnum.NOTNEED.getValue() == asd.getNeedMark()) {
            //报告生成成功后 修改学生诊断记录的报告状态
            getStudentReportDto(asd, model, diagnosisRecordStudents.get(0));

            DiagnosisRecordStudentDto drs = new DiagnosisRecordStudentDto();
            drs.setDiagnosisStatus(DiagnosisStausEnum.REPROT.getValue());
            drs.setCode(model.getCode());
            drs.setUpdateTime(new Date());
            diagnosisRecordStudentOpenService.update(drs);

            result.put("diagnosisStatus", DiagnosisStausEnum.REPROT.getValue());
            result.put("totalScore", asd.getTotalScore());
            result.put("score", asd.getGetScore());
            int total = asd.getRightCout() + asd.getWrongCout();
            int i = asd.getRightCout();
            result.put("right", (int) (100 * new BigDecimal((double) i / total).setScale(2, RoundingMode.HALF_UP).doubleValue()));

            return result;
        } else {
            result.put("diagnosisStatus", DiagnosisStausEnum.SUBMIT.getValue());
            return result;
        }
    }

    @Override
    public List<Map<Integer, Object>> initExamList(StudentModel model) throws Exception {
        List<Integer> subjectCodes = new ArrayList<>();
        AuthGroupBean subjectBySchoolGrade = authGroupService.getSubjectBySchoolGrade(model.getSchoolCode(), model.getGradeCode());
        String groupMaterial = subjectBySchoolGrade.getGroupMaterial();
        List<AuthSubjectBean> subjectBeans = JSONArray.parseArray(groupMaterial, AuthSubjectBean.class);
        if (null != subjectBeans && !subjectBeans.isEmpty()) {
            for (AuthSubjectBean bean : subjectBeans) {
                subjectCodes.add(bean.getSubjectIden());
            }
        }
        if (null != subjectCodes && !subjectCodes.isEmpty()) {
            //获取学生当前班级的考试记录 以统计未参加考试的数量
            DiagnosisRecordStudentDto dto = new DiagnosisRecordStudentDto();
            dto.setStudentCode(model.getUserId());
            dto.setSubjectCodes(subjectCodes);
            dto.setClassCode(model.getClassCode());
            dto.setDiagnosisType(DiagnosisTypeEnum.SINGLE.getValue());
            dto.setDiagnosisStatus(DiagnosisStausEnum.RELEASE.getValue());
            List<DiagnosisRecordStudentDto> recordList = diagnosisRecordStudentOpenService.getAll(dto, null);

            List<Map<Integer, Object>> result = new ArrayList<>();
            Map<Integer, Object> map = null;
            for (Integer i : subjectCodes) {
                map = new HashMap();
                int count = 0;
                for (DiagnosisRecordStudentDto dr : recordList) {
                    //
                    if (i.equals(dr.getSubjectCode()) && dr.getEndTime().after(new Date())) {
                        count++;
                    }
                }
                map.put(i, count);
                result.add(map);
            }
            return result;

        }
        return null;
    }


    @Override
    public StudentReportDto reGetReprot(String diagnosisRecordCode) throws Exception {
        String reportParamKey = "reportParam_" + diagnosisRecordCode;
        String answerResultDtoJson = redisClientTemplate.get(reportParamKey);
        if (null != answerResultDtoJson) {
            StudentAnswerResultDto answerResultDto = JSONObject.parseObject(answerResultDtoJson, StudentAnswerResultDto.class);
            StudentReportDto studentReportDto = b2BReportOpenService.generateStudentReport(answerResultDto);
            //重新生成报告成功后，清除redis缓存的数据，并修改学生该次诊断状态未已出报告
            if (studentReportDto != null) {
                redisClientTemplate.del(reportParamKey);

                DiagnosisRecordStudentDto dto = new DiagnosisRecordStudentDto();
                dto.setCode(diagnosisRecordCode);
                dto.setDiagnosisStatus(DiagnosisStausEnum.REPROT.getValue());
                diagnosisRecordStudentOpenService.update(dto);
            }
            return studentReportDto;
        }
        return null;
    }

    @Override
    public PaperSystem getPaperAndAnswerResult(DiagnosisPaperModel model) throws Exception {
        Gson gson = new Gson();
        PaperSystem paper = getPaper(model);
        if (null != paper) {
            DiagnosisStudentAnswerRecordDto dsard = new DiagnosisStudentAnswerRecordDto();
            dsard.setDiagnosisRecordCode(model.getStudentDiagnosisRecordCode());
            PageInfo<DiagnosisStudentAnswerRecordDto> answerRecord = diagnosisStudentAnswerRecordOpenService.getAnswerRecord(dsard, Integer.MAX_VALUE, 1, null);
            List<DiagnosisStudentAnswerRecordDto> list = answerRecord.getList();
            //获取教师判题信息
            List<AnswerAndMarkQuestionModel> answerAndMarkQuestionModels = new ArrayList<>();
            List<DiagnosisMarkQuestionRecordDto> markQuestions = markQuestionRecordOpenService.findByDiagnosisRecordCode(model.getStudentDiagnosisRecordCode());
            list.stream().forEach(l -> {
                if (l.getIsImg().equals(1)) {
                    markQuestions.stream().forEach(m -> {
                        if (m.getAnswerRecordCode().equals(l.getCode())) {
                            AnswerAndMarkQuestionModel a = new AnswerAndMarkQuestionModel();
                            a.setAnswerRecordCode(l.getCode());
                            a.setScore(m.getScore());
                            a.setAnswerResult(l.getStudentAnswer());
                            a.setComplexQuestionCode(l.getComplexQuestionCode());
                            a.setQuestionCode(l.getQuestionCode());
                            a.setMarkQuestionResult(m.getMarkResult());
                            a.setSurfaceScore(m.getSurfaceScore());
                            a.setBasetree(gson.fromJson(m.getBaseProduction(),
                                    new TypeToken<ArrayList<BaseProductionModel>>() {
                                    }.getType()));
                            a.setTree(gson.fromJson(m.getProductionKnowledge(),
                                    new TypeToken<ArrayList<SimpleTree>>() {
                                    }.getType()));
                            answerAndMarkQuestionModels.add(a);
                        }
                    });
                } else {
                    AnswerAndMarkQuestionModel a = new AnswerAndMarkQuestionModel();
                    a.setAnswerRecordCode(l.getCode());
                    a.setComplexQuestionCode(l.getComplexQuestionCode());
                    a.setQuestionCode(l.getQuestionCode());
                    a.setAnswerResult(l.getStudentAnswer());
                    a.setMarkQuestionResult(l.getIsRight());
                    if (AnswerResultEnum.RIGHT.getValue().equals(l.getIsRight())) {
                        a.setScore(l.getQuestionScore().doubleValue());
                    } else {
                        a.setScore(0d);
                    }
                    a.setBasetree(gson.fromJson(l.getBaseProductionJson(),
                            new TypeToken<ArrayList<BaseProductionModel>>() {
                            }.getType()));
                    a.setTree(gson.fromJson(l.getKnowledgeJson(),
                            new TypeToken<ArrayList<SimpleTree>>() {
                            }.getType()));
                    answerAndMarkQuestionModels.add(a);
                }

            });


            if (null != list) {
                for (QuestionSet qset : paper.getPaperSystemQusetionType()) {
                    for (BigQuestionSystem bigquestionSystem : qset.getTypeList()) {
                        for (SmallQuestionSystem smallQuestion : bigquestionSystem.getList()) {
                            //如果是复合题
                            if (CollectionUtils.isNotEmpty(smallQuestion.getComponentQuestions())) {
                                List<ComponentQuestion> componentQuestions = smallQuestion.getComponentQuestions();
                                for (ComponentQuestion com : componentQuestions) {
                                    a:
                                    for (AnswerAndMarkQuestionModel dto : answerAndMarkQuestionModels) {
                                        if (com.getId().equals(dto.getQuestionCode())) {
                                            com.setUserAnswerResul(dto.getAnswerResult());
                                            com.setTree(dto.getTree());
                                            com.setBasetree(dto.getBasetree());
                                            com.setStudentGetScore(dto.getScore());
                                            com.setMarkQuestionResult(dto.getMarkQuestionResult());
                                            break a;
                                        }
                                    }
                                }
                            } else {
                                b:
                                for (AnswerAndMarkQuestionModel dto : answerAndMarkQuestionModels) {
                                    if (smallQuestion.getId().equals(dto.getQuestionCode())) {
                                        smallQuestion.setUserAnswerResul(dto.getAnswerResult());
                                        smallQuestion.setTree(dto.getTree());
                                        smallQuestion.setBasetree(dto.getBasetree());
                                        smallQuestion.setStudentGetScore(dto.getScore());
                                        smallQuestion.setMarkQuestionResult(dto.getMarkQuestionResult());
                                        break b;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return paper;
        }
        return null;
    }

    /**
     * 校验学生是否已经参加该测试 false 未参加 true已参加
     *
     * @param diagnosisRecordCode
     * @return
     * @throws Exception
     */
    @Override
    public boolean checkExamStatus(String diagnosisRecordCode) throws Exception {
        DiagnosisRecordStudentDto dto = new DiagnosisRecordStudentDto();
        dto.setCode(diagnosisRecordCode);
        dto.setDiagnosisStatus(DiagnosisStausEnum.RELEASE.getValue());
        List<DiagnosisRecordStudentDto> list = diagnosisRecordStudentOpenService.getAll(dto, null);
        return !list.isEmpty();
    }

    @Override
    public PageInfo<DiagnosisRecordStudentDto> complexExamList(StudentModel model) throws Exception {
        DiagnosisRecordStudentDto dto = new DiagnosisRecordStudentDto();
        dto.setStudentCode(model.getUserId());
        dto.setGradeCode(model.getGradeCode());
        dto.setDiagnosisType(DiagnosisTypeEnum.COMPLEX.getValue());
        Order order = new Order("ds.create_time", Order.Direction.desc);
        PageInfo<DiagnosisRecordStudentDto> studentDtoRecords = diagnosisRecordStudentOpenService.getListGroupByDiagName(dto, model.getPageSize(), model.getPageNum(), order);

        List<DiagnosisRecordStudentDto> list = studentDtoRecords.getList();
        Iterator<DiagnosisRecordStudentDto> iterator = list.iterator();
        while (iterator.hasNext()) {
            //不是当前班级的考试 且 还未参加考试的数据 过滤掉
            DiagnosisRecordStudentDto next = iterator.next();
            if (!model.getClassCode().equals(next.getClassCode()) && DiagnosisStausEnum.RELEASE.getValue().equals(next.getDiagnosisStatus())) {
                iterator.remove();
            }
        }
        if (null != list && !list.isEmpty()) {
            list.forEach(diagnosisRecordTeacherDto -> {
                if (diagnosisRecordTeacherDto.getStartTime().after(new Date())) {
                    diagnosisRecordTeacherDto.setExamStatus(0);
                } else if (diagnosisRecordTeacherDto.getEndTime().before(new Date())) {
                    diagnosisRecordTeacherDto.setExamStatus(2);
                } else {
                    diagnosisRecordTeacherDto.setExamStatus(1);
                }
            });
        }
        return studentDtoRecords;
    }

    @Override
    public List<DiagnosisRecordStudentModel> complexSubjectExamList(DiagnosisRecordStudentModel model) throws Exception {
        DiagnosisRecordStudentDto drd = new DiagnosisRecordStudentDto();
        drd.setDiagnosisTeacherRecordCode(model.getCode());
        drd.setStudentCode(model.getStudentCode());
        Order order = new Order("start_time", Order.Direction.desc);
        List<DiagnosisRecordStudentDto> studentDiagnosisRecords = diagnosisRecordStudentOpenService.getAll(drd, order);
        List<DiagnosisRecordStudentModel> diagnosisRecordStudentModels = PageHelperUtil.converterList(studentDiagnosisRecords, DiagnosisRecordStudentModel.class);
        return diagnosisRecordStudentModels;
    }

    private StudentReportDto getStudentReportDto(AnswerSheetDto asd, AnswerSheetModel model, DiagnosisRecordStudentDto dsdto) throws Exception {
        DiagnosisRecordStudentDto drd = new DiagnosisRecordStudentDto();
        drd.setSubjectCode(asd.getSubjectCode());
        drd.setStudentCode(asd.getStudentCode());
        drd.setGradeCode(asd.getGradeCode());
        //根据考试类型获取相应类型的考试记录
        if (ExamTypeEnum.UNIT.getCode().equals(dsdto.getDiagnosisType())) {
            drd.setDiagnosisType(DiagnosisTypeEnum.SINGLE.getValue());
        } else {
            drd.setDiagnosisType(DiagnosisTypeEnum.COMPLEX.getValue());
        }
        drd.setDiagnosisStatus(DiagnosisStausEnum.REPROT.getValue());
        Order order = new Order();
        order.setProperty("start_time");
        order.setDirection(Order.Direction.desc);
        List<DiagnosisRecordStudentDto> studentDiagnosisRecords = diagnosisRecordStudentOpenService.getAll(drd, order);

        List<HistoryScoreModel> scores = new ArrayList<>();
        HistoryScoreModel historyScoreModel = new HistoryScoreModel();
        historyScoreModel.setExamDate(DateUtils.toString(dsdto.getCreateTime(), DateUtils.DATE_FORMAT_DATETIME));
        historyScoreModel.setScore(Double.parseDouble(null == asd.getGetScore() ? "0" : asd.getGetScore().toString()));
        scores.add(historyScoreModel);
        if (null != studentDiagnosisRecords && !studentDiagnosisRecords.isEmpty()) {
            for (DiagnosisRecordStudentDto po : studentDiagnosisRecords) {
                HistoryScoreModel hm = new HistoryScoreModel();
                hm.setExamDate(DateUtils.toString(po.getCreateTime(), DateUtils.DATE_FORMAT_DATETIME));
                hm.setScore(Double.parseDouble(null == po.getTotalScore() ? "0" : po.getTotalScore().toString()));
                scores.add(hm);
            }
        }
        StudentAnswerResultDto answerResultDto = new StudentAnswerResultDto();
        answerResultDto.setPaperStandardScore(asd.getIstopic());
        answerResultDto.setPaperScore(asd.getTotalScore().toString());
        answerResultDto.setPaperCode(asd.getPaperCode());
        answerResultDto.setDiagnosisPaperCode(asd.getDiagnosisPaperCode());
        answerResultDto.setPaperName(asd.getDiagnosisPaperName());
        answerResultDto.setUserCode(asd.getStudentCode().toString());
        answerResultDto.setUserName(asd.getStudentName());
        answerResultDto.setReleaseCode(asd.getDiagnosisTeacherRecordCode());
        answerResultDto.setMarkPaperRecordCode(asd.getCode());
        answerResultDto.setUseTime(Integer.parseInt(asd.getUseTime() == null ? "0" : asd.getUseTime()));
        answerResultDto.setScore(asd.getGetScore());
        answerResultDto.setRightCount(asd.getRightCout());
        answerResultDto.setWrongCount(asd.getWrongCout());
        answerResultDto.setHistoricalScore(JSONArray.toJSONString(scores));
        // 调用组织权限接口 通过班级code和学科code 获取该科老师的code和name
        DiagnosisClassRelationDto diagnosisClassRelationDto = new DiagnosisClassRelationDto();
        diagnosisClassRelationDto.setDiagnosisRecordCode(model.getDiagnosisTeacherRecordCode());
        diagnosisClassRelationDto.setSubjectCode(model.getSubjectCode());
        diagnosisClassRelationDto.setClassCode(model.getClassCode());
        List<DiagnosisClassRelationDto> classRelationDtos = diagnosisClassRelationOpenService.getListByParam(diagnosisClassRelationDto);
        if (!CollectionUtils.isEmpty(classRelationDtos)) {
            DiagnosisClassRelationDto diagnosisClassRelationDto1 = classRelationDtos.get(0);
            answerResultDto.setTeacherCode(diagnosisClassRelationDto1.getTeacherCode().toString());
            answerResultDto.setTeacherName(diagnosisClassRelationDto1.getTeacherName());
        } else {
            throw new DiagnosisException(ExceptionCodeEnum.RESOURCE_NOTFOUND.getCode(), "该科没有任课老师信息。");
        }
        answerResultDto.setMakePaperTime(new Timestamp(new Date().getTime()));
        answerResultDto.setBookTypeVersionCode(asd.getBookVersionCode());
        answerResultDto.setSchoolCode(asd.getSchoolCode().toString());
        answerResultDto.setSchoolName(asd.getSchoolName());
        answerResultDto.setClassCode(asd.getClassCode().toString());
        answerResultDto.setClassName(asd.getClassName());
        answerResultDto.setStageCode(asd.getStageCode().toString());
        answerResultDto.setSubjectCode(asd.getSubjectCode().toString());
        answerResultDto.setGradeCode(asd.getGradeCode().toString());
        answerResultDto.setArtType(model.getArtType() == null ? 2 : model.getArtType());
        List<StudentQuestionAnswerResultDto> sqarList = new ArrayList<>();
        StudentQuestionAnswerResultDto sdto = null;

        for (AnswerSheetQuestionDto dto : asd.getAnswerSheetQuestionDtos()) {
            List<Productionmodels> productionmodelsList = new ArrayList<>();
            List<KnowledgeModel> knowledgeModelList = new ArrayList<>();
            sdto = new StudentQuestionAnswerResultDto();
            if (AnswerResultEnum.WRONG.getValue().equals(dto.getIsRight())) {
                sdto.setScore(0d);
            } else {
                sdto.setScore(Double.valueOf(dto.getQuestionScore()));
            }
            sdto.setAnswerResult(dto.getIsRight());
            sdto.setIsComplex(dto.getIsComplex());
            //questionCode为复合题小题code     complexQuestionCode为复合题大题code
            if(dto.getIsComplex().equals(1)){
                sdto.setComplexQuestionCode(dto.getQuestionCode());
                sdto.setQuestionCode(dto.getComplexQuestionCode());
            }else {
                sdto.setQuestionCode(dto.getQuestionCode());
            }
            sdto.setQuestionSn(dto.getQuestionSn());
            List<SimpleTree> simpleTrees = JSONArray.parseArray(dto.getKnowledgeJson(), SimpleTree.class);
            KnowledgeModel km = null;
            for (SimpleTree st : simpleTrees) {
                km = new KnowledgeModel();
                km.setIsTrue(st.getIsRight());
                km.setKnowledgeCode(st.getId());
                km.setKnowledgeName(st.getName());
                List<TeachingMaterialsProductionModel> tps = new ArrayList<>();
                TeachingMaterialsProductionModel tp = null;
                for (SimpleTreeVO stv : st.getSons()) {
                    tp = new TeachingMaterialsProductionModel();
                    tp.setIsTrue(stv.getIsRight());
                    tp.setBaseCode(stv.getBasecode());
                    tp.setTeachingMaterialsCode(stv.getIdVo());
                    tp.setTypecode(stv.getTypecode());
                    tps.add(tp);
                }
                km.setTeachingMaterialsProdutions(tps);
                knowledgeModelList.add(km);
            }
            sdto.setKnowledgeModelList(knowledgeModelList);
            List<BaseProductionModel> baseProductionModels = JSONArray.parseArray(dto.getProductionJson(), BaseProductionModel.class);
            Productionmodels pm = null;
            for (BaseProductionModel bp : baseProductionModels) {
                pm = new Productionmodels();
                pm.setBasecodeVo(bp.getBasecodeVo());
                pm.setBasenameVo(bp.getBasenameVo());
                pm.setIsTrue(bp.getIsTrue());
                pm.setTypecodeVo(bp.getTypecodeVo());
                List<Sons> ss = new ArrayList<>();
                Sons s = null;
                for (SimpleTreeVO sv : bp.getSons()) {
                    s = new Sons();
                    s.setIdVo(sv.getIdVo());
                    s.setNameVo(sv.getNameVo());
                    ss.add(s);
                }
                pm.setSons(ss);
                productionmodelsList.add(pm);
            }
            sdto.setProductionmodelsList(productionmodelsList);
            sqarList.add(sdto);
        }
        answerResultDto.setStudentQuestionAnswerResultDtos(sqarList);
        try {
            StudentReportDto studentReportDto = b2BReportOpenService.generateStudentReport(answerResultDto);
            return studentReportDto;
        } catch (ReportException e) {
            //如果报告生成失败，将参数缓存，备后期获取报告
            String reportParamKey = "reportParam_" + asd.getCode();
            String reportParamVal = JSONObject.toJSONString(answerResultDto);
            redisClientTemplate.set(reportParamKey, reportParamVal);
            //修改学生诊断记录的报告状态为报告异常
            DiagnosisRecordStudentDto dto = new DiagnosisRecordStudentDto();
            dto.setCode(asd.getCode());
            dto.setUpdateTime(new Date());
            dto.setDiagnosisStatus(DiagnosisStausEnum.REPORT_ERROR.getValue());
            diagnosisRecordStudentOpenService.update(dto);
            throw e;
        } catch (ValidationException e) {
            //如果参数校验未通过
            //1.清除学生的答题信息
            DiagnosisStudentAnswerRecordDto diagnosisStudentAnswerRecordDto = new DiagnosisStudentAnswerRecordDto();
            diagnosisStudentAnswerRecordDto.setDiagnosisRecordCode(asd.getCode());
            diagnosisStudentAnswerRecordOpenService.delete(diagnosisStudentAnswerRecordDto);
            //2.清除此次错题信息
            DiagnosisWrongQuestionDto diagnosisWrongQuestionDto = new DiagnosisWrongQuestionDto();
            diagnosisWrongQuestionDto.setDiagnosisRecordCode(asd.getCode());
            diagnosisWrongQuestionOpenService.delete(diagnosisWrongQuestionDto);
            //3.将学生的考试记录状态还原为未答题
            DiagnosisRecordStudentDto dto = new DiagnosisRecordStudentDto();
            dto.setCode(asd.getCode());
            dto.setUpdateTime(new Date());
            dto.setUseTime(null);
            dto.setTotalScore(null);
            dto.setDiagnosisStatus(DiagnosisStausEnum.RELEASE.getValue());
            diagnosisRecordStudentOpenService.update(dto);
            throw e;
        }
    }

}
