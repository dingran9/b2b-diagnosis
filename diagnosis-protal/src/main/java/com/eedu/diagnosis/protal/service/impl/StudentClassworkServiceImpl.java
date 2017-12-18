package com.eedu.diagnosis.protal.service.impl;

import com.eedu.diagnosis.exam.api.openService.DiagnosisRecordStudentOpenService;
import com.eedu.diagnosis.protal.model.request.StudentModel;
import com.eedu.diagnosis.protal.service.StudentClassworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dqy on 2017/3/21.
 */
@Service
public class StudentClassworkServiceImpl implements StudentClassworkService {

    @Autowired
    private DiagnosisRecordStudentOpenService diagnosisRecordStudentOpenService;


    @Override
    public List<Map<String, Object>> initClasswork(StudentModel model) {
        List<Map<String, Object>> result = new ArrayList<>();

        return result;
    }

//    @Override
//    public List<StudentRecordModel> classworkList(StudentModel model) {
//        return null;
//    }
}
