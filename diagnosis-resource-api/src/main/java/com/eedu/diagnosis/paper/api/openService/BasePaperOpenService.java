package com.eedu.diagnosis.paper.api.openService;

import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.paper.api.dto.*;
import com.github.pagehelper.PageInfo;

import java.util.List;

/** 试卷资源 **/
public interface BasePaperOpenService {

	/**  保存单科诊断卷 **/
	public String saveDiagnosisPaper(DiagnosisPaperDto dpDto) throws Exception;
	
	/** 保存单科诊断卷与学校的关系 **/
	public String saveBatchDiagnosisPaperSchoolRelation(List<DiagnosisPaperSchoolRelationDto> dpsrDtoList) throws Exception ;
	
	/** 修改单科诊断卷与学校的关系 **/
	public  String updateDiagnosisPaperSchoolRelation(List<DiagnosisPaperSchoolRelationDto> dpsrDtoList) throws Exception;
	
	/**  保存全科诊断卷 **/
	public String saveDiagnosisComplexPaper(DiagnosisComplexPaperDto dcpDto)throws Exception;

	/**  查询单科诊断卷集合  **/
	public List<DiagnosisPaperDto> getDiagnosisPaperList(DiagnosisPaperDto dpDto)throws Exception;
	
	/**获取单科卷-分页 **/
	public PageInfo<DiagnosisPaperDto> getDiagnosisPaperPaper(DiagnosisPaperDto dpDto,Integer pageNum, Integer pageSize,Order order  ) throws Exception;
	
	/**获取单科卷关联学校-分页 **/
	public PageInfo<DiagnosisPaperDto> getDiagnosisPaperRelationSchoolByPaper(DiagnosisPaperDto dpDto, Integer pageNum, Integer pageSize,Order order) throws Exception;
	
	/**  查询全科诊断卷集合  **/
	public List<DiagnosisComplexPaperDto> getDiagnosisComplexPaperList(DiagnosisComplexPaperDto dcpDto) throws Exception;

	/**获取全科卷-分页 **/
	public PageInfo<DiagnosisComplexPaperDto> getDiagnosisComplexPaperPaper(DiagnosisComplexPaperDto dcpDto, Integer pageNum, Integer pageSize,Order order,Integer flag)throws Exception;

	/**  获取单科-全科卷关系 **/
	public List<DiagnosisComplexPaperRelationDto> getDiagnosisComplexPaperRelationList(DiagnosisComplexPaperRelationDto dcprDto) throws Exception;
	
	/**  保存或修改诊断收藏  **/
	public String saveOrUpdateDiagnosisFavoriteService(DiagnosisFavoriteDto dfDto)throws Exception;

	/**修改全科卷**/
	public String updateDiagnosisComplexPaperList(List<DiagnosisComplexPaperDto> dcpDtoList)throws Exception;

	/**  查询诊断收藏 -分页 **/
	public PageInfo<DiagnosisFavoriteDto> getDiagnosisFavoritePaper(DiagnosisFavoriteDto dfDto, Integer pageNum, Integer pageSize,Order order)throws Exception;

	/** 保存大题 **/
	public  List<String> saveDiagnosisBigQuestion(List<DiagnosisBigQuestionDto> dbqDtoList)throws Exception;

	/** 保存小题 **/
	public List<String> saveDiagnosisSmallQuestion(List<DiagnosisSmallQuestionDto> dsqDtoList) throws Exception;

	/**  保存自组卷和自组卷与大题关系 **/
	public String saveDiagnosisBasePaper(DiagnosisBasePaperDto dbpDto)throws Exception;
	
	/** 获取大题列表  **/
	public List<DiagnosisBigQuestionDto> getDiagnosisBigQuestionList(DiagnosisBigQuestionDto dbqDto) throws Exception;

	/** 获取小题列表 **/
	public List<DiagnosisSmallQuestionDto> getDiagnosisSmallQuestionList(DiagnosisSmallQuestionDto dsqDto) throws Exception;

	/** 获取大题-小题关系列表 **/
	public List<DiagnosisQuestionRelationDto> getDiagnosisQuestionRelationList(DiagnosisQuestionRelationDto dqrDto) throws Exception;

	/**根据全科code获取单科信息**/
	public List<DiagnosisPaperDto> getDiagnosisPaperListByComplexPaperCode(String code)throws Exception;
	
	/** 查询自组卷列表 **/
	public List<DiagnosisBasePaperDto> getDiagnosisBasePaperList(DiagnosisBasePaperDto dbpDto) throws Exception;

	/** 获取单科诊断卷与学校的关系 **/
	public PageInfo<DiagnosisPaperSchoolRelationDto> getDiagnosisPaperSchoolRelationList(DiagnosisPaperSchoolRelationDto dpsrDto,Integer pageNum,Integer pageSize,Order order)throws Exception ;

	/** 发送短信 **/
	public String sendSms(String phone, String smsType);

	public List<DiagnosisPaperDto> getDiagnosisPaperList(List<String> list)throws Exception;
	/**保存反馈*/
	public String saveDiagnosisFeedback(DiagnosisFeedbackDto dfDto) throws Exception;
	/** 获取全科诊断卷和全科卷关系表 **/
	public List<DiagnosisComplexPaperDto> getDiagnosisComplexPaperAndRelationList(DiagnosisComplexPaperDto dcpDto)throws Exception ;

	public String updateDiagnosisComplexPaper(DiagnosisComplexPaperDto dcpDto)throws Exception;
	/**查看反馈**/
	public List<DiagnosisFeedbackDto> getDiagnosisFeedback(DiagnosisFeedbackDto dfDto) throws Exception;
	/**app更新---获取列表**/
	public PageInfo<DiagnosisAppUpdateDto>  diagnosisAppUpdateList(DiagnosisAppUpdateDto dauDto, Integer pageNum, Integer pageSize,Order order)throws Exception;
	/**app更新---添加url**/
	public  String  addApkUrl(DiagnosisAppUpdateDto dauDto)throws Exception;

	/**
	 * 检查APP版本
	 * @param appType APP类型 IOS Android
	 *                @param  clientType 客户端类型  学生 教师 校长
	 * @return
	 */
	public DiagnosisAppUpdateDto checkAppVersion(String appType,String clientType);
}
