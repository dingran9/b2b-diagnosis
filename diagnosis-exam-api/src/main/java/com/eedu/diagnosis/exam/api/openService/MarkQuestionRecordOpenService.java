package com.eedu.diagnosis.exam.api.openService;

import com.eedu.diagnosis.exam.api.dto.DiagnosisMarkQuestionRecordDto;

import java.util.List;

/**
 * Created by zhuchaowei on 2017/9/19.
 */
public interface MarkQuestionRecordOpenService {
    public void batchSave(List<DiagnosisMarkQuestionRecordDto> dtos) throws Exception;

    /**
     * 根据诊断记录获取判题信息
     * @param diagnosisRecordCode 诊断记录code
     * @return
     */
    public List<DiagnosisMarkQuestionRecordDto> findByDiagnosisRecordCode(String diagnosisRecordCode) throws Exception;
}
