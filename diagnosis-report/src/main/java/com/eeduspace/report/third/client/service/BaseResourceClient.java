package com.eeduspace.report.third.client.service;


import com.eeduspace.report.third.client.model.request.ReportRequestModel;
import com.eeduspace.report.third.client.model.response.ReportModel;

/**
 * 访问资源信息客户端
 * @author zhuchaowei
 * 2016年7月15日
 * Description
 */
public interface BaseResourceClient {
	/**
	 * <p>描述 获取单科报告</p>
	 * @Author zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * Date: 2017/3/16 11:48
	 * @param   reportRequestModel
	 * @return   报告内容
	**/
	public ReportModel getSingelReportFromResource(ReportRequestModel reportRequestModel);
}
