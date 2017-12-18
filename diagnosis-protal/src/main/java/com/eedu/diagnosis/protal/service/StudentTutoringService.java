package com.eedu.diagnosis.protal.service;

import com.eedu.diagnosis.protal.model.request.ResourceBaseModel;
import com.eedu.diagnosis.protal.model.response.VideoInfoVo;
import com.eedu.diagnosis.protal.model.response.question.QuestionListModel;

/**
 * Created by dqy on 2017/3/21.
 */
public interface StudentTutoringService {

	VideoInfoVo getVideoByKnowledge(ResourceBaseModel model) throws Exception;

	QuestionListModel getknowledgequestion(ResourceBaseModel model)throws Exception;

	QuestionListModel getQuestionByDiagnosisPaper(ResourceBaseModel model) throws Exception;

	QuestionListModel getknowledgequestionV2(ResourceBaseModel model) throws Exception;

    QuestionListModel getQuestionsByVideo(ResourceBaseModel model) throws Exception;
}
 