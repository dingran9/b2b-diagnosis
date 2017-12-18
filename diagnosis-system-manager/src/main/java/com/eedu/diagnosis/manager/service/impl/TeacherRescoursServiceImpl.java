package com.eedu.diagnosis.manager.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.eedu.auth.beans.AuthSubjectBean;
import com.eedu.diagnosis.common.model.paperEntity.PaperSystem;
import com.eedu.diagnosis.exam.api.dto.DiagnosisRecordTeacherDto;
import com.eedu.diagnosis.exam.api.openService.DiagnosisRecordTeacherOpenService;
import com.eedu.diagnosis.manager.model.request.ResourceBaseModel;
import com.eedu.diagnosis.manager.model.response.BaseBookVersionVo;
import com.eedu.diagnosis.manager.model.response.BaseDataVo;
import com.eedu.diagnosis.manager.model.response.SubjectVo;
import com.eedu.diagnosis.manager.model.response.question.ChineseQuestion;
import com.eedu.diagnosis.manager.service.GroupDataService;
import com.eedu.diagnosis.manager.service.TeacherRescoursService;
import com.eedu.diagnosis.paper.api.openService.ResourceOpenService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 *   zz
 */
@Service
public class TeacherRescoursServiceImpl implements TeacherRescoursService {
    @Autowired
    private ResourceOpenService resourceOpenServiceImpl;
	@Autowired
	private GroupDataService groupDataService;
	@Autowired
	private DiagnosisRecordTeacherOpenService diagnosisRecordTeacherOpenService;

	/**
	 *  查询资源卷
	 */
	@Override
	public PaperSystem getResourcePaper(String PaperCode)throws Exception {
		String responseJson = resourceOpenServiceImpl.getResourcePaperInfo(PaperCode);
		if (responseJson!=null && !"".equals(responseJson)) {
		    PaperSystem parseObject = JSONObject.parseObject(responseJson, PaperSystem.class);
			return parseObject;
		}
		return null;
	}
	 /**
     * 	 根据学段,学科获取教材上下册和单元
	 * @return 
	 * @throws Exception 
     */
	@Override
	public List<BaseDataVo> getbookTypeVersionAndUnit(String gradeCode,String subjectCode,String ctbCode,String productsIds,String code) throws Exception {
		String responseJson =resourceOpenServiceImpl.getbookTypeVersionAndUnit(gradeCode, subjectCode, ctbCode, productsIds, code);
		if (responseJson!=null && !"".equals(responseJson)) {
			List<BaseDataVo> vo = JSONObject.parseArray(responseJson, BaseDataVo.class);
			//设置系统当前时间
			if(!CollectionUtils.isEmpty(vo)){
				for (BaseDataVo baseDataVo : vo) {
					baseDataVo.setServerTime(new Date());
				}
			}
			return vo;
		}
		return null;
	}

	
	/**
	 * 根据单元查询试卷
	 **/
	@Override
	public List<BaseDataVo> getPaperByUnit(String unitCode) throws Exception{
		String responseJson = resourceOpenServiceImpl.getPaperByUnit(unitCode);
		if (responseJson!=null && !"".equals(responseJson)) {
		List<BaseDataVo> vo = JSONObject.parseArray(responseJson, BaseDataVo.class);
		return vo;
	}
	return null;
	}

   
	/**
  	 * 查询试题详情
  	 **/
	@SuppressWarnings("unchecked")
	@Override
	public List<ChineseQuestion> getQuestionInfo(String questionCode, String subjectFlag) throws Exception {
//		String responseJson =resourceOpenServiceImpl.getQuestionInfo(questionCode, subjectFlag);
//		if (responseJson!=null && !"".equals(responseJson)) {
//			List<ChineseQuestion> list = JSONObject.parseArray(responseJson, ChineseQuestion.class);
//			ChineseQuestion chineseQuestion = list.get(0);
//			if (chineseQuestion!=null) {
//				chineseQuestion.setQuesAnalyzes(JSONObject.parseArray(chineseQuestion.getQues_analyze(),QuestionAnalysisModel.class));
//				chineseQuestion.setQuesOptions(JSONObject.parseArray(chineseQuestion.getQues_option(),QuesionOptionModel.class));
//				chineseQuestion.setQues_analyze(null);
//				chineseQuestion.setQues_option(null);
//			}
//			
//			return list;
//		}
		return null;
	}
	@Override
	public List<BaseBookVersionVo> getBookTypeVersion(String gradeCode,String subjectCode) throws Exception {
		String responseJson = resourceOpenServiceImpl.getBookVersion(gradeCode, subjectCode);
		if (responseJson!=null && !"".equals(responseJson)) {
		List<BaseBookVersionVo> vo = JSONObject.parseArray(responseJson, BaseBookVersionVo.class);
		return vo;
	 }
		return null;	
	}
	
	
	@Override
	public List<SubjectVo> getSubjectByGradeCode(String gradeCode) throws Exception {
		String responseJson = resourceOpenServiceImpl.getSubjectByGradeCode(gradeCode);
		if (responseJson!=null && !"".equals(responseJson)) {
		List<SubjectVo> vo = JSONObject.parseArray(responseJson, SubjectVo.class);
		return vo;
	 }
		return null;	
	}


	@Override
	public List<SubjectVo> getNoSubjectBySchoolGrade(String schoolId,String gradeCode) throws Exception {

		String responseJson = resourceOpenServiceImpl.getSubjectByGradeCode(gradeCode);
		Map<String,Object> maps = new HashMap<String,Object>();
		maps.put("schoolId",Integer.valueOf(schoolId));
		maps.put("gradeIden",Integer.valueOf(gradeCode));
		List<AuthSubjectBean> list = groupDataService.managerMaterial(maps);
		if (responseJson!=null && !"".equals(responseJson)) {
			List<SubjectVo> vo = JSONObject.parseArray(responseJson, SubjectVo.class);
			if(null != list && list.size() > 0 ){
				Set<String> set = new HashSet<>();
				for(AuthSubjectBean bean : list){
					set.add(String.valueOf(bean.getSubjectIden()));
				}
				List<SubjectVo> vos = new ArrayList<>();
				for(SubjectVo subjectVo : vo){
					if(set.add(subjectVo.getSubject_code()))vos.add(subjectVo);
				}
				return vos;
			}else {
				return vo;
			}
		}
		return null;
	}

	@Override
	public List<BaseDataVo> getUnitListWithStatus(ResourceBaseModel model) throws Exception{
		String responseJson = resourceOpenServiceImpl.getbookTypeVersionAndUnit(model.getGradeCode(), model.getSubjectCode(), model.getBooktypeCode(),
				model.getProductsIds(), model.getCode());
		if (!StringUtils.isEmpty(responseJson)) {
			List<BaseDataVo> vo = JSONObject.parseArray(responseJson, BaseDataVo.class);
			if(!CollectionUtils.isEmpty(vo)){
				DiagnosisRecordTeacherDto drtd = new DiagnosisRecordTeacherDto();
				drtd.setExamType(0);
				drtd.setSubjectCode(Integer.parseInt(model.getSubjectCode()));
				drtd.setGradeCode(Integer.parseInt(model.getGradeCode()));
				drtd.setGroupAreaDistrictId(model.getDistrictId());
				drtd.setExamYear(model.getExamYear());
				PageInfo<DiagnosisRecordTeacherDto> pageInfo = diagnosisRecordTeacherOpenService.getDiagnosisListBySubject(drtd, 1, Integer.MAX_VALUE, null);
				List<DiagnosisRecordTeacherDto> list = pageInfo.getList();
				if(!CollectionUtils.isEmpty(list)){
					for (DiagnosisRecordTeacherDto diagnosisRecordTeacherDto : list) {
						for (BaseDataVo baseDataVo : vo) {
							if(diagnosisRecordTeacherDto.getUnitCode().equals(baseDataVo.getCode())){
								baseDataVo.setHasReport(true);
							}
						}
					}
				}
			}
			return vo;
		}
		return null;
	}

	private int getExamYear() {
		int num = 0;
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		if (2 <= month && month < 9) {//如果月份为2-9月份 考试年为来下学期
			num = 1;
		}
		return num;
	}

	@Override
	public List<BaseDataVo> getVolume(String gradeCode, String subjectCode, String booktypeCode, String productsIds, String code) throws Exception{
		List<BaseDataVo> result = new ArrayList<>();
		String responseJson =resourceOpenServiceImpl.getbookTypeVersionAndUnit(gradeCode, subjectCode, booktypeCode, productsIds, code);
		if (responseJson!=null && !"".equals(responseJson)) {
			List<BaseDataVo> vo = JSONObject.parseArray(responseJson, BaseDataVo.class);
			if(!CollectionUtils.isEmpty(vo) && vo.size() > 1){
				result.add(vo.get(getExamYear()));
			}else {
				result.addAll(vo);
			}
			return result;
		}
		return null;
	}

}
