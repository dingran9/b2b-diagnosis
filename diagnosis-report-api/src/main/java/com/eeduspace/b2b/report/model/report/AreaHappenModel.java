package com.eeduspace.b2b.report.model.report;

import java.io.Serializable;

/**
 * Created by liuhongfei on 2017/9/29.
 */
public class AreaHappenModel implements Serializable{

    //出勤率
    private Double attendanceRate;
    //平均作答时间（分钟）
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
