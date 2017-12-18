package com.eedu.diagnosis.inclass.test.service.impl;

import com.eedu.diagnosis.common.utils.PageHelperUtil;
import com.eedu.diagnosis.inclass.test.api.dto.DiagnosisStudentAnswerSheetDto;
import com.eedu.diagnosis.inclass.test.api.service.DiagnosisStudentAnswerSheetService;
import com.eedu.diagnosis.inclass.test.persist.dao.DiagnosisStudentAnswerSheetDao;
import com.eedu.diagnosis.inclass.test.persist.po.DiagnosisStudentAnswerSheet;
import com.eedu.diagnosis.inclass.test.persist.util.PublicUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DiagnosisStudentAnswerSheetServiceImpl implements DiagnosisStudentAnswerSheetService {

	@Autowired
	private DiagnosisStudentAnswerSheetDao diagnosisStudentAnswerSheetDao;

	/**保存课中测答题记录**/
	@Override
	public int saveDiagnosisStudentAnswerSheet(DiagnosisStudentAnswerSheetDto dto) throws Exception {
		DiagnosisStudentAnswerSheet po = new DiagnosisStudentAnswerSheet();
		BeanUtils.copyProperties(dto,po);
		return diagnosisStudentAnswerSheetDao.insertSelective(po);
	}
	/**批量课中测答题记录**/
	@Override
	public int batchSaveDiagnosisStudentAnswerSheet(List<DiagnosisStudentAnswerSheetDto> dtoList) throws Exception {
		List<DiagnosisStudentAnswerSheet> list = PageHelperUtil.converterList(dtoList, DiagnosisStudentAnswerSheet.class);
//		if (!CollectionUtils.isEmpty(list)){
//			for (DiagnosisStudentAnswerSheet model : list) {
//				model.setStudentAnswerSheetCode(PublicUtil.getUUID());
//			}
//		}
		Map<String,Object> map = new HashMap();
		map.put("list",list);
		return diagnosisStudentAnswerSheetDao.batchSaveDiagnosisStudentAnswerSheet(map);
	}
	/**课中测答题记录Page**/
	@Override
	public PageInfo<DiagnosisStudentAnswerSheetDto> selectDiagnosisStudentAnswerSheetPage(DiagnosisStudentAnswerSheetDto dto) throws Exception {
		DiagnosisStudentAnswerSheet po = new DiagnosisStudentAnswerSheet();
		BeanUtils.copyProperties(dto,po);
		if (dto.getPageNum() != null && dto.getPageSize() != null) {
			PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
		}
		List<DiagnosisStudentAnswerSheet> list = diagnosisStudentAnswerSheetDao.selectByCondtionList(po);
		PageInfo<DiagnosisStudentAnswerSheet> pageInfo = new PageInfo<DiagnosisStudentAnswerSheet>(list);
		PageInfo<DiagnosisStudentAnswerSheetDto> info = PageHelperUtil.pageInfoConverter(pageInfo, DiagnosisStudentAnswerSheetDto.class);
		return info;
	}
	/**课中测答题记录list**/
	@Override
	public List<DiagnosisStudentAnswerSheetDto> selectDiagnosisStudentAnswerSheetList(DiagnosisStudentAnswerSheetDto dto) throws Exception {
		DiagnosisStudentAnswerSheet po = new DiagnosisStudentAnswerSheet();
		BeanUtils.copyProperties(dto,po);
		List<DiagnosisStudentAnswerSheet> list = diagnosisStudentAnswerSheetDao.selectByCondtionList(po);
		return PageHelperUtil.converterList(list,DiagnosisStudentAnswerSheetDto.class);
	}
	/**课中测答题记录修改**/
	@Override
	public int updateDiagnosisStudentAnswerSheet(DiagnosisStudentAnswerSheetDto dto) throws Exception {
		DiagnosisStudentAnswerSheet po = new DiagnosisStudentAnswerSheet();
		BeanUtils.copyProperties(dto,po);
		return  diagnosisStudentAnswerSheetDao.updateByPrimaryKey(po);
	}
	/**课中测答题记录删除**/
	@Override
	public int deleteDiagnosisStudentAnswerSheet(DiagnosisStudentAnswerSheetDto dto) throws Exception {
		return  diagnosisStudentAnswerSheetDao.deleteByPrimaryKey(dto.getBaseCode());
	}
	/**课中测答题记录**/
	@Override
	public DiagnosisStudentAnswerSheetDto selectDiagnosisStudentAnswerSheetDto(String studentAnswerSheetCode) throws Exception {
		DiagnosisStudentAnswerSheetDto dto = new DiagnosisStudentAnswerSheetDto();
		DiagnosisStudentAnswerSheet po = diagnosisStudentAnswerSheetDao.selectByPrimaryKey(studentAnswerSheetCode);
		BeanUtils.copyProperties(po,dto);
		return dto;
	}

}
