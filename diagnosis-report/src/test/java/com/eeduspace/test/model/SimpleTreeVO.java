package com.eeduspace.test.model;

import java.io.Serializable;

/**
 * 简单树节点 对象
 * @author Administrator
 *
 */
public class SimpleTreeVO implements Serializable{

	private static final long serialVersionUID = -2937325073595876528L;
	private String idVo;
	private String nameVo;
	private String basecode;
	private int isRight = 1;//0错1对
	/**
	 * 产生式类型
	 */
	private String typecode;

	public String getTypecode() {
		return typecode;
	}

	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}

	public String getBasecode() {
		return basecode;
	}

	public void setBasecode(String basecode) {
		this.basecode = basecode;
	}

	public String getIdVo() {
		return idVo;
	}
	public void setIdVo(String idVo) {
		this.idVo = idVo;
	}
	public String getNameVo() {
		return nameVo;
	}
	public void setNameVo(String nameVo) {
		this.nameVo = nameVo;
	}

	public int getIsRight() {
		return isRight;
	}

	public void setIsRight(int isRight) {
		this.isRight = isRight;
	}
}
