package com.eeduspace.b2b.report.model.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhongfei on 2017/10/9.
 */
public class TeacherTeachingProgressModel implements Serializable{

    private Integer teacherCode;

    private String teacherName;

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

    public List<TeachingProgressModel> getTeachingProgressModels() {
        return teachingProgressModels;
    }

    public void setTeachingProgressModels(List<TeachingProgressModel> teachingProgressModels) {
        this.teachingProgressModels = teachingProgressModels;
    }

    private List<TeachingProgressModel> teachingProgressModels = new ArrayList<>();


}
