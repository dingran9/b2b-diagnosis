package com.eedu.auth.beans;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/29
 * Time: 17:44
 * Describe:
 */
public class AuthClassBean implements Serializable{

	private Integer classId;

	private String className;

	private Integer studentNumber;   //学生人数

	private Integer teacherNumber;   //教师人数

	private Integer groupArt;

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}


	public Integer getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(Integer studentNumber) {
		this.studentNumber = studentNumber;
	}

	public Integer getTeacherNumber() {
		return teacherNumber;
	}

	public void setTeacherNumber(Integer teacherNumber) {
		this.teacherNumber = teacherNumber;
	}

	public void setGroupArt(Integer groupArt){ this.groupArt = groupArt; }

	public Integer getGroupArt(){return groupArt; }
}
