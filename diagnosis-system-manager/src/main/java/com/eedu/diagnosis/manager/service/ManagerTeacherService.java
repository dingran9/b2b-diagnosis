package com.eedu.diagnosis.manager.service;

import com.eedu.diagnosis.manager.model.request.*;
import com.eedu.diagnosis.manager.model.response.DiagnosisComplexPaperVo;
import com.eedu.diagnosis.manager.model.response.DiagnosisPaperSchoolRelationVo;
import com.eedu.diagnosis.manager.model.response.DiagnosisPaperVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;


/**
 * zz
 */
public interface ManagerTeacherService {

	PageInfo<DiagnosisPaperVo> getDiagnosisPaperByPaper(DiagnosisPaperModel model) throws Exception ;

	boolean saveDiagnosisPaper(DiagnosisPaperModel model) throws Exception ;

	boolean saveDiagnosisComplexPaper(DiagnosisComplexPaperModel model) throws Exception;

	PageInfo<DiagnosisComplexPaperVo> getDiagnosisComplexPaperByPaper(DiagnosisComplexPaperModel model) throws Exception;

	List<DiagnosisComplexPaperVo> getDiagnosisComplexRelationList(String complexPaperCode) throws Exception;

	boolean saveDiagnosisPaperSchoolRelation(DiagnosisPaperSchoolRelationModel model) throws Exception;

	PageInfo<DiagnosisPaperSchoolRelationVo> getDiagnosisPaperSchoolRelationList(DiagnosisPaperSchoolRelationModel model) throws Exception;

	boolean updateDiagnosisPaperSchoolRelation(List<DiagnosisPaperSchoolRelationModel> list) throws Exception;

	PageInfo<DiagnosisPaperVo> getDiagnosisPaperRelationSchoolByPaper(DiagnosisPaperModel model) throws Exception;


    List<DiagnosisPaperVo> getDiagnosisPaperListByComplexPaperCode(String code)throws Exception;

    boolean bathUpdateDiagnosisComplexPaperByIsRelease(List<String> list) throws Exception;

	boolean updateDiagnosisComplexPaper(DiagnosisComplexPaperModel model) throws Exception;

	boolean updateDiagnosisPaper(DiagnosisPaperModel model) throws Exception;

	boolean saveDiagnosisFeedback(DiagnosisFeedbackModel model) throws Exception;

	/**
	 * 新增和更新单元进度
	 * @param model
	 */
    void saveAndUpdateUnitSchedule(DiagnosisUnitScheduleListModel model)throws Exception;

	/**
	 * 获取单元进度列表
	 * @param model
	 * @return
	 */
	Map<String,Object> getScheduleList(GetUnitScheduleListModel model) throws Exception;
}
