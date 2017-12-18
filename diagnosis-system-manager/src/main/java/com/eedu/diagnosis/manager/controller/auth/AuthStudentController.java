package com.eedu.diagnosis.manager.controller.auth;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.eedu.auth.beans.AuthUserBean;
import com.eedu.diagnosis.common.utils.GsonUtils;
import com.eedu.diagnosis.manager.responsecode.BaseResponse;
import com.eedu.diagnosis.manager.responsecode.ResponseCode;
import com.eedu.diagnosis.manager.service.AuthStudentService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/22
 * Time: 10:13
 * Describe:
 */
@RestController
@RequestMapping("/studentManager")
public class AuthStudentController {

	private final Logger logger = LoggerFactory.getLogger(AuthStudentController.class);
	@Autowired
	private AuthStudentService  studentService;



	/**
	 * 学生注册
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/stuRegister", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public BaseResponse stuRegister(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
		BaseResponse baseResponse = new BaseResponse(requestId);
		try{
			AuthUserBean condition = GsonUtils.getGson().fromJson(requestBody, AuthUserBean.class);
			if(null == condition || StringUtils.isBlank(condition.getUserAccount())){
				logger.error("userAccount is null," + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"userAccount is null");
			}
			if(StringUtils.isBlank(condition.getUserPassword())){
				logger.error("userPassword is null," + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"userPassword is null");
			}
			List<AuthUserBean> userBeanList = studentService.getUserBean(condition);
			if(!CollectionUtils.isEmpty(userBeanList)){
				logger.error("userAccount is exist," + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse,ResponseCode.REPEAT_LOGIN.toString(),"userAccount is exist");
			}
			boolean bool = studentService.stuRegister(condition);
			baseResponse.setResult(bool);
		}catch (Exception ex){
			logger.error("AuthStudentController.stuRegister is error ",ex);
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
				logger.error("userId is null," + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString()," userId is null");
			}
//			if(null == condition || null == condition.getArtType() ){
//				logger.error("artType is null," + baseResponse.getRequestId());
//				return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString()," artType is null");
//			}
			AuthUserBean userBean = new AuthUserBean();
			userBean.setUserId(condition.getUserId());
			List<AuthUserBean> userBeanList = studentService.getUserBean(userBean);
			if(CollectionUtils.isEmpty(userBeanList)){
				logger.error("user is not exist," + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse,ResponseCode.RESOURCE_NOTFOUND.toString()," user is not exist");
			}
			boolean bool = studentService.updateStuInfo(condition);
			baseResponse.setResult(bool);
		}catch (Exception ex){
			logger.error("AuthStudentController.stuUpdate is error ",ex);
			return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
		}
		return baseResponse;
	}

	/**
	 * 修改密码
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/stuUpdatePwd", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public BaseResponse stuUpdatePwd(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
		BaseResponse baseResponse = new BaseResponse(requestId);
		try{
			AuthUserBean condition = GsonUtils.getGson().fromJson(requestBody, AuthUserBean.class);
			if(null != condition && !StringUtils.isBlank(condition.getUserAccount()) && !StringUtils.isBlank(condition.getUserPassword())){
				List<AuthUserBean> userBeanList = studentService.getUserBean(condition);
				if(CollectionUtils.isEmpty(userBeanList)){
					logger.error("userAccount is not exist," + baseResponse.getRequestId());
					return BaseResponse.setResponse(baseResponse,ResponseCode.REPEAT_LOGIN.toString(),"userAccount is not exist");
				 }
				AuthUserBean userBean = userBeanList.get(0);
				userBean.setUserPassword(condition.getUserPassword());
				boolean bool = studentService.updateStuInfo(condition);
				baseResponse.setResult(bool);
			}
		}catch (Exception ex){
			logger.error("AuthStudentController.stuRegister is error ",ex);
			return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
		}
		return baseResponse;
	}

	/**
	 * 后台学生管理列表
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/stuManager", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public BaseResponse stuManager(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
		BaseResponse baseResponse = new BaseResponse(requestId);
		try{
			AuthUserBean condition = GsonUtils.getGson().fromJson(requestBody, AuthUserBean.class);
			List<AuthUserBean> userBeanList = studentService.stuManager(condition);
			baseResponse.setResult(userBeanList);
		}catch (Exception ex){
			logger.error("AuthStudentController.stuRegister is error ",ex);
			return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
		}
		return baseResponse;
	}

	/**
	 * 后台学生管理,启用 禁用
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateStuManager", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public BaseResponse updateStuManager(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
		BaseResponse baseResponse = new BaseResponse(requestId);
		try{
			AuthUserBean condition = GsonUtils.getGson().fromJson(requestBody, AuthUserBean.class);
			boolean bool = studentService.updateStuInfo(condition);
			baseResponse.setResult(bool);
		}catch (Exception ex){
			logger.error("AuthStudentController.stuRegister is error ",ex);
			return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
		}
		return baseResponse;
	}

	/**
	 * 学生端 获取用户信息
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
			if(null == condition || (null == condition.getUserId() || condition.getUserId() <= 0) && StringUtils.isBlank(condition.getUserAccount())){
				logger.error("condition is null," + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"condition is null");
			}
			AuthUserBean userBean = studentService.getStudentInfo(condition);
			baseResponse.setResult(userBean);
		}catch (Exception ex){
			logger.error("AuthStudentController.stuRegister is error ",ex);
			return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
		}
		return baseResponse;
	}
	/**
	 * 后台学生列表
	 * @param requestId
	 * @param requestBody
	 */
	@ResponseBody
	@RequestMapping(value = "/getStudentInfoBySchoolAndGrade", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public BaseResponse getStudentInfoBySchoolAndGrade(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
		BaseResponse baseResponse = new BaseResponse(requestId);
		try{
			AuthUserBean condition = GsonUtils.getGson().fromJson(requestBody, AuthUserBean.class);
			PageInfo<AuthUserBean> list = studentService.getStudentInfoBySchoolAndGrade(condition, condition.getPageNum(), condition.getPageSize());
			baseResponse.setResult(list);
		}catch (Exception ex){
			logger.error("AuthStudentController.getStudentInfoBySchoolAndGrade is error ",ex);
			return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
		}
		return baseResponse;
	}

}
