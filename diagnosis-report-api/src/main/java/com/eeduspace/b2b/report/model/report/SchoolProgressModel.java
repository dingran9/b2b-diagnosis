package com.eeduspace.b2b.report.model.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhongfei on 2017/10/9.
 */
public class SchoolProgressModel implements Serializable{
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

    public List<ClassTeachingProgressModel> getClassTeachingProgressModels() {
        return classTeachingProgressModels;
    }

    public void setClassTeachingProgressModels(List<ClassTeachingProgressModel> classTeachingProgressModels) {
        this.classTeachingProgressModels = classTeachingProgressModels;
    }

    private Integer schoolCode;

    private String schoolName;

    public List<TeachingProgressModel> getTeachingProgressModels() {
        return teachingProgressModels;
    }

    public void setTeachingProgressModels(List<TeachingProgressModel> teachingProgressModels) {
        this.teachingProgressModels = teachingProgressModels;
    }

    public List<TeacherTeachingProgressModel> getTeacherTeachingProgressModels() {
        return teacherTeachingProgressModels;
    }

    public void setTeacherTeachingProgressModels(List<TeacherTeachingProgressModel> teacherTeachingProgressModels) {
        this.teacherTeachingProgressModels = teacherTeachingProgressModels;
    }

    private List<ClassTeachingProgressModel> classTeachingProgressModels = new ArrayList<>();

    private List<TeachingProgressModel> teachingProgressModels = new ArrayList<>();

    private List<TeacherTeachingProgressModel> teacherTeachingProgressModels = new ArrayList<>();
}
