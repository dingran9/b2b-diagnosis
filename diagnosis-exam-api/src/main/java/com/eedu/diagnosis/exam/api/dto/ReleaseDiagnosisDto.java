package com.eedu.diagnosis.exam.api.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dqy on 2017/10/10.
 */
public class ReleaseDiagnosisDto implements Serializable {
    private String code;
    private String diagnosisName;

    private Date createTime;

    private Date updateTime;

    private Integer stageCode;

    private Integer gradeCode;

    private Integer teacherCode;

    private String teacherName;

    private Integer examScope;//考试的地区范围  1班级 2学校级 3多校级 4全区级

    private String schools;//所发布考试的学校code集合
    /**
     * 测试类型 3模拟考 月考6 联考7
     */
    private Integer examType;
    /**
     * 单科、全科考试
     */
    private Integer diagnosisType;

    private Integer groupAreaDistrictId;  //所属地区：区县Id

    private String groupAreaDistrictName;  //所属地区：区县Id

    private String examYear;

    private String detailModels;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Integer getExamScope() {
        return examScope;
    }

    public void setExamScope(Integer examScope) {
        this.examScope = examScope;
    }


    public Integer getExamType() {
        return examType;
    }

    public void setExamType(Integer examType) {
        this.examType = examType;
    }

    public Integer getDiagnosisType() {
        return diagnosisType;
    }

    public void setDiagnosisType(Integer diagnosisType) {
        this.diagnosisType = diagnosisType;
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

    public String getSchools() {
        return schools;
    }

    public void setSchools(String schools) {
        this.schools = schools;
    }

    public String getDetailModels() {
        return detailModels;
    }

    public void setDetailModels(String detailModels) {
        this.detailModels = detailModels;
    }
}
