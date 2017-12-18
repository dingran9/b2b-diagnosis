package com.eeduspace.b2b.report.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 知识模块基础信息
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-10-09 11:53
 **/
@Data
public class BaseKnowledgeModuleDto implements Serializable{
    /**
     * 知识点模块名称
     */
    private String knowledgeModuleName;
    /**
     * 知识点模块code
     */
    private String knowledgeModuleCode;
    /**
     * 知识点模块得分
     */
    private Double score;
    /**
     * 所统计维度名称
     */
    private String ownerName;
    /**
     * 所统计维度code
     */
    private String ownerCode;


}
