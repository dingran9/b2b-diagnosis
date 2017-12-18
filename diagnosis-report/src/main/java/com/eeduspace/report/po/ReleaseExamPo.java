package com.eeduspace.report.po;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by zhuchaowei on 2017/3/15.
 */
@Entity
@Table(name = "edu_release_exam", schema = "b2b_report", catalog = "")
public class ReleaseExamPo {

    private int code;
    private String releaseCode;
    private String releaseName;
    private Timestamp createTime;
    private Timestamp updateTime;
    private String lastReleaseCode;
    private String examType;
    /**
     * 学期
     */
    private String semester;
    private Double totalScore;
    @Basic
    @Column(name = "total_score")
    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

    @Basic
    @Column(name = "semester")
    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    @Basic
    @Column(name = "exam_type")
    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    @Basic
    @Column(name = "last_release_code")
    public String getLastReleaseCode() {
        return lastReleaseCode;
    }

    public void setLastReleaseCode(String lastReleaseCode) {
        this.lastReleaseCode = lastReleaseCode;
    }

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
    @Column(name = "release_code")
    public String getReleaseCode() {
        return releaseCode;
    }

    public void setReleaseCode(String releaseCode) {
        this.releaseCode = releaseCode;
    }

    @Basic
    @Column(name = "release_name")
    public String getReleaseName() {
        return releaseName;
    }

    public void setReleaseName(String releaseName) {
        this.releaseName = releaseName;
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

        ReleaseExamPo that = (ReleaseExamPo) o;

        if (code != that.code) return false;
        if (releaseCode != null ? !releaseCode.equals(that.releaseCode) : that.releaseCode != null) return false;
        if (releaseName != null ? !releaseName.equals(that.releaseName) : that.releaseName != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = code;
        result = 31 * result + (releaseCode != null ? releaseCode.hashCode() : 0);
        result = 31 * result + (releaseName != null ? releaseName.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
    }
}
