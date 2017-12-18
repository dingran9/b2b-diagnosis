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
public class SimpleBaseTree implements Serializable{

	private static final long serialVersionUID = 1L;
	private String basecodeVo;
	private String basenameVo;
	private List<SimpleTreeVO> sons=new ArrayList<SimpleTreeVO>(0);
	private String typecodeVo;
	
	public String getBasecodeVo() {
		return basecodeVo;
	}
	public void setBasecodeVo(String basecodeVo) {
		this.basecodeVo = basecodeVo;
	}
	public String getBasenameVo() {
		return basenameVo;
	}
	public void setBasenameVo(String basenameVo) {
		this.basenameVo = basenameVo;
	}
	public List<SimpleTreeVO> getSons() {
		return sons;
	}
	public void setSons(List<SimpleTreeVO> sons) {
		this.sons = sons;
	}
	public String getTypecodeVo() {
		return typecodeVo;
	}
	public void setTypecodeVo(String typecodeVo) {
		this.typecodeVo = typecodeVo;
	}
	
}
