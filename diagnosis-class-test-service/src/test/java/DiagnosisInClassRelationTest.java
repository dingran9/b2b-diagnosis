import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.inclass.test.api.dto.DiagnosisInClassRelationDto;
import com.eedu.diagnosis.inclass.test.api.enums.ConstantEnum;
import com.eedu.diagnosis.inclass.test.api.model.QuestionBankModel;
import com.eedu.diagnosis.inclass.test.api.service.DiagnosisInClassRelationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/14.
 */

@ContextConfiguration("classpath*:spring-comm-conf.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DiagnosisInClassRelationTest {

    @Autowired
    DiagnosisInClassRelationService diagnosisInClassRelationService;

    @Test
    public void test1() throws Exception {
        List<DiagnosisInClassRelationDto> list = new ArrayList<>();
        String a1 ="[{\"inClassRelationCode\":\"1111\",\"baseCode\":\"4573AB04484447F3B033DC5B01935109\",\"baseName\":\"1\",\"coverUrl\":\"1\",\"difficultStar\":1,\"inClassTestCode\":\"5F5B8E0B25FB4C9F957D304CC644B958\",\"knowledges\":\"1\",\"questionAnalyze\":\"1\",\"questionBookCode\":\"1\",\"questionBookName\":\"1\",\"questionOption\":\"1\",\"questionScore\":1,\"questionStem\":\"1\",\"questionType\":1,\"resourceCode\":\"1\",\"rightAnswer\":\"1\",\"source\":\"1\",\"sout\":\"2\",\"subjectCode\":1}]";
        list= JSONObject.parseArray(a1,DiagnosisInClassRelationDto.class);
        int i = diagnosisInClassRelationService.batchSaveDiagnosisInClassRelation(list);
        System.out.println("------------------------------------------="+i);
    }

    @Test
    public void test2() throws Exception {
        List<DiagnosisInClassRelationDto> list = diagnosisInClassRelationService.selectDiagnosisInClassRelationList(new DiagnosisInClassRelationDto());
        System.out.println("------------------------------------------="+ JSONObject.toJSONString(list));
    }
    @Test
    public void test3() throws Exception {
        List<QuestionBankModel> questionBankModels = diagnosisInClassRelationService.selectDiagnosisQuestionBankCount("1");
        System.out.println("------------------------------------------="+JSONObject.toJSONString(questionBankModels));
    }
    @Test
    public void test4() throws Exception {

        DiagnosisInClassRelationDto dto = new DiagnosisInClassRelationDto();
        dto.setInClassTestCode("1");
        dto.setIsEnd(ConstantEnum.START.getType());
        List<DiagnosisInClassRelationDto> list = diagnosisInClassRelationService.selectDiagnosisInClassRelationList(dto);
        if (CollectionUtils.isEmpty(list)) {
            System.out.println("0") ;        }
        else if (list.size() == 1) {
            System.out.println(list.get(0).getSout()) ;
        }else {
            list.sort((o1, o2) -> Integer.parseInt(o1.getSout()) - Integer.parseInt(o2.getSout()));
            System.out.println(list.get(0).getSout());
        }
    }
}
