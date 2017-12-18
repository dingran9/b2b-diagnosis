package com.eedu.diagnosis.manager.model.request;

import com.eeduspace.b2b.report.model.report.UnitModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liuhongfei on 2017/10/13.
 */
public class ResearchReportModel implements Serializable{



    private String unitCode;

    private Integer districtId;

    private String semester;
    private String contrastType;

    public String getContrastType() {
        return contrastType;
    }

    public void setContrastType(String contrastType) {
        this.contrastType = contrastType;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
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

    public Integer getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(Integer gradeCode) {
        this.gradeCode = gradeCode;
    }

    public List<UnitModel> getUnitModels() {
        return unitModels;
    }

    public void setUnitModels(List<UnitModel> unitModels) {
        this.unitModels = unitModels;
    }

    private Integer gradeCode;

    private List<UnitModel> unitModels;

    public String getPaperCode() {
        return paperCode;
    }

    public void setPaperCode(String paperCode) {
        this.paperCode = paperCode;
    }

    private String paperCode;


}
