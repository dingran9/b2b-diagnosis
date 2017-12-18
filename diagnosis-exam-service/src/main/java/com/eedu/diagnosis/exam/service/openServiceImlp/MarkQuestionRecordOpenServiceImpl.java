package com.eedu.diagnosis.exam.service.openServiceImlp;

import com.eedu.diagnosis.exam.api.dto.DiagnosisMarkQuestionRecordDto;
import com.eedu.diagnosis.exam.api.openService.MarkQuestionRecordOpenService;
import com.eedu.diagnosis.exam.persist.po.DiagnosisMarkQuestionRecordPo;
import com.eedu.diagnosis.exam.service.DiagnosisMarkQuestionRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 判题记录
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-09-19 16:34
 **/
@Service
public class MarkQuestionRecordOpenServiceImpl implements MarkQuestionRecordOpenService{
    @Autowired
    private DiagnosisMarkQuestionRecordService diagnosisMarkQuestionRecordService;
    @Override
    public void batchSave(List<DiagnosisMarkQuestionRecordDto> dtos) throws Exception{
        List<DiagnosisMarkQuestionRecordPo> pos = new ArrayList<>();
        dtos.stream().forEach(d->{
            DiagnosisMarkQuestionRecordPo po = new DiagnosisMarkQuestionRecordPo();
            BeanUtils.copyProperties(d,po);
            po.setCode(UUID.randomUUID().toString().replace("-", "").toUpperCase());
            po.setCreateTime(new Date());
            pos.add(po);
        });
            diagnosisMarkQuestionRecordService.saveList(pos);
    }

    /**
     * 根据诊断记录获取判题信息
     *
     * @param diagnosisRecordCode 诊断记录code
     * @return
     */
    @Override
    public List<DiagnosisMarkQuestionRecordDto> findByDiagnosisRecordCode(String diagnosisRecordCode) throws Exception{
        Map<String,Object> queryMap = new HashMap();
        queryMap.put("diagnosisRecordCode",diagnosisRecordCode);
        List<DiagnosisMarkQuestionRecordPo> pos = diagnosisMarkQuestionRecordService.findByCondition(queryMap);
        List<DiagnosisMarkQuestionRecordDto> dtos = new ArrayList<>();
        pos.stream().forEach(po->{
            DiagnosisMarkQuestionRecordDto dto = new DiagnosisMarkQuestionRecordDto();
            BeanUtils.copyProperties(po,dto);
            dtos.add(dto);
        });
        return dtos;
    }
}
