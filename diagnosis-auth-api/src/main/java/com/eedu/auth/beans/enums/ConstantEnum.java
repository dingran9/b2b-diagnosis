package com.eedu.auth.beans.enums;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/17
 * Time: 16:47
 * Describe:
 */
public enum ConstantEnum {

	PRODUCT_TYPE_ID(1,"系统"),
	/**
	 * 权限类型
	 */
	MENU_VALUE(1,"菜单"),BTN_VALUE(2,"按钮"),
	/**
	 * 组织结构
	 */
	GROUP_TYPE_SCHOOL(1,"学校"),GROUP_TYPE_GRADE(2,"年级"),GROUP_TYPE_CLASS(3,"班级"),
	/**
	 * 用户类型
	 */
	USER_TYPE_STUDENT(1,"学生"),USER_TYPE_TEACHER(2,"教师"),USER_TYPE_SCHOOL_MASTER(3,"校长"),USER_TYPE_SCHOOL_ADMIN(4,"校级管理员"),USER_TYPE_SYSTEM_TEACHER(5,"系统普通用户"),USER_TYPE_SYSTEM_ADMIN(6,"系统管理员"),
	USER_TYPE_DIRECTOR(7,"局长"),USER_TYPE_TEACHING_STAFF(8,"教研员"),USER_TYPE_AREA_ADMINISTRATOR(9,"区域管理员"),
	/**
	 * 发送短信的类型
	 * 1,绑定手机号  2,修改密码
	 */
	SEND_TYPE_BIND_PHONE(1,"comcode"),SEND_TYPE_REGISTER(2,"edit_password"),
	/**
	 * 基础数据类型
	 */
	DATA_DICTIONARY_SUBJECT_TYPE(1,"学科"),DATA_DICTIONARY_GRADE_TYPE(2,"年级"),DATA_DICTIONARY_PERIOD_TYPE(3,"学段"),
	/**
	 * 组织结构操作类型
	 */
	GROUP_OPERATING_ADD(1,"新增"),GROUP_OPERATING_UPDATE(1,"修改"),GROUP_OPERATING_DELETE(2,"删除"),
	/**
	 * 文理类型
	 */
	GROUP_ART_LIBERAL(0,"文科"),GROUP_ART_SCIENCE(1,"理科"),GROUP_ART_NOTYPE(2,"无类型");
	private Integer type;

	private String typeName;

	ConstantEnum(Integer type, String typeName) {
		this.type = type;
		this.typeName = typeName;
	}

	public String getValueByType(Integer type){

		return null;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
