package com.eeduspace.b2b.report.model;

import com.eeduspace.b2b.report.model.question.KnowledgeModel;
import com.eeduspace.b2b.report.model.question.Productionmodels;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * <p>描述  学生单个答题结果信息</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/16 16:22
**/
public class StudentQuestionAnswerResultDto implements Serializable{

    /**code*/
    private Integer code;

    /**答题结果 对错  0错  1对*/
    @NotNull(message = "answerResult is null")
    private Integer answerResult;

    /**本题得分 */
    @NotNull(message = "score is null")
    private Double score;

    /**是否复合题 0否 1是*/
    @NotNull(message = "isComplex is null")
    private Integer isComplex;

    /**试题题号*/
   @NotBlank(message = "questionSn is null")
    private String questionSn;

    /**基础树产生式信息*/
    @NotEmpty(message = "productionmodelsList is null")
    private List<Productionmodels> productionmodelsList;

    /**教材树知识点信息*/
    @NotEmpty(message = "knowledgeModelList is null")
    private List<KnowledgeModel> knowledgeModelList;

    /**试题code*/
    @NotBlank(message = "questionCode is null")
    private String questionCode;

    /**复合题小题code */
    private String complexQuestionCode;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getAnswerResult() {
        return answerResult;
    }

    public void setAnswerResult(Integer answerResult) {
        this.answerResult = answerResult;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getIsComplex() {
        return isComplex;
    }

    public void setIsComplex(Integer isComplex) {
        this.isComplex = isComplex;
    }

    public String getQuestionSn() {
        return questionSn;
    }

    public void setQuestionSn(String questionSn) {
        this.questionSn = questionSn;
    }

    public List<Productionmodels> getProductionmodelsList() {
        return productionmodelsList;
    }

    public void setProductionmodelsList(List<Productionmodels> productionmodelsList) {
        this.productionmodelsList = productionmodelsList;
    }

    public List<KnowledgeModel> getKnowledgeModelList() {
        return knowledgeModelList;
    }

    public void setKnowledgeModelList(List<KnowledgeModel> knowledgeModelList) {
        this.knowledgeModelList = knowledgeModelList;
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    public String getComplexQuestionCode() {
        return complexQuestionCode;
    }

    public void setComplexQuestionCode(String complexQuestionCode) {
        this.complexQuestionCode = complexQuestionCode;
    }
}
