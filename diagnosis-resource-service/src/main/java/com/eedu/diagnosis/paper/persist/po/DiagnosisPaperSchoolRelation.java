package com.eedu.diagnosis.paper.persist.po;

import java.util.Date;

public class DiagnosisPaperSchoolRelation {
    private String code;

    /** 所属学校 **/
    private String schoolCode;
    /** 学校名称**/
    private String schoolName;
    /** 单科诊断卷 code **/
	private String diagnosisPaperCode;
    /** 是否发布 **/
	private String isRelease;
	
	private Date createTime;

	private Date updateTime;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSchoolCode() {
		return schoolCode;
	}

	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getDiagnosisPaperCode() {
		return diagnosisPaperCode;
	}

	public void setDiagnosisPaperCode(String diagnosisPaperCode) {
		this.diagnosisPaperCode = diagnosisPaperCode;
	}

	public String getIsRelease() {
		return isRelease;
	}

	public void setIsRelease(String isRelease) {
		this.isRelease = isRelease;
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

	
	
	
}