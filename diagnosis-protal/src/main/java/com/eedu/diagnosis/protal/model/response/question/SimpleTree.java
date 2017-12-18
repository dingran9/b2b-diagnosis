package com.eedu.diagnosis.protal.model.response.question;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
* 项目名称：keepMark-teacher-business<br>
* 包名：com.eedu.keepMark.teacher.model.response <br>
* 类名称：SimpleTreeVO  <br>
* 类描述：简单树节点 对象<br>
* 作者：zhangjian  <br>
* 创建日期：2016年11月22日 <br>
* 公司：北京易教空间教育科技股份有限公司<br>
 */
public class SimpleTree implements Serializable{

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private List<SimpleTreeVO> sons=new ArrayList<SimpleTreeVO>(0);
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
	public List<SimpleTreeVO> getSons() {
		return sons;
	}
	public void setSons(List<SimpleTreeVO> sons) {
		this.sons = sons;
	}
}
