package com.eeduspace.report.service.impl;

import com.eeduspace.b2b.report.model.report.KnowledgeModuleModel;
import com.eeduspace.b2b.report.model.report.StudentMakeResultModel;
import com.eeduspace.report.model.ClassResultsTableModel;
import com.eeduspace.report.model.KnowledgeModel;
import com.eeduspace.report.model.PassRateModel;
import com.eeduspace.report.model.SubjectAbilityModel;
import com.eeduspace.report.service.*;
import com.eeduspace.report.util.CountUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 教师报告业务
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-03-18 12:15
 **/
@Service
public class TeacherReportServiceImpl implements TeacherReportService {
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
    /**
     * 生成教师报告
     *@param teacherCode 教师code
     * @param releaseExamCode 发布考试记录code
     */
    @Override
    public void generateTeacherReport(String releaseExamCode,String teacherCode,String subjectCode) throws Exception{
        List<StudentMakeResultModel> studentSubjectScorePoList=studentSubjectScoreService.findByReleaseExamCode(releaseExamCode,teacherCode,subjectCode);
        List<SubjectAbilityModel> subjectAbilityModels = subjectAbilityService.getByReleaseExamCodeAndTeacherCodeAndSubjectCode(releaseExamCode, teacherCode, subjectCode,new ArrayList<>(),null,null,null);
        List<KnowledgeModel> knowledgePoList = knowledgeService.findByReleaseExamCodeAndTeacherCodeAndSubjectCode(releaseExamCode, teacherCode, subjectCode);
        //班级成绩表 按班级区分
        generateClassResultsTable(studentSubjectScorePoList);
        //班级合格率 按班级区分
        generateClassQualified(studentSubjectScorePoList);
        //班级达标 按班级区分
        generateClassStandard(studentSubjectScorePoList);
        //班级错题排行榜 按班级区分
        generateClassWrongQuestionRank(releaseExamCode,teacherCode,subjectCode);
        //班级共性错误知识点模块 全部参加的班级
        generateClassKnowledgeMoudle(releaseExamCode,teacherCode,subjectCode);
        //班级共性错误知识点  全部参加的班级
        generateClassWrongKnowledge(knowledgePoList);
        //班级错误知识点 按照班级区分
        generateClassWrongKnowledgeGroupByClass(knowledgePoList);
        //班级所有能力得分 按照班级区分
        generateClassAbilityScore(subjectAbilityModels);
        //班级单科能力得分 按照班级 学科
        generateClassSingleAbilityScore(subjectAbilityModels);
        //获取学科能力的平均分 包含历史所有  按照班级区分
        generateClassSubjectAverageScore(subjectAbilityModels);

    }

    private void generateClassSubjectAverageScore(List<SubjectAbilityModel> subjectAbilityModels) {
        Map<String, Map<Timestamp, Double>> resultMap = subjectAbilityModels.stream().collect(Collectors.groupingBy(SubjectAbilityModel::getClassName,
                Collectors.groupingBy(SubjectAbilityModel::getReleaseExamCreateTime,
                        Collectors.averagingDouble(SubjectAbilityModel::getAbilityScore))));
    }

    private void generateClassSingleAbilityScore(List<SubjectAbilityModel> subjectAbilityModels) {
        //0 记忆能力  1理解能力 2应用能力 3分析能力 4 综合能力
        Map<String, Map<String, Map<Timestamp, Double>>> abilityMap = subjectAbilityModels.stream().
                collect(Collectors.groupingBy(SubjectAbilityModel::getAbilityCode,
                        Collectors.groupingBy(SubjectAbilityModel::getClassName,
                        Collectors.groupingBy(SubjectAbilityModel::getReleaseExamCreateTime,
                        Collectors.averagingDouble(SubjectAbilityModel::getAbilityScore)))));



    }

    /**
     *班级所有能力得分 按照班级区分
     */

    private void generateClassAbilityScore( List<SubjectAbilityModel> subjectAbilityModels) throws Exception{
        Map<String, Map<String, Double>> collect = subjectAbilityModels.stream().collect(Collectors.groupingBy(SubjectAbilityModel::getClassName,
                Collectors.groupingBy(SubjectAbilityModel::getAbilityName, Collectors.averagingDouble(SubjectAbilityModel::getAbilityScore))));

    }

    /**
     * 按班级区分
     * @param knowledgePoList
     */
    private void generateClassWrongKnowledgeGroupByClass(  List<KnowledgeModel> knowledgePoList) {
        Map<String, List<KnowledgeModel>> collect = knowledgePoList.stream().collect(Collectors.groupingBy(KnowledgeModel::getClassName));
        Map classMap=Maps.newHashMap();
        for (Map.Entry<String, List<KnowledgeModel>> s : collect.entrySet()) {
            Map knowResult=Maps.newHashMap();
            //获取当前班级人数
            long studentCount = s.getValue().stream().distinct().count();
            Map<String, Map<String, List<KnowledgeModel>>> knowledgeMap = s.getValue().stream().collect(Collectors.groupingBy(KnowledgeModel::getKnowledgeName,
                    Collectors.groupingBy(k -> {
                        if (k.getIsRight().equals(0)) {
                            return "wrongCount";
                        } else {
                            return "rightCount";
                        }
                    })));

            for (Map.Entry<String, Map<String, List<KnowledgeModel>>> key : knowledgeMap.entrySet()) {
                int wrongCount = key.getValue().get("wrongCount") == null ? 0 : key.getValue().get("wrongCount").size();
                knowResult.put(key.getKey(),wrongCount/(int)studentCount);
            }
            classMap.put(s,knowResult);
        }
    }

    //班级下错误知识点平均
    private void generateClassWrongKnowledge(  List<KnowledgeModel> knowledgePoList) {
        //学生总数量
        long count = knowledgePoList.stream().distinct().count();
        Map<String, Map<String, List<KnowledgeModel>>> collect = knowledgePoList.stream().collect(Collectors.groupingBy(KnowledgeModel::getKnowledgeName, Collectors.groupingBy(k -> {
            if (k.getIsRight().equals(0)) {
                return "wrongCount";
            } else {
                return "rightCount";
            }
        })));
        Map<String,Integer> resultMap= Maps.newHashMap();
        for (Map.Entry<String, Map<String, List<KnowledgeModel>>> s : collect.entrySet()) {
            int wrongCount = s.getValue().get("wrongCount") == null ? 0 : s.getValue().get("wrongCount").size();
            resultMap.put(s.getKey(),wrongCount/(int)count);
        }
    }



    /**
     * 模块知识点统计
     * @param releaseExamCode
     * @param subjectCode
     * @param teacherCode
     * @throws Exception
     */
    private void generateClassKnowledgeMoudle(String releaseExamCode,String subjectCode,String teacherCode) throws Exception{
        List<KnowledgeModuleModel> knowledgeModulePoList = knowledgeModuleService.findByReleaseExamCodeAndSubjectCodeAndTeacherCode(releaseExamCode, teacherCode, subjectCode);
        Map<String, Double> averagingDouble = knowledgeModulePoList.stream().collect(Collectors.groupingBy(KnowledgeModuleModel::getKnowledgeModuleName, Collectors.averagingDouble(KnowledgeModuleModel::getKnowledgeModuleScore)));

    }

    /**
     * 班级错题记录统计
     * @param releaseExamCode 发布考试记录code
     */
    private void generateClassWrongQuestionRank(String releaseExamCode,String teacherCode,String subjectCode) throws Exception{
        userAnswerResultService.getWrongQuestionRank(releaseExamCode, teacherCode, subjectCode);
    }
    //班级达标率统计
    private void generateClassStandard(List<StudentMakeResultModel> studentSubjectScorePoList) {
        if (!studentSubjectScorePoList.isEmpty()){
            //卷面达标分
            Double standardScore=studentSubjectScorePoList.get(0).getStandardScore();
            Map<String, Map<String, Long>> resultMap = studentSubjectScorePoList.stream().collect(
                    Collectors.groupingBy(StudentMakeResultModel::getClassName,
                            Collectors.groupingBy(stu -> {
                                if (stu.getScore() > standardScore) {
                                    return "standardCount";
                                } else {
                                    return "unStandardCount";
                                }

                            }, Collectors.counting())));
            //达标率结果
            Map standardResult=Maps.newHashMap();
            for (Map.Entry<String, Map<String, Long>> key : resultMap.entrySet()) {
                //达标率集合
                Map standardMap=Maps.newHashMap();
                long standardCount = key.getValue().get("standardCount") == null ? 0L : key.getValue().get("standardCount");
                long unStandardCount = key.getValue().get("unStandardCount") == null ? 0L : key.getValue().get("unStandardCount");
                standardMap.put("standard",standardCount/standardCount+unStandardCount);
                standardMap.put("unStandardCount",unStandardCount/standardCount+unStandardCount);
                standardResult.put(key.getKey(),standardMap);
            }


        }

    }
    /**
     *班级合格率 按班级区分
     */
    private void generateClassQualified(List<StudentMakeResultModel> studentSubjectScorePoList) {
        Map<String, Map<String, List<StudentMakeResultModel>>> listMap = studentSubjectScorePoList.stream().collect(Collectors.groupingBy(
                StudentMakeResultModel::getClassName, Collectors.groupingBy(
                        stu -> {
                            if (stu.getScore() >= stu.getPaperScore() * .6 && stu.getScore()<stu.getPaperScore()*.8) {
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
            passRateModel.setJige(jigeCount);
            passRateModel.setYouxiu(youxiuCount);
            passRateModelList.add(passRateModel);
        }

    }

    /**
     * 生成班级成绩表
     */
    public void generateClassResultsTable(List<StudentMakeResultModel> studentSubjectScorePoList) throws Exception{

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
                            if (stu.getScore() >= stu.getPaperScore() * .6 && stu.getScore()<stu.getPaperScore()*.8) {
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
                cs.setDoubleSummaryStatistics(doubleSummary.get(s.getKey()));
                cs.setSubjectCount(s.getValue());
                scoreMap.put("0--" + paperScore * 0.6, s.getValue().get("bujige") == null ? 0 : s.getValue().get("bujige").size());
                scoreMap.put(paperScore * 0.6 + "--" + paperScore * 0.8, s.getValue().get("jige") == null ? 0 : s.getValue().get("jige").size());
                scoreMap.put(paperScore * 0.8 + "--" + paperScore, s.getValue().get("youxiu") == null ? 0 : s.getValue().get("youxiu").size());
                cs.setScoreCount(scoreMap);
                //总学生数量
                Long totalStudent = classStudentCount.get(s.getKey());
                Map rankingAverageMap= Maps.newHashMap();
                CountUtils.getScoreTimes(totalStudent);
//                for (Integer integer : times.keySet()) {
//                    //平均分
//                    Double averageScore = studentSubjectScorePoList.stream().filter(stu -> stu.getClassName().equals(s)).limit(times.get(integer)).collect(Collectors.averagingDouble(StudentMakeResultModel::getScore));
//                    rankingAverageMap.put(times.get(integer),averageScore);
//                }
                cs.setRankingAverage(rankingAverageMap);
                c.add(cs);
            }
        }
       }
    }


}
