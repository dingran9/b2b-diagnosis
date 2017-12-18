package com.eedu.diagnosis.manager.model.request.Resource;

import java.util.List;

/**
* 项目名称：keepMark-teacher-business<br>
* 包名：com.eedu.keepMark.teacher.model.response <br>
* 类名称：QuestionListModel  <br>
* 类描述：返回的试题集合的实体类<br>
* 作者：zx  <br>
* 创建日期：2016年8月27日 <br>
* 公司：北京易教空间教育科技股份有限公司<br>
 */
public class QuestionListModel {

	//试题集合
	private List<QuestionModel> data;
	//总条数
	private String total;
	//当前页
	private String pageNum;
	//每页数
	private String pageSize;
	//总页数
	private String pages;
	public List<QuestionModel> getData() {
		return data;
	}
	public void setData(List<QuestionModel> data) {
		this.data = data;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getPageNum() {
		return pageNum;
	}
	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getPages() {
		return pages;
	}
	public void setPages(String pages) {
		this.pages = pages;
	}
	
	
}
