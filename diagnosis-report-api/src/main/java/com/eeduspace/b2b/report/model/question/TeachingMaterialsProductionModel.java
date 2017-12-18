package com.eeduspace.b2b.report.model.question;

import java.io.Serializable;

/**
 * <p>描述 教材树产生式</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/16 20:20
 * @param    
 * @return   
**/
public class TeachingMaterialsProductionModel implements Serializable{
    /**
     * 基础树 产生式code
     */
    private String baseCode;
    /**
     * 产生式类型
     */
    private String typecode;
    /**
     * 教材树产生式code
     */
    private String teachingMaterialsCode;
    /**
     * 对错  0 false 1 true
     */
    private Integer isTrue;
    /**
     * 教材树产生式名称
     */
    private String teachingMaterialName;

    public String getBaseCode() {
        return baseCode;
    }

    public void setBaseCode(String baseCode) {
        this.baseCode = baseCode;
    }

    public Integer getIsTrue() {
        return isTrue;
    }

    public void setIsTrue(Integer isTrue) {
        this.isTrue = isTrue;
    }

    public String getTypecode() {
        return typecode;
    }

    public void setTypecode(String typecode) {
        this.typecode = typecode;
    }

    public String getTeachingMaterialsCode() {
        return teachingMaterialsCode;
    }

    public void setTeachingMaterialsCode(String teachingMaterialsCode) {
        this.teachingMaterialsCode = teachingMaterialsCode;
    }

    public String getTeachingMaterialName() {
        return teachingMaterialName;
    }

    public void setTeachingMaterialName(String teachingMaterialName) {
        this.teachingMaterialName = teachingMaterialName;
    }

}
