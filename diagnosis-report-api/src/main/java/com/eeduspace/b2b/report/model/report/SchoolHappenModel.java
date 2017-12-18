package com.eeduspace.b2b.report.model.report;

import java.io.Serializable;

/**
 * Created by liuhongfei on 2017/10/9.
 */
public class SchoolHappenModel implements Serializable{

    //学校ID
    private Integer SchoolId;
    //学校名称
    private String schoolName;
    //出勤率
    private Double attendanceRate;
    //平均作答时间（分钟）

    public Integer getSchoolId() {
        return SchoolId;
    }

    public void setSchoolId(Integer schoolId) {
        SchoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    private Double avgAnswerTime;
    //平均作答率
    private Double avgResponseRate;


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
