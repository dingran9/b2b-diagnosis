package com.eeduspace.test.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 卷子实体 体系
 * 注： 字段及字段属性不可修改  与资源库对应。
 * @author Administrator
 * 
 */
public class PaperSystem {

	private String id;
	private List<QuestionSet> paperSystemQusetionType=new ArrayList<QuestionSet>(0);
	
	private String paperName;

	private String subjectCode;

	private String gradeCode;

	private String type;

	private String booktype;

	private String paperDesc;
	private String releaseFlag;
	private Integer totalScore;

	private Integer totalTime;

	private String areaCode;

	private String schoolYear;

	private String createBy;

	private String createName;


	private String updateBy;
	private String updateName;

	private String isencryption;

	private String isdistribution;

	private String semester;

	private String volume;
	private String remark;
	private String isdel;

	private String useTimes;
	private String knowledgeCode;
	private String istopic;

	private String source;

	
	
	public List<QuestionSet> getPaperSystemQusetionType() {
		return paperSystemQusetionType;
	}

	public void setPaperSystemQusetionType(List<QuestionSet> paperSystemQusetionType) {
		this.paperSystemQusetionType = paperSystemQusetionType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPaperName() {
		return paperName;
	}

	public void setPaperName(String paperName) {
		this.paperName = paperName;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBooktype() {
		return booktype;
	}

	public void setBooktype(String booktype) {
		this.booktype = booktype;
	}

	public String getPaperDesc() {
		return paperDesc;
	}

	public void setPaperDesc(String paperDesc) {
		this.paperDesc = paperDesc;
	}

	public String getReleaseFlag() {
		return releaseFlag;
	}

	public void setReleaseFlag(String releaseFlag) {
		this.releaseFlag = releaseFlag;
	}

	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

	public Integer getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Integer totalTime) {
		this.totalTime = totalTime;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(String schoolYear) {
		this.schoolYear = schoolYear;
	}


	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}


	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public String getIsencryption() {
		return isencryption;
	}

	public void setIsencryption(String isencryption) {
		this.isencryption = isencryption;
	}

	public String getIsdistribution() {
		return isdistribution;
	}

	public void setIsdistribution(String isdistribution) {
		this.isdistribution = isdistribution;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsdel() {
		return isdel;
	}

	public void setIsdel(String isdel) {
		this.isdel = isdel;
	}

	public String getUseTimes() {
		return useTimes;
	}

	public void setUseTimes(String useTimes) {
		this.useTimes = useTimes;
	}

	public String getKnowledgeCode() {
		return knowledgeCode;
	}

	public void setKnowledgeCode(String knowledgeCode) {
		this.knowledgeCode = knowledgeCode;
	}

	public String getIstopic() {
		return istopic;
	}

	public void setIstopic(String istopic) {
		this.istopic = istopic;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	
}