package com.eedu.diagnosis.manager.model.response;


import com.eedu.diagnosis.manager.model.BaseModel;


/** 
 * 全科诊断卷与单科诊断卷关系表
 * 
 **/
public class DiagnosisComplexPaperRelationVo {

	
	/** 主键code **/
    private String code;
    /** 全科诊断卷code **/
    private String complexPaperCode;
    /** 单科诊断卷code **/
    private String diagnosisPaperCode;
    /** 单科诊断卷名称 **/
    private String diagnosisPaperName;
    /** 学科 **/
    private Integer subjectCode;

    private Integer artsType;

    private Double totalScore;

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getArtsType() {
        return artsType;
    }

    public void setArtsType(Integer artsType) {
        this.artsType = artsType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getComplexPaperCode() {
        return complexPaperCode;
    }

    public void setComplexPaperCode(String complexPaperCode) {
        this.complexPaperCode = complexPaperCode;
    }

    public String getDiagnosisPaperCode() {
        return diagnosisPaperCode;
    }

    public void setDiagnosisPaperCode(String diagnosisPaperCode) {
        this.diagnosisPaperCode = diagnosisPaperCode;
    }

    public String getDiagnosisPaperName() {
        return diagnosisPaperName;
    }

    public void setDiagnosisPaperName(String diagnosisPaperName) {
        this.diagnosisPaperName = diagnosisPaperName;
    }

    public Integer getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(Integer subjectCode) {
        this.subjectCode = subjectCode;
    }
}