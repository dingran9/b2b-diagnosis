package com.eeduspace.report.po;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by zhuchaowei on 2017/3/15.
 */
@Entity
@Table(name = "edu_exam", schema = "b2b_report", catalog = "")
public class ExamPo {
    private int code;
    private Integer releaseExamCode;
    private String schoolCode;
    private String schoolName;
    private String classCode;
    private String className;
    private String gradeCode;
    private String gradeName;
    private String subjectName;
    private String subjectCode;
    private String teacherName;
    private String teacherCode;
    private String userCode;
    private String userName;
    private String userMakePaperCode;
    private String paperName;
    private String paperCode;
    private Timestamp createTime;
    private Timestamp updateTime;
    private String bookTypeVersionCode;
    private String bookTypeVersionName;
    /**
     * 文理类型 0 无  1文  2理
     */
    private Integer artType;
    @Basic
    @Column(name = "art_type")
    public Integer getArtType() {
        return artType;
    }

    public void setArtType(Integer artType) {
        this.artType = artType;
    }

    @Basic
    @Column(name = "book_type_version_code")
    public String getBookTypeVersionCode() {
        return bookTypeVersionCode;
    }

    public void setBookTypeVersionCode(String bookTypeVersionCode) {
        this.bookTypeVersionCode = bookTypeVersionCode;
    }
    @Basic
    @Column(name = "book_type_version_name")
    public String getBookTypeVersionName() {
        return bookTypeVersionName;
    }

    public void setBookTypeVersionName(String bookTypeVersionName) {
        this.bookTypeVersionName = bookTypeVersionName;
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
    @Column(name = "release_exam_code")
    public Integer getReleaseExamCode() {
        return releaseExamCode;
    }

    public void setReleaseExamCode(Integer releaseExamCode) {
        this.releaseExamCode = releaseExamCode;
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
    @Column(name = "subject_name")
    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
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
    @Column(name = "teacher_name")
    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    @Basic
    @Column(name = "teacher_code")
    public String getTeacherCode() {
        return teacherCode;
    }

    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
    }

    @Basic
    @Column(name = "user_code")
    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    @Basic
    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "user_make_paper_code")
    public String getUserMakePaperCode() {
        return userMakePaperCode;
    }

    public void setUserMakePaperCode(String userMakePaperCode) {
        this.userMakePaperCode = userMakePaperCode;
    }

    @Basic
    @Column(name = "paper_name")
    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    @Basic
    @Column(name = "paper_code")
    public String getPaperCode() {
        return paperCode;
    }

    public void setPaperCode(String paperCode) {
        this.paperCode = paperCode;
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

        ExamPo examPo = (ExamPo) o;

        if (code != examPo.code) return false;
        if (releaseExamCode != null ? !releaseExamCode.equals(examPo.releaseExamCode) : examPo.releaseExamCode != null)
            return false;
        if (schoolCode != null ? !schoolCode.equals(examPo.schoolCode) : examPo.schoolCode != null) return false;
        if (schoolName != null ? !schoolName.equals(examPo.schoolName) : examPo.schoolName != null) return false;
        if (classCode != null ? !classCode.equals(examPo.classCode) : examPo.classCode != null) return false;
        if (className != null ? !className.equals(examPo.className) : examPo.className != null) return false;
        if (gradeCode != null ? !gradeCode.equals(examPo.gradeCode) : examPo.gradeCode != null) return false;
        if (gradeName != null ? !gradeName.equals(examPo.gradeName) : examPo.gradeName != null) return false;
        if (subjectName != null ? !subjectName.equals(examPo.subjectName) : examPo.subjectName != null) return false;
        if (subjectCode != null ? !subjectCode.equals(examPo.subjectCode) : examPo.subjectCode != null) return false;
        if (teacherName != null ? !teacherName.equals(examPo.teacherName) : examPo.teacherName != null) return false;
        if (teacherCode != null ? !teacherCode.equals(examPo.teacherCode) : examPo.teacherCode != null) return false;
        if (userCode != null ? !userCode.equals(examPo.userCode) : examPo.userCode != null) return false;
        if (userName != null ? !userName.equals(examPo.userName) : examPo.userName != null) return false;
        if (userMakePaperCode != null ? !userMakePaperCode.equals(examPo.userMakePaperCode) : examPo.userMakePaperCode != null)
            return false;
        if (paperName != null ? !paperName.equals(examPo.paperName) : examPo.paperName != null) return false;
        if (paperCode != null ? !paperCode.equals(examPo.paperCode) : examPo.paperCode != null) return false;
        if (createTime != null ? !createTime.equals(examPo.createTime) : examPo.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(examPo.updateTime) : examPo.updateTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = code;
        result = 31 * result + (releaseExamCode != null ? releaseExamCode.hashCode() : 0);
        result = 31 * result + (schoolCode != null ? schoolCode.hashCode() : 0);
        result = 31 * result + (schoolName != null ? schoolName.hashCode() : 0);
        result = 31 * result + (classCode != null ? classCode.hashCode() : 0);
        result = 31 * result + (className != null ? className.hashCode() : 0);
        result = 31 * result + (gradeCode != null ? gradeCode.hashCode() : 0);
        result = 31 * result + (gradeName != null ? gradeName.hashCode() : 0);
        result = 31 * result + (subjectName != null ? subjectName.hashCode() : 0);
        result = 31 * result + (subjectCode != null ? subjectCode.hashCode() : 0);
        result = 31 * result + (teacherName != null ? teacherName.hashCode() : 0);
        result = 31 * result + (teacherCode != null ? teacherCode.hashCode() : 0);
        result = 31 * result + (userCode != null ? userCode.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (userMakePaperCode != null ? userMakePaperCode.hashCode() : 0);
        result = 31 * result + (paperName != null ? paperName.hashCode() : 0);
        result = 31 * result + (paperCode != null ? paperCode.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
    }
}
