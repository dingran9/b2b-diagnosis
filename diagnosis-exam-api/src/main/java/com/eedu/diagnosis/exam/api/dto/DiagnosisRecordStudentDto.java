package com.eedu.diagnosis.exam.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by dqy on 2017/3/16.
 */
public class DiagnosisRecordStudentDto implements Serializable {
    private String code;

    private String diagnosisName;

    private List<Integer> subjectCodes;
    private List<Integer> classCodes;
    /**
     * 答题卡来源 0诊断1作业
     */
    private Integer resourceType;

    private Integer studentCode;

    private String diagnosisTeacherRecordCode;

    private String studentName;

    private Date createTime;

    private Date updateTime;

    private Date startTime;

    private Date endTime;

    private String diagnosisPaperCode;

    private String diagnosisPaperName;

    private Integer stageCode;

    private Integer gradeCode;

    private Integer subjectCode;
    /** 0单科1全科*/
    private Integer diagnosisType;
    /**0文1理*/
    private Integer artType;
    /**
     * 答题用时
     */
    private String useTime;

    //学生提交答题卡时间
    private Date makePaperTime;

    private BigDecimal totalScore;
    private BigDecimal objectiveScore;//客观题得分
    private BigDecimal subjectiveScore;//主观题得分
    private Integer diagnosisStatus;

    private Integer markStatus;

    private Integer classCode;
    private String schoolName;
    private Integer schoolCode;
    private String className;
    private Integer examType;
    /**
     * 全科考试的考试状态 0未开始 1已开始 2已结束
     */
    private Integer examStatus;

    public List<Integer> getClassCodes() {
        return classCodes;
    }

    public void setClassCodes(List<Integer> classCodes) {
        this.classCodes = classCodes;
    }

    public Date getMakePaperTime() {
        return makePaperTime;
    }

    public void setMakePaperTime(Date makePaperTime) {
        this.makePaperTime = makePaperTime;
    }

    public BigDecimal getObjectiveScore() {
        return objectiveScore;
    }

    public void setObjectiveScore(BigDecimal objectiveScore) {
        this.objectiveScore = objectiveScore;
    }

    public BigDecimal getSubjectiveScore() {
        return subjectiveScore;
    }

    public void setSubjectiveScore(BigDecimal subjectiveScore) {
        this.subjectiveScore = subjectiveScore;
    }

    public Integer getMarkStatus() {
        return markStatus;
    }

    public void setMarkStatus(Integer markStatus) {
        this.markStatus = markStatus;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Integer getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(Integer schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getExamType() {
        return examType;
    }

    public void setExamType(Integer examType) {
        this.examType = examType;
    }

    public Integer getExamStatus() {
        return examStatus;
    }

    public void setExamStatus(Integer examStatus) {
        this.examStatus = examStatus;
    }

    public String getDiagnosisName() {
        return diagnosisName;
    }

    public void setDiagnosisName(String diagnosisName) {
        this.diagnosisName = diagnosisName;
    }

    public Integer getResourceType() {
        return resourceType;
    }

    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }

    public Integer getDiagnosisType() {
        return diagnosisType;
    }

    public void setDiagnosisType(Integer diagnosisType) {
        this.diagnosisType = diagnosisType;
    }

    public Integer getArtType() {
        return artType;
    }

    public void setArtType(Integer artType) {
        this.artType = artType;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(Integer studentCode) {
        this.studentCode = studentCode;
    }

    public String getDiagnosisTeacherRecordCode() {
        return diagnosisTeacherRecordCode;
    }

    public void setDiagnosisTeacherRecordCode(String diagnosisTeacherRecordCode) {
        this.diagnosisTeacherRecordCode = diagnosisTeacherRecordCode;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
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

    public List<Integer> getSubjectCodes() {
        return subjectCodes;
    }

    public void setSubjectCodes(List<Integer> subjectCodes) {
        this.subjectCodes = subjectCodes;
    }

    public BigDecimal getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(BigDecimal totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getDiagnosisStatus() {
        return diagnosisStatus;
    }

    public void setDiagnosisStatus(Integer diagnosisStatus) {
        this.diagnosisStatus = diagnosisStatus;
    }

    public Integer getClassCode() {
        return classCode;
    }

    public void setClassCode(Integer classCode) {
        this.classCode = classCode;
    }
}
