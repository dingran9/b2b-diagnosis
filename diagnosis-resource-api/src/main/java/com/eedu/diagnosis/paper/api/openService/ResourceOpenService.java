package com.eedu.diagnosis.paper.api.openService;

import com.eedu.diagnosis.paper.api.model.ResourceBaseDto;




/** 试卷资源 **/
public interface ResourceOpenService {

	/**  根据资源库试卷code获取试卷信息 **/
	public String getResourcePaperInfo(String resourcesPaperCode) throws Exception;

	/**描述：根据学年、学科、教材版本 、知识点获取视频列表**/
	public String getVideo(ResourceBaseDto model) throws Exception;

	/**
	 *根据学年、学科、教材版本 、知识点获取视频列表
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String getVideoByKnowledgeCodeAndSubjectCodeAndGradeCodeAndBookTypeCode(ResourceBaseDto model) throws Exception;

	/** 描述：根据学年、学科、教材版本 、知识点获取试题 **/
	public String getQuestions(ResourceBaseDto model) throws Exception;


	/** 根据学段,学科获取教材上下册和单元**/
	 public String getbookTypeVersionAndUnit(String gradeCode,String subjectCode,String ctbCode,String productsIds,String code)throws Exception;
	
	 /** 根据试题code，学科标记  获取试题的详情  **/
//	public String getQuestionInfo(String id, String subjectFlag) throws Exception;

	/** 根据单元code查询试卷  **/
	public String getPaperByUnit(String unitCode)throws Exception;

	/** 学年，学科查询教材版本  **/
	public String getBookVersion(String gradeCode, String subjectCode)throws Exception;
	/** 根据学年获取学科**/
	public String getSubjectByGradeCode(String gradeCode) throws Exception;

	public String getQuestionByDiagnosisPaper(String diagnosisPaperCode) throws Exception;

	String getQuestionsV2(ResourceBaseDto rbDto) throws Exception;

	String getArticleStem(String ids, String subjectCode)throws Exception ;

	String getQuestionsV3(ResourceBaseDto rbDto);
}
