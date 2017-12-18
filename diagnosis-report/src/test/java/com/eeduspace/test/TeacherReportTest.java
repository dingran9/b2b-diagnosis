package com.eeduspace.test;

import com.alibaba.fastjson.JSONObject;
import com.eeduspace.b2b.report.model.report.*;
import com.eeduspace.b2b.report.service.TeacherReportOpenService;
import com.eeduspace.report.B2bReportApplication;
import com.eeduspace.report.config.BaseResourceConfig;
import com.eeduspace.report.service.KnowledgeModuleService;
import com.eeduspace.report.service.StudentSubjectScoreService;
import com.eeduspace.report.util.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 教师报告测试类
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-03-22 16:27
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = B2bReportApplication.class,webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TeacherReportTest {
    @Autowired
    private TeacherReportOpenService teacherReportOpenServiceImpl;
    @Autowired
    private StudentSubjectScoreService studentSubjectScoreService;
    @Autowired
    BaseResourceConfig baseResourceConfig;
    @Autowired
    KnowledgeModuleService knowledgeModuleService;
    @Test
    public void getClassResultsTable(){
        String releaseExamCode="53EC92C3902C492EB451177E56D303CB";
        String teacherCode="472";
        String subjectCode="1";
        try {
            Map<String, Object> classResultsTableForDaqiao = teacherReportOpenServiceImpl.getClassResultsTableForDaqiao(teacherCode, subjectCode, releaseExamCode);
            log.info("====>"+ JSONObject.toJSONString(classResultsTableForDaqiao));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    //班级合格率
    public void getClassQualified() throws Exception{
        String releaseExamCode="0001";
        String teacherCode="zz";
        String subjectCode="2";
        List<PassRateModel> classQualified = teacherReportOpenServiceImpl.getClassQualified(teacherCode, subjectCode, releaseExamCode);
        log.info("--合格率--->"+GsonUtils.toJson(classQualified));
    }

    /**
     * 获取答题信息
     */
    @Test
    public void findByReleaseExamCodeAndClassCode() throws Exception{
        List<String> examType=new ArrayList<>();
        examType.add("2");
        examType.add("0");
       // List<StudentMakeResultModel> byReleaseExamCodeAndClassCode = studentSubjectScoreService.findByCondition("8F56095EBD024DEB8ADD340727FEFD12", "29", "2", null,null,null);
        List<StudentMakeResultModel> makeResultModels = studentSubjectScoreService.findByReleaseExamCodeAndSemesterAndExamType(null, null, null, examType);
        log.info("-----获取答题信息---->"+GsonUtils.toJson(makeResultModels));
    }
    @Test
    //班级达标率
    public void getClassStandard() throws Exception{
        String releaseExamCode="53EC92C3902C492EB451177E56D303CB";
        String teacherCode="472";
        String subjectCode="1";
        HashMap<String, HashMap<String, Object>> classStandard = teacherReportOpenServiceImpl.getClassStandard(teacherCode, subjectCode, releaseExamCode);
        log.info("-----达标率---->"+GsonUtils.toJson(classStandard));
    }

    @Test
    //班级错误排行榜
    public void getClassWrongQuestionRank() throws Exception{
        String releaseExamCode="97CE988EC774437680AE733A94501089";
        String teacherCode="230";
        String subjectCode="2";
        Map<String, List<WrongQuestionRankModel>> collect = teacherReportOpenServiceImpl.getClassWrongQuestionRank(teacherCode, subjectCode, releaseExamCode);
        log.info("---错题排行榜---->"+GsonUtils.toJson(collect));
    }
    @Test
    //知识点掌握情况
    public void getClassKnowledgeMoudle() throws  Exception{
        String releaseExamCode="0001";
        String teacherCode="zz";
        String subjectCode="2";
        Map<String, Double> classKnowledgeMoudle = teacherReportOpenServiceImpl.getClassKnowledgeMoudle(teacherCode, subjectCode, releaseExamCode);
        log.info("---知识点掌握情况---->"+GsonUtils.toJson(classKnowledgeMoudle));
    }
    @Test
    //班级共性知识点统计
    public void getClassWrongKnowledge() throws Exception{

        ///teacherReport/getClassWrongKnowledgeGroupByClass/513/1/9C1B3D8E756C413D888998B494430371

        String releaseExamCode="9C1B3D8E756C413D888998B494430371";
        String teacherCode="513";
        String subjectCode="1";
        Map<String, Integer> classWrongKnowledge = teacherReportOpenServiceImpl.getClassWrongKnowledge(teacherCode, subjectCode, releaseExamCode);
        log.info("---班级共性知识点统计---->"+GsonUtils.toJson(classWrongKnowledge));

    }

    @Test
    //各班级错误知识点情况
    public void getClassWrongKnowledgeGroupByClass() throws Exception{
        String releaseExamCode="AB92AE37D9B14E4E9EF422AB26B26DBD";
        String teacherCode="779";
        String subjectCode="3";
        List<List<String>> classWrongKnowledgeGroupByClass = teacherReportOpenServiceImpl.getClassWrongKnowledgeGroupByClass(teacherCode, subjectCode, releaseExamCode);
        log.info("---各班级错误知识点情况---->"+GsonUtils.toJson(classWrongKnowledgeGroupByClass));

    }
    @Test
    //获取班级平均能力
    public void getClassAbilityScore() throws  Exception{
        String releaseExamCode="0001";
        String teacherCode="zz";
        String subjectCode="2";
        Map<String, Map<String, Double>> classAbilityScore = teacherReportOpenServiceImpl.getClassAbilityScore(teacherCode, subjectCode, releaseExamCode);
        log.info("---获取班级平均能力---->"+GsonUtils.toJson(classAbilityScore));

    }

    @Test
    //获取单个能力历史平均
    public void getClassSingleAbilityScore() throws Exception{
        String releaseExamCode="4AEC9FDA6417466897760B33C54EAFAE";
        String teacherCode="362";
        String subjectCode="1";
        Map<String, Map<String, Map<Long, Double>>> classSingleAbilityScore = teacherReportOpenServiceImpl.getClassSingleAbilityScore(teacherCode, subjectCode, releaseExamCode);
        log.info("---获取单个能力历史平均---->"+GsonUtils.toJson(classSingleAbilityScore));

    }

    @Test
    //学科能力平均值
    public void getClassSubjectAverageScore() throws Exception{
        String releaseExamCode="E5DF599718314D85A2AFFA1932CA8A1C";
        String teacherCode="216";
        String subjectCode="2";
        Map<String, Map<Long, Double>> classSubjectAverageScore = teacherReportOpenServiceImpl.getClassSubjectAverageScore(teacherCode, subjectCode, releaseExamCode);
        log.info("---学科能力平均值---->"+GsonUtils.toJson(classSubjectAverageScore));

    }

    /**
     * 获取单个班级报告
     */
    @Test
    public void getSingleClassReport() throws Exception{
        String releaseExamCode="49532FC564CB4795AC7318900B4D3C7D";
        String teacherCode="489";
        String subjectCode="1";
        String classCode="888";
        SingleClassReportModel singleClassReport = teacherReportOpenServiceImpl.getSingleClassReport(null, subjectCode, releaseExamCode, classCode);
        log.info("获取单个班级报告-----》"+GsonUtils.toJson(singleClassReport));
    }
    @Test
    public void getBaseConfig(){
        log.info("获取单个班级报告-----》"+baseResourceConfig.getServer());

    }

    /**
     * 平均标准分
     * @throws Exception
     */
    @Test
    public void getClassStandardScore() throws Exception{

        StandardScoreChangeModel standardScoreChange = teacherReportOpenServiceImpl.getStandardScoreChange(1, "612", "1", "050E1DAF269A4EEFAA55EF30A7D5D44E");
        System.out.println(GsonUtils.toJson(standardScoreChange));
    }

    @Test
    public void getKnowledge(){
        List<String> codes = new ArrayList<>();
        codes.add("");
        codes.add("");
        try {
            List<KnowledgeModuleModel> byReleaseCodeIn = knowledgeModuleService.findByReleaseCodeIn(codes);
            log.info("====================>"+GsonUtils.toJson(byReleaseCodeIn));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
