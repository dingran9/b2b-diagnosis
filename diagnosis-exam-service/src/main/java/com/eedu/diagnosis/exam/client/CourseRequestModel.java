package com.eedu.diagnosis.exam.client;

public class CourseRequestModel {
	private String bigName;
	private String categoriesCode;
	private String bigCategoriesCode;
	private String gradeCode;
	private String subjectCode;
	private String bookType;
	public String getBigName() {
		return bigName;
	}
	public void setBigName(String bigName) {
		this.bigName = bigName;
	}
	public String getCategoriesCode() {
		return categoriesCode;
	}
	public void setCategoriesCode(String categoriesCode) {
		this.categoriesCode = categoriesCode;
	}
	public String getBigCategoriesCode() {
		return bigCategoriesCode;
	}
	public void setBigCategoriesCode(String bigCategoriesCode) {
		this.bigCategoriesCode = bigCategoriesCode;
	}
	public String getGradeCode() {
		return gradeCode;
	}
	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public String getBookType() {
		return bookType;
	}
	public void setBookType(String bookType) {
		this.bookType = bookType;
	}
	public CourseRequestModel(String bigName, String categoriesCode,
			String bigCategoriesCode, String gradeCode, String subjectCode,
			String bookType) {
		super();
		this.bigName = bigName;
		this.categoriesCode = categoriesCode;
		this.bigCategoriesCode = bigCategoriesCode;
		this.gradeCode = gradeCode;
		this.subjectCode = subjectCode;
		this.bookType = bookType;
	}
	
}
