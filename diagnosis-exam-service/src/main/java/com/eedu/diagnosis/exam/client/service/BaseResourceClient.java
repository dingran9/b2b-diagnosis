package com.eedu.diagnosis.exam.client.service;

import org.apache.http.client.ClientProtocolException;

import java.io.IOException;
import java.util.List;

/**
 * 访问资源信息客户端
 * @author zhuchaowei
 * 2016年7月15日
 * Description
 */
public interface BaseResourceClient {
	/**
	 * 根据试卷code获取试卷信息  第二版结构新结构
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月15日 下午2:10:52
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public String getPapper(String paperCode) throws ClientProtocolException, IOException;

	/**
	 * 根据资源库 节的code 获取 课后作业和视频资源
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年9月2日 上午10:23:58
	 * @param repositoryBurlCode 资源库节code
	 * @return
	 */
	public String getAttendClassResources(String repositoryBurlCode);
	/**
	 * 获取课程体系
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年9月8日 下午5:32:04
	 * @param bookVersion 教材
	 * @param categoriesCode 小课程code
	 * @param bigCategoriesCode 大课程code
	 * @param gradeCode 学年code
	 * @param subjectCode 学科code
	 * @return
	 */
	public String getCourseFromRepository(String bookVersion, String categoriesCode, String bigCategoriesCode, String gradeCode, String subjectCode);
	/**
	 * 根据产生式集合 获取资源库20道难点攻克题
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年9月10日 下午3:02:23
	 * @param subjectCode 学科code
	 * @param gradeCode   学年code
	 * @param productionCodes 产生式code集合
	 * @return
	 */
	public String getQuestionsByProductionCode(String subjectCode, String gradeCode, List<String> productionCodes);
	/**
	 * 获取试卷第一版结构
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年10月28日 上午10:37:08
	 * @param paperId
	 * @return
	 */
	public String getPaperByIdFromRepository(String paperId);
}
