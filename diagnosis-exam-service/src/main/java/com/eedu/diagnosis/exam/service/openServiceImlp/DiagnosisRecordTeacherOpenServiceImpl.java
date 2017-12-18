package com.eedu.diagnosis.exam.service.openServiceImlp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eedu.auth.beans.AuthClassBean;
import com.eedu.auth.beans.AuthGroupBean;
import com.eedu.auth.beans.AuthUserManagerConditionBean;
import com.eedu.auth.service.AuthGroupService;
import com.eedu.auth.service.AuthUserManagerService;
import com.eedu.diagnosis.common.enumration.DiagnosisTypeEnum;
import com.eedu.diagnosis.common.enumration.EventSourceEnum;
import com.eedu.diagnosis.common.enumration.ExamTypeEnum;
import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.common.utils.PageHelperUtil;
import com.eedu.diagnosis.exam.api.dto.DiagnosisRecordDetailDto;
import com.eedu.diagnosis.exam.api.dto.DiagnosisRecordTeacherDto;
import com.eedu.diagnosis.exam.api.dto.ReleaseDiagnosisDto;
import com.eedu.diagnosis.exam.api.dto.SchoolDto;
import com.eedu.diagnosis.exam.api.openService.DiagnosisRecordTeacherOpenService;
import com.eedu.diagnosis.exam.eventListener.DiagnosisEvent;
import com.eedu.diagnosis.exam.eventListener.EventSourceHandler;
import com.eedu.diagnosis.exam.persist.po.DiagnosisClassRelationPo;
import com.eedu.diagnosis.exam.persist.po.DiagnosisRecordTeacherPo;
import com.eedu.diagnosis.exam.service.DiagnosisClassRelationService;
import com.eedu.diagnosis.exam.service.DiagnosisRecordTeacherService;
import com.eedu.diagnosis.paper.api.dto.DiagnosisPaperDto;
import com.eedu.diagnosis.paper.api.openService.BasePaperOpenService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dqy on 2017/3/16.
 */
@Service
public class DiagnosisRecordTeacherOpenServiceImpl implements DiagnosisRecordTeacherOpenService, ApplicationContextAware {

    private ApplicationContext applicationContext;
    @Autowired
    private DiagnosisRecordTeacherService diagnosisRecordTeacherService;
    @Autowired
    private DiagnosisClassRelationService diagnosisClassRelationService;
    @Autowired
    private AuthGroupService authGroupService;
    @Autowired
    private BasePaperOpenService basePaperOpenService;
    @Autowired
    private AuthUserManagerService userManagerService;


    @Override
    public DiagnosisRecordTeacherDto getDiagnosisRecordTeacherByCode(String teacherRecordCode) throws Exception {
        DiagnosisRecordTeacherPo diagnosisRecordTeacherPo = diagnosisRecordTeacherService.get(teacherRecordCode);
        DiagnosisRecordTeacherDto diagnosisRecordTeacherDto = new DiagnosisRecordTeacherDto();
        BeanUtils.copyProperties(diagnosisRecordTeacherPo, diagnosisRecordTeacherDto);
        return diagnosisRecordTeacherDto;
    }

    /**
     * 教师发布诊断
     *
     * @param drtd
     * @return
     */
    @Transactional
    @Override
    public String release(DiagnosisRecordTeacherDto drtd) throws Exception {
        //1 保存教师发布记录
        DiagnosisRecordTeacherPo drtp = getDiagnosisRecordTeacherPo(drtd);

        //2 保存 该次所发布的班级情况
        List<Integer> classCodes = null;
        List<Integer> subjectCodes = new ArrayList<>();
        //如果不是单元测试 默认是发布给全年级
        List<DiagnosisPaperDto> diagnosisPaperList = new ArrayList<>();
        if (!ExamTypeEnum.UNIT.getCode().equals(drtd.getExamType())) {
            List<AuthGroupBean> classes = authGroupService.getClassBySchoolGrade(drtd.getSchoolCode(), drtd.getGradeCode());
            if (!CollectionUtils.isEmpty(classes)) {
                classCodes = new ArrayList<>();
                for (AuthGroupBean authGroupBean : classes) {
                    classCodes.add(authGroupBean.getGroupId());
                }
            }
            diagnosisPaperList = basePaperOpenService.getDiagnosisPaperListByComplexPaperCode(drtd.getDiagnosisPaperCode());
            if (!CollectionUtils.isEmpty(diagnosisPaperList)) {
                for (DiagnosisPaperDto dp : diagnosisPaperList) {
                    if (!subjectCodes.contains(dp.getSubjectCode())) {
                        subjectCodes.add(dp.getSubjectCode());
                    }
                }
            }
        } else {
            classCodes = drtd.getClassCodes();
            subjectCodes.add(drtd.getSubjectCode());
        }

        List<AuthUserManagerConditionBean> teacherList = getAuthUserManagerConditionBeans(drtd);

        if (!classCodes.isEmpty()) {
            List<DiagnosisClassRelationPo> list = getDiagnosisClassRelationPos(drtd.getSchoolCode(), drtd.getSchoolName(), drtp, classCodes, subjectCodes, teacherList,diagnosisPaperList);
            diagnosisClassRelationService.saveList(list);
        }
        Map params = new HashMap();
        params.put("classCodes", classCodes);
        params.put("drtd", JSONObject.toJSONString(drtp));
        DiagnosisEvent event = new DiagnosisEvent(new Object(), new EventSourceHandler(JSONObject.toJSONString(params), EventSourceEnum.SAVE_STUDENT_DIAGNOSIS_RECORDS));
        applicationContext.publishEvent(event);
        return drtp.getCode();
    }

    private DiagnosisRecordTeacherPo getDiagnosisRecordTeacherPo(DiagnosisRecordTeacherDto drtd) throws Exception {
        DiagnosisRecordTeacherPo drtp = new DiagnosisRecordTeacherPo();
        BeanUtils.copyProperties(drtd, drtp);
        drtp.setCode(UUID.randomUUID().toString().replace("-", "").toUpperCase());
        drtp.setCreateTime(new Date());
        drtp.setIsSnapshot(0);
        diagnosisRecordTeacherService.save(drtp);
        return drtp;
    }

    private List<DiagnosisClassRelationPo> getDiagnosisClassRelationPos(Integer schoolCode, String schoolName,
                                                                        DiagnosisRecordTeacherPo drtp, List<Integer> classCodes, List<Integer> subjectCodes,
                                                                        List<AuthUserManagerConditionBean> teacherList,List<DiagnosisPaperDto> diagnosisPaperList) {
        List<DiagnosisClassRelationPo> list = new ArrayList<>();
        for (Integer classCode : classCodes) {
            for (Integer subjectCode : subjectCodes) {
                for (AuthUserManagerConditionBean teacher : teacherList) {
                    List<AuthClassBean> classBeanList = teacher.getClassBeanList();
                    for (AuthClassBean authClassBean : classBeanList) {
                        if (authClassBean.getClassId().equals(classCode)
                                && teacher.getUserSubjectIden().equals(subjectCode.toString())) {
                            DiagnosisClassRelationPo dc = new DiagnosisClassRelationPo();
                            dc.setCreateTime(new Date());
                            dc.setClassCode(classCode);
                            dc.setClassName(authClassBean.getClassName());
                            dc.setDiagnosisRecordCode(drtp.getCode());
                            dc.setSchoolCode(schoolCode);
                            dc.setSchoolName(schoolName);
                            dc.setSubjectCode(subjectCode);
                            dc.setTeacherCode(teacher.getUserId());
                            dc.setTeacherName(teacher.getUserName());
                            dc.setStartTime(drtp.getStartTime());
                            dc.setEndTime(drtp.getEndTime());
                            dc.setArtType(authClassBean.getGroupArt());
                            dc.setHasReport(0);
                            dc.setIsRead(0);
                            if(drtp.getDiagnosisType().equals(DiagnosisTypeEnum.SINGLE.getValue())){
                                dc.setDiagnosisPaperName(drtp.getDiagnosisPaperName());
                                dc.setDiagnosisPaperCode(drtp.getDiagnosisPaperCode());
                            }else {
                                for (DiagnosisPaperDto diagnosisPaperDto : diagnosisPaperList) {
                                    if(teacher.getUserSubjectIden().equals(diagnosisPaperDto.getSubjectCode().toString())
                                            && authClassBean.getGroupArt().equals(diagnosisPaperDto.getArtsType())){
                                        dc.setDiagnosisPaperCode(diagnosisPaperDto.getCode());
                                        dc.setDiagnosisPaperName(diagnosisPaperDto.getDiagnosisPaperName());
                                        break;
                                    }
                                }
                            }
                            list.add(dc);
                        }
                    }
                }
            }
        }
        return list;
    }

    private List<AuthUserManagerConditionBean> getAuthUserManagerConditionBeans(DiagnosisRecordTeacherDto drtd) {
        AuthUserManagerConditionBean conditionBean = new AuthUserManagerConditionBean();
        conditionBean.setPageNum(1);
        conditionBean.setPageSize(200);
        conditionBean.setUserGradeIden(drtd.getGradeCode().toString());
        conditionBean.setUserSchoolId(drtd.getSchoolCode());
        PageInfo<AuthUserManagerConditionBean> pageInfo = userManagerService.getUserManagerListByCondition(conditionBean);
        return pageInfo.getList();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public String teachingManagerRelease(ReleaseDiagnosisDto releaseExamDto) throws Exception {
        //1保存发布记录
        DiagnosisRecordTeacherPo po = new DiagnosisRecordTeacherPo();
        BeanUtils.copyProperties(releaseExamDto, po);
        po.setCode(UUID.randomUUID().toString().replace("-", "").toUpperCase());
        po.setCreateTime(new Date());
        po.setIsSnapshot(0);
        // 将detail中所有学科考试的最早和最晚时间 设置为教师发布记录中的考试开始和结束时间
        //各科考试详情
        List<DiagnosisRecordDetailDto> detailModels = JSONArray.parseArray(releaseExamDto.getDetailModels(),DiagnosisRecordDetailDto.class);
        //取集合中开考时间最小值
        Date minStartTime = detailModels.stream().min((o1, o2) -> {
            if (o1.getStartTime().before(o2.getStartTime())) {
                return -1;
            } else if (o1.getStartTime().after(o2.getStartTime())) {
                return 1;
            }
            return 0;
        }).get().getStartTime();
        //取集合中考试结束时间最大值
        Date maxEndTime = detailModels.stream().max((o1, o2) -> {
            if (o1.getEndTime().before(o2.getEndTime())) {
                return -1;
            } else if (o1.getEndTime().after(o2.getEndTime())) {
                return 1;
            }
            return 0;
        }).get().getEndTime();
        po.setStartTime(minStartTime);
        po.setEndTime(maxEndTime);
        diagnosisRecordTeacherService.save(po);

        //2 保存考试详情 每个学校每一科考试
        //需发布考试的学校
        List<SchoolDto> schools = JSONArray.parseArray(releaseExamDto.getSchools(),SchoolDto.class);

        List<DiagnosisClassRelationPo> diagnosisClassRelationPos = new ArrayList<>();
        List<Integer> classCodes = new ArrayList<>();
        for (SchoolDto school : schools) {
            List<AuthGroupBean> classes = authGroupService.getClassBySchoolGrade(Integer.parseInt(school.getSchoolCode()), releaseExamDto.getGradeCode());
            if(!CollectionUtils.isEmpty(classes)){
                classCodes.addAll(classes.stream().map(AuthGroupBean::getGroupId).collect(Collectors.toList()));
                DiagnosisRecordTeacherDto drtd = new DiagnosisRecordTeacherDto();
                drtd.setSchoolCode(Integer.parseInt(school.getSchoolCode()));
                drtd.setGradeCode(releaseExamDto.getGradeCode());
                List<AuthUserManagerConditionBean> teacherList = getAuthUserManagerConditionBeans(drtd);

                for (DiagnosisRecordDetailDto detailModel : detailModels) {
                    for (AuthUserManagerConditionBean teacher : teacherList) {
                        List<AuthClassBean> classBeanList = teacher.getClassBeanList();
                        for (AuthClassBean authClassBean : classBeanList) {
                            for (AuthGroupBean aClass : classes) {
                                if (authClassBean.getClassId().equals(aClass.getGroupId())
                                        && teacher.getUserSubjectIden().equals(detailModel.getSubjectCode().toString())) {
                                    if((releaseExamDto.getGradeCode().equals(32) || releaseExamDto.getGradeCode().equals(33))
                                            && teacher.getUserSubjectIden().equals("2")){
                                        if(detailModel.getArtType().equals(aClass.getGroupArt())){
                                            DiagnosisClassRelationPo dc = getDiagnosisClassRelationPo(po, school, detailModel, teacher, aClass);
                                            diagnosisClassRelationPos.add(dc);
                                        }
                                    }else {
                                        DiagnosisClassRelationPo dc = getDiagnosisClassRelationPo(po, school, detailModel, teacher, aClass);
                                        diagnosisClassRelationPos.add(dc);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if(!CollectionUtils.isEmpty(diagnosisClassRelationPos)){
            diagnosisClassRelationService.saveList(diagnosisClassRelationPos);
        }

        Map params = new HashMap();
        params.put("classCodes", classCodes);
        releaseExamDto.setCode(po.getCode());
        params.put("reDto", JSONObject.toJSONString(releaseExamDto));
        DiagnosisEvent event = new DiagnosisEvent(new Object(), new EventSourceHandler(JSONObject.toJSONString(params), EventSourceEnum.NEW_SAVE_STUDENT_DIAGNOSIS_RECORDS));
        applicationContext.publishEvent(event);
        return po.getCode();
    }

    private DiagnosisClassRelationPo getDiagnosisClassRelationPo(DiagnosisRecordTeacherPo po, SchoolDto school, DiagnosisRecordDetailDto detailModel, AuthUserManagerConditionBean teacher, AuthGroupBean aClass) {
        DiagnosisClassRelationPo dc = new DiagnosisClassRelationPo();
        dc.setCreateTime(new Date());
        dc.setClassCode(aClass.getGroupId());
        dc.setClassName(aClass.getGroupName());
        dc.setDiagnosisRecordCode(po.getCode());
        dc.setSchoolCode(Integer.parseInt(school.getSchoolCode()));
        dc.setSchoolName(school.getSchoolName());
        dc.setSubjectCode(detailModel.getSubjectCode());
        dc.setStartTime(detailModel.getStartTime());
        dc.setEndTime(detailModel.getEndTime());
        dc.setArtType(detailModel.getArtType());
        dc.setDiagnosisPaperCode(detailModel.getDiagnosisPaperCode());
        dc.setDiagnosisPaperName(detailModel.getDiagnosisPaperName());
        dc.setTeacherCode(teacher.getUserId());
        dc.setTeacherName(teacher.getUserName());
        dc.setHasReport(0);
        dc.setIsRead(0);
        return dc;
    }


    @Override
    public PageInfo<DiagnosisRecordTeacherDto> getDiagnosisListBySubject(DiagnosisRecordTeacherDto drtd, Integer pageNum, Integer pageSize, Order order)throws Exception {
        PageHelper.startPage(pageNum, pageSize);
        List<DiagnosisRecordTeacherPo> pos = diagnosisRecordTeacherService.getDiagnosisListBySubject(JSON.parseObject(JSONObject.toJSONString(drtd), Map.class), order);
        PageInfo<DiagnosisRecordTeacherPo> pageInfo = new PageInfo<>(pos);
        PageInfo<DiagnosisRecordTeacherDto> page = PageHelperUtil.pageInfoConverter(pageInfo, DiagnosisRecordTeacherDto.class);
        return page;
    }

    @Override
    public PageInfo<DiagnosisRecordTeacherDto> getDiagnosisList(DiagnosisRecordTeacherDto drtd, Integer pageNum, Integer pageSize, Order order) throws Exception {
        PageHelper.startPage(pageNum, pageSize);
        List<DiagnosisRecordTeacherPo> pos = diagnosisRecordTeacherService.findByCondition(JSON.parseObject(JSONObject.toJSONString(drtd), Map.class), order);
        PageInfo<DiagnosisRecordTeacherPo> pageInfo = new PageInfo<>(pos);
        PageInfo<DiagnosisRecordTeacherDto> page = PageHelperUtil.pageInfoConverter(pageInfo, DiagnosisRecordTeacherDto.class);
        return page;
    }

    @Override
    public List<DiagnosisRecordTeacherDto> getAll(DiagnosisRecordTeacherDto drtd, Order order) throws Exception {
        List<DiagnosisRecordTeacherPo> pos = diagnosisRecordTeacherService.findByCondition(JSON.parseObject(JSONObject.toJSONString(drtd), Map.class), order);
        List<DiagnosisRecordTeacherDto> diagnosisRecordTeacherDtos = PageHelperUtil.converterList(pos, DiagnosisRecordTeacherDto.class);
        return diagnosisRecordTeacherDtos;
    }

    @Override
    public boolean update(DiagnosisRecordTeacherDto dto) throws Exception {
        DiagnosisRecordTeacherPo po = new DiagnosisRecordTeacherPo();
        BeanUtils.copyProperties(dto, po);
        diagnosisRecordTeacherService.update(po);
        return true;
    }

    @Override
    public PageInfo<DiagnosisRecordTeacherDto> getDiagnosisListWithReportCount(DiagnosisRecordTeacherDto dto, Integer pageNum, Integer pageSize, Order order) throws Exception {
        PageHelper.startPage(pageNum, pageSize);
        List<DiagnosisRecordTeacherPo> pos = diagnosisRecordTeacherService.getDiagnosisListWithReportCount(JSON.parseObject(JSONObject.toJSONString(dto), Map.class), order);
        PageInfo<DiagnosisRecordTeacherPo> pageInfo = new PageInfo<>(pos);
        PageInfo<DiagnosisRecordTeacherDto> page = PageHelperUtil.pageInfoConverter(pageInfo, DiagnosisRecordTeacherDto.class);
        return page;
    }

    @Override
    public PageInfo<DiagnosisRecordTeacherDto> getDiagnosisListForMaster(DiagnosisRecordTeacherDto dto, Integer pageNum, Integer pageSize) throws Exception {
        PageHelper.startPage(pageNum, pageSize);
        List<DiagnosisRecordTeacherPo> pos = diagnosisRecordTeacherService.getDiagnosisListForMaster(JSON.parseObject(JSONObject.toJSONString(dto), Map.class));
        PageInfo<DiagnosisRecordTeacherPo> pageInfo = new PageInfo<>(pos);
        PageInfo<DiagnosisRecordTeacherDto> page = PageHelperUtil.pageInfoConverter(pageInfo, DiagnosisRecordTeacherDto.class);
        return page;
    }

    @Override
    public List<Map<String, Object>> getTeachingProgressByClasses(Integer gradeCode, List<String> unitCodes, Integer districtId, String semester) throws Exception {
        Map queryMap = new HashMap();
        queryMap.put("gradeCode", gradeCode);
        queryMap.put("unitCodes", unitCodes);
        queryMap.put("districtId", districtId);
        queryMap.put("semester", semester);
        List<Map<String, Object>> result = diagnosisRecordTeacherService.getTeachingProgressByClasses(queryMap);
        return result;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
