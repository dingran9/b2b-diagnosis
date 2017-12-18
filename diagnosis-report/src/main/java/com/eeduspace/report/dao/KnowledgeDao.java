package com.eeduspace.report.dao;

import com.eeduspace.report.po.KnowledgePo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>描述 知识点统计</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/17 9:11
 **/
public interface KnowledgeDao extends JpaRepository<KnowledgePo,Integer>{
}
