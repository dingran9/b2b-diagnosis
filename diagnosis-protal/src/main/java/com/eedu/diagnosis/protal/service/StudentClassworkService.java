package com.eedu.diagnosis.protal.service;

import com.eedu.diagnosis.protal.model.request.StudentModel;

import java.util.List;
import java.util.Map;

/**
 * Created by dqy on 2017/3/21.
 */
public interface StudentClassworkService {
    /**
     * 初始化作业主页面
     * @param model
     * @return
     */
    List<Map<String,Object>> initClasswork(StudentModel model);

    /**
     * 按学科获取学生的作业列表
     * @param model
     * @return
     */
//    List<StudentRecordModel> classworkList(StudentModel model);
}
