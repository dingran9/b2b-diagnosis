package com.eedu.diagnosis.protal.model;

/**
 * Created by dqy on 2017/3/20.
 */
public class DiagnosisPaperModel extends BaseModel{

    /**
     * 诊断试卷code
     */
    private String code;

    private Integer studentCode;
    /**
     * 学生诊断记录code
     */
    private String studentDiagnosisRecordCode;

    public String getStudentDiagnosisRecordCode() {
        return studentDiagnosisRecordCode;
    }

    public void setStudentDiagnosisRecordCode(String studentDiagnosisRecordCode) {
        this.studentDiagnosisRecordCode = studentDiagnosisRecordCode;
    }

    public Integer getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(Integer studentCode) {
        this.studentCode = studentCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
