package com.eedu.diagnosis.paper.service.client;

import java.util.List;
import java.util.Map;




/**
 * 资源信息客户端
 */
public interface BaseResourceClient {
	/**
	 * 根据试卷code获取试卷信息  第二版结构新结构
	 */
	public String getPapper(String paperCode) throws Exception;
	
	/**
	 * 根据资源库 节的code 获取 课后作业和视频资源
	 */
	public String getAttendClassResources(String repositoryBurlCode)throws Exception;
	/**
	 * 获取试卷第一版结构
	 */
	public String getPaperByIdFromRepository(String paperId)throws Exception;
	 /**
     * 根据学年获取学科的接口
     */
	public String getSubjectByGradeCode(String gradeCode)throws Exception;
	 /**
     * 根据学年学科获取教材接口
     */
	public String getBookVersion(String gradeCode, String subjectCode)throws Exception;
	/**
     * 描述：根据 学年 学科 教材版本 学生目标类型获取试卷列表
     */
	public String getPagers(Map<String, Object> map)throws Exception;
    /**
     * 描述：根据 学年 学科 教材版本 获取资源知识树
     */
	public String knowledgeTree(Map<String, Object> map)throws Exception;
	 /**
     * 描述：根据学年、学科、教材版本 、知识点获取视频列表
     */
	public String getVideo(Map<String, Object> map) throws Exception;
	/**
     * 描述：根据学年、学科、教材版本 、知识点获取试题
     */
	public String getQuestions(Map<String, Object> map)throws Exception;
	
	
	public String getbookTypeVersionAndUnit(Map<String, Object> map)throws Exception;

	public String getPaperByUnit(String unitCode) throws Exception;


	/**
	 * 获取视频
	 * @param knowledgeCodes 教材树知识点集合
	 * @param subjectCode 学科code
	 * @param gradeCode   学年code
	 * @param bookTypeCode 教材版本
	 * @return
	 * @throws Exception
	 */
	public String getVideoByKnowledgeCodeAndSubjectCodeAndGradeCodeAndBookTypeCode(List<String> knowledgeCodes,String subjectCode,String gradeCode,String bookTypeCode) throws Exception;

	
	public String getQuestionListByUnitCode(String unitCode);


	String getQuestionsV2(Map<String, Object> map) throws Exception;

    String getArticleStem(String ids, String subjectCode) throws Exception;

	String getQuestionsV3(Map<String, Object> map);
}
