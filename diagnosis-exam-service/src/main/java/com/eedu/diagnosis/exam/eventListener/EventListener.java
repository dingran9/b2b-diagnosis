package com.eedu.diagnosis.exam.eventListener;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eedu.auth.beans.AuthGroupBean;
import com.eedu.auth.beans.AuthUserBean;
import com.eedu.auth.service.AuthGroupService;
import com.eedu.diagnosis.common.enumration.ArtTypeEnum;
import com.eedu.diagnosis.common.enumration.DiagnosisTypeEnum;
import com.eedu.diagnosis.common.enumration.EventSourceEnum;
import com.eedu.diagnosis.common.enumration.ExamTypeEnum;
import com.eedu.diagnosis.exam.api.dto.DiagnosisRecordDetailDto;
import com.eedu.diagnosis.exam.api.dto.ReleaseDiagnosisDto;
import com.eedu.diagnosis.exam.api.dto.SchoolDto;
import com.eedu.diagnosis.exam.api.enumeration.DiagnosisStausEnum;
import com.eedu.diagnosis.exam.api.enumeration.MarkPaperStatusEnum;
import com.eedu.diagnosis.exam.api.enumeration.PaperSourceEnum;
import com.eedu.diagnosis.exam.persist.po.DiagnosisRecordStudentPo;
import com.eedu.diagnosis.exam.persist.po.DiagnosisRecordTeacherPo;
import com.eedu.diagnosis.exam.service.DiagnosisRecordStudentService;
import com.eedu.diagnosis.paper.api.dto.DiagnosisComplexPaperRelationDto;
import com.eedu.diagnosis.paper.api.openService.BasePaperOpenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dqy on 2017/3/16.
 */

@Component
public class EventListener implements ApplicationListener<DiagnosisEvent> {
    Logger log = LoggerFactory.getLogger(EventListener.class);
    private static final int[] LIBERAL_SUBJECTS = {7, 8, 9};//文科独有学科
    private static final int[] SCIENCE_SUBJECTS = {4, 5, 6};//理科独有学科
    @Autowired
    private DiagnosisRecordStudentService diagnosisRecordStudentService;
    @Autowired
    private AuthGroupService authGroupService;
    @Autowired
    private BasePaperOpenService basePaperOpenService;

    @Async
    @Override
    public void onApplicationEvent(DiagnosisEvent diagnosisEvent) {
        EventSourceHandler source = diagnosisEvent.getSourceHandler();
        String jsonBody = source.getJsonBody();
        EventSourceEnum sourceEnum = source.getSource();
        switch (sourceEnum) {
            case SAVE_STUDENT_DIAGNOSIS_RECORDS:
                try {
                    Map map = JSONArray.parseObject(jsonBody, Map.class);
                    List<Integer> classCodes = (List<Integer>) map.get("classCodes");
                    String drtpJson = (String) map.get("drtd");
                    DiagnosisRecordTeacherPo drtd = JSONObject.parseObject(drtpJson, DiagnosisRecordTeacherPo.class);
                    //调用组织权限接口获取学生list
                    List<AuthUserBean> students = authGroupService.getStudentByClassLists(classCodes);
                    List<AuthGroupBean> classes = authGroupService.getGroupByIds(classCodes);
                    Map<Integer, List<AuthGroupBean>> classMap = classes.stream().collect(Collectors.groupingBy(AuthGroupBean::getGroupId));
                    List<DiagnosisRecordStudentPo> drsp = getDiagnosisRecordStudentPos(drtd, students,classMap);
                    diagnosisRecordStudentService.saveList(drsp);
                } catch (Exception e) {
                    //保存失败，补偿措施。
                    log.error("EventListener  error" + e.getMessage());
                }
                break;
            case NEW_SAVE_STUDENT_DIAGNOSIS_RECORDS:
                try {
                    Map map = JSONArray.parseObject(jsonBody, Map.class);
                    List<Integer> classCodes = (List<Integer>) map.get("classCodes");
                    String reDtoJson = (String) map.get("reDto");
                    ReleaseDiagnosisDto releaseDiagnosisDto = JSONObject.parseObject(reDtoJson, ReleaseDiagnosisDto.class);
                    List<AuthUserBean> students = authGroupService.getStudentByClassLists(classCodes);
                    Map<Integer, List<AuthUserBean>> studentMap = students.stream().collect(Collectors.groupingBy(AuthUserBean::getSchoolId));
                    List<AuthGroupBean> classes = authGroupService.getGroupByIds(classCodes);
                    Map<Integer, List<AuthGroupBean>> classMap = classes.stream().collect(Collectors.groupingBy(AuthGroupBean::getGroupId));
                    List<SchoolDto> schools = JSONArray.parseArray(releaseDiagnosisDto.getSchools(), SchoolDto.class);
                    //各科考试详情
                    List<DiagnosisRecordDetailDto> detailModels = JSONArray.parseArray(releaseDiagnosisDto.getDetailModels(), DiagnosisRecordDetailDto.class);
                    List<DiagnosisRecordStudentPo> drsp = new ArrayList<>();
                    for (SchoolDto school : schools) {
                        List<AuthUserBean> authUserBeans = studentMap.get(Integer.parseInt(school.getSchoolCode()));
                        for (AuthUserBean authUserBean : authUserBeans) {
                            for (DiagnosisRecordDetailDto detailModel : detailModels) {
                                if((releaseDiagnosisDto.getGradeCode().equals(32) || releaseDiagnosisDto.getGradeCode().equals(32))
                                        && detailModel.getSubjectCode().equals(2)){
                                    if (detailModel.getArtType().equals(authUserBean.getArtType())) {
                                        drsp.add(getStudentPo(releaseDiagnosisDto, classMap, school, authUserBean, detailModel));
                                    }
                                }else {
                                    drsp.add(getStudentPo(releaseDiagnosisDto, classMap, school, authUserBean, detailModel));
                                }
                            }
                        }
                    }
                    diagnosisRecordStudentService.saveList(drsp);
                } catch (Exception e) {
                    //保存失败，补偿措施。
                    log.error("EventListener  error" + e.getMessage());
                }
        }
    }

    private DiagnosisRecordStudentPo getStudentPo(ReleaseDiagnosisDto releaseDiagnosisDto, Map<Integer, List<AuthGroupBean>> classMap,
                                                        SchoolDto school, AuthUserBean authUserBean, DiagnosisRecordDetailDto detailModel) throws Exception{
        DiagnosisRecordStudentPo po = new DiagnosisRecordStudentPo();
        po.setDiagnosisTeacherRecordCode(releaseDiagnosisDto.getCode());
        po.setSchoolCode(Integer.parseInt(school.getSchoolCode()));
        po.setSchoolName(school.getSchoolName());
        po.setClassCode(authUserBean.getClassId());
        po.setClassName(classMap.get(authUserBean.getClassId()).get(0).getGroupName());
        po.setStudentCode(authUserBean.getUserId());
        po.setStudentName(authUserBean.getUserName());
        po.setArtType(authUserBean.getArtType());
        po.setDiagnosisName(releaseDiagnosisDto.getDiagnosisName());
        po.setGradeCode(releaseDiagnosisDto.getGradeCode());
        po.setStageCode(releaseDiagnosisDto.getStageCode());
        po.setSubjectCode(detailModel.getSubjectCode());
        po.setStartTime(detailModel.getStartTime());
        po.setEndTime(detailModel.getEndTime());
        po.setDiagnosisPaperName(detailModel.getDiagnosisPaperName());
        po.setDiagnosisPaperCode(detailModel.getDiagnosisPaperCode());
        po.setExamType(releaseDiagnosisDto.getExamType());
        po.setDiagnosisStatus(DiagnosisStausEnum.RELEASE.getValue());
        po.setDiagnosisType(releaseDiagnosisDto.getDiagnosisType());
        po.setMarkStatus(MarkPaperStatusEnum.NOTMARK.getValue());
        return po;
    }

    private List<DiagnosisRecordStudentPo> getDiagnosisRecordStudentPos(DiagnosisRecordTeacherPo drtd, List<AuthUserBean> students,Map<Integer, List<AuthGroupBean>> classMap) throws Exception {
        List<DiagnosisRecordStudentPo> drsp = new ArrayList<>();
        //如果是全科
        if (!ExamTypeEnum.UNIT.getCode().equals(drtd.getExamType())) {
            DiagnosisComplexPaperRelationDto dcpr = new DiagnosisComplexPaperRelationDto();
            dcpr.setComplexPaperCode(drtd.getDiagnosisPaperCode());
            List<DiagnosisComplexPaperRelationDto> diagnosisComplexPaperRelationList = basePaperOpenService.getDiagnosisComplexPaperRelationList(dcpr);
            if (!diagnosisComplexPaperRelationList.isEmpty()) {
                for (AuthUserBean authUserBean : students) {
                    for (DiagnosisComplexPaperRelationDto dto : diagnosisComplexPaperRelationList) {
                        DiagnosisRecordStudentPo po = new DiagnosisRecordStudentPo();
                        po.setDiagnosisTeacherRecordCode(drtd.getCode());
                        po.setSchoolCode(drtd.getSchoolCode());
                        po.setSchoolName(drtd.getSchoolName());
                        po.setClassCode(authUserBean.getClassId());
                        po.setClassName(classMap.get(authUserBean.getClassId()).get(0).getGroupName());
                        po.setStudentCode(authUserBean.getUserId());
                        po.setStudentName(authUserBean.getUserName());
                        po.setArtType(authUserBean.getArtType());
                        po.setDiagnosisName(drtd.getDiagnosisName());
                        po.setGradeCode(drtd.getGradeCode());
                        po.setStageCode(drtd.getStageCode());
                        po.setStartTime(drtd.getStartTime());
                        po.setEndTime(drtd.getEndTime());
                        po.setExamType(drtd.getExamType());
                        po.setResourceType(PaperSourceEnum.DIAGNOSIS.getValue());
                        po.setDiagnosisStatus(DiagnosisStausEnum.RELEASE.getValue());
                        po.setDiagnosisType(DiagnosisTypeEnum.COMPLEX.getValue());
                        po.setMarkStatus(MarkPaperStatusEnum.NOTMARK.getValue());
                        if (32 == drtd.getGradeCode() || 33 == drtd.getGradeCode()) {//高二 高三 分文理  数学卷也分文理科
                            po = formatPoSubjectAndPaper(po, authUserBean, dto);
                            if (null == po) continue;
                        } else {
                            po = formatData(po, dto);
                        }
                        drsp.add(po);
                    }
                }
            }

        } else {//单科的直接分配
            students.forEach(authUserBean -> {
                DiagnosisRecordStudentPo po = new DiagnosisRecordStudentPo();
                po.setDiagnosisTeacherRecordCode(drtd.getCode());
                po.setSchoolCode(drtd.getSchoolCode());
                po.setSchoolName(drtd.getSchoolName());
                po.setClassCode(authUserBean.getClassId());
                po.setClassName(classMap.get(authUserBean.getClassId()).get(0).getGroupName());
                po.setStudentCode(authUserBean.getUserId());
                po.setStudentName(authUserBean.getUserName());
                po.setDiagnosisPaperCode(drtd.getDiagnosisPaperCode());
                po.setDiagnosisPaperName(drtd.getDiagnosisPaperName());
                po.setDiagnosisName(drtd.getDiagnosisName());
                po.setGradeCode(drtd.getGradeCode());
                po.setSubjectCode(drtd.getSubjectCode());
                po.setStageCode(drtd.getStageCode());
                po.setStartTime(drtd.getStartTime());
                po.setEndTime(drtd.getEndTime());
                po.setExamType(drtd.getExamType());
                po.setDiagnosisStatus(DiagnosisStausEnum.RELEASE.getValue());
                po.setResourceType(PaperSourceEnum.HOMEWORK.getValue());
                po.setDiagnosisType(DiagnosisTypeEnum.SINGLE.getValue());
                po.setMarkStatus(MarkPaperStatusEnum.NOTMARK.getValue());
                po.setArtType(authUserBean.getArtType());
                po.setCreateTime(new Date());
                drsp.add(po);
            });
        }

        return drsp;
    }

    private DiagnosisRecordStudentPo formatPoSubjectAndPaper(DiagnosisRecordStudentPo po, AuthUserBean authUserBean, DiagnosisComplexPaperRelationDto dto) {
        //如果是语文或者英语 直接分配
        if (1 == dto.getSubjectCode() || 3 == dto.getSubjectCode()) {
            return formatData(po, dto);
        }
        //高二高三的 理科生
        if (ArtTypeEnum.SCIENCE.getValue() == authUserBean.getArtType()) {
            //将数学的理科卷分配给该生
            if ((2 == dto.getSubjectCode() && ArtTypeEnum.SCIENCE.getValue() == dto.getArtsType())
                    || Arrays.asList(SCIENCE_SUBJECTS).contains(dto.getSubjectCode())) {
                po = formatData(po, dto);
            } else {
                return null;
            }
        }
        //文科生
        else if (ArtTypeEnum.LIBERAL.getValue() == authUserBean.getArtType()) {
            //将数学的文科卷分配给该生
            if ((2 == dto.getSubjectCode() && ArtTypeEnum.LIBERAL.getValue() == dto.getArtsType())
                    || Arrays.asList(LIBERAL_SUBJECTS).contains(dto.getSubjectCode())) {
                po = formatData(po, dto);
            } else {
                return null;
            }
        } else {
            return null;
        }
        return po;
    }

    private DiagnosisRecordStudentPo formatData(DiagnosisRecordStudentPo po, DiagnosisComplexPaperRelationDto dto) {
        po.setDiagnosisPaperCode(dto.getDiagnosisPaperCode());
        po.setDiagnosisPaperName(dto.getDiagnosisPaperName());
        po.setSubjectCode(dto.getSubjectCode());
        return po;
    }
}
