package com.eedu.diagnosis.exam.persist.dao.impl;

import com.eedu.diagnosis.exam.persist.dao.DiagnosisStudentAnswerRecordPoMapper;
import com.eedu.diagnosis.exam.persist.po.DiagnosisStudentAnswerRecordPo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by dqy on 2017/3/15.
 */
@Repository
public class DiagnosisStudentAnswerRecordPoMapperImpl extends BaseDaoImpl<DiagnosisStudentAnswerRecordPo>  implements DiagnosisStudentAnswerRecordPoMapper {
    private static String DELETEBYDIAGNOSISSTUDENTANSWERRECORDPO = "deleteByDiagnosisStudentAnswerRecordPo";
    private static String GETLISTBYANSWERRECORDCODES = "getListByAnswerRecordCodes";
    @Override
    public void deleteByDiagnosisStudentAnswerRecordPo(DiagnosisStudentAnswerRecordPo diagnosisStudentAnswerRecordPo) throws Exception{
        sessionTemplate.delete(DELETEBYDIAGNOSISSTUDENTANSWERRECORDPO,diagnosisStudentAnswerRecordPo.getDiagnosisRecordCode());
    }

    @Override
    public List<DiagnosisStudentAnswerRecordPo> getListByAnswerRecordCodes(Map queryMap) throws Exception {
        return sessionTemplate.selectList(GETLISTBYANSWERRECORDCODES,queryMap);
    }
}
