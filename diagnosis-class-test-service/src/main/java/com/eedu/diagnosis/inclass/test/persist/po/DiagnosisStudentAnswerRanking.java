package com.eedu.diagnosis.inclass.test.persist.po;

import java.util.Date;

public class DiagnosisStudentAnswerRanking {
    private String studentAnswerRankingCode;

    private Date createTime;

    private String inClassTestCode;

    private String studentName;

    private String studentCode;

    private Integer rank;

    public String getStudentAnswerRankingCode() {
        return studentAnswerRankingCode;
    }

    public void setStudentAnswerRankingCode(String studentAnswerRankingCode) {
        this.studentAnswerRankingCode = studentAnswerRankingCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getInClassTestCode() {
        return inClassTestCode;
    }

    public void setInClassTestCode(String inClassTestCode) {
        this.inClassTestCode = inClassTestCode;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}