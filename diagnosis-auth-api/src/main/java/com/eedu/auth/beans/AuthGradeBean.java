package com.eedu.auth.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/29
 * Time: 17:44
 * Describe:
 */
public class AuthGradeBean extends BaseModel implements Serializable{

	private Integer gradeId;

	private String gradeName;

	private Integer gradeIden;

	private Integer schoolId;

	private String schoolName;

	private List<AuthClassBean> classBeanList = new ArrayList<AuthClassBean>();

	public Integer getGradeId() {
		return gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public Integer getGradeIden() {
		return gradeIden;
	}

	public void setGradeIden(Integer gradeIden) {
		this.gradeIden = gradeIden;
	}

	public Integer getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public List<AuthClassBean> getClassBeanList() {
		return classBeanList;
	}

	public void setClassBeanList(List<AuthClassBean> classBeanList) {
		this.classBeanList = classBeanList;
	}
}
