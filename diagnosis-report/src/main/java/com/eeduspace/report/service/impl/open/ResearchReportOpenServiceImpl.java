package com.eeduspace.report.service.impl.open;

import com.alibaba.fastjson.JSONArray;
import com.eedu.auth.service.AuthGroupService;
import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.exam.api.dto.DiagnosisRecordStudentDto;
import com.eedu.diagnosis.exam.api.dto.DiagnosisStudentAnswerRecordDto;
import com.eedu.diagnosis.exam.api.openService.DiagnosisRecordStudentOpenService;
import com.eedu.diagnosis.exam.api.openService.DiagnosisRecordTeacherOpenService;
import com.eedu.diagnosis.exam.api.openService.DiagnosisStudentAnswerRecordOpenService;
import com.eeduspace.b2b.report.constant.ReportConstant;
import com.eeduspace.b2b.report.model.*;
import com.eeduspace.b2b.report.model.report.*;
import com.eeduspace.b2b.report.service.ResearchReportOpenService;
import com.eeduspace.report.model.ReleaseViewData;
import com.eeduspace.report.model.ResultsDistributionModel;
import com.eeduspace.report.model.SubjectAbilityModel;
import com.eeduspace.report.po.ExamPo;
import com.eeduspace.report.po.StudentSubjectScorePo;
import com.eeduspace.report.po.TeachingProgressPo;
import com.eeduspace.report.po.UserAnswerResultPo;
import com.eeduspace.report.service.KnowledgeModuleService;
import com.eeduspace.report.service.StudentSubjectScoreService;
import com.eeduspace.report.service.SubjectAbilityService;
import com.eeduspace.report.service.UnitResearchReportService;
import com.eeduspace.report.util.ReportMathUtils;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by liuhongfei on 2017/9/28.
 */
@Slf4j
@Service("researchReportOpenServiceImpl")
@com.alibaba.dubbo.config.annotation.Service
public class ResearchReportOpenServiceImpl implements ResearchReportOpenService {

    @Autowired
    private UnitResearchReportService unitResearchReportService;
    @Autowired
    private DiagnosisStudentAnswerRecordOpenService diagnosisStudentAnswerRecordOpenService;
    @Autowired
    private DiagnosisRecordStudentOpenService diagnosisRecordStudentOpenService;
    @Autowired
    private KnowledgeModuleService knowledgeModuleService;
    @Autowired
    private SubjectAbilityService subjectAbilityService;
    @Autowired
    private AuthGroupService authGroupService;
    @Autowired
    private DiagnosisRecordTeacherOpenService diagnosisRecordTeacherOpenService;
    @Autowired
    private StudentSubjectScoreService studentSubjectScoreService;
    //最小记录数
    private static int minsize = 2;

    /**
     * 获取单元教学成绩
     *
     * @param contrastType 对比类型
     * @param unitCode 单元code
     * @param semester 学期code
     * @param areaCode 区域code
     * @return
     * @throws Exception
     */
    @Override
    public List<AchievementDto> getUnitTeachingAchievement(String contrastType, String unitCode, String semester, Integer areaCode) throws Exception {
        /**
         * 1、获取当前单元所有教师第一次发布记录信息。
         * 2、根据所有的发布记录获取所有的学生答卷得分信息。
         * 3、根据学校、班级、教师分组统计本单元得分详细信息。
         */
        List<AchievementDto> achievementDtos = new ArrayList<>();
        List<ReleaseViewData> releaseViewDatas = loadReleaseExamCode(unitCode, areaCode, semester);
        if (CollectionUtils.isEmpty(releaseViewDatas)){
            return achievementDtos;
        }
        List<StudentGetScoreDo> allStudentGetScore = studentSubjectScoreService.findByReleaseExamCodesIn(releaseViewDatas.stream().map(ReleaseViewData::getEcode).collect(Collectors.toList()));
        //区域平均分
        Double areaAveScore = allStudentGetScore.stream().collect(Collectors.averagingDouble(StudentGetScoreDo::getScore));
        switch (contrastType){
            case ReportConstant.CLASS_CONTRAST :
                Map<String, List<StudentGetScoreDo>> schools = allStudentGetScore.stream().collect(Collectors.groupingBy(StudentGetScoreDo::getSchoolCode));
                schools.forEach((k,v)->{
                    Map<String, List<StudentGetScoreDo>> classCodes = v.stream().collect(Collectors.groupingBy(StudentGetScoreDo::getClassCode));
                    classCodes.forEach((classCode,c)->{
                        AchievementDto achievementDto =new AchievementDto();
                        Double classAveScore = c.stream().collect(Collectors.averagingDouble(StudentGetScoreDo::getScore));
                        achievementDto.setAveScore(ReportMathUtils.rounding(classAveScore,2));
                        achievementDto.setClassCode(classCode);
                        achievementDto.setClassName(c.get(0).getClassName());
                        achievementDto.setSchoolName(v.get(0).getSchoolName());
                        achievementDto.setSchoolCode(k);
                        Double standardDeviation = ReportMathUtils.getStandardDeviation(c.stream().map(StudentGetScoreDo::getScore).collect(Collectors.toList()), areaAveScore);
                        achievementDto.setStand(ReportMathUtils.rounding(standardDeviation,4));
                        achievementDtos.add(achievementDto);
                    });
                });
                return achievementDtos;
            case ReportConstant.TEACHER_CONTRAST :
                Map<String, List<StudentGetScoreDo>> teacherCodes = allStudentGetScore.stream().collect(Collectors.groupingBy(StudentGetScoreDo::getTeacherCode));
                teacherCodes.forEach((k,v)->{
                    Double teacherAveScore = v.stream().collect(Collectors.averagingDouble(StudentGetScoreDo::getScore));
                    AchievementDto achievementDto =new AchievementDto();
                    achievementDto.setAveScore(ReportMathUtils.rounding(teacherAveScore,2));
                    Double standardDeviation = ReportMathUtils.getStandardDeviation(v.stream().map(StudentGetScoreDo::getScore).collect(Collectors.toList()), areaAveScore);
                    achievementDto.setStand(ReportMathUtils.rounding(standardDeviation,4));
                    achievementDto.setTeacherCode(k);
                    achievementDto.setTeacherName(v.get(0).getTeacherName());
                    achievementDto.setSchoolCode(v.get(0).getSchoolCode());
                    achievementDto.setSchoolName(v.get(0).getSchoolName());
                    achievementDto.setClassName(v.get(0).getClassName());
                    achievementDto.setClassCode(v.get(0).getClassCode());
                    achievementDtos.add(achievementDto);
                });
                return achievementDtos;
            default:
                return achievementDtos;
        }
    }

    /**
     * 获取单元能力信息
     *
     * @param contrastType 对比类型
     * @param unitCode     单元code
     * @param semester     学期
     * @param areaCode     区域code
     * @return
     * @throws Exception
     */
    @Override
    public List<BaseAbilityDto> getUnitAbility(String contrastType, String unitCode, String semester, Integer areaCode) throws Exception {
        List<ReleaseViewData> releaseViewDatas = loadReleaseExamCode(unitCode, areaCode, semester);
        List<BaseAbilityDto> dtos = new ArrayList<>();
        if(CollectionUtils.isEmpty(releaseViewDatas)){
            return dtos;
        }
        List<SubjectAbilityModel> subjectAbilityModels = subjectAbilityService.getByReleaseCodeIn(releaseViewDatas.stream().map(r -> r.getCode()).collect(Collectors.toList()));
        //能力分组
        Map<String, List<SubjectAbilityModel>> ability = subjectAbilityModels.stream().collect(Collectors.groupingBy(SubjectAbilityModel::getAbilityCode));

        switch (contrastType){
            case ReportConstant.GRADE_CONTRAST:
                ability.forEach((k, v) ->{
                    BaseAbilityDto dto = new BaseAbilityDto();
                    dto.setAbilityCode(k);
                    dto.setScore(v.stream().collect(Collectors.averagingDouble(SubjectAbilityModel::getAbilityScore)));
                    dto.setAbilityName(v.get(0).getAbilityName());
                    dto.setOwnerCode(v.get(0).getGradeCode());
                    dto.setOwnerName(ReportConstant.gradeCode2GradeName(v.get(0).getGradeCode()));
                    dtos.add(dto);
                });
                return dtos;
            case ReportConstant.SCHOOL_CONTRAST:
                //按照学校分组
                ability.forEach((k,v)->{
                    Map<String, List<SubjectAbilityModel>> schools = v.stream().collect(Collectors.groupingBy(SubjectAbilityModel::getSchoolCode));
                    schools.forEach((school,a)->{
                        BaseAbilityDto dto = new BaseAbilityDto();
                        dto.setScore(a.stream().collect(Collectors.averagingDouble(SubjectAbilityModel::getAbilityScore)));
                        dto.setOwnerCode(school);
                        dto.setOwnerName(a.get(0).getSchoolName());
                        dto.setAbilityCode(k);
                        dto.setAbilityName(v.get(0).getAbilityName());
                        dtos.add(dto);
                    });
                });
                return dtos;
            case ReportConstant.CLASS_CONTRAST:
                //按照班级分组
                ability.forEach((k,v)->{
                    Map<String, List<SubjectAbilityModel>> classCodes = v.stream().collect(Collectors.groupingBy(SubjectAbilityModel::getClassCode));
                    classCodes.forEach((classCode,c)->{
                        BaseAbilityDto dto = new BaseAbilityDto();
                        dto.setScore(c.stream().collect(Collectors.averagingDouble(SubjectAbilityModel::getAbilityScore)));
                        dto.setOwnerName(c.get(0).getSchoolName()+c.get(0).getClassName());
                        dto.setOwnerCode(classCode);
                        dto.setAbilityName(v.get(0).getAbilityName());
                        dto.setAbilityCode(k);
                        dtos.add(dto);
                    });
                });
                return dtos;
            case ReportConstant.TEACHER_CONTRAST:
                //按照教师分组
                ability.forEach((k,v)->{
                    Map<String, List<SubjectAbilityModel>> teachers = v.stream().collect(Collectors.groupingBy(SubjectAbilityModel::getTeacherCode));
                    teachers.forEach((teacherCode,t)->{
                        BaseAbilityDto dto = new BaseAbilityDto();
                        dto.setScore(t.stream().collect(Collectors.averagingDouble(SubjectAbilityModel::getAbilityScore)));
                        dto.setOwnerCode(teacherCode);
                        dto.setOwnerName(t.get(0).getTeacherName());
                        dto.setAbilityCode(k);
                        dto.setAbilityName(v.get(0).getAbilityName());
                        dtos.add(dto);
                    });
                });
                return dtos;
        }

        return dtos;
    }

    /**
     * 教研员获取单元知识点模块掌握情况
     *
     * @param unitCode 单元code
     * @param semester 学期
     * @param areaCode 区域code
     * @param contrastType 对比类型
     * @return
     * @throws Exception
     */
    @Override
    public List<BaseKnowledgeModuleDto> getUnitKnowledgeModule(String contrastType,String unitCode, String semester, Integer areaCode) throws Exception {
        List<ReleaseViewData> releaseViewDatas = loadReleaseExamCode(unitCode, areaCode, semester);
        List<BaseKnowledgeModuleDto> baseKnowledgeModuleDtos = new ArrayList<>();

        List<KnowledgeModuleModel> knowledgeModuleModels = knowledgeModuleService.findByReleaseCodeIn(releaseViewDatas.stream().map(r -> r.getCode()).collect(Collectors.toList()));
        if (CollectionUtils.isEmpty(knowledgeModuleModels)) {
            return baseKnowledgeModuleDtos;
        }
        Map<String, List<KnowledgeModuleModel>> kModules = knowledgeModuleModels.stream().collect(Collectors.groupingBy(KnowledgeModuleModel::getKnowledgeModuleCode));


        switch (contrastType){
            case ReportConstant.GRADE_CONTRAST:
                for (Map.Entry<String, List<KnowledgeModuleModel>> m : kModules.entrySet()) {
                    BaseKnowledgeModuleDto dto =new BaseKnowledgeModuleDto();
                    dto.setScore(m.getValue().stream().collect(Collectors.averagingDouble(KnowledgeModuleModel::getKnowledgeModuleScore)));
                    dto.setKnowledgeModuleCode(m.getKey());
                    dto.setKnowledgeModuleName(m.getValue().get(0).getKnowledgeModuleName());
                    dto.setOwnerCode(m.getValue().get(0).getGradeCode());
                    dto.setOwnerName(ReportConstant.gradeCode2GradeName(m.getValue().get(0).getGradeCode()));
                    baseKnowledgeModuleDtos.add(dto);
                }
                break;
            case ReportConstant.SCHOOL_CONTRAST:
                for (Map.Entry<String, List<KnowledgeModuleModel>> m : kModules.entrySet()) {
                    //按照学校分组
                    Map<String, List<KnowledgeModuleModel>> schools = m.getValue().stream().collect(Collectors.groupingBy(KnowledgeModuleModel::getSchoolCode));
                    for (Map.Entry<String, List<KnowledgeModuleModel>> s : schools.entrySet()) {
                        BaseKnowledgeModuleDto dto =new BaseKnowledgeModuleDto();
                        dto.setScore(s.getValue().stream().collect(Collectors.averagingDouble(KnowledgeModuleModel::getKnowledgeModuleScore)));
                        dto.setKnowledgeModuleCode(m.getKey());
                        dto.setKnowledgeModuleName(m.getValue().get(0).getKnowledgeModuleName());
                        dto.setOwnerCode(s.getKey());
                        dto.setOwnerName(s.getValue().get(0).getSchoolName());
                        baseKnowledgeModuleDtos.add(dto);
                    }
                }
                break;
            case ReportConstant.CLASS_CONTRAST:
                for (Map.Entry<String, List<KnowledgeModuleModel>> m : kModules.entrySet()) {
                    //按照班级分组
                    Map<String, List<KnowledgeModuleModel>> classCodes = m.getValue().stream().collect(Collectors.groupingBy(KnowledgeModuleModel::getClassCode));
                    for (Map.Entry<String, List<KnowledgeModuleModel>> c : classCodes.entrySet()) {
                        BaseKnowledgeModuleDto dto =new BaseKnowledgeModuleDto();
                        dto.setScore(c.getValue().stream().collect(Collectors.averagingDouble(KnowledgeModuleModel::getKnowledgeModuleScore)));
                        dto.setKnowledgeModuleCode(m.getKey());
                        dto.setKnowledgeModuleName(m.getValue().get(0).getKnowledgeModuleName());
                        dto.setOwnerCode(c.getKey());
                        dto.setOwnerName(c.getValue().get(0).getSchoolName()+c.getValue().get(0).getClassName());
                        baseKnowledgeModuleDtos.add(dto);
                    }
                }
                break;
            case ReportConstant.TEACHER_CONTRAST:

                for (Map.Entry<String, List<KnowledgeModuleModel>> m : kModules.entrySet()) {
                    //按照教师分组
                    Map<String, List<KnowledgeModuleModel>> teacherCodes = m.getValue().stream().collect(Collectors.groupingBy(KnowledgeModuleModel::getTeacherCode));
                    for (Map.Entry<String, List<KnowledgeModuleModel>> t : teacherCodes.entrySet()) {
                        BaseKnowledgeModuleDto dto =new BaseKnowledgeModuleDto();
                        dto.setScore(t.getValue().stream().collect(Collectors.averagingDouble(KnowledgeModuleModel::getKnowledgeModuleScore)));
                        dto.setKnowledgeModuleName(m.getValue().get(0).getKnowledgeModuleName());
                        dto.setKnowledgeModuleCode(m.getKey());
                        dto.setOwnerCode(t.getKey());
                        dto.setOwnerName(t.getValue().get(0).getSchoolName()+"-"+t.getValue().get(0).getTeacherName());
                        baseKnowledgeModuleDtos.add(dto);
                    }
                }
                break;
        }
        return baseKnowledgeModuleDtos;
    }

    /**
     * 获取区域年级下单元平均分
     *
     * @param gradeCode 年级code
     * @param semester  学期code
     * @param unitModels
     * 单元code
     * @param areaCode  区域code
     * @param contrastType 对比类型
     * @return
     */
    @Override
    public Map<String,Object> getAreaUnitScore(String contrastType,String gradeCode, String semester, List<UnitModel> unitModels, Integer areaCode) throws Exception{
        List<UnitAveRnakDto> unitAveRnakDtos = new ArrayList<>();
        List<OrgDto> orgDtos = new ArrayList<>();
        Map<String,Object> result =new HashMap();

        List<ReleaseViewData> datas = unitResearchReportService.getReleaseViewDataByCodes(unitModels.stream().map(UnitModel::getUnitCode).collect(Collectors.toList()), areaCode, semester);
        if (CollectionUtils.isEmpty(datas)){
            return result;
        }
        //根据单元分组
        Map<String, List<ReleaseViewData>> unitCodes = datas.stream().collect(Collectors.groupingBy(ReleaseViewData::getUnitCode));
        List<ReleaseViewData> uniqueDatas = new ArrayList<>();
        //获取所有教师第一次单元测试信息
        unitCodes.forEach((k,v)->{
            //根据教师分组
            Map<Integer, List<ReleaseViewData>> teacherCodes = v.stream().collect(Collectors.groupingBy(ReleaseViewData::getTeacherCode));
            teacherCodes.forEach((teacherCode,d)->{
                ReleaseViewData releaseViewData = d.stream().sorted(Comparator.comparing(ReleaseViewData::getStartTime)).collect(Collectors.toList()).get(0);
                uniqueDatas.add(releaseViewData);
            });
        });
        //获取发布记录信息表主键codes
        List<Integer> eCodes = uniqueDatas.stream().map(releaseViewData -> releaseViewData.getEcode()).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(eCodes)){
            return result;
        }
        List<StudentGetScoreDo> studentGetScoreDos = studentSubjectScoreService.findByReleaseExamCodesIn(eCodes);
        if (CollectionUtils.isEmpty(studentGetScoreDos)){
            return result;
        }
        Map<String, List<ReleaseViewData>> collect = uniqueDatas.stream().collect(Collectors.groupingBy(ReleaseViewData::getUnitCode));

        switch (contrastType){
            case ReportConstant.GRADE_CONTRAST :
                collect.forEach((k,v)->{
                    UnitAveRnakDto unitAveRnakDto =new UnitAveRnakDto();
                    UnitModel unitModel = new UnitModel();
                    unitModel.setUnitCode(k);
                    unitModel.setSort(v.get(0).getSort());
                    unitModel.setUnitName(v.get(0).getUnitName());
                    unitAveRnakDto.setUnitModel(unitModel);
                    Double aveScore =0d;
                    for (ReleaseViewData releaseViewData : v) {
                        //单个教师单元平均分
                        aveScore += studentGetScoreDos.stream().filter(studentGetScoreDo ->
                                            releaseViewData.getEcode().equals(studentGetScoreDo.getReleaseExamCode())).
                                            collect(Collectors.averagingDouble(StudentGetScoreDo::getScore));
                    }
                    BigDecimal unitAveScore =new BigDecimal(aveScore).divide(new BigDecimal(v.size()),8,BigDecimal.ROUND_HALF_UP);
                    unitAveRnakDto.setScore(ReportMathUtils.rounding(unitAveScore.doubleValue(),2));
                    OrgDto orgDto = new OrgDto();
                    orgDto.setCode(gradeCode);
                    orgDto.setName(ReportConstant.gradeCode2GradeName(gradeCode));
                    unitAveRnakDto.setOrgDto(orgDto);
                    unitAveRnakDtos.add(unitAveRnakDto);
                });
                OrgDto orgDto = new OrgDto();
                orgDto.setCode(gradeCode);
                orgDto.setName(ReportConstant.gradeCode2GradeName(gradeCode));
                orgDtos.add(orgDto);
                break;
            case ReportConstant.SCHOOL_CONTRAST :
                collect.forEach((k,v)->{
                    UnitModel unitModel = new UnitModel();
                    unitModel.setUnitCode(k);
                    unitModel.setSort(v.get(0).getSort());
                    unitModel.setUnitName(v.get(0).getUnitName());
                    Map<String, List<StudentGetScoreDo>> schools = studentGetScoreDos.stream().filter(studentGetScoreDo ->
                        v.stream().map(releaseViewData ->
                        releaseViewData.getEcode()).collect(Collectors.toList()).contains(studentGetScoreDo.getReleaseExamCode())).
                        collect(Collectors.groupingBy(StudentGetScoreDo::getSchoolCode));

                    schools.forEach((s, stu) ->{
                        UnitAveRnakDto unitAveRnakDto =new UnitAveRnakDto();
                        unitAveRnakDto.setUnitModel(unitModel);
                        OrgDto dto = new OrgDto();
                        dto.setCode(s);
                        dto.setName(stu.get(0).getSchoolName());
                        unitAveRnakDto.setOrgDto(dto);
                        unitAveRnakDto.setScore(ReportMathUtils.rounding(stu.stream().collect(Collectors.averagingDouble(StudentGetScoreDo::getScore)),2));
                        orgDtos.add(dto);
                        unitAveRnakDtos.add(unitAveRnakDto);
                    } );
                });
                break;
            default:
                break;
        }

        result.put("datas",unitAveRnakDtos);
        result.put("units",unitModels);
        result.put("orgs",orgDtos);

        return result;
    }

    /**
     * 教研员单元测试题质量分析
     * 教研员单元测试题质量分析
     * @param unitCode  单元CODE
     * @param districtId  区县ID
     * @param semester  学期
     * @param paperCode 试卷CODE
     * @throws Exception
     */
    @Override
    public List<QuestionsqualityModel> unitQualityofQuestionsReport(String unitCode,Integer districtId,String semester,String paperCode) throws Exception {
        //查询教师首次发布的releaseCode
        List<ReleaseViewData> list = loadReleaseExamCode(unitCode,districtId,semester);

        if(CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        //按试卷分组
        Map<String,List<ReleaseViewData>> groupByPaperMap = list.stream().collect(Collectors.groupingBy(ReleaseViewData::getDiagnosisPaperCode));

        List<ReleaseViewData> releaseViewData = groupByPaperMap.get(paperCode);

        if(CollectionUtils.isEmpty(releaseViewData)) {
            return new ArrayList<>();
        }

        Map<Integer,List<ReleaseViewData>> groupByreleaseCode = releaseViewData.stream().collect(Collectors.groupingBy(ReleaseViewData::getEcode));

        List<Integer> codes = new ArrayList<>();

        codes.addAll(groupByreleaseCode.keySet());
        //releaseCode 查询学生答题记录
        List<ExamPo> examPos =  unitResearchReportService.getexam(codes);

        if(CollectionUtils.isEmpty(examPos)) {
            return new ArrayList<>();
        }
        //本次单元 学科下对应的参加考试的所有学生数量
        int sutdentTotalCount = examPos.size();

        Map<Integer,List<ExamPo>> groupByCode = examPos.stream().collect(Collectors.groupingBy(ExamPo::getCode));

        List<Integer> examCodes = new ArrayList<>();

        examCodes.addAll(groupByCode.keySet());

        ExamPo po = examPos.get(0);

        DiagnosisStudentAnswerRecordDto dto = new DiagnosisStudentAnswerRecordDto();

        dto.setDiagnosisRecordCode(po.getUserMakePaperCode());
        //根据学生答题记录 usermakepapercode 查询学生的答题信息  获取试题信息
        Order order = new Order("question_sn+0", Order.Direction.asc);
        PageInfo<DiagnosisStudentAnswerRecordDto> pageInfo =  diagnosisStudentAnswerRecordOpenService.getAnswerRecord(dto,0,Integer.MAX_VALUE,order);

        if(null == pageInfo) {
            return new ArrayList<>();
        }

        List<DiagnosisStudentAnswerRecordDto> question = pageInfo.getList();
        //根据 examCode 查询学生的答题信息  学生每道题的得分
        List<UserAnswerResultPo> answerResultPos = unitResearchReportService.getAnswerResult(examCodes);
        //根据 examCode 查询学生的答题信息 学生的试卷总分
        List<StudentSubjectScorePo> studentSubjectScorePos = unitResearchReportService.getstudentTotalScore(examCodes);

        if(CollectionUtils.isEmpty(studentSubjectScorePos) || studentSubjectScorePos.size() < minsize) {
            return new ArrayList<>();
        }
        //将学生卷面总分成绩按50%：50%的比例分为高分组和低分组两部分  如果有奇数，放在低分组
        int num = studentSubjectScorePos.size() / 2;

        List<StudentSubjectScorePo> highScoreList = new ArrayList<>();

        List<StudentSubjectScorePo> lowScoreList = new ArrayList<>();
        //高分组人员
        highScoreList.addAll(studentSubjectScorePos.subList(0,num));
        //低分组人员
        lowScoreList.addAll(studentSubjectScorePos.subList(num,studentSubjectScorePos.size()));

        if(CollectionUtils.isEmpty(answerResultPos)) {
            return new ArrayList<>();
        }

        Map<String,List<UserAnswerResultPo>> groupByQuestionCode = answerResultPos.stream().collect(Collectors.groupingBy(UserAnswerResultPo::getQuestionCode));

        List<QuestionsqualityModel> result = new ArrayList<>();

        for(DiagnosisStudentAnswerRecordDto diagnosisStudentAnswerRecordDto : question){

            QuestionsqualityModel model = new QuestionsqualityModel();
            //试题序号  CODE
            model.setQuestionSn(Integer.valueOf(diagnosisStudentAnswerRecordDto.getQuestionSn()));
            model.setQuestionCode(diagnosisStudentAnswerRecordDto.getQuestionCode());
            //试题总分
            double questScore = diagnosisStudentAnswerRecordDto.getQuestionScore().doubleValue();
            //所有学生对这道题的答题记录
            List<UserAnswerResultPo> questAnswer =  groupByQuestionCode.get(diagnosisStudentAnswerRecordDto.getQuestionCode());

            if(!CollectionUtils.isEmpty(questAnswer)){
                //examCode 对应学生  1对1   学生不重复
                Map<Integer,List<UserAnswerResultPo>> orderByExamCode = questAnswer.stream().collect(Collectors.groupingBy(UserAnswerResultPo::getExamCode));
                //总人数*题总分的值
                double questTotalScore = sutdentTotalCount * questScore;
                //高分组 在这道题的所得分的和
                double hightScore = 0.0;
                for(StudentSubjectScorePo spo : highScoreList){
                    List<UserAnswerResultPo> highs = orderByExamCode.get(spo.getExamCode());
                    if(!CollectionUtils.isEmpty(highs)){
                        hightScore = hightScore + highs.get(0).getScore();
                    }
                }
                //低分组在这道题的所得分的和
                double lowScore = 0.0;
                for(StudentSubjectScorePo spo : lowScoreList){
                    List<UserAnswerResultPo> lows = orderByExamCode.get(spo.getExamCode());
                    if(!CollectionUtils.isEmpty(lows)){
                        lowScore = lowScore + lows.get(0).getScore();
                    }
                }
                //高分组得分率
                double highdistinction = new BigDecimal(hightScore).divide(new BigDecimal(questTotalScore),4,BigDecimal.ROUND_HALF_UP).doubleValue();
                //低分组得分率
                double lowdistinction = new BigDecimal(lowScore).divide(new BigDecimal(questTotalScore),4,BigDecimal.ROUND_HALF_UP).doubleValue();

                model.setDistinction(new BigDecimal(highdistinction).subtract(new BigDecimal(lowdistinction)).setScale(4,BigDecimal.ROUND_HALF_UP).doubleValue() );
                //获取所有学生在本题得分的平均分
                DoubleSummaryStatistics dss = questAnswer.stream().collect(Collectors.summarizingDouble(UserAnswerResultPo::getScore));

                double ave = new BigDecimal(dss.getAverage()).setScale(4, RoundingMode.HALF_UP).doubleValue();

                double difficulty = new BigDecimal(ave).divide(new BigDecimal(questScore),4,BigDecimal.ROUND_HALF_UP).doubleValue();

                model.setDifficulty(difficulty);
            }
            result.add(model);
        }
        return result;

    }

    /**
     * 教研员单元全区测情况
     * @param unitCode 单元CODE
     * @param districtId  区县ID
     * @param semester 学期
     * @throws Exception
     */
    @Override
    public AreaHappenModel unitAreaHappening(String unitCode, Integer districtId, String semester) throws Exception {

        AreaHappenModel model = new AreaHappenModel();
        //查询教师首次发布的releaseCode
        List<ReleaseViewData> list = loadReleaseExamCode(unitCode, districtId, semester);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        //releaseCode 查询学生答题信息     预留 需要调用接口
        Map<String, List<ReleaseViewData>> groupByCode = list.stream().collect(Collectors.groupingBy(ReleaseViewData::getCode));

        List<String> codes = new ArrayList<>();

        codes.addAll(groupByCode.keySet());

        List<DiagnosisRecordStudentDto> diagnosisRecordStudentDtos = diagnosisRecordStudentOpenService.getListByRealseCodes(codes);

        if (CollectionUtils.isEmpty(diagnosisRecordStudentDtos)) {
            return null;
        }

        Map<String, List<DiagnosisRecordStudentDto>> studentgroupByCode = diagnosisRecordStudentDtos.stream().collect(Collectors.groupingBy(DiagnosisRecordStudentDto::getCode));

        List<String> diagscodes = new ArrayList<>();

        diagscodes.addAll(studentgroupByCode.keySet());

        //学生答题信息的CODE 查询 学生做题信息       预留 需要调用接口
        List<DiagnosisStudentAnswerRecordDto> diagnosisStudentAnswerRecordDtos = diagnosisStudentAnswerRecordOpenService.getAnswerRecordByDiagnosisCodes(diagscodes);

        if (CollectionUtils.isEmpty(diagnosisStudentAnswerRecordDtos)) {
            return null;
        }
        //学生人数
        int studetnCount = diagnosisRecordStudentDtos.size();
        //学生出勤人数
        int recordCount = 0;
        //总答题时间  秒
        int recordTimeSum = 0;

        for (DiagnosisRecordStudentDto dto : diagnosisRecordStudentDtos) {
            if (null != dto.getDiagnosisStatus() && dto.getDiagnosisStatus() != 0) {
                recordCount = recordCount + 1;
            }
            if (null != dto.getUseTime() && !"".equals(dto.getUseTime())) {
                recordTimeSum = recordTimeSum + Integer.valueOf(dto.getUseTime());
            }
        }
        Map<Integer, List<DiagnosisStudentAnswerRecordDto>> groupByStudentCode = diagnosisStudentAnswerRecordDtos.stream().collect(Collectors.groupingBy(DiagnosisStudentAnswerRecordDto::getStudentCode));
        //总作答率
        double totalResponseRate = 0.0;
        for (Integer studentCode : groupByStudentCode.keySet()) {
            int responseRate = 0;
            List<DiagnosisStudentAnswerRecordDto> answerRecordDtos = groupByStudentCode.get(studentCode);
            for (DiagnosisStudentAnswerRecordDto dto : answerRecordDtos) {
                if (null != dto.getStudentAnswer() && !"".equals(dto.getStudentAnswer())) {
                    responseRate = responseRate + 1;
                }
            }
            totalResponseRate = new BigDecimal(totalResponseRate).add(new BigDecimal(responseRate).divide(new BigDecimal(answerRecordDtos.size()),2, BigDecimal.ROUND_HALF_UP)).doubleValue();
        }
        double attendanceRate = 0.0;
        double avgAnswerTime = 0.0;
        double avgResponseRate = 0.0;
        if(recordCount > 0 ) {
            //出勤率
             attendanceRate = new BigDecimal(new BigDecimal(recordCount).divide(new BigDecimal(studetnCount), 4, BigDecimal.ROUND_HALF_UP).doubleValue() * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            //平均作答时间（分钟）
             avgAnswerTime = new BigDecimal(new BigDecimal(recordTimeSum).divide(new BigDecimal(60), 4, BigDecimal.ROUND_HALF_UP).doubleValue()).divide(new BigDecimal(recordCount), 4, BigDecimal.ROUND_HALF_UP).doubleValue();
            //平均作答率
             avgResponseRate = new BigDecimal(new BigDecimal(totalResponseRate).divide(new BigDecimal(recordCount), 4, BigDecimal.ROUND_HALF_UP).doubleValue() * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        model.setAttendanceRate(attendanceRate);
        model.setAvgAnswerTime(avgAnswerTime);
        model.setAvgResponseRate(avgResponseRate);

        return model;
    }


    /**
     * 教研员单元全区各学校测试情况
     * @param unitCode 单元CODE
     * @param districtId  区县ID
     * @param semester 学期
     * @throws Exception
     */
    @Override
    public List<SchoolHappenModel> unitAreaHappeningBySchool(String unitCode, Integer districtId, String semester) throws Exception {

        List<SchoolHappenModel> result = new ArrayList<>();
        //查询教师首次发布的releaseCode
        List<ReleaseViewData> list = loadReleaseExamCode(unitCode, districtId, semester);

        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        Map<String, List<ReleaseViewData>> groupByCode = list.stream().collect(Collectors.groupingBy(ReleaseViewData::getCode));

        List<String> codes = new ArrayList<>();

        codes.addAll(groupByCode.keySet());
        //releaseCode 查询学生答题信息     预留 需要调用接口
        List<DiagnosisRecordStudentDto> diagnosisRecordStudentDtos = diagnosisRecordStudentOpenService.getListByRealseCodes(codes);

        if (CollectionUtils.isEmpty(diagnosisRecordStudentDtos)) {
            return null;
        }

        Map<String, List<DiagnosisRecordStudentDto>> studentgroupByCode = diagnosisRecordStudentDtos.stream().collect(Collectors.groupingBy(DiagnosisRecordStudentDto::getCode));

        List<String> diagscodes = new ArrayList<>();

        diagscodes.addAll(studentgroupByCode.keySet());

        //学生答题信息的CODE 查询 学生做题信息       预留 需要调用接口
        List<DiagnosisStudentAnswerRecordDto> diagnosisStudentAnswerRecordDtos = diagnosisStudentAnswerRecordOpenService.getAnswerRecordByDiagnosisCodes(diagscodes);

        if (CollectionUtils.isEmpty(diagnosisStudentAnswerRecordDtos)) {
            return null;
        }
        //全区学生答题记录按学校分组
        Map<Integer, List<DiagnosisRecordStudentDto>> diagnosisRecordStudentDtosByschool = diagnosisRecordStudentDtos.stream().collect(Collectors.groupingBy(DiagnosisRecordStudentDto::getSchoolCode));
        //全区学生答题信息按答题记录CODE分组
        Map<String, List<DiagnosisStudentAnswerRecordDto>> answerByRecordCode = diagnosisStudentAnswerRecordDtos.stream().collect(Collectors.groupingBy(DiagnosisStudentAnswerRecordDto::getDiagnosisRecordCode));

        for (Integer schoolCode : diagnosisRecordStudentDtosByschool.keySet()) {

            List<DiagnosisRecordStudentDto> schoolDatas = diagnosisRecordStudentDtosByschool.get(schoolCode);

            List<DiagnosisStudentAnswerRecordDto> schoolAnswerDatas = new ArrayList<>();
            //拼接单个学校的学生答题信息
            for (DiagnosisRecordStudentDto dto : schoolDatas) {

                List<DiagnosisStudentAnswerRecordDto> answerRecordDtos = answerByRecordCode.get(dto.getCode());

                if (!CollectionUtils.isEmpty(answerRecordDtos)) {
                    schoolAnswerDatas.addAll(answerRecordDtos);
                }

            }

            SchoolHappenModel model = new SchoolHappenModel();

            model.setSchoolId(schoolCode);

            model.setSchoolName(schoolDatas.get(0).getSchoolName());
            //学生人数
            int studetnCount = schoolDatas.size();
            //学生出勤人数
            int recordCount = 0;
            //总答题时间  秒
            int recordTimeSum = 0;

            for (DiagnosisRecordStudentDto dto : schoolDatas) {
                if (null != dto.getDiagnosisStatus() && dto.getDiagnosisStatus() != 0) {
                    recordCount = recordCount + 1;
                }
                if (null != dto.getUseTime() && !"".equals(dto.getUseTime())) {
                    recordTimeSum = recordTimeSum + Integer.valueOf(dto.getUseTime());
                }
            }
            Map<Integer, List<DiagnosisStudentAnswerRecordDto>> groupByStudentCode = schoolAnswerDatas.stream().collect(Collectors.groupingBy(DiagnosisStudentAnswerRecordDto::getStudentCode));
            //总作答率
            double totalResponseRate = 0.0;
            for (Integer studentCode : groupByStudentCode.keySet()) {
                int responseRate = 0;
                List<DiagnosisStudentAnswerRecordDto> answerRecordDtos = groupByStudentCode.get(studentCode);
                for (DiagnosisStudentAnswerRecordDto dto : answerRecordDtos) {
                    if (null != dto.getStudentAnswer() && !"".equals(dto.getStudentAnswer())) {
                        responseRate = responseRate + 1;
                    }
                }
                totalResponseRate = new BigDecimal(totalResponseRate).add(new BigDecimal(responseRate).divide(new BigDecimal(answerRecordDtos.size()),2, BigDecimal.ROUND_HALF_UP)).doubleValue();
            }
            double attendanceRate = 0.0;
            double avgAnswerTime = 0.0;
            double avgResponseRate = 0.0;
            if(recordCount > 0 ) {
                //出勤率
                 attendanceRate = new BigDecimal(new BigDecimal(recordCount).divide(new BigDecimal(studetnCount), 4, BigDecimal.ROUND_HALF_UP).doubleValue() * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                //平均作答时间（分钟）
                 avgAnswerTime = new BigDecimal(new BigDecimal(recordTimeSum).divide(new BigDecimal(60), 4, BigDecimal.ROUND_HALF_UP).doubleValue()).divide(new BigDecimal(recordCount), 4, BigDecimal.ROUND_HALF_UP).doubleValue();
                //平均作答率
                 avgResponseRate = new BigDecimal(new BigDecimal(totalResponseRate).divide(new BigDecimal(recordCount), 4, BigDecimal.ROUND_HALF_UP).doubleValue() * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            model.setAttendanceRate(attendanceRate);
            model.setAvgAnswerTime(avgAnswerTime);
            model.setAvgResponseRate(avgResponseRate);
            result.add(model);

        }
        return result;
    }


    /**
     * 教研员单元全区各学校班级测试情况
     * @param unitCode 单元CODE
     * @param districtId  区县ID
     * @param semester 学期
     * @throws Exception
     */
    @Override
    public List<ClassHappenModel> unitAreaHappeningByclass(String unitCode, Integer districtId, String semester) throws Exception {

        List<ClassHappenModel> result = new ArrayList<>();
        //查询教师首次发布的releaseCode
        List<ReleaseViewData> list = loadReleaseExamCode(unitCode, districtId, semester);

        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        Map<String, List<ReleaseViewData>> groupByCode = list.stream().collect(Collectors.groupingBy(ReleaseViewData::getCode));

        List<String> codes = new ArrayList<>();

        codes.addAll(groupByCode.keySet());
        //releaseCode 查询学生答题信息     预留 需要调用接口
        List<DiagnosisRecordStudentDto> diagnosisRecordStudentDtos = diagnosisRecordStudentOpenService.getListByRealseCodes(codes);

        if (CollectionUtils.isEmpty(diagnosisRecordStudentDtos)) {
            return null;
        }

        Map<String, List<DiagnosisRecordStudentDto>> studentgroupByCode = diagnosisRecordStudentDtos.stream().collect(Collectors.groupingBy(DiagnosisRecordStudentDto::getCode));

        List<String> diagscodes = new ArrayList<>();

        diagscodes.addAll(studentgroupByCode.keySet());

        //学生答题信息的CODE 查询 学生做题信息       预留 需要调用接口
        List<DiagnosisStudentAnswerRecordDto> diagnosisStudentAnswerRecordDtos = diagnosisStudentAnswerRecordOpenService.getAnswerRecordByDiagnosisCodes(diagscodes);

        if (CollectionUtils.isEmpty(diagnosisStudentAnswerRecordDtos)) {
            return null;
        }
        //全区学生答题记录按学校分组
        Map<Integer, List<DiagnosisRecordStudentDto>> diagnosisRecordStudentDtosByschool = diagnosisRecordStudentDtos.stream().collect(Collectors.groupingBy(DiagnosisRecordStudentDto::getSchoolCode));
        //全区学生答题信息按答题记录CODE分组
        Map<String, List<DiagnosisStudentAnswerRecordDto>> answerByRecordCode = diagnosisStudentAnswerRecordDtos.stream().collect(Collectors.groupingBy(DiagnosisStudentAnswerRecordDto::getDiagnosisRecordCode));

        for (Integer schoolCode : diagnosisRecordStudentDtosByschool.keySet()) {

            List<DiagnosisRecordStudentDto> schoolDatas = diagnosisRecordStudentDtosByschool.get(schoolCode);

            List<HappenModel> models = new ArrayList<>();

            ClassHappenModel model = new ClassHappenModel();

            model.setSchoolId(schoolCode);

            model.setSchoolName(schoolDatas.get(0).getSchoolName());
            //学校学生答题记录按年级分组
            Map<Integer, List<DiagnosisRecordStudentDto>> groupbyGrade = schoolDatas.stream().collect(Collectors.groupingBy(DiagnosisRecordStudentDto::getGradeCode));



            for (Integer gradeCode : groupbyGrade.keySet()) {

                List<DiagnosisRecordStudentDto> gradeDatas = groupbyGrade.get(gradeCode);
                //学校学生答题记录按班级分组
                Map<Integer, List<DiagnosisRecordStudentDto>> groupbyClass = gradeDatas.stream().collect(Collectors.groupingBy(DiagnosisRecordStudentDto::getClassCode));

                for (Integer classCode : groupbyClass.keySet()) {

                    HappenModel happenModel = new HappenModel();

                    List<DiagnosisStudentAnswerRecordDto> classAnswerDatas = new ArrayList<>();

                    List<DiagnosisRecordStudentDto> classDatas = groupbyClass.get(classCode);
                    //拼接单个班级的学生答题信息
                    for (DiagnosisRecordStudentDto dto : classDatas) {

                        List<DiagnosisStudentAnswerRecordDto> answerRecordDtos = answerByRecordCode.get(dto.getCode());

                        if (!CollectionUtils.isEmpty(answerRecordDtos)) {
                            classAnswerDatas.addAll(answerRecordDtos);
                        }

                    }
                    //学生人数
                    int studetnCount = classDatas.size();
                    //学生出勤人数
                    int recordCount = 0;
                    //总答题时间  秒
                    int recordTimeSum = 0;

                    for (DiagnosisRecordStudentDto dto : classDatas) {
                        if (null != dto.getDiagnosisStatus() && dto.getDiagnosisStatus() != 0) {
                            recordCount = recordCount + 1;
                        }
                        if (null != dto.getUseTime() && !"".equals(dto.getUseTime())) {
                            recordTimeSum = recordTimeSum + Integer.valueOf(dto.getUseTime());
                        }
                    }
                    Map<Integer, List<DiagnosisStudentAnswerRecordDto>> groupByStudentCode = classAnswerDatas.stream().collect(Collectors.groupingBy(DiagnosisStudentAnswerRecordDto::getStudentCode));
                    //总作答率
                    double totalResponseRate = 0.0;
                    for (Integer studentCode : groupByStudentCode.keySet()) {
                        int responseRate = 0;
                        List<DiagnosisStudentAnswerRecordDto> answerRecordDtos = groupByStudentCode.get(studentCode);
                        for (DiagnosisStudentAnswerRecordDto dto : answerRecordDtos) {
                            if (null != dto.getStudentAnswer() && !"".equals(dto.getStudentAnswer())) {
                                responseRate = responseRate + 1;
                            }
                        }
                        totalResponseRate = new BigDecimal(totalResponseRate).add(new BigDecimal(responseRate).divide(new BigDecimal(answerRecordDtos.size()),2, BigDecimal.ROUND_HALF_UP)).doubleValue();
                    }

                    double attendanceRate = 0.0;
                    double avgAnswerTime = 0.0;
                    double avgResponseRate = 0.0;
                    if(recordCount > 0 ) {
                        //出勤率
                         attendanceRate = new BigDecimal(new BigDecimal(recordCount).divide(new BigDecimal(studetnCount), 4, BigDecimal.ROUND_HALF_UP).doubleValue() * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        //平均作答时间（分钟）
                         avgAnswerTime = new BigDecimal(new BigDecimal(recordTimeSum).divide(new BigDecimal(60), 4, BigDecimal.ROUND_HALF_UP).doubleValue()).divide(new BigDecimal(recordCount), 4, BigDecimal.ROUND_HALF_UP).doubleValue();
                        //平均作答率
                         avgResponseRate = new BigDecimal(new BigDecimal(totalResponseRate).divide(new BigDecimal(recordCount), 4, BigDecimal.ROUND_HALF_UP).doubleValue() * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    }
                    happenModel.setGradeCode(gradeCode);
                    happenModel.setClassCode(classCode);
                    happenModel.setClassName(classDatas.get(0).getClassName());
                    happenModel.setAttendanceRate(attendanceRate);
                    happenModel.setAvgAnswerTime(avgAnswerTime);
                    happenModel.setAvgResponseRate(avgResponseRate);
                    models.add(happenModel);

                }
            }
            model.setHappenModels(models);
            result.add(model);
        }
        return result;
    }

    /**
     * 全区各年级总体情进度
     * @param gradeCode 年级CODE
     * @param unitModels 多单元CODE
     * @param districtId 区县CODE
     * @param semester 学期
     * @return
     * @throws Exception
     */
    @Override
    public  List<TeachingProgressModel> teachingProgressByArea(Integer gradeCode,List<UnitModel> unitModels, Integer districtId, String semester) throws Exception{

        List<TeachingProgressModel> result = new ArrayList<>();

        List<String> codes = unitModels.stream().map(UnitModel::getUnitCode).collect(Collectors.toList());
        //查询教师发布的所有单元测试releaseCode
        List<ReleaseViewData> list = unitResearchReportService.getReleaseViewDataByCodes(codes, districtId, semester);

        if(CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        //单元CODE分组
        Map<String,List<ReleaseViewData>> groupbyUnitCode = list.stream().collect(Collectors.groupingBy(ReleaseViewData::getUnitCode));

        for(UnitModel unitModel : unitModels){
            //单次单元对应的发布记录CODE组
            List<ReleaseViewData> unitList = groupbyUnitCode.get(unitModel.getUnitCode());

            TeachingProgressModel model = new TeachingProgressModel();

            model.setUnitCode(unitModel.getUnitCode());
            model.setUnitName(unitModel.getUnitName());
            model.setGradeCode(gradeCode);

            if(!CollectionUtils.isEmpty(unitList)) {

                List<ReleaseViewData> artsort = new ArrayList<>();
                //发布记录按教师分组
                Map<Integer, List<ReleaseViewData>> groupByTeacherMap = unitList.stream().collect(Collectors.groupingBy(ReleaseViewData::getTeacherCode));
                //轮询教师releaseCode  比较发布时间  获取每个教师最早一次的发布时间
                for (Map.Entry<Integer, List<ReleaseViewData>> integerListEntry : groupByTeacherMap.entrySet()) {
                    List<ReleaseViewData> releaseViewData = integerListEntry.getValue();
                    List<ReleaseViewData> sort = releaseViewData.stream().sorted(Comparator.comparing(ReleaseViewData::getStartTime)).collect(Collectors.toList());
                    artsort.add(sort.get(0));
                }
                //所有教师的发布记录时间倒序排  取最后一次发布时间
                List<ReleaseViewData> descsort = artsort.stream().sorted(Comparator.comparing(ReleaseViewData::getStartTime).reversed()).collect(Collectors.toList());

                model.setStartTime(descsort.get(0).getStartTime());
            }
            result.add(model);
        }


        return result;


    }

    /**
     * 全区各学校年级总体情进度
     * @param gradeCode 年级CODE
     * @param unitModels 多单元CODE
     * @param districtId 区县CODE
     * @param semester 学期
     * @return
     * @throws Exception
     */
    @Override
    public List<SchoolProgressModel> teachingProgressBySchool(Integer gradeCode,List<UnitModel> unitModels, Integer districtId, String semester) throws Exception{

        List<SchoolProgressModel> result = new ArrayList<>();

        List<String> codes = unitModels.stream().map(UnitModel::getUnitCode).collect(Collectors.toList());
        //查询教师发布的所有单元测试releaseCode
        List<ReleaseViewData> list = unitResearchReportService.getReleaseViewDataByCodes(codes, districtId, semester);

        if(CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        //发布记录学校分组
        Map<Integer,List<ReleaseViewData>> unitListBySchool = list.stream().collect(Collectors.groupingBy(ReleaseViewData::getSchoolCode));

        for(Integer schoolCode : unitListBySchool.keySet()) {
            //单个学校下  单元的发布记录
            List<ReleaseViewData> dataList = unitListBySchool.get(schoolCode);
            //单元CODE分组
            Map<String, List<ReleaseViewData>> groupbyUnitCode = dataList.stream().collect(Collectors.groupingBy(ReleaseViewData::getUnitCode));

            SchoolProgressModel schoolModel = new SchoolProgressModel();
            schoolModel.setSchoolCode(schoolCode);

            schoolModel.setSchoolName(dataList.get(0).getSchoolName());

            List<TeachingProgressModel> teachingProgressModels = new ArrayList<>();

            for (UnitModel unitModel : unitModels) {

                List<ReleaseViewData> unitList = groupbyUnitCode.get(unitModel.getUnitCode());

                TeachingProgressModel model = new TeachingProgressModel();
                model.setUnitCode(unitModel.getUnitCode());
                model.setUnitName(unitModel.getUnitName());
                model.setGradeCode(gradeCode);

                if (!CollectionUtils.isEmpty(unitList)) {

                    List<ReleaseViewData> artsort = new ArrayList<>();
                    //发布记录按教师分组
                    Map<Integer, List<ReleaseViewData>> groupByTeacherMap = unitList.stream().collect(Collectors.groupingBy(ReleaseViewData::getTeacherCode));
                    //轮询教师releaseCode  比较发布时间 获取每个教师最早一次的发布时间
                    for (Map.Entry<Integer, List<ReleaseViewData>> integerListEntry : groupByTeacherMap.entrySet()) {
                        List<ReleaseViewData> releaseViewData = integerListEntry.getValue();
                        List<ReleaseViewData> sort = releaseViewData.stream().sorted(Comparator.comparing(ReleaseViewData::getStartTime)).collect(Collectors.toList());
                        artsort.add(sort.get(0));
                    }
                    //所有教师的发布记录时间倒序排  取最后一次发布时间
                    List<ReleaseViewData> descsort = artsort.stream().sorted(Comparator.comparing(ReleaseViewData::getStartTime).reversed()).collect(Collectors.toList());

                    model.setStartTime(descsort.get(0).getStartTime());
                }
                teachingProgressModels.add(model);
            }
            schoolModel.setTeachingProgressModels(teachingProgressModels);

            result.add(schoolModel);
        }

        return result;


    }



    /**
     * 全区各学校教师总体情进度
     * @param gradeCode 年级CODE
     * @param unitModels 多单元CODE
     * @param districtId 区县CODE
     * @param semester 学期
     * @return
     * @throws Exception
     */
    @Override
    public List<SchoolProgressModel> teachingProgressByTeacher(Integer gradeCode,List<UnitModel> unitModels, Integer districtId, String semester) throws Exception{

        List<SchoolProgressModel> result = new ArrayList<>();

        List<String> codes = unitModels.stream().map(UnitModel::getUnitCode).collect(Collectors.toList());
        //查询教师发布的所有单元测试releaseCode
        List<ReleaseViewData> list = unitResearchReportService.getReleaseViewDataByCodes(codes, districtId, semester);

        if(CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        //所有单元发布记录以学校分组
        Map<Integer,List<ReleaseViewData>> unitListBySchool = list.stream().collect(Collectors.groupingBy(ReleaseViewData::getSchoolCode));

        for(Integer schoolCode : unitListBySchool.keySet()) {

            List<ReleaseViewData> dataList = unitListBySchool.get(schoolCode);
            //单元CODE分组
            Map<String, List<ReleaseViewData>> groupbyUnitCode = dataList.stream().collect(Collectors.groupingBy(ReleaseViewData::getUnitCode));

            SchoolProgressModel schoolModel = new SchoolProgressModel();

            schoolModel.setSchoolCode(schoolCode);

            schoolModel.setSchoolName(dataList.get(0).getSchoolName());
            //单个学校所有单元发布记录以教师分组
            Map<Integer, List<ReleaseViewData>> teadataMap = dataList.stream().collect(Collectors.groupingBy(ReleaseViewData::getTeacherCode));

            List<TeacherTeachingProgressModel> teacherTeachingProgressModels = new ArrayList<>();

            for(Integer teacherCode : teadataMap.keySet()){
                //单个教师所有单元的发布记录
                List<ReleaseViewData> teadataList = teadataMap.get(teacherCode);

                TeacherTeachingProgressModel teacherTeachingProgressModel = new TeacherTeachingProgressModel();

                teacherTeachingProgressModel.setTeacherCode(teacherCode);

                teacherTeachingProgressModel.setTeacherName(teadataList.get(0).getTeacherName());

                Map<String,List<ReleaseViewData>> teaUnitDataList = teadataList.stream().collect(Collectors.groupingBy(ReleaseViewData::getUnitCode));

                List<TeachingProgressModel> teachingProgressModels = new ArrayList<>();

                for (UnitModel unitModel : unitModels) {

                    List<ReleaseViewData> unitList = teaUnitDataList.get(unitModel.getUnitCode());

                    TeachingProgressModel teachingProgressModel = new TeachingProgressModel();
                    teachingProgressModel.setUnitCode(unitModel.getUnitCode());
                    teachingProgressModel.setUnitName(unitModel.getUnitName());
                    if (!CollectionUtils.isEmpty(unitList)) {

                        //正序排  取最大元素 获取每个教师最早一次的发布时间
                    List<ReleaseViewData> artsort = unitList.stream().sorted(Comparator.comparing(ReleaseViewData::getStartTime)).collect(Collectors.toList());

                        teachingProgressModel.setStartTime(artsort.get(0).getStartTime());
                    }
                    teachingProgressModels.add(teachingProgressModel);
                    teacherTeachingProgressModel.setTeachingProgressModels(teachingProgressModels);
                }
                teacherTeachingProgressModels.add(teacherTeachingProgressModel);
            }
            schoolModel.setTeacherTeachingProgressModels(teacherTeachingProgressModels);
            result.add(schoolModel);
        }

        return result;
    }



    /**
     *  教研员单元区域，学校，班级 学生成绩分布
     * @param unitCode  单元CODE
     * @param districtId 区县ID
     * @param semester  学期
     * @param schoolCode  学校CODE
     * @param gradeCode 年级CODE
     * @param classCode 班级CODE
     * @return
     * @throws Exception
     */
    @Override
    public List<DistributedModel> unitResultsDistribution(String unitCode, Integer districtId, String semester, Integer schoolCode,Integer gradeCode, Integer classCode)throws Exception{

        List<DistributedModel> result = new ArrayList<>();

        //查询教师首次发布的releaseCode
        List<ReleaseViewData> list = loadReleaseExamCode(unitCode,districtId,semester);

        if(CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        //releaseCode 分组
        Map<Integer,List<ReleaseViewData>> groupByreleaseCode = list.stream().collect(Collectors.groupingBy(ReleaseViewData::getEcode));

        List<Integer> codes = new ArrayList<>();

        codes.addAll(groupByreleaseCode.keySet());
        //查询学生答卷总分
        List<ResultsDistributionModel> resultsDistributionModels = unitResearchReportService.getstudentTotalScoreByGroup(codes,null,gradeCode,null,null);

        if(CollectionUtils.isEmpty(resultsDistributionModels)) {
            return new ArrayList<>();
        }
        //十个百分比区间
        double[][] interval = {{0.0,0.6},{0.6,0.7},{0.7,0.8},{0.8,0.9},{0.9,1.0}};

        int count = 0;
        for(double[] d : interval){
            count = count + 1;
            DistributedModel model = new DistributedModel();
            model.setSn(count);
            model.setSegment(d[0]*100+"% - "+d[1]*100+"%");
            //分组取各区间内的记录数
            List<ResultsDistributionModel> filterList = new ArrayList<>();
            if(count == interval.length) {
                filterList = resultsDistributionModels.stream().filter(a -> new BigDecimal(a.getScore()).divide(new BigDecimal(a.getPaperScore()),2,BigDecimal.ROUND_HALF_UP).doubleValue() >= d[0] && new BigDecimal(a.getScore()).divide(new BigDecimal(a.getPaperScore()),2,BigDecimal.ROUND_HALF_UP).doubleValue() <= d[1]).collect(Collectors.toList());
            } else {
                filterList = resultsDistributionModels.stream().filter(a -> new BigDecimal(a.getScore()).divide(new BigDecimal(a.getPaperScore()),2,BigDecimal.ROUND_HALF_UP).doubleValue() >= d[0] && new BigDecimal(a.getScore()).divide(new BigDecimal(a.getPaperScore()),2,BigDecimal.ROUND_HALF_UP).doubleValue() < d[1]).collect(Collectors.toList());
            }

            if(CollectionUtils.isEmpty(filterList)){
                model.setPeopleCount(0);
                model.setPercentage(0.0);
            }else{
                model.setPeopleCount(filterList.size());
                model.setPercentage(new BigDecimal(new BigDecimal(filterList.size()).divide(new BigDecimal(resultsDistributionModels.size()),4,BigDecimal.ROUND_HALF_UP).doubleValue() * 100).setScale(4,BigDecimal.ROUND_HALF_UP).doubleValue());
            }
            result.add(model);
        }

        return result;

    }


    /**
     *  教研员单元测区域学校成绩分布
     * @param districtId 区县ID
     * @param semester  学期
     * @param gradeCode 年级CODE
     * @return
     * @throws Exception
     */
    @Override
    public List<ResultsModel> unitResultsDistributionBySchool(String unitCode, Integer districtId, String semester,Integer gradeCode)throws Exception{

        List<ResultsModel> result = new ArrayList<>();

        //查询教师首次发布的releaseCode
        List<ReleaseViewData> list = loadReleaseExamCode(unitCode,districtId,semester);

        if(CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        //releaseCode 分组
        Map<Integer,List<ReleaseViewData>> groupByreleaseCode = list.stream().collect(Collectors.groupingBy(ReleaseViewData::getEcode));

        List<Integer> codes = new ArrayList<>();

        codes.addAll(groupByreleaseCode.keySet());
        //查询学生答卷总分
        List<ResultsDistributionModel> resultsDistributionModelList = unitResearchReportService.getstudentTotalScoreByGroup(codes,null,gradeCode,null,null);

        if(CollectionUtils.isEmpty(resultsDistributionModelList)) {
            return new ArrayList<>();
        }
        //学生答题信息以学校分组
        Map<Integer, List<ResultsDistributionModel>> mapSchool = resultsDistributionModelList.stream().collect(Collectors.groupingBy(ResultsDistributionModel::getSchoolCode));
        double[][] interval = {{0.0,0.6},{0.6,0.7},{0.7,0.8},{0.8,0.9},{0.9,1.0}};
        for(Integer schoolCode : mapSchool.keySet()) {
            //十个百分比区间
            List<ResultsDistributionModel> resultsDistributionModels = mapSchool.get(schoolCode);
            ResultsModel resultsModel = new ResultsModel();

            resultsModel.setSchoolCode(schoolCode);
            resultsModel.setSchoolName(resultsDistributionModels.get(0).getSchoolName());
            List<DistributedModel> distributedModels = new ArrayList<>();
            int count = 0;
            for (double[] d : interval) {
                count = count + 1;
                DistributedModel model = new DistributedModel();
                model.setSn(count);
                model.setSegment(d[0] * 100 + "% - " + d[1] * 100 + "%");
                //分组取各区间内的记录数
                List<ResultsDistributionModel> filterList = new ArrayList<>();
                if(count == interval.length) {
                    filterList = resultsDistributionModels.stream().filter(a -> new BigDecimal(a.getScore()).divide(new BigDecimal(a.getPaperScore()),2,BigDecimal.ROUND_HALF_UP).doubleValue() >= d[0] && new BigDecimal(a.getScore()).divide(new BigDecimal(a.getPaperScore()),2,BigDecimal.ROUND_HALF_UP).doubleValue() <= d[1]).collect(Collectors.toList());
                } else {
                    filterList = resultsDistributionModels.stream().filter(a -> new BigDecimal(a.getScore()).divide(new BigDecimal(a.getPaperScore()),2,BigDecimal.ROUND_HALF_UP).doubleValue() >= d[0] && new BigDecimal(a.getScore()).divide(new BigDecimal(a.getPaperScore()),2,BigDecimal.ROUND_HALF_UP).doubleValue() < d[1]).collect(Collectors.toList());
                }

                if (CollectionUtils.isEmpty(filterList)) {
                    model.setPeopleCount(0);
                    model.setPercentage(0.0);
                } else {
                    model.setPeopleCount(filterList.size());
                    model.setPercentage(new BigDecimal(new BigDecimal(filterList.size()).divide(new BigDecimal(resultsDistributionModels.size()), 4, BigDecimal.ROUND_HALF_UP).doubleValue() * 100).setScale(4,BigDecimal.ROUND_HALF_UP).doubleValue());
                }
                distributedModels.add(model);
            }
            resultsModel.setDistributedModels(distributedModels);
            result.add(resultsModel);
        }
        return result;
    }


    /**
     *  教研员单元测区域学校班级成绩分布
     * @param districtId 区县ID
     * @param semester  学期
     * @param gradeCode 年级CODE
     * @return
     * @throws Exception
     */
    @Override
    public List<ResultsModel> unitResultsDistributionByClass(String unitCode, Integer districtId, String semester,Integer gradeCode)throws Exception{

        List<ResultsModel> result = new ArrayList<>();

        //查询教师首次发布的releaseCode
        List<ReleaseViewData> list = loadReleaseExamCode(unitCode,districtId,semester);

        if(CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        //releaseCode 分组
        Map<Integer,List<ReleaseViewData>> groupByreleaseCode = list.stream().collect(Collectors.groupingBy(ReleaseViewData::getEcode));

        List<Integer> codes = new ArrayList<>();

        codes.addAll(groupByreleaseCode.keySet());
        //查询学生答卷总分
        List<ResultsDistributionModel> resultsDistributionModelList = unitResearchReportService.getstudentTotalScoreByGroup(codes,null,gradeCode,null,null);

        if(CollectionUtils.isEmpty(resultsDistributionModelList)) {
            return new ArrayList<>();
        }
        Map<Integer, List<ResultsDistributionModel>> mapSchool = resultsDistributionModelList.stream().collect(Collectors.groupingBy(ResultsDistributionModel::getSchoolCode));
        double[][] interval = {{0.0,0.6},{0.6,0.7},{0.7,0.8},{0.8,0.9},{0.9,1.0}};
        for(Integer schoolCode : mapSchool.keySet()) {
            //十个百分比区间
            List<ResultsDistributionModel> resultsDistributionSchoolModels = mapSchool.get(schoolCode);
            ResultsModel resultsModel = new ResultsModel();

            resultsModel.setSchoolCode(schoolCode);
            resultsModel.setSchoolName(resultsDistributionSchoolModels.get(0).getSchoolName());

            Map<Integer, List<ResultsDistributionModel>> mapClass = resultsDistributionSchoolModels.stream().collect(Collectors.groupingBy(ResultsDistributionModel::getClassCode));

            List<ResultsClassModel> resultsClassModels = new ArrayList<>();

            for(Integer classCode : mapClass.keySet()) {

                List<ResultsDistributionModel> resultsDistributionModels = mapClass.get(classCode);

                ResultsClassModel resultsClassModel = new ResultsClassModel();

                resultsClassModel.setClassCode(classCode);
                resultsClassModel.setClassName(resultsDistributionModels.get(0).getClassName());

                List<DistributedModel> distributedModels = new ArrayList<>();
                int count = 0;
                for (double[] d : interval) {
                    count = count + 1;
                    DistributedModel model = new DistributedModel();
                    model.setSn(count);
                    model.setSegment(d[0] * 100 + "% - " + d[1] * 100 + "%");
                    //分组取各区间内的记录数
                    List<ResultsDistributionModel> filterList = new ArrayList<>();
                    if(count == interval.length) {
                        filterList = resultsDistributionModels.stream().filter(a -> new BigDecimal(a.getScore()).divide(new BigDecimal(a.getPaperScore()),2,BigDecimal.ROUND_HALF_UP).doubleValue() >= d[0] && new BigDecimal(a.getScore()).divide(new BigDecimal(a.getPaperScore()),2,BigDecimal.ROUND_HALF_UP).doubleValue() <= d[1]).collect(Collectors.toList());
                    } else {
                        filterList = resultsDistributionModels.stream().filter(a -> new BigDecimal(a.getScore()).divide(new BigDecimal(a.getPaperScore()),2,BigDecimal.ROUND_HALF_UP).doubleValue() >= d[0] && new BigDecimal(a.getScore()).divide(new BigDecimal(a.getPaperScore()),2,BigDecimal.ROUND_HALF_UP).doubleValue() < d[1]).collect(Collectors.toList());
                    }

                    if (CollectionUtils.isEmpty(filterList)) {
                        model.setPeopleCount(0);
                        model.setPercentage(0.0);
                    } else {
                        model.setPeopleCount(filterList.size());
                        model.setPercentage(new BigDecimal(new BigDecimal(filterList.size()).divide(new BigDecimal(resultsDistributionModels.size()), 4, BigDecimal.ROUND_HALF_UP).doubleValue() * 100).setScale(4,BigDecimal.ROUND_HALF_UP).doubleValue());
                    }
                    distributedModels.add(model);
                }
                resultsClassModel.setDistributedModels(distributedModels);
                resultsClassModels.add(resultsClassModel);
            }
            resultsModel.setResultsClassModels(resultsClassModels);
            result.add(resultsModel);
        }
        return result;
    }







    /**
     * 获取区域下学期下单学科所有老师的第一次发布记录
     * @param unitCode
     * @param districtId
     * @param semester
     * @return
     * @throws Exception
     */
    public List<ReleaseViewData> loadReleaseExamCode(String unitCode, Integer districtId, String semester) throws Exception {

        List<ReleaseViewData> result = new ArrayList<>();
        //查询教师发布的所有单元测试releaseCode
        List<ReleaseViewData> list = unitResearchReportService.getReleaseViewData(unitCode, districtId, semester);

        if (CollectionUtils.isEmpty(list)) {
            return result;
        }

        Map<Integer, List<ReleaseViewData>> groupByTeacherMap = list.stream().collect(Collectors.groupingBy(ReleaseViewData::getTeacherCode));
        //轮询教师releaseCode  比较发布时间
        for (Map.Entry<Integer, List<ReleaseViewData>> integerListEntry : groupByTeacherMap.entrySet()) {
            List<ReleaseViewData> releaseViewData = integerListEntry.getValue();
//            ReleaseViewData max = Collections.max(releaseViewData, (o1, o2) -> {
//                return (int) (o1.getStartTime().getTime() - o2.getStartTime().getTime());
//            });
//            result.add(max);
            List<ReleaseViewData> sort =  releaseViewData.stream().sorted(Comparator.comparing(ReleaseViewData::getStartTime)).collect(Collectors.toList());
            result.add(sort.get(0));
        }

        return result;
    }



    /**
     * 全区各学校班级总体情进度
     * @param gradeCode 年级CODE
     * @param unitModels 多单元CODE
     * @param districtId 区县CODE
     * @param semester 学期
     * @return
     * @throws Exception
     */
    @Override
    public  List<SchoolProgressModel> teachingProgressByClass(Integer gradeCode,List<UnitModel> unitModels, Integer districtId, String semester) throws Exception{

        List<SchoolProgressModel> result = new ArrayList<>();

        List<String> codes = unitModels.stream().map(UnitModel::getUnitCode).collect(Collectors.toList());
        //查询教师发布的所有单元测试releaseCode
        List<Map<String,Object>> list = diagnosisRecordTeacherOpenService.getTeachingProgressByClasses(gradeCode,  codes,  districtId,  semester);
//        List<ReleaseViewData> list = unitResearchReportService.getReleaseViewDataByCodes(codes, districtId, semester);

        if(CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }

        String jsonData = JSONArray.toJSONString(list);
//        List<TeachingProgressPo> teachingProgressPos = PageHelperUtil.converterList(list,TeachingProgressPo.class);
        List<TeachingProgressPo> teachingProgressPos = JSONArray.parseArray(jsonData,TeachingProgressPo.class);
        //教师单元发布记录以学校分组
        Map<Integer,List<TeachingProgressPo>> unitListBySchool = teachingProgressPos.stream().collect(Collectors.groupingBy(TeachingProgressPo::getSchoolCode));

        for(Integer schoolCode : unitListBySchool.keySet()) {
            //单个学校下教师所有单元发布记录
            List<TeachingProgressPo> dataList = unitListBySchool.get(schoolCode);

            SchoolProgressModel schoolModel = new SchoolProgressModel();

            schoolModel.setSchoolCode(schoolCode);

            schoolModel.setSchoolName(dataList.get(0).getSchoolName());

            Map<Integer, List<TeachingProgressPo>> groupbyClass = dataList.stream().collect(Collectors.groupingBy(TeachingProgressPo::getClassCode));
            //单个学校下教师所有单元发布记录以班级分组
            List<ClassTeachingProgressModel> classTeachingProgressModels = new ArrayList<>();

            for(Integer classCode : groupbyClass.keySet()){
                //单个学校下单个班级教师所有单元发布记录
                List<TeachingProgressPo> classdataList = groupbyClass.get(classCode);

                ClassTeachingProgressModel classTeachingProgressModel = new ClassTeachingProgressModel();

                classTeachingProgressModel.setGradeCode(gradeCode);

                classTeachingProgressModel.setClassCode(classCode);

                classTeachingProgressModel.setClasslName(classdataList.get(0).getClassName());

                classTeachingProgressModel.setTeacherCode(classdataList.get(0).getTeacherCode());

                classTeachingProgressModel.setTeacherName(classdataList.get(0).getTeacherName());
                //单元CODE分组
                Map<String,List<TeachingProgressPo>> groupbyUnitCode = classdataList.stream().collect(Collectors.groupingBy(TeachingProgressPo::getUnitCode));

                List<TeachingProgressModel> teachingProgressModels = new ArrayList<>();

                for (UnitModel unitModel : unitModels) {

                    List<TeachingProgressPo> unitList = groupbyUnitCode.get(unitModel.getUnitCode());

                    TeachingProgressModel teachingProgressModel = new TeachingProgressModel();
                    teachingProgressModel.setUnitCode(unitModel.getUnitCode());
                    teachingProgressModel.setUnitName(unitModel.getUnitName());
                    if (!CollectionUtils.isEmpty(unitList)) {
                    //单个班级在单个单元下的发布时间排序，取最早的时间
                    List<TeachingProgressPo> artsort = unitList.stream().sorted(Comparator.comparing(TeachingProgressPo::getStartTime)).collect(Collectors.toList());

                        teachingProgressModel.setStartTime(artsort.get(0).getStartTime());
                    }
                    teachingProgressModels.add(teachingProgressModel);
                }
                classTeachingProgressModel.setTeachingProgressModels(teachingProgressModels);
                classTeachingProgressModels.add(classTeachingProgressModel);
            }
            schoolModel.setClassTeachingProgressModels(classTeachingProgressModels);
            result.add(schoolModel);
        }

        return result;
    }


    //
//    public static List<List<StudentSubjectScorePo>>  createList(List<StudentSubjectScorePo> targe,int size) {
//        List<List<StudentSubjectScorePo>> listArr = new ArrayList<List<StudentSubjectScorePo>>();
//        //获取被拆分的数组个数
//        int arrSize = targe.size()%size==0?targe.size()/size:targe.size()/size+1;
//        for(int i=0;i<arrSize;i++) {
//            List<StudentSubjectScorePo>  sub = new ArrayList<StudentSubjectScorePo>();
//            //把指定索引数据放入到list中
//            for(int j=i*size;j<=size*(i+1)-1;j++) {
//                if(j<=targe.size()-1) {
//                    sub.add(targe.get(j));
//                }
//            }
//            listArr.add(sub);
//        }
//        return listArr;
//    }



}
