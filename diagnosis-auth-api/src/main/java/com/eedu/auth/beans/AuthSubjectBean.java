package com.eedu.auth.beans;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/4/20
 * Time: 17:01
 * Describe:
 */
public class AuthSubjectBean implements Serializable {

	private Integer subjectId;

	private Integer subjectIden;

	private String subjectName;

	private Integer gradeId;

	private Integer gradeIden;

	private String gradeName;

	private String materialVersion;

	private String versionCode;

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getSubjectIden() {
		return subjectIden;
	}

	public void setSubjectIden(Integer subjectIden) {
		this.subjectIden = subjectIden;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Integer getGradeId() {
		return gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	public Integer getGradeIden() {
		return gradeIden;
	}

	public void setGradeIden(Integer gradeIden) {
		this.gradeIden = gradeIden;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getMaterialVersion() {
		return materialVersion;
	}

	public void setMaterialVersion(String materialVersion) {
		this.materialVersion = materialVersion;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
}
