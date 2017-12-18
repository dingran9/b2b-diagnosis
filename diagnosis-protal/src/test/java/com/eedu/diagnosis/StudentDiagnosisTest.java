package com.eedu.diagnosis;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eedu.auth.beans.AuthUserBean;
import com.eedu.auth.service.AuthGroupService;
import com.eedu.diagnosis.common.model.paperEntity.*;
import com.eedu.diagnosis.exam.api.dto.AnswerSheetQuestionDto;
import com.eedu.diagnosis.exam.api.dto.DiagnosisRecordStudentDto;
import com.eedu.diagnosis.exam.api.enumeration.DiagnosisPaperEnum;
import com.eedu.diagnosis.exam.api.enumeration.PaperSourceEnum;
import com.eedu.diagnosis.paper.api.dto.DiagnosisComplexPaperRelationDto;
import com.eedu.diagnosis.paper.api.dto.DiagnosisPaperDto;
import com.eedu.diagnosis.paper.api.openService.BasePaperOpenService;
import com.eedu.diagnosis.protal.model.DiagnosisPaperModel;
import com.eedu.diagnosis.protal.model.request.AnswerSheetModel;
import com.eedu.diagnosis.protal.model.request.StudentModel;
import com.eedu.diagnosis.protal.model.response.DiagnosisRecordStudentModel;
import com.eedu.diagnosis.protal.service.StudentDiagnosisService;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by dqy on 2017/3/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:conf/spring-*.xml")
public class StudentDiagnosisTest {
    @Autowired
    private StudentDiagnosisService studentDiagnosisService;
    @Autowired
    private AuthGroupService authGroupService;
    @Autowired
    private BasePaperOpenService basePaperOpenService;

    @Test
    public void initExamList() {
        StudentModel model = new StudentModel();
        model.setUserId(1);
        model.setGradeCode(33);
        model.setStageCode(3);
        List<Integer> subjectCodes = new ArrayList<>();
        subjectCodes.add(1);
        subjectCodes.add(2);
        model.setSubjectCodes(subjectCodes);
        try {
            List<Map<Integer, Object>> maps = studentDiagnosisService.initExamList(model);
            System.out.println(JSONObject.toJSONString(maps));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void examList() {
        StudentModel model = new StudentModel();
        model.setUserId(13);
        model.setGradeCode(32);
        model.setStageCode(3);
        model.setSubjectCode(3);
        model.setPageNum(1);
        model.setPageSize(5);
        PageInfo<DiagnosisRecordStudentModel> diagnosisRecordStudentDtoPageInfo = null;
        try {
            diagnosisRecordStudentDtoPageInfo = studentDiagnosisService.examList(model);
            System.out.println(JSONObject.toJSONString(diagnosisRecordStudentDtoPageInfo));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //单科模拟考试
    @Test
    public void submit() throws Exception {
        int[] classes = {411, 412};


        for (int classcode : classes) {
            int classCodeIndex = 1;
            //班级集合 获取学生
            List<Integer> classCodes = new ArrayList<>();
            classCodes.add(classcode);
            List<AuthUserBean> students = authGroupService.getStudentByClassLists(classCodes);
            Random random = new Random();
            for (AuthUserBean stu : students) {

                //获取试卷
                DiagnosisPaperModel paperModel = new DiagnosisPaperModel();
                paperModel.setCode("3C6E79652359403F9E97BB59D2D1F5DA");
                paperModel.setStudentCode(stu.getUserId());
                PaperSystem paperSystem = studentDiagnosisService.paperDetail(paperModel);

                StudentModel model1 = new StudentModel();
                model1.setClassCode(classcode);
                model1.setPageSize(1000);
                model1.setPageNum(1);
                model1.setStageCode(2);
                model1.setGradeCode(22);
                model1.setUserId(stu.getUserId());
                model1.setSubjectCode(3);
                PageInfo<DiagnosisRecordStudentModel> diagnosisRecordStudentModelPageInfo = studentDiagnosisService.examList(model1);
                List<DiagnosisRecordStudentModel> list = diagnosisRecordStudentModelPageInfo.getList();
                for (DiagnosisRecordStudentModel dto1 : list) {
                    AnswerSheetModel model = new AnswerSheetModel();
                    model.setSubjectCode(dto1.getSubjectCode());
                    model.setCode(dto1.getCode());
                    model.setStageCode(dto1.getStageCode());
                    model.setGradeCode(dto1.getGradeCode());
                    model.setStudentCode(stu.getUserId());
                    model.setResourceType(PaperSourceEnum.DIAGNOSIS.getValue());
                    model.setUseTime(random.nextInt(10000) + "");
                    model.setSchoolCode(407);
                    model.setSchoolName("北京八十八中");
                    model.setClassCode(classcode);
                    model.setClassName(classCodeIndex + "班");
                    model.setStudentName(stu.getUserName());
                    model.setNeedMark(DiagnosisPaperEnum.NeedmarkEnum.NOTNEED.getValue());
                    model.setPaperCode("8a2a74685c0f70a2015c1fd35a0f1979");
                    model.setDiagnosisPaperCode(dto1.getDiagnosisPaperCode());
                    model.setDiagnosisPaperName(dto1.getDiagnosisPaperName());
                    model.setDiagnosisTeacherRecordCode(dto1.getDiagnosisTeacherRecordCode());
                    Double totalScore = 0d;
                    List<AnswerSheetQuestionDto> answerSheetQuestionDtos = new ArrayList<>();
                    for (QuestionSet qset : paperSystem.getPaperSystemQusetionType()) {
                        for (BigQuestionSystem bigquestionSystem : qset.getTypeList()) {
                            int sn = 1;
                            for (SmallQuestionSystem smallQuestion : bigquestionSystem.getList()) {
                                String quesOption = smallQuestion.getQuesOption();
                                List<QuestionOption> questionOptions = JSONArray.parseArray(quesOption, QuestionOption.class);
                                AnswerSheetQuestionDto dto = new AnswerSheetQuestionDto();
                                dto.setIsComplex(0);
                                dto.setIsImg(0);
                                dto.setRightAnswer(smallQuestion.getAnswer());
                                QuestionOption questionOption = questionOptions.get(random.nextInt(3));
                                dto.setStudentAnswer(questionOption.getOptionKey());
                                if (smallQuestion.getAnswer().equals(questionOption.getOptionKey())) {
                                    dto.setIsRight(1);
                                    totalScore += smallQuestion.getScore();
                                } else {
                                    dto.setIsRight(0);
                                }
                                dto.setItemContent("单选题");
                                dto.setLogicType("1");
                                dto.setQuestionCode(smallQuestion.getId());
                                dto.setQuestionScore(smallQuestion.getScore() + "");
                                dto.setQuestionSn(sn + "");
                                sn++;
                                answerSheetQuestionDtos.add(dto);
                            }
                        }
                    }
                    model.setTotalScore(totalScore + "");
                    model.setAnswerSheetQuestionDtos(answerSheetQuestionDtos);
                    studentDiagnosisService.submit(model);
                }
            }
            classCodeIndex++;
        }


//        List<AnswerSheetQuestionDto> answerSheetQuestionDtos = new ArrayList<>();
//
//        AnswerSheetQuestionDto dto = new AnswerSheetQuestionDto();
//        dto.setIsComplex(0);
//        dto.setIsImg(0);
//        dto.setIsRight(0);
//        dto.setRightAnswer("zhengque0");
//        dto.setStudentAnswer("student0");
//        dto.setItemContent("单选题");
//        dto.setLogicType("1");
//        dto.setQuestionCode("8a2a7468593fc45e015945152e504900");
//        dto.setQuestionScore("4.0");
//        dto.setQuestionSn("1.1");
//        answerSheetQuestionDtos.add(dto);
//
//        AnswerSheetQuestionDto dto2 = new AnswerSheetQuestionDto();
//        dto2.setIsComplex(0);
//        dto2.setIsImg(0);
//        dto2.setIsRight(1);
//        dto2.setRightAnswer("zhengque1");
//        dto2.setStudentAnswer("student1");
//        dto2.setItemContent("单选题");
//        dto2.setLogicType("1");
//        dto2.setQuestionCode("8a2a7468593fc45e0159451ff54f490b");
//        dto2.setQuestionScore("4.0");
//        dto2.setQuestionSn("1.2");
//        answerSheetQuestionDtos.add(dto2);
//
//        AnswerSheetQuestionDto dto3 = new AnswerSheetQuestionDto();
//        dto3.setIsComplex(0);
//        dto3.setIsImg(0);
//        dto3.setIsRight(0);
//        dto3.setRightAnswer("zhengque2");
//        dto3.setStudentAnswer("student2");
//        dto3.setItemContent("单选题");
//        dto3.setLogicType("1");
//        dto3.setQuestionCode("8a2a7468593fc45e0159452608b4490f");
//        dto3.setQuestionScore("4.0");
//        dto3.setQuestionSn("1.3");
//        answerSheetQuestionDtos.add(dto3);
//        AnswerSheetQuestionDto dto1 = new AnswerSheetQuestionDto();
//        dto1.setIsComplex(0);
//        dto1.setIsImg(0);
//        dto1.setIsRight(1);
//        dto1.setRightAnswer("zhengque3");
//        dto1.setStudentAnswer("student3");
//        dto1.setItemContent("单选题");
//        dto1.setLogicType("1");
//        dto1.setQuestionCode("8a2a7468593fc45e0159451ff54f490b");
//        dto1.setQuestionScore("4.0");
//        dto1.setQuestionSn("1.4");
//        answerSheetQuestionDtos.add(dto1);
//
//        model.setAnswerSheetQuestionDtos(answerSheetQuestionDtos);
//        String s = JSONObject.toJSONString(model);
//
//        try {
//            Map submit = studentDiagnosisService.submit(model);
//            System.out.println(submit);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    //全科考试 提交答题卡 模拟
    @Test
    public void complexSubmit() throws Exception {
        int[] classes = {411, 412};//所有的班级code

        //全科试卷
        DiagnosisComplexPaperRelationDto dcpr = new DiagnosisComplexPaperRelationDto();
        dcpr.setComplexPaperCode("8F949790880545B2B2B8816425BB6C42");
        List<DiagnosisComplexPaperRelationDto> diagnosisComplexPaperRelationList = basePaperOpenService.getDiagnosisComplexPaperRelationList(dcpr);

        for (int classcode : classes) {
            int classCodeIndex = 1;
            //班级集合 获取学生
            List<Integer> classCodes = new ArrayList<>();
            classCodes.add(classcode);
            List<AuthUserBean> students = authGroupService.getStudentByClassLists(classCodes);
            Random random = new Random();
            for (AuthUserBean stu : students) {
                //全科考试记录列表
                StudentModel model1 = new StudentModel();
                model1.setClassCode(classcode);
                model1.setPageSize(1000);
                model1.setPageNum(1);
                model1.setStageCode(2);
                model1.setGradeCode(22);
                model1.setUserId(stu.getUserId());
                PageInfo<DiagnosisRecordStudentDto> pageInfo = studentDiagnosisService.complexExamList(model1);
                List<DiagnosisRecordStudentDto> list = pageInfo.getList();
                for (DiagnosisRecordStudentDto ds : list) {
                    //该次全科考试内的各学科考试列表
                    DiagnosisRecordStudentModel drsm = new DiagnosisRecordStudentModel();
                    drsm.setCode(ds.getDiagnosisTeacherRecordCode());
                    drsm.setStudentCode(stu.getUserId());
                    List<DiagnosisRecordStudentModel> diagnosisRecordStudentModels = studentDiagnosisService.complexSubjectExamList(drsm);

                    for (DiagnosisRecordStudentModel dto1:diagnosisRecordStudentModels) {

                        //获取试卷
                        DiagnosisPaperModel paperModel = new DiagnosisPaperModel();
                        paperModel.setCode(dto1.getDiagnosisPaperCode());
                        paperModel.setStudentCode(stu.getUserId());
                        PaperSystem paperSystem = studentDiagnosisService.paperDetail(paperModel);

                        DiagnosisPaperDto dpd = new DiagnosisPaperDto();
                        dpd.setCode(dto1.getDiagnosisPaperCode());
                        List<DiagnosisPaperDto> diagnosisPaperList = basePaperOpenService.getDiagnosisPaperList(dpd);



                        AnswerSheetModel model = new AnswerSheetModel();
                        model.setSubjectCode(dto1.getSubjectCode());
                        model.setCode(dto1.getCode());
                        model.setStageCode(dto1.getStageCode());
                        model.setGradeCode(dto1.getGradeCode());
                        model.setStudentCode(stu.getUserId());
                        model.setResourceType(PaperSourceEnum.DIAGNOSIS.getValue());
                        model.setUseTime(random.nextInt(10000) + "");
                        model.setSchoolCode(407);
                        model.setSchoolName("北京八十八中");
                        model.setClassCode(classcode);
                        model.setClassName(classCodeIndex + "班");
                        model.setStudentName(stu.getUserName());
                        model.setNeedMark(DiagnosisPaperEnum.NeedmarkEnum.NOTNEED.getValue());
                        model.setPaperCode(diagnosisPaperList.get(0).getResourcePaperCode());
                        model.setDiagnosisPaperCode(dto1.getDiagnosisPaperCode());
                        model.setDiagnosisPaperName(dto1.getDiagnosisPaperName());
                        model.setDiagnosisTeacherRecordCode(dto1.getDiagnosisTeacherRecordCode());
                        Double totalScore = 0d;
                        List<AnswerSheetQuestionDto> answerSheetQuestionDtos = new ArrayList<>();
                        for (QuestionSet qset : paperSystem.getPaperSystemQusetionType()) {
                            for (BigQuestionSystem bigquestionSystem : qset.getTypeList()) {
                                int sn = 1;
                                for (SmallQuestionSystem smallQuestion : bigquestionSystem.getList()) {
                                    String quesOption = smallQuestion.getQuesOption();
                                    List<QuestionOption> questionOptions = JSONArray.parseArray(quesOption, QuestionOption.class);
                                    AnswerSheetQuestionDto dto = new AnswerSheetQuestionDto();
                                    dto.setIsComplex(0);
                                    dto.setIsImg(0);
                                    dto.setRightAnswer(smallQuestion.getAnswer());
                                    QuestionOption questionOption = questionOptions.get(random.nextInt(3));
                                    dto.setStudentAnswer(questionOption.getOptionKey());
                                    if (smallQuestion.getAnswer().equals(questionOption.getOptionKey())) {
                                        dto.setIsRight(1);
                                        totalScore += smallQuestion.getScore();
                                    } else {
                                        dto.setIsRight(0);
                                    }
                                    dto.setItemContent("单选题");
                                    dto.setLogicType("1");
                                    dto.setQuestionCode(smallQuestion.getId());
                                    dto.setQuestionScore(smallQuestion.getScore() + "");
                                    dto.setQuestionSn(sn + "");
                                    sn++;
                                    answerSheetQuestionDtos.add(dto);
                                }
                            }
                        }
                        model.setTotalScore(totalScore + "");
                        model.setAnswerSheetQuestionDtos(answerSheetQuestionDtos);
                        studentDiagnosisService.submit(model);
                    }
                }
            }
            classCodeIndex++;
        }

    }


    public static void main(String[] args) {
        AnswerSheetModel model = new AnswerSheetModel();
        model.setSubjectCode(2);
        model.setCode("3");
        model.setStageCode(3);
        model.setGradeCode(33);
        model.setStudentCode(1);
        model.setResourceType(PaperSourceEnum.DIAGNOSIS.getValue());
        model.setUseTime("2222");
        model.setClassCode(2);
        model.setStudentName("朱2");
        model.setClassName("小2班");
        model.setNeedMark(DiagnosisPaperEnum.NeedmarkEnum.NOTNEED.getValue());
        model.setTotalScore("66.5");
        model.setPaperCode("8a2a746858a9d6cd0158a9e97c43009f");
        model.setDiagnosisPaperCode("0AAAF55ECB864953B5546EE970BDA49B");
        model.setDiagnosisPaperName("试卷名字");
        model.setDiagnosisTeacherRecordCode("EE8EA32F7763F1A2B3EA04ACD1669111");
        List<AnswerSheetQuestionDto> answerSheetQuestionDtos = new ArrayList<>();

        AnswerSheetQuestionDto dto = new AnswerSheetQuestionDto();
        dto.setIsComplex(0);
        dto.setIsImg(0);
        dto.setIsRight(0);
        dto.setRightAnswer("zhengque");
        dto.setStudentAnswer("student");
        dto.setItemContent("单选题");
        dto.setLogicType("1");
        dto.setQuestionCode("8a2a746858a9d6cd0158aa053d850201");
        dto.setQuestionScore("4.0");
        dto.setQuestionSn("1.1");
        answerSheetQuestionDtos.add(dto);

        AnswerSheetQuestionDto dto1 = new AnswerSheetQuestionDto();
        dto1.setIsComplex(0);
        dto1.setIsImg(0);
        dto1.setIsRight(1);
        dto1.setRightAnswer("zhengque");
        dto1.setStudentAnswer("student");
        dto1.setItemContent("单选题");
        dto1.setLogicType("1");
        dto1.setQuestionCode("8a2a746858b8a0b30158b987ca5402ba");
        dto1.setQuestionScore("4.0");
        dto1.setQuestionSn("1.2");
        answerSheetQuestionDtos.add(dto1);

        model.setAnswerSheetQuestionDtos(answerSheetQuestionDtos);
        System.out.println(JSONObject.toJSONString(model));
    }
}
