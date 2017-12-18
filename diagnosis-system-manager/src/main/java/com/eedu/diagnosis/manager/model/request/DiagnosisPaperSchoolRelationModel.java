package com.eedu.diagnosis.manager.model.request;

import java.util.Date;
import java.util.List;

import com.eedu.diagnosis.manager.model.BaseModel;

public class DiagnosisPaperSchoolRelationModel extends BaseModel{

	
    private String code;

	private String diagnosisPaperCode;
    /** 是否发布 **/
	private String isRelease;
	
	private Date createTime;

	private Date updateTime;

    private String schoolCode;
    /** 学校名称**/
    private String schoolName;
	
	/** 单科诊断卷 code **/
    private List<SchoolModel> schoolModelList;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public List<SchoolModel> getSchoolModelList() {
		return schoolModelList;
	}

	public void setSchoolModelList(List<SchoolModel> schoolModelList) {
		this.schoolModelList = schoolModelList;
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

    
    
}