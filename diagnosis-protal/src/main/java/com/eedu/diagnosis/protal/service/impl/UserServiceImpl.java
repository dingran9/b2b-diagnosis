package com.eedu.diagnosis.protal.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eedu.auth.beans.AuthGroupBean;
import com.eedu.auth.beans.AuthSubjectBean;
import com.eedu.auth.beans.AuthUserBean;
import com.eedu.auth.beans.AuthUserWeChatBindBean;
import com.eedu.auth.service.AuthGroupService;
import com.eedu.auth.service.AuthUserService;
import com.eedu.auth.service.AuthUserWeChatBindService;
import com.eedu.diagnosis.common.enumration.EventSourceEnum;
import com.eedu.diagnosis.common.service.redisService.RedisClientTemplate;
import com.eedu.diagnosis.common.utils.AccountValidatorUtil;
import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.common.utils.PageHelperUtil;
import com.eedu.diagnosis.eventListen.EventSourceHandler;
import com.eedu.diagnosis.eventListen.event.DiagnosisFeedbackEvent;
import com.eedu.diagnosis.eventListen.event.SendSmsEvent;
import com.eedu.diagnosis.eventListen.event.UpdateUserEvent;
import com.eedu.diagnosis.inclass.test.api.dto.DiagnosisStudentAnswerMachineDto;
import com.eedu.diagnosis.paper.api.dto.DiagnosisFavoriteDto;
import com.eedu.diagnosis.paper.api.openService.BasePaperOpenService;
import com.eedu.diagnosis.paper.api.openService.ResourceOpenService;
import com.eedu.diagnosis.protal.client.UserClient;
import com.eedu.diagnosis.protal.model.classTest.DiagnosisStudentAnswerMachineModel;
import com.eedu.diagnosis.protal.model.request.DiagnosisFavoriteModele;
import com.eedu.diagnosis.protal.model.request.DiagnosisFeedbackModel;
import com.eedu.diagnosis.protal.model.response.AuthUserVo;
import com.eedu.diagnosis.protal.model.response.DiagnosisFavoriteVo;
import com.eedu.diagnosis.protal.model.response.question.QuestionModel;
import com.eedu.diagnosis.protal.model.response.question.QuestionModels;
import com.eedu.diagnosis.protal.model.user.*;
import com.eedu.diagnosis.protal.responsecode.BaseResponse;
import com.eedu.diagnosis.protal.responsecode.ResponseCode;
import com.eedu.diagnosis.protal.service.StudentClassTestService;
import com.eedu.diagnosis.protal.service.UserService;
import com.eeduspace.uuims.api.OauthClient;
import com.eeduspace.uuims.api.enumeration.SourceEnum;
import com.eeduspace.uuims.api.request.login.LoginRequest;
import com.eeduspace.uuims.api.request.user.ActivationUserRequest;
import com.eeduspace.uuims.api.response.login.LoginResponse;
import com.eeduspace.uuims.api.response.user.ActivationUserResponse;
import com.eeduspace.uuims.comm.util.base.json.GsonUtil;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: zz
 * Description:  用户操作管理的实现类
 */
@Service
public class UserServiceImpl  implements  UserService,ApplicationContextAware{

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private Gson  gson = new  Gson();
    @Autowired
    private StudentClassTestService classTestService;

    @Autowired
    private SMSUtil smsUtil ;
    @Value("${diagnosis.protal.uuims.server.url}")
    private String serverUrl;
    @Value("${diagnosis.protal.accessKey}")
    private String accessKey;
    @Value("${diagnosis.protal.secretKey}")
    private String secretKey;
    @Value("${diagnosis.protal.productType}")
    private String productType;
    @Inject
    private UserClient userClient;
    @Autowired
    private RedisClientTemplate redisClientTemplate;
    @Autowired
    private AuthUserService authUserService;
    @Autowired
	private BasePaperOpenService basePaperOpenServiceImpl;
    @Autowired
    private AuthGroupService authGroupService;
    @Autowired
    private AuthUserWeChatBindService authUserWeChatBindService ;
    @Autowired
    private ResourceOpenService resourceOpenServiceImpl;

	private ApplicationContext applicationContext;

/**     注册
 *           
 *  uuims 存在 keepMark不存在 -同步添加、存在 -同步修改
 *  uuims 不存在 keepMark不存在 -同步添加、存在 -同步修改
 * @throws Exception 
 */
    @Override
    public AuthUserBean register(UserRequestModel userModel) throws Exception {
    	AuthUserBean findModel=null;
    	//校验统一用户管理中手机号 的用户是否存在
    	BaseResponse validateByMobile = userClient.validateByMobile(userModel);
    	if("Success".equals(validateByMobile.getCode())){//uuims 查询
            UserInfoResponse fromObjectJson = GsonUtil.fromObjectJson(gson.toJson(validateByMobile), "result", "userModel", UserInfoResponse.class);
    		if (fromObjectJson==null) { //uuims  不存在 ，走注册
    			AuthUserBean t = new AuthUserBean();
	            t.setUserPhone(userModel.getPhone());
	            List<AuthUserBean> userByCondition = authUserService.getUserByCondition(t);	              
				BaseResponse userRegister = userClient.userRegister(userModel);//uuims 成功注册
    			if("Success".equals(userRegister.getCode())){
    				UserInfoResponse infoResponse = GsonUtil.fromObjectJson(gson.toJson(userRegister), "result", "userModel", UserInfoResponse.class);
                    if (infoResponse!=null) {
	         			 if (userByCondition==null || userByCondition.size() ==0) { //门户  不存在
	         				AuthUserBean authUserBean= new AuthUserBean();
		                    findModel  = getModelToAuthStudent(authUserBean,infoResponse);		                     	
	                     	int l = authUserService.addUserInfo(findModel);
	          		        return l !=0 ? findModel : null;
	      				 }
	         			  //门户 存在
	                     findModel  = getModelToAuthStudent(userByCondition.get(0),infoResponse);
	         			 int flag = authUserService.updateUserInfo(findModel);
						 return flag != 0 ? findModel : null;
        			 }
    			}
			}
     }
		return null;
}  
  
    /**     登录
     * @throws Exception 
     *           
     */
    @Override
    public LoginResponse login(UserRequestModel userRequestModel, boolean isScan, BaseResponse baseResponse) throws Exception {
        TokenModel tokenModel=null;

        OauthClient oauthClient = new OauthClient(serverUrl);
        LoginRequest loginRequest = new LoginRequest();
        if(!StringUtils.isEmpty(userRequestModel.getPhone()) && AccountValidatorUtil.isMobile(userRequestModel.getPhone())){
            loginRequest.setPhone(userRequestModel.getPhone());
        }else{
            loginRequest.setName(userRequestModel.getPhone());
        }
        loginRequest.setPassword(userRequestModel.getPassword());
        loginRequest.setProductType(productType);
        if(StringUtils.isEmpty(userRequestModel.getEquipmentType())){
            loginRequest.setEquipmentType(SourceEnum.EquipmentType.Web);
        }else if("Android".equals(userRequestModel.getEquipmentType())){
            loginRequest.setEquipmentType(SourceEnum.EquipmentType.Android);
        }else if("Ios".equals(userRequestModel.getEquipmentType())){
            loginRequest.setEquipmentType(SourceEnum.EquipmentType.Ios);
        }else {
            loginRequest.setEquipmentType(SourceEnum.EquipmentType.Web);
        }
        LoginResponse execute = oauthClient.execute(loginRequest);
        logger.debug("uuims  login  response-------="+gson.toJson(execute));
        if (execute==null) {
            logger.error("login Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_NOTFOUND.toString()+".userLoginResponse");
            return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "userLoginResponse");
        }

        if(("200".equals(execute.getHttpCode()))){
            LoginResponse loginResponse = JSONObject.parseObject(JSONObject.toJSONString(execute.getResult()),LoginResponse.class);
            AuthUserBean findModel ;
            Integer userCode = null;
            AuthUserBean t = new AuthUserBean();
            t.setUserOpenId(loginResponse.getUserModel().getOpenId());
             List<AuthUserBean> userByCondition = authUserService.getUserByCondition(t);
             AuthUserBean authUserBean =null;
            if(userByCondition ==null || userByCondition.size() == 0){
               //如果本地不存在用户，去UUIMS激活用户
            	findModel = new AuthUserBean();
                ActivationUserRequest activationUserRequest = new ActivationUserRequest();
                if(!StringUtils.isEmpty(userRequestModel.getPhone()) && AccountValidatorUtil.isMobile(userRequestModel.getPhone())){
                    activationUserRequest.setPhone(userRequestModel.getPhone());
                }else{
                    activationUserRequest.setName(userRequestModel.getPhone());
                }
                activationUserRequest.setPassword(userRequestModel.getPassword());
                OauthClient oauthClient1 = new OauthClient(serverUrl,accessKey,secretKey);
                ActivationUserResponse execute1 = oauthClient1.execute(activationUserRequest);
                UserLoginResponse userActivationResponse=GsonUtil.fromObjectJson(gson.toJson(execute1), "result", "userModel", UserLoginResponse.class);
                //当uuims登录成功，但是本地不存在，同步数据
                if (userActivationResponse!=null) {
                	findModel.setUserAccesskey(userActivationResponse.getAccessKey());
                    findModel.setUserSecretkey(userActivationResponse.getSecretKey());
                    findModel.setUserOpenId(userActivationResponse.getOpenId());
                    findModel.setUserPhone(userActivationResponse.getPhone());
                    findModel.setUserAccount(userActivationResponse.getName());
                    authUserService.addUserInfo(findModel);
                    authUserBean = authUserService.getUserByCondition(findModel).get(0);
                    userCode = authUserBean.getUserId();
				}
            }else {
                userCode=userByCondition.get(0).getUserId();
                authUserBean = getStudentInfo(userByCondition.get(0));
            }
            if(authUserBean != null){

            }
            authUserBean.setUserLoginDate(new Date());
//            authUserService.updateUserInfo(authUserBean);
            saveStudentLoginTime(authUserBean);
            if ("Wechat".equals(userRequestModel.getEquipmentType())) {
            	AuthUserWeChatBindBean userWeChatBindBean = new AuthUserWeChatBindBean();
    			userWeChatBindBean.setOpenId(userRequestModel.getOpenId());
            	AuthUserWeChatBindBean userWeChatBind = authUserWeChatBindService.getUserWeChatBind(userWeChatBindBean);
				if (null == userWeChatBind) {
					  logger.error("login WeChat Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_NOTFOUND.toString()+".Wechat login  userWeChatBind");
		              return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "Wechat login  userWeChatBind");
				}  
				userWeChatBind.setUserId(authUserBean.getUserId());
				userWeChatBind.setType(authUserBean.getUserType());
				userWeChatBind.setUpdateDate(new Date());
				authUserWeChatBindService.updateUserWeChatBind(userWeChatBind);
			}
            if(userRequestModel.getEquipmentType().equals("Android") || userRequestModel.getEquipmentType().equals("Ios")){
                userRequestModel.setEquipmentType("Phone");
            }
            tokenModel=new TokenModel();
            tokenModel.setCode(userCode==null? "0" : String.valueOf(userCode));
            tokenModel.setToken(loginResponse.getUserModel().getToken());
            tokenModel.setRefreshToken(loginResponse.getUserModel().getRefreshToken());
            tokenModel.setExpires(loginResponse.getUserModel().getExpires());
            tokenModel.setOpenId(loginResponse.getUserModel().getOpenId());
            AuthUserVo authUserVo = new AuthUserVo();
            BeanUtils.copyProperties(authUserBean,authUserVo);
            /**查询教师下学生是否绑定答题器**/
            if (authUserVo.getUserId()!=null){
                DiagnosisStudentAnswerMachineModel model = new DiagnosisStudentAnswerMachineModel();
                model.setStudentCode(String.valueOf(authUserVo.getUserId()));
                List<DiagnosisStudentAnswerMachineDto> list = classTestService.studentList(model);
                if (!CollectionUtils.isEmpty(list)){
                    authUserVo.setIsBind(1);
                    authUserVo.setMachineCode(list.get(0).getMachineCode());
                }else {
                    authUserVo.setIsBind(0);
                }
            }
            tokenModel.setAuthUserVo(authUserVo);
//            redisClientTemplate.setex(userLoginResponse.getToken(), Integer.parseInt(userLoginResponse.getExpires()), gson.toJson(tokenModel));
//            logger.debug(" login  redis设置到的token----key-token-------------------："+redisClientTemplate.get(userLoginResponse.getToken()));
            if(userRequestModel.getEquipmentType().equals("Phone")){
                redisClientTemplate.setex(loginResponse.getUserModel().getOpenId() + "Phone", Integer.parseInt(loginResponse.getUserModel().getExpires()), loginResponse.getUserModel().getToken());
            }else if (userRequestModel.getEquipmentType().equals("Wechat")) {
                redisClientTemplate.setex(loginResponse.getUserModel().getOpenId() + "Wechat", Integer.parseInt(loginResponse.getUserModel().getExpires()), loginResponse.getUserModel().getToken());
            }else{
                redisClientTemplate.setex(loginResponse.getUserModel().getOpenId() + "Web", Integer.parseInt(loginResponse.getUserModel().getExpires()), loginResponse.getUserModel().getToken());
            }
            BeanUtils.copyProperties(execute,loginResponse);
            loginResponse.setResult(tokenModel);
            return loginResponse;
        }
        return execute;
    }
    /**
     * 校验原密码
     */
	@Override
	public BaseResponse chackOldPwd(UserRequestModel userRequestModel, BaseResponse baseResponse)throws Exception {      
         AuthUserBean userById = authUserService.getUserById(Integer.parseInt(userRequestModel.getUserCode()));
	     if (userById==null || userById.getUserId() == null) {
             logger.error("login Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_NOTFOUND.toString()+".AuthUserBean");
             return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "AuthUserBean");
	     }
	     userRequestModel.setPhone(userById.getUserPhone());
		 BaseResponse loginResponse=userClient.userActivation(userRequestModel);
		 return loginResponse;
	}
    
    /**
     *  刷新token
     */
    @Override
    public BaseResponse refreshToken(UserRequestModel userRequestModel) throws IOException {
        BaseResponse clientBaseResponse =  userClient.refreshToken(userRequestModel);
        if("Success".equals(clientBaseResponse.getCode())){
            UserLoginResponse userLoginResponse=GsonUtil.fromObjectJson(gson.toJson(clientBaseResponse), "result", "tokenModel", UserLoginResponse.class);
            UserModel  userModel=new UserModel();
            userModel.setUserCode(userRequestModel.getUserCode());
            userModel.setToken(userLoginResponse.getToken());
            userModel.setRefreshToken(userLoginResponse.getRefreshToken());
            userModel.setExpires(userLoginResponse.getExpires());

            redisClientTemplate.setex(userLoginResponse.getToken(), Integer.parseInt(userLoginResponse.getExpires()), gson.toJson(userModel));
            redisClientTemplate.setex("diagnosis_protal_"+userRequestModel.getUserCode(), Integer.parseInt(userLoginResponse.getExpires()), userLoginResponse.getToken());
        }
        return clientBaseResponse;
    }
    /**
     * 注册的实体转换
     *  
     */
  		public  AuthUserBean getModelToAuthStudent(AuthUserBean model,UserInfoResponse findModel) {
  			model.setUserOpenId(findModel.getOpenId());
  			model.setUserPhone(findModel.getPhone());
  			model.setUserAccount(findModel.getPhone());
  			model.setUserAccesskey(findModel.getAccessKey());
  			model.setUserSecretkey(findModel.getSecretKey());
  			return model;
  		}
	 /**
     *收藏
     *  
     */
		@Override
		public String saveOrUpdateDiagnosisFavoriteService(DiagnosisFavoriteModele model)throws Exception {
			DiagnosisFavoriteDto dfDto = new DiagnosisFavoriteDto();
			BeanUtils.copyProperties(model,dfDto);
			String responseJson = basePaperOpenServiceImpl.saveOrUpdateDiagnosisFavoriteService(dfDto);
			return responseJson;
		}

	@Override
	public PageInfo<DiagnosisFavoriteVo> getDiagnosisFavoritePaper(DiagnosisFavoriteModele model)throws Exception {
        String ids = "";
        DiagnosisFavoriteDto dfDto = new DiagnosisFavoriteDto();
		BeanUtils.copyProperties(model,dfDto);
		Order order = new Order("create_time", Order.Direction.desc);
	    PageInfo<DiagnosisFavoriteDto> pageInfo = basePaperOpenServiceImpl.getDiagnosisFavoritePaper(dfDto, model.getPageNum(), model.getPageSize(), order);
        PageInfo<DiagnosisFavoriteVo> result = PageHelperUtil.pageInfoConverter(pageInfo, DiagnosisFavoriteVo.class);
        List<DiagnosisFavoriteVo> dpList = result.getList();
        if (!CollectionUtils.isEmpty(dpList)){
            for (DiagnosisFavoriteVo df : dpList){
                if (null != df.getArticleCode() && !df.getArticleCode().equals("")){
                    ids +=  ","+df.getArticleCode();
                }
            }
        }
        if(!ids.equals("") && ids.length()!=0 && !ids.equals(",") ){
            ids = ids.substring(1);
            String  jsonString =  resourceOpenServiceImpl.getArticleStem(ids,dpList.get(0).getSubjectCode());
            QuestionModels questionListModels =  JSONArray.parseObject(jsonString, QuestionModels.class);
            List<QuestionModel> allQuestions = questionListModels.getAllQuestions();
            if (!CollectionUtils.isEmpty(allQuestions)) {
                for (QuestionModel qm : allQuestions) {
                    for (DiagnosisFavoriteVo dv : dpList) {
                        if (qm.getId().equals(dv.getArticleCode())) {
                            dv.setArticleContent(qm.getStem());
                        }
                    }
                }
            }
        }
        return result;
	}

	@Override
	public String sendSms(String phone, String smsType)throws Exception  {
		String code = basePaperOpenServiceImpl.sendSms( phone,  smsType);
        logger.debug("sendSms  code："+code);
		return code;
	}

	@Override
	public BaseResponse editPhone(UserRequestModel userRequestModel,BaseResponse baseResponse) throws Exception{
//         //验证手机号唯一 去UUIMS 验证 当该手机号未被使用时则返回200
//         BaseResponse clientBaseResponse = userClient.validateByMobile(userRequestModel);
//         if (clientBaseResponse == null || !"Success".equals(clientBaseResponse.getCode())) {
//             logger.error("edit phone Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_DUPLICATE.toString()+".phone");
//             return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_DUPLICATE.toString(), "phone");
//         }
         AuthUserBean t = new AuthUserBean();
         t.setUserId(Integer.parseInt(userRequestModel.getUserCode()));
         AuthUserBean findModel = authUserService.getUserByCondition(t).get(0);  
 	    if (findModel==null || findModel.getUserId() == null) {
 	    	 logger.error("edit phone Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_NOTFOUND.toString()+".AuthUserBean");
              return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "AuthUserBean");
 		}
        userRequestModel.setOpenId(findModel.getUserOpenId());
 	    userRequestModel.setUserAccessKey(findModel.getUserAccesskey());
 	    userRequestModel.setUserSecretKey(findModel.getUserSecretkey());
 	    userRequestModel.setToken(userRequestModel.getToken());
 	    userRequestModel.setPhone(userRequestModel.getPhone());
        BaseResponse updateUserInfo = userClient.updateUserInfo(userRequestModel);
         if (updateUserInfo != null && "Success".equals(updateUserInfo.getCode())) {
             findModel.setUserAccount(userRequestModel.getPhone());
             findModel.setUserPhone(userRequestModel.getPhone());
             updateUser(findModel,EventSourceEnum.EDIT_PHONE);
         }
        return updateUserInfo;
	}

	@Override
	public void saveDiagnosisFeedback(DiagnosisFeedbackModel model) throws Exception{
        DiagnosisFeedbackEvent event = new DiagnosisFeedbackEvent(new EventSourceHandler(JSONObject.toJSONString(model),EventSourceEnum.SAVE_DIAGNOSIS_FEEDBACK));
        applicationContext.publishEvent(event);

	}

    @Override
    public List<Integer> initStudentSubject(String schoolCode, String gradeCode) {
        List<Integer> subjectCodes = new ArrayList<>();
        AuthGroupBean subjectBySchoolGrade = authGroupService.getSubjectBySchoolGrade(Integer.parseInt(schoolCode), Integer.parseInt(gradeCode));
        String groupMaterial = subjectBySchoolGrade.getGroupMaterial();
        List<AuthSubjectBean> subjectBeans = JSONArray.parseArray(groupMaterial, AuthSubjectBean.class);
        if(null != subjectBeans && !subjectBeans.isEmpty()){
            for (AuthSubjectBean bean:subjectBeans) {
                subjectCodes.add(bean.getSubjectIden());
            }
        }
        return subjectCodes;
    }

    /**
     * 阳光金点登录
     * 使用UUIMS特殊提供根据手机号注册后免登陆功能
     * @param userRequestModel 请求实体
     * @param baseResponse 返回实体
     * @return
     * @throws Exception
     * @author dingran
     */
    @Override
    public BaseResponse ygjdLogin(UserRequestModel userRequestModel, BaseResponse baseResponse) throws Exception {
        BaseResponse uuimsResponse=userClient.freeLoginByPhone(userRequestModel);
        logger.debug("uuims  searchByPhone  response------->"+gson.toJson(uuimsResponse));
        if(!"200".equals(uuimsResponse.getHttpCode())){
            return uuimsResponse;
        }
        UserLoginResponse userModelResponse= GsonUtil.fromObjectJson(gson.toJson(uuimsResponse), "result", "userModel", UserLoginResponse.class);
        if (userModelResponse==null) {
            logger.error("login Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_NOTFOUND.toString()+".userLoginResponse");
            return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "userLoginResponse");
        }
        //获取用户基本信息
        AuthUserBean authUserBean = new AuthUserBean();
        authUserBean.setUserOpenId(userModelResponse.getOpenId());
        authUserBean = getAuthUserBean(authUserBean,userModelResponse);

        //修改用户登录时间
        authUserBean.setUserLoginDate(new Date());
        authUserService.updateUserInfo(authUserBean);
        //如果是微信端登录 则需要绑定微信公众账号与系统openId的关系
        if ("Wechat".equals(userRequestModel.getEquipmentType()) && userRequestModel.getOpenId()!=null) {
            bindWechatUser(authUserBean.getUserId(),userRequestModel.getOpenId());
        }
      /*  //阳光保险目前无APP端
        if(userRequestModel.getEquipmentType().equals("Android") || userRequestModel.getEquipmentType().equals("Ios")){
            userRequestModel.setEquipmentType("Phone");
        }*/
        //保存token到redis中
        TokenModel tokenModel=setTokenInRedis(authUserBean,userModelResponse, userRequestModel.getEquipmentType());
        baseResponse.setResult(tokenModel);
        return baseResponse;

    }

    /**
     * 获取AuthUserBean 实体，当不存在时，则根据UUIMS返回的userModelResponse 新增数据
     * @param authUserBean 系统用户实体
     * @param userModelResponse UUIMS登录返回信息
     * @return
     * @throws Exception
     * @author dingran
     */
    private  AuthUserBean getAuthUserBean(AuthUserBean authUserBean,UserLoginResponse userModelResponse) throws Exception {
        List<AuthUserBean> userByCondition = authUserService.getUserByCondition(authUserBean);
        if(userByCondition ==null || userByCondition.size() == 0){
            //若不存在 则在测评库里面新增一个
            authUserBean.setUserAccesskey(userModelResponse.getAccessKey());
            authUserBean.setUserSecretkey(userModelResponse.getSecretKey());
            authUserBean.setUserOpenId(userModelResponse.getOpenId());
            authUserBean.setUserPhone(userModelResponse.getPhone());
            authUserBean.setUserAccount(userModelResponse.getPhone());
            authUserService.addUserInfo(authUserBean);
            AuthUserBean  bean = authUserService.getUserByCondition(authUserBean).get(0);
            authUserBean = getStudentInfo(bean);
        }else {
            authUserBean = getStudentInfo(userByCondition.get(0));
        }
        return authUserBean;
    }

    /**
     * 保存token到redis中
     * @param authUserBean 系统用户实体
     * @param userModelResponse UUIMS登录返回信息
     * @param equipmentType 登录设备
     * @return
     * @author dingran
     */
    private TokenModel setTokenInRedis(AuthUserBean authUserBean,UserLoginResponse userModelResponse,String equipmentType){
        TokenModel tokenModel=new TokenModel();
        tokenModel.setCode(authUserBean.getUserId()==null? "0" : String.valueOf(authUserBean.getUserId()));
        tokenModel.setToken(userModelResponse.getToken());
        tokenModel.setRefreshToken(userModelResponse.getRefreshToken());
        tokenModel.setExpires(userModelResponse.getExpires());
        tokenModel.setOpenId(userModelResponse.getOpenId());
        AuthUserVo authUserVo = new AuthUserVo();
        BeanUtils.copyProperties(authUserBean,authUserVo);
        tokenModel.setAuthUserVo(authUserVo);
        redisClientTemplate.setex(userModelResponse.getToken(), Integer.parseInt(userModelResponse.getExpires()), gson.toJson(tokenModel));
        logger.debug(" login  redis设置到的token----key-token-------------------："+redisClientTemplate.get(userModelResponse.getToken()));
        redisClientTemplate.setex(userModelResponse.getOpenId() + equipmentType, Integer.parseInt(userModelResponse.getExpires()), userModelResponse.getToken());
        return tokenModel;
    }

    /**
     * 绑定微信与userId关系
     * @param userId 系统中 数字主键
     * @param wechatOpenId 微信的openITd
     * @throws Exception
     * @author dingran
     */
    private void bindWechatUser(Integer userId, String wechatOpenId) throws Exception {
        AuthUserWeChatBindBean userWeChatBindBean = new AuthUserWeChatBindBean();
        userWeChatBindBean.setOpenId(wechatOpenId);
        AuthUserWeChatBindBean userWeChatBind = authUserWeChatBindService.getUserWeChatBind(userWeChatBindBean);
        userWeChatBindBean.setUserId(userId);
        if (null == userWeChatBind) {
            authUserWeChatBindService.addUserWeChatBind(userWeChatBindBean);
            return;
        }
        authUserWeChatBindService.updateUserWeChatBind(userWeChatBind);
    }
    /**
     * 获取学生个人信息
     *
     * @param bean
     * @return
     */
    @Override
    public AuthUserBean getStudentInfo(AuthUserBean bean) throws Exception{
		AuthUserBean studentInfo = authUserService.getStudentInfo(bean);
		return studentInfo;
	}

    /**
	 * 查找用户
	 *
	 * @param userBean
	 * @return
	 */
	@Override
	public List<AuthUserBean> getUserBean(AuthUserBean userBean)throws Exception {
		return authUserService.getUserByCondition(userBean);
	}
	/**
	 * 修改用户
	 *
	 * @param condition
	 * @return
	 */
	@Override
	public boolean updateStuInfo(AuthUserBean condition)throws Exception {

		return authUserService.updateUserInfo(condition) > 0;
	}
    /**
	 * 根据条件查询子标签
	 * @param groupBean
	 * @return
	 */
	@Override
	public List<AuthGroupBean> getGroupByParent(AuthGroupBean groupBean)throws Exception  {
		return authGroupService.getGroupByCondition(groupBean);
	}
	/**
	 * 根据条件查询组织信息
	 * @param groupBean
	 * @return
	 */
	@Override
	public List<AuthGroupBean> getGroupByCondition(AuthGroupBean groupBean)throws Exception  {
		return authGroupService.getGroupByCondition(groupBean);
	}


	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void sendToSms(UserRequestModel model, EventSourceEnum editPhoneSms) {
		SendSmsEvent event = new SendSmsEvent(new EventSourceHandler(JSONObject.toJSONString(model),editPhoneSms));
		applicationContext.publishEvent(event);
	}

    public void updateUser(AuthUserBean findModel, EventSourceEnum editPhoneSms) {
        UpdateUserEvent event = new UpdateUserEvent(new EventSourceHandler(JSONObject.toJSONString(findModel),editPhoneSms));
        applicationContext.publishEvent(event);
    }

    /**
     * 异步保存学生的登录时间
     * @param bean
     * @throws Exception
     */
	@Override
	public void saveStudentLoginTime(AuthUserBean bean) throws Exception{

        authUserService.saveStudentLoginTime(bean);
    }
}
