package com.eeduspace.report.po;

import javax.persistence.*;

/**
 * Created by zhuchaowei on 2017/3/15.
 */
@Entity
@Table(name = "edu_user_answer_result", schema = "b2b_report", catalog = "")
public class UserAnswerResultPo {
    private int code;
    private Integer answerResult;
    private Double score;
    private Integer isComplex;
    private String questionSn;
    private Integer examCode;
    private String productionJson;
    private String knowledgeJson;
    private String questionCode;
    private String complexQuestionCode;

    @Id
    @Column(name = "code")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Basic
    @Column(name = "answer_result")
    public Integer getAnswerResult() {
        return answerResult;
    }

    public void setAnswerResult(Integer answerResult) {
        this.answerResult = answerResult;
    }

    @Basic
    @Column(name = "score")
    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Basic
    @Column(name = "is_complex")
    public Integer getIsComplex() {
        return isComplex;
    }

    public void setIsComplex(Integer isComplex) {
        this.isComplex = isComplex;
    }

    @Basic
    @Column(name = "question_sn")
    public String getQuestionSn() {
        return questionSn;
    }

    public void setQuestionSn(String questionSn) {
        this.questionSn = questionSn;
    }

    @Basic
    @Column(name = "exam_code")
    public Integer getExamCode() {
        return examCode;
    }

    public void setExamCode(Integer examCode) {
        this.examCode = examCode;
    }

    @Basic
    @Column(name = "production_json")
    public String getProductionJson() {
        return productionJson;
    }

    public void setProductionJson(String productionJson) {
        this.productionJson = productionJson;
    }

    @Basic
    @Column(name = "knowledge_json")
    public String getKnowledgeJson() {
        return knowledgeJson;
    }

    public void setKnowledgeJson(String knowledgeJson) {
        this.knowledgeJson = knowledgeJson;
    }

    @Basic
    @Column(name = "question_code")
    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    @Basic
    @Column(name = "complex_question_code")
    public String getComplexQuestionCode() {
        return complexQuestionCode;
    }

    public void setComplexQuestionCode(String complexQuestionCode) {
        this.complexQuestionCode = complexQuestionCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAnswerResultPo that = (UserAnswerResultPo) o;

        if (code != that.code) return false;
        if (answerResult != null ? !answerResult.equals(that.answerResult) : that.answerResult != null) return false;
        if (score != null ? !score.equals(that.score) : that.score != null) return false;
        if (isComplex != null ? !isComplex.equals(that.isComplex) : that.isComplex != null) return false;
        if (questionSn != null ? !questionSn.equals(that.questionSn) : that.questionSn != null) return false;
        if (examCode != null ? !examCode.equals(that.examCode) : that.examCode != null) return false;
        if (productionJson != null ? !productionJson.equals(that.productionJson) : that.productionJson != null)
            return false;
        if (knowledgeJson != null ? !knowledgeJson.equals(that.knowledgeJson) : that.knowledgeJson != null)
            return false;
        if (questionCode != null ? !questionCode.equals(that.questionCode) : that.questionCode != null) return false;
        if (complexQuestionCode != null ? !complexQuestionCode.equals(that.complexQuestionCode) : that.complexQuestionCode != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = code;
        result = 31 * result + (answerResult != null ? answerResult.hashCode() : 0);
        result = 31 * result + (score != null ? score.hashCode() : 0);
        result = 31 * result + (isComplex != null ? isComplex.hashCode() : 0);
        result = 31 * result + (questionSn != null ? questionSn.hashCode() : 0);
        result = 31 * result + (examCode != null ? examCode.hashCode() : 0);
        result = 31 * result + (productionJson != null ? productionJson.hashCode() : 0);
        result = 31 * result + (knowledgeJson != null ? knowledgeJson.hashCode() : 0);
        result = 31 * result + (questionCode != null ? questionCode.hashCode() : 0);
        result = 31 * result + (complexQuestionCode != null ? complexQuestionCode.hashCode() : 0);
        return result;
    }
}
