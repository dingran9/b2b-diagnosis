package com.eeduspace.report.service.impl.open;

import com.eedu.auth.beans.AuthUserBean;
import com.eedu.auth.service.AuthUserManagerService;
import com.eeduspace.b2b.report.constant.BaseSubjectConstant;
import com.eeduspace.b2b.report.exception.ReportException;
import com.eeduspace.b2b.report.model.report.KnowledgeModuleModel;
import com.eeduspace.b2b.report.model.report.StudentMakeResultModel;
import com.eeduspace.b2b.report.model.report.principal.*;
import com.eeduspace.b2b.report.service.PrincipalSingleReportOpenService;
import com.eeduspace.report.model.SubjectAbilityModel;
import com.eeduspace.report.model.ThreeColorModel;
import com.eeduspace.report.po.ReleaseExamPo;
import com.eeduspace.report.service.*;
import com.eeduspace.report.util.NaturalOrderComparator;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 校长单科报告实现类
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-04-25 11:49
 **/
@Slf4j
@Service("principalSingleReportOpenServiceImpl")
@com.alibaba.dubbo.config.annotation.Service
public class PrincipalSingleReportOpenServiceImpl implements PrincipalSingleReportOpenService{
    @Autowired
    private StudentSubjectScoreService studentSubjectScoreService;
    @Autowired
    private ReleaseExamService releaseExamService;
    @Autowired
    private ThreeColorService threeColorService;
    @Autowired
    private SubjectAbilityService subjectAbilityService;
    @Autowired
    private KnowledgeModuleService knowledgeModuleService;
    @Autowired
    private AuthUserManagerService authUserManagerService;
    /**
     * 学科年级成绩分布统计
     *
     * @param releaseExamCode 发布考试记录code
     * @param subjectCode     学科code
     */
    @Override
    public GradeResultsDto getDistributionOfSubjectScores(String releaseExamCode, String subjectCode) throws Exception{
        List<StudentMakeResultModel> studentMakeResultModels = studentSubjectScoreService.findByReleaseExamCodeAndSubjectCode(releaseExamCode, subjectCode);
        if(studentMakeResultModels.isEmpty()){
            return null;
        }
        GradeResultsDto resultsDto = getGradeResultsDto(studentMakeResultModels);
        return resultsDto;
    }

    private GradeResultsDto getGradeResultsDto(List<StudentMakeResultModel> studentMakeResultModels) {
        GradeResultsDto resultsDto=new GradeResultsDto();
        if(studentMakeResultModels.isEmpty()){
            return null;
        }
        Double paperScore = studentMakeResultModels.get(0).getPaperScore();
        Double initScore=paperScore;
        String className=studentMakeResultModels.get(0).getClassName();
        DoubleSummaryStatistics doubleSummaryStatistics = studentMakeResultModels.stream().collect(Collectors.summarizingDouble(StudentMakeResultModel::getScore));
        Double standardDeviation = getStandardDeviation(studentMakeResultModels, doubleSummaryStatistics.getAverage());//标准差
        //及格数量
        long qualifiedCount = studentMakeResultModels.stream().filter(studentMakeResultModel -> studentMakeResultModel.getScore() > studentMakeResultModel.getPaperScore() * .6).count();
        //优秀数量
        long excellentCount = studentMakeResultModels.stream().filter(studentMakeResultModel -> studentMakeResultModel.getScore() > studentMakeResultModel.getPaperScore() * .8).count();
        resultsDto.setQualified(rounding(getMathResult(studentMakeResultModels.size(),qualifiedCount),3));
        resultsDto.setExcellent(rounding(getMathResult(studentMakeResultModels.size(),excellentCount),3));
        resultsDto.setAvgScore(rounding(doubleSummaryStatistics.getAverage(),2));
        resultsDto.setMaxScore(doubleSummaryStatistics.getMax());
        resultsDto.setMinScore(doubleSummaryStatistics.getMin());
        resultsDto.setPeoples(doubleSummaryStatistics.getCount());
        resultsDto.setClassName(className);
        resultsDto.setStandardDeviation(standardDeviation);
        List<PeopleScoreDto> peopleScoreDtos=new ArrayList<>();
        countPeople(studentMakeResultModels.size(),studentMakeResultModels,initScore,paperScore,peopleScoreDtos);
        resultsDto.setPeopleScoreDtos(peopleScoreDtos);
        return resultsDto;
    }


    /**
     * 过滤分数段人数
     */
    public static List<PeopleScoreDto> countPeople(Integer totalPeoples,List<StudentMakeResultModel> studentMakeResultModels, Double score , Double paperScore, List<PeopleScoreDto> resultMap){
        long count=0L;
        Double differenceScore=paperScore*.1;
        if(score<=paperScore*.6){
            count = studentMakeResultModels.stream().filter(studentMakeResultModel -> studentMakeResultModel.getScore()<paperScore*.6).count();
            PeopleScoreDto peopleScoreDto=new PeopleScoreDto();
            peopleScoreDto.setScoreInterval((int)(paperScore*.6)+"以下");
            peopleScoreDto.setStudentCount(count);
            peopleScoreDto.setProportion(rounding(getMathResult(totalPeoples,count),3)+"");
            resultMap.add(peopleScoreDto);
            return resultMap;
        }else {
            if(score.equals(paperScore)){
                count = studentMakeResultModels.stream().filter(studentMakeResultModel -> score >=studentMakeResultModel.getScore() && studentMakeResultModel.getScore() >= score-differenceScore).count();
            }else{
                count = studentMakeResultModels.stream().filter(studentMakeResultModel -> score >studentMakeResultModel.getScore() && studentMakeResultModel.getScore() >= score-differenceScore).count();
            }
            PeopleScoreDto peopleScoreDto=new PeopleScoreDto();
            peopleScoreDto.setScoreInterval((int)(score-differenceScore)+"-" + (int)(score*1));
            peopleScoreDto.setStudentCount(count);
            peopleScoreDto.setProportion(rounding(getMathResult(totalPeoples,count),3)+"");
            resultMap.add(peopleScoreDto);
            return countPeople(totalPeoples,studentMakeResultModels,score-differenceScore,paperScore,resultMap);
        }
    }

    /**
     * 计算百分比
     * @param totalCount
     * @param count
     * @return
     */
    private  static double getMathResult(Integer totalCount, long count) {
        BigDecimal a=new BigDecimal(count);
        BigDecimal b=new BigDecimal(totalCount);
        BigDecimal c = a.divide(b, 3, RoundingMode.HALF_UP);
        return c.doubleValue();
    }
    /**
     * 计算标准差
     * @param studentMakeResultModels
     * @param avgScore 平均分
     * @return
     */
    private static  Double getStandardDeviation(List<StudentMakeResultModel> studentMakeResultModels, Double avgScore) {
        List<Double> sumDouble= Lists.newArrayList();
        //计算平均分差值 平方
        studentMakeResultModels.stream().forEach(studentMakeResultModel -> {
            BigDecimal subtractScore = new BigDecimal(studentMakeResultModel.getScore()).subtract(new BigDecimal(avgScore));
            Double pow = subtractScore.pow(2).setScale(2, RoundingMode.HALF_UP).doubleValue();
            sumDouble.add(pow);
        });

        Double powSumScore = sumDouble.stream().collect(Collectors.summingDouble(powScore -> powScore));

        BigDecimal divideScore = new BigDecimal(powSumScore).divide(new BigDecimal(sumDouble.size()),2,RoundingMode.HALF_UP);
        return new BigDecimal(Math.sqrt(divideScore.doubleValue())).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }


    /**
     * 获取学科历史平均分变化
     * @param releaseExamCode 发布记录code
     * @param subjectCode  学科code
     * @param schoolCode 学校code
     * @param gradeCode 学年code
     * @return
     * @throws Exception
     */
    @Override
    public List<HistoryAverageDto> getSubjectHistoryAverage(String releaseExamCode, String subjectCode,String schoolCode,String gradeCode) throws Exception{
        List<HistoryAverageDto> historyAverageDtos=new ArrayList<>();
        ReleaseExamPo releaseExamPo = getReleaseExamPo(releaseExamCode);

        List<String> examType=new ArrayList<>();
        examType.add("0");
        List<StudentMakeResultModel> makeResultModels = studentSubjectScoreService.findBySchoolCodeAndGradeCodeAndSubjectCodeAndSemesterAndExamType(schoolCode,gradeCode,subjectCode,releaseExamPo.getSemester(),examType);
        if(makeResultModels.isEmpty()){
            return  new ArrayList<>();
        }
        Map<Integer, Double> doubleMap = makeResultModels.stream().collect(Collectors.groupingBy(StudentMakeResultModel::getReleaseExamCode, Collectors.averagingDouble(StudentMakeResultModel::getScore)));
        for (Map.Entry<Integer, Double> doubleEntry : doubleMap.entrySet()) {
            HistoryAverageDto averageDto=new HistoryAverageDto();
            averageDto.setAverageScore(rounding(doubleEntry.getValue(),2));
            averageDto.setReleaseExamCode(doubleEntry.getKey());
            historyAverageDtos.add(averageDto);
        }
        List<HistoryAverageDto> averageDtos = historyAverageDtos.stream().sorted((o1, o2) -> o1.getReleaseExamCode() - o2.getReleaseExamCode()).collect(Collectors.toList());
        return averageDtos;
    }

    @Override
    public List<GradeResultsDto> getClassStudentSubjectResults(String releaseExamCode, String subjectCode) throws Exception {
        List<StudentMakeResultModel> studentMakeResultModels = studentSubjectScoreService.findByReleaseExamCodeAndSubjectCode(releaseExamCode, subjectCode);
        if(studentMakeResultModels.isEmpty()){
            return new ArrayList<>();
        }
        List<GradeResultsDto> gradeResultsDtos=new ArrayList<>();
        Map<String, List<StudentMakeResultModel>> stringListMap = studentMakeResultModels.stream().collect(Collectors.groupingBy(StudentMakeResultModel::getClassCode));

        for (Map.Entry<String, List<StudentMakeResultModel>> stringListEntry : stringListMap.entrySet()) {
            GradeResultsDto gradeResultsDto = getGradeResultsDto(stringListEntry.getValue());
            if(gradeResultsDto != null){
                gradeResultsDtos.add(gradeResultsDto);
            }
        }
        return  gradeResultsDtos.stream().sorted((o1,o2)->NaturalOrderComparator.compare(o1.getClassName(),o2.getClassName())).collect(Collectors.toList());
    }

    /**
     * 各班级学科提分空间
     *
     * @param releaseExamCode 考试发布记录code
     * @param subjectCode     学科code
     * @return
     * @throws Exception
     */
    @Override
    public List<ClassMentionScoreDto> getClasssSubjectMentionScore(String releaseExamCode, String subjectCode) throws Exception {
        List<ThreeColorModel> threeColorModels = threeColorService.findByReleaseExamCodeAndSubjectCode(releaseExamCode, subjectCode);
        if(threeColorModels.isEmpty()){
            return  new ArrayList<>();
        }
        List<ClassMentionScoreDto> classMentionScoreDtos=new ArrayList<>();
        Map<String, List<ThreeColorModel>> stringListMap = threeColorModels.stream().collect(Collectors.groupingBy(ThreeColorModel::getClassCode));
        for (Map.Entry<String, List<ThreeColorModel>> stringListEntry : stringListMap.entrySet()) {
            ClassMentionScoreDto classMentionScoreDto = getClassMentionScoreDto(stringListEntry.getValue());
            if(classMentionScoreDto!=null){
                classMentionScoreDtos.add(classMentionScoreDto);
            }
        }
        return classMentionScoreDtos;
    }

    /**
     * 获取学科提升空间
     * @param classThreeColorModels
     * @return
     */
    private ClassMentionScoreDto getClassMentionScoreDto(List<ThreeColorModel> classThreeColorModels){
        ClassMentionScoreDto classMentionScoreDto=new ClassMentionScoreDto();
        if(classThreeColorModels.isEmpty()){
            return null;
        }
        ThreeColorModel threeColorModel = classThreeColorModels.get(0);
        Map<String, List<ThreeColorModel>> stringListMap = classThreeColorModels.stream().collect(Collectors.groupingBy(ThreeColorModel::getModuleName));
        List<ThreeColorDto> threeColorDtos=new ArrayList<>();
        for (Map.Entry<String, List<ThreeColorModel>> stringListEntry : stringListMap.entrySet()) {
            ThreeColorDto colorDto=new ThreeColorDto();
            Double blueAvgScore = stringListEntry.getValue().stream().collect(Collectors.averagingDouble(ThreeColorModel::getBuleScore));
            Double grayAvgScore = stringListEntry.getValue().stream().collect(Collectors.averagingDouble(ThreeColorModel::getGrayScore));
            Double orangeAvgScore = stringListEntry.getValue().stream().collect(Collectors.averagingDouble(ThreeColorModel::getOrangeScore));
            colorDto.setBuleScore(rounding(blueAvgScore,1));
            colorDto.setGrayScore(rounding(grayAvgScore,1));
            colorDto.setOrangeScore(rounding(orangeAvgScore,1));
            colorDto.setModuleName(stringListEntry.getKey());
            threeColorDtos.add(colorDto);
        }
        classMentionScoreDto.setThreeColorDtos(threeColorDtos);
        classMentionScoreDto.setClassName(threeColorModel.getClassName());
        classMentionScoreDto.setClassCode(threeColorModel.getClassCode());
        classMentionScoreDto.setGradeCode(threeColorModel.getGradeCode());
        classMentionScoreDto.setReleaseExamCode(threeColorModel.getReleaseExamCode());
        return classMentionScoreDto;
    }

    /**
     * 各班级学科能力水平概览
     *
     * @param releaseExamCode 发布记录code
     * @param subjectCode     学科code
     * @throws Exception
     */
    @Override
    public GradeSummaryAbilityDto getClassSubjectAbility(String releaseExamCode, String subjectCode) throws Exception {
        GradeSummaryAbilityDto gradeSummaryAbilityDto=new GradeSummaryAbilityDto();
        List<SubjectAbilityModel> subjectAbilityModels = subjectAbilityService.getByReleaseExamCodeAndSubjectCode(releaseExamCode, subjectCode);
        List<ClassSubjectAbilityDto> classSubjectAbilityDtos = getClassSubjectAbilityDtos(subjectAbilityModels);
        List<AbilityDto> abilityDtos = getAbilityDtos(subjectAbilityModels);
        classSubjectAbilityDtos=classSubjectAbilityDtos.stream().sorted((o1,o2)->NaturalOrderComparator.compare(o1,o2)).collect(Collectors.toList());
        gradeSummaryAbilityDto.setGradeAveAbilityDtos(abilityDtos);
        gradeSummaryAbilityDto.setClassSubjectAbilityDtos(classSubjectAbilityDtos);
        return gradeSummaryAbilityDto;
    }

    /**
     * 获取各班级能力水平概览信息
     * @param subjectAbilityModels
     * @return
     */
    private List<ClassSubjectAbilityDto> getClassSubjectAbilityDtos(List<SubjectAbilityModel> subjectAbilityModels) {
        List<ClassSubjectAbilityDto> classSubjectAbilityDtos=new ArrayList<>();
        Map<String, Map<String, List<SubjectAbilityModel>>> stringMap = subjectAbilityModels.stream().collect(Collectors.groupingBy(SubjectAbilityModel::getClassName, Collectors.groupingBy(SubjectAbilityModel::getAbilityName)));
        for (Map.Entry<String, Map<String, List<SubjectAbilityModel>>> classMapEntry : stringMap.entrySet()) {
            ClassSubjectAbilityDto classSubjectAbilityDto=new ClassSubjectAbilityDto();
            classSubjectAbilityDto.setClassName(classMapEntry.getKey());
            List<AbilityDto> classAbilityDtos=new ArrayList<>();
            for (Map.Entry<String,List<SubjectAbilityModel>> abilityListEntry : classMapEntry.getValue().entrySet()) {
                SubjectAbilityModel subjectAbilityModel = abilityListEntry.getValue().get(0);
                Double aveScore = abilityListEntry.getValue().stream().collect(Collectors.averagingDouble(SubjectAbilityModel::getAbilityScore));
                AbilityDto abilityDto=new AbilityDto();
                abilityDto.setName(subjectAbilityModel.getAbilityName());
                abilityDto.setCode(subjectAbilityModel.getAbilityCode());
                abilityDto.setScore(rounding(aveScore,1));
                classAbilityDtos.add(abilityDto);
            }
            classAbilityDtos=classAbilityDtos.stream().sorted((o1,o2)->Integer.valueOf(o1.getCode())-Integer.valueOf(o2.getCode())).collect(Collectors.toList());
            classSubjectAbilityDto.setAbilityDtos(classAbilityDtos);
            classSubjectAbilityDtos.add(classSubjectAbilityDto);
        }
        return classSubjectAbilityDtos;
    }

    /**
     * 获取年级平均能力值集合
     * @param subjectAbilityModels 能力集合
     * @return
     */
    private List<AbilityDto> getAbilityDtos(List<SubjectAbilityModel> subjectAbilityModels) {
        Map<String, List<SubjectAbilityModel>> subjectAbilityListMap = subjectAbilityModels.stream().collect(Collectors.groupingBy(SubjectAbilityModel::getAbilityCode));
        List<AbilityDto> abilityDtos=new ArrayList<>();
        for (Map.Entry<String, List<SubjectAbilityModel>> subjectAbilityEntry : subjectAbilityListMap.entrySet()) {
            String abilityName = subjectAbilityEntry.getValue().get(0).getAbilityName();
            Double averageAbilityScore = subjectAbilityEntry.getValue().stream().collect(Collectors.averagingDouble(SubjectAbilityModel::getAbilityScore));
            AbilityDto abilityDto=new AbilityDto();
            abilityDto.setCode(subjectAbilityEntry.getKey());
            abilityDto.setScore(rounding(averageAbilityScore,1));
            abilityDto.setName(abilityName);
            abilityDtos.add(abilityDto);
        }
        return abilityDtos;
    }

    /**
     * 学科成绩变动信息
     *
     * @param releaseExamCode 考试发布记录code
     * @param subjectCode 学科code
     * @return
     * @throws Exception
     */
    @Override
    public List<GradeSubjectScoreChangeDto> getClassSubjectScoreChange(String releaseExamCode, String subjectCode,String schoolCode,String gradeCode) throws Exception {
        ReleaseExamPo releaseExamPo = getReleaseExamPo(releaseExamCode);
        List<String> examType=Lists.newArrayList();
        examType.add("0");
        List<StudentMakeResultModel> studentMakeResultModels = studentSubjectScoreService.findBySchoolCodeAndGradeCodeAndSubjectCodeAndSemesterAndExamType(schoolCode,gradeCode,subjectCode,releaseExamPo.getSemester(),examType);


        List<GradeSubjectScoreChangeDto> gradeSubjectScoreChangeDtos=Lists.newArrayList();
        Map<Integer, List<StudentMakeResultModel>> releaseExam = studentMakeResultModels.stream().collect(Collectors.groupingBy(StudentMakeResultModel::getReleaseExamCode));

        //遍历每次考试
        for (Map.Entry<Integer,List<StudentMakeResultModel>> releaseExamEntry : releaseExam.entrySet()) {
            GradeSubjectScoreChangeDto gradeSubjectScoreChangeDto=new GradeSubjectScoreChangeDto();
            gradeSubjectScoreChangeDto.setReleaseExamCode(releaseExamEntry.getKey());
            List<ArtTypeDto> artTypeDtos= Lists.newArrayList();
            //年级平均分 数学要分文理处理
            Double gradeAveScore = releaseExamEntry.getValue().stream().collect(Collectors.averagingDouble(StudentMakeResultModel::getScore));
            //年级标准差
            Double aveStandardDeviation = getStandardDeviation(releaseExamEntry.getValue(),gradeAveScore);
            //按照文理类型分组
            Map<Integer, List<StudentMakeResultModel>> groupByArtType = releaseExamEntry.getValue().stream().collect(Collectors.groupingBy(StudentMakeResultModel::getArtType));
            //遍历文理类型
            for (Map.Entry<Integer, List<StudentMakeResultModel>> artTypeListMap : groupByArtType.entrySet()) {
                ArtTypeDto artTypeDto=new ArtTypeDto();
                artTypeDto.setArtType(artTypeListMap.getKey());
                //班级成绩变动集合
                List<ClassSubjectScoreChangeDto> classSubjectScoreChangeDtos=Lists.newArrayList();
                if(BaseSubjectConstant.MATHEMATICS.equals(subjectCode)){//数学学科分文理
                    //年级平均分 数学要分文理处理
                     gradeAveScore = artTypeListMap.getValue().stream().collect(Collectors.averagingDouble(StudentMakeResultModel::getScore));
                    //年级标准差
                     aveStandardDeviation = getStandardDeviation(artTypeListMap.getValue(),gradeAveScore);
                }

                //按照班级分组
                Map<String, List<StudentMakeResultModel>> groupByClassCode = artTypeListMap.getValue().stream().collect(Collectors.groupingBy(StudentMakeResultModel::getClassCode));
                //遍历所有班级
                for (Map.Entry<String, List<StudentMakeResultModel>> classScore : groupByClassCode.entrySet()) {
                    StudentMakeResultModel studentMakeResultModel = classScore.getValue().get(0);
                    ClassSubjectScoreChangeDto classSubjectScoreChangeDto=new ClassSubjectScoreChangeDto();
                    classSubjectScoreChangeDto.setClassCode(studentMakeResultModel.getClassCode());
                    classSubjectScoreChangeDto.setClassName(studentMakeResultModel.getClassName());

                    Double finalGradeAveScore = gradeAveScore;
                    Double finalAveStandardDeviation = aveStandardDeviation;
                    //班级平均标准分
                    Double aveClassStandardScore;
                    if(aveStandardDeviation.equals(0D)){
                        aveClassStandardScore=0D;
                    }else{
                        aveClassStandardScore= getAveStandard(finalGradeAveScore, finalAveStandardDeviation, classScore);
                    }
                    classSubjectScoreChangeDto.setScore(rounding(aveClassStandardScore,2));
                    classSubjectScoreChangeDtos.add(classSubjectScoreChangeDto);
                }
                classSubjectScoreChangeDtos=classSubjectScoreChangeDtos.stream().sorted((o1,o2)->NaturalOrderComparator.compare(o1.getClassName(),o2.getClassName())).collect(Collectors.toList());

                artTypeDto.setClassSubjectScoreChangeDtos(classSubjectScoreChangeDtos);
                artTypeDtos.add(artTypeDto);
            }
            gradeSubjectScoreChangeDto.setArtTypeDtos(artTypeDtos);
            gradeSubjectScoreChangeDtos.add(gradeSubjectScoreChangeDto);
        }
        gradeSubjectScoreChangeDtos=gradeSubjectScoreChangeDtos.stream().sorted((o1, o2) -> o1.getReleaseExamCode()-o2.getReleaseExamCode()).collect(Collectors.toList());
        return gradeSubjectScoreChangeDtos;
    }

    /**
     * 获取班级历史能力水平历史变化曲线
     *
     * @param releaseExamCode 考试发布记录code
     * @param subjectCode     学科code
     * @throws Exception
     */
    @Override
    public  List<ClassHistoryAbilityChangeDto> getClassAbilistyHistoryChange(String releaseExamCode, String subjectCode,String schoolCode,String gradeCode) throws Exception {
        List<ClassHistoryAbilityChangeDto> changeDtos=Lists.newArrayList();
        ReleaseExamPo releaseExamPo = getReleaseExamPo(releaseExamCode);
        List<String> examTypes=new ArrayList<>();
        examTypes.add("0");
        examTypes.add("8");
        examTypes.add("9");
        examTypes.add("10");
        List<SubjectAbilityModel> abilityModels = subjectAbilityService.getBySchoolCodeAndGradeCodeAndSubjectCodeAndExamTypeAndSemester(schoolCode,gradeCode,subjectCode,examTypes,releaseExamPo.getSemester());
        //根据班级分组

        Map<String, List<SubjectAbilityModel>> classMap = abilityModels.stream().collect(Collectors.groupingBy(SubjectAbilityModel::getClassCode));
        for (Map.Entry<String, List<SubjectAbilityModel>> classEntry : classMap.entrySet()) {
            ClassHistoryAbilityChangeDto dto=new ClassHistoryAbilityChangeDto();
            dto.setClassCode(classEntry.getKey());
            dto.setClassName(classEntry.getValue().get(0).getClassName());
            //根据考试次数分组
            List<HistoryAbilityChangeDto> historyAbilityChangeDtoList=Lists.newArrayList();
            Map<Integer, List<SubjectAbilityModel>> releaseExamMap = classEntry.getValue().stream().collect(Collectors.groupingBy(SubjectAbilityModel::getReleaseExamCode));
            for (Map.Entry<Integer, List<SubjectAbilityModel>> releaseExamEntry : releaseExamMap.entrySet()) {
                HistoryAbilityChangeDto historyAbilityChangeDto=new HistoryAbilityChangeDto();
                historyAbilityChangeDto.setReleaseExamCode(releaseExamEntry.getKey());
                historyAbilityChangeDto.setReleaseExamCreateTime(releaseExamEntry.getValue().get(0).getReleaseExamCreateTime());
                //根据能力code分组
                Map<String, Double> abilityMap = releaseExamEntry.getValue().stream().collect(Collectors.groupingBy(SubjectAbilityModel::getAbilityCode, Collectors.averagingDouble(SubjectAbilityModel::getAbilityScore)));
                List<AbilityDto> abilityDtoList=Lists.newArrayList();
                for (Map.Entry<String, Double> anillityEntry : abilityMap.entrySet()) {
                    AbilityDto abilityDto=new AbilityDto();
                    abilityDto.setCode(anillityEntry.getKey());
                    abilityDto.setScore(rounding(anillityEntry.getValue(),1));
                    abilityDtoList.add(abilityDto);
                }
                historyAbilityChangeDto.setAbilityDtos(abilityDtoList);
                historyAbilityChangeDtoList.add(historyAbilityChangeDto);
            }
            dto.setHistoryAbilityChangeDto(historyAbilityChangeDtoList);
            changeDtos.add(dto);
        }
        changeDtos=changeDtos.stream().sorted((o1,o2)->NaturalOrderComparator.compare(o1,o2)).collect(Collectors.toList());
        return changeDtos;
    }


    /**
     * 教师学科成绩---》学科成绩平均分比对
     *
     * @param releaseExamCode 发布考试记录code
     * @param subjectCode     学科code
     * @throws Exception
     */
    @Override
    public GradeTeacherSubjectDto getTeacherSubjectAveScore(String releaseExamCode, String subjectCode) throws Exception {
        GradeTeacherSubjectDto gradeTeacherSubjectDto=new GradeTeacherSubjectDto();
        List<StudentMakeResultModel> studentMakeResultModelList = studentSubjectScoreService.findByReleaseExamCodeAndSubjectCode(releaseExamCode, subjectCode);
        if (studentMakeResultModelList.isEmpty()){
            return  null;
        }
        Double gradeSubjectAveScore = studentMakeResultModelList.stream().collect(Collectors.averagingDouble(StudentMakeResultModel::getScore));
        gradeTeacherSubjectDto.setGradeSubjectAveScore(rounding(gradeSubjectAveScore,2));
        StudentMakeResultModel studentMakeResultModel = studentMakeResultModelList.get(0);
        gradeTeacherSubjectDto.setPaperScore(studentMakeResultModel.getPaperScore());
        //根据教师分组
        Map<String, List<StudentMakeResultModel>> teacherMap = studentMakeResultModelList.stream().collect(Collectors.groupingBy(StudentMakeResultModel::getTeacherCode));
        List<TeacherSubjectAveScoreDto> teacherSubjectAveScoreDtoList=Lists.newArrayList();
        for (Map.Entry<String, List<StudentMakeResultModel>> teacherEntry : teacherMap.entrySet()) {
            TeacherSubjectAveScoreDto dto=new TeacherSubjectAveScoreDto();
            dto.setTeacherCode(teacherEntry.getKey());
            dto.setTeacherName(teacherEntry.getValue().get(0).getTeacherName());
            dto.setAveScore(rounding(teacherEntry.getValue().stream().collect(Collectors.averagingDouble(StudentMakeResultModel::getScore)),2));
            teacherSubjectAveScoreDtoList.add(dto);
        }
        gradeTeacherSubjectDto.setTeacherSubjectAveScoreDtos(teacherSubjectAveScoreDtoList);
        return  gradeTeacherSubjectDto;
    }

    /**
     * 教师教学成绩----》学科教学成绩贡献率
     *
     * @param releaseExamCode 发布考试记录code
     * @param subjectCode     学科code
     * @return
     * @throws Exception
     */
    @Override
    public List<TeacherContributionDto> getTeacherSubjectContribution(String releaseExamCode, String subjectCode) throws Exception {
        ReleaseExamPo releaseExamPo = getReleaseExamPo(releaseExamCode);
        List<StudentMakeResultModel> studentMakeResultModelList = studentSubjectScoreService.findByReleaseExamCodeAndSubjectCode(releaseExamCode, null);
        if(studentMakeResultModelList.isEmpty()){
            return  new ArrayList<>();
        }
        List<TeacherContributionDto> teacherContributionDtoList=Lists.newArrayList();
        //过滤学科  单科
        List<StudentMakeResultModel> subjectStudentMakeResultLists=studentMakeResultModelList.stream().filter(studentMakeResultModel -> subjectCode.equals(studentMakeResultModel.getSubjectCode())).collect(Collectors.toList());
        if(subjectStudentMakeResultLists.isEmpty()){
            return  new ArrayList<>();
        }
        Double paperScore=subjectStudentMakeResultLists.get(0).getPaperScore();
        Double totalScore=releaseExamPo.getTotalScore();
        //理论贡献率
        Double theoreticalContribution = getDouble(paperScore, totalScore,4);
        //根据教师分组
        Map<String, List<StudentMakeResultModel>> teacherMap = subjectStudentMakeResultLists.stream().collect(Collectors.groupingBy(StudentMakeResultModel::getTeacherCode));
        for (Map.Entry<String, List<StudentMakeResultModel>> teacherEntry : teacherMap.entrySet()) {
            TeacherContributionDto teacherContributionDto=new TeacherContributionDto();
            teacherContributionDto.setTeacherName(teacherEntry.getValue().get(0).getTeacherName());
            teacherContributionDto.setTeacherCode(teacherEntry.getKey());
            //根据教师所教班级分组
            Map<String, List<StudentMakeResultModel>> classMap = teacherEntry.getValue().stream().collect(Collectors.groupingBy(StudentMakeResultModel::getClassCode));
            List<ClassSubjectContributionDto> classSubjectContributionDtoList = getClassSubjectContributionDtos(studentMakeResultModelList, theoreticalContribution, classMap);
            teacherContributionDto.setClassSubjectContributionDtos(classSubjectContributionDtoList.stream().sorted((o1,o2)->NaturalOrderComparator.compare(o1.getClassName(),o2.getClassName())).collect(Collectors.toList()));
            //计算教师所有教的学生的成绩学科实际贡献率

            List<StudentMakeResultModel> temp=Lists.newArrayList();
            for (Map.Entry<String, List<StudentMakeResultModel>> stringListEntry : classMap.entrySet()) {
                temp.addAll(studentMakeResultModelList.stream().filter(studentMakeResultModel -> stringListEntry.getKey().equals(studentMakeResultModel.getClassCode())).collect(Collectors.toList()));
            }
            //用户总分集合
            Map<String, Double> userTotalScoreMap = temp.stream().collect(Collectors.groupingBy(StudentMakeResultModel::getUserCode, Collectors.summingDouble(StudentMakeResultModel::getScore)));
            Double totalScores=0D;

            for (Map.Entry<String, Double> userTotalScoreDoubleEntry : userTotalScoreMap.entrySet()) {
                totalScores += userTotalScoreDoubleEntry.getValue();
            }
            //学生平均总分
            Double allStudentTotalAveScore =getDouble(totalScores, (double) userTotalScoreMap.size(),2);

            //单科 教师下所有学生平均分
            Double singleSubjectAveScore = subjectStudentMakeResultLists.stream().collect(Collectors.averagingDouble(StudentMakeResultModel::getScore));
            //教师所教的所有学生的学科实际贡献率
            Double practicalContribution = getDouble(singleSubjectAveScore, allStudentTotalAveScore,4);
            teacherContributionDto.setContributionIndex(getDouble(practicalContribution,theoreticalContribution,4));
            teacherContributionDtoList.add(teacherContributionDto);
        }
        return teacherContributionDtoList;
    }

    /**
     * 获取班级学科贡献信息
     * @param studentMakeResultModelList
     * @param theoreticalContribution
     * @param classMap
     * @return
     */
    private List<ClassSubjectContributionDto> getClassSubjectContributionDtos(List<StudentMakeResultModel> studentMakeResultModelList, Double theoreticalContribution, Map<String, List<StudentMakeResultModel>> classMap) {
        List<ClassSubjectContributionDto> classSubjectContributionDtoList= Lists.newArrayList();
        for (Map.Entry<String, List<StudentMakeResultModel>> classEntry : classMap.entrySet()) {
            ClassSubjectContributionDto classSubjectContributionDto=new ClassSubjectContributionDto();
            classSubjectContributionDto.setTheoreticalContribution(theoreticalContribution);
            classSubjectContributionDto.setClassCode(classEntry.getKey());
            classSubjectContributionDto.setClassName(classEntry.getValue().get(0).getClassName());

            //单科平均分
            Double aveScore = classEntry.getValue().stream().collect(Collectors.averagingDouble(StudentMakeResultModel::getScore));

            //学生本次考试总得分集合
            Map<String, Double> userTotalScoreMap = studentMakeResultModelList.stream().filter(studentMakeResultModel ->
                                classEntry.getKey().equals(studentMakeResultModel.getClassCode())
            ).collect(Collectors.groupingBy(StudentMakeResultModel::getUserCode, Collectors.summingDouble(StudentMakeResultModel::getScore)));
            Double tempTotalScore=0D;
            for (Map.Entry<String, Double> userTotalScoreDoubleEntry : userTotalScoreMap.entrySet()) {
                tempTotalScore += userTotalScoreDoubleEntry.getValue();
            }
            Double aveTotalScore=getDouble(tempTotalScore, (double) userTotalScoreMap.size(),2);
            //实际贡献率按班级区分
            Double practicalContribution = getDouble(aveScore,aveTotalScore,4);
            classSubjectContributionDto.setPracticalContribution(practicalContribution);
            List<AuthUserBean> students = authUserManagerService.getMyStudentByClassId(Integer.valueOf(classEntry.getKey()));
            Double allStudent = 0d;
            if(CollectionUtils.isNotEmpty(students)){
                allStudent = Double.valueOf(students.size());
            }
            classSubjectContributionDto.setSubjectAnswerRate(getDouble(Double.valueOf(classEntry.getValue().size()),allStudent,4));
            classSubjectContributionDto.setSubjectContribution(getDouble(practicalContribution,theoreticalContribution,4));
            classSubjectContributionDtoList.add(classSubjectContributionDto);
        }
        return classSubjectContributionDtoList;
    }

    private Double getDouble(Double molecular, Double denominator,int scale) {
        //如果分母为0
        if(denominator.equals(0D)){
            return 0D;
        }
        return new BigDecimal(molecular).divide(new BigDecimal(denominator),scale, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 获取各知识点教师教学情况
     *
     * @param releaseExamCode 发布考试记录code
     * @param subjectCode     学科code
     * @return
     * @throws Exception
     */
    @Override
    public List<TeachingKnowledgeModuleDto> getTeachingKnowledgeModule(String releaseExamCode, String subjectCode) throws Exception {

        List<KnowledgeModuleModel> knowledgeModuleModelList = knowledgeModuleService.findByReleaseExamCodeAndSubjectCode(releaseExamCode, subjectCode);

        //根据模块code分组
        List<TeachingKnowledgeModuleDto> teachingKnowledgeModuleDtoList=Lists.newArrayList();
        Map<String, List<KnowledgeModuleModel>> moduleMap = knowledgeModuleModelList.stream().collect(Collectors.groupingBy(KnowledgeModuleModel::getKnowledgeModuleCode));
        for (Map.Entry<String, List<KnowledgeModuleModel>> moduleEntry : moduleMap.entrySet()) {
            TeachingKnowledgeModuleDto teachingKnowledgeModuleDto=new TeachingKnowledgeModuleDto();
            teachingKnowledgeModuleDto.setKnowledgeModuleCode(moduleEntry.getKey());
            teachingKnowledgeModuleDto.setKnowledgeModuleName(moduleEntry.getValue().get(0).getKnowledgeModuleName());
            List<TeacherKnowledgeModuleDto> teacherKnowledgeModuleDtoList = getTeacherKnowledgeModuleDtoList(moduleEntry);
            teachingKnowledgeModuleDto.setTeacherKnowledgeModuleDtoList(teacherKnowledgeModuleDtoList);
            teachingKnowledgeModuleDtoList.add(teachingKnowledgeModuleDto);
        }
        return teachingKnowledgeModuleDtoList;
    }

    /**
     * 获取教师教学模块情况信息
     * @param moduleEntry
     * @return
     */
    private List<TeacherKnowledgeModuleDto> getTeacherKnowledgeModuleDtoList(Map.Entry<String, List<KnowledgeModuleModel>> moduleEntry) {
        List<TeacherKnowledgeModuleDto> teacherKnowledgeModuleDtoList= Lists.newArrayList();
        //根据教师code分组
        Map<String, List<KnowledgeModuleModel>> teacherMap = moduleEntry.getValue().stream().collect(Collectors.groupingBy(KnowledgeModuleModel::getTeacherCode));
        for (Map.Entry<String, List<KnowledgeModuleModel>> teacherListEntry : teacherMap.entrySet()) {
            TeacherKnowledgeModuleDto teacherKnowledgeModuleDto=new TeacherKnowledgeModuleDto();
            teacherKnowledgeModuleDto.setTeacherCode(teacherListEntry.getKey());
            teacherKnowledgeModuleDto.setTeacherName(teacherListEntry.getValue().get(0).getTeacherName());
            teacherKnowledgeModuleDto.setKnowledgeModuleScore(rounding(teacherListEntry.getValue().stream().collect(Collectors.averagingDouble(KnowledgeModuleModel::getKnowledgeModuleScore)),3));
            teacherKnowledgeModuleDtoList.add(teacherKnowledgeModuleDto);
        }
        return teacherKnowledgeModuleDtoList;
    }


    /**
     * 获取各项学科能力教师教学情况
     *
     * @param releaseExamCode 发布考试记录code
     * @param subjectCode     学科code
     * @return
     * @throws Exception
     */
    @Override
    public List<TeachingAbilityDto> getTeachingAbility(String releaseExamCode, String subjectCode) throws Exception {
        List<SubjectAbilityModel> subjectAbilityModelList = subjectAbilityService.getByReleaseExamCodeAndSubjectCode(releaseExamCode, subjectCode);
        //根据能力code分组
        List<TeachingAbilityDto> teachingAbilityDtoList= Lists.newArrayList();
        Map<String, List<SubjectAbilityModel>> abilityMap = subjectAbilityModelList.stream().collect(Collectors.groupingBy(SubjectAbilityModel::getAbilityCode));
        for (Map.Entry<String, List<SubjectAbilityModel>> abilityListEntry : abilityMap.entrySet()) {
            TeachingAbilityDto teachingAbilityDto=new TeachingAbilityDto();
            teachingAbilityDto.setAbilityCode(abilityListEntry.getKey());
            teachingAbilityDto.setAbilityName(abilityListEntry.getValue().get(0).getAbilityName());
            List<TeacherAbilityDto> teacherAbilityDtoList = getTeacherAbilityDtos(abilityListEntry);
            teachingAbilityDto.setTeacherAbilityDtos(teacherAbilityDtoList);
            teachingAbilityDtoList.add(teachingAbilityDto);
        }
        return teachingAbilityDtoList;
    }

    /**
     * 获取教师学科能力教学情况
     * @param abilityListEntry
     * @return
     */
    private List<TeacherAbilityDto> getTeacherAbilityDtos(Map.Entry<String, List<SubjectAbilityModel>> abilityListEntry) {
        List<TeacherAbilityDto> teacherAbilityDtoList= Lists.newArrayList();
        Map<String, List<SubjectAbilityModel>> teacherMap = abilityListEntry.getValue().stream().collect(Collectors.groupingBy(SubjectAbilityModel::getTeacherCode));
        for (Map.Entry<String, List<SubjectAbilityModel>> teacherListEntry : teacherMap.entrySet()) {
            TeacherAbilityDto teacherAbilityDto=new TeacherAbilityDto();
            teacherAbilityDto.setTeacherCode(teacherListEntry.getKey());
            teacherAbilityDto.setTeacherName(teacherListEntry.getValue().get(0).getTeacherName());
            teacherAbilityDto.setAbilityScore(rounding(teacherListEntry.getValue().stream().collect(Collectors.averagingDouble(SubjectAbilityModel::getAbilityScore)),1));
            teacherAbilityDtoList.add(teacherAbilityDto);
        }
        return teacherAbilityDtoList;
    }


    /**
     * 获取教师学科平均成绩历史变化
     *
     * @param releaseExamCode 考试发布记录code
     * @param schoolCode 学校code
     * @param gradeCode 学年code
     * @param subjectCode     学科code
     * @throws Exception
     */
    @Override
    public List<TeacherHistoryStandardDto> getTeacherHistoryAveScore(String releaseExamCode, String subjectCode,String schoolCode,String gradeCode) throws Exception {
        ReleaseExamPo releaseExamPo = getReleaseExamPo(releaseExamCode);
        List<String> examType=Lists.newArrayList();
        examType.add("0");
        List<StudentMakeResultModel> studentMakeResultModelList = studentSubjectScoreService.findBySchoolCodeAndGradeCodeAndSubjectCodeAndSemesterAndExamType(schoolCode, gradeCode,subjectCode,releaseExamPo.getSemester(), examType);
        if(studentMakeResultModelList.isEmpty()){
            return new ArrayList<>();
        }
        List<TeacherHistoryStandardDto> teacherHistoryStandardDtoList=Lists.newArrayList();
        //不区分文理年级学科平均分
        //年级学生平均分
        Double gradeAveScore=studentMakeResultModelList.stream().collect(Collectors.averagingDouble(StudentMakeResultModel::getScore));
        //年级学科标准差
        Double gradeDeviationScore = getStandardDeviation(studentMakeResultModelList, gradeAveScore);
        //按照考试次数分组
        Map<Integer, List<StudentMakeResultModel>> releaseExamListMap = studentMakeResultModelList.stream().collect(Collectors.groupingBy(StudentMakeResultModel::getReleaseExamCode));
        for (Map.Entry<Integer, List<StudentMakeResultModel>> releaseExamListEntry : releaseExamListMap.entrySet()) {
            StudentMakeResultModel studentMakeResultModel = releaseExamListEntry.getValue().get(0);
            TeacherHistoryStandardDto teacherHistoryStandardDto=new TeacherHistoryStandardDto();
            teacherHistoryStandardDto.setReleaseExamCode(studentMakeResultModel.getReleaseExamCode());
            teacherHistoryStandardDto.setReleaseExamCreateTime(studentMakeResultModel.getReleaseCrateTime());
            //按照教师所教学生分组
            Map<String, List<StudentMakeResultModel>> teacherMap = releaseExamListEntry.getValue().stream().collect(Collectors.groupingBy(StudentMakeResultModel::getTeacherCode));
            List<TeacherStandardChangeDto> teacherStandardChangeDtoList=Lists.newArrayList();
            for (Map.Entry<String, List<StudentMakeResultModel>> teacherEntry : teacherMap.entrySet()) {
                StudentMakeResultModel stuModel = teacherEntry.getValue().get(0);
                TeacherStandardChangeDto teacherStandardChangeDto=new TeacherStandardChangeDto();
                teacherStandardChangeDto.setTeacherCode(stuModel.getTeacherCode());
                teacherStandardChangeDto.setTeacherName(stuModel.getTeacherName());
                Double aveStandardScore = getAveStandard(gradeAveScore, gradeDeviationScore, teacherEntry);
                teacherStandardChangeDto.setStandardScore(rounding(aveStandardScore,2));
                teacherStandardChangeDtoList.add(teacherStandardChangeDto);
            }
            teacherHistoryStandardDto.setTeacherStandardChangeDtos(teacherStandardChangeDtoList);
            teacherHistoryStandardDtoList.add(teacherHistoryStandardDto);
        }
        //按考试发布记录时间排序
        teacherHistoryStandardDtoList=teacherHistoryStandardDtoList.stream().sorted((o1, o2) -> o2.getReleaseExamCreateTime().compareTo(o1.getReleaseExamCreateTime())).collect(Collectors.toList());
        return teacherHistoryStandardDtoList;

    }

    /**
     * 获取发布考试记录信息
     * @param releaseExamCode 发布考试记录code
     * @return
     * @throws Exception
     */
    private ReleaseExamPo getReleaseExamPo(String releaseExamCode) throws Exception {
        ReleaseExamPo releaseExamPo = releaseExamService.findByReleaseExamCode(releaseExamCode);
        if(releaseExamPo == null){
            throw new ReportException("此考试发布记录code无效-------->"+releaseExamCode);//new ReportException("此考试发布记录code无效-------->"+releaseExamCode);
        }
        return releaseExamPo;
    }

    /**
     * 获取平均标准分
     * @param gradeAveScore 平均分
     * @param gradeDeviationScore 标准差
     * @param teacherEntry 学生集合信息
     * @return
     */
    private Double getAveStandard(Double gradeAveScore, Double gradeDeviationScore, Map.Entry<String, List<StudentMakeResultModel>> teacherEntry) {
        return teacherEntry.getValue().stream().map(stu -> {
            BigDecimal c = getSingleStandardBigDecimal(gradeAveScore, gradeDeviationScore, stu.getScore());
            return c.doubleValue();
        }).collect(Collectors.averagingDouble(score -> score));
    }

    /**
     * 计算单个的标准分
     * @param gradeAveScore  平均分
     * @param gradeDeviationScore 标准差
     * @param getScore 当前得分
     * @return
     */
    private BigDecimal getSingleStandardBigDecimal(Double gradeAveScore, Double gradeDeviationScore, Double getScore) {
        BigDecimal a=new BigDecimal(getScore).subtract(new BigDecimal(gradeAveScore));
        BigDecimal b=new BigDecimal(gradeDeviationScore);
        if(b.equals(new BigDecimal(0))){
            return new BigDecimal(0);
        }
        return a.divide(b, 2, RoundingMode.HALF_UP);
    }


    /**
     * 获取教师学科贡献指数变化情况
     *
     * @param releaseExamCode 发布考试记录code
     * @param subjectCode     学科code
     * @param schoolCode 学校code
     * @param gradeCode 学年code
     * @throws Exception
     */
    @Override
    public List<TeacherHistoryContributionDto> getTeacherSubjectContributionHistoryChange(String releaseExamCode, String subjectCode,String schoolCode,String gradeCode) throws Exception {
        ReleaseExamPo releaseExamPo = getReleaseExamPo(releaseExamCode);
        List<String> examTypes=Lists.newArrayList();
        examTypes.add("0");
        examTypes.add("8");
        examTypes.add("9");
        examTypes.add("10");
        //List<StudentMakeResultModel> studentMakeResultModelList = studentSubjectScoreService.findByReleaseExamCodeAndSemesterAndExamType(releaseExamCode, subjectCode, releaseExamPo.getSemester(), examTypes);
        //获取本学期历次考试
        List<StudentMakeResultModel> allSubjectStudentResult = studentSubjectScoreService.findBySchoolCodeAndGradeCodeAndSubjectCodeAndSemesterAndExamType(schoolCode,gradeCode,null,releaseExamPo.getSemester(),examTypes);
        if(allSubjectStudentResult.isEmpty()){
            return new ArrayList<>();
        }
        //过滤单科
        List<StudentMakeResultModel> singleSubjectStudentMakeResultModelList=allSubjectStudentResult.stream().filter(studentMakeResultModel -> subjectCode.equals(studentMakeResultModel.getSubjectCode())).collect(Collectors.toList());
        List<TeacherHistoryContributionDto> teacherHistoryContributionDtoList=Lists.newArrayList();
        //按照考试次数分组
//        List<String> xAxis= new ArrayList<>();
//        Multimap<String,Double> multimap= ArrayListMultimap.create();
//        GraphicsModel graphicsModel=new GraphicsModel();
//        List<GraphicsDataModel> datas=new ArrayList<>();
        Map<Integer, List<StudentMakeResultModel>> releaseExamMap = singleSubjectStudentMakeResultModelList.stream().collect(Collectors.groupingBy(StudentMakeResultModel::getReleaseExamCode));
        //Map<String, List<StudentMakeResultModel>> teacherMapList = singleSubjectStudentMakeResultModelList.stream().collect(Collectors.groupingBy(StudentMakeResultModel::getTeacherCode));
        for (Map.Entry<Integer, List<StudentMakeResultModel>> releaseExamEntry : releaseExamMap.entrySet()) {
            //xAxis.add(releaseExamEntry.getKey()+"");
            List<StudentMakeResultModel> allLists=allSubjectStudentResult.stream().filter(s->releaseExamEntry.getKey().equals(s.getReleaseExamCode())).collect(Collectors.toList());
            StudentMakeResultModel studentMakeResultModel = releaseExamEntry.getValue().get(0);
            TeacherHistoryContributionDto teacherHistoryContributionDto=new TeacherHistoryContributionDto();
            teacherHistoryContributionDto.setReleaseExamCreateTime(studentMakeResultModel.getReleaseCrateTime());
            teacherHistoryContributionDto.setReleaseExamCode(studentMakeResultModel.getReleaseExamCode());
            //根据教师code分组 获取教师所教所有学生
            Map<String, List<StudentMakeResultModel>> teacherMap = releaseExamEntry.getValue().stream().collect(Collectors.groupingBy(StudentMakeResultModel::getTeacherCode));
            List<TeacherContributionChangeDto> teacherContributionChangeDtoList=Lists.newArrayList();
            Double totalContribution=0D;

            for (Map.Entry<String, List<StudentMakeResultModel>> teacherListEntry : teacherMap.entrySet()) {
//                if(teacherMap.size()!=teacherMapList.size()){
//                    for (Map.Entry<String, List<StudentMakeResultModel>> entry : teacherMapList.entrySet()) {
//                        if(!entry.getKey().equals(teacherListEntry.getKey())){
//                            multimap.put(entry.getValue().get(0).getTeacherName(),null);
//                        }
//                    }
//                }
                StudentMakeResultModel model = teacherListEntry.getValue().get(0);
                TeacherContributionChangeDto teacherContributionChangeDto=new TeacherContributionChangeDto();
                teacherContributionChangeDto.setTeacherCode(model.getTeacherCode());
                teacherContributionChangeDto.setTeacherName(model.getTeacherName());

                //根据学生分组 获取学生本次考试总成绩
                Map<String, Double> userTotalMap = allLists.stream().collect(Collectors.groupingBy(StudentMakeResultModel::getUserCode,
                                                        Collectors.summingDouble(StudentMakeResultModel::getScore)));
                Double tempTotalScore=0D;
                for (Map.Entry<String, Double> userTotalScoreEntry : userTotalMap.entrySet()) {
                    tempTotalScore += userTotalScoreEntry.getValue();
                }
                Double totalAveScore=getDouble(tempTotalScore, (double) userTotalMap.size(),2);
                Double singleSubjectAveScore=teacherListEntry.getValue().stream().collect(Collectors.averagingDouble(StudentMakeResultModel::getScore));
                //实际贡献率
                Double practicalContribution = getDouble(singleSubjectAveScore,totalAveScore,4);
                //理论贡献率
                Double theoreticalContribution = getDouble(studentMakeResultModel.getPaperScore(), releaseExamPo.getTotalScore(),4);
                //贡献指数
                Double teacherContribution=getDouble(practicalContribution,theoreticalContribution,4);
                teacherContributionChangeDto.setContribution(teacherContribution);
                teacherContributionChangeDtoList.add(teacherContributionChangeDto);

                //multimap.put(model.getTeacherName(),teacherContribution);

                totalContribution += teacherContribution;
            }
            teacherHistoryContributionDto.setTeacherContributionChangeDtos(teacherContributionChangeDtoList);
            teacherHistoryContributionDto.setSubjectAveContribution(getDouble(totalContribution, (double) teacherMap.size(),4));

            //multimap.put("SubjectAveContribution",teacherHistoryContributionDto.getSubjectAveContribution());
            teacherHistoryContributionDtoList.add(teacherHistoryContributionDto);
        }
//        for (String s : multimap.keySet()) {
//            GraphicsDataModel graphicsDataModel=new GraphicsDataModel();
//            graphicsDataModel.setName(s);
//            graphicsDataModel.setData(multimap.get(s));
//            datas.add(graphicsDataModel);
//        }

//        graphicsModel.setXAxi(xAxis);
//        graphicsModel.setSeries(datas);
        return teacherHistoryContributionDtoList;
    }


    /**
     * 获取班级学生原始得分信息
     *
     * @param releaseExamCode 发布考试记录code
     * @param subjectCode     学科code
     * @return
     * @throws Exception
     */
    @Override
    public List<ClassStudentScoreDto> getsClassStudentOriginalScore(String releaseExamCode, String subjectCode) throws Exception {
        List<StudentMakeResultModel> studentMakeResultModels = studentSubjectScoreService.findByReleaseExamCodeAndSubjectCode(releaseExamCode, subjectCode);
        if(studentMakeResultModels.isEmpty()){
            return new ArrayList<>();
        }

        List<ClassStudentScoreDto> classStudentScoreDtoList=Lists.newArrayList();
        //按班级分组
        Map<String, List<StudentMakeResultModel>> classMap = studentMakeResultModels.stream().collect(Collectors.groupingBy(StudentMakeResultModel::getClassCode));
        for (Map.Entry<String, List<StudentMakeResultModel>> classEntry : classMap.entrySet()) {
            StudentMakeResultModel studentMakeResultModel = classEntry.getValue().get(0);
            ClassStudentScoreDto classStudentScoreDto=new ClassStudentScoreDto();
            classStudentScoreDto.setClassName(studentMakeResultModel.getClassName());
            classStudentScoreDto.setClassCode(studentMakeResultModel.getClassCode());
            List<StudentScoreDto> studentScoreDtoList = classEntry.getValue().stream().map(stu->{
                StudentScoreDto studentScoreDto=new StudentScoreDto();
                studentScoreDto.setScore(stu.getScore());
                studentScoreDto.setStudentCode(stu.getUserCode());
                studentScoreDto.setStudentName(stu.getUserName());
                return studentScoreDto;
            }).collect(Collectors.toList());
            classStudentScoreDto.setStudentScoreDtos(studentScoreDtoList);
            classStudentScoreDtoList.add(classStudentScoreDto);
        }
        classStudentScoreDtoList=classStudentScoreDtoList.stream().sorted((o1,o2)->NaturalOrderComparator.compare(o1,o2)).collect(Collectors.toList());
        return classStudentScoreDtoList;
    }


    /**
     * 获取班级学生标准分信息
     *
     * @param releaseExamCode 发布考试记录code
     * @param subjectCode     学科code
     * @return
     * @throws Exception
     */
    @Override
    public List<ClassStudentScoreDto> getsClassStudentStandardScore(String releaseExamCode, String subjectCode) throws Exception {
        List<StudentMakeResultModel> studentMakeResultModels = studentSubjectScoreService.findByReleaseExamCodeAndSubjectCode(releaseExamCode, subjectCode);
        if(studentMakeResultModels.isEmpty()){
            return new ArrayList<>();
        }
        List<ClassStudentScoreDto> classStudentScoreDtoList=Lists.newArrayList();
        //年级学科平均分
        Double gradeAveScore=studentMakeResultModels.stream().collect(Collectors.averagingDouble(StudentMakeResultModel::getScore));
        //年级标准差
        Double gradeStandardDeviation = getStandardDeviation(studentMakeResultModels, gradeAveScore);
        //根据班级分组
        Map<String, List<StudentMakeResultModel>> classMap = studentMakeResultModels.stream().collect(Collectors.groupingBy(StudentMakeResultModel::getClassCode));
        for (Map.Entry<String, List<StudentMakeResultModel>> classEntry : classMap.entrySet()) {
            StudentMakeResultModel studentMakeResultModel = classEntry.getValue().get(0);
            ClassStudentScoreDto classStudentScoreDto=new ClassStudentScoreDto();
            classStudentScoreDto.setClassCode(studentMakeResultModel.getClassCode());
            classStudentScoreDto.setClassName(studentMakeResultModel.getClassName());
            List<StudentScoreDto> studentScoreDtoList = classEntry.getValue().stream().map(stu -> {
                StudentScoreDto studentScoreDto = new StudentScoreDto();
                studentScoreDto.setScore(rounding(getSingleStandardBigDecimal(gradeAveScore, gradeStandardDeviation, stu.getScore()).doubleValue(),2));
                studentScoreDto.setStudentCode(stu.getUserCode());
                studentScoreDto.setStudentName(stu.getUserName());
                return studentScoreDto;
            }).collect(Collectors.toList());
            classStudentScoreDto.setStudentScoreDtos(studentScoreDtoList);
            classStudentScoreDtoList.add(classStudentScoreDto);
        }
        classStudentScoreDtoList=classStudentScoreDtoList.stream().sorted((o1,o2)->NaturalOrderComparator.compare(o1,o2)).collect(Collectors.toList());
        return classStudentScoreDtoList;
    }

    /**
     *四舍五入
     * @param d 数值
     * @param numberOfBits  保留位数
     * @return
     */
    public static Double rounding(Double d,int numberOfBits){
        BigDecimal b=new BigDecimal(d).setScale(numberOfBits,RoundingMode.HALF_UP);
        return b.doubleValue();
    }


    public static void main(String[] args) {
        List<SubjectAbilityModel> subjectAbilityModels =new ArrayList<>();
        SubjectAbilityModel max = Collections.max(subjectAbilityModels, (o1, o2) -> 0);
    }

}

