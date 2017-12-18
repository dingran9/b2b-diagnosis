package com.eeduspace.report.dao;

import com.eeduspace.report.po.UserAnswerResultPo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>描述 用户答题结果表</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/17 17:08
 * @param    
 * @return   
**/
public interface UserAnswerResultDao extends JpaRepository<UserAnswerResultPo,Integer>{

}
