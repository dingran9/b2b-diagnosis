package com.eedu.diagnosis.exam.persist.po;


import java.util.Date;
import java.util.UUID;
public class DiagnosisRecordTeacherPo {
    private String code = UUID.randomUUID().toString().replace("-", "").toUpperCase();

    private String diagnosisName;

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

    private Integer subjectCode;

    /** 文理类型 0文1理*/
    private Integer artType;

    private Integer teacherCode;

    private String teacherName;

    private Integer diagnosisType;

    private Integer examType;
    private Integer examScope;

    private String diagnosisPaperCode;

    private String diagnosisPaperName;

    private Integer isSnapshot;

    private String examYear;//考试年份   eg. 2017-1 2017年上学期 2017-2 2017年下学期

    private Long hasClassReportCount;

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