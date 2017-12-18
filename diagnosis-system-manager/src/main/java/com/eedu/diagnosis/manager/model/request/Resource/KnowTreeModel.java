package com.eedu.diagnosis.manager.model.request.Resource;

/**
* 项目名称：keepMark-teacher-business<br>
* 包名：com.eedu.keepMark.teacher.model.response <br>
* 类名称：KnowTreeModel  <br>
* 类描述：返回的知识点树实体类<br>
* 作者：zhangjian  <br>
* 创建日期：2016年8月27日 <br>
* 公司：北京易教空间教育科技股份有限公司<br>
 */
public class KnowTreeModel {
	
	//编号
	private String ctbCode;
	//教材版本
	private String booktypeCode;
	//树的层级
	private String level;
	//是否是知识点
	private String chk;
	//树的路由
	private String path;
	//知识点名称
	private String knowledgeName;
	//父节点编号
	private String parentCode;
	public String getCtbCode() {
		return ctbCode;
	}
	public void setCtbCode(String ctbCode) {
		this.ctbCode = ctbCode;
	}
	public String getBooktypeCode() {
		return booktypeCode;
	}
	public void setBooktypeCode(String booktypeCode) {
		this.booktypeCode = booktypeCode;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getChk() {
		return chk;
	}
	public void setChk(String chk) {
		this.chk = chk;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getKnowledgeName() {
		return knowledgeName;
	}
	public void setKnowledgeName(String knowledgeName) {
		this.knowledgeName = knowledgeName;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	
}
