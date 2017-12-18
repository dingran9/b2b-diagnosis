package com.eedu.diagnosis.inclass.test.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.common.utils.PageHelperUtil;
import com.eedu.diagnosis.inclass.test.api.dto.DiagnosisBaseQuestionDto;
import com.eedu.diagnosis.inclass.test.api.service.DiagnosisBaseQuestionService;
import com.eedu.diagnosis.inclass.test.persist.dao.DiagnosisBaseQuestionDao;
import com.eedu.diagnosis.inclass.test.persist.model.ResultResponse;
import com.eedu.diagnosis.inclass.test.persist.po.DiagnosisBaseQuestion;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DiagnosisBaseQuestionServiceImpl implements DiagnosisBaseQuestionService {


	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private DiagnosisBaseQuestionDao diagnosisBaseQuestionDao;


	/**保存测试题**/
	@Override
	public int saveDiagnosisBaseQuestion(DiagnosisBaseQuestionDto dto) throws Exception {
		DiagnosisBaseQuestion po = new DiagnosisBaseQuestion();
		BeanUtils.copyProperties(dto,po);
		return diagnosisBaseQuestionDao.insertSelective(po);
	}
	/**批量测试题**/
	@Override
	public int batchSaveDiagnosisBaseQuestion(List<DiagnosisBaseQuestionDto> dtoList) throws Exception {
		List<DiagnosisBaseQuestion> list = PageHelperUtil.converterList(dtoList, DiagnosisBaseQuestion.class);
//		if (!CollectionUtils.isEmpty(list)){
//			for (DiagnosisBaseQuestion model : list) {
//				model.setBaseCode(PublicUtil.getUUID());
//			}
//		}
		Map<String,Object> map = new HashMap();
		map.put("list",list);
		return diagnosisBaseQuestionDao.batchSaveDiagnosisBaseQuestion(map);
	}
	/**测试题**/
	@Override
	public DiagnosisBaseQuestionDto selectDiagnosisBaseQuestionDto(String baseCode) throws Exception {
		DiagnosisBaseQuestionDto dto = new DiagnosisBaseQuestionDto();
		DiagnosisBaseQuestion po = diagnosisBaseQuestionDao.selectByPrimaryKey(baseCode);
		if (po != null){
			BeanUtils.copyProperties(po,dto);
		}
		return dto;
	}
	/**测试题Page**/
	@Override
	public PageInfo<DiagnosisBaseQuestionDto> selectDiagnosisBaseQuestionPage(DiagnosisBaseQuestionDto dto) throws Exception {
		DiagnosisBaseQuestion po = new DiagnosisBaseQuestion();
		BeanUtils.copyProperties(dto,po);
		if (dto.getPageNum() != null && dto.getPageSize() != null) {
			PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
		}
		List<DiagnosisBaseQuestion> list = diagnosisBaseQuestionDao.selectByCondtionList(po);
		PageInfo<DiagnosisBaseQuestion> pageInfo = new PageInfo<DiagnosisBaseQuestion>(list);
		PageInfo<DiagnosisBaseQuestionDto> info = PageHelperUtil.pageInfoConverter(pageInfo, DiagnosisBaseQuestionDto.class);
		return info;
	}
	/**测试题list**/
	@Override
	public List<DiagnosisBaseQuestionDto> selectDiagnosisBaseQuestionList(DiagnosisBaseQuestionDto dto) throws Exception {
		DiagnosisBaseQuestion po = new DiagnosisBaseQuestion();
		BeanUtils.copyProperties(dto,po);
		List<DiagnosisBaseQuestion> list = diagnosisBaseQuestionDao.selectByCondtionList(po);
		return PageHelperUtil.converterList(list,DiagnosisBaseQuestionDto.class);
	}
	/**测试题修改**/
	@Override
	public int updateDiagnosisBaseQuestion(DiagnosisBaseQuestionDto dto) throws Exception {
		DiagnosisBaseQuestion po = new DiagnosisBaseQuestion();
		BeanUtils.copyProperties(dto,po);
		return  diagnosisBaseQuestionDao.updateByPrimaryKey(po);
	}
	/**测试题删除**/
	@Override
	public int deleteDiagnosisBaseQuestion(String baseCode) throws Exception {
		return  diagnosisBaseQuestionDao.deleteByPrimaryKey(baseCode);
	}
	/**课中测code获取测试题Page**/
	@Override
	public PageInfo<DiagnosisBaseQuestionDto> selectDiagnosisBaseQuestionByInClassTest(String inClassTestCode,Integer pageNum,Integer pageSize) throws Exception {
		if (pageNum != null && pageSize != null) {
			PageHelper.startPage(pageNum, pageSize);
		}
		List<DiagnosisBaseQuestion> list = diagnosisBaseQuestionDao.selectDiagnosisBaseQuestionByInClassTest(inClassTestCode);
		PageInfo<DiagnosisBaseQuestion> pageInfo = new PageInfo<DiagnosisBaseQuestion>(list);
		PageInfo<DiagnosisBaseQuestionDto> info = PageHelperUtil.pageInfoConverter(pageInfo, DiagnosisBaseQuestionDto.class);
		return info;
	}
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

}
