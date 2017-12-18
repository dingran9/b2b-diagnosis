package com.eeduspace.report.dao;

import com.eeduspace.report.po.ExamPo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * <p>描述 </p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/17 9:11
**/
public interface ExamDao extends JpaRepository<ExamPo,Integer>{

        List<ExamPo> findByReleaseExamCodeIn(List<Integer> codes);
}
