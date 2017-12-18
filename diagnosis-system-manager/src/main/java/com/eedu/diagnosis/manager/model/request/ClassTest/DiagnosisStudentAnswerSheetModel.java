package com.eedu.diagnosis.manager.model.request.ClassTest;


import com.eedu.diagnosis.manager.model.BaseModel;

import java.io.Serializable;
import java.util.Date;

public class DiagnosisStudentAnswerSheetModel extends BaseModel {
    private String studentAnswerSheetCode;

    private Date createTime;

    private Date updateTime;

    private String inClassTestCode;

    private String baseCode;

    private Integer isRight;

    private String studentCode;

    private String studentName;

    private String knowledges;
    
    private String studentAnswer;

    private String rightAnswer;

    private String sout;

    private Integer classCode;

    private Integer type;


    public String getSout() {
        return sout;
    }

    public void setSout(String sout) {
        this.sout = sout;
    }

    public String getStudentAnswerSheetCode() {
        return studentAnswerSheetCode;
    }

    public void setStudentAnswerSheetCode(String studentAnswerSheetCode) {
        this.studentAnswerSheetCode = studentAnswerSheetCode;
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

    public String getInClassTestCode() {
        return inClassTestCode;
    }

    public void setInClassTestCode(String inClassTestCode) {
        this.inClassTestCode = inClassTestCode;
    }

    public String getBaseCode() {
        return baseCode;
    }

    public void setBaseCode(String baseCode) {
        this.baseCode = baseCode;
    }

    public Integer getIsRight() {
        return isRight;
    }

    public void setIsRight(Integer isRight) {
        this.isRight = isRight;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getKnowledges() {
        return knowledges;
    }

    public void setKnowledges(String knowledges) {
        this.knowledges = knowledges;
    }

	public String getStudentAnswer() {
		return studentAnswer;
	}

	public void setStudentAnswer(String studentAnswer) {
		this.studentAnswer = studentAnswer;
	}

	public String getRightAnswer() {
		return rightAnswer;
	}

	public void setRightAnswer(String rightAnswer) {
		this.rightAnswer = rightAnswer;
	}

    public Integer getClassCode() {
        return classCode;
    }

    public void setClassCode(Integer classCode) {
        this.classCode = classCode;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}