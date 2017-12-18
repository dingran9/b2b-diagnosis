package com.eedu.diagnosis.manager.controller.sysytem;

import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.common.service.redisService.RedisClientTemplate;
import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.common.utils.PageHelperUtil;
import com.eedu.diagnosis.manager.model.request.DiagnosisAppUpdateModel;
import com.eedu.diagnosis.manager.model.response.DiagnosisAppUpdateVo;
import com.eedu.diagnosis.manager.responsecode.BaseResponse;
import com.eedu.diagnosis.manager.responsecode.ResponseCode;
import com.eedu.diagnosis.paper.api.dto.DiagnosisAppUpdateDto;
import com.eedu.diagnosis.paper.api.openService.BasePaperOpenService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * app更新版本
 */
@RestController
@RequestMapping("/diagnosis/app")
public class DiagnosisAppUpdateController {

    private final Logger logger = LoggerFactory.getLogger(DiagnosisAppUpdateController.class);

    @Autowired
    private BasePaperOpenService  basePaperOpenServiceImpl;
    @Autowired
    private RedisClientTemplate redisClientTemplate;
	/**
     * app下载
     */
    @RequestMapping(value = "/updateApk",method = RequestMethod.POST,  produces = "application/json; charset=utf-8")
    public BaseResponse download(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisAppUpdateModel model = JSONObject.parseObject(requestBody, DiagnosisAppUpdateModel.class);
		try {
            Integer pageNum = model.getPageNum();
            Integer pageSize =model.getPageSize();
			DiagnosisAppUpdateDto dto = new  DiagnosisAppUpdateDto();
    		BeanUtils.copyProperties(model,dto);
            Order order = new Order("create_time", Order.Direction.desc);
            PageInfo<DiagnosisAppUpdateDto> pageInfo = basePaperOpenServiceImpl.diagnosisAppUpdateList(dto,pageNum,pageSize,order);
            PageInfo<DiagnosisAppUpdateVo> result = PageHelperUtil.pageInfoConverter(pageInfo, DiagnosisAppUpdateVo.class);
            baseResponse.setResult(result);
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisAppUpdateController  app  download   error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    /**
     * app上传
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
            DiagnosisAppUpdateDto dto = new  DiagnosisAppUpdateDto();
            BeanUtils.copyProperties(model,dto);
            String result = basePaperOpenServiceImpl.addApkUrl(dto);
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
    @RequestMapping(value = "/getIosVersion",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public BaseResponse getIosVersion(@RequestParam("requestId") String requestId,@RequestBody String requestBody){
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            DiagnosisAppUpdateModel model = JSONObject.parseObject(requestBody, DiagnosisAppUpdateModel.class);
            if(null == model.getType()){
                logger.error("DiagnosisAppUpdateController  app  addApkUrl getIosVersion：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".type");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "type");
            }
            String version = getVersion(model.getType());
            if (model.getType()==2){
                if(StringUtils.isBlank(version)){
                    redisClientTemplate.set("diagnosis_system_manager_teacher_ios_version", "1");
                    version="1";
                }
            }
            if (model.getType()==3){
                if(StringUtils.isBlank(version)){
                    redisClientTemplate.set("diagnosis_system_manager_principal_ios_version", "1");
                    version="1";
                }
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
    @RequestMapping(value = "/updateIosVersion",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public BaseResponse updateIosVersion(@RequestParam("requestId") String requestId,@RequestBody String requestBody){
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            DiagnosisAppUpdateModel model = JSONObject.parseObject(requestBody, DiagnosisAppUpdateModel.class);
            if(null == model.getType()){
                logger.error("DiagnosisAppUpdateController  app  addApkUrl getIosVersion：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".type");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "type");
            }
            String version = getVersion(model.getType());
            String newVersion = String.valueOf(Integer.parseInt(version) + 1);
            if (model.getType()==2){
                redisClientTemplate.set("diagnosis_system_manager_teacher_ios_version", newVersion);
            }
            if (model.getType()==3){
                redisClientTemplate.set("diagnosis_system_manager_principal_ios_version", newVersion);
            }
            baseResponse.setResult(newVersion);
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisAppUpdateController  app  updateIosVersion   error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    private  String  getVersion(Integer type){
        String version = "";
        if (type==2){
            version = redisClientTemplate.get("diagnosis_system_manager_teacher_ios_version");
        }
        if (type==3){
            version = redisClientTemplate.get("diagnosis_system_manager_principal_ios_version");
        }
        if(StringUtils.isBlank(version)){
            version="1";
        }
         return  version;
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
            DiagnosisAppUpdateDto dto = basePaperOpenServiceImpl.checkAppVersion(appType, type);
            baseResponse.setResult(dto);
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisAppUpdateController  checkAppVersion   error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

}
