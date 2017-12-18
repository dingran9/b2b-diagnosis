package com.eedu.diagnosis.protal.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eedu.auth.beans.AuthGroupBean;
import com.eedu.auth.beans.AuthUserBean;
import com.eedu.auth.beans.enums.ConstantEnum;
import com.eedu.auth.service.AuthUserManagerService;
import com.eedu.auth.service.AuthUserService;
import com.eedu.diagnosis.common.enumration.EventSourceEnum;
import com.eedu.diagnosis.common.service.redisService.RedisClientTemplate;
import com.eedu.diagnosis.common.utils.GsonUtils;
import com.eedu.diagnosis.protal.client.UserClient;
import com.eedu.diagnosis.protal.model.request.DiagnosisFavoriteModele;
import com.eedu.diagnosis.protal.model.request.DiagnosisFeedbackModel;
import com.eedu.diagnosis.protal.model.response.DiagnosisFavoriteVo;
import com.eedu.diagnosis.protal.model.response.QuestionAnalyzeModel;
import com.eedu.diagnosis.protal.model.response.QuestionOptionModel;
import com.eedu.diagnosis.protal.model.user.UserConvert;
import com.eedu.diagnosis.protal.model.user.UserRequestModel;
import com.eedu.diagnosis.protal.responsecode.BaseResponse;
import com.eedu.diagnosis.protal.responsecode.ResponseCode;
import com.eedu.diagnosis.protal.service.UserService;
import com.eedu.diagnosis.util.SensitivewordFilter;
import com.eeduspace.uuims.api.response.login.LoginResponse;
import com.eeduspace.uuims.comm.util.base.ValidateUtils;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


/**
 * Author: zz
 * Description:  用户操作管理
 */

@RestController
@RequestMapping("/user")
public class UserManagementController {

    private final Logger logger = LoggerFactory.getLogger(UserManagementController.class);
    private Gson gson=new Gson();

    @Autowired
    private UserClient userClient;
    
    @Autowired
    private UserService userService;
 
    @Autowired
    private UserConvert userConvert;

    @Autowired
    private RedisClientTemplate redisClientTemplate;
    
    @Autowired
	private AuthUserService authUserService;
    
    @Autowired
	private AuthUserManagerService authUserManagerService;
    

    @Value("${diagnosis.protal.register.sms.expirese}")
	private String registerSmsExpirese;
   
    
    /**
     * 用户登录
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse login( @RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            UserRequestModel userRequestModel = gson.fromJson(requestBody, UserRequestModel.class);
            if(StringUtils.isBlank(userRequestModel.getPassword())){
                logger.error("UserManagementController login Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".password");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "password");
            }
//            if (StringUtils.isBlank(userRequestModel.getPhone())) {
//                logger.error("UserManagementController login Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".Phone");
//                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "Phone");
//            }
//            // 验证数据格式
//            if (!ValidateUtils.isMobile(userRequestModel.getPhone())) {
//                logger.error("UserManagementController login ValidateMobile Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_INVALID.toString()+".phone");
//                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "phone");
//            }
            if(userRequestModel.getEquipmentType()==null||userRequestModel.getEquipmentType().equals("")){
                userRequestModel.setEquipmentType("Web");
            }
            LoginResponse loginResponse = userService.login(userRequestModel,false,baseResponse);
            BeanUtils.copyProperties(loginResponse,baseResponse);
            baseResponse.setRequestId(baseResponse.getRequestId());
            baseResponse.setResult(loginResponse.getResult());
           return baseResponse;
        } catch (Exception e) {
            logger.error("login error:{}", e);
            return BaseResponse.setResponse(new BaseResponse(),ResponseCode.SERVICE_ERROR.toString());
        }
    }
    /**
     * 用户添加与注册
     * 向 uuims 注册用户
     * @return
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse registerOrCreate(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            UserRequestModel userModel = gson.fromJson(requestBody, UserRequestModel.class);

            if (StringUtils.isBlank(userModel.getPhone())) {
                logger.error("UserManagementController register Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".phone");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "phone");
            }
            if (StringUtils.isBlank(userModel.getCode())) {
                logger.error("UserManagementController register Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".code");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "code");
            }
            // 验证数据格式
            if (!ValidateUtils.isMobile(userModel.getPhone())) {
                logger.error("UserManagementController register ValidateMobile Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_INVALID.toString()+".phone");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "phone");
            }
            if(StringUtils.isNotBlank(userModel.getEquipmentType())){
                userModel.setEquipmentType(userConvert.converterSourceEquipmentType(userModel.getEquipmentType()).getValue());
            }
            String registerSmsCode = redisClientTemplate.get("diagnosis_protal_register_sms"+userModel.getPhone());
            if ( StringUtils.isBlank(registerSmsCode) || !userModel.getCode().equals(registerSmsCode)) {
            	  logger.error("UserManagementController register Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_INVALID.toString()+".code");
                  return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "the code is fail");
			}
            if(StringUtils.isBlank(userModel.getPassword())){
                logger.error("UserManagementController register Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".password");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "password");
            }
            //验证手机号唯一 去UUIMS 验证 当该手机号未被使用时则返回200
//            BaseResponse clientBaseResponse = userClient.validateByMobile(userModel);
//            if (clientBaseResponse == null || !"Success".equals(clientBaseResponse.getCode())) {
//                logger.error("UserManagementController register  ValidateMobile Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_DUPLICATE.toString()+".phone");
//                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_DUPLICATE.toString(), "phone");
//            }
            AuthUserBean register = userService.register(userModel);
            if (register != null){
                 redisClientTemplate.del("diagnosis_protal_register_sms"+userModel.getPhone());
             }
            baseResponse.setResult(register);
            return baseResponse;
        }catch (Exception e){
            logger.error("UserManagementController register  error:", e);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
    }
    /**
     * 添加手机号码校验是否存在接口
     * @return
     */
    @RequestMapping(value = "/check/phone",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse checkPhone(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            UserRequestModel userModel = gson.fromJson(requestBody, UserRequestModel.class);
            if (StringUtils.isBlank(userModel.getPhone())) {
                logger.error("UserManagementController check phone Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".phone");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "phone");
            }
            // 验证数据格式
            if (!ValidateUtils.isMobile(userModel.getPhone())) {
                logger.error("UserManagementController check phone ValidateMobile Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_INVALID.toString()+".phone");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "phone");
            }
            BaseResponse clientBaseResponse = null;
            if (userModel.getType() != null && !userModel.getType().equals("") && "1".equals(userModel.getType())) {
            	clientBaseResponse = userClient.describeByPhone(userModel);
                if (clientBaseResponse == null || !"Success".equals(clientBaseResponse.getCode())) {
                	  logger.error("UserManagementController edit phone chack student ValidateMobile Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_NOTFOUND.toString()+".clientBaseResponse");
                      return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "clientBaseResponse");
                }
			}else {
				//验证手机号唯一 去UUIMS 验证 当该手机号未被使用时则返回200
	               clientBaseResponse = userClient.validateByMobile(userModel);
	            if (clientBaseResponse == null || !"Success".equals(clientBaseResponse.getCode())) {
	                logger.error("UserManagementController check phone  ValidateMobile Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_DUPLICATE.toString()+".phone");
	                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_DUPLICATE.toString(), "phone");
	            }
			}
            baseResponse.setResult(true);
            return baseResponse;
        }catch (Exception e){
            logger.error("UserManagementController register  error:", e);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
    }
    /**
     * 用户添加与注册发送验证码
     * 向 uuims 注册用户
     * @return
     */
    @RequestMapping(value = "/register/sms",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse registerSMS(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            UserRequestModel userModel = gson.fromJson(requestBody, UserRequestModel.class);
            if (StringUtils.isBlank(userModel.getPhone())) {
                logger.error("register sms Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".phone");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "phone");
            }
            // 验证数据格式
            if (!ValidateUtils.isMobile(userModel.getPhone())) {
                logger.error("register sms  ValidateMobile Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_INVALID.toString()+".phone");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "phone");
            }
            //验证手机号唯一 去UUIMS 验证 当该手机号未被使用时则返回200
            BaseResponse clientBaseResponse = userClient.validateByMobile(userModel);
            if (clientBaseResponse == null || !"Success".equals(clientBaseResponse.getCode())) {
                logger.error("register sms ValidateMobile Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_DUPLICATE.toString()+".phone");
                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_DUPLICATE.toString(), "phone");
            }
            
//            String code = userService.sendSms(userModel.getPhone(), userModel.getSmsType());
//            if (StringUtils.isBlank(code)) {
//            	  logger.error("register sms ValidateMobile Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_NOTFOUND.toString()+".code");
//                  return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "code");
//			}
//            redisClientTemplate.setex("diagnosis_protal_register_sms"+userModel.getPhone(), Integer.parseInt(registerSmsExpirese), code);
            sendToSms(userModel, EventSourceEnum.REGISTER_SMS );
            
            baseResponse.setResult(true);
            return baseResponse;
        }catch (Exception e){
            logger.error("register sms error:", e);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
    }



    /**
     * 登出系统
     */
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse logOut(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse=new BaseResponse(requestId);
        try {
            UserRequestModel userRequestModel = gson.fromJson(requestBody, UserRequestModel.class);
            if(StringUtils.isBlank(userRequestModel.getUserCode())){
                logger.error("UserManagementController logOut Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".userCode");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userCode");
            }
            String userToken =  redisClientTemplate.get("diagnosis_protal_"+userRequestModel.getUserCode());
            if(StringUtils.isNotBlank(userToken)){
                redisClientTemplate.del(userToken);
            }
            redisClientTemplate.del(userRequestModel.getUserCode());
            return baseResponse;
        } catch (Exception e) {
            logger.error("UserManagementController logOut error:{}", e);
            return BaseResponse.setResponse(new BaseResponse(),ResponseCode.SERVICE_ERROR.toString());
        }
    }
    
    /**
     * 校验原密码
     */
    @RequestMapping(value = "/chack/oldPwd",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse chackOldPwd(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse=new BaseResponse(requestId);
        try {
            UserRequestModel userRequestModel = gson.fromJson(requestBody, UserRequestModel.class);
            if(StringUtils.isBlank(userRequestModel.getUserCode())){
                logger.error("UserManagementController chackOldPwd Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".userCode");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userCode");
            }
            if (StringUtils.isBlank(userRequestModel.getPassword())) {
                logger.error("UserManagementController chackOldPwd phone  Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".password");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "password");
            }
            if(userRequestModel.getEquipmentType()==null||userRequestModel.getEquipmentType().equals("")){
                userRequestModel.setEquipmentType("Web");
            }
            BaseResponse  response = userService.chackOldPwd(userRequestModel,baseResponse);
            return response;
        } catch (Exception e) {
            logger.error("UserManagementController chackOldPwd error:{}", e);
            return BaseResponse.setResponse(new BaseResponse(),ResponseCode.SERVICE_ERROR.toString());
        }
    }
    /**
     * 修改密码
     *   需要用户自己的ak，sk
     */
    @RequestMapping(value = "/pwd/edit",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse editPwd(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse=new BaseResponse(requestId);
        try {
            UserRequestModel userRequestModel = gson.fromJson(requestBody, UserRequestModel.class);
            if(StringUtils.isBlank(userRequestModel.getUserCode())){
                logger.error("UserManagementController editPwd Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".userCode");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userCode");
            }
            if (StringUtils.isBlank(userRequestModel.getPassword())) {
                logger.error("UserManagementController edit phone  Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".password");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "password");
            }
            AuthUserBean t = new AuthUserBean();
            t.setUserId(Integer.parseInt(userRequestModel.getUserCode()));
            List<AuthUserBean> userByCondition = authUserService.getUserByCondition(t);
    	    if (userByCondition==null || userByCondition.size() == 0) {
    	    	 logger.error("UserManagementController editPwd Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_NOTFOUND.toString()+".AuthStudentModel");
                 return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "AuthStudentModel");
    		}
    	    AuthUserBean findModel = userByCondition.get(0);
    	    userRequestModel.setOpenId(findModel.getUserOpenId());
    	    userRequestModel.setUserAccessKey(findModel.getUserAccesskey());
    	    userRequestModel.setUserSecretKey(findModel.getUserSecretkey());
    	    userRequestModel.setToken(userRequestModel.getToken());
    	    userRequestModel.setPassword(userRequestModel.getPassword());
            BaseResponse editPwdResponse= userClient.resetPwd(userRequestModel);
            editPwdResponse.setRequestId(baseResponse.getRequestId());
            return editPwdResponse;
        } catch (Exception e) {
            logger.error("UserManagementController editPwd error:{}", e);
            return BaseResponse.setResponse(new BaseResponse(),ResponseCode.SERVICE_ERROR.toString());
        }
    }
    /**
     * 发送短信
     */
    @RequestMapping(value = "/send/sms",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse sendSMS(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse=new BaseResponse(requestId);
        try {
            UserRequestModel userRequestModel = gson.fromJson(requestBody, UserRequestModel.class);
            if (StringUtils.isBlank(userRequestModel.getPhone())) {
                logger.error("UserManagementController sendSMS Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".phone");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "phone");
            }
            BaseResponse sendSms = userClient.sendSms(userRequestModel);
            sendSms.setRequestId(baseResponse.getRequestId());
            return sendSms;
        } catch (Exception e) {
            logger.error("UserManagementController sendSMS error:{}", e);
            return BaseResponse.setResponse(new BaseResponse(),ResponseCode.SERVICE_ERROR.toString());
        }
    }
    /**
     * 校验短信验证码
     */
    @RequestMapping(value = "/validate/sms",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse validateSms(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse=new BaseResponse(requestId);
        try {
            UserRequestModel userRequestModel = gson.fromJson(requestBody, UserRequestModel.class);
            if (StringUtils.isBlank(userRequestModel.getPhone())) {
                logger.error("UserManagementController validateSms Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".phone");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "phone");
            }
            BaseResponse validateSms = userClient.validateSms(userRequestModel);
            validateSms.setRequestId(baseResponse.getRequestId());
            return validateSms;
        } catch (Exception e) {
            logger.error("UserManagementController validateSms error:{}", e);
            return BaseResponse.setResponse(new BaseResponse(),ResponseCode.SERVICE_ERROR.toString());
        }
    }
    /**
     * 重置密码
     */
    @RequestMapping(value = "/pwd/reset",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse resetPwd(@RequestParam("requestId") String requestId,
                                 @RequestBody String requestBody) {
        BaseResponse baseResponse=new BaseResponse(requestId);
        try {
            UserRequestModel userRequestModel = gson.fromJson(requestBody, UserRequestModel.class);
            if (StringUtils.isBlank(userRequestModel.getPhone())) {
                logger.error("UserManagementController resetPwd Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".phone");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "phone");
            }
//            if (StringUtils.isBlank(userRequestModel.getCode())) {
//                logger.error("resetPwd Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".code");
//                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "code");
//            }
            if (StringUtils.isBlank(userRequestModel.getTicket())) {
                logger.error("UserManagementController resetPwd Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".ticket");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "ticket");
            }
            if (StringUtils.isBlank(userRequestModel.getPassword())) {
                logger.error("UserManagementController resetPwd Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".password");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "password");
            }
            // 验证数据格式
            if (!ValidateUtils.isMobile(userRequestModel.getPhone())) {
                logger.error("UserManagementController resetPwd ValidateMobile Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_INVALID.toString()+".phone");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "phone");
            }
//            BaseResponse validateSms = userClient.validateSms(userRequestModel);
//            if (validateSms == null || !"Success".equals(validateSms.getCode()) || validateSms.getResult()==null) {
//                logger.error("resetPwd sms validateSms Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_NOTFOUND.toString()+".validateSms");
//                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "validateSms");
//            }
            BaseResponse resetPwdResponse= userClient.restPwdSms(userRequestModel);
            resetPwdResponse.setRequestId(baseResponse.getRequestId());
            return resetPwdResponse;
        } catch (Exception e) {
            logger.error("UserManagementController resetPwd error:{}", e);
            return BaseResponse.setResponse(new BaseResponse(),ResponseCode.SERVICE_ERROR.toString());
        }
    }

    /*******************************************修改手机号*****************************************/
    @RequestMapping(value = "/edit/phone/sms",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse editPhoneSms(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            UserRequestModel userModel = gson.fromJson(requestBody, UserRequestModel.class);
            if (StringUtils.isBlank(userModel.getPhone())) {
                logger.error("UserManagementController edit phone sms Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".phone");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "phone");
            }
            // 验证数据格式
            if (!ValidateUtils.isMobile(userModel.getPhone())) {
                logger.error("UserManagementController edit phone sms   ValidateMobile Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_INVALID.toString()+".phone");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "phone");
            }
            BaseResponse clientBaseResponse = null;
            if (userModel.getType().equals("3")) {
                clientBaseResponse = userClient.describeByPhone(userModel);
                if (clientBaseResponse == null || !"Success".equals(clientBaseResponse.getCode())) {
                    logger.error("UserManagementController edit phone chack student ValidateMobile Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_NOTFOUND.toString()+".clientBaseResponse");
                    return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "clientBaseResponse");
                }
            }
            if (userModel.getType().equals("4")) {
                clientBaseResponse = userClient.validateByMobile(userModel);
                if (clientBaseResponse == null || !"Success".equals(clientBaseResponse.getCode())) {
                    logger.error("UserManagementController edit phone chack student ValidateMobile Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_NOTFOUND.toString()+".clientBaseResponse");
                    return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_DUPLICATE.toString(), "clientBaseResponse");
                }
            }
            sendToSms(userModel,EventSourceEnum.EDIT_PHONE_SMS );
            baseResponse.setResult(true);
            return baseResponse;
        }catch (Exception e){
            logger.error("UserManagementController edit phone sms  error:", e);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
    }
   
	/**
     *   校验手机号的同时，校验验证码
     *   需要用户自己的ak，sk
     */
    @RequestMapping(value = "/chack/code",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse chackPhoneCodeByEditPhone(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            UserRequestModel userModel = gson.fromJson(requestBody, UserRequestModel.class);
            if (StringUtils.isBlank(userModel.getCode())) {
                logger.error("UserManagementController edit phone chack  Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".phone");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "phone");
            }
            if (StringUtils.isBlank(userModel.getPhone())) {
                logger.error("UserManagementController edit phone chack Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".phone");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "phone");
            }
            if (null == userModel.getType()) {
                logger.error("UserManagementController edit phone chack Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".type");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "type");
            }
            // 验证数据格式
            if (!ValidateUtils.isMobile(userModel.getPhone())) {
                logger.error("UserManagementController edit phone chack  ValidateMobile Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_INVALID.toString()+".phone");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "phone");
            }
            boolean flag = false;
            BaseResponse clientBaseResponse = null;
            if (userModel.getType().equals("1")) {
            	clientBaseResponse = userClient.describeByPhone(userModel);
                if (clientBaseResponse == null || !"Success".equals(clientBaseResponse.getCode())) {
                	  logger.error("UserManagementController edit phone chack student ValidateMobile Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_NOTFOUND.toString()+".clientBaseResponse");
                      return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "clientBaseResponse");
                }
			}
            if (userModel.getType().equals("2")) {
            	clientBaseResponse = userClient.validateByMobile(userModel);
                if (clientBaseResponse == null || !"Success".equals(clientBaseResponse.getCode())) {
                	  logger.error("UserManagementController edit phone chack student ValidateMobile Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_NOTFOUND.toString()+".clientBaseResponse");
                      return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_DUPLICATE.toString(), "clientBaseResponse");
                }
			}
            String redisBody = redisClientTemplate.get("diagnosis_protal_edit_phone_sms"+userModel.getPhone());
    	    if (redisBody !=null && !"".equals(redisBody) && redisBody.equals(userModel.getCode())) {
    	    	 flag = true;
                 redisClientTemplate.del("diagnosis_protal_edit_phone_sms"+userModel.getPhone());
		    }
            baseResponse.setResult(flag);
            return baseResponse;
        }catch (Exception e){
            logger.error("UserManagementController edit phone chack  error:", e);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
    }
    /**
     * 修改手机号
     *   需要用户自己的ak，sk
     */
    @RequestMapping(value = "/edit/phone",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse editPhone(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse=new BaseResponse(requestId);
        try {
            UserRequestModel userRequestModel = gson.fromJson(requestBody, UserRequestModel.class);
            if (StringUtils.isBlank(userRequestModel.getPhone())) {
                logger.error("UserManagementController edit phone  Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".phone");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "phone");
            }
            if (StringUtils.isBlank(userRequestModel.getCode())) {
                logger.error("UserManagementController edit phone chack  Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".phone");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "phone");
            }
            if (StringUtils.isBlank(userRequestModel.getUserCode())) {
                logger.error("UserManagementController edit phone  Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".userCode");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userCode");
            }
            // 验证数据格式
            if (!ValidateUtils.isMobile(userRequestModel.getPhone())) {
                logger.error("UserManagementController edit phone  -validateMobile Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_INVALID.toString()+".phone");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "phone");
            }
            String registerSmsCode = redisClientTemplate.get("diagnosis_protal_edit_phone_sms"+userRequestModel.getPhone());
            if ( StringUtils.isBlank(registerSmsCode) || !userRequestModel.getCode().equals(registerSmsCode)) {
                logger.error("UserManagementController register Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_INVALID.toString()+".code");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "the code is fail");
            }
           boolean flag = false;
           BaseResponse response =  userService.editPhone(userRequestModel,baseResponse);
           if (response != null && "Success".equals(response.getCode()) ) {
        	   flag = true;
               redisClientTemplate.del("diagnosis_protal_edit_phone_sms"+userRequestModel.getPhone());
           }
           response.setResult(flag);
           return response;
        }catch (Exception e){
            logger.error("UserManagementController edit phone   error:", e);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
    }
 	 /**
      *  收藏
      */
     @RequestMapping(value = "/saveDiagnosisFavoriteService", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
     public BaseResponse saveDiagnosisFavoriteService(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
         BaseResponse baseResponse = new BaseResponse(requestId);
         DiagnosisFavoriteModele model = JSONObject.parseObject(requestBody, DiagnosisFavoriteModele.class);
        
         if (StringUtils.isBlank(model.getUserCode())) {
 			logger.error("UserManagementController saveOrUpdateDiagnosisFavoriteService  error Exception：UserCode is null." + baseResponse.getRequestId());
 			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "UserCode");
 		}
        if(null == model.getSubjectCode()){
          	 logger.error("UserManagementController saveOrUpdateDiagnosisFavoriteService  Exception：subjectCode is null."+ baseResponse.getRequestId());
               return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
           }
        if(null == model.getStageCode()){
          	 logger.error("UserManagementController saveOrUpdateDiagnosisFavoriteService  Exception：stageCode is null."+ baseResponse.getRequestId());
               return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "stageCode");
           }
        if(null == model.getGradeCode()){
         	 logger.error("UserManagementController saveOrUpdateDiagnosisFavoriteService  Exception：gradeCode is null."+ baseResponse.getRequestId());
              return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeCode");
          }
        if(StringUtils.isBlank(model.getQuestionCode())){
         	 logger.error("UserManagementController saveOrUpdateDiagnosisFavoriteService  Exception：questionCode is null."+ baseResponse.getRequestId());
              return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "questionCode");
          }
        if(null == model.getQuestionAnalyze()){
         	 logger.error("UserManagementController saveOrUpdateDiagnosisFavoriteService  Exception：questionAnalyze is null."+ baseResponse.getRequestId());
              return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "questionAnalyze");
          }
       if(null == model.getQuestionOption()){
         	 logger.error("UserManagementController saveOrUpdateDiagnosisFavoriteService  Exception：questionOption is null."+ baseResponse.getRequestId());
              return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "questionOption");
          }
       if(null == model.getQuestionStem()){
        	 logger.error("UserManagementController saveOrUpdateDiagnosisFavoriteService  Exception：questionStem is null."+ baseResponse.getRequestId());
             return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "questionStem");
         }
       if(StringUtils.isBlank(model.getQuestionCode())){
        	 logger.error("UserManagementController saveOrUpdateDiagnosisFavoriteService  Exception：QuestionCode is null."+ baseResponse.getRequestId());
             return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "QuestionCode");
         }
         if(model.getQuestionOption().equals("1") || model.getQuestionOption().equals("{\"optionKey\":\"null\",\"optionValue\":\"null\"}")){
             model.setQuestionOption(null);
         }
         boolean flag = false;
         try {
         	 String result = userService.saveOrUpdateDiagnosisFavoriteService(model);
         	 if (result != null && !"".equals(result)) {
         		flag = true;
			 }
             baseResponse.setResult(flag);
         } catch (Exception e) {
             logger.error(requestId + "UserManagementController  saveOrUpdateDiagnosisFavoriteService  error: ", e);
             return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
         }
         return baseResponse;
     }
     /**
      * 取消 收藏
      */
     @RequestMapping(value = "/updateDiagnosisFavoriteService", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
     public BaseResponse updateDiagnosisFavoriteService(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
         BaseResponse baseResponse = new BaseResponse(requestId);
         DiagnosisFavoriteModele model = JSONObject.parseObject(requestBody, DiagnosisFavoriteModele.class);
        
         if (StringUtils.isBlank(model.getQuestionCode())) {
 			logger.error("UserManagementController updateDiagnosisFavoriteService error Exception：questioncode is null." + baseResponse.getRequestId());
 			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "questioncode");
 		 }
         if (StringUtils.isBlank(model.getUserCode())) {
  			logger.error("UserManagementController updateDiagnosisFavoriteService error Exception：UserCode is null." + baseResponse.getRequestId());
  			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userCode");
  		 }
         boolean flag = false;
         try {
         	 String result = userService.saveOrUpdateDiagnosisFavoriteService(model);
         	 if (result != null && !"".equals(result)) {
         		flag = true;
			 }
             baseResponse.setResult(flag);
         } catch (Exception e) {
             logger.error(requestId + "UserManagementController  updateDiagnosisFavoriteService  error: ", e);
             return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
         }
         return baseResponse;
     }
     
     
     /**
      *  收藏分页查询
      */
     @RequestMapping(value = "/getDiagnosisFavoritePaper", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
     public BaseResponse getDiagnosisFavoritePaper(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
         BaseResponse baseResponse = new BaseResponse(requestId);
         DiagnosisFavoriteModele model = JSONObject.parseObject(requestBody, DiagnosisFavoriteModele.class);
         try {
        	 PageInfo<DiagnosisFavoriteVo>  info= userService.getDiagnosisFavoritePaper(model);
             info.getList().forEach(DiagnosisFavoriteVo -> {
                 List<QuestionOptionModel> questionOptionModels = JSONArray.parseArray(DiagnosisFavoriteVo.getQuestionOption(),QuestionOptionModel.class);
                 DiagnosisFavoriteVo.setQuestionOptions(questionOptionModels);
                 DiagnosisFavoriteVo.setQuestionOption(null);
                 List<QuestionAnalyzeModel> questionAnalyzeModels = JSONArray.parseArray(DiagnosisFavoriteVo.getQuestionAnalyze(),QuestionAnalyzeModel.class);
                 DiagnosisFavoriteVo.setQuestionAnalyzes(questionAnalyzeModels);
                 DiagnosisFavoriteVo.setQuestionAnalyze(null);
             });
        	 baseResponse.setResult(info);
         } catch (Exception e) {
             logger.error(requestId + "UserManagementController  getDiagnosisFavoritePaper  error: ", e);
             return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
         }
         return baseResponse;
     }
     /**
      * 用户反馈
      */
     @RequestMapping(value = "/saveDiagnosisFeedback", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
     public BaseResponse saveDiagnosisFeedback(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
         BaseResponse baseResponse = new BaseResponse(requestId);
         DiagnosisFeedbackModel model = JSONObject.parseObject(requestBody, DiagnosisFeedbackModel.class);
        
         if (StringUtils.isBlank(model.getUserCode())) {
 			logger.error("UserManagementController saveDiagnosisFeedback  error Exception：UserCode is null." + baseResponse.getRequestId());
 			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "UserCode");
 		}
        if(StringUtils.isBlank(model.getFeedbackContent())){
         	 logger.error("UserManagementController saveDiagnosisFeedback  Exception：fedbackContent is null."+ baseResponse.getRequestId());
              return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "feedbackContent");
          }
        if(null == model.getUserType()){
        	 logger.error("UserManagementController saveDiagnosisFeedback  Exception：userType is null."+ baseResponse.getRequestId());
             return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userType");
         }
         SensitivewordFilter filter = new SensitivewordFilter();
         Set<String> set = filter.getSensitiveWord(model.getFeedbackContent(),1);
        if (set.size()!=0){
            logger.error("UserManagementController saveDiagnosisFeedback  Exception：have sensitive word."+ baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.HAVE_SENSITIVE_WORD.toString(), " sensitiveWord");
         }
         try {
             userService.saveDiagnosisFeedback(model);
             baseResponse.setResult(true);
         } catch (Exception e) {
             logger.error(requestId + "UserManagementController  saveDiagnosisFeedback  error: ", e);
             return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
         }
         return baseResponse;
     }

    /**
     * 获取学校下的subjectCode集合
     * @param requestId
     * @return
     */
    @RequestMapping(value = "/initStudentSubject/{schoolCode}/{gradeCode}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public BaseResponse initStudentSubject(@RequestParam("requestId") String requestId,@PathVariable("schoolCode") String schoolCode
                                                                                    ,@PathVariable("gradeCode") String gradeCode) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        if (StringUtils.isBlank(schoolCode)) {
            logger.error("UserManagementController initStudentSubject Exception：schoolCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolCode");
        }
        if (StringUtils.isBlank(gradeCode)) {
            logger.error("UserManagementController initStudentSubject Exception：gradeCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolCode");
        }
        try {
            List<Integer> subjectCodes = userService.initStudentSubject(schoolCode,gradeCode);
            baseResponse.setResult(subjectCodes);
        } catch (Exception e) {
            logger.error(requestId + "UserManagementController  initStudentSubject  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    
     /********************************抽取到学生端接口-原与教师端共用****************************************/    
    
    /**
     * 查询学校下的所有年级
     *
     * @param requestId
     * @param requestBody
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getGradeList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public BaseResponse getGradeList(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            //组装条件，查询年级
            AuthGroupBean condition = GsonUtils.getGson().fromJson(requestBody, AuthGroupBean.class);
            if (null == condition || null == condition.getGroupId() || condition.getGroupId() <= 0) {
                logger.error("UserManagementController getGradeList groupId is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "groupId");
            }
            AuthGroupBean groupBean = new AuthGroupBean();
            groupBean.setGroupParentId(condition.getGroupId());
            groupBean.setGroupType(ConstantEnum.GROUP_TYPE_GRADE.getType());
            List<AuthGroupBean> groupBeanList = userService.getGroupByParent(groupBean);
            if (CollectionUtils.isEmpty(groupBeanList)) {
                logger.error("UserManagementController getGradeList error,获取数据失败");
                return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
            }
            baseResponse.setResult(groupBeanList);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("UserManagementController.getGradeList error ", ex);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    /**
     * 查询年级下的所有班级
     *
     * @param requestId
     * @param requestBody
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getClassList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public BaseResponse getClassList(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            AuthGroupBean condition = GsonUtils.getGson().fromJson(requestBody, AuthGroupBean.class);
            if (condition == null || condition.getGroupId() == null || condition.getGroupId() <= 0) {
                logger.error("UserManagementController getClassList groupId is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "groupId");
            }
            AuthGroupBean grade = new AuthGroupBean();
            grade.setGroupId(condition.getGroupId());
            List<AuthGroupBean> gradeList = userService.getGroupByCondition(grade);
            if (CollectionUtils.isEmpty(gradeList)) {
                logger.error("UserManagementController getClassList groupId is not exist");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "groupId is not exist");
            }
            AuthGroupBean bean = gradeList.get(0);
            if(null != bean.getGroupIden() && !bean.getGroupIden().equals(0)){
                if(bean.getGroupIden().equals(32) ||  bean.getGroupIden().equals(33)){
                    if (condition == null || condition.getGroupArt() == null ) {
                        logger.error("UserManagementController getClassList groupArt is null or error" + baseResponse.getRequestId());
                        return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "groupArt");
                    }
                }
            }
            AuthGroupBean groupBean = new AuthGroupBean();
            groupBean.setGroupParentId(condition.getGroupId());
            groupBean.setGroupType(ConstantEnum.GROUP_TYPE_CLASS.getType());
            if (condition != null && condition.getGroupArt() != null ) {
                groupBean.setGroupArt(condition.getGroupArt());
            }
            List<AuthGroupBean> groupBeanList = userService.getGroupByParent(groupBean);

            baseResponse.setResult(groupBeanList);
        } catch (Exception ex) {
            logger.error("getGroupByNameAndType.getClassList error ", ex);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    /**
     * 根据名称模糊查询组织信息
     *
     * @param requestId
     * @param requestBody
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getGroupByNameAndType", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public BaseResponse getGroupByNameAndType(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            AuthGroupBean condition = GsonUtils.getGson().fromJson(requestBody, AuthGroupBean.class);
            AuthGroupBean groupBean = new AuthGroupBean();
            if (!ConstantEnum.GROUP_TYPE_SCHOOL.getType().equals(condition.getGroupType())) {
                if (condition == null || condition.getGroupParentId() == null || condition.getGroupParentId() <= 0) {
                    logger.error("UserManagementController getGroupByNameAndType  groupParentId is null or error" + baseResponse.getRequestId());
                    return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "groupParentId");
                }
                groupBean.setGroupParentId(condition.getGroupParentId());
            }

            if (condition == null || StringUtils.isBlank(condition.getGroupName())) {
                logger.error("UserManagementController getGroupByNameAndType groupName is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "groupName");
            }
            if (condition == null || null == condition.getGroupType() || condition.getGroupType() < 0) {
                logger.error("UserManagementController getGroupByNameAndType groupType is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "groupType");
            }

            groupBean.setGroupType(condition.getGroupType());
            groupBean.setGroupName(condition.getGroupName());
            List<AuthGroupBean> groupBeanList = userService.getGroupByParent(groupBean);
            baseResponse.setResult(groupBeanList);
        } catch (Exception ex) {
            logger.error("UserManagementController .getGroupByNameAndType error ", ex);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    /**
     * 修改个人信息
     * @param requestId
     * @param requestBody
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/stuUpdate", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public BaseResponse stuUpdate(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try{
            AuthUserBean condition = GsonUtils.getGson().fromJson(requestBody, AuthUserBean.class);
            if(null == condition || null == condition.getUserId() || condition.getUserId() <= 0 ){
                logger.error("UserManagementController stuUpdate userId is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString()," userId is null");
            }
            AuthUserBean userBean = new AuthUserBean();
            userBean.setUserId(condition.getUserId());
            List<AuthUserBean> userBeanList = userService.getUserBean(userBean);
            if(CollectionUtils.isEmpty(userBeanList)){
                logger.error("UserManagementController stuUpdate user is not exist," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.RESOURCE_NOTFOUND.toString()," user is not exist");
            }
            boolean bool = userService.updateStuInfo(condition);
            baseResponse.setResult(bool);
        }catch (Exception ex){
            logger.error("UserManagementController  stuUpdate is error ",ex);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    //异步去执行发送短信
    private void sendToSms(UserRequestModel model, EventSourceEnum editPhoneSms) {
        userService.sendToSms(model,editPhoneSms);
    }



    /**
     * 学生端 个人中心，更新学生组织
     * @param requestId
     * @param requestBody
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateStudentGroupInfo", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public BaseResponse updateStudentGroupInfo(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        logger.info("json: " + GsonUtils.getGson().toJson(requestBody));
        try{
            AuthUserBean condition = GsonUtils.getGson().fromJson(requestBody,AuthUserBean.class);
            if(null == condition || null == condition.getUserId() || condition.getUserId() <= 0){
                logger.error("userId is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"userId is null");
            }
            if (null == condition || null == condition.getUserType() || condition.getUserType() <= 0) {
                logger.error("userType is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userType is null");
            }
            if (null == condition || null == condition.getSchoolId() || condition.getSchoolId() <= 0) {
                logger.error("schoolId is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolId is null");
            }
            if (null == condition || null == condition.getGradeId() || condition.getGradeId() <= 0) {
                logger.error("gradeId is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeId is null");
            }
            if (null == condition || null == condition.getClassId() || condition.getClassId() <= 0) {
                logger.error("classId is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "classId is null");
            }
            if(condition.getGradeIden().equals(32) || condition.getGradeIden().equals(33)){
                if(condition.getArtType() == null){
                    logger.error("artType is null or error" + baseResponse.getRequestId());
                    return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "artType is null");
                }
            }else {
                condition.setArtType(2);//非高二 高三生 类型定义为无
            }
            boolean bool = authUserService.reBindStudentGroup(condition) > 0;
            baseResponse.setResult(bool);
        }catch (Exception ex){
            logger.error("UserManagementController.updateStudentGroupInfo is error ",ex);
            return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 后端管理，删除学生组织
     * @param requestId
     * @param requestBody
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteStudentGroupInfo", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public BaseResponse deleteStudentGroupInfo(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        logger.info("json: " + GsonUtils.getGson().toJson(requestBody));
        try{
            AuthUserBean condition = GsonUtils.getGson().fromJson(requestBody,AuthUserBean.class);
            if(null == condition || null == condition.getUserId() || condition.getUserId() <= 0){
                logger.error("userId is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"userId is null");
            }
            if (null == condition || null == condition.getUserType() || condition.getUserType() <= 0) {
                logger.error("userType is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userType is null");
            }
            boolean bool = authUserService.delStudentGroup(condition) > 0;
            baseResponse.setResult(bool);
        }catch (Exception ex){
            logger.error("UserManagementController.deleteStudentGroupInfo is error ",ex);
            return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 修改个人信息
     * @param requestId
     * @param requestBody
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getStudentInfo", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public BaseResponse getStudentInfo(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try{
            AuthUserBean condition = GsonUtils.getGson().fromJson(requestBody, AuthUserBean.class);
            if(null == condition || null == condition.getUserId() || condition.getUserId() <= 0 ){
                logger.error("UserManagementController getStudentInfo userId is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString()," userId is null");
            }
            if(null == condition || null == condition.getUserType() || condition.getUserType() <= 0 ){
                logger.error("UserManagementController getStudentInfo userType is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString()," userType is null");
            }
            baseResponse.setResult(userService.getStudentInfo(condition));
        }catch (Exception ex){
            logger.error("UserManagementController  getStudentInfo is error ",ex);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
}
