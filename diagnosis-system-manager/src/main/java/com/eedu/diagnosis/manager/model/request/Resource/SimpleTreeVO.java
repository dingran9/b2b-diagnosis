package com.eedu.diagnosis.manager.model.request.Resource;

import java.io.Serializable;
/**
* 项目名称：keepMark-teacher-business<br>
* 包名：com.eedu.keepMark.teacher.model.response <br>
* 类名称：SimpleTreeVO  <br>
* 类描述：简单树节点 对象<br>
* 作者：zhangjian  <br>
* 创建日期：2016年11月22日 <br>
* 公司：北京易教空间教育科技股份有限公司<br>
 */
public class SimpleTreeVO implements Serializable{

	private static final long serialVersionUID = 1L;
	private String idVo;
	private String nameVo;
	private String basecode;
	
	public String getIdVo() {
		return idVo;
	}
	public void setIdVo(String idVo) {
		this.idVo = idVo;
	}
	public String getNameVo() {
		return nameVo;
	}
	public String getBasecode() {
		return basecode;
	}
	public void setBasecode(String basecode) {
		this.basecode = basecode;
	}
	public void setNameVo(String nameVo) {
		this.nameVo = nameVo;
	}
	
}
