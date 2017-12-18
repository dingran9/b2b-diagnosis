package com.eedu.diagnosis.paper.service.client.impl;

import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.common.model.paperEntity.SmallQuestionSystem;
import com.eedu.diagnosis.paper.persist.model.QuestionSet;
import com.eedu.diagnosis.paper.persist.model.ResponseQuestionsModel;
import com.eedu.diagnosis.paper.persist.model.ResultResponse;
import com.eedu.diagnosis.paper.service.client.BaseResourceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class BaseResourceClientImpl implements BaseResourceClient{
	
	@Value("${BASERESOURCE.SERVER.URL}")
	private String serverUrl;
	@Value("${RESOURCES_URL}")
	private String resourcesUrl;

	@Autowired  
	private RestTemplate restTemplate;
	
	/** get路径获取资源库的资源   **/
	public String  httpGet(String path)throws Exception{
		 String gsonResponse = restTemplate.getForObject(path, String.class);
		 return getJson(gsonResponse);
	}
	/** post路径获取资源库的资源   **/
	public String  httpPost(String path,Map<String, Object> map)throws Exception{
		 String gsonResponse = restTemplate.postForObject(path, map, String.class);
		 return getJson(gsonResponse);
	}
	public String  getJson(String gsonResponse){
		 ResultResponse response=   JSONObject.parseObject(gsonResponse, ResultResponse.class);;
		 if(response.getStatus().equals("200")){
		    return JSONObject.toJSONString(response.getDatas());
		 }
		return "";
	}
	public String  getData(String gsonResponse){
		 ResultResponse response=   JSONObject.parseObject(gsonResponse, ResultResponse.class);;
		 if(response.getStatus().equals("200")){
		    return JSONObject.toJSONString(response.getData());
		 }
		return null;
	}
	@Override
	public String getPapper(String  paperCode) throws Exception {
		String path=resourcesUrl+"/paperController/getPaperByscore3/"+paperCode;
		return httpGet(path);
	}
	@Override
	public String getAttendClassResources(String repositoryBurlCode)throws Exception {
		String path=resourcesUrl+"/paulcourseController/rent/getCourseModuleResources/"+repositoryBurlCode;
		return httpGet(path);
	}
	@Override
	public String getPaperByIdFromRepository(String paperId) throws Exception{
		String path=resourcesUrl+"/paperController/getPaperById/"+paperId;
		return httpGet(path);
	}
	 /**
     * 根据学年获取学科的接口
     */
	@Override
	public String getSubjectByGradeCode(String gradeCode)throws Exception {
		String path=resourcesUrl+"/rest/baseSubjectListByGradeCode/"+gradeCode;
		String gsonResponse = restTemplate.getForObject(path, String.class);
		 return  getData(gsonResponse);
	}
	 /**
     * 根据学年学科获取教材接口
     */
	@Override
	public String getBookVersion(String gradeCode, String subjectCode)throws Exception {
		String path=resourcesUrl+"/rest/baseBookTypeByGradeCodeandSubjectCode/"+gradeCode+"/"+subjectCode;
		String gsonResponse = restTemplate.getForObject(path, String.class);
		return  getData(gsonResponse);
	}
	/**
     * 描述：根据 学年 学科 教材版本 学生目标类型获取试卷列表
     */
	@Override
	public String getPagers(Map<String, Object> map)throws Exception {
		 String path = resourcesUrl+"/paperController/rest/getPapersTosore";
		 return httpPost(path, map);
	}
	/**
     * 描述：根据 学年 学科 教材版本 获取资源知识树
     */
	@Override
	public String knowledgeTree(Map<String, Object> map)throws Exception {
		//知识树类型(0:不完整;1:完整)                                根据学年,学科,教材版本获取知识树---请求地址(畸形知识树)                                   根据学年,学科,教材版本获取知识树    
        String path = ("0".equals(map.get("knowledgeType"))) ? resourcesUrl+"/bookKnowledgeTreeController/rest/getKnowledgeTreeByProducts" : resourcesUrl+"/rest/getKnowledgeTree";
        return httpPost(path, map);
	}
	 /**
     * 描述：根据学年、学科、教材版本 、知识点获取视频列表
     */
	@Override
	public String getVideo(Map<String, Object> map) throws Exception{
		 String path =resourcesUrl+"/instantiationTeaching/rest/queryAllVideosByCodeAndKnowledgecode";
		 String gsonResponse = restTemplate.postForObject(path,map, String.class);
		 return  getData(gsonResponse);
	}
	/**
     * 描述：根据学年、学科、教材版本 、知识点获取试题
     */
	@Override
	public String getQuestions(Map<String, Object> map) throws Exception{
         String path = resourcesUrl+"/rest/getQuestions";
		 return httpPost(path, map);
	}

	
	@Override
	public String getbookTypeVersionAndUnit(Map<String, Object> map)throws Exception {
		String path = resourcesUrl+"/instantiationTeaching/rest/getBookNodesCommon";
		 return httpPost(path, map);
	}
	
	


	@Override
	public String getPaperByUnit(String unitCode) throws Exception {
		String path = resourcesUrl+"/instantiationTeaching/rest/getBookNodesPapers/"+unitCode;
		return httpGet(path);
	}


	/**
	 * 获取视频
	 *
	 * @param knowledgeCodes 教材树知识点集合
	 * @param subjectCode    学科code
	 * @param gradeCode      学年code
	 * @param bookTypeCode   教材版本
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getVideoByKnowledgeCodeAndSubjectCodeAndGradeCodeAndBookTypeCode(List<String> knowledgeCodes, String subjectCode, String gradeCode, String bookTypeCode) throws Exception {
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("knowledges",knowledgeCodes);
		jsonObject.put("subjectCode",subjectCode);
		jsonObject.put("gradeCode",gradeCode);
		jsonObject.put("bookTypeCode","nkCDYaGSR6sNQx868N2cw3BBaPbtRanX");

		String path = resourcesUrl+"/rest/getVideoList";
		return restTemplate.postForObject(path, jsonObject, String.class);
	}
	@Override
	public String getQuestionListByUnitCode(String unitCode) {
		 String path =resourcesUrl+"/instantiationTeaching/rest/getBookNodeDetailInfo/"+unitCode;
		 String gsonResponse = restTemplate.getForObject(path, String.class);
		 return  getData(gsonResponse);
	}

	@Override
	public String getQuestionsV2(Map<String, Object> map)  throws Exception {
		String path = resourcesUrl+"/instantiationTeaching/rest/queryRootNoteResources";
		String gsonResponse = restTemplate.postForObject(path, map, String.class);
		return getData(gsonResponse);
	}

	@Override
	public String getArticleStem(String ids, String subjectCode) throws Exception {
		String path =resourcesUrl+"/questionController/getQuestions/"+ids+"/"+subjectCode;
		return httpGet(path);
	}

	@Override
	public String getQuestionsV3(Map<String, Object> map) {
		String path = resourcesUrl+"/questionController/rest/getCertainQuestionsV2";
		String gsonResponse = restTemplate.postForObject(path, map, String.class);
        ResponseQuestionsModel responseQuestionsModel = JSONObject.parseObject(gsonResponse, ResponseQuestionsModel.class);
        if(responseQuestionsModel.getStatus().equals("200")){
            List<QuestionSet> questionObject = responseQuestionsModel.getQuestionObject();
            if(!CollectionUtils.isEmpty(questionObject)){
                QuestionSet questionSet = questionObject.get(0);
                if(null != questionSet){
                    List<SmallQuestionSystem> questionList = questionSet.getQuestionList();
                    if(!CollectionUtils.isEmpty(questionList)){
                        return JSONObject.toJSONString(questionList);
                    }else {
                        return null;
                    }
                }
            }
        }
        return null;
	}
}
