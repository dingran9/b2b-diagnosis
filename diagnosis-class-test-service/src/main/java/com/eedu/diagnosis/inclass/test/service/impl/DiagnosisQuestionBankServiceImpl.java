package com.eedu.diagnosis.inclass.test.service.impl;

import com.eedu.diagnosis.common.utils.PageHelperUtil;
import com.eedu.diagnosis.inclass.test.api.dto.DiagnosisQuestionBankDto;
import com.eedu.diagnosis.inclass.test.api.service.DiagnosisQuestionBankService;
import com.eedu.diagnosis.inclass.test.persist.dao.DiagnosisQuestionBankDao;
import com.eedu.diagnosis.inclass.test.persist.po.DiagnosisQuestionBank;
import com.eedu.diagnosis.inclass.test.persist.util.PublicUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DiagnosisQuestionBankServiceImpl implements DiagnosisQuestionBankService {

	@Autowired
	private DiagnosisQuestionBankDao diagnosisQuestionBankDao;



	/**保存题库**/
	@Override
	public int saveDiagnosisQuestionBank(DiagnosisQuestionBankDto dto) throws Exception {
		DiagnosisQuestionBank po = new DiagnosisQuestionBank();
		BeanUtils.copyProperties(dto,po);
		return diagnosisQuestionBankDao.insertSelective(po);
	}
	/**题库Page**/
	@Override
	public PageInfo<DiagnosisQuestionBankDto> selectDiagnosisQuestionBankPage(DiagnosisQuestionBankDto dto) throws Exception {
		DiagnosisQuestionBank po = new DiagnosisQuestionBank();
		BeanUtils.copyProperties(dto,po);
		if (dto.getPageNum() != null && dto.getPageSize() != null) {
			PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
		}
		List<DiagnosisQuestionBank> list = diagnosisQuestionBankDao.selectByCondtionList(po);
		PageInfo<DiagnosisQuestionBank> pageInfo = new PageInfo<DiagnosisQuestionBank>(list);
		PageInfo<DiagnosisQuestionBankDto> info = PageHelperUtil.pageInfoConverter(pageInfo, DiagnosisQuestionBankDto.class);
		return info;
	}
	/**题库list**/
	@Override
	public List<DiagnosisQuestionBankDto> selectDiagnosisQuestionBankList(DiagnosisQuestionBankDto dto) throws Exception {
		DiagnosisQuestionBank po = new DiagnosisQuestionBank();
		BeanUtils.copyProperties(dto,po);
		List<DiagnosisQuestionBank> list = diagnosisQuestionBankDao.selectByCondtionList(po);
		return PageHelperUtil.converterList(list,DiagnosisQuestionBankDto.class);
	}
	/**题库修改**/
	@Override
	public int updateDiagnosisQuestionBank(DiagnosisQuestionBankDto dto) throws Exception {
		DiagnosisQuestionBank po = new DiagnosisQuestionBank();
		BeanUtils.copyProperties(dto,po);
		return  diagnosisQuestionBankDao.updateByPrimaryKey(po);
	}
	/**题库删除**/
	@Override
	public int deleteDiagnosisQuestionBank(String questionBookCode) throws Exception {
		return  diagnosisQuestionBankDao.deleteByPrimaryKey(questionBookCode);
	}
	/**题库**/
	@Override
	public DiagnosisQuestionBankDto selectDiagnosisQuestionBankDto(String questionBookCode) throws Exception {
		DiagnosisQuestionBankDto dto = new DiagnosisQuestionBankDto();
		DiagnosisQuestionBank po = diagnosisQuestionBankDao.selectByPrimaryKey(questionBookCode);
		BeanUtils.copyProperties(po,dto);
		return dto;
	}
}
