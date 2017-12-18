package com.eedu.diagnosis.exam.test;

import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.exam.persist.po.DiagnosisWrongQuestion;
import com.eedu.diagnosis.exam.service.DiagnosisWrongQuestionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dqy on 2017/3/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/spring-*.xml")
public class WrongquestionTest {
    @Autowired
    private DiagnosisWrongQuestionService diagnosisWrongQuestionOpenService;

    @Test
    public void getWrongQuestionList(){
//        DiagnosisWrongQuestionDto dto = new DiagnosisWrongQuestionDto();
//        dto.setSubjectCode(2);
//        dto.setStudentCode(1);
        Map queryMap = new HashMap();
        queryMap.put("subjectCode",2);
        queryMap.put("studentCode",1);

        try {
            List<DiagnosisWrongQuestion> wrongQuestions1 = diagnosisWrongQuestionOpenService.getWrongQuestions(queryMap);
            System.out.println(JSONObject.toJSONString(wrongQuestions1));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    public void delWrong(){
        List<String> codes = new ArrayList<>();
        codes.add("450FE397FBB64FB197676E370CBB85A0");
        codes.add("9EF6D39BDF394740B5A6AD978B70CE55");
        try {
//            diagnosisWrongQuestionOpenService.delWrongQuestions(codes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
