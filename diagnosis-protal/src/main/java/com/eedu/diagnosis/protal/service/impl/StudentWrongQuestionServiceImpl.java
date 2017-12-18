package com.eedu.diagnosis.protal.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.eedu.diagnosis.common.utils.PageHelperUtil;
import com.eedu.diagnosis.exam.api.dto.DiagnosisWrongQuestionDto;
import com.eedu.diagnosis.exam.api.openService.DiagnosisWrongQuestionOpenService;
import com.eedu.diagnosis.protal.model.request.StudentModel;
import com.eedu.diagnosis.protal.model.request.WrongQuestionRequestModel;
import com.eedu.diagnosis.protal.model.response.QuestionAnalyzeModel;
import com.eedu.diagnosis.protal.model.response.QuestionOptionModel;
import com.eedu.diagnosis.protal.model.response.WrongQuestionModel;
import com.eedu.diagnosis.protal.service.StudentWrongQuestionService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dqy on 2017/3/27.
 */
@Service
public class StudentWrongQuestionServiceImpl implements StudentWrongQuestionService {
    @Autowired
    private DiagnosisWrongQuestionOpenService diagnosisWrongQuestionOpenService;

    @Override
    public PageInfo<WrongQuestionModel> getWrongQuestions(StudentModel model) throws Exception {
        DiagnosisWrongQuestionDto dto = new DiagnosisWrongQuestionDto();
        dto.setStudentCode(model.getUserId());
        dto.setSubjectCode(model.getSubjectCode());
        PageInfo<DiagnosisWrongQuestionDto> pageInfo = diagnosisWrongQuestionOpenService.getWrongQuestions(dto,model.getPageNum(),model.getPageSize());
        PageInfo<WrongQuestionModel> wrongQuestionModelPageInfo = PageHelperUtil.pageInfoConverter(pageInfo, WrongQuestionModel.class);
        wrongQuestionModelPageInfo.getList().forEach(wrongQuestionModel -> {
            List<QuestionOptionModel> questionOptionModels = JSONArray.parseArray(wrongQuestionModel.getQuestionOption(),QuestionOptionModel.class);
            wrongQuestionModel.setQuestionOptions(questionOptionModels);
            wrongQuestionModel.setQuestionOption(null);
            List<QuestionAnalyzeModel> questionAnalyzeModels = JSONArray.parseArray(wrongQuestionModel.getQuestionAnalyze(),QuestionAnalyzeModel.class);
            wrongQuestionModel.setQuestionAnalyzes(questionAnalyzeModels);
            wrongQuestionModel.setQuestionAnalyze(null);
        });
        return wrongQuestionModelPageInfo;
    }

    @Override
    public void delWrongQuestions(WrongQuestionRequestModel model) throws Exception{
        diagnosisWrongQuestionOpenService.delWrongQuestions(model.getQuestionCodes());
    }
}
