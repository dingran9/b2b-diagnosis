package com.eedu.diagnosis.inclass.test.api.dto;

import com.eedu.diagnosis.inclass.test.api.model.BaseModel;

import java.io.Serializable;
import java.util.Date;

public class DiagnosisStudentAnswerMachineDto extends BaseModel implements Serializable {
    private String studentAnswerMachineCode;

    private Date createTime;

    private Date updateTime;

    private String machineCode;

    private String studentName;

    private String studentCode;

    public String getStudentAnswerMachineCode() {
        return studentAnswerMachineCode;
    }

    public void setStudentAnswerMachineCode(String studentAnswerMachineCode) {
        this.studentAnswerMachineCode = studentAnswerMachineCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }
}