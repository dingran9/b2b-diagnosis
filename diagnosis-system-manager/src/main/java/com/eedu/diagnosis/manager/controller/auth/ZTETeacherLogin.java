package com.eedu.diagnosis.manager.controller.auth;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.eedu.auth.beans.AuthUserManagerBean;
import com.eedu.auth.beans.enums.ConstantEnum;
import com.eedu.auth.service.AuthUserManagerService;
import com.eedu.auth.service.AuthUserWeChatBindService;
import com.eedu.diagnosis.common.service.redisService.RedisClientTemplate;
import com.eedu.diagnosis.manager.model.ZTEResponse;
import com.eedu.diagnosis.manager.model.ZTEToken;
import com.eedu.diagnosis.manager.responsecode.BaseResponse;
import com.eedu.diagnosis.manager.responsecode.ResponseCode;
import com.eedu.diagnosis.manager.service.AuthTeacherService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * 对接中兴教师登录
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-09-09 17:49
 **/
@RestController
@RequestMapping("/zte/teacher")
public class ZTETeacherLogin {
    private static final int EXPIRE_DURATION_WEEK = 60 * 60 * 24 * 7;//单位 秒
    private static final int EXPIRE_DURATION_TWO_HOUR = 60 * 60 * 2;//单位 秒
    private final Logger logger = LoggerFactory.getLogger(ZTETeacherLogin.class);
    @Autowired
    private RedisClientTemplate redisClientTemplate;
    @Autowired
    private AuthUserManagerService userManagerService;
    @Autowired
    private AuthUserWeChatBindService authUserWeChatBindService;
    @Autowired
    private AuthTeacherService teacherService;
    private RestTemplate restTemplate=new RestTemplate();
    private Gson gson =new Gson();
    /**
     * 用户登录
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public BaseResponse userLogin(@RequestParam("Token") String token) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            ZTEToken zteToken =new ZTEToken();
            zteToken.setSERVICE_CODE("zteict.proxy.user.LoginStatus");
            zteToken.setCONSUMER_ID(token);
            ZTEResponse zteResponse = validTokenFromZTE(zteToken);
            ZTEResponse.Body body = zteResponse.getBODY();
            if (StringUtils.isBlank(body.getUSER_ID())){
                return BaseResponse.setResponse(baseResponse, ResponseCode.TOKEN_INVALID.toString(), "TOKEN_INVALID");
            }

            AuthUserManagerBean userManagerBeans = new AuthUserManagerBean();

            AuthUserManagerBean userManagerBean = userManagerService.getUserByAccount(body.getUSER_ID());
            if (null == userManagerBean || null == userManagerBean.getUserId() || userManagerBean.getUserId() <= 0) {
                logger.error("userLogin error , userAccount or userPassword is error " + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.LOGIN_FAILD.toString(), "userAccount");
            } else {
                //如果是校长登录的话，教师及其他成员的账号是不能登录的
                if(ConstantEnum.USER_TYPE_SCHOOL_MASTER.getType() == userManagerBean.getUserType()){
                    if(ConstantEnum.USER_TYPE_SCHOOL_MASTER.getType() != userManagerBean.getUserType() ){
                        logger.error("userLogin error , userAccount or userPassword is error " + baseResponse.getRequestId());
                        return BaseResponse.setResponse(baseResponse, ResponseCode.LOGIN_FAILD.toString(), "userAccount or userPassword");
                    }
                    AuthUserManagerBean bean = new AuthUserManagerBean();
                    bean.setUserId(userManagerBean.getUserId());
                    //获取校长的信息
                    userManagerBeans = teacherService.getPrincipalInfo(bean);
                    //  如果是教师登录的话，校长及其他成员的账号是不能登录的
                }else if(ConstantEnum.USER_TYPE_TEACHER.getType() == userManagerBean.getUserType()){
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

                String logintype = "Web";
//                if("Android".equals(userBean.getEquipmentType()) ||"Ios".equals(userBean.getEquipmentType())){
//                    logintype="Phone";
//                }else{
//                    logintype = userBean.getEquipmentType();
//                }
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
     * 中兴验证token
     * @param zteToken
     * @return
     */
    public ZTEResponse validTokenFromZTE(ZTEToken zteToken) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(600000);
        factory.setReadTimeout(600000);
        restTemplate.setRequestFactory(factory);
        String bathUrl = "http://edu.zteict.com/serviceProxy/servlet?json={json}";
        String json = gson.toJson(zteToken);
        String forObject = restTemplate.getForObject(bathUrl, String.class, json);
        ZTEResponse zteResponse = gson.fromJson(forObject, ZTEResponse.class);
        return zteResponse;
    }
}
