package com.eedu.diagnosis.exam.service.openServiceImlp;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.common.utils.PageHelperUtil;
import com.eedu.diagnosis.exam.api.dto.DiagnosisRecordStudentDto;
import com.eedu.diagnosis.exam.api.openService.DiagnosisRecordStudentOpenService;
import com.eedu.diagnosis.exam.persist.po.DiagnosisRecordStudentPo;
import com.eedu.diagnosis.exam.service.DiagnosisRecordStudentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学生诊断记录
 * Created by dqy on 2017/3/16.
 */
@Service
public class DiagnosisRecordStudentOpenServiceImpl implements DiagnosisRecordStudentOpenService {
    @Autowired
    private DiagnosisRecordStudentService diagnosisRecordStudentService;

    @Override
    public PageInfo<DiagnosisRecordStudentDto> getList(DiagnosisRecordStudentDto drsd, Integer pageSize, Integer pageNum, Order order) throws Exception {
        Map queryMap = JSON.parseObject(JSONObject.toJSONString(drsd), Map.class);
        PageHelper.startPage(pageNum,pageSize);
        List<DiagnosisRecordStudentPo> diagnosisRecordStudentPoList = diagnosisRecordStudentService.findByCondition(queryMap, order);
        PageInfo<DiagnosisRecordStudentPo> pageInfo = new PageInfo<>(diagnosisRecordStudentPoList);
        PageInfo<DiagnosisRecordStudentDto> diagnosisRecordStudentDtoPageInfo = PageHelperUtil.pageInfoConverter(pageInfo, DiagnosisRecordStudentDto.class);
        return diagnosisRecordStudentDtoPageInfo;
    }

    @Override
    public List<DiagnosisRecordStudentDto> getAll(DiagnosisRecordStudentDto drsd, Order order) throws Exception {
        Map queryMap = JSON.parseObject(JSONObject.toJSONString(drsd), Map.class);
        List<DiagnosisRecordStudentPo> diagnosisRecordStudentPoList = diagnosisRecordStudentService.findByCondition(queryMap, order);
        List<DiagnosisRecordStudentDto> diagnosisRecordStudentDtos = PageHelperUtil.converterList(diagnosisRecordStudentPoList, DiagnosisRecordStudentDto.class);
        return diagnosisRecordStudentDtos;
    }

    @Override
    public void update(DiagnosisRecordStudentDto dto) throws Exception{
        DiagnosisRecordStudentPo po = new DiagnosisRecordStudentPo();
        BeanUtils.copyProperties(dto,po);
//        po.setCode(dto.getCode());
//        po.setUpdateTime(dto.getUpdateTime());
//        po.setDiagnosisStatus(dto.getDiagnosisStatus());
        diagnosisRecordStudentService.update(po);
    }

    @Override
    public Map<String, Object> getClassAverage(DiagnosisRecordStudentDto drDto) {
        DiagnosisRecordStudentPo po = new DiagnosisRecordStudentPo();
        BeanUtils.copyProperties(drDto,po);
        return diagnosisRecordStudentService.getClassAverage(po);
    }

    @Override
    public PageInfo<DiagnosisRecordStudentDto> getListGroupByDiagName(DiagnosisRecordStudentDto drsd, Integer pageSize, Integer pageNum, Order order) throws InstantiationException, IllegalAccessException {
        Map queryMap = JSON.parseObject(JSONObject.toJSONString(drsd), Map.class);
        PageHelper.startPage(pageNum,pageSize);
        List<DiagnosisRecordStudentPo> diagnosisRecordStudentPoList = diagnosisRecordStudentService.getListGroupByDiagName(queryMap, order);
        PageInfo<DiagnosisRecordStudentPo> pageInfo = new PageInfo<>(diagnosisRecordStudentPoList);
        PageInfo<DiagnosisRecordStudentDto> diagnosisRecordStudentDtoPageInfo = PageHelperUtil.pageInfoConverter(pageInfo, DiagnosisRecordStudentDto.class);
        return diagnosisRecordStudentDtoPageInfo;
    }

    @Override
    public List<DiagnosisRecordStudentDto> getListByRealseCodes(List<String> codes) throws Exception {
        if(CollectionUtils.isEmpty(codes)) return null;
        Map queryMap = new HashMap();
        queryMap.put("releaseCodes",codes);
        List<DiagnosisRecordStudentPo> diagnosisRecordStudentPoList = diagnosisRecordStudentService.getListByRealseCodes(queryMap);
        List<DiagnosisRecordStudentDto> diagnosisRecordStudentDtos = PageHelperUtil.converterList(diagnosisRecordStudentPoList, DiagnosisRecordStudentDto.class);
        return diagnosisRecordStudentDtos;
    }

    @Override
    public PageInfo<DiagnosisRecordStudentDto> getMarkList(DiagnosisRecordStudentDto dto, Integer pageSize, Integer pageNum, Order order) throws Exception{
        Map queryMap = JSON.parseObject(JSONObject.toJSONString(dto), Map.class);
        PageHelper.startPage(pageNum,pageSize);
        List<DiagnosisRecordStudentPo> diagnosisRecordStudentPoList = diagnosisRecordStudentService.getMarkList(queryMap,order);
        PageInfo<DiagnosisRecordStudentPo> pageInfo = new PageInfo<>(diagnosisRecordStudentPoList);
        PageInfo<DiagnosisRecordStudentDto> diagnosisRecordStudentDtoPageInfo = PageHelperUtil.pageInfoConverter(pageInfo, DiagnosisRecordStudentDto.class);
        return diagnosisRecordStudentDtoPageInfo;

    }
}
