package com.eedu.diagnosis.protal.controller;

import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.common.enumration.AppUpdateEnum;
import com.eedu.diagnosis.common.service.redisService.RedisClientTemplate;
import com.eedu.diagnosis.common.utils.PageHelperUtil;
import com.eedu.diagnosis.paper.api.dto.DiagnosisAppUpdateDto;
import com.eedu.diagnosis.paper.api.openService.BasePaperOpenService;
import com.eedu.diagnosis.protal.model.request.DiagnosisAppUpdateModel;
import com.eedu.diagnosis.protal.model.response.DiagnosisAppUpdateVo;
import com.eedu.diagnosis.protal.responsecode.BaseResponse;
import com.eedu.diagnosis.protal.responsecode.ResponseCode;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * app更新版本
 */
@RestController
@RequestMapping("/diagnosis/app")
public class DiagnosisAppUpdateController {

    private final Logger logger = LoggerFactory.getLogger(DiagnosisAppUpdateController.class);

    @Autowired
	private BasePaperOpenService basePaperOpenService;
    @Autowired
    private RedisClientTemplate redisClientTemplate;

	/**
     * 安卓app下载
     */
    @RequestMapping(value = "/updateApk",method = RequestMethod.POST,  produces = "application/json; charset=utf-8")
    public BaseResponse download(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisAppUpdateModel model = JSONObject.parseObject(requestBody, DiagnosisAppUpdateModel.class);
        if(null == model.getType()){
            logger.error("DiagnosisAppUpdateController  app  download Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".type");
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "type");
        }
		try {
			DiagnosisAppUpdateDto dto = new  DiagnosisAppUpdateDto();
    		BeanUtils.copyProperties(model,dto);
            PageInfo<DiagnosisAppUpdateDto> pageInfo = basePaperOpenService.diagnosisAppUpdateList(dto,null,null,null);
            PageInfo<DiagnosisAppUpdateVo> result = PageHelperUtil.pageInfoConverter(pageInfo, DiagnosisAppUpdateVo.class);
            List<DiagnosisAppUpdateVo> diagnosisAppUpdateList = result.getList();
            if (!CollectionUtils.isEmpty(diagnosisAppUpdateList)) {
                baseResponse.setResult(diagnosisAppUpdateList.get(0));
            }
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisAppUpdateController  app  download   error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    /**
     * 安卓app上传
     */
    @RequestMapping(value = "/addApkUrl",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public BaseResponse upload(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
		DiagnosisAppUpdateModel model = JSONObject.parseObject(requestBody, DiagnosisAppUpdateModel.class);
		try {
            if(null == model.getType()){
                logger.error("DiagnosisAppUpdateController  app  addApkUrl Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".type");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "type");
            }
            if(StringUtils.isBlank( model.getDownUrl())){
                logger.error("DiagnosisAppUpdateController  app  addApkUrl Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".downUrl");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "downUrl");
            }
            if(null == model.getAppVersion()){
                logger.error("DiagnosisAppUpdateController  app  addApkUrl Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".appVersion");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "appVersion");
            }
            if(null == model.getNecessary()){
                logger.error("DiagnosisAppUpdateController  app  addApkUrl Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".necessary");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "necessary");
            }
            model.setAvailable(AppUpdateEnum.ABLE.getValue());
            DiagnosisAppUpdateDto dto = new  DiagnosisAppUpdateDto();
            BeanUtils.copyProperties(model,dto);
            String result = basePaperOpenService.addApkUrl(dto);
            baseResponse.setResult( result != null && !"".equals(result));
		}catch (Exception e) {
            logger.error(requestId + "DiagnosisAppUpdateController  app  addApkUrl   error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * IOS  app下载  查询
     */
    @RequestMapping(value = "/getIosVersion", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public BaseResponse getIosVersion(@RequestParam("requestId") String requestId){
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            String version = redisClientTemplate.get("diagnosis_protal_student_ios_version");
            if(StringUtils.isBlank(version)){
                String diagnosis_protal_student_ios_version = redisClientTemplate.set("diagnosis_protal_student_ios_version", "1");
                System.out.println(diagnosis_protal_student_ios_version);
                version="1";
            }
            baseResponse.setResult(version);
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisAppUpdateController  app  getIosVersion   error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    /**
     * IOS  app下载 修改
     */
    @RequestMapping(value = "/updateIosVersion", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public BaseResponse updateIosVersion(@RequestParam("requestId") String requestId){
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            String version = redisClientTemplate.get("diagnosis_protal_student_ios_version");
            if(StringUtils.isBlank(version)){
                version="1";
            }
            String newVersion = String.valueOf(Integer.parseInt(version) + 1);
            redisClientTemplate.set("diagnosis_protal_student_ios_version", newVersion);
            baseResponse.setResult(newVersion);
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisAppUpdateController  app  updateIosVersion   error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * APP版本检查
     * @param requestId
     * @return
     */
    @RequestMapping(value = "/checkAppVersion", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse checkAppVersion(@RequestParam("requestId") String requestId,@RequestBody String requestBody){
        BaseResponse baseResponse = new BaseResponse(requestId);

        try {
            JSONObject jsonObject = JSONObject.parseObject(requestBody);
            String appType = jsonObject.getString("appType");
            String type = jsonObject.getString("type");
            if(StringUtils.isBlank(appType)){
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "appType");
            }
            if(StringUtils.isBlank(type)){
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "type");
            }
            DiagnosisAppUpdateDto dto = basePaperOpenService.checkAppVersion(appType, type);
            baseResponse.setResult(dto);
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisAppUpdateController  checkAppVersion   error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }


}
