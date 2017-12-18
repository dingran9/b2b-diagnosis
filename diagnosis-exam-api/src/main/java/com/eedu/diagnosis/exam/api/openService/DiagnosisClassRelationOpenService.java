package com.eedu.diagnosis.exam.api.openService;

import com.eedu.diagnosis.exam.api.dto.DiagnosisClassRelationDto;

import java.util.List;
import java.util.Map;

/**
 * B端 测评与班级关系
 * Created by dqy on 2017/3/23.
 */
public interface DiagnosisClassRelationOpenService {
    /**
     * 通过条件查询 eg. 一个班级code 查询与该班对应的所有诊断 或 一个测评记录code 查询与之对应的所有班级
     * @param dto
     * @return
     */
    List<DiagnosisClassRelationDto> getListByParam(DiagnosisClassRelationDto dto) throws InstantiationException, IllegalAccessException, Exception;

    void update(DiagnosisClassRelationDto dcrdto) throws Exception;

    void saveBatch(List<DiagnosisClassRelationDto> dcrList) throws Exception;

    void delete(String code) throws Exception;

    List<DiagnosisClassRelationDto> areaExamHistoryList(Map<String,Object> queryMap) throws Exception;

    public List<DiagnosisClassRelationDto> getExamPaperByParameter(Map<String, Object> queryMap) throws Exception;
}
