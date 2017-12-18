package com.eedu.diagnosis.exam.service.openServiceImlp;

import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.common.utils.PageHelperUtil;
import com.eedu.diagnosis.exam.api.dto.DiagnosisClassRelationDto;
import com.eedu.diagnosis.exam.api.openService.DiagnosisClassRelationOpenService;
import com.eedu.diagnosis.exam.persist.po.DiagnosisClassRelationPo;
import com.eedu.diagnosis.exam.service.DiagnosisClassRelationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Created by dqy on 2017/3/23.
 */
@Service
public class DiagnosisClassRelationOpenServiceImpl implements DiagnosisClassRelationOpenService {
    @Autowired
    private DiagnosisClassRelationService diagnosisClassRelationService;
    @Override
    public List<DiagnosisClassRelationDto> getListByParam(DiagnosisClassRelationDto dto) throws Exception {
        Map map = JSONObject.parseObject(JSONObject.toJSONString(dto), Map.class);
        List<DiagnosisClassRelationPo> list = diagnosisClassRelationService.findByCondition(map);
        List<DiagnosisClassRelationDto> diagnosisClassRelationDtos = PageHelperUtil.converterList(list, DiagnosisClassRelationDto.class);
        return diagnosisClassRelationDtos;
    }

    @Override
    public void update(DiagnosisClassRelationDto dcrdto) throws Exception {
        DiagnosisClassRelationPo diagnosisClassRelationPo = new DiagnosisClassRelationPo();
        BeanUtils.copyProperties(dcrdto,diagnosisClassRelationPo);
        diagnosisClassRelationService.update(diagnosisClassRelationPo);
    }

    @Override
    public void saveBatch(List<DiagnosisClassRelationDto> dcrList) throws Exception {
        if(!CollectionUtils.isEmpty(dcrList)){
            List<DiagnosisClassRelationPo> list = new ArrayList<>();
            dcrList.forEach(new Consumer<DiagnosisClassRelationDto>() {
                @Override
                public void accept(DiagnosisClassRelationDto diagnosisClassRelationDto) {
                    DiagnosisClassRelationPo diagnosisClassRelationPo = new DiagnosisClassRelationPo();
                    BeanUtils.copyProperties(diagnosisClassRelationDto,diagnosisClassRelationPo);
                    diagnosisClassRelationPo.setCode(UUID.randomUUID().toString().replace("-", "").toUpperCase());
                    list.add(diagnosisClassRelationPo);
                }
            });
            diagnosisClassRelationService.saveList(list);
        }

    }

    @Override
    public void delete(String code) throws Exception {
        diagnosisClassRelationService.delete(code);
    }

    @Override
    public List<DiagnosisClassRelationDto> areaExamHistoryList(Map<String, Object> queryMap) throws Exception {
        List<DiagnosisClassRelationPo> list = diagnosisClassRelationService.getAreaExamHistoryList(queryMap);
        return PageHelperUtil.converterList(list,DiagnosisClassRelationDto.class);
    }

    @Override
    public List<DiagnosisClassRelationDto> getExamPaperByParameter(Map<String, Object> queryMap) throws Exception {
        List<DiagnosisClassRelationPo> list = diagnosisClassRelationService.getExamPaperByParameter(queryMap);
        return PageHelperUtil.converterList(list,DiagnosisClassRelationDto.class);
    }

}
