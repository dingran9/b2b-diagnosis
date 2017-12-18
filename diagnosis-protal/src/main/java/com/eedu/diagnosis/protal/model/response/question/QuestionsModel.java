package com.eedu.diagnosis.protal.model.response.question;

/**
* 项目名称：keepMark-teacher-business<br>
* 包名：com.eedu.keepMark.teacher.model.response <br>
* 类名称：QuestionModel  <br>
* 类描述：返回的试题的实体类<br>
* 作者：zhangjian  <br>
* 创建日期：2016年8月27日 <br>
* 公司：北京易教空间教育科技股份有限公司<br>
 */
public class QuestionsModel {

	//返回状态码
	private String status;
	//返回是否成功信息
	private String message;
	//返回结果数据
	private QuestionListModel datas;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public QuestionListModel getDatas() {
		return datas;
	}
	public void setDatas(QuestionListModel datas) {
		this.datas = datas;
	}
	
}
