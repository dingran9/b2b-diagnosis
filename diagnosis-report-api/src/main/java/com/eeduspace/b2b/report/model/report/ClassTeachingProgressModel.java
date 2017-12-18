package com.eeduspace.b2b.report.model.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liuhongfei on 2017/10/9.
 */
public class ClassTeachingProgressModel implements Serializable{


    private Integer classCode;

    private String classlName;

    private String unitCode;

    private String unitName;

    private Integer gradeCode;

    private Date startTime;

    public Integer getClassCode() {
        return classCode;
    }

    public void setClassCode(Integer classCode) {
        this.classCode = classCode;
    }

    public String getClasslName() {
        return classlName;
    }

    public void setClasslName(String classlName) {
        this.classlName = classlName;
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

    public Integer getGradeCode() {
        return gradeCode;
    }

    public List<TeachingProgressModel> getTeachingProgressModels() {
        return teachingProgressModels;
    }

    public void setTeachingProgressModels(List<TeachingProgressModel> teachingProgressModels) {
        this.teachingProgressModels = teachingProgressModels;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    private List<TeachingProgressModel> teachingProgressModels = new ArrayList<>();

    private Integer teacherCode;

    private String teacherName;
}
