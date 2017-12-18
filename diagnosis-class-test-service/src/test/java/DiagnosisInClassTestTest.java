import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.inclass.test.api.dto.DiagnosisInClassTestDto;
import com.eedu.diagnosis.inclass.test.api.service.DiagnosisInClassTestService;
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
public class DiagnosisInClassTestTest {

    @Autowired
    DiagnosisInClassTestService service;

    @Test
    public void test1() throws Exception {
        String aa = "[{\"classCode\":\"1\",\"createTime\":1508142654000,\"gradeCode\":1,\"inClassTestCode\":\"1\",\"inClassTestName\":\"1\",\"questionBookJson\":\"1\",\"questionCount\":1,\"questionJson\":\"1\",\"teacherCode\":\"1\",\"teacherName\":\"1\",\"updateTime\":1508142657000}]\n";
        List<DiagnosisInClassTestDto> dto = JSONObject.parseArray(aa,DiagnosisInClassTestDto.class);
        int i = service.saveDiagnosisInClassTest(dto.get(0));
        System.out.println("------------------------------------------="+i);
    }

    @Test
    public void test2() throws Exception {
        List<DiagnosisInClassTestDto> list = service.selectDiagnosisInClassTestList(new DiagnosisInClassTestDto());
        System.out.println("------------------------------------------="+ JSONObject.toJSONString(list));
    }
}
