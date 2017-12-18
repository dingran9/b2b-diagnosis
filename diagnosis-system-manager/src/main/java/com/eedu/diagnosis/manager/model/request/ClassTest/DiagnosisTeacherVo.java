package com.eedu.diagnosis.manager.model.request.ClassTest;

import com.eedu.diagnosis.manager.model.BaseModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DiagnosisTeacherVo  implements Serializable {

    private String classCode;

    private Integer studentCount;

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public Integer getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(Integer studentCount) {
        this.studentCount = studentCount;
    }
}