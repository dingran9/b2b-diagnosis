package com.eedu.diagnosis.inclass.test.service.impl;

import com.eedu.diagnosis.common.utils.PageHelperUtil;
import com.eedu.diagnosis.inclass.test.api.dto.DiagnosisStudentAnswerRankingDto;
import com.eedu.diagnosis.inclass.test.api.service.DiagnosisStudentAnswerRankingService;
import com.eedu.diagnosis.inclass.test.persist.dao.DiagnosisStudentAnswerRankingDao;
import com.eedu.diagnosis.inclass.test.persist.po.DiagnosisStudentAnswerRanking;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DiagnosisStudentAnswerRankingServiceImpl implements DiagnosisStudentAnswerRankingService {

	@Autowired
	private DiagnosisStudentAnswerRankingDao DiagnosisStudentAnswerRankingDao;

	/**保存课中测答题排名**/
	@Override
	public int saveDiagnosisStudentAnswerRanking(DiagnosisStudentAnswerRankingDto dto) throws Exception {
		DiagnosisStudentAnswerRanking po = new DiagnosisStudentAnswerRanking();
		BeanUtils.copyProperties(dto,po);
		return DiagnosisStudentAnswerRankingDao.insertSelective(po);
	}
	/**批量课中测答题排名**/
	@Override
	public int batchSaveDiagnosisStudentAnswerRanking(List<DiagnosisStudentAnswerRankingDto> dtoList) throws Exception {
		List<DiagnosisStudentAnswerRanking> list = PageHelperUtil.converterList(dtoList, DiagnosisStudentAnswerRanking.class);
		Map<String,Object> map = new HashMap();
		map.put("list",list);
		return DiagnosisStudentAnswerRankingDao.batchSaveDiagnosisStudentAnswerRanking(map);
	}
	/**课中测答题排名Page**/
	@Override
	public PageInfo<DiagnosisStudentAnswerRankingDto> selectDiagnosisStudentAnswerRankingPage(DiagnosisStudentAnswerRankingDto dto) throws Exception {
		DiagnosisStudentAnswerRanking po = new DiagnosisStudentAnswerRanking();
		BeanUtils.copyProperties(dto,po);
		if (dto.getPageNum() != null && dto.getPageSize() != null) {
			PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
		}
		List<DiagnosisStudentAnswerRanking> list = DiagnosisStudentAnswerRankingDao.selectByCondtionList(po);
		PageInfo<DiagnosisStudentAnswerRanking> pageInfo = new PageInfo<DiagnosisStudentAnswerRanking>(list);
		PageInfo<DiagnosisStudentAnswerRankingDto> info = PageHelperUtil.pageInfoConverter(pageInfo, DiagnosisStudentAnswerRankingDto.class);
		return info;
	}
	/**课中测答题排名list**/
	@Override
	public List<DiagnosisStudentAnswerRankingDto> selectDiagnosisStudentAnswerRankingList(DiagnosisStudentAnswerRankingDto dto) throws Exception {
		DiagnosisStudentAnswerRanking po = new DiagnosisStudentAnswerRanking();
		BeanUtils.copyProperties(dto,po);
		List<DiagnosisStudentAnswerRanking> list = DiagnosisStudentAnswerRankingDao.selectByCondtionList(po);
		return PageHelperUtil.converterList(list,DiagnosisStudentAnswerRankingDto.class);
	}
	/**课中测答题排名修改**/
	@Override
	public int updateDiagnosisStudentAnswerRanking(DiagnosisStudentAnswerRankingDto dto) throws Exception {
		DiagnosisStudentAnswerRanking po = new DiagnosisStudentAnswerRanking();
		BeanUtils.copyProperties(dto,po);
		return  DiagnosisStudentAnswerRankingDao.updateByPrimaryKey(po);
	}
	/**课中测答题排名删除**/
	@Override
	public int deleteDiagnosisStudentAnswerRanking(DiagnosisStudentAnswerRankingDto dto) throws Exception {
		return  DiagnosisStudentAnswerRankingDao.deleteByPrimaryKey(dto.getStudentAnswerRankingCode());
	}
	/**课中测答题排名**/
	@Override
	public DiagnosisStudentAnswerRankingDto selectDiagnosisStudentAnswerRankingDto(String studentAnswerSheetCode) throws Exception {
		DiagnosisStudentAnswerRankingDto dto = new DiagnosisStudentAnswerRankingDto();
		DiagnosisStudentAnswerRanking po = DiagnosisStudentAnswerRankingDao.selectByPrimaryKey(studentAnswerSheetCode);
		BeanUtils.copyProperties(po,dto);
		return dto;
	}

}
