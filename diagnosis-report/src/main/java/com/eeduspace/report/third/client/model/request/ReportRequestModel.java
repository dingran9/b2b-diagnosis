/**
  * Copyright 2017 bejson.com 
  */
package com.eeduspace.report.third.client.model.request;

import com.eeduspace.b2b.report.model.question.Questions;

import java.util.List;
/**
 * <p>描述 报告请求</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/16 17:41
 * @param    
 * @return   
**/
public class ReportRequestModel {

    private String paperId;
    private String subjectCode;
    private List<Questions> questions;
    private String gradeCode;
    private String bookType;

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public void setQuestions(List<Questions> questions) {
         this.questions = questions;
     }
     public List<Questions> getQuestions() {
         return questions;
     }



}