package com.eeduspace.report.service.impl;

import com.eeduspace.report.dao.ReleaseExamDao;
import com.eeduspace.report.po.ReleaseExamPo;
import com.eeduspace.report.service.ReleaseExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>描述</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/17 9:31
 **/
@Service
public class ReleaseExamServiceImpl implements ReleaseExamService{
    @Autowired
    private ReleaseExamDao releaseExamDao;
    @Override
    public ReleaseExamPo save(ReleaseExamPo releaseExamPo) {
        return releaseExamDao.save(releaseExamPo);
    }

    /**
     * 获取考试发布记录
     *
     * @param releaseExamCode 考试发布记录code
     * @return
     */
    @Override
    public ReleaseExamPo findByReleaseExamCode(String releaseExamCode) {
        return releaseExamDao.findByReleaseCode(releaseExamCode);
    }
}
