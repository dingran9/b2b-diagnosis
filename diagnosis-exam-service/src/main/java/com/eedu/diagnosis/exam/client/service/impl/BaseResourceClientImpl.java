package com.eedu.diagnosis.exam.client.service.impl;

import com.eedu.diagnosis.exam.client.CourseRequestModel;
import com.eedu.diagnosis.exam.client.service.BaseResourceClient;
import com.eedu.diagnosis.exam.model.ResultResponseModel;
import com.google.gson.Gson;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@Service
public class BaseResourceClientImpl implements BaseResourceClient {
	@Value("${baseresource.server.url}")
	private String serverUrl;
    @Autowired
    private RestTemplate restTemplate;
	private Gson gson=new Gson();
	@Override
	public String getPapper(String  paperCode) throws ClientProtocolException, IOException {
		String path="/paperController/getPaperByscore3/"+paperCode;
        URI uri = URI.create(serverUrl+path);
        String paperResult = restTemplate.getForObject(uri, String.class);
//        String paperResult= HTTPClientUtils.httpGetRequestJson(serverUrl+path);
	    Gson gson=new Gson();
	    ResultResponseModel response=   gson.fromJson(paperResult, ResultResponseModel.class);
	    if(response.getStatus().equals("200")){
	    	return gson.toJson(response.getDatas());
	    }
	     return "";
	}
	@Override
	public String getAttendClassResources(String repositoryBurlCode) {
		String path="/paulcourseController/rent/getCourseModuleResources/"+repositoryBurlCode;
        URI uri = URI.create(serverUrl+path);
        String paperResult = restTemplate.getForObject(uri, String.class);
//		String paperResult=HTTPClientUtils.httpGetRequestJson(serverUrl+path);
		ResultResponseModel response=   gson.fromJson(paperResult, ResultResponseModel.class);
	    if(response.getStatus().equals("200")){
	    	return gson.toJson(response.getDatas());
	    }
	     return "";
	}
	@Override
	public String getCourseFromRepository(String bookVersion,
			String categoriesCode, String bigCategoriesCode, String gradeCode,
			String subjectCode) {

		String path="/paulcourseController/getCourseSmallByCondition";
		CourseRequestModel model=new CourseRequestModel("bigName", categoriesCode, bigCategoriesCode, gradeCode, subjectCode, bookVersion);
        String courseResult;
        URI uri = URI.create(serverUrl+path);
        courseResult = restTemplate.postForObject(uri,model,String.class);
//				courseResult = HTTPClientUtils.httpPostRequestJson(serverUrl+path, gson.toJson(model));
        ResultResponseModel response=   gson.fromJson(courseResult, ResultResponseModel.class);
        if(response.getStatus().equals("200")){
            return gson.toJson(response.getDatas());
        }
        return "";

	}
	@Override
	public String getQuestionsByProductionCode(String subjectCode,
			String gradeCode, List<String> productionCodes) {

		return null;
	}
	@Override
	public String getPaperByIdFromRepository(String paperId) {
		String path="/paperController/getPaperById/"+paperId;
//		String paperResult=HTTPClientUtils.httpGetRequestJson(serverUrl+path);
        URI uri = URI.create(serverUrl+path);
        String paperResult = restTemplate.getForObject(uri, String.class);
	    Gson gson=new Gson();
		ResultResponseModel response=   gson.fromJson(paperResult, ResultResponseModel.class);
	    if(response.getStatus().equals("200")){
	    	return gson.toJson(response.getDatas());
	    }
	     return "";
	}
	
	
}
