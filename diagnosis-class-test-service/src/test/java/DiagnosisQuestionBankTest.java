import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.inclass.test.api.dto.DiagnosisQuestionBankDto;
import com.eedu.diagnosis.inclass.test.api.service.DiagnosisQuestionBankService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by Administrator on 2017/10/14.
 */
@ContextConfiguration("classpath*:spring-comm-conf.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DiagnosisQuestionBankTest {

    @Autowired
    DiagnosisQuestionBankService service;

    @Test
    public void test1() throws Exception {
        String aa = "[{\"coverUrl\":\"1\",\"createTime\":1508142910000,\"description\":\"1\",\"questionBookCode\":\"1\",\"questionBookName\":\"1\",\"questionCount\":1,\"teacherCode\":\"1\",\"teacherName\":\"1\",\"updateTime\":1508142912000}]\n";
        List<DiagnosisQuestionBankDto> dto = JSONObject.parseArray(aa,DiagnosisQuestionBankDto.class);
        int i = service.saveDiagnosisQuestionBank(dto.get(0));
        System.out.println("------------------------------------------="+i);
    }

    @Test
    public void test2() throws Exception {
        List<DiagnosisQuestionBankDto> list = service.selectDiagnosisQuestionBankList(new DiagnosisQuestionBankDto());
        System.out.println("------------------------------------------="+ JSONObject.toJSONString(list));
    }

}
