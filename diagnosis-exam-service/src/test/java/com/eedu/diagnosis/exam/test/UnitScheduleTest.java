package com.eedu.diagnosis.exam.test;

import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.exam.api.dto.DiagnosisUnitScheduleDto;
import com.eedu.diagnosis.exam.api.openService.DiagnosisUnitScheduleOpenService;
import com.eedu.diagnosis.exam.persist.po.DiagnosisUnitSchedulePo;
import com.eedu.diagnosis.exam.service.DiagnosisUnitScheduleService;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by dqy on 2017/10/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/spring-*.xml")
public class UnitScheduleTest {
    @Autowired
    private DiagnosisUnitScheduleService diagnosisUnitScheduleService;
    @Autowired
    private DiagnosisUnitScheduleOpenService diagnosisUnitScheduleOpenService;


    @Test
    public void test1(){
        DiagnosisUnitSchedulePo po = new DiagnosisUnitSchedulePo();
        po.setBookVersion("222222");
        po.setCode(UUID.randomUUID().toString().replace("-", "").toUpperCase());
        po.setDistrictId(222222);
        po.setGradeCode(22);
        po.setUnitCode("222222");
        po.setSubjectCode(2);
        po.setSemester("20181");
        try {
            diagnosisUnitScheduleService.save(po);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2(){
        List<DiagnosisUnitScheduleDto> list = new ArrayList<>();
        DiagnosisUnitScheduleDto dto = new DiagnosisUnitScheduleDto();
        dto.setBookVersion("222222");
        dto.setCode("900B69BEA6354DA1BF43DA164D9B7B40");
        dto.setDistrictId(222333);
        dto.setGradeCode(22);
        dto.setUnitCode("222222");
        dto.setSubjectCode(2);
        dto.setSemester("20181");
        list.add(dto);


        DiagnosisUnitScheduleDto dto1 = new DiagnosisUnitScheduleDto();
        dto1.setCode("A447BF7110524FC19B23A67EB4ED0E7C");
        dto1.setBookVersion("333334444");
        dto1.setDistrictId(333344);
        dto1.setGradeCode(22);
        dto1.setUnitCode("333333444");
        dto1.setSubjectCode(2);
        dto1.setSemester("20181");
        list.add(dto1);

        DiagnosisUnitScheduleDto dto2 = new DiagnosisUnitScheduleDto();
        dto2.setBookVersion("88");
        dto2.setDistrictId(88);
        dto2.setGradeCode(22);
        dto2.setUnitCode("888");
        dto2.setSubjectCode(2);
        dto2.setSemester("20182");
        list.add(dto2);
        try {
            diagnosisUnitScheduleOpenService.saveAndUpdateList(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test3(){
        DiagnosisUnitScheduleDto dto = new DiagnosisUnitScheduleDto();
        dto.setOperatorCode(123123);
        try {
            PageInfo<DiagnosisUnitScheduleDto> list = diagnosisUnitScheduleOpenService.getList(dto, 1, 10, null);
            System.out.println(JSONObject.toJSONString(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
