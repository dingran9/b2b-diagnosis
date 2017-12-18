package com.eeduspace.report.dao;

import com.eeduspace.report.po.ReleaseExamPo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>描述 考试发布记录</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/17 9:11
 **/
public interface ReleaseExamDao extends JpaRepository<ReleaseExamPo,Integer>{
    /**
     * 获取考试发布记录
     * @param releaseExamCode 发布考试记录code
     * @return
     */
    ReleaseExamPo findByReleaseCode(String releaseExamCode);
}
