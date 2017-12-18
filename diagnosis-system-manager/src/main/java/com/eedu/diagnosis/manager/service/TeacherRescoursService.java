package com.eedu.diagnosis.manager.service;

import com.eedu.diagnosis.common.model.paperEntity.PaperSystem;
import com.eedu.diagnosis.manager.model.request.ResourceBaseModel;
import com.eedu.diagnosis.manager.model.response.BaseBookVersionVo;
import com.eedu.diagnosis.manager.model.response.BaseDataVo;
import com.eedu.diagnosis.manager.model.response.SubjectVo;
import com.eedu.diagnosis.manager.model.response.question.ChineseQuestion;

import java.util.List;


/**
 * zz
 */
public interface TeacherRescoursService {


	PaperSystem getResourcePaper(String PaperCode)throws Exception;

	List<ChineseQuestion> getQuestionInfo(String questionCode, String subjectFlag) throws Exception;

	List<BaseDataVo> getPaperByUnit(String unitCode)throws Exception;

	List<BaseDataVo> getbookTypeVersionAndUnit(String gradeCode, String subjectCode,String ctbCode, String productsIds, String code) throws Exception;

	List<BaseBookVersionVo> getBookTypeVersion(String gradeCode,String subjectCode) throws Exception;

	List<SubjectVo> getSubjectByGradeCode(String gradeCode) throws Exception;

	List<SubjectVo> getNoSubjectBySchoolGrade(String schoolId,String gradeCode) throws Exception;

	/**
	 * 教研员报告 获取单元列表
	 * @param model
	 * @return
	 */
    List<BaseDataVo> getUnitListWithStatus(ResourceBaseModel model) throws Exception;

	/**
	 * 发布考试调用  获取上下册  根据系统时间自动判断返回上/下册 如果是全册直接返回
	 * @param gradeCode
	 * @param subjectCode
	 * @param booktypeCode
	 * @param productsIds
	 * @param code
	 * @return
	 */
	List<BaseDataVo> getVolume(String gradeCode, String subjectCode, String booktypeCode, String productsIds, String code) throws Exception;
}
