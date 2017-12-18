package com.eedu.diagnosis.manager.model.request;


import com.eedu.diagnosis.manager.model.BaseModel;

public class SchoolModel extends BaseModel {

    private String schoolCode;
    /** 学校名称**/
    private String schoolName;
    /** 是否发布 **/
	private String isRelease;
	
	
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
	public String getIsRelease() {
		return isRelease;
	}
	public void setIsRelease(String isRelease) {
		this.isRelease = isRelease;
	}

	
	
}