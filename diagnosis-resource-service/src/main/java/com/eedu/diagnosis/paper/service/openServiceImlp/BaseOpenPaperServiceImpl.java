package com.eedu.diagnosis.paper.service.openServiceImlp;

import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.common.enumration.AppUpdateEnum;
import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.common.utils.PageHelperUtil;
import com.eedu.diagnosis.paper.api.dto.*;
import com.eedu.diagnosis.paper.api.openService.BasePaperOpenService;
import com.eedu.diagnosis.paper.api.openService.ResourceOpenService;
import com.eedu.diagnosis.paper.persist.po.*;
import com.eedu.diagnosis.paper.persist.util.EmojiUtil;
import com.eedu.diagnosis.paper.persist.util.SMSUtil;
import com.eedu.diagnosis.paper.service.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.util.*;


@Service
public class BaseOpenPaperServiceImpl implements BasePaperOpenService{
	
	@Autowired
	private DiagnosisPaperService diagnosisPaperServiceImpl; //单科
	@Autowired
	private DiagnosisPaperSchoolRelationService diagnosisPaperSchoolRelationServiceImpl; //单科
	@Autowired
	private DiagnosisComplexPaperService diagnosisComplexPaperServiceImpl;//全科
	@Autowired
	private DiagnosisComplexPaperRelationService  diagnosisComplexPaperRelationServiceImpl;//单科-全科关系
	@Autowired
	private DiagnosisBigQuestionService diagnosisBigQuestionServiceImpl; //大题
	@Autowired
	private DiagnosisSmallQuestionService diagnosisSmallQuestionServiceImpl ;//小题
	@Autowired
	private DiagnosisQuestionRelationService diagnosisQuestionRelationServiceImpl;//答题和小题关系
	@Autowired
	private DiagnosisFavoriteService  diagnosisFavoriteServiceImpl;//收藏
	@Autowired
	private DiagnosisBasePaperService  diagnosisBasePaperServiceImpl;//自组卷
	@Autowired
	private DiagnosisPaperQuestionService diagnosisPaperQuestionServiceImpl;//自组卷与大题
	@Autowired
	private ResourceOpenService resourceOpenService;
	@Autowired
	private DiagnosisFeedbackService diagnosisFeedbackServiceImpl;
	@Autowired
    private SMSUtil smsUtil ;
	@Autowired
	private  DiagnosisAppUpdateService diagnosisAppUpdateServiceImpl;//诊断app

	
	
	
	/**
	 *  保存反馈
	 */
	@Transactional
	@Override
	public String saveDiagnosisFeedback(DiagnosisFeedbackDto dfDto) throws Exception {
		if (null == dfDto) {
			return "";
		}
		DiagnosisFeedback dfPo = new DiagnosisFeedback();
		BeanUtils.copyProperties(dfDto,dfPo);
		String uuid = getUUID();
		dfPo.setCode(uuid);
		dfPo.setCreateTime(new Date());
        String emoji = EmojiUtil.emojiConvert1(dfPo.getFeedbackContent());
        dfPo.setFeedbackContent(emoji);
		int save = diagnosisFeedbackServiceImpl.save(dfPo);
		return  save != 0 ? uuid : null;
	}
	/**
	 *  保存单科诊断卷
	 */
	@Transactional
	@Override
	public String saveDiagnosisPaper(DiagnosisPaperDto dpDto) throws Exception {
		if (null == dpDto) {
			return "";
		}
		DiagnosisPaper dpPo = new DiagnosisPaper();
		BeanUtils.copyProperties(dpDto,dpPo);
		if (dpPo!=null && dpPo.getCode()!=null) {
			dpPo.setUpdateTime(new Date());	
			int update  = diagnosisPaperServiceImpl.update(dpPo);
			return  update != 0 ? "success" : null;
		}
		String uuid = getUUID();
		dpPo.setCode(uuid);
		dpPo.setCreateTime(new Date());
		dpPo.setUpdateTime(new Date());
//		resourceOpenService.getResourcePaperInfo(dpDto.getResourcePaperCode());
		int save = diagnosisPaperServiceImpl.save(dpPo);
		return  save != 0 ? uuid : null;
	}
	/**
	 *  保存单科诊断卷与学校的关系
	 */
	@Transactional
	@Override
	public String saveBatchDiagnosisPaperSchoolRelation(List<DiagnosisPaperSchoolRelationDto> dpsrDtoList) throws Exception {
		if (null == dpsrDtoList || dpsrDtoList.size() == 0 ) {
			return null;
		}
		List<DiagnosisPaperSchoolRelation> list = PageHelperUtil.converterList(dpsrDtoList, DiagnosisPaperSchoolRelation.class);
		if (null == list || list.size() == 0) {
			return null;
		}
		for (DiagnosisPaperSchoolRelation relation : list) {
			relation.setCode(getUUID());
			relation.setCreateTime(new Date());
			relation.setUpdateTime(new Date());
		}
		diagnosisPaperSchoolRelationServiceImpl.saveList(list);
		return "success";
	}
	/**
	 *  保存全科诊断卷
	 */
	@Transactional
	@Override
	public String saveDiagnosisComplexPaper(DiagnosisComplexPaperDto dcpDto)throws Exception {
		if (null == dcpDto) {
			return null;
		}
		List<DiagnosisPaper> list = PageHelperUtil.converterList(dcpDto.getDiagnosisPaperDtoList(), DiagnosisPaper.class);
		if (null == list || list.size() == 0) {
			return null;
		}
		DiagnosisComplexPaper dcpPo = new DiagnosisComplexPaper();
		BeanUtils.copyProperties(dcpDto,dcpPo);
		String uuid = getUUID();
		dcpPo.setCode(uuid);
		dcpPo.setCreateTime(new Date());
		dcpPo.setUpdateTime(new Date());
		dcpPo.setIsDel(0);
		dcpPo.setIsRelease(0);
		int save = diagnosisComplexPaperServiceImpl.save(dcpPo);
		List<DiagnosisComplexPaperRelation> obj = new ArrayList<DiagnosisComplexPaperRelation>();
		for (DiagnosisPaper dp : list) {
			DiagnosisComplexPaperRelation relation = new DiagnosisComplexPaperRelation();
			relation.setCode(getUUID());
			relation.setComplexPaperCode(uuid);
			relation.setDiagnosisPaperCode(dp.getCode());
			relation.setDiagnosisPaperName(dp.getDiagnosisPaperName());
			relation.setSubjectCode(dp.getSubjectCode());
			relation.setArtsType(dp.getArtsType());
			relation.setTotalScore(dp.getTotalScore());
			obj.add(relation);
		}
		diagnosisComplexPaperRelationServiceImpl.saveList(obj);
		return  save != 0 ? uuid : null;
	}
	/**
	 *  修改全科诊断卷及其关系
	 */
	@Override
	public String updateDiagnosisComplexPaper(DiagnosisComplexPaperDto dcpDto)throws Exception {
		if (null == dcpDto) {
			return null;
		}
		DiagnosisComplexPaper dcpPo = new DiagnosisComplexPaper();
		BeanUtils.copyProperties(dcpDto,dcpPo);
		int update = 0;
		if (null != dcpPo && dcpPo.getCode() != null ) {
			dcpPo.setUpdateTime(new Date());
			update = diagnosisComplexPaperServiceImpl.update(dcpPo);
			if (update != 0 && !CollectionUtils.isEmpty(dcpDto.getDiagnosisComplexPaperRelationDtoList()) ) {
				List<DiagnosisComplexPaperRelation> list = PageHelperUtil.converterList(dcpDto.getDiagnosisComplexPaperRelationDtoList(), DiagnosisComplexPaperRelation.class);
				diagnosisComplexPaperRelationServiceImpl.updateList(list);
			}
			return update !=0 ? String.valueOf(update) : null;
		}
		return null;
	}
	
	
	
	/**
	 *  保存大题
	 */
	@Transactional
	@Override
	public List<String> saveDiagnosisBigQuestion(List<DiagnosisBigQuestionDto> dbqDtoList) throws Exception {
		if (null == dbqDtoList || dbqDtoList.size() == 0 ) {
			return null;
		}
		List<DiagnosisBigQuestion> list =  PageHelperUtil.converterList(dbqDtoList, DiagnosisBigQuestion.class);
		diagnosisBigQuestionServiceImpl.saveList(list);
		//拼接小题的
		List<String>  stringList = new ArrayList<String>();
		List<DiagnosisQuestionRelation> obj = new ArrayList<DiagnosisQuestionRelation>();
		for (DiagnosisBigQuestionDto dbq : dbqDtoList) {
			stringList.add(dbq.getQuestionCode());
			if (null != dbq.getDiagnosisSmallQuestionDtosList() && dbq.getDiagnosisSmallQuestionDtosList().size() != 0 ) {
				for (DiagnosisSmallQuestionDto dsq : dbq.getDiagnosisSmallQuestionDtosList()) {
					DiagnosisQuestionRelation relation = new DiagnosisQuestionRelation();
					relation.setBigQuestionCode(dbq.getQuestionCode());
					relation.setSmallQuestionCode(dsq.getCode());
					relation.setSort(dsq.getSort());
					relation.setCode(getUUID());
					obj.add(relation);
				}
			}
		}
		diagnosisQuestionRelationServiceImpl.saveList(obj);
		return stringList;
	}
	/**
	 *  保存小题
	 */
	@Transactional
	@Override
	public List<String> saveDiagnosisSmallQuestion(List<DiagnosisSmallQuestionDto> dsqDtoList)throws Exception {
		if (null == dsqDtoList || dsqDtoList.size() == 0 ) {
			return null;
		}
		List<String>  stringList = new ArrayList<String>();
		List<DiagnosisSmallQuestion> list =  PageHelperUtil.converterList(dsqDtoList, DiagnosisSmallQuestion.class);
		for (DiagnosisSmallQuestion dsq : list) {
			String uuid = getUUID();
			dsq.setCode(uuid);
			dsq.setCreateTime(new Date());
			dsq.setUpdateTime(new Date());
			stringList.add(uuid);
		}
		diagnosisSmallQuestionServiceImpl.saveList(list);
		return stringList;
	}
	/**
	 *  保存自组卷和自组卷与大题关系
	 */
	@Transactional
	@Override
	public String saveDiagnosisBasePaper(DiagnosisBasePaperDto dbpDto) throws Exception {
		if (null == dbpDto) {
			return null;
		}
		DiagnosisBasePaper dbpPo = new DiagnosisBasePaper();
		BeanUtils.copyProperties(dbpDto,dbpPo);
		String uuid = getUUID();
		dbpPo.setCode(uuid);
		dbpPo.setCreateTime(new Date());
		dbpPo.setUpdateTime(new Date());
		diagnosisBasePaperServiceImpl.save(dbpPo);
		//拼接大题的
		List<DiagnosisPaperQuestion> obj = new ArrayList<DiagnosisPaperQuestion>();
		for (DiagnosisBigQuestionDto dbq : dbpDto.getDiagnosisBigQuestionDtosList()) {
				DiagnosisPaperQuestion relation = new DiagnosisPaperQuestion();
				relation.setBigQuestionCode(dbq.getQuestionCode());
				relation.setPaperCode(uuid);
				relation.setCode(getUUID());
				obj.add(relation);
		}
		diagnosisPaperQuestionServiceImpl.saveList(obj);
		return uuid;
	}
	/**
	 *  收藏诊断卷
	 */
	@Transactional
	@Override
	public String saveOrUpdateDiagnosisFavoriteService(DiagnosisFavoriteDto dfDto)throws Exception {
		if (null == dfDto) {
			return null;
		}
		DiagnosisFavorite dfPo = new DiagnosisFavorite();
		BeanUtils.copyProperties(dfDto,dfPo);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(dfPo.getQuestionCode())) {
			queryMap.put("questionCode", dfPo.getQuestionCode());
			queryMap.put("userCode", dfPo.getUserCode());
		}
		/*if (StringUtils.isNotBlank(dfPo.getCode())) {
			queryMap.put("code", dfPo.getCode());
		}*/
		Order order = new Order("create_time", Order.Direction.desc);
		List<DiagnosisFavorite> dpList = diagnosisFavoriteServiceImpl.findByCondition(queryMap,order);
		if (dpList != null && dpList.size() != 0 ){
			DiagnosisFavorite favorite = dpList.get(0);
			favorite.setIsDel(favorite.getIsDel() == 0 ? 1 : 0 ); 
			/*if (dfPo.getCode() != null) {
				favorite.setIsDel(1);   	
				}*/
			favorite.setUpdateTime(new Date());
			int update = diagnosisFavoriteServiceImpl.update(favorite);
			return update != 0 ? "success" : null;
		}else {
			String uuid = getUUID();
			dfPo.setCode(uuid);
			dfPo.setCreateTime(new Date());
			dfPo.setUpdateTime(new Date());
			dfPo.setIsDel(0);
			int save = diagnosisFavoriteServiceImpl.save(dfPo);
			return save != 0 ? uuid : null;
		}

		
	}
	/**
	 *  查询收藏诊断卷-分页
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<DiagnosisFavoriteDto> getDiagnosisFavoritePaper(DiagnosisFavoriteDto dfDto,Integer pageNum,Integer pageSize,Order order)throws Exception {
		System.out.println("------------------------------hou---------------------------------");
		DiagnosisFavorite dfPo = new DiagnosisFavorite();
		BeanUtils.copyProperties(dfDto,dfPo);
		dfPo.setIsDel(0);
        Map<String, Object> queryMap = JSONObject.parseObject(JSONObject.toJSONString(dfPo), Map.class);
		 if (pageNum!=null && pageSize!=null) {
			 PageHelper.startPage(pageNum,pageSize);
		 }
        List<DiagnosisFavorite> dpList = diagnosisFavoriteServiceImpl.findByCondition(queryMap,order);
		PageInfo<DiagnosisFavorite> pageInfo = new PageInfo<DiagnosisFavorite>(dpList);
		PageInfo<DiagnosisFavoriteDto> info = PageHelperUtil.pageInfoConverter(pageInfo, DiagnosisFavoriteDto.class);
		 return info;
	}
	/**
	 *  获取单科卷集合
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DiagnosisPaperDto> getDiagnosisPaperList(DiagnosisPaperDto dpDto)throws Exception {
		if (null == dpDto) {
			return null;
		}
		DiagnosisPaper dpPo = new DiagnosisPaper();
		BeanUtils.copyProperties(dpDto,dpPo);
		Map<String, Object> queryMap = JSONObject.parseObject(JSONObject.toJSONString(dpPo), Map.class);
	    List<DiagnosisPaper> dpList = diagnosisPaperServiceImpl.findByCondition(queryMap);
	    List<DiagnosisPaperDto> tos = PageHelperUtil.converterList(dpList, DiagnosisPaperDto.class);
	    return tos;
	}
	/**
	 *  获取单科卷集合
	 */
	@Override
	public List<DiagnosisPaperDto> getDiagnosisPaperList(List<String> list)throws Exception {
		if (list == null || list.size() == 0 ) {
			return null;
		}
	    List<DiagnosisPaper> dpList = diagnosisPaperServiceImpl.listByIds(list);
	    List<DiagnosisPaperDto> tos = PageHelperUtil.converterList(dpList, DiagnosisPaperDto.class);
	    return tos;
	}
	/**
	 *  获取单科诊断卷与学校的关系
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<DiagnosisPaperSchoolRelationDto> getDiagnosisPaperSchoolRelationList(DiagnosisPaperSchoolRelationDto dpsrDto,Integer pageNum,Integer pageSize,Order order)throws Exception {
		DiagnosisPaperSchoolRelation dpsrPo = new DiagnosisPaperSchoolRelation();
		BeanUtils.copyProperties(dpsrDto,dpsrPo);
		Map<String, Object> queryMap = JSONObject.parseObject(JSONObject.toJSONString(dpsrPo), Map.class);
		if (pageNum!=null && pageSize!=null) {
			 PageHelper.startPage(pageNum,pageSize);
		 }				
	    List<DiagnosisPaperSchoolRelation> list = diagnosisPaperSchoolRelationServiceImpl.findByCondition(queryMap,order);
	    PageInfo<DiagnosisPaperSchoolRelation> pageInfo = new PageInfo<DiagnosisPaperSchoolRelation>(list);
	    PageInfo<DiagnosisPaperSchoolRelationDto> tos = PageHelperUtil.pageInfoConverter(pageInfo, DiagnosisPaperSchoolRelationDto.class);
	    return tos;
	}
	/**
	 *  修改单科诊断卷与学校的关系
	 */
	@Transactional
	@Override
	public String updateDiagnosisPaperSchoolRelation(List<DiagnosisPaperSchoolRelationDto> dpsrDtoList) throws Exception {
		if (null == dpsrDtoList || dpsrDtoList.size() == 0 ) {
			return null;
		}
		List<DiagnosisPaperSchoolRelation> list = PageHelperUtil.converterList(dpsrDtoList, DiagnosisPaperSchoolRelation.class);
		if (null == list || list.size() == 0) {
			return null;
		}
		for (DiagnosisPaperSchoolRelation relation : list) {
			relation.setUpdateTime(new Date());
		}
		diagnosisPaperSchoolRelationServiceImpl.updateList(list);
		return "success";
	}
	/**
	 *  获取单科卷-分页
	 */	
	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<DiagnosisPaperDto> getDiagnosisPaperPaper(DiagnosisPaperDto dpDto,Integer pageNum,Integer pageSize,Order order)throws Exception {
		 DiagnosisPaper dpPo = new DiagnosisPaper();
		 BeanUtils.copyProperties(dpDto,dpPo);
		 Map<String, Object> queryMap = JSONObject.parseObject(JSONObject.toJSONString(dpPo), Map.class);
		 if (pageNum!=null && pageSize!=null) {
			 PageHelper.startPage(pageNum,pageSize);
		 }
		 List<DiagnosisPaper> dpList = diagnosisPaperServiceImpl.findByCondition(queryMap,order);
		 PageInfo<DiagnosisPaper> pageInfo = new PageInfo<DiagnosisPaper>(dpList);
		 PageInfo<DiagnosisPaperDto> info = PageHelperUtil.pageInfoConverter(pageInfo, DiagnosisPaperDto.class);
		 return info;
	}
	/**
	 *  获取单科卷关联学校-分页
	 */	
	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<DiagnosisPaperDto> getDiagnosisPaperRelationSchoolByPaper(DiagnosisPaperDto dpDto, Integer pageNum, Integer pageSize,Order order) throws Exception{
		 DiagnosisPaper dpPo = new DiagnosisPaper();
		 BeanUtils.copyProperties(dpDto,dpPo);
		 Map<String, Object> queryMap = JSONObject.parseObject(JSONObject.toJSONString(dpPo), Map.class);
		 if (pageNum!=null && pageSize!=null) {
			 PageHelper.startPage(pageNum,pageSize);
		 }
		 List<DiagnosisPaper> dpList = diagnosisPaperServiceImpl.getDiagnosisPaperRelationSchoolByPaper(queryMap,order);
		 PageInfo<DiagnosisPaper> pageInfo = new PageInfo<DiagnosisPaper>(dpList);
		 PageInfo<DiagnosisPaperDto> info = PageHelperUtil.pageInfoConverter(pageInfo, DiagnosisPaperDto.class);
		 return info;
	}
	/**  
	 * 查询全科诊断卷集合
	 **/
	@SuppressWarnings("unchecked")
	@Override
	public List<DiagnosisComplexPaperDto> getDiagnosisComplexPaperList(DiagnosisComplexPaperDto dcpDto)throws Exception {
		if (null == dcpDto) {
			return null;
		}
		DiagnosisComplexPaper dcpPo = new DiagnosisComplexPaper();
		BeanUtils.copyProperties(dcpDto,dcpPo);
		Map<String, Object> queryMap = JSONObject.parseObject(JSONObject.toJSONString(dcpPo), Map.class);
	    List<DiagnosisComplexPaper> dcpList = diagnosisComplexPaperServiceImpl.findByCondition(queryMap);
	    List<DiagnosisComplexPaperDto> tos =  PageHelperUtil.converterList(dcpList, DiagnosisComplexPaperDto.class);
		return tos;
	}
	/**
	 *  修改单科诊断卷与学校的关系
	 */
	@Transactional
	@Override
	public String updateDiagnosisComplexPaperList(List<DiagnosisComplexPaperDto> dcpDtoList) throws Exception {
		if (null == dcpDtoList || dcpDtoList.size() == 0 ) {
			return null;
		}
		List<DiagnosisComplexPaper> list = PageHelperUtil.converterList(dcpDtoList, DiagnosisComplexPaper.class);
		if (null == list || list.size() == 0) {
			return null;
		}
		for (DiagnosisComplexPaper relation : list) {
			relation.setUpdateTime(new Date());
		}
		diagnosisComplexPaperServiceImpl.updateList(list);
		return "success";
	}

	/**
	 *  获取全科卷-分页
	 */	
	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<DiagnosisComplexPaperDto> getDiagnosisComplexPaperPaper(DiagnosisComplexPaperDto dcpDto,Integer pageNum,Integer pageSize,Order order,Integer flag)throws Exception {
		 DiagnosisComplexPaper dcpPo = new DiagnosisComplexPaper();
		 BeanUtils.copyProperties(dcpDto,dcpPo);
		 Map<String, Object> queryMap = JSONObject.parseObject(JSONObject.toJSONString(dcpPo), Map.class);
		List<DiagnosisComplexPaper> dcpList = new ArrayList<>();
		long total = 0;
		if (null != flag  && flag == 1) {
			PageHelper.startPage(pageNum,pageSize);
			dcpList = diagnosisComplexPaperServiceImpl.getDiagnosisComplexPaperPaperRelationPaper(queryMap, order);
		}else {
			PageHelper.startPage(pageNum,pageSize);
			dcpList = diagnosisComplexPaperServiceImpl.findByCondition(queryMap, order);
		}
		 PageInfo<DiagnosisComplexPaper> pageInfo = new PageInfo<DiagnosisComplexPaper>(dcpList);
		 PageInfo<DiagnosisComplexPaperDto> info = PageHelperUtil.pageInfoConverter(pageInfo, DiagnosisComplexPaperDto.class);
		 return info;
	}
	/**
	 *  获取单科-全科卷关系
	 */	
	@SuppressWarnings("unchecked")
	@Override
	public List<DiagnosisComplexPaperRelationDto> getDiagnosisComplexPaperRelationList(DiagnosisComplexPaperRelationDto dcprDto)throws Exception {
		 if (null == dcprDto) {
			return null;
		 }
		 DiagnosisComplexPaperRelation dcprPo = new DiagnosisComplexPaperRelation();
		 BeanUtils.copyProperties(dcprDto,dcprPo);
		 Map<String, Object> queryMap = JSONObject.parseObject(JSONObject.toJSONString(dcprPo), Map.class);
		 List<DiagnosisComplexPaperRelation> dcpList = diagnosisComplexPaperRelationServiceImpl.findByCondition(queryMap);
		 List<DiagnosisComplexPaperRelationDto> tos =  PageHelperUtil.converterList(dcpList, DiagnosisComplexPaperRelationDto.class);
		 return tos;
	}
	/**
	 *  获取全科卷和全科卷关系列表
	 */	
	@SuppressWarnings("unchecked")
	@Override
	public List<DiagnosisComplexPaperDto> getDiagnosisComplexPaperAndRelationList(DiagnosisComplexPaperDto dcpDto)throws Exception {
		 if (null == dcpDto) {
			return null;
		 }		 
		 DiagnosisComplexPaper dcpPo = new DiagnosisComplexPaper();
		 BeanUtils.copyProperties(dcpDto,dcpPo);
		 Map<String, Object> queryMap = JSONObject.parseObject(JSONObject.toJSONString(dcpPo), Map.class);
		 List<DiagnosisComplexPaper>  list = diagnosisComplexPaperServiceImpl.findByCondition(queryMap);
		 if (CollectionUtils.isEmpty(list)) {
		      return null;
			}
		 List<DiagnosisComplexPaperDto> tos =  PageHelperUtil.converterList(list, DiagnosisComplexPaperDto.class);
		 for (DiagnosisComplexPaperDto complexPaperDto : tos) {
			 Map<String, Object> queryMaps  = new HashMap<String, Object>();
			 queryMaps.put("complexPaperCode", complexPaperDto.getCode());
			 List<DiagnosisComplexPaperRelation> dcpList = diagnosisComplexPaperRelationServiceImpl.findByCondition(queryMaps);
			 List<DiagnosisComplexPaperRelationDto> tosList =  PageHelperUtil.converterList(dcpList, DiagnosisComplexPaperRelationDto.class);
			 complexPaperDto.setDiagnosisComplexPaperRelationDtoList(tosList);
		 }
		 return tos;
	}
	
	
	
	/**
	 *  根据全科code获取单科信息
	 */	
	@Override
	public List<DiagnosisPaperDto> getDiagnosisPaperListByComplexPaperCode(String code)throws Exception {
		 if (null == code || "".equals(code)) {
		    return null;
		 }
		 Map<String, Object> queryMap = new HashMap<String, Object>();
		 queryMap.put("code", code);
		 List<DiagnosisPaper> dpList = diagnosisPaperServiceImpl.getDiagnosisPaperListByComplexPaperCode(queryMap);
		 List<DiagnosisPaperDto> tos =  PageHelperUtil.converterList(dpList, DiagnosisPaperDto.class);
		 return tos;
	}
	/**
	 *  获取大题列表
	 */	
	@SuppressWarnings("unchecked")
	@Override
	public List<DiagnosisBigQuestionDto> getDiagnosisBigQuestionList(DiagnosisBigQuestionDto dbqDto)throws Exception {
		 if (null == dbqDto) {
			return null;
		 }
		 DiagnosisBigQuestion dbqPo = new DiagnosisBigQuestion();
		 BeanUtils.copyProperties(dbqDto,dbqPo);
		 Map<String, Object> queryMap = JSONObject.parseObject(JSONObject.toJSONString(dbqDto), Map.class);
		 List<DiagnosisBigQuestion> dbqList = diagnosisBigQuestionServiceImpl.findByCondition(queryMap);
		 List<DiagnosisBigQuestionDto> tos =  PageHelperUtil.converterList(dbqList, DiagnosisBigQuestionDto.class);
		 return tos;
	}
	/**
	 *  获取小题列表
	 */	
	@SuppressWarnings("unchecked")
	@Override
	public List<DiagnosisSmallQuestionDto> getDiagnosisSmallQuestionList(DiagnosisSmallQuestionDto dsqDto)throws Exception {
		 if (null == dsqDto) {
			return null;
		 }
		 DiagnosisSmallQuestion dsqPo = new DiagnosisSmallQuestion();
		 BeanUtils.copyProperties(dsqDto,dsqPo);
		 Map<String, Object> queryMap = JSONObject.parseObject(JSONObject.toJSONString(dsqPo), Map.class);
		 List<DiagnosisSmallQuestion> list = diagnosisSmallQuestionServiceImpl.findByCondition(queryMap);
		 List<DiagnosisSmallQuestionDto> tos =  PageHelperUtil.converterList(list, DiagnosisSmallQuestionDto.class);
		 return tos;
	}
	/**
	 *  获取大题-小题关系列表
	 */	
	@SuppressWarnings("unchecked")
	@Override
	public List<DiagnosisQuestionRelationDto> getDiagnosisQuestionRelationList(DiagnosisQuestionRelationDto dqrDto)throws Exception {
		 if (null == dqrDto) {
				return null;
			 }
		     DiagnosisQuestionRelation dqrPo = new DiagnosisQuestionRelation();
			 BeanUtils.copyProperties(dqrDto,dqrPo);
			 Map<String, Object> queryMap = JSONObject.parseObject(JSONObject.toJSONString(dqrPo), Map.class);
			 List<DiagnosisQuestionRelation> list = diagnosisQuestionRelationServiceImpl.findByCondition(queryMap);
			 List<DiagnosisQuestionRelationDto> tos =  PageHelperUtil.converterList(list, DiagnosisQuestionRelationDto.class);
			 return tos;
	}
	/**
	 * 查询自组卷列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DiagnosisBasePaperDto> getDiagnosisBasePaperList(DiagnosisBasePaperDto dbpDto)throws Exception {
		 if (null == dbpDto) {
			return null;
		 }
		 DiagnosisBasePaper dbpPo = new DiagnosisBasePaper();
		 BeanUtils.copyProperties(dbpDto,dbpPo);
		 Map<String, Object> queryMap = JSONObject.parseObject(JSONObject.toJSONString(dbpPo), Map.class);
		 List<DiagnosisBasePaper> list = diagnosisBasePaperServiceImpl.findByCondition(queryMap);
		 List<DiagnosisBasePaperDto> tos =  PageHelperUtil.converterList(list, DiagnosisBasePaperDto.class);
		 return tos;
	}

    /**
     * 查看反馈
     */
    @Override
    public List<DiagnosisFeedbackDto> getDiagnosisFeedback(DiagnosisFeedbackDto dfDto) throws Exception {
        if (null == dfDto) {
            return null;
        }
        DiagnosisFeedback dfPo = new DiagnosisFeedback();
        BeanUtils.copyProperties(dfDto,dfPo);
        List<DiagnosisFeedback> all = diagnosisFeedbackServiceImpl.getAll();
        all.forEach(item->{
            try {
                item.setFeedbackContent(EmojiUtil.emojiRecovery2(item.getFeedbackContent()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
        List<DiagnosisFeedbackDto> tos =  PageHelperUtil.converterList(all, DiagnosisFeedbackDto.class);
        return  tos;
    }

	public String  getUUID(){
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
	/**
	 * 发送短信
	 */
	@Override
	public String sendSms(String phone, String smsType) {
		String code= smsUtil.send(phone, smsType);
		return code;
	}

	/**
	 * 获取诊断app
	 */

	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<DiagnosisAppUpdateDto> diagnosisAppUpdateList(DiagnosisAppUpdateDto dauDto,Integer pageNum,Integer pageSize,Order order)throws Exception {
        DiagnosisAppUpdate dauPo = new DiagnosisAppUpdate();
        BeanUtils.copyProperties(dauDto,dauPo);
        if (dauPo !=null &&  dauPo.getAvailable().equals("2") ){
            dauPo.setAvailable(null);
        }
        Map<String, Object> queryMap = JSONObject.parseObject(JSONObject.toJSONString(dauPo), Map.class);
        if (pageNum!=null && pageSize!=null) {
            PageHelper.startPage(pageNum,pageSize);
        }
        List<DiagnosisAppUpdate> list = diagnosisAppUpdateServiceImpl.findByCondition(queryMap,order);
        PageInfo<DiagnosisAppUpdate> pageInfo = new PageInfo<DiagnosisAppUpdate>(list);
        PageInfo<DiagnosisAppUpdateDto> info = PageHelperUtil.pageInfoConverter(pageInfo, DiagnosisAppUpdateDto.class);
        return info;
	}
	@Override
	public String addApkUrl(DiagnosisAppUpdateDto dauDto) throws Exception {
		if (null == dauDto) {
			return null;
		}
		DiagnosisAppUpdate dauPo = new DiagnosisAppUpdate();
		BeanUtils.copyProperties(dauDto,dauPo);

		DiagnosisAppUpdate dauPos = new DiagnosisAppUpdate();
		dauPos.setAvailable(AppUpdateEnum.ABLE.getValue());
		dauPos.setType(dauPo.getType());
		Map<String, Object> queryMap = JSONObject.parseObject(JSONObject.toJSONString(dauPos), Map.class);
		List<DiagnosisAppUpdate> list = diagnosisAppUpdateServiceImpl.findByCondition(queryMap);
		dauPo.setAppVersion("1");
		if (!CollectionUtils.isEmpty(list)){
            DiagnosisAppUpdate diagnosisAppUpdate = list.get(0);
            diagnosisAppUpdate.setAvailable(AppUpdateEnum.NOTABLE.getValue());
            diagnosisAppUpdate.setUpdateTime(new Date());
            diagnosisAppUpdateServiceImpl.update(diagnosisAppUpdate);
			dauPo.setAppVersion(diagnosisAppUpdate.getAppVersion()+1);
		}
        String uuid = getUUID();
        dauPo.setCode(uuid);
        dauPo.setAvailable(AppUpdateEnum.ABLE.getValue());
        dauPo.setCreateTime(new Date());
        dauPo.setUpdateTime(new Date());
        int save = diagnosisAppUpdateServiceImpl.save(dauPo);
        return  save != 0 ? "success" : null;
	}

	/**
	 * 检查APP版本
	 *
	 * @param appType APP类型 IOS Android
	 * @return
	 */
	@Override
	public DiagnosisAppUpdateDto checkAppVersion(String appType,String clientType) {
		DiagnosisAppUpdateDto dto = new DiagnosisAppUpdateDto();
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("appType",appType);
		queryMap.put("type",clientType);
		queryMap.put("available","1");
		List<DiagnosisAppUpdate> byCondition = diagnosisAppUpdateServiceImpl.findByCondition(queryMap);
		if(CollectionUtils.isEmpty(byCondition)){
			return dto;
		}
		BeanUtils.copyProperties(byCondition.get(0),dto);
		return dto;
	}
}
