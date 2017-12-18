package com.eedu.diagnosis.exam.service.openServiceImlp;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.common.utils.PageHelperUtil;
import com.eedu.diagnosis.exam.api.dto.DiagnosisUnitScheduleDto;
import com.eedu.diagnosis.exam.api.openService.DiagnosisUnitScheduleOpenService;
import com.eedu.diagnosis.exam.persist.po.DiagnosisUnitSchedulePo;
import com.eedu.diagnosis.exam.service.DiagnosisUnitScheduleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by dqy on 2017/10/12.
 */
@Service
public class DiagnosisUnitScheduleOpenServiceImpl implements DiagnosisUnitScheduleOpenService {
    @Autowired
    private DiagnosisUnitScheduleService diagnosisUnitScheduleService;
    @Override
    public PageInfo<DiagnosisUnitScheduleDto> getList(DiagnosisUnitScheduleDto dto, Integer pageNum, Integer pageSize, Order order) throws Exception {
        Map queryMap = JSONObject.parseObject(JSONObject.toJSONString(dto), Map.class);
        PageHelper.startPage(pageNum, pageSize);
        List<DiagnosisUnitSchedulePo> list = diagnosisUnitScheduleService.findByCondition(queryMap, order);
        PageInfo<DiagnosisUnitSchedulePo> diagnosisUnitSchedulePoPageInfo = new PageInfo<>(list);
        PageInfo<DiagnosisUnitScheduleDto> diagnosisUnitScheduleDtoPageInfo = PageHelperUtil.pageInfoConverter(diagnosisUnitSchedulePoPageInfo, DiagnosisUnitScheduleDto.class);
        return diagnosisUnitScheduleDtoPageInfo;
    }

    @Override
    public void saveAndUpdateList(List<DiagnosisUnitScheduleDto> list) throws Exception {
        List<DiagnosisUnitScheduleDto> newList = list.stream().filter(diagnosisUnitScheduleDto -> null == diagnosisUnitScheduleDto.getCode()).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(newList)){
            List<DiagnosisUnitSchedulePo> diagnosisUnitSchedulePos = getDiagnosisUnitSchedulePos(newList);
            diagnosisUnitScheduleService.saveList(diagnosisUnitSchedulePos);
            list.removeAll(newList);
        }
        List<DiagnosisUnitSchedulePo> oldList = PageHelperUtil.converterList(list, DiagnosisUnitSchedulePo.class);
        diagnosisUnitScheduleService.updateList(oldList);


    }

    private List<DiagnosisUnitSchedulePo> getDiagnosisUnitSchedulePos(List<DiagnosisUnitScheduleDto> newList) throws IllegalAccessException, InstantiationException {
        List<DiagnosisUnitSchedulePo> diagnosisUnitSchedulePos = PageHelperUtil.converterList(newList, DiagnosisUnitSchedulePo.class);
        for (DiagnosisUnitSchedulePo diagnosisUnitSchedulePo : diagnosisUnitSchedulePos) {
            diagnosisUnitSchedulePo.setCode(UUID.randomUUID().toString().replace("-","").toUpperCase());
        }
        return diagnosisUnitSchedulePos;
    }


    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(4);
        list.add(3);
        List<Integer> list2 = new ArrayList<>();

        boolean b = list.removeAll(list2);
        System.out.println(b);
        System.out.println(JSONObject.toJSONString(list));

    }
}
