package com.eedu.diagnosis.exam.api.openService;

import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.exam.api.dto.AnswerSheetDto;
import com.eedu.diagnosis.exam.api.dto.DiagnosisStudentAnswerRecordDto;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by dqy on 2017/3/14.
 */
public interface DiagnosisStudentAnswerRecordOpenService {

    /**
     * 提交答题卡
     * @param asd
     * @throws Exception
     */
    AnswerSheetDto submit(AnswerSheetDto asd) throws Exception;

    /**
     * 获取诊断的答题详情和对错情况
     * @param dsard
     * @param pageSize
     * @param pageNum
     * @param order
     * @return
     * @throws Exception
     */
    PageInfo<DiagnosisStudentAnswerRecordDto> getAnswerRecord(DiagnosisStudentAnswerRecordDto dsard, Integer pageSize, Integer pageNum, Order order) throws Exception;

    void delete(DiagnosisStudentAnswerRecordDto diagnosisStudentAnswerRecordDto) throws Exception;

    List<DiagnosisStudentAnswerRecordDto> getAnswerRecordByDiagnosisCodes(List<String> codes) throws Exception;
}
