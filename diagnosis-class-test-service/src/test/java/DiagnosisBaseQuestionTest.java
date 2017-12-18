import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.inclass.test.api.dto.DiagnosisBaseQuestionDto;
import com.eedu.diagnosis.inclass.test.api.service.DiagnosisBaseQuestionService;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;


@ContextConfiguration("classpath*:spring-comm-conf.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DiagnosisBaseQuestionTest {

    @Autowired
    DiagnosisBaseQuestionService service;


    @Test
    public void test1() throws Exception {
        String aa = "{\"baseCode\":\"1\",\"baseName\":\"11\",\"coverUrl\":\"1\",\"createTime\":1507963981000,\"knowledges\":\"1\",\"questionAnalyze\":\"1\",\"questionOption\":\"1\",\"questionScore\":1.00,\"questionStem\":\"1\",\"questionType\":1,\"resourceCode\":\"1\",\"rightAnswer\":\"1\",\"source\":\"1\",\"subjectCode\":1,\"updateTime\":1507963983000}";
        DiagnosisBaseQuestionDto dto = JSONObject.parseObject(aa, DiagnosisBaseQuestionDto.class);
        int i = service.saveDiagnosisBaseQuestion(dto);
        System.out.println("------------------------------------------=" + i);
    }

    @Test
    public void test2() throws Exception {
        DiagnosisBaseQuestionDto diagnosisBaseQuestionDto = service.selectDiagnosisBaseQuestionDto("5AFE407382EF45FC92DAD7305A2217BC");
        System.out.println("------------------------------------------=" + JSONObject.toJSONString(diagnosisBaseQuestionDto));
    }

    @Test
    public void test3() throws Exception {
        String a1 = "{\"baseCode\":\"1\",\"baseName\":\"11\",\"coverUrl\":\"1\",\"createTime\":1507963981000,\"knowledges\":\"1\",\"questionAnalyze\":\"1\",\"questionOption\":\"1\",\"questionScore\":1.00,\"questionStem\":\"1\",\"questionType\":1,\"resourceCode\":\"1\",\"rightAnswer\":\"1\",\"source\":\"1\",\"subjectCode\":1,\"updateTime\":1507963983000}";
        DiagnosisBaseQuestionDto dto1 = JSONObject.parseObject(a1, DiagnosisBaseQuestionDto.class);
        List<DiagnosisBaseQuestionDto> list = new ArrayList<>();
        list.add(dto1);
        list.add(dto1);
        list.add(dto1);
        int i = service.batchSaveDiagnosisBaseQuestion(list);
        System.out.println("------------------------------------------=" + i);
    }


    @Test
    public void test4() throws Exception {
        PageInfo<DiagnosisBaseQuestionDto> i = service.selectDiagnosisBaseQuestionPage(new DiagnosisBaseQuestionDto());
        System.out.println("------------------------------------------=" + JSONObject.toJSONString(i));
    }


    @Test
    public void test6() throws Exception {
        List<DiagnosisBaseQuestionDto> list = service.selectDiagnosisBaseQuestionByInClassTest("1", null, null).getList();
        System.out.println("------------------------------------------=" + JSONObject.toJSONString(list));
    }


}
