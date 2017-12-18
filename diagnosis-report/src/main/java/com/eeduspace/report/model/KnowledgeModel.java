package com.eeduspace.report.model;

import lombok.Data;

/**
 * 知识点
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-03-21 14:25
 **/
@Data
public class KnowledgeModel {

    private String knowledgeName;
    private String knowledgeCode;
    private String classCode;
    private String className;
    private Integer isRight;
    private String userCode;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KnowledgeModel)) return false;

        KnowledgeModel that = (KnowledgeModel) o;

        return getUserCode().equals(that.getUserCode());

    }

    @Override
    public int hashCode() {
        return  (userCode != null ? userCode.hashCode() : 0);
    }


}
