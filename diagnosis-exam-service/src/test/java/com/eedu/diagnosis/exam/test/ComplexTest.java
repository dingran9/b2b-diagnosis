package com.eedu.diagnosis.exam.test;

import com.eedu.diagnosis.exam.persist.po.DiagnosisRecordDetailPo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by dqy on 2017/9/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:META-INF/spring/spring-*.xml")
public class ComplexTest {



    @Test
    public void test3() {
        List<DiagnosisRecordDetailPo> list = new ArrayList<>();

        DiagnosisRecordDetailPo po3 = new DiagnosisRecordDetailPo();
        po3.setStartTime(new Date(1488301260000l));
        po3.setEndTime(new Date(1488301260000l));
        list.add(po3);

        DiagnosisRecordDetailPo po1 = new DiagnosisRecordDetailPo();
        po1.setStartTime(new Date(1493571660000l));
        po1.setEndTime(new Date(1493571660000l));
        list.add(po1);

        DiagnosisRecordDetailPo po = new DiagnosisRecordDetailPo();
        po.setStartTime(new Date(1483203660000l));
        po.setEndTime(new Date(1483203660000l));
        list.add(po);



        Optional<DiagnosisRecordDetailPo> max = list.stream().max((o1, o2) -> {
            if (o1.getEndTime().before(o2.getEndTime())) return -1;
            else if (o1.getEndTime().after(o2.getEndTime())) return 1;
            return 0;
        });
        System.out.println("maxEnd===" + max.get().getEndTime().getTime());
        Optional<DiagnosisRecordDetailPo> min = list.stream().min((o1, o2) -> {
            if (o1.getStartTime().before(o2.getStartTime())) return -1;
            else if (o1.getStartTime().after(o2.getStartTime())) return 1;
            return 0;
        });
        System.out.println("minStart===" + min.get().getStartTime().getTime());
    }

}
