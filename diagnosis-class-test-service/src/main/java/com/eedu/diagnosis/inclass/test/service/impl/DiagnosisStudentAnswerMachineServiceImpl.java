package com.eedu.diagnosis.inclass.test.service.impl;

import com.eedu.diagnosis.common.utils.PageHelperUtil;
import com.eedu.diagnosis.inclass.test.api.dto.DiagnosisStudentAnswerMachineDto;
import com.eedu.diagnosis.inclass.test.api.service.DiagnosisStudentAnswerMachineService;
import com.eedu.diagnosis.inclass.test.persist.dao.DiagnosisStudentAnswerMachineDao;
import com.eedu.diagnosis.inclass.test.persist.po.DiagnosisStudentAnswerMachine;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DiagnosisStudentAnswerMachineServiceImpl implements DiagnosisStudentAnswerMachineService {

	@Autowired
	private DiagnosisStudentAnswerMachineDao diagnosisStudentAnswerMachineDao;

	/**保存学生绑定答题器**/
	@Override
	public int saveDiagnosisStudentAnswerMachine(DiagnosisStudentAnswerMachineDto dto) throws Exception {
		DiagnosisStudentAnswerMachine po = new DiagnosisStudentAnswerMachine();
		BeanUtils.copyProperties(dto,po);
		return diagnosisStudentAnswerMachineDao.insertSelective(po);
	}
	/**批量学生绑定答题器**/
	@Override
	public int batchSaveDiagnosisStudentAnswerMachine(List<DiagnosisStudentAnswerMachineDto> dtoList) throws Exception {
		List<DiagnosisStudentAnswerMachine> list = PageHelperUtil.converterList(dtoList, DiagnosisStudentAnswerMachine.class);
		Map<String,Object> map = new HashMap();
		map.put("list",list);
		return diagnosisStudentAnswerMachineDao.batchSaveDiagnosisStudentAnswerMachine(map);
	}
	/**学生绑定答题器Page**/
	@Override
	public PageInfo<DiagnosisStudentAnswerMachineDto> selectDiagnosisStudentAnswerMachinePage(DiagnosisStudentAnswerMachineDto dto) throws Exception {
		DiagnosisStudentAnswerMachine po = new DiagnosisStudentAnswerMachine();
		BeanUtils.copyProperties(dto,po);
		if (dto.getPageNum() != null && dto.getPageSize() != null) {
			PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
		}
		List<DiagnosisStudentAnswerMachine> list = diagnosisStudentAnswerMachineDao.selectByCondtionList(po);
		PageInfo<DiagnosisStudentAnswerMachine> pageInfo = new PageInfo<DiagnosisStudentAnswerMachine>(list);
		PageInfo<DiagnosisStudentAnswerMachineDto> info = PageHelperUtil.pageInfoConverter(pageInfo, DiagnosisStudentAnswerMachineDto.class);
		return info;
	}
	/**学生绑定答题器list**/
	@Override
	public List<DiagnosisStudentAnswerMachineDto> selectDiagnosisStudentAnswerMachineList(DiagnosisStudentAnswerMachineDto dto) throws Exception {
		DiagnosisStudentAnswerMachine po = new DiagnosisStudentAnswerMachine();
		BeanUtils.copyProperties(dto,po);
		List<DiagnosisStudentAnswerMachine> list = diagnosisStudentAnswerMachineDao.selectByCondtionList(po);
		return PageHelperUtil.converterList(list,DiagnosisStudentAnswerMachineDto.class);
	}
	/**学生绑定答题器修改**/
	@Override
	public int updateDiagnosisStudentAnswerMachine(DiagnosisStudentAnswerMachineDto dto) throws Exception {
		DiagnosisStudentAnswerMachine po = new DiagnosisStudentAnswerMachine();
		BeanUtils.copyProperties(dto,po);
		return  diagnosisStudentAnswerMachineDao.updateByPrimaryKey(po);
	}
	/**学生绑定答题器删除**/
	@Override
	public int deleteDiagnosisStudentAnswerMachine(String  studentAnswerMachineCode) throws Exception {
		return  diagnosisStudentAnswerMachineDao.deleteByPrimaryKey(studentAnswerMachineCode);
	}
	/**学生绑定答题器**/
	@Override
	public DiagnosisStudentAnswerMachineDto selectDiagnosisStudentAnswerMachineDto(String studentAnswerSheetCode) throws Exception {
		DiagnosisStudentAnswerMachineDto dto = new DiagnosisStudentAnswerMachineDto();
		DiagnosisStudentAnswerMachine po = diagnosisStudentAnswerMachineDao.selectByPrimaryKey(studentAnswerSheetCode);
		BeanUtils.copyProperties(po,dto);
		return dto;
	}

}
