/**
  * Copyright 2017 bejson.com 
  */
package com.eeduspace.b2b.report.model.question;
import java.io.Serializable;
import java.util.List;

/**
 * <p>描述 基础树产生式信息</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/16 14:58
 * @param    
 * @return   
**/
public class Productionmodels implements Serializable{
    /**
     * 产生式名
     */
    private String basenameVo;
    private String basecodeVo;
    /**
     * 产生式类型
     */
    private String typecodeVo;
    private List<Sons> sons;
    private Integer isTrue;

    public String getBasenameVo() {
        return basenameVo;
    }

    public void setBasenameVo(String basenameVo) {
        this.basenameVo = basenameVo;
    }

    public String getBasecodeVo() {
        return basecodeVo;
    }

    public void setBasecodeVo(String basecodeVo) {
        this.basecodeVo = basecodeVo;
    }

    public String getTypecodeVo() {
        return typecodeVo;
    }

    public void setTypecodeVo(String typecodeVo) {
        this.typecodeVo = typecodeVo;
    }

    public List<Sons> getSons() {
        return sons;
    }

    public void setSons(List<Sons> sons) {
        this.sons = sons;
    }

    public Integer getIsTrue() {
        return isTrue;
    }

    public void setIsTrue(Integer isTrue) {
        this.isTrue = isTrue;
    }
}