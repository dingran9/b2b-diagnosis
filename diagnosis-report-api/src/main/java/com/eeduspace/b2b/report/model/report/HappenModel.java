package com.eeduspace.b2b.report.model.report;

import java.io.Serializable;

/**
 * Created by liuhongfei on 2017/10/9.
 */
public class HappenModel implements Serializable{

    //年级CODE
    private Integer gradeCode;
    //班级CODE
    private Integer classCode;
    //班级名称
    private String className;
    //出勤率
    private Double attendanceRate;
    //平均作答时间（分钟）
    private Double avgAnswerTime;
    //平均作答率
    private Double avgResponseRate;

    public Integer getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(Integer gradeCode) {
        this.gradeCode = gradeCode;
    }

    public Integer getClassCode() {
        return classCode;
    }

    public void setClassCode(Integer classCode) {
        this.classCode = classCode;
    }

    public String getClassName() {
        return className;

    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Double getAttendanceRate() {
        return attendanceRate;
    }

    public void setAttendanceRate(Double attendanceRate) {
        this.attendanceRate = attendanceRate;
    }

    public Double getAvgAnswerTime() {
        return avgAnswerTime;
    }

    public void setAvgAnswerTime(Double avgAnswerTime) {
        this.avgAnswerTime = avgAnswerTime;
    }

    public Double getAvgResponseRate() {
        return avgResponseRate;
    }

    public void setAvgResponseRate(Double avgResponseRate) {
        this.avgResponseRate = avgResponseRate;
    }

}
