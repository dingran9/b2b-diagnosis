package com.eedu.diagnosis.exam.api.openService;

import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.exam.api.dto.DiagnosisWrongQuestionDto;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 错题记录
 * Created by dqy on 2017/3/16.
 */
public interface DiagnosisWrongQuestionOpenService {
    /**
     * 学生获取自己的错题记录
     * @param dwqd
     * @param pageSize
     * @param pageNum
     * @param order
     * @return
     * @throws Exception
     */
    PageInfo<DiagnosisWrongQuestionDto> listWrongQuestions(DiagnosisWrongQuestionDto dwqd, Integer pageSize, Integer pageNum, Order order) throws Exception;
    /**
     * 根据学科code 学生id 获取错题记录
     * @param dto
     * @return
     */
    PageInfo<DiagnosisWrongQuestionDto> getWrongQuestions(DiagnosisWrongQuestionDto dto, Integer pageNum, Integer pageSize) throws Exception;

    void delWrongQuestions(List<String> codes) throws Exception;

    void delete(DiagnosisWrongQuestionDto diagnosisWrongQuestionDto) throws Exception;

    void batchSave(List<DiagnosisWrongQuestionDto> dtos) throws Exception;
}
