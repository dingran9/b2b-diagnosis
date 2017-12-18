package com.eedu.diagnosis.paper.service.openServiceImlp;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.eedu.diagnosis.paper.api.dto.DiagnosisPaperDto;
import com.eedu.diagnosis.paper.api.model.ResourceBaseDto;
import com.eedu.diagnosis.paper.api.openService.BasePaperOpenService;
import com.eedu.diagnosis.paper.api.openService.ResourceOpenService;
import com.eedu.diagnosis.paper.service.client.BaseResourceClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service
public class ResourceOpenServiceImpl implements ResourceOpenService{
	

	@Autowired
	private BaseResourceClient baseResourceClient;
	@Autowired
	private BasePaperOpenService basePaperOpenService;


	/** 
	 *  根据资源库试卷code获取试卷信息
	 **/
	@Override
	public String getResourcePaperInfo(String resourcesPaperCode) throws Exception {
		String paperJson = baseResourceClient.getPapper(resourcesPaperCode);
		return paperJson;
	}
     /**
      * 描述：根据学年、学科、教材版本 、知识点获取视频列表
      */
     @Override
     public String getVideo(ResourceBaseDto model)throws Exception{
    	 if (null == model) {
 			return null;
 		  }
//    	DiagnosisPaperDto dpDto = new DiagnosisPaperDto();
//    	dpDto.setCode(model.getDiagnosisPaperCode());
//	    List<DiagnosisPaperDto> diagnosisPaperList = basePaperOpenService.getDiagnosisPaperList(dpDto);
//    	if (diagnosisPaperList==null || diagnosisPaperList.size() ==0) {
//			return null;
//		}
		 Map<String, Object> map = new HashMap<>();
		 map.put("code",model.getUnitCode());
		 map.put("knowledgeCode",model.getKnowledgeCode());
    	 return  baseResourceClient.getVideo(map);
     }
	/**
	 * 根据学年、学科、教材版本 、知识点获取视频列表
	 *
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getVideoByKnowledgeCodeAndSubjectCodeAndGradeCodeAndBookTypeCode(ResourceBaseDto model) throws Exception {
		if (null == model) {
			return "";
		}
		return baseResourceClient.getVideoByKnowledgeCodeAndSubjectCodeAndGradeCodeAndBookTypeCode(model.getKnowledges(),model.getSubjectCode(),model.getGradeCode(),model.getBookTypeCode());
	}
	/**
      * 描述：根据单元卷code获取试题
      * 
      */
	@Override
	public String getQuestionByDiagnosisPaper(String diagnosisPaperCode) throws Exception {
		DiagnosisPaperDto dpDto = new DiagnosisPaperDto();
    	dpDto.setCode(diagnosisPaperCode);
	    List<DiagnosisPaperDto> diagnosisPaperList = basePaperOpenService.getDiagnosisPaperList(dpDto);
    	if (diagnosisPaperList==null || diagnosisPaperList.size() ==0) {
			return null;
		}
    	
    	return baseResourceClient.getQuestionListByUnitCode(diagnosisPaperList.get(0).getUnitCode());
	}

	@Override
	public String getQuestionsV2(ResourceBaseDto model) throws Exception {
		if (null == model) {
			return "";
		}
		Map<String, Object> map = new HashMap<>();
		map.put("code",model.getUnitCode());
		map.put("knowledgeCode",model.getKnowledgeCode());
		return baseResourceClient.getQuestionsV2(map);
	}
    /**
     * 描述：根据学科， 文章code获取文章实体
     *
     */
    @Override
    public String getArticleStem(String ids, String subjectCode)throws Exception  {
		return baseResourceClient.getArticleStem(ids,subjectCode);
    }

	@Override
	public String getQuestionsV3(ResourceBaseDto rbDto) {
		if (null == rbDto) {
			return null;
		}
		Map<String, Object> map = new HashMap<>();
		List videoIds = new ArrayList();
		videoIds.add(rbDto.getVideoId());
		map.put("videoIds",videoIds);
		map.put("subjectFlag",rbDto.getSubjectCode());
		return baseResourceClient.getQuestionsV3(map);
	}

	/**
      * 描述：根据学年、学科、教材版本 、知识点获取试题
      * 
      */
     @Override
     public String getQuestions(ResourceBaseDto model)throws Exception{
    	   if (null == model) {
 			    return "";
 		     }
    	   Map<String, Object> map = getMap(model);
           return baseResourceClient.getQuestions(map);
     }
     /**
      * 描述：获取教材上下册，和单元列表
      */
     public String getbookTypeVersionAndUnit(String gradeCode,String subjectCode,String ctbCode,String productsIds,String code)throws Exception{
    	 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("gradeCode",   gradeCode);
		 map.put("subjectCode", subjectCode);
		 map.put("ctbCode", ctbCode);
		 map.put("productsIds", productsIds);
		 map.put("code", code);
		 return baseResourceClient.getbookTypeVersionAndUnit(map);		
     }
     /** 
 	 * 获取单元的试卷信息 id name
 	 *   **/
 	@Override
 	public String getPaperByUnit(String unitCode) throws Exception {
 		return baseResourceClient.getPaperByUnit(unitCode);		
 	} 
    /** 
   	 * 根据学段,学科获取教材
   	 **/
      @Override
   	public String getBookVersion(String gradeCode,String subjectCode) throws Exception{
      	 return baseResourceClient.getBookVersion(gradeCode,subjectCode);
   	}
      /**
       * 根据学年获取学科的接口
       */
       @Override
       public String getSubjectByGradeCode(String gradeCode) throws Exception{
           return baseResourceClient.getSubjectByGradeCode(gradeCode);
       }
     public Map<String, Object> getMap(Object model){
         Map<String, Object> map = JSONObject.parseObject(JSONObject.toJSONString(model),new TypeReference<Map<String, Object>>(){} );
         return map;
     }

}
