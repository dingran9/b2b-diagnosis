/**
  * Copyright 2017 bejson.com 
  */
package com.eeduspace.b2b.report.model.question;
import java.io.Serializable;
import java.util.List;

/**
 * <p>描述 试题</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/16 14:58
 * @param    
 * @return   
**/
public class Questions implements Serializable{
    /**
     * 试题code
     */
    private String questionId;

    /**
     * 产生式信息
     */
    private List<Productionmodels> productionModels;

    private double score;

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public List<Productionmodels> getProductionModels() {
        return productionModels;
    }

    public void setProductionModels(List<Productionmodels> productionModels) {
        this.productionModels = productionModels;
    }
}