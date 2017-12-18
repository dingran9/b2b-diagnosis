package com.eedu.diagnosis.manager.model.request;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 发布考试model
 * Created by dqy on 2017/9/29.
 */
public class ReleaseAreaExamModel {
    @NotBlank(message = "diagnosisName is null.")
    private String diagnosisName;
    private Date updateTime;
    private Date createTime;
    private Integer schoolCode;
    private String schoolName;
    @NotNull(message = "stageCode is null.")
    private Integer stageCode;
    @NotNull(message = "gradeCode is null.")
    private Integer gradeCode;
    @NotNull(message = "teacherCode is null.")
    private Integer teacherCode;
    @NotBlank(message = "teacherName is null.")
    private String teacherName;
    @NotNull(message = "examScope is null.")
    private Integer examScope;//考试的地区范围  1班级 2学校级 3多校级 4全区级
    private List<SchoolModel> schools;//所发布考试的学校集合
    /**
     * 测试类型 3模拟考 月考6 联考7
     */
    @NotNull(message = "examType is null.")
    private Integer examType;
    /**
     * 单科、全科考试
     */
    @NotNull(message = "diagnosisType is null.")
    private Integer diagnosisType;
    @NotNull(message = "groupAreaDistrictId is null.")
    private Integer groupAreaDistrictId;  //所属地区：区县Id
    @NotBlank(message = "groupAreaDistrictName is null.")
    private String groupAreaDistrictName;  //所属地区：区县Id
    @Valid
    @NotEmpty(message = "detailModels is null.")
    private List<ReleaseAreaExamDetailModel> detailModels;//该次全科考所包含的单科考试集合

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDiagnosisType() {
        return diagnosisType;
    }

    public void setDiagnosisType(Integer diagnosisType) {
        this.diagnosisType = diagnosisType;
    }

    public List<SchoolModel> getSchools() {
        return schools;
    }

    public void setSchools(List<SchoolModel> schools) {
        this.schools = schools;
    }

    public Integer getExamScope() {
        return examScope;
    }

    public void setExamScope(Integer examScope) {
        this.examScope = examScope;
    }

    public List<ReleaseAreaExamDetailModel> getDetailModels() {
        return detailModels;
    }

    public void setDetailModels(List<ReleaseAreaExamDetailModel> detailModels) {
        this.detailModels = detailModels;
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

    public Integer getExamType() {
        return examType;
    }

    public void setExamType(Integer examType) {
        this.examType = examType;
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
}
