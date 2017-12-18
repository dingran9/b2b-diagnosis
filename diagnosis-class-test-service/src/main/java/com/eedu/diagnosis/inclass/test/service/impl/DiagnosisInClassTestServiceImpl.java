package com.eedu.diagnosis.inclass.test.service.impl;

import com.eedu.diagnosis.common.utils.PageHelperUtil;
import com.eedu.diagnosis.inclass.test.api.dto.DiagnosisInClassTestDto;
import com.eedu.diagnosis.inclass.test.api.service.DiagnosisInClassTestService;
import com.eedu.diagnosis.inclass.test.persist.dao.DiagnosisInClassTestDao;
import com.eedu.diagnosis.inclass.test.persist.po.DiagnosisInClassTest;
import com.eedu.diagnosis.inclass.test.persist.util.PublicUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DiagnosisInClassTestServiceImpl implements DiagnosisInClassTestService {

	@Autowired
	private DiagnosisInClassTestDao diagnosisInClassTestDao;

	/**保存课中测**/
	@Override
	public int saveDiagnosisInClassTest(DiagnosisInClassTestDto dto) throws Exception {
		DiagnosisInClassTest po = new DiagnosisInClassTest();
		BeanUtils.copyProperties(dto,po);
		return diagnosisInClassTestDao.insertSelective(po);
	}
	/**课中测Page**/
	@Override
	public PageInfo<DiagnosisInClassTestDto> selectDiagnosisInClassTestPage(DiagnosisInClassTestDto dto) throws Exception {
		DiagnosisInClassTest po = new DiagnosisInClassTest();
		BeanUtils.copyProperties(dto,po);
		if (dto.getPageNum() != null && dto.getPageSize() != null) {
			PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
		}
		List<DiagnosisInClassTest> list = diagnosisInClassTestDao.selectByCondtionList(po);
		PageInfo<DiagnosisInClassTest> pageInfo = new PageInfo<DiagnosisInClassTest>(list);
		PageInfo<DiagnosisInClassTestDto> info = PageHelperUtil.pageInfoConverter(pageInfo, DiagnosisInClassTestDto.class);
		return info;
	}
	/**课中测list**/
	@Override
	public List<DiagnosisInClassTestDto> selectDiagnosisInClassTestList(DiagnosisInClassTestDto dto) throws Exception {
		DiagnosisInClassTest po = new DiagnosisInClassTest();
		BeanUtils.copyProperties(dto,po);
		List<DiagnosisInClassTest> list = diagnosisInClassTestDao.selectByCondtionList(po);
		return PageHelperUtil.converterList(list,DiagnosisInClassTestDto.class);
	}
	/**课中测修改**/
	@Override
	public int updateDiagnosisInClassTest(DiagnosisInClassTestDto dto) throws Exception {
		DiagnosisInClassTest po = new DiagnosisInClassTest();
		BeanUtils.copyProperties(dto,po);
		return  diagnosisInClassTestDao.updateByPrimaryKey(po);
	}
	/**课中测删除**/
	@Override
	public int deleteDiagnosisInClassTest(String inClassTestCode) throws Exception {
		return  diagnosisInClassTestDao.deleteByPrimaryKey(inClassTestCode);
	}
	/**课中测**/
	@Override
	public DiagnosisInClassTestDto selectDiagnosisInClassTestDto(String inClassTestCode) throws Exception {
		DiagnosisInClassTestDto dto = new DiagnosisInClassTestDto();
		DiagnosisInClassTest po = diagnosisInClassTestDao.selectByPrimaryKey(inClassTestCode);
		BeanUtils.copyProperties(po,dto);
		return dto;
	}

}
