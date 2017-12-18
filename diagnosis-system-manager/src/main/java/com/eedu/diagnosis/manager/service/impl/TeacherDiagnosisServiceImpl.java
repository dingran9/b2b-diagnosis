package com.eedu.diagnosis.manager.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.eedu.auth.beans.AuthGroupBean;
import com.eedu.auth.beans.AuthUserManagerBean;
import com.eedu.auth.service.AuthGroupService;
import com.eedu.auth.service.AuthUserManagerService;
import com.eedu.diagnosis.common.enumration.DiagnosisTypeEnum;
import com.eedu.diagnosis.common.enumration.ExamScopeEnum;
import com.eedu.diagnosis.common.enumration.ExamTypeEnum;
import com.eedu.diagnosis.common.exception.DiagnosisException;
import com.eedu.diagnosis.common.exception.exceptionCode.ExceptionCodeEnum;
import com.eedu.diagnosis.common.model.paperEntity.BigQuestionSystem;
import com.eedu.diagnosis.common.model.paperEntity.PaperSystem;
import com.eedu.diagnosis.common.model.paperEntity.QuestionSet;
import com.eedu.diagnosis.common.model.paperEntity.SmallQuestionSystem;
import com.eedu.diagnosis.common.service.redisService.RedisClientTemplate;
import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.common.utils.PageHelperUtil;
import com.eedu.diagnosis.common.utils.UIDGenerator;
import com.eedu.diagnosis.exam.api.dto.DiagnosisClassRelationDto;
import com.eedu.diagnosis.exam.api.dto.DiagnosisRecordStudentDto;
import com.eedu.diagnosis.exam.api.dto.DiagnosisRecordTeacherDto;
import com.eedu.diagnosis.exam.api.dto.ReleaseDiagnosisDto;
import com.eedu.diagnosis.exam.api.openService.DiagnosisClassRelationOpenService;
import com.eedu.diagnosis.exam.api.openService.DiagnosisRecordStudentOpenService;
import com.eedu.diagnosis.exam.api.openService.DiagnosisRecordTeacherOpenService;
import com.eedu.diagnosis.manager.model.request.*;
import com.eedu.diagnosis.manager.model.response.DiagnosisClassRelationModel;
import com.eedu.diagnosis.manager.model.response.DiagnosisRecordTeacherModel;
import com.eedu.diagnosis.manager.responsecode.ResponseCode;
import com.eedu.diagnosis.manager.service.TeacherDiagnosisService;
import com.eedu.diagnosis.paper.api.dto.DiagnosisComplexPaperDto;
import com.eedu.diagnosis.paper.api.dto.DiagnosisComplexPaperRelationDto;
import com.eedu.diagnosis.paper.api.dto.DiagnosisPaperDto;
import com.eedu.diagnosis.paper.api.openService.BasePaperOpenService;
import com.eedu.diagnosis.paper.api.openService.ResourceOpenService;
import com.eeduspace.b2b.report.model.ReleaseExamDto;
import com.eeduspace.b2b.report.service.B2BReportOpenService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by dqy on 2017/3/20.
 */
@Service
public class TeacherDiagnosisServiceImpl implements TeacherDiagnosisService {
    @Autowired
    private DiagnosisRecordTeacherOpenService diagnosisRecordTeacherOpenService;
    @Autowired
    private DiagnosisRecordStudentOpenService diagnosisRecordStudentOpenService;
    @Autowired
    private DiagnosisClassRelationOpenService diagnosisClassRelationOpenService;
    @Autowired
    private RedisClientTemplate redisClientTemplate;
    @Autowired
    private BasePaperOpenService basePaperOpenService;
    @Autowired
    private ResourceOpenService resourceOpenService;
    @Autowired
    private AuthGroupService authGroupService;
    @Autowired
    private B2BReportOpenService b2BReportOpenService;
    @Autowired
    private AuthUserManagerService authUserManagerService;
    @Autowired
    private ThreadPoolTaskExecutor executor;

    @Override
    public boolean releaseSingleDiagnosis(DiagnosisReleaseModle model) throws Exception {
        Integer[] examTypes = {1, 2, 3, 4, 5, 6};
        List<DiagnosisComplexPaperRelationDto> diagnosisComplexPaperRelationList = null;
        DiagnosisRecordTeacherDto dto = new DiagnosisRecordTeacherDto();
        BeanUtils.copyProperties(model, dto);
        //单元测
        if (ExamTypeEnum.UNIT.getCode().equals(model.getExamType())) {
            DiagnosisPaperDto dbdto = new DiagnosisPaperDto();
            dbdto.setCode(model.getDiagnosisPaperCode());
            List<DiagnosisPaperDto> diagnosisPaperList = basePaperOpenService.getDiagnosisPaperList(dbdto);
            if (diagnosisPaperList == null || diagnosisPaperList.isEmpty()) {
                throw new DiagnosisException(ExceptionCodeEnum.RESOURCE_NOTFOUND, "试卷无效");
            }
            dto.setExamScope(ExamScopeEnum.CLASS.getValue());
            dto.setDiagnosisType(DiagnosisTypeEnum.SINGLE.getValue());
        } else if (Arrays.asList(examTypes).contains(model.getExamType())) {
            DiagnosisComplexPaperRelationDto dcpr = new DiagnosisComplexPaperRelationDto();
            dcpr.setComplexPaperCode(model.getDiagnosisPaperCode());
            diagnosisComplexPaperRelationList = basePaperOpenService.getDiagnosisComplexPaperRelationList(dcpr);
            if (diagnosisComplexPaperRelationList == null || diagnosisComplexPaperRelationList.isEmpty()) {
                throw new DiagnosisException(ExceptionCodeEnum.RESOURCE_NOTFOUND, "全科卷无效");
            }

            List<Integer> subjects = new ArrayList<>();
            diagnosisComplexPaperRelationList.forEach(diagnosisComplexPaperRelationDto -> {
                subjects.add(diagnosisComplexPaperRelationDto.getSubjectCode());
            });
            boolean b = authGroupService.subjectNoClass(model.getSchoolCode(), model.getGradeId(), model.getGradeCode(), subjects.toArray(new Integer[]{}));
            if (!b) {
                throw new DiagnosisException(ResponseCode.RESOURCE_IMPERFECT.toString(), "该年级尚有班级没有相关学科的任课老师，请完善信息。");
            }
            dto.setExamScope(ExamScopeEnum.SCHOOL.getValue());
            dto.setDiagnosisType(DiagnosisTypeEnum.COMPLEX.getValue());
            List<DiagnosisRecordTeacherDto> list = diagnosisRecordTeacherOpenService.getAll(dto, null);
            if (!CollectionUtils.isEmpty(list)) {
                throw new DiagnosisException(ExceptionCodeEnum.RESOURCE_DUPLICATE.toString(), "全科卷重复发布");
            }
        }
        String examYear = getExamYear();
        dto.setExamYear(examYear);//当前年份的下半学期
        String releaseCode = diagnosisRecordTeacherOpenService.release(dto);

        if (null != releaseCode) {
            //如果是全科考试 发布成功后 修改全科试卷的发布状态
            if (!ExamTypeEnum.UNIT.getCode().equals(model.getExamType())) {
                List<DiagnosisComplexPaperDto> list = new ArrayList<>();
                DiagnosisComplexPaperDto diagnosisComplexPaperDto = new DiagnosisComplexPaperDto();
                diagnosisComplexPaperDto.setCode(model.getDiagnosisPaperCode());
                diagnosisComplexPaperDto.setIsRelease(1);
                list.add(diagnosisComplexPaperDto);
                basePaperOpenService.updateDiagnosisComplexPaperList(list);
            }

            //报告系统 生成该次考试记录
            ReleaseExamDto red = getReleaseExamDto(model, diagnosisComplexPaperRelationList, dto, releaseCode);
            return b2BReportOpenService.releaseExam(red);
        }
        return false;
    }

    private ReleaseExamDto getReleaseExamDto(DiagnosisReleaseModle model, List<DiagnosisComplexPaperRelationDto> diagnosisComplexPaperRelationList, DiagnosisRecordTeacherDto dto, String releaseCode) {
        ReleaseExamDto red = new ReleaseExamDto();
        red.setExamType(model.getExamType().toString());
        red.setReleaseCode(releaseCode);
        red.setReleaseName(model.getDiagnosisName());
        red.setSemester(dto.getExamYear());
        if (ExamTypeEnum.UNIT.getCode().equals(model.getExamType())) {
            red.setTotalScore(model.getTotalScore().doubleValue());
        } else {//全科卷时
            Map<Integer, Double> scores = new HashMap();
            //全科试卷可能出现文理两套数学试卷的情况，所以先用map过滤一层
            for (DiagnosisComplexPaperRelationDto de : diagnosisComplexPaperRelationList) {
                scores.put(de.getSubjectCode(), de.getTotalScore());
            }
            Double totalScore = 0d;
            for (Double score : scores.values()) {
                totalScore += score;
            }
            red.setTotalScore(totalScore);
        }
        return red;
    }

    private String getExamYear() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        String examYear;
        if (2 <= month && month < 9) {//如果月份为2-9月份 考试年为来年的下学期
            examYear = cal.get(Calendar.YEAR) + "2";
        } else {
            examYear = cal.get(Calendar.YEAR) + 1 + "1";
        }
        return examYear;
    }

    /**
     * 通过教师code集合获取测试记录(作业记录)
     */
    @Override
    public Map<String, Object> getTeacherDiagnosisList(TeacherModel model) throws Exception {
        Map<String, Object> pageResult = new HashMap<>();
        DiagnosisRecordTeacherDto dto = new DiagnosisRecordTeacherDto();
        dto.setTeacherCode(model.getTeacherCode());
        dto.setDiagnosisType(DiagnosisTypeEnum.SINGLE.getValue());
        Order order = new Order("create_time", Order.Direction.desc);
        PageInfo<DiagnosisRecordTeacherDto> pageInfo = diagnosisRecordTeacherOpenService.getDiagnosisList(dto, model.getPageNum(), model.getPageSize(), order);
        List<DiagnosisRecordTeacherModel> diagnosisRecordTeacherModels = PageHelperUtil.converterList(pageInfo.getList(), DiagnosisRecordTeacherModel.class);
        if (!diagnosisRecordTeacherModels.isEmpty()) {
            pageResult.put("totalPage", pageInfo.getPages());
            pageResult.put("total", pageInfo.getTotal());
            pageResult.put("currPage", pageInfo.getPageNum());
            pageResult.put("list", diagnosisRecordTeacherModels);
            return pageResult;
        }
        return null;
    }

    @Override
    public String saveClassworkQuestionsToPaper(ClassworkModel model) throws Exception {
        PaperSystem paper = new PaperSystem();
        paper.setId(UIDGenerator.getShortUUID());
        paper.setGradeCode(model.getGradeCode().toString());
        paper.setSubjectCode(model.getSubjectCode().toString());
        paper.setSchoolYear(model.getStageCode().toString());

        //将前端传回的小题list封装成试卷试题
        List<SmallQuestionSystem> smallQuestionSystems = model.getSmallQuestionSystems();
        BigQuestionSystem bq = new BigQuestionSystem();
        List<BigQuestionSystem> bqList = new ArrayList<>();
        bq.setId(UIDGenerator.getShortUUID());
        bq.setItemContent("单选题");
        bq.setItemType("1");
        bq.setList(smallQuestionSystems);
        bqList.add(bq);

        QuestionSet qs = new QuestionSet();
        qs.setType("1");
        qs.setTypeList(bqList);
        List<QuestionSet> questionSets = new ArrayList<>();
        questionSets.add(qs);

        paper.setPaperSystemQusetionType(questionSets);
        //将组装好的试卷 缓存进redis
        redisClientTemplate.set("repositoryPaper_" + paper.getId(), JSONObject.toJSONString(paper));


        String classworkPaperCode = saveDiagnosisPaper(model, paper.getId());
        return classworkPaperCode;
    }

    private String saveDiagnosisPaper(ClassworkModel model, String resourcePaperCode) throws Exception {
        DiagnosisPaperDto dto = new DiagnosisPaperDto();
        dto.setCode(UIDGenerator.getShortUUID());
        dto.setCreateTime(new Date());
        dto.setGradeCode(model.getGradeCode());
        dto.setStageCode(model.getStageCode());
        dto.setOperator(model.getOperator());
        dto.setResourcePaperCode(resourcePaperCode);
        dto.setResourceType(1);
        dto.setSubjectCode(model.getSubjectCode());

        return basePaperOpenService.saveDiagnosisPaper(dto);
    }

    @Override
    public String saveClassworkPaper(ClassworkModel model) throws Exception {
        String paperJson = redisClientTemplate.get("repositoryPaper_" + model.getCode());
        if (StringUtils.isBlank(paperJson)) {
            resourceOpenService.getResourcePaperInfo(model.getCode());
        }
        String s = saveDiagnosisPaper(model, model.getCode());
        return s;
    }

    @Override
    public List<Map<String, Object>> getDiagnosisDetail(TeacherModel model) throws Exception {
        List<Map<String, Object>> result = new ArrayList<>();
        Map map = null;
        DiagnosisRecordStudentDto drDto = null;
        List<Integer> classCodes = new ArrayList<>();

        DiagnosisClassRelationDto dto = new DiagnosisClassRelationDto();
        dto.setDiagnosisRecordCode(model.getCode());
        dto.setSubjectCode(model.getSubjectCode());
        dto.setClassCodes(model.getClassCodes());
        List<DiagnosisClassRelationDto> listByParam = diagnosisClassRelationOpenService.getListByParam(dto);
        listByParam.forEach((diagnosisClassRelationDto) -> {
            model.getClassCodes().forEach(classCode -> {
                if (classCode.equals(diagnosisClassRelationDto.getClassCode())
                        && !classCodes.contains(diagnosisClassRelationDto.getClassCode())) {
                    classCodes.add(diagnosisClassRelationDto.getClassCode());
                }
            });
        });
        double total = 0d;
        Double totalScore = 0d;
        drDto = new DiagnosisRecordStudentDto();
        drDto.setDiagnosisTeacherRecordCode(model.getCode());
        drDto.setSubjectCode(model.getSubjectCode());
        List<DiagnosisRecordStudentDto> all = diagnosisRecordStudentOpenService.getAll(drDto, null);
        if (!all.isEmpty()) {
            DiagnosisPaperDto dbpd = new DiagnosisPaperDto();
            dbpd.setCode(all.get(0).getDiagnosisPaperCode());
            List<DiagnosisPaperDto> diagnosisPaperList = basePaperOpenService.getDiagnosisPaperList(dbpd);
            if (!CollectionUtils.isEmpty(diagnosisPaperList)) {
                totalScore = diagnosisPaperList.get(0).getTotalScore() == null ? 100d : diagnosisPaperList.get(0).getTotalScore();
            }
        }

        if (!classCodes.isEmpty()) {
            boolean b = false;
            int classCount = 0;
            for (Integer classCode : classCodes) {
                map = new HashMap();

                AuthGroupBean groupInfo = authGroupService.getGroupInfoById(classCode);
                if (null != groupInfo) map.put("className", groupInfo.getGroupName());

                drDto = new DiagnosisRecordStudentDto();
//                drDto.setGradeCode(model.getGradeCode());
                drDto.setSubjectCode(model.getSubjectCode());
                drDto.setClassCode(classCode);
                drDto.setDiagnosisTeacherRecordCode(model.getCode());
                Map<String, Object> data = diagnosisRecordStudentOpenService.getClassAverage(drDto);
                long hasExamCount = (long) data.get("hasExamCount");
                if (0 < hasExamCount) {
                    total += ((BigDecimal) data.get("avgScore")).doubleValue();

                    map.put("average", ((BigDecimal) data.get("avgScore")).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    map.put("hasReport", true);
                    b = true;
                    classCount += 1;
                } else {
                    map.put("average", 0d);
                    map.put("hasReport", false);
                }

                map.put("totalScore", totalScore);
                map.put("isGradeAverage", false);
                map.put("classCode", classCode);
                map.put("diagnosisTeacherRecordCode", model.getCode());
                result.add(map);
            }

            //封装已读/未读状态
            listByParam.forEach((diagnosisClassRelationDto) -> {
                result.forEach((dataMap) -> {
                    if (diagnosisClassRelationDto.getClassCode().equals(dataMap.get("classCode"))) {
                        dataMap.put("isRead", diagnosisClassRelationDto.getIsRead());
                        dataMap.put("diagnosisClassRelationCode", diagnosisClassRelationDto.getCode());
                    }
                });
            });

            //按班级名称排序
            Collections.sort(result, new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    if ((int) o1.get("classCode") > (int) o2.get("classCode")) return 1;
                    if ((int) o1.get("classCode") == (int) o2.get("classCode")) return 0;
                    return -1;
                }
            });
            //班级总平均分
            map = new HashMap();
            if (classCount != 0) {
                double gradeAverage = total / classCount;
                BigDecimal bd = new BigDecimal(gradeAverage);
                map.put("average", bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            } else {
                map.put("average", 0d);
            }
            map.put("totalScore", totalScore);
            map.put("isGradeAverage", true);
            map.put("classCode", null);
            map.put("hasReport", b);
            map.put("diagnosisTeacherRecordCode", model.getCode());
            result.add(map);
            return result;
        }
        return null;
    }

    @Override
    public PaperSystem paperDetail(DiagnosisPaperModel model) throws Exception {
        DiagnosisPaperDto dto = new DiagnosisPaperDto();
        dto.setCode(model.getCode());
        List<DiagnosisPaperDto> diagnosisPaperList = basePaperOpenService.getDiagnosisPaperList(dto);
        if (null != diagnosisPaperList && !diagnosisPaperList.isEmpty()) {
            String paperJson = resourceOpenService.getResourcePaperInfo(diagnosisPaperList.get(0).getResourcePaperCode());
            return JSONObject.parseObject(paperJson, PaperSystem.class);
        }
        return null;
    }

    @Override
    public boolean updateReadStatus(String diagnosisClassRelationCode) throws Exception {
        DiagnosisClassRelationDto dto = new DiagnosisClassRelationDto();
        dto.setCode(diagnosisClassRelationCode);
        dto.setIsRead(1);
        diagnosisClassRelationOpenService.update(dto);
        return true;
    }

    @Override
    public PageInfo<DiagnosisRecordTeacherModel> getDiagnosisListForMaster(TeacherModel model) throws Exception {
        DiagnosisRecordTeacherDto dto = new DiagnosisRecordTeacherDto();
        BeanUtils.copyProperties(model, dto);
        if (null != model.getTeacherCode()) {
            dto.setClassCodes(null);
            dto.setGradeCode(null);
        }
        dto.setDiagnosisType(DiagnosisTypeEnum.COMPLEX.getValue());
        PageInfo<DiagnosisRecordTeacherDto> pageInfo = diagnosisRecordTeacherOpenService.getDiagnosisListForMaster(dto, model.getPageNum(), model.getPageSize());
        PageInfo<DiagnosisRecordTeacherModel> diagnosisRecordTeacherModelPageInfo = PageHelperUtil.pageInfoConverter(pageInfo, DiagnosisRecordTeacherModel.class);
        return diagnosisRecordTeacherModelPageInfo;
    }

    @Override
    public List<Integer> getSubjectsForExam(String code) throws Exception {
        DiagnosisComplexPaperDto dto = new DiagnosisComplexPaperDto();
        dto.setCode(code);
        List<DiagnosisComplexPaperDto> diagnosisComplexPaperAndRelationList = basePaperOpenService.getDiagnosisComplexPaperAndRelationList(dto);
        List<Integer> subjects = new ArrayList<>();
        if (diagnosisComplexPaperAndRelationList != null && !diagnosisComplexPaperAndRelationList.get(0).getDiagnosisComplexPaperRelationDtoList().isEmpty()) {
            for (DiagnosisComplexPaperRelationDto dto1 : diagnosisComplexPaperAndRelationList.get(0).getDiagnosisComplexPaperRelationDtoList()) {
                if (dto1.getSubjectCode() != null && !subjects.contains(dto1.getSubjectCode())) {
                    subjects.add(dto1.getSubjectCode());
                }
            }
            if (!subjects.isEmpty()) {
                Collections.sort(subjects);
            }
        }
        return subjects;
    }

    @Override
    public Map<String, Object> getDiagnosisList(TeacherModel model) throws Exception {
        Map<String, Object> pageResult = new HashMap<>();
        DiagnosisClassRelationDto dcdto = new DiagnosisClassRelationDto();
        dcdto.setClassCodes(model.getClassCodes());
        dcdto.setSchoolCode(model.getSchoolCode());
        dcdto.setTeacherCode(model.getTeacherCode());
        dcdto.setIsRead(1);
        List<DiagnosisClassRelationDto> dclist = diagnosisClassRelationOpenService.getListByParam(dcdto);
        if (null != dclist && !dclist.isEmpty()) {
            List<String> recordCodes = new ArrayList<>();
            dclist.forEach(dto -> {
                if (!recordCodes.contains(dto.getDiagnosisRecordCode()))
                    recordCodes.add(dto.getDiagnosisRecordCode());
            });
            DiagnosisRecordTeacherDto dto = new DiagnosisRecordTeacherDto();
            BeanUtils.copyProperties(model, dto);
            dto.setCodes(recordCodes);
            dto.setSubjectCode(model.getSubjectCode());
            dto.setDiagnosisType(DiagnosisTypeEnum.SINGLE.getValue());
            Order order = new Order("create_time", Order.Direction.desc);
            PageInfo<DiagnosisRecordTeacherDto> pageInfo = diagnosisRecordTeacherOpenService.getDiagnosisListWithReportCount(dto, model.getPageNum(), model.getPageSize(), order);
            List<DiagnosisRecordTeacherModel> diagnosisRecordTeacherModels = PageHelperUtil.converterList(pageInfo.getList(), DiagnosisRecordTeacherModel.class);
            if (!diagnosisRecordTeacherModels.isEmpty()) {
                pageResult.put("totalPage", pageInfo.getPages());
                pageResult.put("total", pageInfo.getTotal());
                pageResult.put("currPage", pageInfo.getPageNum());
                pageResult.put("list", diagnosisRecordTeacherModels);
                return pageResult;
            }
        }
        return null;
    }

    @Override
    public Map<String, Object> getTeacherDiagnosisListV2(TeacherModel model) throws Exception {
        Map<String, Object> pageResult = new HashMap<>();
        PageInfo<DiagnosisRecordTeacherDto> pageInfo = null;
        List<DiagnosisRecordTeacherModel> diagnosisRecordTeacherModels = new ArrayList<>();
        //判断是否为主科教师
        AuthUserManagerBean condition = new AuthUserManagerBean();
        condition.setUserId(model.getTeacherCode());
        AuthUserManagerBean teacherInfo = authUserManagerService.getTeacherInfo(condition);
        List<String> subjectCodes = Arrays.asList(teacherInfo.getUserSubjects());
        boolean contains = subjectCodes.contains(model.getSubjectCode().toString());

        DiagnosisClassRelationDto dcdto = new DiagnosisClassRelationDto();
        if (contains) {
            //只获取教师当前所教班级里自己发布的考试列表
//            dcdto.setClassCodes(model.getClassCodes());
            dcdto.setSchoolCode(model.getSchoolCode());
            dcdto.setTeacherCode(model.getTeacherCode());
        } else {
            dcdto.setClassCodes(model.getClassCodes());
            dcdto.setSchoolCode(model.getSchoolCode());
        }
        List<DiagnosisClassRelationDto> dclist = diagnosisClassRelationOpenService.getListByParam(dcdto);

        if (null != dclist && !dclist.isEmpty()) {
            List<String> recordCodes = new ArrayList<>();
            dclist.forEach(diagnosisClassRelationDto -> {
                if (!recordCodes.contains(diagnosisClassRelationDto.getDiagnosisRecordCode()))
                    recordCodes.add(diagnosisClassRelationDto.getDiagnosisRecordCode());
            });
            DiagnosisRecordTeacherDto diagnosisRecordTeacherDto = new DiagnosisRecordTeacherDto();
            diagnosisRecordTeacherDto.setCodes(recordCodes);
            diagnosisRecordTeacherDto.setExamYear(model.getExamYear());
            diagnosisRecordTeacherDto.setSubjectCode(model.getSubjectCode());
            diagnosisRecordTeacherDto.setExamType(ExamTypeEnum.UNIT.getCode());
            Order order = new Order("create_time", Order.Direction.desc);
            pageInfo = diagnosisRecordTeacherOpenService.getDiagnosisListWithReportCount(diagnosisRecordTeacherDto, model.getPageNum(), model.getPageSize(), order);
        }
        if (pageInfo != null && !pageInfo.getList().isEmpty()) {
            diagnosisRecordTeacherModels = PageHelperUtil.converterList(pageInfo.getList(), DiagnosisRecordTeacherModel.class);
            pageResult.put("totalPage", pageInfo.getPages());
            pageResult.put("total", pageInfo.getTotal());
        } else {
            pageResult.put("totalPage", 0);
            pageResult.put("total", 0);
        }
        pageResult.put("currPage", model.getPageNum());
        pageResult.put("list", diagnosisRecordTeacherModels);
        return pageResult;
    }

    @Override
    public PageInfo<DiagnosisRecordTeacherModel> getDiagnosisHistoryListForMaster(TeacherModel model) throws Exception {
        DiagnosisRecordTeacherDto dto = new DiagnosisRecordTeacherDto();
        dto.setTeacherCode(model.getTeacherCode());
        dto.setIsRead(1);
        dto.setClassCode(model.getClassCode());
        dto.setSchoolCode(model.getSchoolCode());
        dto.setSubjectCode(model.getSubjectCode());
        dto.setDiagnosisType(DiagnosisTypeEnum.COMPLEX.getValue());
        PageInfo<DiagnosisRecordTeacherDto> pageInfo = diagnosisRecordTeacherOpenService.getDiagnosisListForMaster(dto, model.getPageNum(), model.getPageSize());
        PageInfo<DiagnosisRecordTeacherModel> diagnosisRecordTeacherModelPageInfo = PageHelperUtil.pageInfoConverter(pageInfo, DiagnosisRecordTeacherModel.class);
        return diagnosisRecordTeacherModelPageInfo;
    }

    @Override
    public boolean teachingManagerRelease(ReleaseAreaExamModel model) throws Exception {
        List<SchoolModel> schools = new ArrayList<>();
        List<String> stageCodes = new ArrayList<>();
        stageCodes.add(model.getStageCode().toString());
        if (ExamScopeEnum.SOME_SCHOOL.getValue().equals(model.getExamScope())) {
            schools = model.getSchools();
        } else if (ExamScopeEnum.ALL_AREA.getValue().equals(model.getExamScope())) {
            AuthGroupBean authGroupBean = new AuthGroupBean();
            authGroupBean.setGroupAreaDistrictId(model.getGroupAreaDistrictId());
            authGroupBean.setGroupPeriod(stageCodes);
            authGroupBean.setGroupType(1);
            List<AuthGroupBean> groupByCondition = authGroupService.getGroupByCondition(authGroupBean);
            for (AuthGroupBean bean : groupByCondition) {
                SchoolModel schoolModel = new SchoolModel();
                schoolModel.setSchoolCode(bean.getGroupId().toString());
                schoolModel.setSchoolName(bean.getGroupName());
                schools.add(schoolModel);
            }
        }
        model.setSchools(schools);
        ReleaseDiagnosisDto releaseExamDto = new ReleaseDiagnosisDto();
        BeanUtils.copyProperties(model, releaseExamDto);
        releaseExamDto.setSchools(JSONObject.toJSONString(schools));
        releaseExamDto.setDetailModels(JSONObject.toJSONString(model.getDetailModels()));
        String examYear = getExamYear();
        releaseExamDto.setExamYear(examYear);
        String releaseCode = diagnosisRecordTeacherOpenService.teachingManagerRelease(releaseExamDto);

        //报告系统 生成该次考试记录
        ReleaseExamDto red = new ReleaseExamDto();
        red.setExamType(model.getExamType().toString());
        red.setReleaseCode(releaseCode);
        red.setReleaseName(model.getDiagnosisName());
        red.setSemester(examYear);
        Map<Integer, Double> scores = new HashMap();
        List<ReleaseAreaExamDetailModel> detailModels = model.getDetailModels();
        for (ReleaseAreaExamDetailModel detailModel : detailModels) {
            scores.put(detailModel.getSubjectCode(), detailModel.getPaperScore());
        }
        Double totalScore = 0d;
        for (Double score : scores.values()) {
            totalScore += score;
        }
        red.setTotalScore(totalScore);
        return b2BReportOpenService.releaseExam(red);
    }

    @Override
    public Map<String, Object> areaExamList(DiagnosisRecordTeacherRequestModel teacherModel) throws Exception {
        Map<String, Object> pageResult = new HashMap<>();
        List<DiagnosisRecordTeacherModel> diagnosisRecordTeacherModels = new ArrayList<>();
        DiagnosisRecordTeacherDto drtd = new DiagnosisRecordTeacherDto();
        drtd.setTeacherCode(teacherModel.getTeacherCode());
        drtd.setExamYear(teacherModel.getExamYear());
        drtd.setGradeCode(teacherModel.getGradeCode());
        drtd.setSubjectCode(teacherModel.getSubjectCode());
        drtd.setGroupAreaDistrictId(teacherModel.getGroupAreaDistrictId());
        drtd.setExamScope(teacherModel.getExamScope());
        Order order = new Order("create_time", Order.Direction.desc);
        PageInfo<DiagnosisRecordTeacherDto> pageInfo = null;
        if(drtd.getSubjectCode() != null){
            pageInfo = diagnosisRecordTeacherOpenService.getDiagnosisListBySubject(drtd, teacherModel.getPageNum(), teacherModel.getPageSize(), order);
        }else {
            pageInfo = diagnosisRecordTeacherOpenService.getDiagnosisList(drtd, teacherModel.getPageNum(), teacherModel.getPageSize(), order);
        }
        if (pageInfo != null && !pageInfo.getList().isEmpty()) {
            diagnosisRecordTeacherModels = PageHelperUtil.converterList(pageInfo.getList(), DiagnosisRecordTeacherModel.class);
            pageResult.put("totalPage", pageInfo.getPages());
            pageResult.put("total", pageInfo.getTotal());
        } else {
            pageResult.put("totalPage", 0);
            pageResult.put("total", 0);
        }
        pageResult.put("currPage", teacherModel.getPageNum());
        pageResult.put("list", diagnosisRecordTeacherModels);
        return pageResult;
    }

    @Override
    public List<DiagnosisClassRelationModel> getExamPaperByParameter(DiagnosisRecordTeacherRequestModel teacherModel) throws Exception {
        Map<String,Object> queryMap = new HashMap();
        queryMap.put("code",teacherModel.getCode());
        queryMap.put("subjectCode",teacherModel.getSubjectCode());

        List<DiagnosisClassRelationDto> diagnosisClassRelationDtos = diagnosisClassRelationOpenService.getExamPaperByParameter(queryMap);
        if(CollectionUtils.isEmpty(diagnosisClassRelationDtos)){return new ArrayList<>();}
        List<DiagnosisClassRelationModel> result = PageHelperUtil.converterList(diagnosisClassRelationDtos,DiagnosisClassRelationModel.class);
        return result;
    }

    @Override
    public List<Integer> examSubjectList(String code) throws Exception {
        List<Integer> subjectCodes = new ArrayList<>();
        DiagnosisClassRelationDto dto = new DiagnosisClassRelationDto();
        dto.setDiagnosisRecordCode(code);
        List<DiagnosisClassRelationDto> diagnosisClassRelationDtos = diagnosisClassRelationOpenService.getListByParam(dto);
        for (DiagnosisClassRelationDto diagnosisClassRelationDto : diagnosisClassRelationDtos) {
            if(!subjectCodes.contains(diagnosisClassRelationDto.getSubjectCode())){
                subjectCodes.add(diagnosisClassRelationDto.getSubjectCode());
            }
        }
        return subjectCodes;
    }
}
