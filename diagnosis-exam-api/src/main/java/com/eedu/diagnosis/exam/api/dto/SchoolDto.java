package com.eedu.diagnosis.exam.api.dto;

import java.io.Serializable;

/**
 * Created by dqy on 2017/10/10.
 */
public class SchoolDto implements Serializable{
    private String schoolCode;
    /** 学校名称**/
    private String schoolName;

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
}
