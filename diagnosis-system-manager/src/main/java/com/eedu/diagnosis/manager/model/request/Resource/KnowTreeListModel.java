package com.eedu.diagnosis.manager.model.request.Resource;

import java.util.List;

/**
* 项目名称：keepMark-teacher-business<br>
* 包名：com.eedu.keepMark.teacher.model.response <br>
* 类名称：KnowTreeListModel  <br>
* 类描述：返回的知识点树集合实体类<br>
* 作者：zhangjian  <br>
* 创建日期：2016年8月27日 <br>
* 公司：北京易教空间教育科技股份有限公司<br>
 */
public class KnowTreeListModel {

	
	private List<KnowTreeModel> datas;

	public List<KnowTreeModel> getDatas() {
		return datas;
	}

	public void setDatas(List<KnowTreeModel> datas) {
		this.datas = datas;
	}

	
}
