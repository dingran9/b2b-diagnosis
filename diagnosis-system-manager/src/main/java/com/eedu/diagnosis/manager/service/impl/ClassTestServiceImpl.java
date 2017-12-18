package com.eedu.diagnosis.manager.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.eedu.auth.beans.AuthUserBean;
import com.eedu.auth.service.AuthUserManagerService;
import com.eedu.diagnosis.common.utils.PageHelperUtil;
import com.eedu.diagnosis.inclass.test.api.dto.*;
import com.eedu.diagnosis.inclass.test.api.enums.ConstantEnum;
import com.eedu.diagnosis.inclass.test.api.model.QuestionBankModel;
import com.eedu.diagnosis.inclass.test.api.service.*;
import com.eedu.diagnosis.manager.model.request.ClassTest.*;
import com.eedu.diagnosis.manager.model.request.ClassTest.machine.Answer;
import com.eedu.diagnosis.manager.model.request.ClassTest.machine.ExercisesAnswer;
import com.eedu.diagnosis.manager.service.ClassTestService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


/**
 * zz
 */
@Service
public class ClassTestServiceImpl implements ClassTestService {
	@Autowired
	private DiagnosisBaseQuestionService diagnosisBaseQuestionService ;//基础题
	@Autowired
	private DiagnosisInClassRelationService diagnosisInClassRelationService ;//课中测-基础题
	@Autowired
	private DiagnosisInClassTestService diagnosisInClassTestService ;//课中测
	@Autowired
	private DiagnosisQuestionBankService diagnosisQuestionBankService ;//题库
	@Autowired
	private DiagnosisStudentAnswerSheetService diagnosisStudentAnswerSheetService ;//课中测答题
	@Autowired
	private DiagnosisStudentAnswerRankingService  diagnosisStudentAnswerRankingService ;//课中测答题
	@Autowired
	private DiagnosisStudentAnswerMachineService diagnosisStudentAnswerMachineService ;
	@Autowired
	private AuthUserManagerService userManagerService;





	/**根据题库code获取基础题**/
	@Override
	public Map<String, Object> baseQuestionListByQuestionBankCode(DiagnosisBaseQuestionModel model) throws Exception {
		DiagnosisBaseQuestionDto dto = new DiagnosisBaseQuestionDto();
		BeanUtils.copyProperties(model,dto);
		PageInfo<DiagnosisBaseQuestionDto> pageInfo = diagnosisBaseQuestionService.selectDiagnosisBaseQuestionPage(dto);
		PageInfo<DiagnosisBaseQuestionVo> diagnosisBaseQuestionVoPageInfo = PageHelperUtil.pageInfoConverter(pageInfo, DiagnosisBaseQuestionVo.class);
		return getMap(diagnosisBaseQuestionVoPageInfo);
	}
	/** 基础题新增**/
	@Override
	public boolean diagnosisBaseQuestionSave(DiagnosisBaseQuestionModel model) throws Exception {
		boolean b = false;
		String uuid = getUUID();
		DiagnosisBaseQuestionDto dto =  new DiagnosisBaseQuestionDto();
		BeanUtils.copyProperties(model,dto);
		dto.setBaseCode(uuid);
		b = diagnosisBaseQuestionService.saveDiagnosisBaseQuestion(dto) > 0;
		if (b){
			DiagnosisQuestionBankDto bean = getQuestionBankDto(model.getQuestionBookCode());
			if (bean!=null){
				bean.setQuestionCount(bean.getQuestionCount()+1);
				diagnosisQuestionBankService.updateDiagnosisQuestionBank(bean);
			}
		}
		return b;
	}
	/** 基础题修改**/
	@Override
	public boolean baseQuestionUpdate(DiagnosisBaseQuestionModel model) throws Exception {
		DiagnosisBaseQuestionDto dto =  new DiagnosisBaseQuestionDto();
		BeanUtils.copyProperties(model,dto);
		return diagnosisBaseQuestionService.updateDiagnosisBaseQuestion(dto) > 0;
	}
    /** 基础题删除**/
	@Override
	public boolean deleteDiagnosisBaseQuestion(String baseCode, String questionBookCode) throws Exception{
		boolean b = diagnosisBaseQuestionService.deleteDiagnosisBaseQuestion(baseCode) > 0;
		if (b){
			DiagnosisQuestionBankDto bean = getQuestionBankDto(questionBookCode);
			if (bean!=null){
				bean.setQuestionCount(bean.getQuestionCount()-1);
				diagnosisQuestionBankService.updateDiagnosisQuestionBank(bean);
			}
		}
		return b;
	}
	/** 题库列表**/
	@Override
	public Map<String, Object> diagnosisQuestionBankList(DiagnosisQuestionBankModel model) throws Exception {
		DiagnosisQuestionBankDto dto = new DiagnosisQuestionBankDto();
		BeanUtils.copyProperties(model,dto);
		PageInfo<DiagnosisQuestionBankDto> pageInfo = diagnosisQuestionBankService.selectDiagnosisQuestionBankPage(dto);
		return getMap(pageInfo);
	}
	/** 题库修改**/
	@Override
	public boolean questionBankUpdate(DiagnosisQuestionBankModel model) throws Exception {
		DiagnosisQuestionBankDto dto = new DiagnosisQuestionBankDto();
		BeanUtils.copyProperties(model,dto);
		boolean b = diagnosisQuestionBankService.updateDiagnosisQuestionBank(dto) > 0;
		if (b && StringUtils.isNotBlank(model.getQuestionBookName())){
			DiagnosisBaseQuestionDto dtos = new DiagnosisBaseQuestionDto();
			dtos.setQuestionBookCode(model.getQuestionBookCode());
			List<DiagnosisBaseQuestionDto> list = diagnosisBaseQuestionService.selectDiagnosisBaseQuestionList(dtos);
			if (!CollectionUtils.isEmpty(list)){
				for (DiagnosisBaseQuestionDto bean: list){
					bean.setQuestionBookName(model.getQuestionBookName());
					diagnosisBaseQuestionService.updateDiagnosisBaseQuestion(bean);
				}
			}
		}
		return b;
	}
    /** 题库新增**/
	@Override
	public boolean diagnosisQuestionBankSave(DiagnosisQuestionBankModel model) throws Exception {
		DiagnosisQuestionBankDto dto = new DiagnosisQuestionBankDto();
	    BeanUtils.copyProperties(model,dto);
		dto.setQuestionBookCode(getUUID());
		dto.setQuestionCount(0);
		return diagnosisQuestionBankService.saveDiagnosisQuestionBank(dto) > 0;
	}
	/** 题库删除**/
	@Override
	public boolean diagnosisQuestionBankDelete(DiagnosisQuestionBankModel model)throws Exception  {
		return diagnosisQuestionBankService.deleteDiagnosisQuestionBank(model.getQuestionBookCode()) > 0;
	}
	/***********************************课中测**************************************/
	/** 课中测分页列表**/
	@Override
	public Map<String, Object> classTestList(DiagnosisInClassTestModel model) throws Exception {
		List<DiagnosisTeacherVo> voList = new ArrayList<DiagnosisTeacherVo>();

		DiagnosisInClassTestDto dto = new DiagnosisInClassTestDto();
		BeanUtils.copyProperties(model,dto);
		PageInfo<DiagnosisInClassTestDto> pageInfo = diagnosisInClassTestService.selectDiagnosisInClassTestPage(dto);
		Map<String, Object> map = getMap(pageInfo);

		if (!CollectionUtils.isEmpty(pageInfo.getList())){
			List<String> collect = pageInfo.getList().stream().distinct().map(DiagnosisInClassTestDto::getClassCode).collect(Collectors.toList());
			for (String classCode : collect){
				int studentCount = 0;
				if(StringUtils.isNotBlank(classCode)){
					DiagnosisTeacherVo vo = new DiagnosisTeacherVo();
					List<AuthUserBean> userBeanList = getUserBeanList(Integer.parseInt(classCode));
					if (!CollectionUtils.isEmpty(userBeanList)){
						studentCount =userBeanList.size();
					}
					vo.setClassCode(classCode);
					vo.setStudentCount(studentCount);
					voList.add(vo);
				}
			}
		}
		map.put("number",voList);
		return  map;
	}

	@Override
	public Map<String, Object> unlockClassTest(DiagnosisStudentAnswerSheetModel model) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("soutList",getSize(model));
		List<DiagnosisInClassRelationDto> lists = getUnLockList(model.getInClassTestCode(),ConstantEnum.HAS_ENDED.getType());
		if (!CollectionUtils.isEmpty(lists)){
			map.put("sout",lists.get(0).getSout());
			return map;
		}
		List<DiagnosisInClassRelationDto> list = getUnLockList(model.getInClassTestCode(),ConstantEnum.START.getType());
		if (CollectionUtils.isEmpty(list)){
			map.put("sout", 0);
			return map;
		}else if (list.size() == 1){
			map.put("sout", list.get(0).getSout());
			return map;
		}else {
			list.sort((o1, o2) -> Integer.parseInt(o1.getSout())-Integer.parseInt(o2.getSout()));

			map.put("sout", list.get(0).getSout());
			return  map;
		}
	}
	/** 课中测添加**/
	@Override
	public String classTestSave(DiagnosisInClassTestModel model) throws Exception {
		DiagnosisInClassTestDto dto = new DiagnosisInClassTestDto();
		BeanUtils.copyProperties(model,dto);
		String uuid = getUUID();
		dto.setInClassTestCode(uuid);
		dto.setStatus(ConstantEnum.NOT_STARTED.getType());
		dto.setQuestionCount(model.getList().size());
		dto.setIsRead(ConstantEnum.START.getType());
		boolean b = diagnosisInClassTestService.saveDiagnosisInClassTest(dto) > 0;
		if (b){
			List<DiagnosisInClassRelationDto> result = PageHelperUtil.converterList(model.getList(), DiagnosisInClassRelationDto.class);
            for (DiagnosisInClassRelationDto bean : result) {
				String sout = bean.getSout();
				String baseCode = bean.getBaseCode();
				bean.setInClassRelationCode(getUUID());
				bean.setInClassTestCode(uuid);
				bean.setIsEnd(ConstantEnum.NOT_STARTED.getType());
				DiagnosisBaseQuestionDto dto1 = diagnosisBaseQuestionService.selectDiagnosisBaseQuestionDto(bean.getBaseCode());
				if (null != dto1){
					BeanUtils.copyProperties(dto1,bean);
				}
				bean.setBaseCode(baseCode);
				bean.setSout(sout);

			}
			diagnosisInClassRelationService.batchSaveDiagnosisInClassRelation(result);
		}
		return b ? uuid : null;
	}
	/**
	 *  课中测试题的调整和删除
	 */
	@Override
	public boolean classTestQuestionAdjustment(DiagnosisInClassTestModel model) throws Exception {

		diagnosisInClassRelationService.deleteDiagnosisInClassRelation(model.getInClassTestCode());

		List<DiagnosisInClassRelationDto> result = PageHelperUtil.converterList(model.getList(), DiagnosisInClassRelationDto.class);
		int questionCount = result.size();
		for (DiagnosisInClassRelationDto bean : result) {
			bean.setInClassRelationCode(getUUID());
			bean.setInClassTestCode(model.getInClassTestCode());
			bean.setIsEnd(ConstantEnum.NOT_STARTED.getType());
			String sout = bean.getSout();
			String baseCode = bean.getBaseCode();
			DiagnosisBaseQuestionDto dto1 = diagnosisBaseQuestionService.selectDiagnosisBaseQuestionDto(bean.getBaseCode());
			if (null != dto1){
				BeanUtils.copyProperties(dto1,bean);
			}
			bean.setBaseCode(baseCode);
			bean.setSout(sout);

		}
		boolean b = diagnosisInClassRelationService.batchSaveDiagnosisInClassRelation(result) > 0;
		if (b){
			DiagnosisInClassTestDto dto = new DiagnosisInClassTestDto();
			dto.setInClassTestCode(model.getInClassTestCode());
			dto.setQuestionCount(questionCount);
			 diagnosisInClassTestService.updateDiagnosisInClassTest(dto);
		}

		return  b;
	}
	/** 课中测code查询序号**/
	@Override
	public Map<String, Object> baseQuestionCodeListByClassTestCode(DiagnosisInClassTestModel model) throws Exception {
		DiagnosisInClassRelationDto bean = new DiagnosisInClassRelationDto();
		bean.setInClassTestCode(model.getInClassTestCode());
		List<DiagnosisInClassRelationDto> list = getInClassRelationList(bean);
		List<DiagnosisInClassRelationDto> list1 = new ArrayList<>();
        for (DiagnosisInClassRelationDto beans : list){
			DiagnosisInClassRelationDto bean1 = new DiagnosisInClassRelationDto();
			bean1.setBaseCode(beans.getBaseCode());
			bean1.setInClassTestCode(beans.getInClassTestCode());
			bean1.setSout(beans.getSout());
			list1.add(bean1);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows",list1);
		return map;
	}
    /** 课中测code查询基础卷列表
	 * @param model**/
	@Override
	public Map<String, Object>  baseQuestionListByClassTestCode(DiagnosisInClassRelationModel model) throws Exception {

		DiagnosisInClassRelationDto dto = new DiagnosisInClassRelationDto();
		dto.setInClassTestCode(model.getInClassTestCode());
		dto.setPageNum(model.getPageNum());
		dto.setPageSize(model.getPageSize());
		PageInfo<DiagnosisInClassRelationDto> pageInfo = diagnosisInClassRelationService.selectDiagnosisInClassRelationPage(dto);
		List<DiagnosisBaseQuestionDto> list =PageHelperUtil.converterList(pageInfo.getList(), DiagnosisBaseQuestionDto.class);

		List<QuestionBankModel> countlist = diagnosisInClassRelationService.selectDiagnosisQuestionBankCount(model.getInClassTestCode());
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(model.getSout())){
			list = list.stream().filter(g-> g.getSout().equals(model.getSout())).collect(Collectors.toList());
			map.put("rows",list);
			return map;
		}
		map.put("rows",list);
		map.put("count",countlist);
		return map;
	}
	/** 课中测删除**/
	@Override
	public boolean classTestDelete(DiagnosisInClassTestModel model) throws Exception {
		boolean b = diagnosisInClassTestService.deleteDiagnosisInClassTest(model.getInClassTestCode()) > 0;
		if (b){
			diagnosisInClassRelationService.deleteDiagnosisInClassRelation(model.getInClassTestCode());
		}
		return b;
	}
	/** 课中测修改-开始测试**/
	@Override
	public Map<String, Object> classTestStartTest(DiagnosisInClassTestModel model) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean rankingList = false;
		int studentCount = 0;
		DiagnosisInClassTestDto dto = new DiagnosisInClassTestDto();
		BeanUtils.copyProperties(model,dto);
		if (model.getStatus()!= null && model.getStatus()==ConstantEnum.STARTING.getType()){
            dto.setTestTime(new Date());
		}
		boolean b = diagnosisInClassTestService.updateDiagnosisInClassTest(dto) > 0;
		if (null != model.getStatus() && model.getStatus() == 2){
			List<DiagnosisInClassRelationDto> list = getUnLockList(model.getInClassTestCode(),null);
	        if (!CollectionUtils.isEmpty(list)){
				for (DiagnosisInClassRelationDto dcrd: list){
					dcrd.setIsEnd(ConstantEnum.END.getType());
					diagnosisInClassRelationService.updateDiagnosisInClassRelation(dcrd);
				}
			}
			List<AuthUserBean> userBeanList = getUserBeanList(Integer.parseInt(model.getClassCode()));
			if (!CollectionUtils.isEmpty(userBeanList)){
				studentCount =userBeanList.size();
			}
			 rankingList = getRankingList(model.getInClassTestCode());
		}
		map.put("studentCount",studentCount);
		map.put("result",rankingList);
		return map;
	}
	/** 课中测关系表修改---下一题和结束本题**/
	@Override
	public Map<String, Object> classTestRelationUpdate(DiagnosisInClassRelationModel model) throws Exception {
		boolean b = false;
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> report = new HashMap<String, Object>();
		DiagnosisInClassRelationDto dto = new DiagnosisInClassRelationDto();
		BeanUtils.copyProperties(model,dto);
		List<DiagnosisInClassRelationDto> list = getInClassRelationList(dto);

		if (null != model.getClassCode()){
			if (!CollectionUtils.isEmpty(list)){
				for (DiagnosisInClassRelationDto dcrd: list){
					dcrd.setIsEnd(ConstantEnum.HAS_ENDED.getType());
					b = diagnosisInClassRelationService.updateDiagnosisInClassRelation(dcrd) > 0;
				}
			}
			 report = classTestRelationSingleReport(model.getBaseCode(), model.getInClassTestCode(), model.getClassCode());
			map.put("outcome",true);
			map.put("singleReport",report);
		}else {
			if (!CollectionUtils.isEmpty(list)){
				for (DiagnosisInClassRelationDto dcrd: list){
					dcrd.setIsEnd(ConstantEnum.END.getType());
					b = diagnosisInClassRelationService.updateDiagnosisInClassRelation(dcrd) > 0;
				}
			}
			map.put("outcome",b);
		}
		return map;
	}
	/** 课中测---单题报告**/
	@Override
	public Map<String, Object> classTestRelationSingleReport(String baseCode, String inClassTestCode, Integer classCode) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> isRightString = new ArrayList<String>();
		List<String> isWrongString = new ArrayList<String>();
		List<String> all = new ArrayList<String>();
		DiagnosisInClassRelationDto dtos = new DiagnosisInClassRelationDto();
		dtos.setInClassTestCode(inClassTestCode);
		dtos.setBaseCode(baseCode);
		List<DiagnosisInClassRelationDto> list1 = getInClassRelationList(dtos);
		DiagnosisBaseQuestionDto dto1 = new DiagnosisBaseQuestionDto();
		BeanUtils.copyProperties(list1.get(0),dto1);

		List<AuthUserBean> userBeanList = getUserBeanList(classCode);

		if (!CollectionUtils.isEmpty(userBeanList)){
			all =userBeanList.stream().map(AuthUserBean::getUserName).collect(Collectors.toList());
			DiagnosisStudentAnswerSheetModel  dto = new DiagnosisStudentAnswerSheetModel();
			dto.setInClassTestCode(inClassTestCode);
			dto.setBaseCode(baseCode);
			List<DiagnosisStudentAnswerSheetDto> list = getDtoList(dto);
			if (!CollectionUtils.isEmpty(list)){
				List<DiagnosisStudentAnswerSheetDto>  isRightList  = list.stream().filter(g -> g.getIsRight() == ConstantEnum.RIGHT.getType()).collect(Collectors.toList());
				List<DiagnosisStudentAnswerSheetDto>  isWrongList  = list.stream().filter(g -> g.getIsRight()==ConstantEnum.WRONG.getType()).collect(Collectors.toList());
				isRightString = isRightList.stream().map(DiagnosisStudentAnswerSheetDto::getStudentName).collect(Collectors.toList());
				isWrongString = isWrongList.stream().map(DiagnosisStudentAnswerSheetDto::getStudentName).collect(Collectors.toList());
				List<String> alls =list.stream().map(DiagnosisStudentAnswerSheetDto::getStudentName).collect(Collectors.toList());
				all.removeAll(alls);

			}
		}
		map.put("studentTotalCount",userBeanList.size());
		map.put("qaseQuestion",dto1);
		map.put("rightCount",isRightString.size());
		map.put("rightList",isRightString);
		map.put("wrongCount",isWrongString.size());
		map.put("wrongList",isWrongString);
		map.put("notanswerList",all);
		return map;
	}
	/*********************************************报告******************************************************/
	/**
	 * 课中测--报告校验
	 * **/
	@Override
	public boolean reportVerification(DiagnosisStudentAnswerSheetModel model) throws Exception {
		List<DiagnosisStudentAnswerSheetDto> list = getDtoList(model);
		return CollectionUtils.isEmpty(list) ? false:true;
	}


	/** 课中测---班级报告总览**/
	@Override
	public Map<String,Object> reportOverview(DiagnosisStudentAnswerSheetModel model) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> knowledgeRanking = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> knowledgeReport  =  new ArrayList<Map<String, Object>>();
		List<DiagnosisStudentAnswerSheetDto> list = getDtoList(model);
		if (!CollectionUtils.isEmpty(list)) {
			map.put("overview", getOverview(list));
			map.put("soutList", getSize(model));

			DiagnosisStudentAnswerRankingDto dto = new DiagnosisStudentAnswerRankingDto();
			dto.setInClassTestCode(model.getInClassTestCode());
			List<DiagnosisStudentAnswerRankingDto> lists = diagnosisStudentAnswerRankingService.selectDiagnosisStudentAnswerRankingList(dto);
			List<DiagnosisStudentAnswerRankingDto> collect = lists.stream().limit(10).collect(Collectors.toList());
			map.put("ranking", collect);
			List<StudentAnswerSheetModel> recombineList = getRecombineList(list);
			if (!CollectionUtils.isEmpty(recombineList)) {
				knowledgeRanking = getKnowledgeCout(recombineList);
				knowledgeReport = getKnowledgeReport(recombineList);
			}
			map.put("knowledgeRanking", knowledgeRanking);
			map.put("knowledgeReport", knowledgeReport);
		}
		return map;
	}
    @Override
    public Map<String, Object> studentList(DiagnosisStudentAnswerMachineModel model) throws Exception {
		DiagnosisStudentAnswerMachineDto dto = new DiagnosisStudentAnswerMachineDto();
		BeanUtils.copyProperties(model,dto);
		PageInfo<DiagnosisStudentAnswerMachineDto> pageInfo = diagnosisStudentAnswerMachineService.selectDiagnosisStudentAnswerMachinePage(dto);
		return getMap(pageInfo);
    }
	/**
	 *  答题绑定用户新增
	 */
	@Override
	public boolean studentSave(DiagnosisStudentAnswerMachineModel model)throws Exception {
		boolean b = false;
        DiagnosisStudentAnswerMachineDto dto1 = new DiagnosisStudentAnswerMachineDto();
        dto1.setStudentCode(model.getStudentCode());
        List<DiagnosisStudentAnswerMachineDto> list = diagnosisStudentAnswerMachineService.selectDiagnosisStudentAnswerMachineList(dto1);
        if (!CollectionUtils.isEmpty(list)){
			DiagnosisStudentAnswerMachineDto diagnosisStudentAnswerMachineDto = list.get(0);
			diagnosisStudentAnswerMachineDto.setMachineCode(model.getMachineCode());
			b = diagnosisStudentAnswerMachineService.updateDiagnosisStudentAnswerMachine(diagnosisStudentAnswerMachineDto) > 0;
		}else {
			DiagnosisStudentAnswerMachineDto dto = new DiagnosisStudentAnswerMachineDto();
			BeanUtils.copyProperties(model,dto);
			dto.setStudentAnswerMachineCode(getUUID());
			b = diagnosisStudentAnswerMachineService.saveDiagnosisStudentAnswerMachine(dto) > 0;

		}
		return b;
	}
	/**
	 *  答题绑定用户删除
	 */
	@Override
	public boolean studentDelete(DiagnosisStudentAnswerMachineModel model) throws Exception {
		return diagnosisStudentAnswerMachineService.deleteDiagnosisStudentAnswerMachine(model.getStudentAnswerMachineCode()) > 0 ;
	}
	/**
	 * 答题绑定用户修改
	 * */
	@Override
	public boolean studentUpdate(DiagnosisStudentAnswerMachineModel model) throws Exception {
		DiagnosisStudentAnswerMachineDto dto = new DiagnosisStudentAnswerMachineDto();
		BeanUtils.copyProperties(model,dto);
		return diagnosisStudentAnswerMachineService.updateDiagnosisStudentAnswerMachine(dto) > 0 ;
	}
	/*
   * 答题器保存
   * */
	@Override
	public boolean collectExercisesAnswer(ExercisesAnswer ea) throws Exception {
		List<DiagnosisStudentAnswerSheetDto> dtoList = new ArrayList<DiagnosisStudentAnswerSheetDto>();
		String inClassTestCode = ea.getTestCode();
		String baseCode = ea.getExercisesCode();
		boolean b = false;
		DiagnosisInClassRelationDto dtos1 = new DiagnosisInClassRelationDto();
		dtos1.setInClassTestCode(inClassTestCode);
		dtos1.setBaseCode(baseCode);
		List<DiagnosisInClassRelationDto> list1 = getInClassRelationList(dtos1);
        if (!CollectionUtils.isEmpty(list1)){
			DiagnosisInClassTestDto diagnosisInClassTestDto = diagnosisInClassTestService.selectDiagnosisInClassTestDto(inClassTestCode);
			if (diagnosisInClassTestDto != null){

				List<AuthUserBean> userBeanList = getUserBeanList(Integer.parseInt(diagnosisInClassTestDto.getClassCode()));

				for (Answer answer : ea.getAnswers()) {
					DiagnosisStudentAnswerSheetDto dto = new DiagnosisStudentAnswerSheetDto();
					dto.setInClassTestCode(inClassTestCode);
					dto.setBaseCode(baseCode);
					dto.setSout(list1.get(0).getSout());
					dto.setRightAnswer(list1.get(0).getKnowledges());
					dto.setRightAnswer(list1.get(0).getRightAnswer());
					dto.setKnowledges(list1.get(0).getKnowledges());
					dto.setStudentAnswerSheetCode(getUUID());
					dto.setIsRight(list1.get(0).getRightAnswer().equals(answer.getAnswer()) ? ConstantEnum.RIGHT.getType() : ConstantEnum.WRONG.getType());
					DiagnosisStudentAnswerMachineDto dsam = new DiagnosisStudentAnswerMachineDto();
					dsam.setMachineCode(answer.getKeyPadCode());
					List<DiagnosisStudentAnswerMachineDto> list = diagnosisStudentAnswerMachineService.selectDiagnosisStudentAnswerMachineList(dsam);
					dto.setStudentAnswer(answer.getAnswer());
					dto.setStudentAnswerSheetCode(getUUID());

					if (CollectionUtils.isEmpty(list) || list.get(0).getStudentCode() ==null ){
						continue;
					}
					if (userBeanList.stream().filter(g -> g.getUserId() ==Integer.parseInt(list.get(0).getStudentCode())).collect(Collectors.toList()).size() == 0){
						continue;
					}
					dto.setStudentCode(list.get(0).getStudentCode());
					dto.setStudentName(list.get(0).getStudentName());
					dtoList.add(dto);
				 }

					b = diagnosisStudentAnswerSheetService.batchSaveDiagnosisStudentAnswerSheet(dtoList) > 0;
				}
			}
		return b;
	}



	public  boolean getRankingList(String inClassTestCode) throws Exception {
		DiagnosisInClassTestDto dtos = new DiagnosisInClassTestDto();
		List<StudentRankingModel> studentRanking = new ArrayList<>();
		dtos.setInClassTestCode(inClassTestCode);
		DiagnosisInClassTestDto diagnosisInClassTestDto = diagnosisInClassTestService.selectDiagnosisInClassTestList(dtos).get(0);
		List<AuthUserBean> userBeanList =getUserBeanList(Integer.parseInt(diagnosisInClassTestDto.getClassCode()));
		if (!CollectionUtils.isEmpty(userBeanList)){
			List<Integer> all = userBeanList.stream().map(AuthUserBean::getUserId).collect(Collectors.toList());

			List<DiagnosisStudentAnswerRankingDto> lists1 = new ArrayList<DiagnosisStudentAnswerRankingDto>();
			DiagnosisStudentAnswerSheetModel models = new DiagnosisStudentAnswerSheetModel();
			models.setInClassTestCode(inClassTestCode);
			List<DiagnosisStudentAnswerSheetDto> list1 = getDtoList(models);
			if (!CollectionUtils.isEmpty(list1)) {
				studentRanking = getStudentRanking(list1);
				List<String> alls = studentRanking.stream().map(StudentRankingModel::getStudentCode).collect(Collectors.toList());
				List<Integer> asll = new ArrayList<Integer>();
				for (String strs : alls) {
					asll.add(Integer.parseInt(strs));
				}
				all.removeAll(asll);
			}
				int a = 0;
				if (!CollectionUtils.isEmpty(list1)) {
					for (int i = 0; i < studentRanking.size(); i++) {
						DiagnosisStudentAnswerRankingDto ranking = new DiagnosisStudentAnswerRankingDto();
						ranking.setStudentCode(studentRanking.get(i).getStudentCode());
						ranking.setStudentName(studentRanking.get(i).getStudentName());
						ranking.setInClassTestCode(inClassTestCode);
						ranking.setRank(i + 1);
						a = i + 1;
						lists1.add(ranking);
					}
				}

				if (!CollectionUtils.isEmpty(all)) {
					for (Integer code : all) {
						DiagnosisStudentAnswerRankingDto rankings = new DiagnosisStudentAnswerRankingDto();
						AuthUserBean bean = userBeanList.stream().filter(g -> g.getUserId() == code).collect(Collectors.toList()).get(0);
						rankings.setStudentCode(String.valueOf(bean.getUserId()));
						rankings.setStudentName(bean.getUserName());
						rankings.setInClassTestCode(inClassTestCode);
						rankings.setRank(a + 1);
						lists1.add(rankings);
						a = a + 1;
					}
			}
			List<DiagnosisStudentAnswerRankingDto> collect = lists1.stream().distinct().collect(Collectors.toList());
			for (DiagnosisStudentAnswerRankingDto bean : collect){
				bean.setStudentAnswerRankingCode(getUUID());
			}
			return diagnosisStudentAnswerRankingService.batchSaveDiagnosisStudentAnswerRanking(collect) > 0;
		}
		return false;
	}


	public  List<Map<String,Integer>> getOverview(List<DiagnosisStudentAnswerSheetDto> list){
		List<Map<String,Integer>>  lists = new ArrayList<>();
		Map<String, List<DiagnosisStudentAnswerSheetDto>> mapCollect = list.stream().collect(Collectors.groupingBy(DiagnosisStudentAnswerSheetDto::getSout));
		for (Map.Entry<String, List<DiagnosisStudentAnswerSheetDto>> entry : mapCollect.entrySet()) {
			Map<String,Integer> map = new HashMap<String,Integer>();
			int wrongCount = entry.getValue().stream().filter(g -> g.getIsRight() == ConstantEnum.WRONG.getType()).collect(Collectors.toList()).size();
			int rightCount = entry.getValue().stream().filter(g -> g.getIsRight() == ConstantEnum.RIGHT.getType()).collect(Collectors.toList()).size();
			map.put("sout",Integer.parseInt(entry.getKey()));
			map.put("rightCount",rightCount);
			map.put("wrongCount",wrongCount);
			lists.add(map);
		}
		return  lists;
	}
	public List<StudentRankingModel> getStudentRanking(List<DiagnosisStudentAnswerSheetDto> list){
		List<StudentRankingModel>  lists = new ArrayList<StudentRankingModel>();
		List<DiagnosisStudentAnswerSheetDto>  isRightList  = list.stream().filter(g -> g.getIsRight() == ConstantEnum.RIGHT.getType()).collect(Collectors.toList());
		Map<String, List<DiagnosisStudentAnswerSheetDto>>  mapCollect= list.stream().collect(Collectors.groupingBy(DiagnosisStudentAnswerSheetDto::getStudentCode));
		for (Map.Entry<String, List<DiagnosisStudentAnswerSheetDto>> entry : mapCollect.entrySet()) {
			StudentRankingModel bean = new StudentRankingModel();
			bean.setStudentName(entry.getValue().get(0).getStudentName());
			bean.setStudentCode(entry.getValue().get(0).getStudentCode());
			bean.setNum(entry.getValue().stream().filter(g -> g.getIsRight() == ConstantEnum.RIGHT.getType()).collect(Collectors.toList()).size());
			lists.add(bean);
		}
		lists.sort((o1, o2) -> o2.getNum()-o1.getNum());
		return  lists;
	}


/********************************华丽丽分割线-公共************************************/
	/** classCode获取班级下的学生 **/
	private List<AuthUserBean> getUserBeanList(Integer classCode) {
		List<AuthUserBean> userBeanList = userManagerService.getMyStudentByClassId(classCode);
		return  userBeanList;
	}

	public static String  getUUID(){
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}

	private  Map<String, Object> getMap(PageInfo  pageInfo){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows",pageInfo.getList());
		map.put("total",pageInfo.getTotal());
		return map;
	}
	public List<String> getSize(DiagnosisStudentAnswerSheetModel model) throws Exception {
		DiagnosisInClassRelationDto bean = new DiagnosisInClassRelationDto();
		bean.setInClassTestCode(model.getInClassTestCode());
		List<DiagnosisInClassRelationDto> list = getInClassRelationList(bean);
		List<String> collect = list.stream().map(DiagnosisInClassRelationDto::getSout).collect(Collectors.toList());
		collect.sort((o1, o2) -> Integer.parseInt(o1)-Integer.parseInt(o2));
		return collect;
	}

	public   List<DiagnosisStudentAnswerSheetDto> getDtoList(DiagnosisStudentAnswerSheetModel model) throws Exception {
		DiagnosisStudentAnswerSheetDto dto = new DiagnosisStudentAnswerSheetDto();
		BeanUtils.copyProperties(model,dto);
		List<DiagnosisStudentAnswerSheetDto> bean = diagnosisStudentAnswerSheetService.selectDiagnosisStudentAnswerSheetList(dto);
		return bean;
	}
	//重组list
	public   List<StudentAnswerSheetModel> getRecombineList(List<DiagnosisStudentAnswerSheetDto> beans) throws Exception {
		List<StudentAnswerSheetModel> modelsList = new ArrayList<StudentAnswerSheetModel>();
		for (DiagnosisStudentAnswerSheetDto bean : beans) {
			List<KnowledgeDateModel> modelList = JSONObject.parseArray(bean.getKnowledges(), KnowledgeDateModel.class);
			if (!CollectionUtils.isEmpty(modelList)) {
				for (KnowledgeDateModel knowledgeModel : modelList) {
					StudentAnswerSheetModel studentAnswerSheetModel = new StudentAnswerSheetModel();
					BeanUtils.copyProperties(bean, studentAnswerSheetModel);
					studentAnswerSheetModel.setKnowledgeCode(knowledgeModel.getId());
					studentAnswerSheetModel.setKnowledgeName(knowledgeModel.getName());
					modelsList.add(studentAnswerSheetModel);
				}
			}
		}
		return modelsList;
	}
	public List<Map<String, Object>> getKnowledgeCout(List<StudentAnswerSheetModel> modelsList) {
		List<Map<String,Object>>  lists = new ArrayList<>();
		Map<String, List<StudentAnswerSheetModel>> mapCollect = modelsList.stream().collect(Collectors.groupingBy(StudentAnswerSheetModel::getKnowledgeCode));
		System.out.println("-------------dgfsdf---------------------------------" + JSONObject.toJSONString(mapCollect));

		for (Map.Entry<String, List<StudentAnswerSheetModel>> entry : mapCollect.entrySet()) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("knowledgeCode",entry.getKey());
			map.put("knowledgeName",entry.getValue().get(0).getKnowledgeName());
			List<String> collect = entry.getValue().stream().map(StudentAnswerSheetModel::getSout).collect(Collectors.toList());
			map.put("soutCount",collect.stream().distinct().collect(Collectors.toList()).size());
			lists.add(map);
		}
		return  lists;
	}


	public List<Map<String, Object>> getKnowledgeReport(List<StudentAnswerSheetModel> modelsList) {
		List<Map<String,Object>>  lists = new ArrayList<>();

	    Map<String, List<StudentAnswerSheetModel>> mapCollect = modelsList.stream().collect(Collectors.groupingBy(StudentAnswerSheetModel::getKnowledgeCode));
			for (Map.Entry<String, List<StudentAnswerSheetModel>> entry : mapCollect.entrySet()) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("knowledgeCode",entry.getKey());
				map.put("knowledgeName",entry.getValue().get(0).getKnowledgeName());
				int size1 = entry.getValue().stream().filter(g -> g.getIsRight() == ConstantEnum.RIGHT.getType() ).collect(Collectors.toList()).size();
				int size2 = entry.getValue().stream().collect(Collectors.toList()).size();
				double v = new BigDecimal(size1).divide(new BigDecimal(size2), 5, BigDecimal.ROUND_HALF_UP).doubleValue() * 100;
				map.put("rightRatio",v);
				lists.add(map);
			}
	      	return  lists;
	}
	public DiagnosisQuestionBankDto getQuestionBankDto(String questionBookCode) throws Exception {
		DiagnosisQuestionBankDto bean = diagnosisQuestionBankService.selectDiagnosisQuestionBankDto(questionBookCode);
		return  bean;
	}


	private   List<DiagnosisInClassRelationDto> getUnLockList(String  InClassTestCode, Integer flag) throws Exception {
		DiagnosisInClassRelationDto dto1 = new DiagnosisInClassRelationDto();
		dto1.setInClassTestCode(InClassTestCode);
		dto1.setIsEnd(flag);
		List<DiagnosisInClassRelationDto> lists = diagnosisInClassRelationService.selectDiagnosisInClassRelationList(dto1);
		return   lists;
	}
	private   List<DiagnosisInClassRelationDto> getInClassRelationList(DiagnosisInClassRelationDto  model) throws Exception {
		List<DiagnosisInClassRelationDto> list1 = diagnosisInClassRelationService.selectDiagnosisInClassRelationList(model);
		return   list1;
	}


}
