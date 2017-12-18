package com.eeduspace.report.po;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 知识点模块
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-03-17 11:42
 **/
@Entity
@Table(name = "edu_knowledge_module", schema = "b2b_report", catalog = "")
public class KnowledgeModulePo {
    private int code;
    private String knowledgeModuleName;
    private String knowledgeModuleCode;
    private Double knowledgeModuleScore;
    private Integer examCode;
    private Timestamp createTime;
    private Timestamp updateTime;
    @Id
    @Column(name = "code")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    @Column(name = "knowledge_module_name")
    public String getKnowledgeModuleName() {
        return knowledgeModuleName;
    }

    public void setKnowledgeModuleName(String knowledgeModuleName) {
        this.knowledgeModuleName = knowledgeModuleName;
    }

    @Column(name = "knowledge_module_code")
    public String getKnowledgeModuleCode() {
        return knowledgeModuleCode;
    }

    public void setKnowledgeModuleCode(String knowledgeModuleCode) {
        this.knowledgeModuleCode = knowledgeModuleCode;
    }
    @Column(name = "knowledge_module_score")
    public Double getKnowledgeModuleScore() {
        return knowledgeModuleScore;
    }

    public void setKnowledgeModuleScore(Double knowledgeModuleScore) {
        this.knowledgeModuleScore = knowledgeModuleScore;
    }

    @Column(name = "exam_code")
    public Integer getExamCode() {
        return examCode;
    }

    public void setExamCode(Integer examCode) {
        this.examCode = examCode;
    }
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
    @Column(name = "update_time")
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
