package com.eedu.diagnosis.manager.model.request;

import com.eedu.diagnosis.manager.model.BaseModel;

import java.util.Date;
import java.util.List;

/**
 * Created by dqy on 2017/3/20.
 */
public class DiagnosisReleaseModle extends BaseModel{

    private String code;
    /**0诊断 1作业 */
    private Integer useType;

    private String diagnosisName;

    private Date createTime;

    private Date updateTime;
    private Date startTime;
    private Date endTime;

    private Integer schoolCode;

    private String schoolName;

    private Integer stageCode;
    private Integer gradeId;
    private Integer gradeCode;
    /**班级codes*/
    private List<Integer> classCodes;
    /**是否全年级发布 0否1是*/
    private Integer isAllGrade;

    private Integer subjectCode;
    /** 文理类型 0文1理*/
    private Integer artType;

    private Integer teacherCode;

    private String teacherName;
    /** 测试类型 0单元测试 1期中 2期末 3模拟考 4会考 */
    private Integer examType;

    private String diagnosisPaperCode;

    private String diagnosisPaperName;

    private Integer isSnapshot;

    private Integer totalScore;//试卷总分

    private String unitCode;

    private String unitName;

    private Integer groupAreaDistrictId;  //所属地区：区县Id

    private String groupAreaDistrictName;  //所属地区：区县名称

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

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getUseType() {
        return useType;
    }

    public void setUseType(Integer useType) {
        this.useType = useType;
    }

    public Integer getArtType() {
        return artType;
    }

    public void setArtType(Integer artType) {
        this.artType = artType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDiagnosisName() {
        return diagnosisName;
    }

    public void setDiagnosisName(String diagnosisName) {
        this.diagnosisName = diagnosisName;
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

    public Integer getExamType() {
        return examType;
    }

    public void setExamType(Integer examType) {
        this.examType = examType;
    }
}
