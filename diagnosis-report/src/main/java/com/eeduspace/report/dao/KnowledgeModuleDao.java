package com.eeduspace.report.dao;

import com.eeduspace.report.po.KnowledgeModulePo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>描述 知识点模块</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/17 11:46
 * @param    
 * @return   
**/
public interface KnowledgeModuleDao extends JpaRepository<KnowledgeModulePo,Integer>{
}
