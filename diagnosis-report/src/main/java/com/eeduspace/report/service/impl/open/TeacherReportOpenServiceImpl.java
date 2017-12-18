package com.eeduspace.report.service.impl.open;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.eedu.auth.beans.AuthGroupBean;
import com.eedu.auth.beans.AuthUserManagerBean;
import com.eedu.auth.service.AuthUserManagerService;
import com.eedu.diagnosis.exam.api.dto.DiagnosisClassRelationDto;
import com.eedu.diagnosis.exam.api.dto.DiagnosisRecordStudentDto;
import com.eedu.diagnosis.exam.api.dto.DiagnosisRecordTeacherDto;
import com.eedu.diagnosis.exam.api.openService.DiagnosisClassRelationOpenService;
import com.eedu.diagnosis.exam.api.openService.DiagnosisRecordStudentOpenService;
import com.eedu.diagnosis.exam.api.openService.DiagnosisRecordTeacherOpenService;
import com.eeduspace.b2b.report.exception.ReportException;
import com.eeduspace.b2b.report.model.report.*;
import com.eeduspace.b2b.report.service.TeacherReportOpenService;
import com.eeduspace.report.model.KnowledgeModel;
import com.eeduspace.report.model.SubjectAbilityModel;
import com.eeduspace.report.po.ReleaseExamPo;
import com.eeduspace.report.service.*;
import com.eeduspace.report.util.CountUtils;
import com.eeduspace.report.util.ReportMathUtils;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 教师报告对外接口实现
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-03-22 14:22
 **/
@Slf4j
@Service("teacherReportOpenServiceImpl")
@com.alibaba.dubbo.config.annotation.Service
public class TeacherReportOpenServiceImpl implements TeacherReportOpenService{
    @Autowired
    private StudentSubjectScoreService studentSubjectScoreService;
    @Autowired
    private UserAnswerResultService userAnswerResultService;
    @Autowired
    private KnowledgeModuleService knowledgeModuleService;
    @Autowired
    private KnowledgeService knowledgeService;
    @Autowired
    private SubjectAbilityService subjectAbilityService;
    @Autowired
    private DiagnosisRecordStudentOpenService diagnosisRecordStudentOpenService;
    @Autowired
    private ReleaseExamService releaseExamService;
    @Autowired
    private AuthUserManagerService userManagerService;
    @Autowired
    private DiagnosisRecordTeacherOpenService diagnosisRecordTeacherOpenService;
    @Autowired
    private DiagnosisClassRelationOpenService diagnosisClassRelationOpenService;

    /**
     * 获取班级标准分变动
     *
     * @param teacherCode
     * @param subjectCode
     * @param releaseExamCode
     * @param type 0 同步辅导 1 学科测评
     * @return
     */
    @Override
    public StandardScoreChangeModel getStandardScoreChange(Integer type,String teacherCode, String subjectCode, String releaseExamCode) throws Exception{
        StandardScoreChangeModel standardScoreChangeModel =new StandardScoreChangeModel();

        ReleaseExamPo releaseExamPo = getReleaseExamPo(releaseExamCode);
        List<String> examTypes =new ArrayList<>();
        String queryTeacherCode = teacherCode;
        AuthUserManagerBean teacherInfo =new AuthUserManagerBean();
        teacherInfo.setUserId(Integer.valueOf(teacherCode));
        teacherInfo=userManagerService.getTeacherInfo(teacherInfo);
        List<String> releaseCodes = new ArrayList<>();
        DiagnosisRecordTeacherDto diagnosisRecordTeacherDto =new DiagnosisRecordTeacherDto();
        List<AuthGroupBean> classLists = userManagerService.getClassByTeacherId(Integer.valueOf(teacherCode));
        List<String> groupIds = classLists.stream().map(group -> group.getGroupId().toString()).collect(Collectors.toList());
        if (type.equals(0)){
            examTypes.add("1");
            examTypes.add("2");
            examTypes.add("3");
            examTypes.add("4");
            examTypes.add("5");
            examTypes.add("6");
            examTypes.add("7");
            examTypes.add("8");
            examTypes.add("9");
            examTypes.add("10");


            DiagnosisClassRelationDto dcdto = new DiagnosisClassRelationDto();
            dcdto.setTeacherCode(Integer.parseInt(teacherCode));
            dcdto.setSchoolCode(teacherInfo.getUserSchoolId());
            dcdto.setSubjectCode(Integer.parseInt(subjectCode));
            dcdto.setClassCodes(groupIds.stream().map(c->Integer.parseInt(c)).collect(Collectors.toList()));
            List<DiagnosisClassRelationDto> dclist = diagnosisClassRelationOpenService.getListByParam(dcdto);
            if(!CollectionUtils.isEmpty(dclist)){
                List<String> recordCodes = new ArrayList<>();
                dclist.forEach(dto -> {
                    if (!recordCodes.contains(dto.getDiagnosisRecordCode()))
                        recordCodes.add(dto.getDiagnosisRecordCode());
                });
                diagnosisRecordTeacherDto.setCodes(recordCodes);
                diagnosisRecordTeacherDto.setExamYear(releaseExamPo.getSemester());
                diagnosisRecordTeacherDto.setGradeCode(teacherInfo.getUserGradeIden());
                List<DiagnosisRecordTeacherDto> all = diagnosisRecordTeacherOpenService.getAll(diagnosisRecordTeacherDto, null);

                DiagnosisRecordTeacherDto dto = all.stream().filter(r -> releaseExamPo.getReleaseCode().equals(r.getCode())).findFirst().get();
                releaseCodes=all.stream().filter(r->dto.getEndTime().getTime()>=r.getEndTime().getTime()).map(t->t.getCode()).collect(Collectors.toList());
            }
        }else {
            queryTeacherCode= null;
            examTypes.add("0");
            examTypes.add("8");
            examTypes.add("9");
            examTypes.add("10");
            diagnosisRecordTeacherDto.setTeacherCode(Integer.valueOf(teacherCode));
            diagnosisRecordTeacherDto.setClassCodes(groupIds.stream().map(c->Integer.parseInt(c)).collect(Collectors.toList()));
            diagnosisRecordTeacherDto.setSubjectCode(Integer.parseInt(subjectCode));
            diagnosisRecordTeacherDto.setSchoolCode(teacherInfo.getUserSchoolId());
            diagnosisRecordTeacherDto.setExamYear(releaseExamPo.getSemester());
            PageInfo<DiagnosisRecordTeacherDto> diagnosisListForMaster = diagnosisRecordTeacherOpenService.getDiagnosisListForMaster(diagnosisRecordTeacherDto, 1, Integer.MAX_VALUE);
            DiagnosisRecordTeacherDto dto = diagnosisListForMaster.getList().stream().filter(r -> releaseExamPo.getReleaseCode().equals(r.getCode())).findFirst().get();
            releaseCodes=diagnosisListForMaster.getList().stream().filter(r->dto.getEndTime().getTime()>=r.getEndTime().getTime()).map(t->t.getCode()).collect(Collectors.toList());
        }

        List<StudentMakeResultModel> studentMakeResultModels = studentSubjectScoreService.findByCondition(null, queryTeacherCode, subjectCode, null, releaseExamPo.getSemester(), examTypes, teacherInfo.getUserSchoolId()==null?null:teacherInfo.getUserSchoolId()+"", teacherInfo.getUserGradeIden()==null?null:teacherInfo.getUserGradeIden()+"");

        List<String> allReleaseCodes = releaseCodes;

        List<ClassModel> classModels = classLists.stream().map(group -> {
            ClassModel classModel = new ClassModel();
            classModel.setClassName(group.getGroupName());
            classModel.setClassCode(group.getGroupId() + "");
            return classModel;
        }).collect(Collectors.toList());
        //根据考试次数分组
        Map<Integer, List<StudentMakeResultModel>> collect;
        if(type.equals(0)){//同步辅导 教师所教班级对比
            collect = studentMakeResultModels.stream().filter(resultModel -> groupIds.contains(resultModel.getClassCode())&&allReleaseCodes.contains(resultModel.getReleaseCode())).collect(Collectors.groupingBy(StudentMakeResultModel::getReleaseExamCode));
        }else {//学科测评  所有年级下班级对比
            collect = studentMakeResultModels.stream().filter(resultModel -> allReleaseCodes.contains(resultModel.getReleaseCode())).collect(Collectors.groupingBy(StudentMakeResultModel::getReleaseExamCode));
        }
        List<ClassStandardModel> classStandardModels = new ArrayList<>();
        List<ExamModel> examModels =new ArrayList<>();
        collect.forEach((key, value) ->{
            ExamModel examModel = new ExamModel();
            examModel.setExamCode(key);
            examModel.setExamTime(value.get(0).getReleaseCrateTime());
            examModels.add(examModel);
            //一次考试学科得分的平均分
            Double avgScore = value.stream().collect(Collectors.averagingDouble(StudentMakeResultModel::getScore));
            //标准差
            Double standardDeviation = getStandardDeviation(value, avgScore);
            //根据班级分组
            Map<String, List<StudentMakeResultModel>> classMap = value.stream().filter(resultModel -> groupIds.contains(resultModel.getClassCode())).collect(Collectors.groupingBy(StudentMakeResultModel::getClassCode));
            classMap.forEach((classKey, classValue)->{
                //班级平均标准分
                Double aveStandard = getAveStandard(avgScore, standardDeviation, classValue);
                ClassStandardModel classStandardModel =  new ClassStandardModel();
                classStandardModel.setAvgStandardScore(rounding(aveStandard,4));
                classStandardModel.setReleaseExamCode(key);
                classStandardModel.setClassCode(classKey);
                classStandardModel.setClassName(classValue.get(0).getClassName());
                classStandardModels.add(classStandardModel);
            });

        });
        standardScoreChangeModel.setClassStandardModels(classStandardModels);
        standardScoreChangeModel.setClassModels(classModels);
        standardScoreChangeModel.setExamModels(examModels.stream().sorted((o1, o2) -> o1.getExamCode()-o2.getExamCode()).collect(Collectors.toList()));
        return standardScoreChangeModel;
    }

    /**
     * 获取平均标准分
     * @param gradeAveScore 平均分
     * @param gradeDeviationScore 标准差
     * @param studentMakeResultModels 学生集合信息
     * @return
     */
    private Double getAveStandard(Double gradeAveScore, Double gradeDeviationScore, List<StudentMakeResultModel> studentMakeResultModels) {
        return studentMakeResultModels.stream().map(stu -> {
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
     * 获取教师班级成绩表
     *
     * @param teacherCode     教师code
     * @param subjectCode     学科code
     * @param releaseExamCode 发布考试记录code
     * @return
     * @throws Exception
     */
    @Override
    public List<ClassResultsTableModel> getClassResultsTable(String teacherCode, String subjectCode, String releaseExamCode) throws Exception {
        List<StudentMakeResultModel> studentSubjectScorePoList=studentSubjectScoreService.findByReleaseExamCode(releaseExamCode,teacherCode,subjectCode);
        return generateClassResultsTable(studentSubjectScorePoList);
    }

    /**
     * 获取教师班级成绩表 为前端定制
     *
     * @param teacherCode
     * @param subjectCode
     * @param releaseExamCode
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> getClassResultsTableForDaqiao(String teacherCode, String subjectCode, String releaseExamCode) throws Exception {
        List<StudentMakeResultModel> studentSubjectScorePoList=studentSubjectScoreService.findByReleaseExamCode(releaseExamCode,teacherCode,subjectCode);
        return generateClassResultsTableForDaqiao(studentSubjectScorePoList);
    }

    /**
     * 获取错题排行榜
     *
     * @param teacherCode     教师code
     * @param subjectCode     学科code
     * @param releaseExamCode 发布考试记录code
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, List<WrongQuestionRankModel>>  getClassWrongQuestionRank(String teacherCode, String subjectCode, String releaseExamCode) throws Exception {
        return userAnswerResultService.getWrongQuestionRank(releaseExamCode,teacherCode,subjectCode).stream().collect(Collectors.groupingBy(WrongQuestionRankModel::getClassName));
    }

    /**
     * 获取班级知识模块掌握
     *
     * @param teacherCode     教师code
     * @param subjectCode     学科code
     * @param releaseExamCode 发布考试记录code
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Double> getClassKnowledgeMoudle(String teacherCode, String subjectCode, String releaseExamCode) throws Exception {
        List<KnowledgeModuleModel> knowledgeModulePoList = knowledgeModuleService.findByReleaseExamCodeAndSubjectCodeAndTeacherCode(releaseExamCode, teacherCode, subjectCode);
        Map<String, Double> averagingDouble = knowledgeModulePoList.stream().collect(Collectors.groupingBy(KnowledgeModuleModel::getKnowledgeModuleName, Collectors.averagingDouble(KnowledgeModuleModel::getKnowledgeModuleScore)));
        return averagingDouble;
    }

    /**
     * 获取班级共性错误知识点
     *
     * @param teacherCode     教师code
     * @param subjectCode     学科code
     * @param releaseExamCode 发布考试记录code
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Integer> getClassWrongKnowledge(String teacherCode, String subjectCode, String releaseExamCode) throws Exception {
        List<KnowledgeModel> knowledgePoList = knowledgeService.findByReleaseExamCodeAndTeacherCodeAndSubjectCode(releaseExamCode, teacherCode, subjectCode);
        //学生总数量
        long count = knowledgePoList.stream().distinct().count();

        Map<String, List<KnowledgeModel>> collect = knowledgePoList.stream().filter(k -> k.getIsRight().equals(0)).collect(Collectors.groupingBy(KnowledgeModel::getKnowledgeName));
        Map<String,Integer> resultMap= Maps.newHashMap();
        for (Map.Entry<String, List<KnowledgeModel>> s : collect.entrySet()) {
            int wrongCount = s.getValue().size();
            int i = wrongCount / (int) count;
            //除不尽取值为1次
            if(i==0){
                i=1;
            }
            resultMap.put(s.getKey(),i);
        }
        return resultMap;
    }

    /**
     * 各班级错误知识点情况
     *
     * @param teacherCode     教师code
     * @param subjectCode     学科code
     * @param releaseExamCode 发布考试记录code
     * @return
     * @throws Exception
     */
    @Override
    public List<List<String>> getClassWrongKnowledgeGroupByClass(String teacherCode, String subjectCode, String releaseExamCode) throws Exception {
        List<KnowledgeModel> knowledgePoList = knowledgeService.findByReleaseExamCodeAndTeacherCodeAndSubjectCode(releaseExamCode, teacherCode, subjectCode);
        Map<String, List<KnowledgeModel>> collect = knowledgePoList.stream().collect(Collectors.groupingBy(KnowledgeModel::getClassName));

        Map<String, Map<String, List<KnowledgeModel>>> stringMapMap = knowledgePoList.stream().collect(
                Collectors.groupingBy(KnowledgeModel::getClassName,
                        Collectors.groupingBy(KnowledgeModel::getKnowledgeName)));
        Multimap<String,String> wrongKnowName=ArrayListMultimap.create();

        for (Map.Entry<String, Map<String, List<KnowledgeModel>>> stringMapEntry : stringMapMap.entrySet()) {
            String className=stringMapEntry.getKey();
            wrongKnowName.put("班级",className);
            long studentCount = collect.get(className).stream().distinct().count();
            for (Map.Entry<String,List<KnowledgeModel>> knowledgeNameEntry : stringMapEntry.getValue().entrySet()) {
                //错误知识点数量
                long  wrongCount = stringMapEntry.getValue().get(knowledgeNameEntry.getKey()).stream().filter(k->k.getIsRight().equals(0)).count();
                //班级下每个知识点平均错误数量
                //long avgWrongCount = wrongCount/studentCount;
                BigDecimal divide = new BigDecimal(wrongCount).divide(new BigDecimal(studentCount),1,RoundingMode.HALF_UP);
                wrongKnowName.put(knowledgeNameEntry.getKey(),rounding(divide.doubleValue(),1)+"");
            }
        }

        List<List<String>> resultStr=Lists.newArrayList();
        for (String s : wrongKnowName.asMap().keySet()) {
            List<String> str= Lists.newArrayList();
            str.add(s);
            wrongKnowName.asMap().get(s).forEach(val->{
                str.add(val);
            });
            if(("班级").equals(s)){
                resultStr.add(0,str);
            }else{
                resultStr.add(str);
            }
        }
        return  resultStr;
    }

    /**
     * 获取班级平均能力
     *
     * @param teacherCode     教师code
     * @param subjectCode     学科code
     * @param releaseExamCode 发布考试记录code
     * @return //0 记忆能力  1理解能力 2应用能力 3分析能力 4 综合能力
     * @throws Exception
     */
    @Override
    public Map<String, Map<String, Double>> getClassAbilityScore(String teacherCode, String subjectCode, String releaseExamCode) throws Exception {
        List<SubjectAbilityModel> subjectAbilityModels = subjectAbilityService.getByReleaseExamCodeAndTeacherCodeAndSubjectCode(releaseExamCode, teacherCode, subjectCode,new ArrayList<>(),null,null,null);
        Map<String, Map<String, Double>> collect = subjectAbilityModels.stream().collect(Collectors.groupingBy(SubjectAbilityModel::getClassName,
                Collectors.groupingBy(SubjectAbilityModel::getAbilityName, Collectors.averagingDouble(SubjectAbilityModel::getAbilityScore))));
        for (Map.Entry<String, Map<String, Double>> mapEntry : collect.entrySet()) {
            for (Map.Entry<String,Double> stringDoubleEntry : mapEntry.getValue().entrySet()) {
                mapEntry.getValue().put(stringDoubleEntry.getKey(),rounding(stringDoubleEntry.getValue(),2));
            }
        }
        return  collect;
    }

    /**
     * 获取单个能力历史平均
     *
     * @param teacherCode     教师code
     * @param subjectCode     学科code
     * @param releaseExamCode 发布考试记录code
     * @return //0 记忆能力  1理解能力 2应用能力 3分析能力 4 综合能力
     * @throws Exception
     */
    @Override
    public Map<String, Map<String, Map<Long, Double>>> getClassSingleAbilityScore(String teacherCode, String subjectCode, String releaseExamCode) throws Exception {
        ReleaseExamPo releaseExamPo = getReleaseExamPo(releaseExamCode);
        List<String> examTyes=Lists.newArrayList();
        examTyes.add("0");
//        examTyes.add("2");
//        examTyes.add("3");
//        examTyes.add("4");
        List<AuthGroupBean> classLists = userManagerService.getClassByTeacherId(Integer.valueOf(teacherCode));
        List<String> groupIds = classLists.stream().map(group -> group.getGroupId().toString()).collect(Collectors.toList());
        List<SubjectAbilityModel> historySubjectCodeAndExamTypeAndSemester = subjectAbilityService.getBySubjectCodeAndExamTypeAndSemesterAndTeacherCode(subjectCode, examTyes, releaseExamPo.getSemester(),teacherCode);
        //List<SubjectAbilityModel> subjectAbilityModels = subjectAbilityService.getByReleaseExamCodeAndTeacherCodeAndSubjectCode(releaseExamCode, teacherCode, subjectCode,new ArrayList<>(),null);

//        //0 记忆能力  1理解能力 2应用能力 3分析能力 4 综合能力
//        Map<String, Map<String, Map<Timestamp, Double>>> abilityMap = historySubjectCodeAndExamTypeAndSemester.stream().
//                collect(Collectors.groupingBy(SubjectAbilityModel::getAbilityCode,
//                        Collectors.groupingBy(SubjectAbilityModel::getClassName,
//                                Collectors.groupingBy(SubjectAbilityModel::getReleaseExamCreateTime,
//                                        Collectors.averagingDouble(SubjectAbilityModel::getAbilityScore)))));

        Map<String, Map<String, Map<Long, Double>>> resultMap=new HashMap<>();
        //根据能力code分组
        Map<String, List<SubjectAbilityModel>> collect = historySubjectCodeAndExamTypeAndSemester.stream().filter(subjectAbilityModel -> groupIds.contains(subjectAbilityModel.getClassCode())).collect(Collectors.groupingBy(SubjectAbilityModel::getAbilityCode));
        for (Map.Entry<String, List<SubjectAbilityModel>> abilityCodeEntry : collect.entrySet()) {
            Map<String, List<SubjectAbilityModel>> classMap = abilityCodeEntry.getValue().stream().collect(Collectors.groupingBy(SubjectAbilityModel::getClassName));
            Map<String, Map<Long, Double>> cMap=new HashMap<>();
            for (Map.Entry<String, List<SubjectAbilityModel>> classEntry : classMap.entrySet()) {
                Map<Timestamp, List<SubjectAbilityModel>> timeMap = classEntry.getValue().stream().collect(Collectors.groupingBy(SubjectAbilityModel::getReleaseExamCreateTime));
                Map<Long, Double> lMap=new HashMap<>();
                for (Map.Entry<Timestamp, List<SubjectAbilityModel>> timestampListEntry : timeMap.entrySet()) {
                    Double aveDouble = timestampListEntry.getValue().stream().collect(Collectors.averagingDouble(SubjectAbilityModel::getAbilityScore));
                    lMap.put(timestampListEntry.getKey().getTime(),rounding(aveDouble,2));
                }
                cMap.put(classEntry.getKey(),lMap);
            }
            resultMap.put(abilityCodeEntry.getKey(),cMap);
        }
    
        Map<String, Map<String, Map<Long, Double>>> abilityMapResult=new HashMap<>();


        for (Map.Entry<String, Map<String, Map<Long, Double>>> stringMapEntry : resultMap.entrySet()) {
            Map<String, Map<Long, Double>> classNameMap=new HashMap<>();
            for (Map.Entry<String, Map<Long, Double>> mapEntry : stringMapEntry.getValue().entrySet()) {
                Map<Long, Double> map = new TreeMap<Long, Double>(
                                    (obj1, obj2) -> {
                                        // 降序排序
                                        return obj2.compareTo(obj1);
                                    });
                map.putAll(mapEntry.getValue());
                classNameMap.put(mapEntry.getKey(),map);
            }
            abilityMapResult.put(stringMapEntry.getKey(),classNameMap);
        }
        return abilityMapResult;
    }

    private ReleaseExamPo getReleaseExamPo(String releaseExamCode) {
        ReleaseExamPo releaseExamPo = releaseExamService.findByReleaseExamCode(releaseExamCode);
        if(releaseExamPo==null){
            throw new ReportException("考试发布记录无效："+releaseExamCode);
        }
        return releaseExamPo;
    }

    /**
     * 学科能力平均值
     *
     * @param teacherCode     教师code
     * @param subjectCode     学科code
     * @param releaseExamCode 发布考试记录code
     * @return //0 记忆能力  1理解能力 2应用能力 3分析能力 4 综合能力
     * @throws Exception
     */
    @Override
    public Map<String, Map<Long, Double>> getClassSubjectAverageScore(String teacherCode, String subjectCode, String releaseExamCode) throws Exception {
        ReleaseExamPo releaseExamPo = getReleaseExamPo(releaseExamCode);
        List<String> examTyes=Lists.newArrayList();
        examTyes.add("0");
//        examTyes.add("2");
//        examTyes.add("3");
//        examTyes.add("4");
        List<AuthGroupBean> classLists = userManagerService.getClassByTeacherId(Integer.valueOf(teacherCode));
        List<String> groupIds = classLists.stream().map(group -> group.getGroupId().toString()).collect(Collectors.toList());
        List<SubjectAbilityModel> historySubjectCodeAndExamTypeAndSemester = subjectAbilityService.getBySubjectCodeAndExamTypeAndSemesterAndTeacherCode(subjectCode, examTyes, releaseExamPo.getSemester(),teacherCode);
        //List<SubjectAbilityModel> subjectAbilityModels = subjectAbilityService.getByReleaseExamCodeAndTeacherCodeAndSubjectCode(releaseExamCode, teacherCode, subjectCode,new ArrayList<>(),null);
//        Map<String, Map<Timestamp, Double>> resultMap = historySubjectCodeAndExamTypeAndSemester.stream().collect(Collectors.groupingBy(SubjectAbilityModel::getClassName,
//                Collectors.groupingBy(SubjectAbilityModel::getReleaseExamCreateTime,
//                        Collectors.averagingDouble(SubjectAbilityModel::getAbilityScore))));

        //班级分组
        Map<String,Map<Long,Double>> resultMap=new HashMap<>();
        Map<String, List<SubjectAbilityModel>> classCllect = historySubjectCodeAndExamTypeAndSemester.stream().filter(subjectAbilityModel -> groupIds.contains(subjectAbilityModel.getClassCode())).collect(Collectors.groupingBy(SubjectAbilityModel::getClassName));
        for (Map.Entry<String, List<SubjectAbilityModel>> classListEntry : classCllect.entrySet()) {
            Map<Long,Double> longMap=new HashMap<>();
            Map<Timestamp, List<SubjectAbilityModel>> timeMap = classListEntry.getValue().stream().collect(Collectors.groupingBy(SubjectAbilityModel::getReleaseExamCreateTime));
            for (Map.Entry<Timestamp, List<SubjectAbilityModel>> timestampListEntry : timeMap.entrySet()) {
                Double aveDouble = timestampListEntry.getValue().stream().collect(Collectors.averagingDouble(SubjectAbilityModel::getAbilityScore));
                longMap.put(timestampListEntry.getKey().getTime(),rounding(aveDouble,2));
            }
            resultMap.put(classListEntry.getKey(),longMap);
        }
        //排序
        Map<String, Map<Long, Double>> newResultMap = new HashMap<>();
        for (Map.Entry<String, Map<Long, Double>> stringMapEntry : resultMap.entrySet()) {
            Map<Long, Double> map = new TreeMap<Long, Double>(
                                (obj1, obj2) -> {
                                    // 降序排序
                                    return obj2.compareTo(obj1);
                                });
            map.putAll(stringMapEntry.getValue());
            newResultMap.put(stringMapEntry.getKey(),map);
        }
        return newResultMap;
    }

    /**
     * 获取班级合格率
     *
     * @param teacherCode     教师code
     * @param subjectCode     学科code
     * @param releaseExamCode 发布考试记录code
     * @return
     */
    @Override
    public List<PassRateModel> getClassQualified(String teacherCode, String subjectCode, String releaseExamCode) throws Exception {
        List<StudentMakeResultModel> studentSubjectScorePoList=studentSubjectScoreService.findByReleaseExamCode(releaseExamCode,teacherCode,subjectCode);
        Map<String, Map<String, List<StudentMakeResultModel>>> listMap = studentSubjectScorePoList.stream().collect(Collectors.groupingBy(
                StudentMakeResultModel::getClassName, Collectors.groupingBy(
                        stu -> {
                            if (stu.getScore() >= stu.getPaperScore() * .6 && stu.getScore() < stu.getPaperScore() * .8) {
                                return "jige";
                            }
                            if (stu.getScore() >= stu.getPaperScore() * .8) {
                                return "youxiu";
                            }
                            return "bujige";
                        })));
        List<PassRateModel> passRateModelList= Lists.newArrayList();
        for (Map.Entry<String, Map<String, List<StudentMakeResultModel>>> s : listMap.entrySet()) {
            PassRateModel passRateModel=new PassRateModel();
            int bujigeCount = s.getValue().get("bujige") == null ? 0 : s.getValue().get("bujige").size();
            int youxiuCount = s.getValue().get("youxiu") == null ? 0 : s.getValue().get("youxiu").size();
            int jigeCount = s.getValue().get("jige") == null ? 0 : s.getValue().get("jige").size();
            passRateModel.setClassName(s.getKey());
            passRateModel.setBujige(bujigeCount);
            passRateModel.setJige(jigeCount+youxiuCount);
            passRateModel.setYouxiu(youxiuCount);
            passRateModel.setTotalCount(bujigeCount+youxiuCount+jigeCount);
            passRateModelList.add(passRateModel);

        }
        return passRateModelList;
    }

    /**
     * 获取班级达标率
     *
     * @param teacherCode     教师code
     * @param subjectCode     学科code
     * @param releaseExamCode 发布考试记录code
     * @return
     * @throws Exception
     */
    @Override
    public HashMap<String, HashMap<String, Object>> getClassStandard(String teacherCode, String subjectCode, String releaseExamCode) throws Exception {

        List<StudentMakeResultModel> studentSubjectScorePoList=studentSubjectScoreService.findByReleaseExamCode(releaseExamCode,teacherCode,subjectCode);
        if (!studentSubjectScorePoList.isEmpty()){
            //卷面达标分
            Double standardScore=studentSubjectScorePoList.get(0).getStandardScore();
            Map<String, Map<String, Long>> resultMap = studentSubjectScorePoList.stream().collect(
                    Collectors.groupingBy(StudentMakeResultModel::getClassName,
                            Collectors.groupingBy(stu -> {
                                if (stu.getScore() >= standardScore) {
                                    return "standardCount";
                                } else {
                                    return "unStandardCount";
                                }

                            }, Collectors.counting())));
            //达标率结果
            HashMap standardResult=Maps.newHashMap();
            for (Map.Entry<String, Map<String, Long>> key : resultMap.entrySet()) {
                //达标率集合
                Map standardMap=Maps.newHashMap();
                long standardCount = key.getValue().get("standardCount") == null ? 0L : key.getValue().get("standardCount");
                long unStandardCount = key.getValue().get("unStandardCount") == null ? 0L : key.getValue().get("unStandardCount");
                Double total=new BigDecimal(standardCount).add(new BigDecimal(unStandardCount)).doubleValue();
                standardMap.put("standard",new BigDecimal(new BigDecimal(standardCount).doubleValue()/total*100).setScale(1, RoundingMode.HALF_UP).toString());
                standardMap.put("unStandardCount",new BigDecimal(new BigDecimal(unStandardCount).doubleValue()/total*100).setScale(1, RoundingMode.HALF_UP).toString());
                standardResult.put(key.getKey(),standardMap);
            }
            return standardResult;
        }
        return null;
    }

    public  Map<String,Object>  generateClassResultsTableForDaqiao(List<StudentMakeResultModel> studentSubjectScorePoList) throws Exception {
        Map<String,Object> listHashMap= Maps.newHashMap();
        if(!studentSubjectScorePoList.isEmpty()){
            Map<String, List<StudentMakeResultModel>> classNameMap = studentSubjectScorePoList.stream().collect(Collectors.groupingBy(StudentMakeResultModel::getClassName));

            Double paperScore=studentSubjectScorePoList.get(0).getPaperScore();
            //按班级分组 获取 班级平均分 班级最高分 班级最低分 班级成绩总分  个数
            Map<String, DoubleSummaryStatistics> doubleSummary = studentSubjectScorePoList.stream().collect(Collectors.groupingBy(
                    StudentMakeResultModel::getClassName,
                    Collectors.summarizingDouble(StudentMakeResultModel::getScore)));

            //统计优秀 及格 不及格学生分类
            Map<String, Map<String, List<StudentMakeResultModel>>> listMap = studentSubjectScorePoList.stream().collect(Collectors.groupingBy(
                    StudentMakeResultModel::getClassName, Collectors.groupingBy(
                            stu -> {
                                if (stu.getScore() >= stu.getPaperScore() * .6 &&stu.getScore() < stu.getPaperScore()*.8) {
                                    return "jige";
                                }
                                if (stu.getScore() >= stu.getPaperScore() * .8) {
                                    return "youxiu";
                                }
                                return "bujige";
                            })));

            //班级人数
            Map<String, Long> classStudentCount = studentSubjectScorePoList.stream().collect(Collectors.groupingBy(StudentMakeResultModel::getClassName, Collectors.counting()));
            //班级均分段
            Map<String,Map<String,Double>> classAvgScore = getClassAvgScore(classStudentCount, studentSubjectScorePoList);


            Multimap<String,String> stringMultimap=ArrayListMultimap.create();
            Multimap<String,Double> avgScoreMultimap=ArrayListMultimap.create();

            for (Map.Entry<String, Map<String, List<StudentMakeResultModel>>> className : listMap.entrySet()) {
                for (Map.Entry<String,Double> stringDoubleEntry : classAvgScore.get(className.getKey()).entrySet()) {
                    avgScoreMultimap.put(stringDoubleEntry.getKey(),stringDoubleEntry.getValue());
                }
                BigDecimal youxiuCount = new BigDecimal(className.getValue().get("youxiu") == null ? 0 : className.getValue().get("youxiu").size());
                //及格率
                BigDecimal jigeCount = new BigDecimal(className.getValue().get("jige") == null ? 0 : className.getValue().get("jige").size());
                BigDecimal classStudentCountBigDecimal = new BigDecimal(classStudentCount.get(className.getKey()));
                BigDecimal jigelv = new BigDecimal((jigeCount.doubleValue() + youxiuCount.doubleValue()) / classStudentCountBigDecimal.doubleValue() * 100).setScale(1, RoundingMode.HALF_UP);//jigeCount.divide(new BigDecimal(classStudentCount.get(className)),3).multiply(new BigDecimal(100)).setScale(1, RoundingMode.HALF_UP);
                //优秀率
                BigDecimal youxiulv = new BigDecimal(youxiuCount.doubleValue() / classStudentCountBigDecimal.doubleValue() * 100).setScale(1, RoundingMode.HALF_UP);
                //汇总
                stringMultimap.put("1_", className.getKey() );
                stringMultimap.put("2_平均分",rounding(doubleSummary.get(className.getKey()).getAverage(),2)+"");
                stringMultimap.put("3_标准差", getStandardDeviation(classNameMap.get(className.getKey()),doubleSummary.get(className.getKey()).getAverage()).toString());

                stringMultimap.put("4_最高分", doubleSummary.get(className.getKey()).getMax() + "");
                stringMultimap.put("5_最低分", doubleSummary.get(className.getKey()).getMin() + "");
                //stringMultimap.put("4_总分", doubleSummary.get(className.getKey()).getSum() + "");
                stringMultimap.put("6_及格率",rounding(jigelv.doubleValue(),2)+"%");
                stringMultimap.put("7_优秀率",rounding(youxiulv.doubleValue(),2) + "%");
                stringMultimap.put("8_0--" + (int)(paperScore * 0.6),className.getValue().get("bujige") == null ? 0 + "" : className.getValue().get("bujige").size() + "");
                stringMultimap.put("9_"+(int)(paperScore * 0.6)+ "--" + (int)(paperScore * 0.8),className.getValue().get("jige") == null ? 0 + "" : className.getValue().get("jige").size() + "");
                stringMultimap.put("10_"+(int)(paperScore * 0.8) + "--" + (int)(paperScore*1),className.getValue().get("youxiu") == null ? 0 + "" : className.getValue().get("youxiu").size() + "");
            }
            Map<String, Collection<String>> stringCollectionMap = stringMultimap.asMap();
            List<List<String>> stringCollectionList=Lists.newArrayList();
            for (Map.Entry<String, Collection<String>> string : stringCollectionMap.entrySet()) {
                List<String> tempStrings=Lists.newArrayList();
                tempStrings.add(string.getKey()+"");
                for (String str : string.getValue()) {
                    tempStrings.add(str+"");
                }
                stringCollectionList.add(tempStrings);
            }
            stringCollectionList=stringCollectionList.stream().sorted(new Comparator<List<String>>() {
                @Override
                public int compare(List<String> o1, List<String> o2) {
                    int i1 = Integer.parseInt(o1.get(0).split("_")[0]);
                    int i2 = Integer.parseInt(o2.get(0).split("_")[0]);
                    return i1-i2;
                }
            }).collect(Collectors.toList());

            List<List<String>> avgScoreTimes=Lists.newArrayList();
            for (Map.Entry<String, Collection<Double>> aLong : avgScoreMultimap.asMap().entrySet()) {
                List<String> tempStrings=Lists.newArrayList();
                tempStrings.add(aLong.getKey()+"");
                for (Double aDouble : aLong.getValue()) {
                    tempStrings.add(rounding(aDouble,2)+"");
                }
                avgScoreTimes.add(tempStrings);
            }
            avgScoreTimes=avgScoreTimes.stream().sorted((o1, o2) -> {
                int i1 = Integer.parseInt(o1.get(0).split("_")[0]);
                int i2 = Integer.parseInt(o2.get(0).split("_")[0]);
                return i1-i2;
            }).collect(Collectors.toList());
            avgScoreTimes.stream().forEach(strings -> {
                String s = strings.get(0).split("_")[1];
                strings.remove(0);
                strings.add(0,s);
            });
            stringCollectionList.stream().forEach(strings -> {
                String[] split = strings.get(0).split("_");
                if(split.length>1){
                    String s = split[1];
                    strings.remove(0);
                    strings.add(0,s);
                }else{
                    strings.remove(0);
                    strings.add(0,"");
                }

            });
            listHashMap.put("avgScoreTimes",avgScoreTimes);
            listHashMap.put("top",stringCollectionList);
            listHashMap.put("paperScore",paperScore);
        }
        return listHashMap;
    }

    private Map<String,Map<String,Double>> getClassAvgScore(Map<String, Long> classStudentCount,List<StudentMakeResultModel> studentSubjectScorePoList) {
        Map avgMap= Maps.newHashMap();
        List<Long> classStudentCounts=Lists.newArrayList();
        for (Map.Entry<String,Long> stringLongEntry : classStudentCount.entrySet()) {
            classStudentCounts.add(stringLongEntry.getValue());
        }
        //获取班级中最多人数班级的人数
        Long maxStudentCount = classStudentCounts.stream().max(Long::compareTo).get();
        Map<String, List<StudentMakeResultModel>> classNameStudents = studentSubjectScorePoList.stream().collect(Collectors.groupingBy(StudentMakeResultModel::getClassName));
        //计算统计均分段
        //获取班级中最多人数班级的人数
        List<Long> longList = CountUtils.getScoreTimes(maxStudentCount);
        for (Map.Entry<String,List<StudentMakeResultModel>> classNameStudentsEntry : classNameStudents.entrySet()) {
            Map<String,Double> classNameAvgScore= Maps.newHashMap();
            List<StudentMakeResultModel> collect = classNameStudentsEntry.getValue().stream().sorted((s1, s2) -> {
                double result = s2.getScore() - s1.getScore();
                if (result == 0) {
                    return 0;
                } else if (result > 0) {
                    return 1;
                } else {
                    return -1;
                }
            }).collect(Collectors.toList());
            int temp=collect.size();
            int init=1;
            int shengyu = collect.size() % 10;
            for (int i = 0; i < longList.size(); i++) {
                if(temp <= longList.get(i)){
                    if(init==1){
                        classNameAvgScore.put(i+"_"+"0--"+longList.get(i),collect.stream().collect(Collectors.averagingDouble(StudentMakeResultModel::getScore)));
                        init++;
                    }else  {
                        if((shengyu+Math.abs(temp-longList.get(i)))==10){
                            classNameAvgScore.put(i+"_"+longList.get(i-1)+"--"+longList.get(i),collect.subList(Math.toIntExact(longList.get(i - 1)),Math.toIntExact(longList.get(i-1))+shengyu).stream().collect(Collectors.averagingDouble(StudentMakeResultModel::getScore)));
                        }else {
                            classNameAvgScore.put(i+"_"+longList.get(i-1)+"--"+longList.get(i),0D);
                        }
                    }
                }
                if(temp>longList.get(i)){
                    if(init==1){
                        classNameAvgScore.put(i+"_"+"0--"+longList.get(i),collect.subList(0, Math.toIntExact(longList.get(i))).stream().collect(Collectors.averagingDouble(StudentMakeResultModel::getScore)));
                        init++;
                    }else {
                        classNameAvgScore.put(i+"_"+longList.get(i-1)+"--"+longList.get(i),collect.subList(Math.toIntExact(longList.get(i - 1)), Math.toIntExact(longList.get(i))).stream().collect(Collectors.averagingDouble(StudentMakeResultModel::getScore)));
                    }
                }
            }
            avgMap.put(classNameStudentsEntry.getKey(),classNameAvgScore);
        }
        return avgMap;
    }

    /**
         * 生成班级成绩表
         */
    public List<ClassResultsTableModel> generateClassResultsTable(List<StudentMakeResultModel> studentSubjectScorePoList) throws Exception{
        if(!studentSubjectScorePoList.isEmpty()){
            Double paperScore=studentSubjectScorePoList.get(0).getPaperScore();
            //按班级分组 获取 班级平均分 班级最高分 班级最低分 班级成绩总分  个数
            Map<String, DoubleSummaryStatistics> doubleSummary = studentSubjectScorePoList.stream().collect(Collectors.groupingBy(
                    StudentMakeResultModel::getClassName,
                    Collectors.summarizingDouble(StudentMakeResultModel::getScore)));
            //统计优秀 及格 不及格学生分类
            Map<String, Map<String, List<StudentMakeResultModel>>> listMap = studentSubjectScorePoList.stream().collect(Collectors.groupingBy(
                    StudentMakeResultModel::getClassName, Collectors.groupingBy(
                            stu -> {
                                if (stu.getScore() >= stu.getPaperScore() * .6) {
                                    return "jige";
                                }
                                if (stu.getScore() >= stu.getPaperScore() * .8) {
                                    return "youxiu";
                                }
                                return "bujige";
                            })));

            //班级人数
            Map<String, Long> classStudentCount = studentSubjectScorePoList.stream().collect(Collectors.groupingBy(StudentMakeResultModel::getClassName, Collectors.counting()));

            List<ClassResultsTableModel> c= Lists.newArrayList();
            for (Map.Entry<String, Map<String, List<StudentMakeResultModel>>> s : listMap.entrySet()) {
                if (doubleSummary.containsKey(s.getKey())) {
                    ClassResultsTableModel cs = new ClassResultsTableModel();
                    Map scoreMap = Maps.newHashMap();
                    cs.setClassName(s.getKey());
                    cs.setCount(doubleSummary.get(s.getKey()).getCount());
                    cs.setMax(doubleSummary.get(s.getKey()).getMax());
                    cs.setMin(doubleSummary.get(s.getKey()).getMin());
                    cs.setSum(doubleSummary.get(s.getKey()).getSum());
                    cs.setClassAverage(new BigDecimal(doubleSummary.get(s.getKey()).getAverage()).setScale(1,RoundingMode.HALF_UP).doubleValue());
                    cs.setSubjectCount(s.getValue());
                    scoreMap.put("0--" + paperScore * 0.6, s.getValue().get("bujige") == null ? 0 : s.getValue().get("bujige").size());
                    scoreMap.put(paperScore * 0.6 + "--" + paperScore * 0.8, s.getValue().get("jige") == null ? 0 : s.getValue().get("jige").size());
                    scoreMap.put(paperScore * 0.8 + "--" + paperScore, s.getValue().get("youxiu") == null ? 0 : s.getValue().get("youxiu").size());
                    cs.setScoreCount(scoreMap);
                    //总学生数量
                    Long totalStudent = classStudentCount.get(s.getKey());
                    Map rankingAverageMap= Maps.newHashMap();
                    List<Long> times = CountUtils.getScoreTimes(totalStudent);
                    for (Long integer : times) {
                        //平均分
                        Double averageScore = studentSubjectScorePoList.stream().filter(stu -> stu.getClassName().equals(s.getKey())).limit(integer).collect(Collectors.averagingDouble(StudentMakeResultModel::getScore));
                        rankingAverageMap.put("前"+integer+"名均分",new BigDecimal(averageScore).setScale(1,RoundingMode.HALF_UP).doubleValue());
                    }
                    cs.setRankingAverage(rankingAverageMap);
                    c.add(cs);
                }

            }
            return  c;
        }
       return null;
    }


    /**
     * 获取单个班级班级概况报告
     *
     * @param teacherCode     教师code
     * @param subjectCode     学科code
     * @param releaseExamCode 考试发布记录code
     * @param classCode       班级code
     * @return
     * @throws Exception
     */
    @Override
    public SingleClassReportModel getSingleClassReport(String teacherCode, String subjectCode, String releaseExamCode, String classCode) throws Exception {
        SingleClassReportModel singleClassReportModel=new SingleClassReportModel();
        //参加考试的学生列表
        //List<StudentMakeResultModel> studentMakeResultModels = studentSubjectScoreService.findByReleaseExamCodeAndTeacherCodeAndSubjectCodeAndClassCode(releaseExamCode, teacherCode, subjectCode, classCode);
        List<StudentMakeResultModel> studentMakeResultModels = studentSubjectScoreService.findByReleaseExamCodeAndSubjectCodeAndClassCode(releaseExamCode, subjectCode, classCode);

        DiagnosisRecordStudentDto diagnosisRecordStudentDto=new DiagnosisRecordStudentDto();
        diagnosisRecordStudentDto.setDiagnosisStatus(0);
        diagnosisRecordStudentDto.setSubjectCode(Integer.valueOf(subjectCode));
        diagnosisRecordStudentDto.setClassCode(Integer.valueOf(classCode));
        diagnosisRecordStudentDto.setDiagnosisTeacherRecordCode(releaseExamCode);
        //获取未参加考试的学生列表
        List<DiagnosisRecordStudentDto> notAttendExamStudents = diagnosisRecordStudentOpenService.getAll(diagnosisRecordStudentDto, null);

        //未参加考试学生
        List<StudentScoreModel> notAttendedStudentScoreModels=Lists.newArrayList();
        notAttendExamStudents.stream().forEach(dto ->{
            StudentScoreModel studentScoreModel=new StudentScoreModel();
            studentScoreModel.setScore(0D);
            studentScoreModel.setStudentCode(dto.getStudentCode().toString());
            studentScoreModel.setStudentName(dto.getStudentName());
            notAttendedStudentScoreModels.add(studentScoreModel);
        } );

       if(studentMakeResultModels.isEmpty()){
           singleClassReportModel.setNotAttendedStudentScoreModels(notAttendedStudentScoreModels);
           return singleClassReportModel;
       }
        //达标分
        Double standardScore=studentMakeResultModels.get(0).getStandardScore();
        //试卷分
        Double paperTotalScore=studentMakeResultModels.get(0).getPaperScore();
        String className = studentMakeResultModels.get(0).getClassName();
        //达标数量
        long standardCount = studentMakeResultModels.stream().filter(studentMakeResultModel -> studentMakeResultModel.getScore() >= standardScore).count();
        //未达标数量
        long unStandardCount = studentMakeResultModels.stream().filter(studentMakeResultModel -> studentMakeResultModel.getScore() < standardScore).count();
        //合格数量
        long qualifiedCount = studentMakeResultModels.stream().filter(studentMakeResultModel -> studentMakeResultModel.getScore() >= 0.6 * paperTotalScore).count();
        //不合格数量
        long unQualifiedCount = studentMakeResultModels.stream().filter(studentMakeResultModel -> studentMakeResultModel.getScore() < 0.6 * paperTotalScore).count();
        //优秀数量
        long excellentCount = studentMakeResultModels.stream().filter(studentMakeResultModel -> studentMakeResultModel.getScore() >= 0.8 * paperTotalScore).count();

        DoubleSummaryStatistics summaryStatistics = studentMakeResultModels.stream().collect(Collectors.summarizingDouble(StudentMakeResultModel::getScore));
        singleClassReportModel.setAvgScore(rounding(summaryStatistics.getAverage(),2));
        singleClassReportModel.setMaxScore(summaryStatistics.getMax());
        singleClassReportModel.setMinScore(summaryStatistics.getMin());
        singleClassReportModel.setPaperTotalScore(paperTotalScore);
        Double standardDeviation = getStandardDeviation(studentMakeResultModels, summaryStatistics.getAverage());
        singleClassReportModel.setStandard(getMathResult(studentMakeResultModels, standardCount));
        singleClassReportModel.setNotStandard(getMathResult(studentMakeResultModels, unStandardCount));
        singleClassReportModel.setExcellent(getMathResult(studentMakeResultModels, excellentCount));
        singleClassReportModel.setQualified(getMathResult(studentMakeResultModels, qualifiedCount));
        singleClassReportModel.setUnQualified(getMathResult(studentMakeResultModels, unQualifiedCount));
        singleClassReportModel.setStandardDeviation(ReportMathUtils.rounding(standardDeviation,4));
        singleClassReportModel.setClassName(className);


        //参加考试学生
        List<StudentScoreModel> studentScoreModels=Lists.newArrayList();
        studentMakeResultModels.stream().sorted((o1, o2) -> o2.getScore().compareTo(o1.getScore())).forEach(stu->{
            StudentScoreModel studentScoreModel=new StudentScoreModel();
            studentScoreModel.setScore(stu.getScore());
            studentScoreModel.setStudentName(stu.getUserName());
            studentScoreModel.setStudentCode(stu.getUserCode());
            studentScoreModels.add(studentScoreModel);
        });
        singleClassReportModel.setNotAttendedStudentScoreModels(notAttendedStudentScoreModels);
        singleClassReportModel.setStudentScoreModels(studentScoreModels);

        return singleClassReportModel;
    }

    private double getMathResult(List<StudentMakeResultModel> studentMakeResultModels, long count) {
        return (new BigDecimal(count).divide(new BigDecimal(studentMakeResultModels.size()),3, RoundingMode.HALF_UP)).multiply(new BigDecimal(100)).doubleValue();
    }

    /**
     * 计算标准差
     * @param studentMakeResultModels
     * @param avg
     * @return
     */
    private Double getStandardDeviation(List<StudentMakeResultModel> studentMakeResultModels, Double avg) {
        List<Double> sumDouble= Lists.newArrayList();
        //计算平均分差值 平方
        studentMakeResultModels.stream().forEach(studentMakeResultModel -> {
            BigDecimal subtractScore = new BigDecimal(studentMakeResultModel.getScore()).subtract(new BigDecimal(avg));
            Double pow = subtractScore.pow(2).setScale(2, RoundingMode.HALF_UP).doubleValue();
            sumDouble.add(pow);
        });

        Double powSumScore = sumDouble.stream().collect(Collectors.summingDouble(powScore -> powScore));
        BigDecimal divideScore = new BigDecimal(powSumScore).divide(new BigDecimal(sumDouble.size()),2,RoundingMode.HALF_UP);
        return new BigDecimal(Math.sqrt(divideScore.doubleValue())).setScale(2, RoundingMode.HALF_UP).doubleValue();
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

}
