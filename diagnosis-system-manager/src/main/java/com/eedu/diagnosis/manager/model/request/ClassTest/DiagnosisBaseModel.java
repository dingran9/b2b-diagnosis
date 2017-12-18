package com.eedu.diagnosis.manager.model.request.ClassTest;


import com.eedu.diagnosis.manager.model.BaseModel;

import java.math.BigDecimal;
import java.util.Date;

public class DiagnosisBaseModel {

    private Integer stage;

    private Integer subjectCode;

    private Integer gradeCode;

    private String bookVersion;




    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public Integer getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(Integer subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Integer getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(Integer gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getBookVersion() {
        return bookVersion;
    }

    public void setBookVersion(String bookVersion) {
        this.bookVersion = bookVersion;
    }
}