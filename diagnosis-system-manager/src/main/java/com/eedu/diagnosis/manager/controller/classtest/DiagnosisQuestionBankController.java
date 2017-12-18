package com.eedu.diagnosis.manager.controller.classtest;

import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.inclass.test.api.dto.DiagnosisBaseQuestionDto;
import com.eedu.diagnosis.inclass.test.api.dto.DiagnosisQuestionBankDto;
import com.eedu.diagnosis.inclass.test.api.service.DiagnosisBaseQuestionService;
import com.eedu.diagnosis.inclass.test.api.service.DiagnosisQuestionBankService;
import com.eedu.diagnosis.manager.model.request.ClassTest.DiagnosisBaseQuestionModel;
import com.eedu.diagnosis.manager.model.request.ClassTest.DiagnosisQuestionBankModel;
import com.eedu.diagnosis.manager.model.request.Resource.KnowTreeListModel;
import com.eedu.diagnosis.manager.model.request.Resource.QuestionModel;
import com.eedu.diagnosis.manager.model.request.Resource.QuestionsModel;
import com.eedu.diagnosis.manager.model.request.ResourceBaseModel;
import com.eedu.diagnosis.manager.responsecode.BaseResponse;
import com.eedu.diagnosis.manager.responsecode.ResponseCode;
import com.eedu.diagnosis.manager.service.ClassTestService;
import com.eeduspace.uuims.comm.util.HTTPClientUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 *   模块:题库
 *  @author  zz
 */

@RestController
@RequestMapping("/question/bank")
public class  DiagnosisQuestionBankController {
    private final Logger logger = LoggerFactory.getLogger(DiagnosisQuestionBankController.class);
    @Autowired
    private ClassTestService classTestService ;
    @Autowired
    private DiagnosisBaseQuestionService diagnosisBaseQuestionService ;//基础题
    @Autowired
    private DiagnosisQuestionBankService diagnosisQuestionBankService ;//题库

    @Value("${KNOWLEDGETREE_URL}")
    private  String KNOWLEDGETREE_URL;
    @Value("${QUESTIONS_URL}")
    private  String QUESTIONS_URL ;
    /**
     *  新增题库
     */
    @ResponseBody
    @RequestMapping(value = "/questionBankSave", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse diagnosisQuestionBankSave(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisQuestionBankModel model = JSONObject.parseObject(requestBody, DiagnosisQuestionBankModel.class);
        try {
            if (StringUtils.isBlank(model.getQuestionBookName())) {
                logger.error("DiagnosisQuestionBankController questionBankSave  error Exception：questionBookName is null." + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "questionBookName");
            }
            if(StringUtils.isBlank(model.getTeacherCode())){
                logger.error("DiagnosisQuestionBankController questionBankSave  Exception：teacherCode is null."+ baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "teacherCode");
            }

            List<DiagnosisQuestionBankDto> list = getQuestionBankList(model.getTeacherCode());
            List<DiagnosisQuestionBankDto> collect = list.stream().filter(g -> g.getQuestionBookName().equals(model.getQuestionBookName())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(collect)){
                logger.error("DiagnosisQuestionBankController questionBankSave  error Exception：questionBookName is duplicate." + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_DUPLICATE.toString(), "questionBookName is duplicate");
            }
            baseResponse.setResult(classTestService.diagnosisQuestionBankSave(model));
            } catch (Exception e) {
                logger.error(requestId + "DiagnosisQuestionBankController  questionBankSave  error: ", e);
                return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
            }
            return baseResponse;
    }
    /**
     *  修改题库
     */
    @ResponseBody
    @RequestMapping(value = "/questionBankUpdate", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse questionBankUpdate(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisQuestionBankModel model = JSONObject.parseObject(requestBody, DiagnosisQuestionBankModel.class);

        try {
        if (StringUtils.isBlank(model.getQuestionBookCode())) {
            logger.error("DiagnosisQuestionBankController questionBankUpdate  error Exception：questionBookName is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "questionBookName");
        }
        if(StringUtils.isBlank(model.getTeacherCode())){
            logger.error("DiagnosisQuestionBankController questionBankUpdate  Exception：teacherCode is null."+ baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "teacherCode");
        }
            if (StringUtils.isNotBlank(model.getQuestionBookName())) {
                List<DiagnosisQuestionBankDto> list = getQuestionBankList(model.getTeacherCode());
                List<DiagnosisQuestionBankDto> collect = list.stream().filter(g -> g.getQuestionBookName().equals(model.getQuestionBookName()) && !g.getQuestionBookCode().equals(model.getQuestionBookCode())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(collect)){
                    logger.error("DiagnosisQuestionBankController questionBankUpdate  error Exception：questionBookName is duplicate." + baseResponse.getRequestId());
                    return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_DUPLICATE.toString(), "questionBookName is duplicate");
                }
            }
            baseResponse.setResult(classTestService.questionBankUpdate(model));
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisQuestionBankController  questionBankUpdate  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    /**
     *  删除题库
     */
    @ResponseBody
    @RequestMapping(value = "/questionBankDelete", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse diagnosisQuestionBankDelete(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisQuestionBankModel model = JSONObject.parseObject(requestBody, DiagnosisQuestionBankModel.class);
        if (StringUtils.isBlank(model.getQuestionBookCode())) {
            logger.error("DiagnosisQuestionBankController questionBankDelete  error Exception：questionBookName is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "questionBookName");
        }
        try {
            DiagnosisBaseQuestionDto dto = new DiagnosisBaseQuestionDto();
            dto.setQuestionBookCode(model.getQuestionBookCode());
            List<DiagnosisBaseQuestionDto> list = diagnosisBaseQuestionService.selectDiagnosisBaseQuestionList(dto);
            if (!CollectionUtils.isEmpty(list)) {
                logger.error("DiagnosisQuestionBankController questionBankDelete  error Exception：resource is in use." + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_INUSE.toString(), "resource is in use");
            }
            baseResponse.setResult(classTestService.diagnosisQuestionBankDelete(model));
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisQuestionBankController  questionBankDelete  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    /**
     *  题库列表
     */
    @ResponseBody
    @RequestMapping(value = "/questionBankList", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse diagnosisQuestionBankList(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisQuestionBankModel model = JSONObject.parseObject(requestBody, DiagnosisQuestionBankModel.class);
        if(StringUtils.isBlank(model.getTeacherCode())){
            logger.error("DiagnosisQuestionBankController questionBankList  Exception：teacherCode is null."+ baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "teacherCode");
        }
        try {
            baseResponse.setResult(classTestService.diagnosisQuestionBankList(model));
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisQuestionBankController  questionBankList  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    /**
     *  基础题获取知识点
     */
    @ResponseBody
    @RequestMapping(value = "/getKnowledge", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse diagnosisBaseQuestionByKnowledge(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            ResourceBaseModel model = JSONObject.parseObject(requestBody, ResourceBaseModel.class);
            if(StringUtils.isBlank(model.getGradeCode())){
                logger.error("DiagnosisQuestionBankController getKnowledge Exception：GradeCode is null."+baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeCode");
            }
            if(StringUtils.isBlank(model.getSubjectCode())){
                logger.error("DiagnosisQuestionBankController getKnowledge error Exception：SubjectCode is null."+baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
            }
            if(StringUtils.isBlank(model.getBooktypeCode())){
                logger.error("DiagnosisQuestionBankController getKnowledge error Exception：booktypeCode  is null."+baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "booktypeCode ");
            }
            model.setKnowledgeType("1");
            String url =  KNOWLEDGETREE_URL;
            //从资源库获取
            String result = HTTPClientUtils.httpPostRequestJson(url, JSONObject.toJSONString(model));
                baseResponse.setResult(JSONObject.parseObject(result, KnowTreeListModel.class));
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisQuestionBankController  getKnowledge  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    /**
     *  基础题获取易教题库
     */
    @ResponseBody
    @RequestMapping(value = "/getQuestionByKnowledge", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse diagnosisBaseQuestionByByYiJiao(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            ResourceBaseModel model = JSONObject.parseObject(requestBody, ResourceBaseModel.class);
            if (StringUtils.isBlank(model.getGradeCode())) {
                logger.error("DiagnosisQuestionBankController  getQuestionByKnowledge Exception：GradeCode is null." + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeCode");
            }
            if (StringUtils.isBlank(model.getSubjectCode())) {
                logger.error("DiagnosisQuestionBankController  getQuestionByKnowledge Exception：subjectCode is null." + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
            }
            if (StringUtils.isBlank(model.getBooktypeCode())) {
                logger.error("DiagnosisQuestionBankController  getQuestionByKnowledge Exception：booktypeCode is null." + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "booktypeCode");
            }
            if (StringUtils.isBlank(model.getKnowledgeCode())) {
                logger.error("DiagnosisQuestionBankController  getQuestionByKnowledge Exception：knowledgeCode is null." + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "knowledgeCode");
            }
            if (null == model.getPageNum()) {
                logger.error("DiagnosisQuestionBankController  getQuestionByKnowledge Exception：pageNum is null." + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "pageNum");
            }
            if (null == model.getPageSize()) {
                logger.error("DiagnosisQuestionBankController  getQuestionByKnowledge Exception：pageSize is null." + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "pageSize");
            }
            String type = getType(model.getSubjectCode());
            if (type != null){
                model.setType(type);
            }
            QuestionsModel questionsModel = new QuestionsModel();
            String result = HTTPClientUtils.httpPostRequestJson(QUESTIONS_URL, JSONObject.toJSONString(model));
            //result =result.replaceAll("\"","\"" ).replaceAll("&nbsp;"," " );
            if (StringUtils.isBlank(result)){
                baseResponse.setResult(questionsModel);
                return  baseResponse;
            }
            questionsModel = JSONObject.parseObject(result, QuestionsModel.class);
            if (null == questionsModel || questionsModel.getDatas() ==null || CollectionUtils.isEmpty(questionsModel.getDatas().getData()) ){
                baseResponse.setResult(new QuestionsModel());
                return  baseResponse;
            }
            List<QuestionModel> data = questionsModel.getDatas().getData();
            for (QuestionModel questionModel:data){
                questionModel.setQuesOption(questionModel.getQuesOption().replaceAll("\"","\"" ).replaceAll("&nbsp;","" ).trim());
                questionModel.setQuesAnalyze(questionModel.getQuesAnalyze().replaceAll("\"","\"" ).replaceAll("&nbsp;"," " ).trim());
            }
            baseResponse.setResult(questionsModel);
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisQuestionBankController  baseQuestionByYiJiao  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    /**
     *  根据题库code获取基础题
     */
    @ResponseBody
    @RequestMapping(value = "/baseQuestionList", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse baseQuestionList(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisBaseQuestionModel model = JSONObject.parseObject(requestBody, DiagnosisBaseQuestionModel.class);
        try {
            if (StringUtils.isBlank(model.getQuestionBookCode())) {
                logger.error("DiagnosisQuestionBankController baseQuestionList  error Exception：questionBookName is null." + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "questionBookName");
            }
            baseResponse.setResult(classTestService.baseQuestionListByQuestionBankCode(model));
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisQuestionBankController  baseQuestionList  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    /**
     *  新增基础题
     */
    @ResponseBody
    @RequestMapping(value = "/baseQuestionSave", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse saveDiagnosisBaseQuestion(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisBaseQuestionModel model = JSONObject.parseObject(requestBody, DiagnosisBaseQuestionModel.class);
        try {
            if (StringUtils.isBlank(model.getQuestionBookCode())) {
                logger.error("DiagnosisQuestionBankController baseQuestionSave  error Exception：questionBookCode is null." + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "questionBookCode");
            }
            if (StringUtils.isBlank(model.getBaseName())) {
                logger.error("DiagnosisQuestionBankController baseQuestionSave  Exception：baseName is null."+ baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "baseName");
            }
            if (StringUtils.isBlank(model.getSource())) {
                logger.error("DiagnosisQuestionBankController baseQuestionSave  Exception：source is null."+ baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "source");
            }
            if (StringUtils.isBlank(model.getRightAnswer())) {
                logger.error("DiagnosisQuestionBankController baseQuestionSave  Exception：rightAnswer is null."+ baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "rightAnswer");
            }
            if (StringUtils.isBlank(model.getKnowledges())) {
                logger.error("DiagnosisQuestionBankController baseQuestionSave  Exception：knowledges is null."+ baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "knowledges");
            }
            List<DiagnosisBaseQuestionDto> list =  getBaseQuestionList(model.getQuestionBookCode());
            List<DiagnosisBaseQuestionDto> collect = list.stream().filter(g -> g.getBaseName().equals(model.getBaseName())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(collect)) {
                logger.error("DiagnosisQuestionBankController baseQuestionSave  error Exception：baseName is duplicate." + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_DUPLICATE.toString(), "baseName is duplicate");
            }
            baseResponse.setResult(classTestService.diagnosisBaseQuestionSave(model));
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisQuestionBankController  baseQuestionSave  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    /**
     *  修改基础题
     */
    @ResponseBody
    @RequestMapping(value = "/baseQuestionUpdate", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse baseQuestionUpdate(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisBaseQuestionModel model = JSONObject.parseObject(requestBody, DiagnosisBaseQuestionModel.class);
        try {
            if (StringUtils.isBlank(model.getBaseCode())) {
                logger.error("DiagnosisQuestionBankController baseQuestionUpdate  Exception：baseCode is null."+ baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "baseCode");
            }
            if (StringUtils.isBlank(model.getQuestionBookCode())) {
                logger.error("DiagnosisQuestionBankController baseQuestionUpdate  error Exception：questionBookCode is null." + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "questionBookCode");
            }
            if (StringUtils.isNotBlank(model.getBaseName())) {
                List<DiagnosisBaseQuestionDto> list = getBaseQuestionList(model.getQuestionBookCode());
                List<DiagnosisBaseQuestionDto> collect = list.stream().filter(g -> g.getBaseName().equals(model.getBaseName()) && !g.getBaseCode().equals(model.getBaseCode())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(collect)) {
                    logger.error("DiagnosisQuestionBankController baseQuestionSave  error Exception：baseName is duplicate." + baseResponse.getRequestId());
                    return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_DUPLICATE.toString(), "baseName is duplicate");
                }
            }
            baseResponse.setResult(classTestService.baseQuestionUpdate(model));
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisQuestionBankController  baseQuestionUpdate  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    /**
     *  删除基础题关习
     */
    @ResponseBody
    @RequestMapping(value = "/baseQuestionDelete", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse baseQuestionDelete(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisBaseQuestionModel model = JSONObject.parseObject(requestBody, DiagnosisBaseQuestionModel.class);
        try {
            if (StringUtils.isBlank(model.getBaseCode())) {
                logger.error("DiagnosisQuestionBankController baseQuestionDelete  Exception：baseCode is null."+ baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "baseCode");
            }
            baseResponse.setResult(classTestService.deleteDiagnosisBaseQuestion(model.getBaseCode(),model.getQuestionBookCode()));
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisQuestionBankController  baseQuestionDelete  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    public String getType(String subjectCode) {

      if (subjectCode.equals("1")){
          return  "402881b951dd6d230151dd8cf375000c";
      } if (subjectCode.equals("2")){
          return  "402881c5529ab64101529b4b3ea2002a";
      } if (subjectCode.equals("3")){
          return  "402881b951dd91520151dd9e4cbc0007";
      } if (subjectCode.equals("4")){
          return  "402881c5529ab64101529b4ffa080030";
      } if (subjectCode.equals("5")){
          return  "402881c5529ab64101529b4e206c002b";
      } if (subjectCode.equals("6")){
          return  "402881c5529ab64101529b4e8fc2002c";
      } if (subjectCode.equals("7")){
          return  "402881c5529ab64101529b4eb75f002d";
      } if (subjectCode.equals("8")){
          return  "402881c5529ab64101529b4f2207002e";
      } if (subjectCode.equals("9")){
          return  "402881c5529ab64101529b4f458e002f";
      }if (subjectCode.equals("10")){
            return  "8a2a74685c0f70a2015c2e9828482a83";
      }if (subjectCode.equals("11")){
            return  "8a2a74685c0f70a2015c2e965dd52a30";
        }
        return null;
    }

    private  List<DiagnosisQuestionBankDto>  getQuestionBankList(String teacherCode) throws Exception {
        DiagnosisQuestionBankDto dto = new DiagnosisQuestionBankDto();
        dto.setTeacherCode(teacherCode);
        List<DiagnosisQuestionBankDto> list = diagnosisQuestionBankService.selectDiagnosisQuestionBankList(dto);
        return list;
    }
    private List<DiagnosisBaseQuestionDto> getBaseQuestionList(String questionBookCode) throws Exception {
        DiagnosisBaseQuestionDto dto = new DiagnosisBaseQuestionDto();
        dto.setQuestionBookCode(questionBookCode);
        List<DiagnosisBaseQuestionDto> list = diagnosisBaseQuestionService.selectDiagnosisBaseQuestionList(dto);
        return list;
    }



}
