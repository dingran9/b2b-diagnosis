package com.eedu.diagnosis.manager.model.request;

import com.eedu.diagnosis.manager.model.BaseModel;

import java.util.Date;
import java.util.List;

/**
 * Created by dqy on 2017/3/20.
 */
public class TeacherModel  extends BaseModel {
    /**
     * 教师发布测试的记录code
     */
    private String code;

    private Integer teacherCode;

    private String teacherName;

    private Date createTime;

    private Integer schoolCode;

    private String schoolName;

    private Integer stageCode;

    private Integer gradeCode;

    private Integer subjectCode;

    private Integer classCode;

    /**所教班级codes*/
    private List<Integer> classCodes;

    private List<Integer> subjectCodes;

    private Integer diagnosisType;

    private String examYear;

    public String getExamYear() {
        return examYear;
    }

    public void setExamYear(String examYear) {
        this.examYear = examYear;
    }

    public Integer getDiagnosisType() {
        return diagnosisType;
    }

    public void setDiagnosisType(Integer diagnosisType) {
        this.diagnosisType = diagnosisType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getTeacherCode() {
        return teacherCode;
    }

    public void setTeacherCode(Integer teacherCode) {
        this.teacherCode = teacherCode;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(Integer schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Integer getStageCode() {
        return stageCode;
    }

    public void setStageCode(Integer stageCode) {
        this.stageCode = stageCode;
    }

    public Integer getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(Integer gradeCode) {
        this.gradeCode = gradeCode;
    }

    public Integer getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(Integer subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Integer getClassCode() {
        return classCode;
    }

    public void setClassCode(Integer classCode) {
        this.classCode = classCode;
    }

    public List<Integer> getClassCodes() {
        return classCodes;
    }

    public void setClassCodes(List<Integer> classCodes) {
        this.classCodes = classCodes;
    }

    public List<Integer> getSubjectCodes() {
        return subjectCodes;
    }

    public void setSubjectCodes(List<Integer> subjectCodes) {
        this.subjectCodes = subjectCodes;
    }
}
