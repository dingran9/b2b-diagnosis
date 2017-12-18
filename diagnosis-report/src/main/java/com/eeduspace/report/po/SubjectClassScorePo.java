package com.eeduspace.report.po;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by zhuchaowei on 2017/3/15.
 */
@Entity
@Table(name = "edu_subject_class_score", schema = "b2b_report", catalog = "")
public class SubjectClassScorePo {
    private int code;
    private Integer rank;
    private Double averageScore;
    private Integer releaseCode;
    private String classCode;
    private String className;
    private String subjectCode;
    private String subjectName;
    private String schoolCode;
    private String schoolName;
    private Timestamp createTime;
    private Timestamp updateTime;
    private String passingRate;
    private String excellentRate;
    private Double highestScore;
    private Double lowestScore;
    private String failRate;
    private String classComplianceRate;

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
    @Column(name = "rank")
    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Basic
    @Column(name = "average_score")
    public Double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }

    @Basic
    @Column(name = "release_code")
    public Integer getReleaseCode() {
        return releaseCode;
    }

    public void setReleaseCode(Integer releaseCode) {
        this.releaseCode = releaseCode;
    }

    @Basic
    @Column(name = "class_code")
    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    @Basic
    @Column(name = "class_name")
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Basic
    @Column(name = "subject_code")
    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    @Basic
    @Column(name = "subject_name")
    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Basic
    @Column(name = "school_code")
    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    @Basic
    @Column(name = "school_name")
    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
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
    @Column(name = "passing_rate")
    public String getPassingRate() {
        return passingRate;
    }

    public void setPassingRate(String passingRate) {
        this.passingRate = passingRate;
    }

    @Basic
    @Column(name = "excellent_rate")
    public String getExcellentRate() {
        return excellentRate;
    }

    public void setExcellentRate(String excellentRate) {
        this.excellentRate = excellentRate;
    }

    @Basic
    @Column(name = "highest_score")
    public Double getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(Double highestScore) {
        this.highestScore = highestScore;
    }

    @Basic
    @Column(name = "lowest_score")
    public Double getLowestScore() {
        return lowestScore;
    }

    public void setLowestScore(Double lowestScore) {
        this.lowestScore = lowestScore;
    }

    @Basic
    @Column(name = "fail_rate")
    public String getFailRate() {
        return failRate;
    }

    public void setFailRate(String failRate) {
        this.failRate = failRate;
    }

    @Basic
    @Column(name = "class_compliance_rate")
    public String getClassComplianceRate() {
        return classComplianceRate;
    }

    public void setClassComplianceRate(String classComplianceRate) {
        this.classComplianceRate = classComplianceRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubjectClassScorePo that = (SubjectClassScorePo) o;

        if (code != that.code) return false;
        if (rank != null ? !rank.equals(that.rank) : that.rank != null) return false;
        if (averageScore != null ? !averageScore.equals(that.averageScore) : that.averageScore != null) return false;
        if (releaseCode != null ? !releaseCode.equals(that.releaseCode) : that.releaseCode != null) return false;
        if (classCode != null ? !classCode.equals(that.classCode) : that.classCode != null) return false;
        if (className != null ? !className.equals(that.className) : that.className != null) return false;
        if (subjectCode != null ? !subjectCode.equals(that.subjectCode) : that.subjectCode != null) return false;
        if (subjectName != null ? !subjectName.equals(that.subjectName) : that.subjectName != null) return false;
        if (schoolCode != null ? !schoolCode.equals(that.schoolCode) : that.schoolCode != null) return false;
        if (schoolName != null ? !schoolName.equals(that.schoolName) : that.schoolName != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (passingRate != null ? !passingRate.equals(that.passingRate) : that.passingRate != null) return false;
        if (excellentRate != null ? !excellentRate.equals(that.excellentRate) : that.excellentRate != null)
            return false;
        if (highestScore != null ? !highestScore.equals(that.highestScore) : that.highestScore != null) return false;
        if (lowestScore != null ? !lowestScore.equals(that.lowestScore) : that.lowestScore != null) return false;
        if (failRate != null ? !failRate.equals(that.failRate) : that.failRate != null) return false;
        if (classComplianceRate != null ? !classComplianceRate.equals(that.classComplianceRate) : that.classComplianceRate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = code;
        result = 31 * result + (rank != null ? rank.hashCode() : 0);
        result = 31 * result + (averageScore != null ? averageScore.hashCode() : 0);
        result = 31 * result + (releaseCode != null ? releaseCode.hashCode() : 0);
        result = 31 * result + (classCode != null ? classCode.hashCode() : 0);
        result = 31 * result + (className != null ? className.hashCode() : 0);
        result = 31 * result + (subjectCode != null ? subjectCode.hashCode() : 0);
        result = 31 * result + (subjectName != null ? subjectName.hashCode() : 0);
        result = 31 * result + (schoolCode != null ? schoolCode.hashCode() : 0);
        result = 31 * result + (schoolName != null ? schoolName.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (passingRate != null ? passingRate.hashCode() : 0);
        result = 31 * result + (excellentRate != null ? excellentRate.hashCode() : 0);
        result = 31 * result + (highestScore != null ? highestScore.hashCode() : 0);
        result = 31 * result + (lowestScore != null ? lowestScore.hashCode() : 0);
        result = 31 * result + (failRate != null ? failRate.hashCode() : 0);
        result = 31 * result + (classComplianceRate != null ? classComplianceRate.hashCode() : 0);
        return result;
    }
}
