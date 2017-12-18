package com.eedu.diagnosis.protal.model.response.question;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
  *	@Author Dong_Qingyan 
  *	@Date 2016年11月8日 下午12:36:26
  *	@description
  */
public class BaseProductionModel implements Serializable{
	private static final long serialVersionUID = 1L;
	private String basenameVo;
	private String basecodeVo;
	private String typecodeVo;


	private List<SimpleTreeVO> sons=new ArrayList<SimpleTreeVO>(0);


	private int isTrue = 1;//0错 1对
	public String getBasenameVo() {
		return basenameVo;
	}
	public void setBasenameVo(String basenameVo) {
		this.basenameVo = basenameVo;
	}
	public String getBasecodeVo() {
		return basecodeVo;
	}
	public void setBasecodeVo(String basecodeVo) {
		this.basecodeVo = basecodeVo;
	}
	public String getTypecodeVo() {
		return typecodeVo;
	}
	public void setTypecodeVo(String typecodeVo) {
		this.typecodeVo = typecodeVo;
	}
	public int getIsTrue() {
		return isTrue;
	}
	public void setIsTrue(int isTrue) {
		this.isTrue = isTrue;
	}
	public List<SimpleTreeVO> getSons() {
		return sons;
	}
	public void setSons(List<SimpleTreeVO> sons) {
		this.sons = sons;
	}
}
