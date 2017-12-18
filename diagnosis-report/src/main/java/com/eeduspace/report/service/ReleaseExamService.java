package com.eeduspace.report.service;

import com.eeduspace.report.po.ReleaseExamPo;

/**
 * <p>描述</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/17 9:31
 **/
public interface ReleaseExamService {
    ReleaseExamPo save(ReleaseExamPo releaseExamPo);

    /**
     * 获取考试发布记录
     * @param releaseExamCode 考试发布记录code
     * @return
     */
    ReleaseExamPo findByReleaseExamCode(String releaseExamCode);
}
