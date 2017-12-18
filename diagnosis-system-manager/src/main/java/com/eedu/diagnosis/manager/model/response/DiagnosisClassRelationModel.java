package com.eedu.diagnosis.manager.model.response;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by liuhongfei on 2017/10/17.
 */
public class DiagnosisClassRelationModel implements Serializable{

    private String code;

    private String diagnosisRecordCode;

    private Integer schoolCode;

    private String schoolName;

    private Integer classCode;

    private String className;

    private Integer subjectCode;

    private Integer teacherCode;

    private String teacherName;

    private Date createTime;

    private List<Integer> classCodes;

    private List<String> diagnosisRecordCodes;

    private Integer hasReport;

    private Integer isRead;

    private Date startTime;

    public Integer getArtType() {
        return artType;
    }

    public void setArtType(Integer artType) {
        this.artType = artType;
    }

    public String getDiagnosisPaperCode() {
        return diagnosisPaperCode;
    }

    public void setDiagnosisPaperCode(String diagnosisPaperCode) {
        this.diagnosisPaperCode = diagnosisPaperCode;
    }

    public String getDiagnosisPaperName() {
        return diagnosisPaperName;
    }

    public void setDiagnosisPaperName(String diagnosisPaperName) {
        this.diagnosisPaperName = diagnosisPaperName;
    }

    private Date endTime;

    private Integer artType;

    private String diagnosisPaperCode;

    private String diagnosisPaperName;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Integer getTeacherCode() {
        return teacherCode;
    }

    public void setTeacherCode(Integer teacherCode) {
        this.teacherCode = teacherCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDiagnosisRecordCode() {
        return diagnosisRecordCode;
    }

    public void setDiagnosisRecordCode(String diagnosisRecordCode) {
        this.diagnosisRecordCode = diagnosisRecordCode;
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

    public Integer getClassCode() {
        return classCode;
    }

    public void setClassCode(Integer classCode) {
        this.classCode = classCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<Integer> getClassCodes() {
        return classCodes;
    }

    public void setClassCodes(List<Integer> classCodes) {
        this.classCodes = classCodes;
    }

    public List<String> getDiagnosisRecordCodes() {
        return diagnosisRecordCodes;
    }

    public void setDiagnosisRecordCodes(List<String> diagnosisRecordCodes) {
        this.diagnosisRecordCodes = diagnosisRecordCodes;
    }

    public Integer getHasReport() {
        return hasReport;
    }

    public void setHasReport(Integer hasReport) {
        this.hasReport = hasReport;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Integer getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(Integer subjectCode) {
        this.subjectCode = subjectCode;
    }
}
