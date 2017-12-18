package com.eeduspace.b2b.report.model.question;

import java.io.Serializable;
import java.util.List;

/**
 * <p>描述 教材树知识点信息</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/16 16:49
 * @param    
 * @return   
**/
public class KnowledgeModel implements Serializable{
    /**
     * 教材树知识点名称
     */
    private String knowledgeName;
    /**
     * 教材树知识点code
     */
    private String knowledgeCode;
    /**
     *教材树产生式
     */
    private List<TeachingMaterialsProductionModel>  teachingMaterialsProdutions;
    /**
     * 是否正确  0 false  1 true
     */
    private Integer isTrue;
    public String getKnowledgeName() {
        return knowledgeName;
    }

    public void setKnowledgeName(String knowledgeName) {
        this.knowledgeName = knowledgeName;
    }

    public String getKnowledgeCode() {
        return knowledgeCode;
    }

    public void setKnowledgeCode(String knowledgeCode) {
        this.knowledgeCode = knowledgeCode;
    }

    public List<TeachingMaterialsProductionModel> getTeachingMaterialsProdutions() {
        return teachingMaterialsProdutions;
    }

    public void setTeachingMaterialsProdutions(List<TeachingMaterialsProductionModel> teachingMaterialsProdutions) {
        this.teachingMaterialsProdutions = teachingMaterialsProdutions;
    }

    public Integer getIsTrue() {
        return isTrue;
    }

    public void setIsTrue(Integer isTrue) {
        this.isTrue = isTrue;
    }

}
