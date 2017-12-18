package com.eeduspace.report.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by liuhongfei on 2017/9/28.
 */
public class ReleaseViewData implements Serializable{
    /**
     * 教师的发布记录code
     */
    private String code ;
    private String  diagnosisName;
    private Date startTime;
    private Date endTime;
    private Integer  schoolCode;
    private String  schoolName;
    private Integer stageCode;
    private Integer gradeCode;
    private Integer subjectCode;
    private Integer teacherCode;
    private String teacherName;
    private Integer  artType;

    public Integer getEcode() {
        return ecode;
    }

    public void setEcode(Integer ecode) {
        this.ecode = ecode;
    }

    private String diagnosisPaperCode;
    private Integer diagnosisType;
    private Integer  isSnapshot;
    private String unitCode;
    /**
     * 报告中发布记录code
     */
    private Integer ecode;

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

    public Integer getDiagnosisType() {
        return diagnosisType;
    }

    public void setDiagnosisType(Integer diagnosisType) {
        this.diagnosisType = diagnosisType;
    }

    public Integer getIsSnapshot() {
        return isSnapshot;
    }

    public void setIsSnapshot(Integer isSnapshot) {
        this.isSnapshot = isSnapshot;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getExamType() {
        return examType;
    }

    public void setExamType(Integer examType) {
        this.examType = examType;
    }

    public Integer getReleaseCode() {
        return releaseCode;
    }

    public void setReleaseCode(Integer releaseCode) {
        this.releaseCode = releaseCode;
    }

    public String getReleaseName() {
        return releaseName;
    }

    public void setReleaseName(String releaseName) {
        this.releaseName = releaseName;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getDifficultStar() {
        return difficultStar;
    }

    public void setDifficultStar(Integer difficultStar) {
        this.difficultStar = difficultStar;
    }

    private String unitName;
    private Integer  groupAreaDistrictId;
    private String groupAreaDistrictName;
    private  Date createTime;
    private Integer examType;
    private Integer releaseCode;
    private String releaseName;
    private String  semester;
    private Integer totalScore;
    private Integer difficultStar;


    private Integer sort;

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
