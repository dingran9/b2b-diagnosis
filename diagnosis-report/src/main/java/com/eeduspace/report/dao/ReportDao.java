package com.eeduspace.report.dao;

import com.eeduspace.report.po.ReportPo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>描述 报告dao</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/16 15:56
**/
public interface ReportDao extends JpaRepository<ReportPo,Integer> {
    /**
     * 根据答卷记录code获取 试卷报告
     * @param makePaperRecordCode
     * @return ReportPo 报告实体
     */
    ReportPo findByMakePaperRecordCode(String makePaperRecordCode);
}
