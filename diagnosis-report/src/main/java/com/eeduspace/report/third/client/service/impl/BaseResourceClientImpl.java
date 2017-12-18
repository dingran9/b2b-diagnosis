package com.eeduspace.report.third.client.service.impl;


import com.eeduspace.report.config.BaseResourceConfig;
import com.eeduspace.report.third.client.model.ResultResponse;
import com.eeduspace.report.third.client.model.request.ReportRequestModel;
import com.eeduspace.report.third.client.model.response.ReportModel;
import com.eeduspace.report.third.client.service.BaseResourceClient;
import com.eeduspace.report.util.GsonUtils;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BaseResourceClientImpl implements BaseResourceClient {
	@Autowired
	private BaseResourceConfig baseResourceConfig;
	private RestTemplate restTemplate=new RestTemplate();
	private final Logger logger = LoggerFactory.getLogger(BaseResourceClient.class);
	private Gson gson=new Gson();

	/**
	 * <p>描述 获取单科报告</p>
	 *
	 * @param reportRequestModel
	 * @return 报告内容
	 * @Author zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * Date: 2017/3/16 11:48
	 **/
	@Override
	public ReportModel getSingelReportFromResource(ReportRequestModel reportRequestModel) {
		SimpleClientHttpRequestFactory factory=new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(600000);
		factory.setReadTimeout(600000);
		restTemplate.setRequestFactory(factory);
		logger.debug("getSingelReportFromResource 报告请求参数--->："+gson.toJson(reportRequestModel));
		ResultResponse resultResponse= restTemplate.postForObject(baseResourceConfig.getServer()+"diagnoseController/subjectDiagnose",reportRequestModel,ResultResponse.class);
		logger.debug("getSingelReportFromResource 报告返回数据--->："+gson.toJson(resultResponse));
		if ("200".equals(resultResponse.getStatus())){
			return GsonUtils.toEntity(GsonUtils.toJson(resultResponse.getDatas()),ReportModel.class);
		}
		return null;
	}
}
