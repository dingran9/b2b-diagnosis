package com.eedu.diagnosis.manager.model.response;

import com.eedu.diagnosis.manager.model.BaseModel;

import java.util.Date;

public class BaseDataVo extends BaseModel{

	private String id;
	private String name;
	private String code;
	private String gradeCode;//学年
	private String subjectCode;//学科
	private String paperType;//试卷类型
	private String paperTypeName;//试卷类型
	private Double paperScore;//资源总分
	private Integer paperDifficult;//难易度
	private Integer sort;//排序
	private boolean hasReport;//是否可查看报告
	private Date serverTime;//系统当前时间


	public Date getServerTime() {
		return serverTime;
	}

	public void setServerTime(Date serverTime) {
		this.serverTime = serverTime;
	}

	public String getPaperTypeName() {
		return paperTypeName;
	}

	public void setPaperTypeName(String paperTypeName) {
		this.paperTypeName = paperTypeName;
	}

	public boolean getHasReport() {
		return hasReport;
	}

	public void setHasReport(boolean hasReport) {
		this.hasReport = hasReport;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Double getPaperScore() {
		return paperScore;
	}

	public void setPaperScore(Double paperScore) {
		this.paperScore = paperScore;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public String getPaperType() {
		return paperType;
	}
	public void setPaperType(String paperType) {
		this.paperType = paperType;
	}

	public Integer getPaperDifficult() {
		return paperDifficult;
	}

	public void setPaperDifficult(Integer paperDifficult) {
		this.paperDifficult = paperDifficult;
	}
}
