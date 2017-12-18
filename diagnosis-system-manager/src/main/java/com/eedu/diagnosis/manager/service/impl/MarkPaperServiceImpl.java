package com.eedu.diagnosis.manager.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.common.enumration.DiagnosisTypeEnum;
import com.eedu.diagnosis.common.enumration.ExamTypeEnum;
import com.eedu.diagnosis.common.exception.DiagnosisException;
import com.eedu.diagnosis.common.exception.exceptionCode.ExceptionCodeEnum;
import com.eedu.diagnosis.common.model.paperEntity.*;
import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.common.utils.PageHelperUtil;
import com.eedu.diagnosis.exam.api.dto.*;
import com.eedu.diagnosis.exam.api.enumeration.AnswerResultEnum;
import com.eedu.diagnosis.exam.api.enumeration.DiagnosisStausEnum;
import com.eedu.diagnosis.exam.api.enumeration.MarkPaperStatusEnum;
import com.eedu.diagnosis.exam.api.openService.*;
import com.eedu.diagnosis.manager.model.AnswerAndMarkQuestionModel;
import com.eedu.diagnosis.manager.model.request.DiagnosisPaperModel;
import com.eedu.diagnosis.manager.model.request.MarkPaperModel;
import com.eedu.diagnosis.manager.model.request.MarkQuestionModel;
import com.eedu.diagnosis.manager.model.response.DiagnosisRecordTeacherModel;
import com.eedu.diagnosis.manager.service.MarkPaperService;
import com.eedu.diagnosis.paper.api.dto.DiagnosisPaperDto;
import com.eedu.diagnosis.paper.api.openService.BasePaperOpenService;
import com.eedu.diagnosis.paper.api.openService.ResourceOpenService;
import com.eeduspace.b2b.report.model.StudentAnswerResultDto;
import com.eeduspace.b2b.report.model.StudentQuestionAnswerResultDto;
import com.eeduspace.b2b.report.model.question.KnowledgeModel;
import com.eeduspace.b2b.report.model.question.Productionmodels;
import com.eeduspace.b2b.report.model.question.Sons;
import com.eeduspace.b2b.report.model.question.TeachingMaterialsProductionModel;
import com.eeduspace.b2b.report.model.report.HistoryScoreModel;
import com.eeduspace.b2b.report.service.B2BReportOpenService;
import com.eeduspace.uuims.comm.util.base.DateUtils;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dqy on 2017/9/11.
 */
@Service
public class MarkPaperServiceImpl implements MarkPaperService {

    @Autowired
    private DiagnosisRecordStudentOpenService diagnosisRecordStudentOpenService;
    @Autowired
    private DiagnosisStudentAnswerRecordOpenService diagnosisStudentAnswerRecordOpenService;
    @Autowired
    private DiagnosisWrongQuestionOpenService diagnosisWrongQuestionOpenService;
    @Autowired
    private BasePaperOpenService basePaperOpenService;
    @Autowired
    private ResourceOpenService resourceOpenService;
    @Autowired
    private MarkQuestionRecordOpenService markQuestionRecordOpenService;
    @Autowired
    private DiagnosisClassRelationOpenService diagnosisClassRelationOpenService;
    @Autowired
    private B2BReportOpenService b2BReportOpenService;
    @Autowired
    private ThreadPoolTaskExecutor executor;

    @Override
    public Map<String, Object> studentExamList(DiagnosisRecordTeacherModel model) throws Exception {
        Map<String, Object> pageResult = new HashMap();
        DiagnosisRecordStudentDto dto = new DiagnosisRecordStudentDto();
        dto.setDiagnosisTeacherRecordCode(model.getCode());
        dto.setMarkStatus(model.getMarkStatus());
        dto.setSubjectCode(model.getSubjectCode());
        dto.setClassCodes(model.getClassCodes());
        PageInfo<DiagnosisRecordStudentDto> pageInfo = diagnosisRecordStudentOpenService.getMarkList(dto, model.getPageSize(), model.getPageNum(), null);
        List<DiagnosisRecordStudentDto> list = pageInfo.getList();
        if (!CollectionUtils.isEmpty(list)) {
            pageResult.put("totalPage", pageInfo.getPages());
            pageResult.put("total", pageInfo.getTotal());
        } else {
            pageResult.put("totalPage", 0);
            pageResult.put("total", 0);
        }
        pageResult.put("currPage", model.getPageNum());
        pageResult.put("list", list);
        return pageResult;
    }

    @Override
    public PaperSystem getPaperAndAnswerResult(DiagnosisPaperModel model) throws Exception {
        PaperSystem paper = getPaper(model);
        if (null != paper) {
            DiagnosisStudentAnswerRecordDto dsard = new DiagnosisStudentAnswerRecordDto();
            dsard.setDiagnosisRecordCode(model.getStudentDiagnosisRecordCode());
            PageInfo<DiagnosisStudentAnswerRecordDto> answerRecord = diagnosisStudentAnswerRecordOpenService.getAnswerRecord(dsard, Integer.MAX_VALUE, 1, null);
            List<DiagnosisStudentAnswerRecordDto> list = answerRecord.getList();
            if (null != list) {
                for (QuestionSet qset : paper.getPaperSystemQusetionType()) {
                    for (BigQuestionSystem bigquestionSystem : qset.getTypeList()) {
                        for (SmallQuestionSystem smallQuestion : bigquestionSystem.getList()) {
                            //如果是复合题
                            if (!CollectionUtils.isEmpty(smallQuestion.getComponentQuestions())) {
                                List<ComponentQuestion> componentQuestions = smallQuestion.getComponentQuestions();
                                for (ComponentQuestion com : componentQuestions) {
                                    for (DiagnosisStudentAnswerRecordDto dto : list) {
                                        if (com.getId().equals(dto.getQuestionCode())) {
                                            com.setUserAnswerResul(dto.getStudentAnswer());
                                            com.setIsImg(dto.getIsImg());
                                            com.setIsComplex(dto.getIsComplexQuestion());
                                            com.setAnswerRecordCode(dto.getCode());
                                            break;
                                        }
                                    }
                                }
                            } else {
                                for (DiagnosisStudentAnswerRecordDto dto : list) {
                                    if (smallQuestion.getId().equals(dto.getQuestionCode())) {
                                        smallQuestion.setUserAnswerResul(dto.getStudentAnswer());
                                        smallQuestion.setIsImg(dto.getIsImg());
                                        smallQuestion.setIsComplex(dto.getIsComplexQuestion());
                                        smallQuestion.setAnswerRecordCode(dto.getCode());
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return paper;
        }
        return null;
    }


    /**
     * 教师判卷后获取判断信息与学生答案信息
     *
     * @param model
     * @return
     * @throws Exception
     */
    @Override
    public PaperSystem getMarkResult(DiagnosisPaperModel model) throws Exception {
        Gson gson = new Gson();
        PaperSystem paper = getPaper(model);
        if (null != paper) {
            DiagnosisStudentAnswerRecordDto dsard = new DiagnosisStudentAnswerRecordDto();
            dsard.setDiagnosisRecordCode(model.getStudentDiagnosisRecordCode());
            PageInfo<DiagnosisStudentAnswerRecordDto> answerRecord = diagnosisStudentAnswerRecordOpenService.getAnswerRecord(dsard, Integer.MAX_VALUE, 1, null);
            List<DiagnosisStudentAnswerRecordDto> list = answerRecord.getList();
            //获取教师判题信息
            List<AnswerAndMarkQuestionModel> answerAndMarkQuestionModels = new ArrayList<>();
            List<DiagnosisMarkQuestionRecordDto> markQuestions = markQuestionRecordOpenService.findByDiagnosisRecordCode(model.getStudentDiagnosisRecordCode());
            list.stream().forEach(l -> {
                if (l.getIsImg().equals(1)) {
                    markQuestions.stream().forEach(m -> {
                        if (m.getAnswerRecordCode().equals(l.getCode())) {
                            AnswerAndMarkQuestionModel a = new AnswerAndMarkQuestionModel();
                            a.setAnswerRecordCode(l.getCode());
                            a.setScore(m.getScore());
                            a.setAnswerResult(l.getStudentAnswer());
                            a.setComplexQuestionCode(l.getComplexQuestionCode());
                            a.setQuestionCode(l.getQuestionCode());
                            a.setMarkQuestionResult(m.getMarkResult());
                            a.setBasetree(gson.fromJson(m.getBaseProduction(),
                                    new TypeToken<ArrayList<BaseProductionModel>>() {
                                    }.getType()));
                            a.setTree(gson.fromJson(m.getProductionKnowledge(),
                                    new TypeToken<ArrayList<SimpleTree>>() {
                                    }.getType()));
                            answerAndMarkQuestionModels.add(a);
                        }
                    });
                } else {
                    AnswerAndMarkQuestionModel a = new AnswerAndMarkQuestionModel();
                    a.setAnswerRecordCode(l.getCode());
                    a.setComplexQuestionCode(l.getComplexQuestionCode());
                    a.setQuestionCode(l.getQuestionCode());
                    a.setAnswerResult(l.getStudentAnswer());
                    a.setMarkQuestionResult(l.getIsRight());
                    if (AnswerResultEnum.RIGHT.getValue().equals(l.getIsRight())) {
                        a.setScore(l.getQuestionScore().doubleValue());
                    } else {
                        a.setScore(0d);
                    }
                    a.setBasetree(gson.fromJson(l.getBaseProductionJson(),
                            new TypeToken<ArrayList<BaseProductionModel>>() {
                            }.getType()));
                    a.setTree(gson.fromJson(l.getKnowledgeJson(),
                            new TypeToken<ArrayList<SimpleTree>>() {
                            }.getType()));
                    answerAndMarkQuestionModels.add(a);
                }

            });


            if (null != list) {
                for (QuestionSet qset : paper.getPaperSystemQusetionType()) {
                    for (BigQuestionSystem bigquestionSystem : qset.getTypeList()) {
                        for (SmallQuestionSystem smallQuestion : bigquestionSystem.getList()) {
                            //如果是复合题
                            if (CollectionUtils.isNotEmpty(smallQuestion.getComponentQuestions())) {
                                List<ComponentQuestion> componentQuestions = smallQuestion.getComponentQuestions();
                                for (ComponentQuestion com : componentQuestions) {
                                    a:
                                    for (AnswerAndMarkQuestionModel dto : answerAndMarkQuestionModels) {
                                        if (com.getId().equals(dto.getQuestionCode())) {
                                            com.setUserAnswerResul(dto.getAnswerResult());
                                            com.setTree(dto.getTree());
                                            com.setBasetree(dto.getBasetree());
                                            com.setStudentGetScore(dto.getScore());
                                            com.setMarkQuestionResult(dto.getMarkQuestionResult());
                                            break a;
                                        }
                                    }
                                }
                            } else {
                                b:
                                for (AnswerAndMarkQuestionModel dto : answerAndMarkQuestionModels) {
                                    if (smallQuestion.getId().equals(dto.getQuestionCode())) {
                                        smallQuestion.setUserAnswerResul(dto.getAnswerResult());
                                        smallQuestion.setTree(dto.getTree());
                                        smallQuestion.setBasetree(dto.getBasetree());
                                        smallQuestion.setStudentGetScore(dto.getScore());
                                        smallQuestion.setMarkQuestionResult(dto.getMarkQuestionResult());
                                        break b;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return paper;
        }
        return null;
    }

    @Override
    public void saveMarkPaperInfo(MarkPaperModel model) throws Exception {
        //I保存老师的判卷信息
        List<DiagnosisMarkQuestionRecordDto> diagnosisMarkQuestionRecordDtos = PageHelperUtil.converterList(model.getMarkQuestionList(), DiagnosisMarkQuestionRecordDto.class);
        markQuestionRecordOpenService.batchSave(diagnosisMarkQuestionRecordDtos);

        //II 为该生生成报告
        List<MarkQuestionModel> markQuestionModels = model.getMarkQuestionList();
        //2.1获取该次考试记录信息
        String diagnosisRecordCode = markQuestionModels.get(0).getDiagnosisRecordCode();
        DiagnosisRecordStudentDto dto = new DiagnosisRecordStudentDto();
        dto.setCode(diagnosisRecordCode);
        List<DiagnosisRecordStudentDto> all = diagnosisRecordStudentOpenService.getAll(dto, null);
        DiagnosisRecordStudentDto diagnosisRecordStudentDto = all.get(0);

        //2.2获取该次考试学生的答题信息
        DiagnosisStudentAnswerRecordDto diagnosisStudentAnswerRecordDto = new DiagnosisStudentAnswerRecordDto();
        diagnosisStudentAnswerRecordDto.setDiagnosisRecordCode(diagnosisRecordStudentDto.getCode());
        PageInfo<DiagnosisStudentAnswerRecordDto> answerRecord = diagnosisStudentAnswerRecordOpenService.getAnswerRecord(diagnosisStudentAnswerRecordDto, Integer.MAX_VALUE, 1, null);
        List<DiagnosisStudentAnswerRecordDto> list = answerRecord.getList();

        //2.3客观题中的正确题集合
        List<DiagnosisStudentAnswerRecordDto> rightList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            rightList = list.stream().filter(dsar -> dsar.getIsImg().equals(0) && dsar.getIsRight().equals(1)).collect(Collectors.toList());
        }

        //2.4封装判卷信息中对错题数和得分
        Map result = formatScore(markQuestionModels);

        int objectiveRightCount = rightList.size();
        int rightCount = objectiveRightCount + (int) result.get("rightCount");
        double score = (Double) result.get("score") + diagnosisRecordStudentDto.getObjectiveScore().doubleValue();
        diagnosisRecordStudentDto.setSubjectiveScore(new BigDecimal((Double) result.get("score")));
        diagnosisRecordStudentDto.setTotalScore(new BigDecimal(score));

        //2.5获取该生对应考试类型的考试的历史分数
        String historyScore = getHistoryScore(diagnosisRecordStudentDto);
        //2.6 封装获取报告所需参数
        StudentAnswerResultDto answerResultDto = getStudentAnswerResultDto(model, markQuestionModels, diagnosisRecordStudentDto, list, rightCount, score, historyScore);
        b2BReportOpenService.generateStudentReport(answerResultDto);

        //III 异步保存学生的主观题错题记录
        executor.execute(() -> saveWrongQuestions(model, diagnosisRecordStudentDto, list));
        //IV 修改该生的考试记录状态
        diagnosisRecordStudentDto.setMarkStatus(MarkPaperStatusEnum.MARKED.getValue());
        diagnosisRecordStudentDto.setDiagnosisStatus(DiagnosisStausEnum.REPROT.getValue());
        diagnosisRecordStudentOpenService.update(diagnosisRecordStudentDto);
    }

    private void saveWrongQuestions(MarkPaperModel model, DiagnosisRecordStudentDto diagnosisRecordStudentDto, List<DiagnosisStudentAnswerRecordDto> list) {
        try {
            //获取试卷
            String resourcePaperInfo = resourceOpenService.getResourcePaperInfo(model.getResourcePaperCode());
            PaperSystem paper = JSONObject.parseObject(resourcePaperInfo, PaperSystem.class);
            //判卷信息
            List<MarkQuestionModel> markQuestionList = model.getMarkQuestionList();
            //封装错题信息
            List<DiagnosisWrongQuestionDto> dtos = new ArrayList<>();
            for (QuestionSet qset : paper.getPaperSystemQusetionType()) {
                for (BigQuestionSystem bigquestionSystem : qset.getTypeList()) {
                    for (SmallQuestionSystem smallQuestion : bigquestionSystem.getList()) {
                        //如果是复合题中的非客观题
                        if (CollectionUtils.isNotEmpty(smallQuestion.getComponentQuestions())) {
                            List<ComponentQuestion> componentQuestions = smallQuestion.getComponentQuestions();
                            for (ComponentQuestion com : componentQuestions) {
                                for (MarkQuestionModel markQuestionModel : markQuestionList) {
                                    if (com.getId().equals(markQuestionModel.getComplexQuestionCode()) && AnswerResultEnum.WRONG.getValue().equals(markQuestionModel.getMarkResult())) {
                                        List<DiagnosisStudentAnswerRecordDto> diagnosisStudentAnswerRecordDtos = list.stream().filter(diagnosisStudentAnswerRecordDto -> com.getId().equals(diagnosisStudentAnswerRecordDto.getQuestionCode())).collect(Collectors.toList());
                                        DiagnosisStudentAnswerRecordDto diagnosisStudentAnswerRecordDto = diagnosisStudentAnswerRecordDtos.get(0);
                                        DiagnosisWrongQuestionDto wrongQuestion = getWrongQuestion(diagnosisRecordStudentDto, smallQuestion, com, markQuestionModel, diagnosisStudentAnswerRecordDto);
                                        dtos.add(wrongQuestion);
                                    }
                                }
                            }
                        } else {//如果是非复合题的非客观题
                            for (MarkQuestionModel markQuestionModel : markQuestionList) {
                                if (markQuestionModel.getQuestionCode().equals(smallQuestion.getId()) && AnswerResultEnum.WRONG.getValue().equals(markQuestionModel.getMarkResult())) {
                                    List<DiagnosisStudentAnswerRecordDto> diagnosisStudentAnswerRecordDtos = list.stream().filter(diagnosisStudentAnswerRecordDto -> smallQuestion.getId().equals(diagnosisStudentAnswerRecordDto.getQuestionCode())).collect(Collectors.toList());
                                    DiagnosisStudentAnswerRecordDto diagnosisStudentAnswerRecordDto = diagnosisStudentAnswerRecordDtos.get(0);
                                    DiagnosisWrongQuestionDto wrongQuestion = getWrongQuestion(diagnosisRecordStudentDto, smallQuestion, null, markQuestionModel, diagnosisStudentAnswerRecordDto);
                                    dtos.add(wrongQuestion);
                                }
                            }
                        }
                    }
                }
            }
            if (!CollectionUtils.isEmpty(dtos)) {
                diagnosisWrongQuestionOpenService.batchSave(dtos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //封装错题信息
    private DiagnosisWrongQuestionDto getWrongQuestion(DiagnosisRecordStudentDto diagnosisRecordStudentDto, SmallQuestionSystem smallQuestion, ComponentQuestion componentQuestion, MarkQuestionModel markQuestionModel, DiagnosisStudentAnswerRecordDto diagnosisStudentAnswerRecordDto) {
        DiagnosisWrongQuestionDto wrongQuestion = new DiagnosisWrongQuestionDto();
        wrongQuestion.setCode(UUID.randomUUID().toString().replace("-", "").toUpperCase());
        wrongQuestion.setStudentCode(diagnosisRecordStudentDto.getStudentCode());
        wrongQuestion.setDiagnosisRecordCode(diagnosisRecordStudentDto.getCode());
        wrongQuestion.setSubjectCode(diagnosisRecordStudentDto.getSubjectCode());
        wrongQuestion.setCreateTime(new Date());
        wrongQuestion.setUpdateTime(new Date());
        if (null != componentQuestion) {
            wrongQuestion.setComplexQuestionCode(smallQuestion.getId());
            wrongQuestion.setQuestionCode(componentQuestion.getId());
            wrongQuestion.setIsComplex(1);
            wrongQuestion.setQuestionType(componentQuestion.getType());
            wrongQuestion.setComplexQuestionStem(componentQuestion.getStem());
            wrongQuestion.setQuestionOption(componentQuestion.getQuesOption());
            if (componentQuestion.getQuesOption().equals("1") || componentQuestion.getQuesOption().equals("{\"optionKey\":\"null\",\"optionValue\":\"null\"}")) {
                wrongQuestion.setQuestionOption(null);
            }
            wrongQuestion.setQuestionAnalyze(componentQuestion.getQuesAnalyze());
            wrongQuestion.setAudioAnalyzePath(componentQuestion.getVoice());
            wrongQuestion.setRightAnswer(componentQuestion.getAnswer());
            wrongQuestion.setBaseProduction(JSONObject.toJSONString(markQuestionModel.getBaseProduction()));
        } else {
            wrongQuestion.setQuestionCode(smallQuestion.getId());
            wrongQuestion.setIsComplex(0);
            wrongQuestion.setQuestionType(smallQuestion.getType());
            wrongQuestion.setQuestionOption(smallQuestion.getQuesOption());
            if (smallQuestion.getQuesOption().equals("1") || smallQuestion.getQuesOption().equals("{\"optionKey\":\"null\",\"optionValue\":\"null\"}")) {
                wrongQuestion.setQuestionOption(null);
            }
            wrongQuestion.setAudioAnalyzePath(smallQuestion.getAudioAnalyzePath());
            wrongQuestion.setQuestionAnalyze(smallQuestion.getQuesAnalyze());
            wrongQuestion.setRightAnswer(smallQuestion.getAnswer());
            wrongQuestion.setBaseProduction(diagnosisStudentAnswerRecordDto.getBaseProductionJson());
        }
        wrongQuestion.setQuestionStem(smallQuestion.getStem());
        wrongQuestion.setStudentAnswer(diagnosisStudentAnswerRecordDto.getStudentAnswer());
        return wrongQuestion;
    }

    private StudentAnswerResultDto getStudentAnswerResultDto(MarkPaperModel model, List<MarkQuestionModel> markQuestionModels, DiagnosisRecordStudentDto diagnosisRecordStudentDto, List<DiagnosisStudentAnswerRecordDto> list, int rightCount, double score, String historyScore) throws Exception {
        StudentAnswerResultDto answerResultDto = new StudentAnswerResultDto();
        answerResultDto.setHistoricalScore(historyScore);
        answerResultDto.setPaperStandardScore(Double.parseDouble(model.getPaperScore()) * 0.8);
        answerResultDto.setPaperScore(model.getPaperScore().toString());
        answerResultDto.setPaperCode(model.getResourcePaperCode());
        answerResultDto.setDiagnosisPaperCode(diagnosisRecordStudentDto.getDiagnosisPaperCode());
        answerResultDto.setPaperName(diagnosisRecordStudentDto.getDiagnosisPaperName());
        answerResultDto.setUserCode(diagnosisRecordStudentDto.getStudentCode().toString());
        answerResultDto.setUserName(diagnosisRecordStudentDto.getStudentName());
        answerResultDto.setReleaseCode(diagnosisRecordStudentDto.getDiagnosisTeacherRecordCode());
        answerResultDto.setMarkPaperRecordCode(diagnosisRecordStudentDto.getCode());
        answerResultDto.setUseTime(Integer.parseInt(diagnosisRecordStudentDto.getUseTime() == null ? "0" : diagnosisRecordStudentDto.getUseTime()));
        answerResultDto.setScore(score);
        answerResultDto.setRightCount(rightCount);
        answerResultDto.setWrongCount(list.size() - rightCount);
        DiagnosisClassRelationDto diagnosisClassRelationDto = new DiagnosisClassRelationDto();
        diagnosisClassRelationDto.setDiagnosisRecordCode(model.getDiagnosisTeacherRecordCode());
        diagnosisClassRelationDto.setSubjectCode(diagnosisRecordStudentDto.getSubjectCode());
        diagnosisClassRelationDto.setClassCode(diagnosisRecordStudentDto.getClassCode());
        List<DiagnosisClassRelationDto> classRelationDtos = diagnosisClassRelationOpenService.getListByParam(diagnosisClassRelationDto);
        DiagnosisClassRelationDto diagnosisClassRelationDto1;
        if (!CollectionUtils.isEmpty(classRelationDtos)) {
            diagnosisClassRelationDto1 = classRelationDtos.get(0);
            answerResultDto.setTeacherCode(diagnosisClassRelationDto1.getTeacherCode().toString());
            answerResultDto.setTeacherName(diagnosisClassRelationDto1.getTeacherName());
        } else {
            throw new DiagnosisException(ExceptionCodeEnum.RESOURCE_NOTFOUND.getCode(), "该科没有任课老师信息。");
        }
        answerResultDto.setMakePaperTime(new Timestamp(diagnosisRecordStudentDto.getMakePaperTime().getTime()));
        answerResultDto.setBookTypeVersionCode(model.getBookVersionCode());
        answerResultDto.setSchoolCode(diagnosisRecordStudentDto.getSchoolCode().toString());
        answerResultDto.setSchoolName(diagnosisRecordStudentDto.getSchoolName());
        answerResultDto.setClassCode(diagnosisRecordStudentDto.getClassCode().toString());
        answerResultDto.setClassName(diagnosisClassRelationDto1.getClassName());
        answerResultDto.setStageCode(diagnosisRecordStudentDto.getStageCode().toString());
        answerResultDto.setSubjectCode(diagnosisRecordStudentDto.getSubjectCode().toString());
        answerResultDto.setGradeCode(diagnosisRecordStudentDto.getGradeCode().toString());
        answerResultDto.setArtType(diagnosisRecordStudentDto.getArtType() == null ? 2 : diagnosisRecordStudentDto.getArtType());


        List<StudentQuestionAnswerResultDto> answerResultDtos = new ArrayList<>();
        //2.7封装客观题部分参数
        List<StudentQuestionAnswerResultDto> objectiveAnswerResultDtos
                = formatObjectParam(list.stream().filter(sqa -> sqa.getIsImg().equals(0)).collect(Collectors.toList()));
        //2.8封装主观题部分参数
        List<StudentQuestionAnswerResultDto> subjectiveAnswerResultDtos = formatSubjectiveParam(markQuestionModels, list);
        answerResultDtos.addAll(objectiveAnswerResultDtos);
        answerResultDtos.addAll(subjectiveAnswerResultDtos);
        answerResultDto.setStudentQuestionAnswerResultDtos(answerResultDtos);
        return answerResultDto;
    }
    //封装主观题参数
    private List<StudentQuestionAnswerResultDto> formatSubjectiveParam(List<MarkQuestionModel> markQuestionModels, List<DiagnosisStudentAnswerRecordDto> list) {
        List<StudentQuestionAnswerResultDto> studentQuestionAnswerResultDtos = new ArrayList<>();
        StudentQuestionAnswerResultDto sdto = null;
        List<DiagnosisStudentAnswerRecordDto> collect = list.stream().filter(dsar -> dsar.getIsImg().equals(1)).collect(Collectors.toList());
        for (MarkQuestionModel markQuestionModel : markQuestionModels) {
            for (DiagnosisStudentAnswerRecordDto diagnosisStudentAnswerRecordDto : collect) {
                if (markQuestionModel.getAnswerRecordCode().equals(diagnosisStudentAnswerRecordDto.getCode())) {
                    sdto = new StudentQuestionAnswerResultDto();
                    sdto.setScore(markQuestionModel.getScore());
                    sdto.setAnswerResult(markQuestionModel.getMarkResult());
                    sdto.setQuestionCode(diagnosisStudentAnswerRecordDto.getQuestionCode());
                    sdto.setComplexQuestionCode(diagnosisStudentAnswerRecordDto.getComplexQuestionCode());
                    sdto.setIsComplex(markQuestionModel.getIsComplex());
                    sdto.setQuestionSn(diagnosisStudentAnswerRecordDto.getQuestionSn());
                    String productionKnowledge = markQuestionModel.getProductionKnowledge();
                    List<KnowledgeModel> knowledgeModels = getKnowledgeModels(productionKnowledge);
                    sdto.setKnowledgeModelList(knowledgeModels);
                    String baseProduction = markQuestionModel.getBaseProduction();
                    List<Productionmodels> productionmodels = getProductionmodels(baseProduction);
                    sdto.setProductionmodelsList(productionmodels);
                }

            }
            studentQuestionAnswerResultDtos.add(sdto);
        }
        return studentQuestionAnswerResultDtos;
    }
    //封装客观题参数
    private List<StudentQuestionAnswerResultDto> formatObjectParam(List<DiagnosisStudentAnswerRecordDto> list) {
        List<StudentQuestionAnswerResultDto> studentQuestionAnswerResultDtos = new ArrayList<>();
        StudentQuestionAnswerResultDto sdto = null;
        for (DiagnosisStudentAnswerRecordDto dto1 : list) {
            sdto = new StudentQuestionAnswerResultDto();
            if(AnswerResultEnum.WRONG.getValue().equals(dto1.getIsRight())){
                sdto.setScore(0d);
            }else {
                sdto.setScore(dto1.getQuestionScore().doubleValue());
            }
            sdto.setAnswerResult(dto1.getIsRight());
            sdto.setComplexQuestionCode(dto1.getComplexQuestionCode());
            sdto.setIsComplex(dto1.getIsComplexQuestion());
            sdto.setQuestionCode(dto1.getQuestionCode());
            sdto.setQuestionSn(dto1.getQuestionSn());
            String knowledgeJson = dto1.getKnowledgeJson();
            List<KnowledgeModel> knowledgeModelList = getKnowledgeModels(knowledgeJson);
            sdto.setKnowledgeModelList(knowledgeModelList);
            String baseProductionJson = dto1.getBaseProductionJson();
            List<Productionmodels> productionmodelsList = getProductionmodels(baseProductionJson);
            sdto.setProductionmodelsList(productionmodelsList);
            studentQuestionAnswerResultDtos.add(sdto);
        }
        return studentQuestionAnswerResultDtos;
    }

    private List<Productionmodels> getProductionmodels(String baseProductionJson) {
        List<BaseProductionModel> baseProductionModels = JSONArray.parseArray(baseProductionJson, BaseProductionModel.class);
        List<Productionmodels> productionmodelsList = new ArrayList<>();
        Productionmodels pm = null;
        for (BaseProductionModel bp : baseProductionModels) {
            pm = new Productionmodels();
            pm.setBasecodeVo(bp.getBasecodeVo());
            pm.setBasenameVo(bp.getBasenameVo());
            pm.setIsTrue(bp.getIsTrue());
            pm.setTypecodeVo(bp.getTypecodeVo());
            List<Sons> ss = new ArrayList<>();
            Sons s = null;
            for (SimpleTreeVO sv : bp.getSons()) {
                s = new Sons();
                s.setIdVo(sv.getIdVo());
                s.setNameVo(sv.getNameVo());
                ss.add(s);
            }
            pm.setSons(ss);
            productionmodelsList.add(pm);
        }
        return productionmodelsList;
    }

    private List<KnowledgeModel> getKnowledgeModels(String knowledgeJson) {
        List<KnowledgeModel> knowledgeModelList = new ArrayList<>();
        List<SimpleTree> simpleTrees = JSONArray.parseArray(knowledgeJson, SimpleTree.class);
        KnowledgeModel km = null;
        for (SimpleTree st : simpleTrees) {
            km = new KnowledgeModel();
            km.setIsTrue(st.getIsRight());
            km.setKnowledgeCode(st.getId());
            km.setKnowledgeName(st.getName());
            List<TeachingMaterialsProductionModel> tps = new ArrayList<>();
            TeachingMaterialsProductionModel tp = null;
            for (SimpleTreeVO stv : st.getSons()) {
                tp = new TeachingMaterialsProductionModel();
                tp.setIsTrue(stv.getIsRight());
                tp.setBaseCode(stv.getBasecode());
                tp.setTeachingMaterialsCode(stv.getIdVo());
                tp.setTypecode(stv.getTypecode());
                tps.add(tp);
            }
            km.setTeachingMaterialsProdutions(tps);
            knowledgeModelList.add(km);
        }
        return knowledgeModelList;
    }

    private Map formatScore(List<MarkQuestionModel> markQuestionModels) {
        if (CollectionUtils.isEmpty(markQuestionModels)) return null;
        Map result = new HashMap();
        double score = 0d;
        int rightCount = 0;
        for (MarkQuestionModel markQuestionModel : markQuestionModels) {
            score += markQuestionModel.getScore();
            if (AnswerResultEnum.RIGHT.getValue().equals(markQuestionModel.getMarkResult())) {
                rightCount++;
            }
        }
        result.put("score", score);
        result.put("rightCount", rightCount);
        result.put("wrongCount", markQuestionModels.size() - rightCount);
        return result;
    }

    private String getHistoryScore(DiagnosisRecordStudentDto diagnosisRecordStudentDto) throws Exception {
        DiagnosisRecordStudentDto drd = new DiagnosisRecordStudentDto();
        drd.setSubjectCode(diagnosisRecordStudentDto.getSubjectCode());
        drd.setStudentCode(diagnosisRecordStudentDto.getStudentCode());
        drd.setGradeCode(diagnosisRecordStudentDto.getGradeCode());
        //根据考试类型获取相应类型的考试记录
        if (ExamTypeEnum.UNIT.getCode().equals(diagnosisRecordStudentDto.getDiagnosisType())) {
            drd.setDiagnosisType(DiagnosisTypeEnum.SINGLE.getValue());
        } else {
            drd.setDiagnosisType(DiagnosisTypeEnum.COMPLEX.getValue());
        }
        drd.setDiagnosisStatus(DiagnosisStausEnum.REPROT.getValue());
        Order order = new Order();
        order.setProperty("start_time");
        order.setDirection(Order.Direction.desc);
        List<DiagnosisRecordStudentDto> studentDiagnosisRecords = diagnosisRecordStudentOpenService.getAll(drd, order);

        List<HistoryScoreModel> scores = new ArrayList<>();
        HistoryScoreModel historyScoreModel = new HistoryScoreModel();
        historyScoreModel.setExamDate(DateUtils.toString(diagnosisRecordStudentDto.getStartTime(), DateUtils.DATE_FORMAT_DATETIME));
        historyScoreModel.setScore(Double.parseDouble(null == diagnosisRecordStudentDto.getTotalScore() ? "0" : diagnosisRecordStudentDto.getTotalScore().toString()));
        scores.add(historyScoreModel);
        if (null != studentDiagnosisRecords && !studentDiagnosisRecords.isEmpty()) {
            for (DiagnosisRecordStudentDto po : studentDiagnosisRecords) {
                HistoryScoreModel hm = new HistoryScoreModel();
                hm.setExamDate(DateUtils.toString(po.getCreateTime(), DateUtils.DATE_FORMAT_DATETIME));
                hm.setScore(Double.parseDouble(null == po.getTotalScore() ? "0" : po.getTotalScore().toString()));
                scores.add(hm);
            }
        }
        return JSONObject.toJSONString(scores);
    }

    private PaperSystem getPaper(DiagnosisPaperModel model) throws Exception {
        DiagnosisPaperDto dto = new DiagnosisPaperDto();
        dto.setCode(model.getCode());
        List<DiagnosisPaperDto> diagnosisPaperList = basePaperOpenService.getDiagnosisPaperList(dto);
        if (null != diagnosisPaperList && !diagnosisPaperList.isEmpty()) {
            String responseJson = resourceOpenService.getResourcePaperInfo(diagnosisPaperList.get(0).getResourcePaperCode());
            if (!StringUtils.isBlank(responseJson)) {
                return JSONObject.parseObject(responseJson, PaperSystem.class);
            }
        }
        return null;
    }
}
