package com.eedu.diagnosis.exam.api.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by dqy on 2017/3/16.
 */
public class DiagnosisRecordTeacherDto implements Serializable {
    private String code;

    private String diagnosisName;

    private List<String> codes;
    /**0诊断 1作业 */
    private Integer useType;

    private Date createTime;

    private Date updateTime;

    private Date startTime;

    private Date endTime;

    private Integer schoolCode;

    private String schoolName;

    private Integer stageCode;

    private Integer gradeCode;
    /**班级codes*/
    private List<Integer> classCodes;

    private Integer classCode;
    /**是否全年级发布 0否1是*/
    private Integer isAllGrade;

    private Integer subjectCode;
    /** 文理类型 0文1理*/
    private Integer artType;

    private Integer teacherCode;

    private String teacherName;
    /** 0单科 1全科*/
    private Integer diagnosisType;
    /** 测试类型 0单元测试 1期中 2期末 3模拟考 4会考 */
    private Integer examType;
    private Integer examScope;
    private String diagnosisPaperCode;

    private String diagnosisPaperName;

    private Integer isSnapshot;

    private String examYear;//考试年份   eg. 2017-1 2017年上学期 2017-2 2017年下学期

    //已出班级报告的数量
    private Long hasClassReportCount;

    /**
     * 全科考试的考试状态 0未开始 1已开始 2已结束
     */
    private Integer examStatus;

    private Integer isRead;//冗余字段

    private String unitCode;

    private String unitName;

    private Integer groupAreaDistrictId;  //所属地区：区县Id

    private String groupAreaDistrictName;  //所属地区：区县Id

    public Integer getExamScope() {
        return examScope;
    }

    public void setExamScope(Integer examScope) {
        this.examScope = examScope;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getGroupAreaDistrictId() {
        return groupAreaDistrictId;
    }

    public void setGroupAreaDistrictId(Integer groupAreaDistrictId) {
        this.groupAreaDistrictId = groupAreaDistrictId;
    }

    public String getGroupAreaDistrictName() {
        return groupAreaDistrictName;
    }

    public void setGroupAreaDistrictName(String groupAreaDistrictName) {
        this.groupAreaDistrictName = groupAreaDistrictName;
    }

    public Integer getClassCode() {
        return classCode;
    }

    public void setClassCode(Integer classCode) {
        this.classCode = classCode;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Integer getExamStatus() {
        return examStatus;
    }

    public void setExamStatus(Integer examStatus) {
        this.examStatus = examStatus;
    }

    public String getExamYear() {
        return examYear;
    }

    public void setExamYear(String examYear) {
        this.examYear = examYear;
    }

    public String getDiagnosisName() {
        return diagnosisName;
    }

    public void setDiagnosisName(String diagnosisName) {
        this.diagnosisName = diagnosisName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<String> getCodes() {
        return codes;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }

    public Integer getUseType() {
        return useType;
    }

    public void setUseType(Integer useType) {
        this.useType = useType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

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

    public List<Integer> getClassCodes() {
        return classCodes;
    }

    public void setClassCodes(List<Integer> classCodes) {
        this.classCodes = classCodes;
    }

    public Integer getIsAllGrade() {
        return isAllGrade;
    }

    public void setIsAllGrade(Integer isAllGrade) {
        this.isAllGrade = isAllGrade;
    }

    public Integer getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(Integer subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Integer getArtType() {
        return artType;
    }

    public void setArtType(Integer artType) {
        this.artType = artType;
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

    public Integer getDiagnosisType() {
        return diagnosisType;
    }

    public void setDiagnosisType(Integer diagnosisType) {
        this.diagnosisType = diagnosisType;
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

    public Integer getIsSnapshot() {
        return isSnapshot;
    }

    public void setIsSnapshot(Integer isSnapshot) {
        this.isSnapshot = isSnapshot;
    }

    public Long getHasClassReportCount() {
        return hasClassReportCount;
    }

    public void setHasClassReportCount(Long hasClassReportCount) {
        this.hasClassReportCount = hasClassReportCount;
    }

    public Integer getExamType() {
        return examType;
    }

    public void setExamType(Integer examType) {
        this.examType = examType;
    }
}
