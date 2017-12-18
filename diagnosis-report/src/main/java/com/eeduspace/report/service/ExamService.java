package com.eeduspace.report.service;

import com.eeduspace.report.model.TeacherModel;
import com.eeduspace.report.po.ExamPo;

import java.util.List;

/**
 * <p>描述</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/17 9:31
 */
public interface ExamService {
    /**
     * 保存
     * @param examPo
     * @return
     */
    ExamPo save(ExamPo examPo);

    public List<TeacherModel> getTeacherByReleaseExamCode(String releaseExamCode) throws Exception;

}


