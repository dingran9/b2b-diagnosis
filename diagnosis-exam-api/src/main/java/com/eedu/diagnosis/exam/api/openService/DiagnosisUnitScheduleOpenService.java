package com.eedu.diagnosis.exam.api.openService;

import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.exam.api.dto.DiagnosisUnitScheduleDto;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by dqy on 2017/10/12.
 */
public interface DiagnosisUnitScheduleOpenService {
    PageInfo<DiagnosisUnitScheduleDto> getList(DiagnosisUnitScheduleDto dto, Integer pageNum, Integer pageSize, Order order) throws Exception;

    void saveAndUpdateList(List<DiagnosisUnitScheduleDto> list) throws Exception;
}
