package com.eeduspace.report.po;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by zhuchaowei on 2017/3/15.
 */
@Entity
@Table(name = "edu_student_subject_score", schema = "b2b_report", catalog = "")
public class StudentSubjectScorePo {
    private int code;
    private Double score;
    private Integer examCode;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Double paperScore;
    /**
     * 达标分数线
     */
    private Double standardScore;
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
    @Column(name = "score")
    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
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

    @Basic
    @Column(name = "standard_score")
    public Double getStandardScore() {
        return standardScore;
    }

    public void setStandardScore(Double standardScore) {
        this.standardScore = standardScore;
    }
    @Basic
    @Column(name = "paper_score")
    public Double getPaperScore() {
        return paperScore;
    }

    public void setPaperScore(Double paperScore) {
        this.paperScore = paperScore;
    }
}
