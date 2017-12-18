package com.eedu.diagnosis.manager.controller.auth;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.eedu.auth.beans.*;
import com.eedu.auth.beans.enums.ConstantEnum;
import com.eedu.auth.beans.enums.EnumBean;
import com.eedu.auth.beans.enums.UserConstants;
import com.eedu.auth.service.*;
import com.eedu.diagnosis.common.service.redisService.RedisClientTemplate;
import com.eedu.diagnosis.common.utils.GsonUtils;
import com.eedu.diagnosis.common.utils.UIDGenerator;
import com.eedu.diagnosis.manager.responsecode.BaseResponse;
import com.eedu.diagnosis.manager.responsecode.ResponseCode;
import com.eedu.diagnosis.manager.service.AuthAuthorizeService;
import com.eedu.diagnosis.manager.service.AuthTeacherService;
import com.eedu.diagnosis.paper.api.openService.BasePaperOpenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/21
 * Time: 14:45
 * Describe:
 */
@RestController
@RequestMapping("/login")
public class AuthLoginController {

    private static final int EXPIRE_DURATION_WEEK = 60 * 60 * 24 * 7;//单位 秒
    private static final int EXPIRE_DURATION_TWO_HOUR = 60 * 60 * 2;//单位 秒

    private final Logger logger = LoggerFactory.getLogger(AuthLoginController.class);
    @Autowired
    private RedisClientTemplate redisClientTemplate;
    @Autowired
    private AuthUserService userService;
    @Autowired
    private AuthRoleService roleService;
    @Autowired
    private AuthResourceService resourceService;
    @Autowired
    private AuthUserManagerService userManagerService;
    @Autowired
    private BasePaperOpenService basePaperOpenService;
    @Autowired
    private AuthUserWeChatBindService authUserWeChatBindService;
    @Autowired
    private AuthTeacherService teacherService;
    @Autowired
    private AuthGroupService groupService;
    @Autowired
    private AuthUserGroupBindService authUserGroupBindService;
    @Autowired
    private AuthAuthorizeService authorizeService;
    /**
     * 发送验证码
     *
     * @param requestId
     * @param requestBody
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/sendIdenCode", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public BaseResponse sendIdenCode(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            AuthUserBean condition = GsonUtils.getGson().fromJson(requestBody, AuthUserBean.class);
            if (null == condition || StringUtils.isBlank(condition.getUserPhone())) {
                logger.error("userPhone is null " + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userPhone is null");
            }
            if (null == condition || null == condition.getValidateType() || condition.getValidateType() <= 0) {
                logger.error("validateType is null " + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "validateType is null");
            }
            if (null == condition || null == condition.getUserType() || condition.getUserType() <= 0) {
                logger.error("userType is null " + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userType is null");
            }

            String sendType = null;
            if (ConstantEnum.SEND_TYPE_REGISTER.getType().equals(condition.getValidateType())) {
                if (ConstantEnum.USER_TYPE_TEACHER.getType().equals(condition.getUserType())) {
                    AuthUserManagerBean userManagerBean = new AuthUserManagerBean();
                    userManagerBean.setUserPhone(condition.getUserPhone());
                    userManagerBean.setUserType(condition.getUserType());
                    List<AuthUserManagerBean> userManagerBeanList = userManagerService.getUserManagerList(userManagerBean);
                    if (CollectionUtils.isEmpty(userManagerBeanList)) {
                        logger.error("user.userPhone is not bond " + baseResponse.getRequestId());
                        return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTBOND.toString(), "userPhone is not bond");
                    }
                } else if (ConstantEnum.USER_TYPE_STUDENT.getType().equals(condition.getUserType())) {
                    List<AuthUserBean> userBeanList = userService.getUserByCondition(condition);
                    if (CollectionUtils.isEmpty(userBeanList)) {
                        logger.error("user.userPhone is not bond " + baseResponse.getRequestId());
                        return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTBOND.toString(), "userPhone is not bond");
                    }
                } else if(ConstantEnum.USER_TYPE_SCHOOL_MASTER.getType().equals(condition.getUserType())){
                    AuthUserManagerBean userManagerBean = new AuthUserManagerBean();
                    userManagerBean.setUserPhone(condition.getUserPhone());
                    userManagerBean.setUserType(condition.getUserType());
                    List<AuthUserManagerBean> userManagerBeanList = userManagerService.getUserManagerList(userManagerBean);
                    if (CollectionUtils.isEmpty(userManagerBeanList)) {
                        logger.error("user.userPhone is not bond " + baseResponse.getRequestId());
                        return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTBOND.toString(), "userPhone is not bond");
                    }
                }
                sendType = ConstantEnum.SEND_TYPE_REGISTER.getTypeName();
            } else if (ConstantEnum.SEND_TYPE_BIND_PHONE.getType().equals(condition.getValidateType())) {
                AuthUserBean bean = new AuthUserBean();
                bean.setUserPhone(condition.getUserPhone());
                bean.setUserType(ConstantEnum.USER_TYPE_STUDENT.getType());
                List<AuthUserBean> userBeanList = userService.getUserByCondition(bean);
                if (!CollectionUtils.isEmpty(userBeanList)) {
                    logger.error("user.userPhone is exist " + baseResponse.getRequestId());
                    return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_DUPLICATE.toString(), "userPhone is exist");
                }
                //校长和教师数据在一张表里，校验手机是不区分 用户类型，只要手机号不存在即可，如果数据分开存储，需验证用户类型
                AuthUserManagerBean userManagerBean = new AuthUserManagerBean();
                userManagerBean.setUserPhone(condition.getUserPhone());
//                userManagerBean.setUserType(ConstantEnum.USER_TYPE_TEACHER.getType());
                List<AuthUserManagerBean> userManagerBeanList = userManagerService.getUserManagerList(userManagerBean);
                if (!CollectionUtils.isEmpty(userManagerBeanList)) {
                    logger.error("userManager.userPhone is exist " + baseResponse.getRequestId());
                    return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_DUPLICATE.toString(), "userPhone is exist");
                }
                sendType = ConstantEnum.SEND_TYPE_BIND_PHONE.getTypeName();
            }
            String sendCode = basePaperOpenService.sendSms(condition.getUserPhone(), sendType);
            if (!StringUtils.isBlank(sendCode)) {
                redisClientTemplate.set(UserConstants.SENDIDENCODE_PHONE + condition.getUserPhone(), sendCode);
                //设置过期时间 60秒
                redisClientTemplate.expire(UserConstants.SENDIDENCODE_PHONE + condition.getUserPhone(), 300);
            }
//            baseResponse.setResult(true);
            baseResponse.setResponse(baseResponse, ResponseCode.SUCCESS.toString(), ".send success ");
        } catch (Exception ex) {
            logger.error("AuthLoginController.sendIdenCode is error ", ex);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 验证 验证码
     *
     * @param requestId
     * @param requestBody
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/validateIdenCode", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public BaseResponse validateIdenCode(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            AuthUserBean condition = GsonUtils.getGson().fromJson(requestBody, AuthUserBean.class);
            if (null == condition || StringUtils.isBlank(condition.getUserPhone())) {
                logger.error("userPhone is null " + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userPhone");
            }
            if (StringUtils.isBlank(condition.getValidateCode())) {
                logger.error("validateCode is null " + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "validateCode");
            }
            String validateCode = redisClientTemplate.get(UserConstants.SENDIDENCODE_PHONE + condition.getUserPhone());
            if (condition.getValidateCode().equals(validateCode)) {
                baseResponse.setResult(true);
                redisClientTemplate.del(UserConstants.SENDIDENCODE_PHONE + condition.getUserPhone());
            } else {
                baseResponse.setResult(false);
            }

        } catch (Exception ex) {
            logger.error("AuthLoginController.validateIdenCode is error ", ex);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 用户登录
     *
     * @param requestId
     * @param requestBody
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/userLogin", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public BaseResponse userLogin(@RequestParam("requestId") String requestId, @RequestBody String requestBody,
                                  HttpServletRequest request) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            AuthUserBean userBean = GsonUtils.getGson().fromJson(requestBody, AuthUserBean.class);
            if (StringUtils.isBlank(userBean.getUserAccount()) && StringUtils.isBlank(userBean.getUserPhone())) {
                logger.error("userAccount or userPhone error , userAccount or userPhone is null " + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userAccount or userPhone");
            }
            if (StringUtils.isBlank(userBean.getUserPassword())) {
                logger.error("userPassword error , userPassword is null " + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userPassword");
            }
            if (null == userBean || null == userBean.getUserType() || userBean.getUserType() <= 0) {
                logger.error("userType error  " + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userType");
            }
            if (null == userBean || null == userBean.getEquipmentType() || "".equals(userBean.getEquipmentType())) {
                logger.error("equipmentType error  " + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "equipmentType");
            }
//			userBean.setUserPassword(Digest.md5Digest(userBean.getUserPassword()));
            //根据用户名密码查询用户
//            AuthUserBean userBeanLogin = userService.getUserByAccountAndPwd(userBean);
//            if (null == userBeanLogin || null == userBeanLogin.getUserId() || userBeanLogin.getUserId() <= 0) {
                AuthUserManagerBean userManagerBeans = new AuthUserManagerBean();
                AuthUserManagerBean userManagerBeanCondition = new AuthUserManagerBean();
                userManagerBeanCondition.setUserAccount(userBean.getUserAccount());
                userManagerBeanCondition.setUserPhone(userBean.getUserPhone());
                userManagerBeanCondition.setUserPassword(userBean.getUserPassword());
                AuthUserManagerBean userManagerBean = userManagerService.getUserByAccountAndPwd(userManagerBeanCondition);
                if (null == userManagerBean || null == userManagerBean.getUserId() || userManagerBean.getUserId() <= 0) {
                    logger.error("userLogin error , userAccount or userPassword is error " + baseResponse.getRequestId());
                    return BaseResponse.setResponse(baseResponse, ResponseCode.LOGIN_FAILD.toString(), "userAccount or userPassword");
                } else {
                    //如果是校长登录的话，教师及其他成员的账号是不能登录的
                    if(ConstantEnum.USER_TYPE_SCHOOL_MASTER.getType() == userBean.getUserType()){
                        if(ConstantEnum.USER_TYPE_SCHOOL_MASTER.getType() != userManagerBean.getUserType() ){
                            logger.error("userLogin error , userAccount or userPassword is error " + baseResponse.getRequestId());
                            return BaseResponse.setResponse(baseResponse, ResponseCode.LOGIN_FAILD.toString(), "userAccount or userPassword");
                        }
                        AuthUserManagerBean bean = new AuthUserManagerBean();
                        bean.setUserId(userManagerBean.getUserId());
                        //获取校长的信息
                        userManagerBeans = teacherService.getPrincipalInfo(bean);
                      //  如果是教师登录的话，校长及其他成员的账号是不能登录的
                    }else if(ConstantEnum.USER_TYPE_TEACHER.getType() == userBean.getUserType()){
                        if(ConstantEnum.USER_TYPE_TEACHER.getType() != userManagerBean.getUserType() ){
                            logger.error("userLogin error , userAccount or userPassword is error " + baseResponse.getRequestId());
                            return BaseResponse.setResponse(baseResponse, ResponseCode.LOGIN_FAILD.toString(), "userAccount or userPassword");
                        }
                        //获取教师的信息
                        userManagerBeans = teacherService.getTeacherInfo(userManagerBean);
                        if(null == userManagerBeans || userManagerBeans.getUserId() <= 0 ||  null == userManagerBeans.getClassBeanList() || userManagerBeans.getClassBeanList().size() == 0){
                            logger.error("userLogin error ,class is not exist" + baseResponse.getRequestId());
                            return BaseResponse.setResponse(baseResponse,ResponseCode.RESOURCE_IMPERFECT.toString(),"class is not exist");
                        }
                    }
                    String token = UIDGenerator.getUUIDCode32();
                    String logintype = "";
                    if("Android".equals(userBean.getEquipmentType()) ||"Ios".equals(userBean.getEquipmentType())){
                        logintype="Phone";
                    }else{
                        logintype = userBean.getEquipmentType();
                    }
                    if("Wechat".equals(logintype)){
                        //更新web端token的有效时间
                        redisClientTemplate.set(userManagerBeans.getUserId()+"Wechat",token);
                        Long result =  redisClientTemplate.expire(userManagerBeans.getUserId()+"Wechat",EXPIRE_DURATION_TWO_HOUR);
                    }else if("Web".equals(logintype)){
                        //更新移动端token的有效时间
                        redisClientTemplate.set(userManagerBeans.getUserId()+"Web",token);
                        Long result =  redisClientTemplate.expire(userManagerBeans.getUserId()+"Web",EXPIRE_DURATION_WEEK);
                    }else if("Phone".equals(logintype)){
                        //更新移动端token的有效时间
                        redisClientTemplate.set(userManagerBeans.getUserId()+"Phone",token);
                        Long result =  redisClientTemplate.expire(userManagerBeans.getUserId()+"Phone",EXPIRE_DURATION_WEEK);
                    }else{
                        logger.error("userLogin error ,  equipmentType is Invalid " + baseResponse.getRequestId());
                        return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), " equipmentType is Invalid ");
                    }
                    userManagerBeans.setToken(token);
//                    //将用户登录后的信息放到 session
//                    HttpSession session = request.getSession();
//                    session.setAttribute(UserConstants.LOGIN_ID, userManagerBean.getUserId());
//                    session.setAttribute(UserConstants.LOGIN_USER_MANAGER, userManagerBean);
//
//                    //查询用户后需要查询用户绑定的角色
//                    List<AuthRoleBean> roleBeenList = roleService.getRoleByUserId(userManagerBean.getUserId());
//                    session.setAttribute(UserConstants.LOGIN_ROLES, roleBeenList);
//                    //根据用户ID查询资源菜单
//                    List<AuthResourceBean> resourceBeen = resourceService.getResourceByUserId(userManagerBean.getUserId());
//                    session.setAttribute(UserConstants.LOGIN_MENUS, resourceBeen);
                    //TODO 登录需要优化
                    //绑定不同角色的用户的微信公众号
                    if (userBean.getUserOpenId() != null) {
                        AuthUserWeChatBindBean userWeChatBindBean = new AuthUserWeChatBindBean();
                        userWeChatBindBean.setOpenId(userBean.getUserOpenId());
                        AuthUserWeChatBindBean userWeChatBind = authUserWeChatBindService.getUserWeChatBind(userWeChatBindBean);
                        if (null == userWeChatBind) {
                            logger.error("userLogin  Exception：requestId：" + baseResponse.getRequestId() + "," + ResponseCode.RESOURCE_NOTFOUND.toString() + ".userLogin  AuthUserWeChatBindBean is null by openid");
                            return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "userLogin  AuthUserWeChatBindBean is null by openid");
                        }
                        userWeChatBind.setUserId(userManagerBean.getUserId());
                        userWeChatBind.setType(userManagerBean.getUserType());
                        userWeChatBind.setUpdateDate(new Date());
                        authUserWeChatBindService.updateUserWeChatBind(userWeChatBind);
                    }
                    baseResponse.setResult(userManagerBeans);
                }
//            } else {
//                AuthUserBean updateUserBean = new AuthUserBean();
//                updateUserBean.setUserId(userBeanLogin.getUserId());
//                updateUserBean.setUserLoginDate(new Date());
//                userService.updateUserInfo(updateUserBean);
//                //将用户登录后的信息放到 session
//                HttpSession session = request.getSession();
//                session.setAttribute(UserConstants.LOGIN_ID, userBeanLogin.getUserId());
//                session.setAttribute(UserConstants.LOGIN_USER, userBeanLogin);
//
//                //查询用户后需要查询用户绑定的角色
//                List<AuthRoleBean> roleBeenList = roleService.getRoleByUserId(userBeanLogin.getUserId());
//                session.setAttribute(UserConstants.LOGIN_ROLES, roleBeenList);
//                //根据用户ID查询资源菜单
//                List<AuthResourceBean> resourceBeen = resourceService.getResourceByUserId(userBeanLogin.getUserId());
//                session.setAttribute(UserConstants.LOGIN_MENUS, resourceBeen);
//                //TODO 登录需要优化
//                baseResponse.setResult(true);
//            }

        } catch (Exception ex) {
            logger.error("AuthUserController.userLogin error", ex);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 后台管理  用户登录
     *
     * @param requestId
     * @param requestBody
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/userManagerLogin", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public BaseResponse userManagerLogin(@RequestParam("requestId") String requestId, @RequestBody String requestBody,
                                         HttpServletRequest request) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            AuthUserManagerBean userBean = GsonUtils.getGson().fromJson(requestBody, AuthUserManagerBean.class);
            if (StringUtils.isBlank(userBean.getUserAccount()) && StringUtils.isBlank(userBean.getUserPhone())) {
                logger.error("userAccount or userPhone error , userAccount or userPhone is null " + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userAccount or userPhone");
            }
            if (StringUtils.isBlank(userBean.getUserPassword())) {
                logger.error("userPassword error , userPassword is null " + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userPassword");
            }
            if (null == userBean || null == userBean.getEquipmentType() || "".equals(userBean.getEquipmentType())) {
                logger.error("equipmentType error  " + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "equipmentType");
            }
            AuthUserManagerBean userManagerBeanCondition = new AuthUserManagerBean();
            userManagerBeanCondition.setUserAccount(userBean.getUserAccount());
            userManagerBeanCondition.setUserPhone(userBean.getUserPhone());
            userManagerBeanCondition.setUserPassword(userBean.getUserPassword());
            AuthUserManagerBean userManagerBean = userManagerService.getUserByAccountAndPwd(userManagerBeanCondition);
            if (null == userManagerBean || null == userManagerBean.getUserId() || userManagerBean.getUserId() <= 0) {
                logger.error("userLogin error , userAccount or userPassword is error " + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.LOGIN_FAILD.toString(), "userAccount or userPassword");
            } else {
                //将用户登录后的信息放到 session
                HttpSession session = request.getSession();
                session.setAttribute(UserConstants.LOGIN_ID, userManagerBean.getUserId());
                session.setAttribute(UserConstants.LOGIN_USER_MANAGER, userManagerBean);

                if (null != userManagerBean.getUserType()) {
                    if (userManagerBean.getUserType() == ConstantEnum.USER_TYPE_SYSTEM_TEACHER.getType() || userManagerBean.getUserType() == ConstantEnum.USER_TYPE_SYSTEM_ADMIN.getType()) {
                        if("1".equals(userManagerBean.getStatus())){
                            logger.error("userStatus is Disabled" + baseResponse.getRequestId());
                            return BaseResponse.setResponse(baseResponse, ResponseCode.FORBIDDEN_NOPERMISSION.toString(), "userStatus is Disabled");
                        }


                        //查询用户后需要查询用户绑定的角色
                        List<AuthRoleBean> roleBeenList = roleService.getRoleByUserId(userManagerBean.getUserId());
                        session.setAttribute(UserConstants.LOGIN_ROLES, roleBeenList);
                        //根据用户ID查询资源菜单
                        List<AuthResourceBean> resourceBeen = resourceService.getResourceByUserId(userManagerBean.getUserId());
                        session.setAttribute(UserConstants.LOGIN_MENUS, resourceBeen);
                        //TODO 登录需要优化
                        //查询用户权限对应的资源列表（去重） 返回前端用于左侧的菜单栏显示  目前为一期需求，只查询第一级目录
                        List<EnumBean> list = userManagerService.getUserManagerResourceList(userManagerBean);
                        if (null != list && list.size() > 0) userManagerBean.setList(list);
                    }else if(userManagerBean.getUserType() == ConstantEnum.USER_TYPE_SCHOOL_ADMIN.getType()){
                        AuthUserGroupBindBean userGroupBindBean = new AuthUserGroupBindBean();
                        userGroupBindBean.setUserId(userManagerBean.getUserId());
                        userGroupBindBean.setUserType(ConstantEnum.USER_TYPE_SCHOOL_ADMIN.getType());
                        List<AuthUserGroupBindBean> list = authUserGroupBindService.getUserGroupBindByCondition(userGroupBindBean);
                        if(!CollectionUtils.isEmpty(list)) {
                            AuthUserGroupBindBean bean = list.get(0);
                            AuthGroupBean authGroupBean = groupService.getGroupInfoById(bean.getGroupId());
                            if(null != authGroupBean){
                                userManagerBean.setGroupAreaDistrictId(authGroupBean.getGroupAreaDistrictId());
                                userManagerBean.setGroupAreaDistrictName(authGroupBean.getGroupAreaDistrictName());
                            }
                        }
                    }
                }

                if (userManagerBean.getUserType() == ConstantEnum.USER_TYPE_AREA_ADMINISTRATOR.getType()){
                    AuthUserAuthorityManagerBean condition = new AuthUserAuthorityManagerBean();
                    condition.setUserAccount(userBean.getUserAccount());
                    condition.setUserPhone(userBean.getUserPhone());
                    condition.setUserPassword(userBean.getUserPassword());
                    List<AuthUserAuthorityManagerBean> bean = authorizeService.getUserByAccountAndPwd(condition);
                    if (null == bean ||  bean.size() == 0) {
                        logger.error("userManagerLogin user  login error , userAccount or userPassword is error " + baseResponse.getRequestId());
                        return BaseResponse.setResponse(baseResponse, ResponseCode.LOGIN_FAILD.toString(), "userAccount or userPassword");
                    }
                    AuthUserAuthorityManagerBean userAuthority = bean.get(0);
                    userManagerBean.setGroupAreaDistrictId(userAuthority.getUserGroupAreaDistrictId());
                    userManagerBean.setGroupAreaDistrictName(userAuthority.getUserGroupAreaDistrictName());
                }
                String token = UIDGenerator.getUUIDCode32();
                String logintype = "";
                if("Android".equals(userBean.getEquipmentType()) ||"Ios".equals(userBean.getEquipmentType())){
                    logintype="Phone";
                }else{
                    logintype = userBean.getEquipmentType();
                }
                if("Wechat".equals(logintype)){
                    //更新web端token的有效时间
                    redisClientTemplate.set(userManagerBean.getUserId()+"Wechat",token);
                    redisClientTemplate.expire(userManagerBean.getUserId()+"Wechat",EXPIRE_DURATION_TWO_HOUR);
                }else if("Web".equals(logintype)){
                    //更新移动端token的有效时间
                    redisClientTemplate.set(userManagerBean.getUserId()+"Web",token);
                    redisClientTemplate.expire(userManagerBean.getUserId()+"Web",EXPIRE_DURATION_WEEK);
                }else if("Phone".equals(logintype)){
                    //更新移动端token的有效时间
                    redisClientTemplate.set(userManagerBean.getUserId()+"Phone",token);
                    redisClientTemplate.expire(userManagerBean.getUserId()+"Phone",EXPIRE_DURATION_WEEK);
                }else{
                    logger.error("userLogin error ,  equipmentType is Invalid " + baseResponse.getRequestId());
                    return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), " equipmentType is Invalid ");
                }
                userManagerBean.setToken(token);

                baseResponse.setResult(userManagerBean);
            }
        } catch (Exception ex) {
            logger.error("AuthUserController.userLogin error", ex);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 用户登出
     *
     * @param requestId
     * @param requestBody
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/userLoginOut", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public BaseResponse userLoginOut(@RequestParam("requestId") String requestId, @RequestBody String requestBody,
                                     HttpServletRequest request) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        request.getSession().removeAttribute(UserConstants.LOGIN_ID);
        request.getSession().invalidate();
        baseResponse.setResult(true);
        return baseResponse;
    }
}
