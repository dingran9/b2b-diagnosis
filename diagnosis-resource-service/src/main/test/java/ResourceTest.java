import com.eedu.diagnosis.paper.service.client.BaseResourceClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * 资源测试类
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-04-18 14:44
 **/
@ContextConfiguration("classpath*:META-INF/spring/spring-*.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ResourceTest {
    @Autowired
    BaseResourceClient baseResourceClient;
    @Test
    public void testGetVideo() throws  Exception{
        List<String> knowledge=new ArrayList<>();
        knowledge.add("666500050001");
        String subjectCode="2";
        String gradeCode="16";
        String bookType="nkCDYaGSR6sNQx868N2cw3BBaPbtRanX";
        String jsonStr = baseResourceClient.getVideoByKnowledgeCodeAndSubjectCodeAndGradeCodeAndBookTypeCode(knowledge, subjectCode, gradeCode, bookType);
        System.out.println(jsonStr);
    }
}
