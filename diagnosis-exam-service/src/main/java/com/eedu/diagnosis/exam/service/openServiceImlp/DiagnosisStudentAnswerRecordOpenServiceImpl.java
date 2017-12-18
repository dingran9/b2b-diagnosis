package com.eedu.diagnosis.exam.service.openServiceImlp;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.common.model.paperEntity.*;
import com.eedu.diagnosis.common.service.redisService.RedisClientTemplate;
import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.common.utils.PageHelperUtil;
import com.eedu.diagnosis.exam.api.dto.AnswerSheetDto;
import com.eedu.diagnosis.exam.api.dto.AnswerSheetQuestionDto;
import com.eedu.diagnosis.exam.api.dto.DiagnosisStudentAnswerRecordDto;
import com.eedu.diagnosis.exam.api.enumeration.AnswerResultEnum;
import com.eedu.diagnosis.exam.api.enumeration.DiagnosisPaperEnum;
import com.eedu.diagnosis.exam.api.enumeration.DiagnosisStausEnum;
import com.eedu.diagnosis.exam.api.enumeration.MarkPaperStatusEnum;
import com.eedu.diagnosis.exam.api.openService.DiagnosisStudentAnswerRecordOpenService;
import com.eedu.diagnosis.exam.client.service.BaseResourceClient;
import com.eedu.diagnosis.exam.persist.po.DiagnosisRecordStudentPo;
import com.eedu.diagnosis.exam.persist.po.DiagnosisStudentAnswerRecordPo;
import com.eedu.diagnosis.exam.persist.po.DiagnosisWrongQuestion;
import com.eedu.diagnosis.exam.service.DiagnosisRecordStudentService;
import com.eedu.diagnosis.exam.service.DiagnosisStudentAnswerRecordService;
import com.eedu.diagnosis.exam.service.DiagnosisWrongQuestionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by dqy on 2017/3/14.
 */
@Service
public class DiagnosisStudentAnswerRecordOpenServiceImpl implements DiagnosisStudentAnswerRecordOpenService {

    @Autowired
    private RedisClientTemplate redisClientTemplate;
    @Autowired
    private BaseResourceClient baseResourceClient;
    @Autowired
    private DiagnosisRecordStudentService diagnosisRecordStudentService;
    @Autowired
    private DiagnosisStudentAnswerRecordService diagnosisStudentAnswerRecordService;
    @Autowired
    private DiagnosisWrongQuestionService diagnosisWrongQuestionService;


    @Transactional
    @Override
    public AnswerSheetDto submit(AnswerSheetDto asd) throws Exception {
        BigDecimal objectiveScore = new BigDecimal(0);//客观题得分
        int rightCout = 0;
        int wrongCout = 0;
        //获取试卷
        PaperSystem paperModel = getPaperSystem(asd);

        //封装客观题得分、对错题数量等
        List<AnswerSheetQuestionDto> answerSheetQuestionDtos = asd.getAnswerSheetQuestionDtos();
        for (AnswerSheetQuestionDto dto : answerSheetQuestionDtos) {
            if (dto.getIsImg().equals(0)) {//客观题
                if (AnswerResultEnum.WRONG.getValue().equals(dto.getIsRight())) {
                    wrongCout++;
                } else {
                    rightCout++;
                    objectiveScore = objectiveScore.add(new BigDecimal(dto.getQuestionScore()));
                }
            }
        }

        //更新学生的诊断记录
        DiagnosisRecordStudentPo drsp = new DiagnosisRecordStudentPo();
        drsp.setCode(asd.getCode());
        drsp.setUpdateTime(new Date());
        drsp.setObjectiveScore(objectiveScore);
        if (DiagnosisPaperEnum.NeedmarkEnum.NOTNEED.getValue().equals(asd.getNeedMark())) {
            drsp.setTotalScore(objectiveScore);
            drsp.setMarkStatus(MarkPaperStatusEnum.MARKED.getValue());
        } else {
            drsp.setMarkStatus(MarkPaperStatusEnum.NOTMARK.getValue());
        }
        drsp.setUseTime(asd.getUseTime());
        drsp.setDiagnosisStatus(DiagnosisStausEnum.SUBMIT.getValue());
        drsp.setMakePaperTime(new Date());
        diagnosisRecordStudentService.update(drsp);

        //保存答题信息和错题记录
        List<AnswerSheetQuestionDto> result = saveAnswerAndWrongQuestion(asd, paperModel);
        asd.setWrongCout(wrongCout);
        asd.setRightCout(rightCout);
        asd.setTotalScore(paperModel.getTotalScore().doubleValue());
        asd.setGetScore(objectiveScore.doubleValue());
        asd.setAnswerSheetQuestionDtos(result);
        asd.setIstopic(Double.parseDouble(StringUtils.isEmpty(paperModel.getIstopic()) ? paperModel.getTotalScore() * 0.8 + "" : paperModel.getIstopic()));
        asd.setSubjectCode(Integer.parseInt(paperModel.getSubjectCode()));
        asd.setBookVersionCode(paperModel.getBooktype());
        return asd;


    }


    @Override
    public PageInfo<DiagnosisStudentAnswerRecordDto> getAnswerRecord(DiagnosisStudentAnswerRecordDto dsard, Integer pageSize, Integer pageNum, Order order) throws Exception {
        Map<String, Object> queryMap = JSONObject.parseObject(JSONObject.toJSONString(dsard), Map.class);
        PageHelper.startPage(pageNum, pageSize);
        List<DiagnosisStudentAnswerRecordPo> diagnosisStudentAnswerRecordPoList = diagnosisStudentAnswerRecordService.findByCondition(queryMap, order);
        PageInfo<DiagnosisStudentAnswerRecordPo> pageInfo = new PageInfo<>(diagnosisStudentAnswerRecordPoList);
        PageInfo<DiagnosisStudentAnswerRecordDto> result = PageHelperUtil.pageInfoConverter(pageInfo, DiagnosisStudentAnswerRecordDto.class);
        return result;
    }

    @Override
    public void delete(DiagnosisStudentAnswerRecordDto diagnosisStudentAnswerRecordDto) throws Exception {
        DiagnosisStudentAnswerRecordPo diagnosisStudentAnswerRecordPo = new DiagnosisStudentAnswerRecordPo();
        diagnosisStudentAnswerRecordPo.setDiagnosisRecordCode(diagnosisStudentAnswerRecordDto.getDiagnosisRecordCode());
        diagnosisStudentAnswerRecordService.deleteByDiagnosisStudentAnswerRecordPo(diagnosisStudentAnswerRecordPo);
    }


    private List<AnswerSheetQuestionDto> saveAnswerAndWrongQuestion(AnswerSheetDto asd, PaperSystem paperModel) throws Exception {
        List<AnswerSheetQuestionDto> result = new ArrayList<>();
        List<DiagnosisWrongQuestion> wrongQuestions = new ArrayList<>();
        List<DiagnosisStudentAnswerRecordPo> answerPos = new ArrayList<>();
        DiagnosisStudentAnswerRecordPo answerPo;
        DiagnosisWrongQuestion wrongQuestion;
        List<BaseProductionModel> baseTrees;
        List<SimpleTree> trees;
        for (AnswerSheetQuestionDto answerSheetQuestionDto : asd.getAnswerSheetQuestionDtos()) {
            baseTrees = new ArrayList<>();
            trees = new ArrayList<>();

            answerPo = new DiagnosisStudentAnswerRecordPo();
            BeanUtils.copyProperties(answerSheetQuestionDto, answerPo);
            answerPo.setIsComplexQuestion(answerSheetQuestionDto.getIsComplex());
            answerPo.setDiagnosisRecordCode(asd.getCode());
            answerPo.setStudentCode(asd.getStudentCode());
            answerPo.setCreateTime(new Date());
            answerPo.setUpdateTime(new Date());

            for (QuestionSet qset : paperModel.getPaperSystemQusetionType()) {
                for (BigQuestionSystem bigquestionSystem : qset.getTypeList()) {
                    for (SmallQuestionSystem smallQuestion : bigquestionSystem.getList()) {
                        //小题题号匹配
                        if (smallQuestion.getId().equals(answerSheetQuestionDto.getQuestionCode())) {
                            //是否为复合题
                            if (!CollectionUtils.isEmpty(smallQuestion.getComponentQuestions())) {
                                for (ComponentQuestion componentQuestion : smallQuestion.getComponentQuestions()) {
                                    if (componentQuestion.getId().equals(answerSheetQuestionDto.getComplexQuestionCode())) {
                                        baseTrees = componentQuestion.getBasetree();
                                        trees = componentQuestion.getTree();

                                        //questionCode为复合题小题code     complexQuestionCode为复合题大题code
                                        answerPo.setQuestionCode(answerSheetQuestionDto.getComplexQuestionCode());
                                        answerPo.setComplexQuestionCode(smallQuestion.getId());

                                        if (answerSheetQuestionDto.getIsImg().equals(0) &&
                                                AnswerResultEnum.WRONG.getValue().equals(answerSheetQuestionDto.getIsRight())) {
                                            formatTree(trees, answerSheetQuestionDto);
                                            formatBaseTree(baseTrees, answerSheetQuestionDto);
                                            wrongQuestion = getDiagnosisWrongQuestion(asd, paperModel, baseTrees, answerSheetQuestionDto, smallQuestion, componentQuestion);
                                            wrongQuestions.add(wrongQuestion);
                                        }
                                        if (answerSheetQuestionDto.getIsImg().equals(0)) {
                                            answerPo.setBaseProductionJson(JSONObject.toJSONString(baseTrees));
                                            answerPo.setKnowledgeJson(JSONObject.toJSONString(trees));
                                        }
                                        answerPo.setQuestionScore(new BigDecimal(componentQuestion.getScore() == null ? 0d : componentQuestion.getScore()));
                                    }
                                }

                            } else {
                                baseTrees = smallQuestion.getBasetree();
                                trees = smallQuestion.getTree();
                                if (answerSheetQuestionDto.getIsImg().equals(0) &&
                                        AnswerResultEnum.WRONG.getValue().equals(answerSheetQuestionDto.getIsRight())) {
                                    formatTree(trees, answerSheetQuestionDto);
                                    formatBaseTree(baseTrees, answerSheetQuestionDto);
                                    wrongQuestion = getDiagnosisWrongQuestion(asd, paperModel, baseTrees, answerSheetQuestionDto, smallQuestion,null);
                                    wrongQuestions.add(wrongQuestion);
                                }
                                if (answerSheetQuestionDto.getIsImg().equals(0)) {
                                    answerPo.setBaseProductionJson(JSONObject.toJSONString(baseTrees));
                                    answerPo.setKnowledgeJson(JSONObject.toJSONString(trees));
                                }
                                answerPo.setQuestionScore(new BigDecimal(smallQuestion.getScore() == null ? 0d : smallQuestion.getScore()));
                            }
                            answerSheetQuestionDto.setProductionJson(JSONObject.toJSONString(baseTrees));
                            answerSheetQuestionDto.setKnowledgeJson(JSONObject.toJSONString(trees));
                            answerPos.add(answerPo);
                        }
                    }
                }
            }
            result.add(answerSheetQuestionDto);
        }
        if (!answerPos.isEmpty()) {
            diagnosisStudentAnswerRecordService.saveList(answerPos);
        }
        if (!wrongQuestions.isEmpty()) {
            diagnosisWrongQuestionService.saveList(wrongQuestions);
        }
        return result;
    }

    private void formatTree(List<SimpleTree> trees, AnswerSheetQuestionDto answerSheetQuestionDto) {
        for (SimpleTree simpleTree : trees) {//知识点
            simpleTree.setIsRight(AnswerResultEnum.WRONG.getValue());
            for (SimpleTreeVO simpleTreeVO : simpleTree.getSons()) {//产生式
                if (AnswerResultEnum.WRONG.getValue().equals(answerSheetQuestionDto.getIsRight())) {
                    simpleTreeVO.setIsRight(AnswerResultEnum.WRONG.getValue());
                }
            }
        }
    }

    private void formatBaseTree(List<BaseProductionModel> baseTrees, AnswerSheetQuestionDto answerSheetQuestionDto) {
        for (BaseProductionModel model : baseTrees) {
            model.setIsTrue(AnswerResultEnum.WRONG.getValue());
            List<SimpleTreeVO> sons = model.getSons();
            for (SimpleTreeVO son : sons) {
                if (AnswerResultEnum.WRONG.getValue().equals(answerSheetQuestionDto.getIsRight())) {
                    son.setIsRight(AnswerResultEnum.WRONG.getValue());
                }
            }
        }
    }

    private DiagnosisWrongQuestion getDiagnosisWrongQuestion(AnswerSheetDto asd, PaperSystem paperModel, List<BaseProductionModel> baseTrees, AnswerSheetQuestionDto answerSheetQuestionDto, SmallQuestionSystem smallQuestion, ComponentQuestion componentQuestion) {
        DiagnosisWrongQuestion wrongQuestion  = new DiagnosisWrongQuestion();
        BeanUtils.copyProperties(answerSheetQuestionDto, wrongQuestion);
        wrongQuestion.setStudentCode(asd.getStudentCode());
        wrongQuestion.setDiagnosisRecordCode(asd.getCode());
        wrongQuestion.setSubjectCode(Integer.parseInt(paperModel.getSubjectCode()));
        wrongQuestion.setCreateTime(new Date());
        wrongQuestion.setUpdateTime(new Date());
        if(null != componentQuestion){
            wrongQuestion.setQuestionCode(componentQuestion.getId());
            wrongQuestion.setComplexQuestionCode(smallQuestion.getId());
            wrongQuestion.setIsComplex(1);
            wrongQuestion.setQuestionType(componentQuestion.getType());
            wrongQuestion.setComplexQuestionStem(componentQuestion.getStem());
            wrongQuestion.setQuestionOption(componentQuestion.getQuesOption());
            wrongQuestion.setQuestionAnalyze(componentQuestion.getQuesAnalyze());
            wrongQuestion.setAudioAnalyzePath(componentQuestion.getVoice());
        }else {
            wrongQuestion.setQuestionCode(smallQuestion.getId());
            wrongQuestion.setIsComplex(0);
            wrongQuestion.setQuestionType(smallQuestion.getType());
            wrongQuestion.setQuestionOption(smallQuestion.getQuesOption());
            wrongQuestion.setAudioAnalyzePath(smallQuestion.getAudioAnalyzePath());
            wrongQuestion.setQuestionAnalyze(smallQuestion.getQuesAnalyze());
        }
        wrongQuestion.setQuestionStem(smallQuestion.getStem());
        wrongQuestion.setBaseProduction(JSONObject.toJSONString(baseTrees));
        wrongQuestion.setRightAnswer(answerSheetQuestionDto.getRightAnswer());
        return wrongQuestion;
    }


    private PaperSystem getPaperSystem(AnswerSheetDto asd) {
        try {
            String paperJson = baseResourceClient.getPapper(asd.getPaperCode());
            return JSONObject.parseObject(paperJson, PaperSystem.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DiagnosisStudentAnswerRecordDto> getAnswerRecordByDiagnosisCodes(List<String> codes) throws Exception {
        if(CollectionUtils.isEmpty(codes)) return null;
        Map queryMap = new HashMap();
        queryMap.put("answerRecordCodes",codes);
        List<DiagnosisStudentAnswerRecordPo> list = diagnosisStudentAnswerRecordService.getListByAnswerRecordCodes(queryMap);
        List<DiagnosisStudentAnswerRecordDto> diagnosisStudentAnswerRecordDtos = PageHelperUtil.converterList(list, DiagnosisStudentAnswerRecordDto.class);
        return diagnosisStudentAnswerRecordDtos;
    }
}
