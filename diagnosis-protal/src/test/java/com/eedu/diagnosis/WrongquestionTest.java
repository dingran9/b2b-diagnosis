package com.eedu.diagnosis;

import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.exam.api.dto.DiagnosisWrongQuestionDto;
import com.eedu.diagnosis.exam.api.openService.DiagnosisWrongQuestionOpenService;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dqy on 2017/3/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:conf/spring-*.xml")
public class WrongquestionTest {
    @Autowired
    private DiagnosisWrongQuestionOpenService diagnosisWrongQuestionOpenService;

    @Test
    public void getWrongQuestionList(){
        DiagnosisWrongQuestionDto dto = new DiagnosisWrongQuestionDto();
        dto.setSubjectCode(2);
        dto.setStudentCode(1);

        try {
            PageInfo<DiagnosisWrongQuestionDto> wrongQuestions = diagnosisWrongQuestionOpenService.getWrongQuestions(dto, 10, 1);
            System.out.println(JSONObject.toJSONString(wrongQuestions));
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
