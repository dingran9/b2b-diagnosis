package com.eeduspace.report.service.impl.open;

import com.eedu.diagnosis.common.enumration.ArtTypeEnum;
import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.exam.api.dto.DiagnosisClassRelationDto;
import com.eedu.diagnosis.exam.api.dto.DiagnosisRecordStudentDto;
import com.eedu.diagnosis.exam.api.dto.DiagnosisStudentAnswerRecordDto;
import com.eedu.diagnosis.exam.api.openService.DiagnosisClassRelationOpenService;
import com.eedu.diagnosis.exam.api.openService.DiagnosisRecordStudentOpenService;
import com.eedu.diagnosis.exam.api.openService.DiagnosisRecordTeacherOpenService;
import com.eedu.diagnosis.exam.api.openService.DiagnosisStudentAnswerRecordOpenService;
import com.eeduspace.b2b.report.constant.ReportConstant;
import com.eeduspace.b2b.report.model.*;
import com.eeduspace.b2b.report.model.report.*;
import com.eeduspace.b2b.report.model.report.principal.StageTeachingProgressModel;
import com.eeduspace.b2b.report.service.StageTestReportOpenService;
import com.eeduspace.report.model.ResultsDistributionModel;
import com.eeduspace.report.model.SubjectAbilityModel;
import com.eeduspace.report.po.ExamPo;
import com.eeduspace.report.po.ReleaseExamPo;
import com.eeduspace.report.po.StudentSubjectScorePo;
import com.eeduspace.report.po.UserAnswerResultPo;
import com.eeduspace.report.service.*;
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
 * Created by liuhongfei on 2017/10/10.
 */
@Slf4j
@Service("stageTestReportOpenServiceImpl")
@com.alibaba.dubbo.config.annotation.Service
public class StageTestReportOpenServiceImpl implements StageTestReportOpenService{


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
    private DiagnosisRecordTeacherOpenService diagnosisRecordTeacherOpenService;
    @Autowired
    private ReleaseExamService releaseExamService;
    @Autowired
    private StudentSubjectScoreService studentSubjectScoreService;
    @Autowired
    private DiagnosisClassRelationOpenService diagnosisClassRelationOpenService;
    //最小记录数
    private static int minsize = 2;
    /**
     * 获取阶段考试能力信息
     *
     * @param contrastType 对比类型
     * @param releaseCode  发布记录code
     * @param semester     学期
     * @param areaCode     区域code
     * @param subjectCode 学科code
     * @return
     * @throws Exception
     */
    @Override
    public List<BaseAbilityDto> getStageAbility(String contrastType, String releaseCode, String semester, Integer areaCode,String subjectCode) throws Exception {
        List<SubjectAbilityModel> subjectAbilityModels = subjectAbilityService.getByReleaseCodeAndSubjectCode(releaseCode,subjectCode);
        List<BaseAbilityDto> dtos = new ArrayList<>();
        if(CollectionUtils.isEmpty(subjectAbilityModels)){
            return dtos;
        }
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
            default:
                return dtos;
        }
    }

    /**
     * 获取阶段考试知识点模块掌握情况
     *
     * @param contrastType 对比类型
     * @param releaseCode  发布记录code
     * @param semester     学期
     * @param areaCode     区域code
     * @param subjectCode 学科code
     * @return
     * @throws Exception
     */
    @Override
    public List<BaseKnowledgeModuleDto> getStageKnowledgeModule(String contrastType, String releaseCode, String semester, Integer areaCode,String subjectCode) throws Exception {

        List<KnowledgeModuleModel> knowledgeModuleModels = knowledgeModuleService.findByReleaseCodeAndSubjectCode(releaseCode,subjectCode);
        List<BaseKnowledgeModuleDto> baseKnowledgeModuleDtos = new ArrayList<>();
        if(CollectionUtils.isEmpty(knowledgeModuleModels)){
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
                return baseKnowledgeModuleDtos;
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
                return baseKnowledgeModuleDtos;
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
                return baseKnowledgeModuleDtos;
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
                return baseKnowledgeModuleDtos;

            default:
                return baseKnowledgeModuleDtos;
        }
    }

    /**
     * 获取标准分平均分变动信息
     *
     * @param contrastType 对比类型
     * @param releaseCode  发布记录code
     * @param areaCode     区域code
     * @param semester     学期code
     * @param subjectCode  学科
     * @param gradeCode    学年
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> getStandardScoreChange(String contrastType, String releaseCode, Integer areaCode, String semester, Integer subjectCode, Integer gradeCode) throws Exception {
        Map<String,Object> result = new HashMap<>();

        //获取当前次数之前的所有考试 用于阶段考历史
        List<StageTeachingProgressModel> progressModels = this.stageTeachingProgressByArea(releaseCode, areaCode, semester, gradeCode, subjectCode);
        if (CollectionUtils.isEmpty(progressModels)){
            return result;
        }
        List<StudentGetScoreDo> scoreDos = studentSubjectScoreService.findByReleaseCodesIn(progressModels.stream().
                            map(StageTeachingProgressModel::getReleaseCode).collect(Collectors.toList()),subjectCode+"");
        //根据次数分组
        Map<String, List<StudentGetScoreDo>> publicationTimes = scoreDos.stream().collect(Collectors.groupingBy(StudentGetScoreDo::getReleaseFKCode));
        List<StageStandardScoreDto> stageStandardScoreDtos = new ArrayList<>();
        switch (contrastType){
            case ReportConstant.SCHOOL_CONTRAST :
                publicationTimes.forEach((k, v) -> {
                    Map<String, Double> areaAveAndStandDeviation = getAreaAveAndStandDeviation(v.stream().
                                        map(StudentGetScoreDo::getScore).collect(Collectors.toList()));
                    //根据学校分组
                    Map<String, List<StudentGetScoreDo>> schoolGroup = v.stream().collect(Collectors.
                                        groupingBy(StudentGetScoreDo::getSchoolCode));

                    schoolGroup.forEach((schoolCode, schoolLists) -> {
                        StageStandardScoreDto schoolStandardScore = new StageStandardScoreDto();
                        schoolStandardScore.setSchoolCode(schoolCode);
                        schoolStandardScore.setReleaseCode(k);
                        schoolStandardScore.setSchoolName(schoolLists.get(0).getSchoolName());
                        Double schoolAveStandard = getAveStandardScore(areaAveAndStandDeviation, schoolLists);
                        schoolStandardScore.setStandardScore(ReportMathUtils.rounding(schoolAveStandard,4));
                        stageStandardScoreDtos.add(schoolStandardScore);
                    });
                });
                break;
            case ReportConstant.CLASS_CONTRAST :
                publicationTimes.forEach((k,v)->{
                    Map<String, Double> areaAveAndStandDeviation = getAreaAveAndStandDeviation(v.stream().
                                        map(StudentGetScoreDo::getScore).collect(Collectors.toList()));
                    Map<String, List<StudentGetScoreDo>> classGroup = v.stream().
                                        collect(Collectors.groupingBy(StudentGetScoreDo::getClassCode));
                    classGroup.forEach((classCode,classLists)->{
                        StageStandardScoreDto classStandardScore = new StageStandardScoreDto();
                        classStandardScore.setSchoolCode(classLists.get(0).getSchoolCode());
                        classStandardScore.setSchoolName(classLists.get(0).getSchoolName());
                        classStandardScore.setClassCode(classCode);
                        classStandardScore.setReleaseCode(k);
                        classStandardScore.setClassName(classLists.get(0).getClassName());
                        Double classAveStandard = getAveStandardScore(areaAveAndStandDeviation, classLists);
                        classStandardScore.setStandardScore(ReportMathUtils.rounding(classAveStandard,4));
                        stageStandardScoreDtos.add(classStandardScore);
                    });
                });
                break;
            case ReportConstant.TEACHER_CONTRAST :
                publicationTimes.forEach((k,v)->{
                    Map<String, Double> areaAveAndStandDeviation = getAreaAveAndStandDeviation(v.stream().
                                        map(StudentGetScoreDo::getScore).collect(Collectors.toList()));
                    Map<String, List<StudentGetScoreDo>> teacherGroup = v.stream().
                                        collect(Collectors.groupingBy(StudentGetScoreDo::getTeacherCode));
                    teacherGroup.forEach((teacherCode,teacherLists)->{
                        StageStandardScoreDto teacherStandardScore = new StageStandardScoreDto();
                        teacherStandardScore.setSchoolCode(teacherLists.get(0).getSchoolCode());
                        teacherStandardScore.setSchoolName(teacherLists.get(0).getSchoolName());
                        teacherStandardScore.setClassCode(teacherLists.get(0).getClassCode());
                        teacherStandardScore.setClassName(teacherLists.get(0).getClassName());
                        teacherStandardScore.setTeacherCode(teacherCode);
                        teacherStandardScore.setReleaseCode(k);
                        teacherStandardScore.setTeacherName(teacherLists.get(0).getTeacherName());
                        Double teacherAveStandard = getAveStandardScore(areaAveAndStandDeviation, teacherLists);
                        teacherStandardScore.setStandardScore(ReportMathUtils.rounding(teacherAveStandard,4));
                        stageStandardScoreDtos.add(teacherStandardScore);
                    });
                });
                break;
            default:
                break;
        }
        result.put("stageStandardScoreDtos",stageStandardScoreDtos);
        result.put("progressModels",progressModels);
        return result;
    }

    private Double getAveStandardScore(Map<String, Double> areaAveAndStandDeviation, List<StudentGetScoreDo> school) {
        return ReportMathUtils.getAveStandard(areaAveAndStandDeviation.get("areaAve"),
                                                areaAveAndStandDeviation.get("areaStandardDeviation"), school.stream().
                                                                    map(StudentGetScoreDo::getScore).collect(Collectors.toList()));
    }

    /**
     * 获取区域平均分与标准差
     * @param areaDoubles 区域内所有人员分数
     * @return
     */
    private Map<String,Double> getAreaAveAndStandDeviation(List<Double> areaDoubles) {
        Map<String,Double>  result = new HashMap<>();
        Double areaAve = areaDoubles.stream().collect(Collectors.averagingDouble(d->d));
        Double areaStandardDeviation = ReportMathUtils.getStandardDeviation(areaDoubles, areaAve);
        result.put("areaAve",areaAve);
        result.put("areaStandardDeviation",areaStandardDeviation);
        return result;
    }

    /**
     * 获取阶段考试教学成绩
     *
     * @param contrastType 对比类型
     * @param releaseCode  发布记录code
     * @param areaCode     区域code
     * @param semester     学期
     * @param subjectCode 学科code
     * @return
     * @throws Exception
     */
    @Override
    public List<AchievementDto> getStageTeachineAchievement(String contrastType, String releaseCode, Integer areaCode, String semester,String subjectCode) throws Exception {
        ReleaseExamPo releaseExamPo = releaseExamService.findByReleaseExamCode(releaseCode);
        if(null == releaseExamPo) {
            return new ArrayList<>();
        }

        List<StudentGetScoreDo> studentGetScoreDos = studentSubjectScoreService.findByReleaseExamCodeAndSubjectCode4Stage(releaseExamPo.getCode()+"",subjectCode);
        if(CollectionUtils.isEmpty(studentGetScoreDos)){
            return new ArrayList<>();
        }
        //区域平均分
        Double areaAveScore = studentGetScoreDos.stream().collect(Collectors.averagingDouble(StudentGetScoreDo::getScore));
        List<Double> areaStudents = studentGetScoreDos.stream().map(StudentGetScoreDo::getScore).collect(Collectors.toList());
        //区域标准差
        Double areaStandarDeviation = ReportMathUtils.getStandardDeviation(areaStudents,areaAveScore);
        List<AchievementDto> achievementDtos = new ArrayList<>();
        switch (contrastType){
            case ReportConstant.GRADE_CONTRAST :
                DoubleSummaryStatistics areaInfo = studentGetScoreDos.stream().collect(Collectors.summarizingDouble(StudentGetScoreDo::getScore));
                AchievementDto dto = new AchievementDto();
                dto.setAveScore(ReportMathUtils.rounding(areaInfo.getAverage(),2));
                dto.setMaxScore(areaInfo.getMax());
                dto.setMinScore(areaInfo.getMin());
                //标准差
                dto.setStand(ReportMathUtils.rounding(areaStandarDeviation,4));
                //标准分判平均分
                Double aveStandardScore = ReportMathUtils.getAveStandard(areaAveScore, areaStandarDeviation, areaStudents);
                dto.setStandScore(ReportMathUtils.rounding(aveStandardScore,4));
                achievementDtos.add(dto);
                return achievementDtos;
            case ReportConstant.SCHOOL_CONTRAST :
                Map<String, List<StudentGetScoreDo>> schools = studentGetScoreDos.stream().collect(Collectors.groupingBy(StudentGetScoreDo::getSchoolCode));
                schools.forEach((k,v)->{
                    DoubleSummaryStatistics schoolInfo = v.stream().collect(Collectors.summarizingDouble(StudentGetScoreDo::getScore));
                    //标准差
                    Double schoolStandardDeviation = ReportMathUtils.getStandardDeviation(v.stream().map(StudentGetScoreDo::getScore).collect(Collectors.toList()), areaAveScore);
                    //标准分平均分
                    Double schoolStandarAveScore = ReportMathUtils.getAveStandard(areaAveScore, areaStandarDeviation, v.stream().map(StudentGetScoreDo::getScore).collect(Collectors.toList()));
                    AchievementDto schoolAchievement = new AchievementDto();
                    schoolAchievement.setStand(ReportMathUtils.rounding(schoolStandardDeviation,4));
                    schoolAchievement.setStandScore(ReportMathUtils.rounding(schoolStandarAveScore,4));
                    schoolAchievement.setAveScore(ReportMathUtils.rounding(schoolInfo.getAverage(),2));
                    schoolAchievement.setMaxScore(schoolInfo.getMax());
                    schoolAchievement.setMinScore(schoolInfo.getMin());
                    schoolAchievement.setSchoolName(v.get(0).getSchoolName());
                    schoolAchievement.setSchoolCode(k);
                    achievementDtos.add(schoolAchievement);
                });
                return achievementDtos;
            case ReportConstant.CLASS_CONTRAST :
                Map<String, List<StudentGetScoreDo>> classCodes = studentGetScoreDos.stream().collect(Collectors.groupingBy(StudentGetScoreDo::getClassCode));
                classCodes.forEach((k,v)->{
                    DoubleSummaryStatistics classInfo = v.stream().collect(Collectors.summarizingDouble(StudentGetScoreDo::getScore));
                    Double classStandardDeviation=ReportMathUtils.getStandardDeviation(v.stream().map(StudentGetScoreDo::getScore).collect(Collectors.toList()), areaAveScore);
                    Double classStandarAveScore = ReportMathUtils.getAveStandard(areaAveScore, areaStandarDeviation, v.stream().map(StudentGetScoreDo::getScore).collect(Collectors.toList()));
                    AchievementDto classAchievement = new AchievementDto();
                    classAchievement.setStand(ReportMathUtils.rounding(classStandardDeviation,4));
                    classAchievement.setStandScore(ReportMathUtils.rounding(classStandarAveScore,4));
                    classAchievement.setAveScore(ReportMathUtils.rounding(classInfo.getAverage(),2));
                    classAchievement.setMaxScore(classInfo.getMax());
                    classAchievement.setMinScore(classInfo.getMin());
                    classAchievement.setSchoolName(v.get(0).getSchoolName());
                    classAchievement.setSchoolCode(v.get(0).getSchoolCode());
                    classAchievement.setClassCode(k);
                    classAchievement.setClassName(v.get(0).getClassName());
                    achievementDtos.add(classAchievement);
                });
                return achievementDtos;
            case ReportConstant.TEACHER_CONTRAST :
                Map<String, List<StudentGetScoreDo>> teacherCodes = studentGetScoreDos.stream().collect(Collectors.groupingBy(StudentGetScoreDo::getTeacherCode));
                teacherCodes.forEach((k,v)->{
                    DoubleSummaryStatistics teacherInfo = v.stream().collect(Collectors.summarizingDouble(StudentGetScoreDo::getScore));
                    Double teacherStandardDeviation=ReportMathUtils.getStandardDeviation(v.stream().map(StudentGetScoreDo::getScore).collect(Collectors.toList()), areaAveScore);
                    Double teacherStandarAveScore = ReportMathUtils.getAveStandard(areaAveScore, areaStandarDeviation, v.stream().map(StudentGetScoreDo::getScore).collect(Collectors.toList()));
                    AchievementDto teacherAchievement = new AchievementDto();
                    teacherAchievement.setStand(ReportMathUtils.rounding(teacherStandardDeviation,4));
                    teacherAchievement.setStandScore(ReportMathUtils.rounding(teacherStandarAveScore,4));
                    teacherAchievement.setAveScore(ReportMathUtils.rounding(teacherInfo.getAverage(),2));
                    teacherAchievement.setMaxScore(teacherInfo.getMax());
                    teacherAchievement.setMinScore(teacherInfo.getMin());
                    teacherAchievement.setSchoolName(v.get(0).getSchoolName());
                    teacherAchievement.setSchoolCode(v.get(0).getSchoolCode());
                    teacherAchievement.setClassCode(v.get(0).getClassCode());
                    teacherAchievement.setClassName(v.get(0).getClassName());
                    teacherAchievement.setTeacherCode(k);
                    teacherAchievement.setTeacherName(v.get(0).getTeacherName());
                    achievementDtos.add(teacherAchievement);
                });
                return achievementDtos;
            default:
                return achievementDtos;
        }
    }

    /**
     * 教研员阶段考题质量分析
     * @param releaseCode  全科考发布记录CODE
     * @param districtId  区县ID
     * @param semester  学期
     * @param artType 文理类型
     * @throws Exception
     */
    @Override
    public List<QuestionsqualityModel> stageQualityofQuestionsReport(String releaseCode, Integer districtId, String semester,Integer subjectCode, Integer artType) throws Exception {

            ReleaseExamPo releaseExamPo = releaseExamService.findByReleaseExamCode(releaseCode);

            if(null == releaseExamPo) {
                return new ArrayList<>();
            }

            List<Integer> codes = new ArrayList<>();

            codes.add(releaseExamPo.getCode());
            //releaseCode 查询学生答题记录
            List<ExamPo> allexamPoList =  unitResearchReportService.getexam(codes);

            if(CollectionUtils.isEmpty(allexamPoList)) {
                return new ArrayList<>();
            }
            //学生答题记录按学科分组
            Map<String,List<ExamPo>> examMap = allexamPoList.stream().collect(Collectors.groupingBy(ExamPo::getSubjectCode));
            //获取全科考对应的学科的学生答题记录
            List<ExamPo> examPoList = examMap.get(String.valueOf(subjectCode));

            if(CollectionUtils.isEmpty(examPoList)) {
                return new ArrayList<>();
            }

            List<ExamPo> examPos = new ArrayList<>();
            //无文理类型  取全部答题记录
            if(artType .equals(ArtTypeEnum.NOTYPE.getValue())) {
                examPos = examPoList;
            } else {
                //全科考下对应学科学生答题记录按文理分组   数学会分为文理科
                Map<Integer, List<ExamPo>> artexamMap = allexamPoList.stream().collect(Collectors.groupingBy(ExamPo::getArtType));
                //获取该文理类型对应的学生答题记录
                examPos = artexamMap.get(artType);
            }

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
            //试题按题号排序
//            List<DiagnosisStudentAnswerRecordDto> question = questions.stream().sorted(Comparator.comparing(DiagnosisStudentAnswerRecordDto::getQuestionSn)).collect(Collectors.toList());
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
     * 教研员阶段考全区测情况
     * @param releaseCode 阶段考发布CODE
     * @param subjectCode  区县ID
     * @throws Exception
     */
    @Override
    public AreaHappenModel stageAreaHappening(String releaseCode, Integer subjectCode) throws Exception {

        AreaHappenModel model = new AreaHappenModel();

        List<String> codes = new ArrayList<>();

        codes.add(releaseCode);
        //全科考查询学生答题记录
        List<DiagnosisRecordStudentDto> diagnosisRecordStudentDtoList = diagnosisRecordStudentOpenService.getListByRealseCodes(codes);

        if (CollectionUtils.isEmpty(diagnosisRecordStudentDtoList)) {
            return null;
        }
        //学生答题记录按学科分组
        Map<Integer,List<DiagnosisRecordStudentDto>> diagnosisMap = diagnosisRecordStudentDtoList.stream().collect(Collectors.groupingBy(DiagnosisRecordStudentDto::getSubjectCode));

        List<DiagnosisRecordStudentDto> diagnosisRecordStudentDtos = diagnosisMap.get(subjectCode);

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
     * 教研员阶段考全区各学校测试情况
     * @param releaseCode 单元CODE
     * @param subjectCode  区县ID
     * @throws Exception
     */
    @Override
    public List<SchoolHappenModel> stageAreaHappeningBySchool(String releaseCode, Integer subjectCode) throws Exception {

        List<SchoolHappenModel> result = new ArrayList<>();
        List<String> codes = new ArrayList<>();

        codes.add(releaseCode);
        //全科考查询学生答题记录
        List<DiagnosisRecordStudentDto> diagnosisRecordStudentDtoList = diagnosisRecordStudentOpenService.getListByRealseCodes(codes);

        if (CollectionUtils.isEmpty(diagnosisRecordStudentDtoList)) {
            return null;
        }
        //学生答题记录按学科分组
        Map<Integer,List<DiagnosisRecordStudentDto>> diagnosisMap = diagnosisRecordStudentDtoList.stream().collect(Collectors.groupingBy(DiagnosisRecordStudentDto::getSubjectCode));

        List<DiagnosisRecordStudentDto> diagnosisRecordStudentDtos = diagnosisMap.get(subjectCode);

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
     * 教研员阶段考全区各学校班级测试情况
     * @param releaseCode 单元CODE
     * @param subjectCode  区县ID
     * @throws Exception
     */
    @Override
    public List<ClassHappenModel> stageAreaHappeningByclass(String releaseCode, Integer subjectCode) throws Exception {

        List<ClassHappenModel> result = new ArrayList<>();
        List<String> codes = new ArrayList<>();

        codes.add(releaseCode);
        //全科考查询学生答题记录
        List<DiagnosisRecordStudentDto> diagnosisRecordStudentDtoList = diagnosisRecordStudentOpenService.getListByRealseCodes(codes);

        if (CollectionUtils.isEmpty(diagnosisRecordStudentDtoList)) {
            return null;
        }
        //学生答题记录按学科分组
        Map<Integer,List<DiagnosisRecordStudentDto>> diagnosisMap = diagnosisRecordStudentDtoList.stream().collect(Collectors.groupingBy(DiagnosisRecordStudentDto::getSubjectCode));

        List<DiagnosisRecordStudentDto> diagnosisRecordStudentDtos = diagnosisMap.get(subjectCode);

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
//                    if(!CollectionUtils.isEmpty(classGroupMap.get(classCode))) {
//                        happenModel.setClassName(classGroupMap.get(classCode).get(0).getGroupName());
//                    }
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
     * 加载全区学生成绩分布数据
     * @param releaseCode
     * @param districtId
     * @param semester
     * @param gradeCode
     * @param subjectCode
     * @return
     * @throws Exception
     */
    public List<ResultsDistributionModel> loadResultsDistributionData(String releaseCode, Integer districtId, String semester,Integer gradeCode, Integer subjectCode)throws Exception{

        ReleaseExamPo releaseExamPo = releaseExamService.findByReleaseExamCode(releaseCode);

        if(null == releaseExamPo) {
            return new ArrayList<>();
        }
        //releaseCode 分组
        List<Integer> codes = new ArrayList<>();

        codes.add(releaseExamPo.getCode());
        //查询学生答卷总分
        List<ResultsDistributionModel> resultsDistributionModels = unitResearchReportService.getstudentTotalScoreByGroup(codes,null,gradeCode,null,subjectCode);

        return resultsDistributionModels;

    }



    /**
     *  教研员阶段考区域学生成绩分布
     * @param releaseCode  阶段考发布CODE
     * @param districtId 区县ID
     * @param semester  学期
     * @param gradeCode 年级CODE
     * @param subjectCode 班级CODE
     * @return
     * @throws Exception
     */
    @Override
    public List<DistributedModel> stageResultsDistribution(String releaseCode, Integer districtId, String semester,Integer gradeCode, Integer subjectCode)throws Exception{

        List<DistributedModel> result = new ArrayList<>();

        List<ResultsDistributionModel> resultsDistributionModels = loadResultsDistributionData( releaseCode,  districtId,  semester, gradeCode,  subjectCode);

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
     *  教研员阶段考区域学校成绩分布
     * @param releaseCode  阶段考发布CODE
     * @param districtId 区县ID
     * @param semester  学期
     * @param gradeCode 年级CODE
     * @return
     * @throws Exception
     */
    @Override
    public List<ResultsModel> stageResultsDistributionBySchool(String releaseCode, Integer districtId, String semester,Integer gradeCode,Integer subjectCode)throws Exception{

        List<ResultsModel> result = new ArrayList<>();

        List<ResultsDistributionModel> resultsDistributionModelList = loadResultsDistributionData( releaseCode,  districtId,  semester, gradeCode,  subjectCode);

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
     *  教研员阶段考区域学校班级成绩分布
     * @param releaseCode  阶段考发布CODE
     * @param districtId 区县ID
     * @param semester  学期
     * @param gradeCode 年级CODE
     * @return
     * @throws Exception
     */
    @Override
    public List<ResultsModel> stageResultsDistributionByClass(String releaseCode, Integer districtId, String semester,Integer gradeCode,Integer subjectCode)throws Exception{

        List<ResultsModel> result = new ArrayList<>();

        List<ResultsDistributionModel> resultsDistributionModelList = loadResultsDistributionData( releaseCode,  districtId,  semester, gradeCode,  subjectCode);

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
     * 教研员阶段考全区各年级总体情进度
     * @param gradeCode 年级CODE
     * @param districtId 区县CODE
     * @param semester 学期
     * @return
     * @throws Exception
     */
    @Override
    public List<StageTeachingProgressModel> stageTeachingProgressByArea(String releaseCode,Integer districtId, String semester, Integer gradeCode,Integer subjectCode) throws Exception{

        List<StageTeachingProgressModel> result = new ArrayList<>();

        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("groupAreaDistrictId",districtId);
        queryMap.put("subjectCode",subjectCode);
        queryMap.put("examYear",semester);
        queryMap.put("gradeCode",gradeCode);
        List<DiagnosisClassRelationDto> diagnosisClassRelationDtos = diagnosisClassRelationOpenService.areaExamHistoryList(queryMap);

        if(CollectionUtils.isEmpty(diagnosisClassRelationDtos)) {
            return new ArrayList<>();
        }
                //正序排  取最大元素
        List<DiagnosisClassRelationDto> ascsort = diagnosisClassRelationDtos.stream().sorted(Comparator.comparing(DiagnosisClassRelationDto::getStartTime)).collect(Collectors.toList());

        int count = 1;
        for(DiagnosisClassRelationDto diagnosisClassRelationDto : ascsort){
            StageTeachingProgressModel model = new StageTeachingProgressModel();
            model.setReleaseCode(diagnosisClassRelationDto.getDiagnosisRecordCode());
            model.setSn(count++);
            model.setStartTime(diagnosisClassRelationDto.getStartTime());
            result.add(model);
            if(diagnosisClassRelationDto.getDiagnosisRecordCode().equals(releaseCode)) {
                break;
            }
        }
        return result;
    }


}
