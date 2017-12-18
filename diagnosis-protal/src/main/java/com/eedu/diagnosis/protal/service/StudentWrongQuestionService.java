package com.eedu.diagnosis.protal.service;

import com.eedu.diagnosis.protal.model.request.StudentModel;
import com.eedu.diagnosis.protal.model.request.WrongQuestionRequestModel;
import com.eedu.diagnosis.protal.model.response.WrongQuestionModel;
import com.github.pagehelper.PageInfo;

/**
 * Created by dqy on 2017/3/27.
 */
public interface StudentWrongQuestionService {
    /**
     * 根据学科code 学生id 获取错题记录
     * @param model
     * @return
     */
    PageInfo<WrongQuestionModel> getWrongQuestions(StudentModel model) throws Exception;

    void delWrongQuestions(WrongQuestionRequestModel model) throws Exception;
}
