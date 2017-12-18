package com.eeduspace.report.service;

import com.eeduspace.report.model.ThreeColorModel;
import com.eeduspace.report.po.ThreeColorPo;

import java.util.List;

/**
 * <p>描述</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/17 9:31
 **/
public interface ThreeColorService {
    ThreeColorPo save(ThreeColorPo threeColorPo);
    List<ThreeColorPo> batchSave(List<ThreeColorPo> threeColorPoList);

    /**
     * 获取三色图组合信息
     * @param releaseExamCode 发布考试记录cde
     * @param subjectCode  学科code
     * @return
     */
    List<ThreeColorModel> getByCondition(String releaseExamCode,String subjectCode) throws Exception;
    /**
     * 获取三色图组合信息
     * @param releaseExamCode 发布考试记录cde
     * @param subjectCode  学科code
     * @return
     */
    List<ThreeColorModel> findByReleaseExamCodeAndSubjectCode(String releaseExamCode,String subjectCode) throws Exception;
}
