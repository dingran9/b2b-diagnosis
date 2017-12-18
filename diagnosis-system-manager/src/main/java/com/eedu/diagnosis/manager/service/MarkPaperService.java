package com.eedu.diagnosis.manager.service;

import com.eedu.diagnosis.common.model.paperEntity.PaperSystem;
import com.eedu.diagnosis.manager.model.request.DiagnosisPaperModel;
import com.eedu.diagnosis.manager.model.request.MarkPaperModel;
import com.eedu.diagnosis.manager.model.response.DiagnosisRecordTeacherModel;

import java.util.Map;

/**
 * Created by dqy on 2017/9/11.
 */
public interface MarkPaperService {
    /**
     * 获取学生答题记录
     * @param model
     * @return
     * @throws Exception
     */
    Map<String,Object> studentExamList(DiagnosisRecordTeacherModel model) throws Exception;

    /**
     * 获取试卷详情 附带学生答题信息
     * @param model
     * @return
     * @throws Exception
     */
    PaperSystem getPaperAndAnswerResult(DiagnosisPaperModel model) throws Exception;

    /**
     * 教师判卷后获取判断信息与学生答案信息
     * @param model
     * @return
     * @throws Exception
     */
    PaperSystem getMarkResult(DiagnosisPaperModel model) throws Exception;

    void saveMarkPaperInfo(MarkPaperModel model) throws Exception;


}
