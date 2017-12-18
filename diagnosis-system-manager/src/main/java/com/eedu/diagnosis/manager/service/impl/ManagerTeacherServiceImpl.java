package com.eedu.diagnosis.manager.service.impl;

import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.common.utils.PageHelperUtil;
import com.eedu.diagnosis.exam.api.dto.DiagnosisUnitScheduleDto;
import com.eedu.diagnosis.exam.api.openService.DiagnosisUnitScheduleOpenService;
import com.eedu.diagnosis.manager.model.request.*;
import com.eedu.diagnosis.manager.model.response.DiagnosisComplexPaperVo;
import com.eedu.diagnosis.manager.model.response.DiagnosisPaperSchoolRelationVo;
import com.eedu.diagnosis.manager.model.response.DiagnosisPaperVo;
import com.eedu.diagnosis.manager.model.response.DiagnosisUnitScheduleVo;
import com.eedu.diagnosis.manager.service.ManagerTeacherService;
import com.eedu.diagnosis.paper.api.dto.*;
import com.eedu.diagnosis.paper.api.openService.BasePaperOpenService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * zz
 */
@Service
public class ManagerTeacherServiceImpl implements ManagerTeacherService {
    @Autowired
    private BasePaperOpenService  basePaperOpenServiceImpl;
	@Autowired
	private DiagnosisUnitScheduleOpenService diagnosisUnitScheduleOpenService;
	/**
	 *  保存单科卷
	 */
	@Override
	public boolean saveDiagnosisPaper(DiagnosisPaperModel model) throws Exception {
		DiagnosisPaperDto dpDto = new DiagnosisPaperDto();
		BeanUtils.copyProperties(model,dpDto);
		String result = basePaperOpenServiceImpl.saveDiagnosisPaper(dpDto);

//		ManagerSystemEvent event = new ManagerSystemEvent(new Object(),new EventHandler(model.getResourcePaperCode(), EventSourceEnum.CREATE_DIAGNOSIS_PAPER));
//		applicationContext.publishEvent(event);
		return (result != null && !"".equals(result));
	}
	/**
	 *  修改单科卷
	 */
	@Override
	public boolean updateDiagnosisPaper(DiagnosisPaperModel model) throws Exception{
		DiagnosisPaperDto dpDto = new DiagnosisPaperDto();
		BeanUtils.copyProperties(model,dpDto);
		String result = basePaperOpenServiceImpl.saveDiagnosisPaper(dpDto);
		return (result != null && !"".equals(result));
	}
	/**
     * 添加单科卷和学校的关系
     */
	@Override
	public boolean saveDiagnosisPaperSchoolRelation(DiagnosisPaperSchoolRelationModel model)throws Exception  {
		List<DiagnosisPaperSchoolRelationDto> list = new ArrayList<DiagnosisPaperSchoolRelationDto>();
		for (SchoolModel schoolModel : model.getSchoolModelList()) {
			DiagnosisPaperSchoolRelationDto relationDto = new DiagnosisPaperSchoolRelationDto();
			relationDto.setDiagnosisPaperCode(model.getDiagnosisPaperCode());
			relationDto.setSchoolCode(schoolModel.getSchoolCode());
			relationDto.setSchoolName(schoolModel.getSchoolName());
			list.add(relationDto);
		}
		String result = basePaperOpenServiceImpl.saveBatchDiagnosisPaperSchoolRelation(list);
		return (result != null && !"".equals(result));
	}
	/**
	 *  保存全科卷
	 */
	@Override
	public boolean saveDiagnosisComplexPaper(DiagnosisComplexPaperModel model) throws Exception {
		DiagnosisComplexPaperDto dcpDto = new DiagnosisComplexPaperDto();
		BeanUtils.copyProperties(model,dcpDto);
		List<DiagnosisPaperDto> tos = PageHelperUtil.converterList(model.getDiagnosisPaperModelsList(), DiagnosisPaperDto.class);
		dcpDto.setDiagnosisPaperDtoList(tos);
		String code = basePaperOpenServiceImpl.saveDiagnosisComplexPaper(dcpDto);
		return (code!=null && !"".equals(code));
	}
	/**
	 *  查询单科卷
	 */
	@Override
	public PageInfo<DiagnosisPaperVo> getDiagnosisPaperByPaper(DiagnosisPaperModel model)  throws Exception {
		DiagnosisPaperDto dpDto = new DiagnosisPaperDto();
        BeanUtils.copyProperties(model,dpDto);
        Order order = new Order("create_time", Order.Direction.desc);
        PageInfo<DiagnosisPaperDto> pageInfo = basePaperOpenServiceImpl.getDiagnosisPaperPaper(dpDto, model.getPageNum(), model.getPageSize(), order);
        PageInfo<DiagnosisPaperVo> result = PageHelperUtil.pageInfoConverter(pageInfo, DiagnosisPaperVo.class);
		return result;
	}
	/**
     * 查询全科卷 分页
     */
	@Override
	public PageInfo<DiagnosisComplexPaperVo> getDiagnosisComplexPaperByPaper(DiagnosisComplexPaperModel model) throws Exception {
		DiagnosisComplexPaperDto dcpDto = new DiagnosisComplexPaperDto();
		BeanUtils.copyProperties(model,dcpDto);
        Order order = new Order("create_time", Order.Direction.desc);
        PageInfo<DiagnosisComplexPaperDto> pageInfo = basePaperOpenServiceImpl.getDiagnosisComplexPaperPaper(dcpDto, model.getPageNum(), model.getPageSize(), order,model.getFlag());
        PageInfo<DiagnosisComplexPaperVo> result = PageHelperUtil.pageInfoConverter(pageInfo, DiagnosisComplexPaperVo.class);
		return result;
	}
	/**
     * 查询全科卷-下面的关系
     */
	@Override
	public List<DiagnosisComplexPaperVo> getDiagnosisComplexRelationList(String complexPaperCode) throws Exception {
        DiagnosisComplexPaperDto dcprDto = new DiagnosisComplexPaperDto();
        dcprDto.setCode(complexPaperCode);
		List<DiagnosisComplexPaperDto> list = basePaperOpenServiceImpl.getDiagnosisComplexPaperAndRelationList(dcprDto);
        List<DiagnosisComplexPaperVo> tos = PageHelperUtil.converterList(list, DiagnosisComplexPaperVo.class);
		return tos;
	}
	/**
     * 查询单科卷科卷-下面的关系
     */
	@Override
	public PageInfo<DiagnosisPaperSchoolRelationVo> getDiagnosisPaperSchoolRelationList(DiagnosisPaperSchoolRelationModel model) throws Exception {
		DiagnosisPaperSchoolRelationDto relationDto = new DiagnosisPaperSchoolRelationDto();
		BeanUtils.copyProperties(model,relationDto);
		Order order = new Order("create_time", Order.Direction.desc);
		PageInfo<DiagnosisPaperSchoolRelationDto> pageInfo = basePaperOpenServiceImpl.getDiagnosisPaperSchoolRelationList(relationDto,model.getPageNum(), model.getPageSize(),order);
        PageInfo<DiagnosisPaperSchoolRelationVo> result = PageHelperUtil.pageInfoConverter(pageInfo, DiagnosisPaperSchoolRelationVo.class);
		return result;
	}
	 /**
     *  修改单科卷和学校的关系发布状态
     */
	@Override
	public boolean updateDiagnosisPaperSchoolRelation(List<DiagnosisPaperSchoolRelationModel> list) throws Exception {
        List<DiagnosisPaperSchoolRelationDto> relationList = PageHelperUtil.converterList(list, DiagnosisPaperSchoolRelationDto.class);
        String result = basePaperOpenServiceImpl.updateDiagnosisPaperSchoolRelation(relationList);
        return (result != null && !"".equals(result));
	}
	@Override
	public PageInfo<DiagnosisPaperVo> getDiagnosisPaperRelationSchoolByPaper(DiagnosisPaperModel model) throws Exception {
		DiagnosisPaperDto dpDto = new DiagnosisPaperDto();
        BeanUtils.copyProperties(model,dpDto);
        Order order = new Order("create_time", Order.Direction.desc);
        PageInfo<DiagnosisPaperDto> pageInfo = basePaperOpenServiceImpl.getDiagnosisPaperRelationSchoolByPaper(dpDto, model.getPageNum(), model.getPageSize(), order);
        PageInfo<DiagnosisPaperVo> result = PageHelperUtil.pageInfoConverter(pageInfo, DiagnosisPaperVo.class);
		return result;
	}

    @Override
    public List<DiagnosisPaperVo> getDiagnosisPaperListByComplexPaperCode(String code)throws Exception {
		List<DiagnosisPaperDto> list = basePaperOpenServiceImpl.getDiagnosisPaperListByComplexPaperCode(code);
		List<DiagnosisPaperVo> tos = PageHelperUtil.converterList(list, DiagnosisPaperVo.class);
		return tos;
    }

    @Override
    public boolean bathUpdateDiagnosisComplexPaperByIsRelease(List<String> list)  throws Exception{
		List<DiagnosisComplexPaperDto> dcpDtoList =new ArrayList<DiagnosisComplexPaperDto>();
		list.forEach(dcp -> {
			DiagnosisComplexPaperDto mode = new DiagnosisComplexPaperDto();
			mode.setIsRelease(1);
			mode.setCode(dcp);
			dcpDtoList.add(mode);
		});
		String result = basePaperOpenServiceImpl.updateDiagnosisComplexPaperList(dcpDtoList);
		return (result != null && !"".equals(result));
    }
	@Override
	public boolean updateDiagnosisComplexPaper(DiagnosisComplexPaperModel model)  throws Exception{

		DiagnosisComplexPaperDto dcpDto = new DiagnosisComplexPaperDto();
		BeanUtils.copyProperties(model,dcpDto);
		List<DiagnosisComplexPaperRelationDto> tos = PageHelperUtil.converterList(model.getDiagnosisComplexPaperRelationModelList(), DiagnosisComplexPaperRelationDto.class);
		dcpDto.setDiagnosisComplexPaperRelationDtoList(tos);
		String code = basePaperOpenServiceImpl.updateDiagnosisComplexPaper(dcpDto);
		return (code!=null && !"".equals(code));
		
	}
	@Override
	public boolean saveDiagnosisFeedback(DiagnosisFeedbackModel model) throws Exception {
		DiagnosisFeedbackDto dfDto = new DiagnosisFeedbackDto();
		BeanUtils.copyProperties(model,dfDto);
		String result = basePaperOpenServiceImpl.saveDiagnosisFeedback(dfDto);
		return (result != null && !"".equals(result));
	}

	@Override
	public void saveAndUpdateUnitSchedule(DiagnosisUnitScheduleListModel model) throws Exception {
        List<DiagnosisUnitScheduleModel> diagnosisUnitSchedules = model.getDiagnosisUnitSchedules();
        List<DiagnosisUnitScheduleDto> diagnosisUnitScheduleDtos = PageHelperUtil.converterList(diagnosisUnitSchedules, DiagnosisUnitScheduleDto.class);
        diagnosisUnitScheduleOpenService.saveAndUpdateList(diagnosisUnitScheduleDtos);
    }

    @Override
    public Map<String, Object> getScheduleList(GetUnitScheduleListModel model) throws Exception{
        Map<String, Object>  result = new HashMap();
        List<DiagnosisUnitScheduleVo> diagnosisUnitSchedules = new ArrayList<>();

        DiagnosisUnitScheduleDto dto = new DiagnosisUnitScheduleDto();
        BeanUtils.copyProperties(model,dto);
        Order order = new Order("create_time", Order.Direction.desc);
        PageInfo<DiagnosisUnitScheduleDto> pageInfo = diagnosisUnitScheduleOpenService.getList(dto, model.getPageNum(), model.getPageSize(), order);
        if (pageInfo != null && !pageInfo.getList().isEmpty()) {
            diagnosisUnitSchedules = PageHelperUtil.converterList(pageInfo.getList(), DiagnosisUnitScheduleVo.class);
            result.put("totalPage", pageInfo.getPages());
            result.put("total", pageInfo.getTotal());
        } else {
            result.put("totalPage", 0);
            result.put("total", 0);
        }
        result.put("currPage", model.getPageNum());
        result.put("list", diagnosisUnitSchedules);
        return result;
    }


}
