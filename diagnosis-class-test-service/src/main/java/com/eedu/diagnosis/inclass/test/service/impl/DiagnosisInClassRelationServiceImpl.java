package com.eedu.diagnosis.inclass.test.service.impl;

import com.eedu.diagnosis.common.utils.PageHelperUtil;
import com.eedu.diagnosis.inclass.test.api.dto.DiagnosisInClassRelationDto;
import com.eedu.diagnosis.inclass.test.api.model.QuestionBankModel;
import com.eedu.diagnosis.inclass.test.api.service.DiagnosisInClassRelationService;
import com.eedu.diagnosis.inclass.test.persist.dao.DiagnosisInClassRelationDao;
import com.eedu.diagnosis.inclass.test.persist.po.DiagnosisInClassRelation;
import com.eedu.diagnosis.inclass.test.persist.util.PublicUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zz on 2017/10/13.
 */

@Service
public class DiagnosisInClassRelationServiceImpl implements DiagnosisInClassRelationService {

    @Autowired
    private DiagnosisInClassRelationDao diagnosisInClassRelationDao;

    /**题库-基础试题绑定列表**/
    @Override
    public List<DiagnosisInClassRelationDto> selectDiagnosisInClassRelationList(DiagnosisInClassRelationDto dto) throws Exception {
        DiagnosisInClassRelation po = new DiagnosisInClassRelation();
        BeanUtils.copyProperties(dto,po);
        List<DiagnosisInClassRelation> list = diagnosisInClassRelationDao.selectByCondtionList(po);
        return PageHelperUtil.converterList(list,DiagnosisInClassRelationDto.class);
    }
    /**保存题库-基础试题绑定**/
    @Override
    public int saveDiagnosisInClassRelation(DiagnosisInClassRelationDto dto) throws Exception {
        DiagnosisInClassRelation po = new DiagnosisInClassRelation();
        BeanUtils.copyProperties(dto,po);
        return diagnosisInClassRelationDao.insertSelective(po);
    }

    /**批量题库-基础试题绑定**/
    @Override
    public int batchSaveDiagnosisInClassRelation(List<DiagnosisInClassRelationDto> dtoList) throws Exception {
        List<DiagnosisInClassRelation> list = PageHelperUtil.converterList(dtoList, DiagnosisInClassRelation.class);
        Map<String,Object> map = new HashMap();
        map.put("list",list);
        return diagnosisInClassRelationDao.batchSaveDiagnosisInClassRelation(map);
    }

    /**根据课中测的code查询题库数量**/
    @Override
    public List<QuestionBankModel> selectDiagnosisQuestionBankCount(String inClassTestCode) throws Exception {
        return diagnosisInClassRelationDao.selectByCondtionCount(inClassTestCode);
    }
    /**删除课中测-基础试题绑定**/
    @Override
    public int deleteDiagnosisInClassRelation(String InClassTestCode) throws Exception {
        return diagnosisInClassRelationDao.deleteByPrimaryKeyByPo(InClassTestCode);
    }
    /**修改课中测-基础试题绑定**/
    @Override
    public int updateDiagnosisInClassRelation(DiagnosisInClassRelationDto dto) throws Exception {
        DiagnosisInClassRelation po = new DiagnosisInClassRelation();
        BeanUtils.copyProperties(dto,po);
        return diagnosisInClassRelationDao.updateByPrimaryKey(po);
    }
    /**课中测Page**/
    @Override
    public PageInfo<DiagnosisInClassRelationDto> selectDiagnosisInClassRelationPage(DiagnosisInClassRelationDto dto) throws Exception {
        DiagnosisInClassRelation po = new DiagnosisInClassRelation();
        BeanUtils.copyProperties(dto,po);
        if (dto.getPageNum() != null && dto.getPageSize() != null) {
            PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        }
        List<DiagnosisInClassRelation> list = diagnosisInClassRelationDao.selectByCondtionList(po);
        PageInfo<DiagnosisInClassRelation> pageInfo = new PageInfo<DiagnosisInClassRelation>(list);
        PageInfo<DiagnosisInClassRelationDto> info = PageHelperUtil.pageInfoConverter(pageInfo, DiagnosisInClassRelationDto.class);
        return info;
    }

}
