package com.eeduspace.report.po;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by zhuchaowei on 2017/3/15.
 */
@Entity
@Table(name = "edu_class_question", schema = "b2b_report", catalog = "")
public class ClassQuestionPo {
    private int code;
    private String questionSn;
    private Integer isRight;
    private Integer examCode;
    private Timestamp createTime;
    private Timestamp updateTime;
    private String classCode;
    private String className;
    private String gradeCode;
    private String gradeName;
    private String schoolCode;
    private String schoolName;

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
    @Column(name = "question_sn")
    public String getQuestionSn() {
        return questionSn;
    }

    public void setQuestionSn(String questionSn) {
        this.questionSn = questionSn;
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
    @Column(name = "grade_code")
    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    @Basic
    @Column(name = "grade_name")
    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClassQuestionPo that = (ClassQuestionPo) o;

        if (code != that.code) return false;
        if (questionSn != null ? !questionSn.equals(that.questionSn) : that.questionSn != null) return false;
        if (isRight != null ? !isRight.equals(that.isRight) : that.isRight != null) return false;
        if (examCode != null ? !examCode.equals(that.examCode) : that.examCode != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (classCode != null ? !classCode.equals(that.classCode) : that.classCode != null) return false;
        if (className != null ? !className.equals(that.className) : that.className != null) return false;
        if (gradeCode != null ? !gradeCode.equals(that.gradeCode) : that.gradeCode != null) return false;
        if (gradeName != null ? !gradeName.equals(that.gradeName) : that.gradeName != null) return false;
        if (schoolCode != null ? !schoolCode.equals(that.schoolCode) : that.schoolCode != null) return false;
        if (schoolName != null ? !schoolName.equals(that.schoolName) : that.schoolName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = code;
        result = 31 * result + (questionSn != null ? questionSn.hashCode() : 0);
        result = 31 * result + (isRight != null ? isRight.hashCode() : 0);
        result = 31 * result + (examCode != null ? examCode.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (classCode != null ? classCode.hashCode() : 0);
        result = 31 * result + (className != null ? className.hashCode() : 0);
        result = 31 * result + (gradeCode != null ? gradeCode.hashCode() : 0);
        result = 31 * result + (gradeName != null ? gradeName.hashCode() : 0);
        result = 31 * result + (schoolCode != null ? schoolCode.hashCode() : 0);
        result = 31 * result + (schoolName != null ? schoolName.hashCode() : 0);
        return result;
    }
}
