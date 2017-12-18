package com.eedu.diagnosis.exam.api.openService;

import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.exam.api.dto.DiagnosisRecordStudentDto;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by dqy on 2017/3/16.
 */
public interface DiagnosisRecordStudentOpenService {
    /**
     * 获取学生的诊断记录(分页)
     * @param drsd
     * @param pageSize
     * @param pageNum
     * @param order
     * @return
     * @throws Exception
     */
    PageInfo<DiagnosisRecordStudentDto> getList(DiagnosisRecordStudentDto drsd, Integer pageSize, Integer pageNum, Order order) throws Exception;

    /**
     * 条件查询 无分页
     * @param drsd
     * @param order
     * @return
     */
    List<DiagnosisRecordStudentDto> getAll(DiagnosisRecordStudentDto drsd, Order order) throws Exception;

    /**
     * 更新
     * @param dto
     */
    void update(DiagnosisRecordStudentDto dto) throws Exception;

    /**
     * 获取班级平均分
     * @param drDto
     * @return
     */
    Map<String, Object> getClassAverage(DiagnosisRecordStudentDto drDto);

    PageInfo<DiagnosisRecordStudentDto> getListGroupByDiagName(DiagnosisRecordStudentDto dto, Integer pageSize, Integer pageNum, Order order) throws InstantiationException, IllegalAccessException;

    List<DiagnosisRecordStudentDto> getListByRealseCodes(List<String> codes) throws Exception;

    PageInfo<DiagnosisRecordStudentDto> getMarkList(DiagnosisRecordStudentDto dto, Integer pageSize, Integer pageNum, Order order) throws Exception;
}
