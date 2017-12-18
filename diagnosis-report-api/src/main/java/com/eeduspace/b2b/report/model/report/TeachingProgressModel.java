package com.eeduspace.b2b.report.model.report;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by liuhongfei on 2017/10/9.
 */
public class TeachingProgressModel implements Serializable{


    private String unitCode;

    private String unitName;

    private Integer gradeCode;

    private Date startTime;


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

    public Integer getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(Integer gradeCode) {
        this.gradeCode = gradeCode;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
