package com.eedu.diagnosis.exam.test;

import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.exam.api.dto.DiagnosisRecordStudentDto;
import com.eedu.diagnosis.exam.api.dto.DiagnosisRecordTeacherDto;
import com.eedu.diagnosis.exam.api.openService.DiagnosisRecordStudentOpenService;
import com.eedu.diagnosis.exam.api.openService.DiagnosisRecordTeacherOpenService;
import com.eedu.diagnosis.exam.persist.po.DiagnosisClassRelationPo;
import com.eedu.diagnosis.exam.service.DiagnosisClassRelationService;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by dqy on 2017/3/15.
 */
@ContextConfiguration("classpath*:META-INF/spring/spring-*.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DiagnosisClassRelationTest {
    @Autowired
    private DiagnosisClassRelationService diagnosisClassRelationService;
    @Autowired
    private DiagnosisRecordTeacherOpenService diagnosisRecordTeacherOpenService;
    @Autowired
    private DiagnosisRecordStudentOpenService diagnosisRecordStudentOpenService;
    @Test
    public void add(){
        DiagnosisClassRelationPo ar = null;
        List<DiagnosisClassRelationPo> list = new ArrayList<>();
//        for (int i = 0;i < 5;i++){

            ar = new DiagnosisClassRelationPo();
            ar.setCode("F1A2BEE8EA32F77633EA04ACD1669A6i");
            ar.setClassCode(321);
            ar.setSchoolCode(1);
            ar.setCreateTime(new Date());
            list.add(ar);
//        }
//        diagnosisClassRelationService.saveList(list);
        try {
            diagnosisClassRelationService.save(ar);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void  list(){
//        List<DiagnosisClassRelationPo> all = dia1gnosisClassRelationService.getAll();
//        Map<String,Object> queryMap = new HashMap<>();
//        queryMap.put("code","F1A2BEE8EA32F77633EA04ACD1669A62");
//        List<DiagnosisClassRelationPo> byCondition = diagnosisClassRelationService.findByCondition(queryMap);
        Object[] ids = {"F1A2BEE8EA32F77633EA04ACD1669A63","F1A2BEE8EA32F77633EA04ACD1669A64","F1A2BEE8EA32F77633EA04ACD1669A6i"};
        List<Object> list = Arrays.asList(ids);
        List<DiagnosisClassRelationPo> diagnosisClassRelationPos = null;
        try {
            diagnosisClassRelationPos = diagnosisClassRelationService.listByIds(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(diagnosisClassRelationPos.size());
    }

    @Test
    public void update(){
        List<DiagnosisClassRelationPo> list = new ArrayList<>();
        DiagnosisClassRelationPo dc = new DiagnosisClassRelationPo();
        dc.setCode("F1A2BEE8EA32F77633EA04ACD1669A60");
        dc.setDiagnosisRecordCode("2222BEE8EA32F77633EA04ACD1669A62");
        list.add(dc);

        DiagnosisClassRelationPo dc1 = new DiagnosisClassRelationPo();
        dc1.setCode("F1A2BEE8EA32F77633EA04ACD1669A61");
        dc1.setDiagnosisRecordCode("3332BEE8EA32F77633EA04ACD1669A62");
        list.add(dc1);

        DiagnosisClassRelationPo dc2 = new DiagnosisClassRelationPo();
        dc2.setCode("F1A2BEE8EA32F77633EA04ACD1669A62");
        dc2.setDiagnosisRecordCode("4442BEE8EA32F77633EA04ACD1669A62");
        list.add(dc2);

        try {
            diagnosisClassRelationService.updateList(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void delete(){
//        diagnosisClassRelationService.delete("F1A2BEE8EA32F77633EA04ACD1669A63");
        String[] ids = {"F1A2BEE8EA32F77633EA04ACD1669A63","F1A2BEE8EA32F77633EA04ACD1669A64","F1A2BEE8EA32F77633EA04ACD1669A6i"};
        try {
            diagnosisClassRelationService.delete(ids);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void trans(){
        DiagnosisRecordTeacherDto drtd = new DiagnosisRecordTeacherDto();
        drtd.setCode("EE8EA32F7763F1A2B3EA04ACD1669111");
        drtd.setCreateTime(new Date());
        try {
            diagnosisRecordTeacherOpenService.release(drtd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void release(){
        DiagnosisRecordTeacherDto dto = new DiagnosisRecordTeacherDto();
        dto.setCode("1132F3EA04ACD11237B3337E1163F1A2");
        dto.setCreateTime(new Date());
        try {
            diagnosisRecordTeacherOpenService.release(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void listDto(){
        try {
            DiagnosisRecordTeacherDto dto = new DiagnosisRecordTeacherDto();
            PageInfo<DiagnosisRecordTeacherDto> diagnosisList = diagnosisRecordTeacherOpenService.getDiagnosisList(dto, 1, 2, null);
            System.out.println(JSONObject.toJSON(diagnosisList));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test4(){
        DiagnosisRecordStudentDto drDto = new DiagnosisRecordStudentDto();
//                drDto.setGradeCode(model.getGradeCode());
        drDto.setSubjectCode(3);
        drDto.setClassCode(759);
        drDto.setDiagnosisTeacherRecordCode("389B3B6E352F431DA38E44B05DFD2503");
        diagnosisRecordStudentOpenService.getClassAverage(drDto);
    }
}
