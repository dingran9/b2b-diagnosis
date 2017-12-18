package com.eedu.diagnosis.manager.model.request;

import java.io.Serializable;

/**
 * Created by liuhongfei on 2017/10/13.
 */
public class StageReportModel implements Serializable{
    /**
     * 对比类型
     */
    private String contrastType;

    public String getContrastType() {
        return contrastType;
    }

    public void setContrastType(String contrastType) {
        this.contrastType = contrastType;
    }

    private String releaseCode;

    private Integer districtId;

    public String getReleaseCode() {
        return releaseCode;
    }

    public void setReleaseCode(String releaseCode) {
        this.releaseCode = releaseCode;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
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

    public Integer getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(Integer gradeCode) {
        this.gradeCode = gradeCode;
    }

    private String semester;

    private Integer subjectCode;

    private Integer artType;

    private Integer gradeCode;

}
