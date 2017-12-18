package com.eeduspace.report.po;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by zhuchaowei on 2017/3/15.
 */
@Entity
@Table(name = "edu_knowledge", schema = "b2b_report", catalog = "")
public class KnowledgePo {
    private int code;
    private String knowledgeName;
    private String knowledgeCode;
    private Integer isRight;
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

    @Basic
    @Column(name = "knowledge_name")
    public String getKnowledgeName() {
        return knowledgeName;
    }

    public void setKnowledgeName(String knowledgeName) {
        this.knowledgeName = knowledgeName;
    }

    @Basic
    @Column(name = "knowledge_code")
    public String getKnowledgeCode() {
        return knowledgeCode;
    }

    public void setKnowledgeCode(String knowledgeCode) {
        this.knowledgeCode = knowledgeCode;
    }



    @Basic
    @Column(name = "is_right")
    public Integer getIsRight() {
        return isRight;
    }

    public void setIsRight(Integer isRight) {
        this.isRight = isRight;
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
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "update_time")
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KnowledgePo that = (KnowledgePo) o;

        if (code != that.code) return false;
        if (knowledgeName != null ? !knowledgeName.equals(that.knowledgeName) : that.knowledgeName != null)
            return false;
        if (knowledgeCode != null ? !knowledgeCode.equals(that.knowledgeCode) : that.knowledgeCode != null)
            return false;


        if (isRight != null ? !isRight.equals(that.isRight) : that.isRight != null) return false;
        if (examCode != null ? !examCode.equals(that.examCode) : that.examCode != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = code;
        result = 31 * result + (knowledgeName != null ? knowledgeName.hashCode() : 0);
        result = 31 * result + (knowledgeCode != null ? knowledgeCode.hashCode() : 0);
        result = 31 * result + (isRight != null ? isRight.hashCode() : 0);
        result = 31 * result + (examCode != null ? examCode.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
    }

}
