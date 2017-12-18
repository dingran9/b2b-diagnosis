package com.eeduspace.b2b.report.model.report;

import lombok.Data;

import java.io.Serializable;

/**
 * 错误知识点排行
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-03-31 11:10
 **/
@Data
public class WrongKnowledgeRankModel implements Serializable{
    private String knowledgeCode;
    private String knowledgeName;
    private Integer wrongCount;
}
