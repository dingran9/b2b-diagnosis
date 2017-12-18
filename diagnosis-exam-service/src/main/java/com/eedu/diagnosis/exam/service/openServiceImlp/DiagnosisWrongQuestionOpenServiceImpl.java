package com.eedu.diagnosis.exam.service.openServiceImlp;

import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.common.utils.PageHelperUtil;
import com.eedu.diagnosis.exam.api.dto.DiagnosisWrongQuestionDto;
import com.eedu.diagnosis.exam.api.openService.DiagnosisWrongQuestionOpenService;
import com.eedu.diagnosis.exam.persist.po.DiagnosisWrongQuestion;
import com.eedu.diagnosis.exam.service.DiagnosisWrongQuestionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 错题记录
 * Created by dqy on 2017/3/17.
 */
@Service
public class DiagnosisWrongQuestionOpenServiceImpl implements DiagnosisWrongQuestionOpenService {
    @Autowired
    private DiagnosisWrongQuestionService diagnosisWrongQuestionService;

    @Override
    public PageInfo<DiagnosisWrongQuestionDto> listWrongQuestions(DiagnosisWrongQuestionDto dwqd, Integer pageSize, Integer pageNum, Order order) throws Exception {
        Map queryMap = JSONObject.parseObject(JSONObject.toJSONString(dwqd), Map.class);
        PageHelper.startPage(pageNum,pageSize);
        List<DiagnosisWrongQuestion> wrongQuestionList = diagnosisWrongQuestionService.findByCondition(queryMap, order);
        PageInfo<DiagnosisWrongQuestion> pageInfo = new PageInfo<>(wrongQuestionList);

        PageInfo<DiagnosisWrongQuestionDto> diagnosisWrongQuestionDtoPageInfo = PageHelperUtil.pageInfoConverter(pageInfo, DiagnosisWrongQuestionDto.class);
        return diagnosisWrongQuestionDtoPageInfo;
    }

    @Override
    public PageInfo<DiagnosisWrongQuestionDto> getWrongQuestions(DiagnosisWrongQuestionDto dto, Integer pageNum, Integer pageSize) throws Exception {
        Map queryMap = JSONObject.parseObject(JSONObject.toJSONString(dto), Map.class);
        PageHelper.startPage(pageNum,pageSize);
        List<DiagnosisWrongQuestion> list = diagnosisWrongQuestionService.getWrongQuestions(queryMap);
        PageInfo<DiagnosisWrongQuestion> pageInfo = new PageInfo<>(list);

        PageInfo<DiagnosisWrongQuestionDto> diagnosisWrongQuestionDtoPageInfo = PageHelperUtil.pageInfoConverter(pageInfo, DiagnosisWrongQuestionDto.class);
        return diagnosisWrongQuestionDtoPageInfo;
    }

    @Override
    public void delWrongQuestions(List<String> codes) throws Exception{
        diagnosisWrongQuestionService.delete(codes);
    }

    @Override
    public void delete(DiagnosisWrongQuestionDto diagnosisWrongQuestionDto) throws Exception {
        diagnosisWrongQuestionService.deleteByDiaWrongQuestion(diagnosisWrongQuestionDto.getDiagnosisRecordCode());
    }

    @Override
    public void batchSave(List<DiagnosisWrongQuestionDto> dtos) throws Exception {
        List<DiagnosisWrongQuestion> wrongQuestions = PageHelperUtil.converterList(dtos, DiagnosisWrongQuestion.class);
        diagnosisWrongQuestionService.saveList(wrongQuestions);
    }

}
