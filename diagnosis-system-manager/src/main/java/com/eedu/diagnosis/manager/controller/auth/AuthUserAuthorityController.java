package com.eedu.diagnosis.manager.controller.auth;

import com.eedu.auth.beans.AuthUserAuthorityManagerBean;
import com.eedu.auth.beans.enums.ConstantEnum;
import com.eedu.auth.service.AuthUserAuthorityManagerService;
import com.eedu.diagnosis.common.service.redisService.RedisClientTemplate;
import com.eedu.diagnosis.common.utils.GsonUtils;
import com.eedu.diagnosis.common.utils.UIDGenerator;
import com.eedu.diagnosis.manager.responsecode.BaseResponse;
import com.eedu.diagnosis.manager.responsecode.ResponseCode;
import com.eedu.diagnosis.manager.service.AuthAuthorizeService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * @author  zz
 * 局长和教研员
 */
@RestController
@RequestMapping("/authUserAuthority")
@Slf4j
public class AuthUserAuthorityController {


	private final Logger logger = LoggerFactory.getLogger(AuthUserAuthorityController.class);

	private static final int EXPIRE_DURATION_WEEK = 60 * 60 * 24 * 7;//单位 秒
	private static final int EXPIRE_DURATION_TWO_HOUR = 60 * 60 * 2;//单位 秒
	@Autowired
	private AuthAuthorizeService authorizeService;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	@Autowired
	private AuthUserAuthorityManagerService authUserAuthorityManagerService;
	/**
	 * 新增用户 (局长教研员)
	 */
	@ResponseBody
	@RequestMapping(value = "/save",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	public BaseResponse addAuthUserAuthorityManager(@RequestParam("requestId") String requestId, @RequestBody String requestBody){
		BaseResponse baseResponse = new BaseResponse(requestId);
		try{
            AuthUserAuthorityManagerBean condition = GsonUtils.getGson().fromJson(requestBody,AuthUserAuthorityManagerBean.class);
			if(null == condition ){
				logger.error(" addAuthUserAuthorityManager  usermanager is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"usermanager is null");
			}
			if(null == condition.getUserName() || "".equals(condition.getUserName())){
				logger.error("addAuthUserAuthorityManager userName is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"userName is null");
			}
			if(null == condition.getUserAccount() || "".equals(condition.getUserAccount())){
				logger.error("addAuthUserAuthorityManager userAccount is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"userAccount is null");
			}
			if(null == condition.getUserPassword() || "".equals(condition.getUserPassword())){
				logger.error("addAuthUserAuthorityManager userPassword is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"userPassword is null");
			}
			if(null == condition.getUserType() || "".equals(condition.getUserType())){
				logger.error("addAuthUserAuthorityManager userType is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"userType is null");
			}
			AuthUserAuthorityManagerBean userManagerBean = new AuthUserAuthorityManagerBean();
			userManagerBean.setUserAccount(condition.getUserAccount());
			List<AuthUserAuthorityManagerBean>  userlist = authorizeService.getAuthUserAuthorityManagerList(userManagerBean);
			if(null != userlist && userlist.size() > 0){
				logger.error("addAuthUserAuthorityManager userAccount is having" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_DUPLICATE.toString(),".userAccount is having");
			}
			boolean result = authorizeService.addAuthUserAuthorityManager(condition);
			if(result)
				baseResponse.setResponse(baseResponse, ResponseCode.SUCCESS.toString(),"add AuthUserAuthorityManager success");
			else
				baseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString(),"add AuthUserAuthorityManager fail");
		}catch(Exception e){
			logger.error("AuthAuthorizeController.addUserManager",e);
			return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
		}
		return baseResponse;
	}
    /**
	 * 用户 列表(局长教研员)
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public BaseResponse getAuthUserAuthorityManagerList(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try{
			AuthUserAuthorityManagerBean condition = GsonUtils.getGson().fromJson(requestBody,AuthUserAuthorityManagerBean.class);
            PageInfo<AuthUserAuthorityManagerBean> pageInfo = authorizeService.getAuthUserAuthorityManagerPage(condition);
			Map<String,Object>  map = new HashMap();
			map.put("total",pageInfo.getTotal());
			map.put("row",pageInfo.getList());
            baseResponse.setResult(map);
        }catch (Exception ex){
			logger.error("getAuthUserAuthorityManager List  is error ",ex);
            return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

	/**
	 * 用户登录(局长教研员)
	 */
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public BaseResponse authUserAuthorityManagerLogin(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
		BaseResponse baseResponse = new BaseResponse(requestId);
		try {
			AuthUserAuthorityManagerBean userBean = GsonUtils.getGson().fromJson(requestBody, AuthUserAuthorityManagerBean.class);
			if (com.alibaba.dubbo.common.utils.StringUtils.isBlank(userBean.getUserAccount()) && com.alibaba.dubbo.common.utils.StringUtils.isBlank(userBean.getUserPhone())) {
				logger.error("userAuthorityManagerLogin userAccount or userPhone error , userAccount or userPhone is null " + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userAccount or userPhone");
			}
			if (com.alibaba.dubbo.common.utils.StringUtils.isBlank(userBean.getUserPassword())) {
				logger.error("userAuthorityManagerLogin userPassword error , userPassword is null " + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userPassword");
			}
			AuthUserAuthorityManagerBean userManagerBeanCondition = new AuthUserAuthorityManagerBean();
			userManagerBeanCondition.setUserAccount(userBean.getUserAccount());
			userManagerBeanCondition.setUserPhone(userBean.getUserPhone());
			userManagerBeanCondition.setUserPassword(userBean.getUserPassword());
			List<AuthUserAuthorityManagerBean> bean = authorizeService.getUserByAccountAndPwd(userManagerBeanCondition);
			Map<String,Object>  map = new HashMap();
			if (null == bean ||  bean.size() == 0) {
				logger.error("userAuthorityManagerLogin user  login error , userAccount or userPassword is error " + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.LOGIN_FAILD.toString(), "userAccount or userPassword");
			} else {
				AuthUserAuthorityManagerBean userManagerBean = bean.get(0);
				if (null != userManagerBean.getUserType()) {
					if (userManagerBean.getUserType() == ConstantEnum.USER_TYPE_DIRECTOR.getType() || userManagerBean.getUserType() == ConstantEnum.USER_TYPE_TEACHING_STAFF.getType()) {
						if ("1".equals(userManagerBean.getStatus())) {
							logger.error(" userAuthorityManagerLogin userStatus is Disabled" + baseResponse.getRequestId());
							return BaseResponse.setResponse(baseResponse, ResponseCode.FORBIDDEN_NOPERMISSION.toString(), "userStatus is Disabled");
						}
						String token = UIDGenerator.getUUIDCode32();
						String logintype = "";
						if ("Android".equals(userBean.getEquipmentType()) || "Ios".equals(userBean.getEquipmentType())) {
							logintype = "Phone";
						} else {
							logintype = userBean.getEquipmentType();
						}
						if ("Wechat".equals(logintype)) {
							redisClientTemplate.set("authority"+userManagerBean.getUserId() + "Wechat", token);
							redisClientTemplate.expire("authority"+userManagerBean.getUserId() + "Wechat", EXPIRE_DURATION_TWO_HOUR);
						} else if ("Web".equals(logintype)) {
							redisClientTemplate.set("authority"+userManagerBean.getUserId() + "Web", token);
							redisClientTemplate.expire("authority"+userManagerBean.getUserId() + "Web", EXPIRE_DURATION_WEEK);
						} else if ("Phone".equals(logintype)) {
							redisClientTemplate.set("authority"+userManagerBean.getUserId() + "Phone", token);
							redisClientTemplate.expire("authority"+userManagerBean.getUserId() + "Phone", EXPIRE_DURATION_WEEK);
						} else {
							logger.error("userAuthorityManagerLogin user login error ,  equipmentType is Invalid " + baseResponse.getRequestId());
							return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), " equipmentType is Invalid ");
						}
						map.put("model",userManagerBean);
						map.put("token",token);
						baseResponse.setResult(map);
					}
				 }
			  }
			} catch (Exception ex) {
				logger.error("userAuthorityManagerLogin.user login error", ex);
				return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
			}
		return baseResponse;
	}

	@ResponseBody
	@RequestMapping(value = "/editPwd",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	public BaseResponse editPwd(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
		BaseResponse baseResponse = new BaseResponse(requestId);
		try {
			AuthUserAuthorityManagerBean userBean = GsonUtils.getGson().fromJson(requestBody, AuthUserAuthorityManagerBean.class);
			if (null == userBean.getUserId() ) {
				logger.error("editPwd    error , userId  is null " + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userId");
			}
			if (StringUtils.isBlank(userBean.getUserPassword())) {
				logger.error("editPwd    error , pwd  is null " + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "pwd");
			}
			AuthUserAuthorityManagerBean authUserAuthorityManagerBean =new AuthUserAuthorityManagerBean();
			authUserAuthorityManagerBean.setUserId(userBean.getUserId());
			authUserAuthorityManagerBean.setUserPassword(userBean.getUserPassword());
			int i = authUserAuthorityManagerService.updateUserAuthorityManager(authUserAuthorityManagerBean);
			baseResponse.setResult(i>0);
			return  baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
		}
	}
}
