package com.eedu.diagnosis.protal.controller;

import com.alibaba.fastjson.JSONObject;
import com.eedu.auth.beans.AuthUserBean;
import com.eedu.auth.service.AuthUserService;
import com.eedu.diagnosis.common.service.redisService.RedisClientTemplate;
import com.eedu.diagnosis.common.utils.MD5Utils;
import com.eedu.diagnosis.protal.model.ZTEResponse;
import com.eedu.diagnosis.protal.model.ZTEToken;
import com.eedu.diagnosis.protal.model.response.AuthUserVo;
import com.eedu.diagnosis.protal.model.user.TokenModel;
import com.eedu.diagnosis.protal.model.user.UserInfoResponse;
import com.eedu.diagnosis.protal.responsecode.BaseResponse;
import com.eedu.diagnosis.protal.responsecode.ResponseCode;
import com.eedu.diagnosis.protal.service.UserService;
import com.eeduspace.uuims.api.OauthClient;
import com.eeduspace.uuims.api.request.user.CreateUserRequest;
import com.eeduspace.uuims.api.request.user.ValidateUserRequest;
import com.eeduspace.uuims.api.response.user.CreateUserResponse;
import com.eeduspace.uuims.api.response.user.ValidateUserResponse;
import com.eeduspace.uuims.comm.util.base.json.GsonUtil;
import com.google.gson.Gson;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 集成中兴登录
 */
@RestController
@RequestMapping("/zte/user")
public class ZTEUserController {
    @Value("${diagnosis.protal.uuims.server.url}")
    private String serverUrl;
    @Value("${diagnosis.protal.accessKey}")
    private String accessKey;
    @Value("${diagnosis.protal.secretKey}")
    private String secretKey;
    @Value("${diagnosis.protal.productType}")
    private String productType;
    private final Logger logger = LoggerFactory.getLogger(ZTEUserController.class);
    private Gson gson=new Gson();
    @Autowired
    private UserService userService;
    private RestTemplate restTemplate=new RestTemplate();
    @Autowired
    private AuthUserService authUserService;
    @Autowired
    private RedisClientTemplate redisClientTemplate;
    /**
     * 中兴对接-用户登录
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse login(@RequestParam("Token") String token) {
        BaseResponse baseResponse = new BaseResponse();
        ZTEToken zteToken =new ZTEToken();
        zteToken.setSERVICE_CODE("zteict.proxy.user.LoginStatus");
        zteToken.setCONSUMER_ID(token);
        ZTEResponse zteResponse = validTokenFromZTE(zteToken);
        ZTEResponse.Body body = zteResponse.getBODY();
        if (StringUtils.isBlank(body.getUSER_ID())){
            return BaseResponse.setResponse(baseResponse, ResponseCode.TOKEN_INVALID.toString(), "TOKEN_INVALID");
        }
        OauthClient client = new OauthClient(serverUrl, accessKey, secretKey);
        ValidateUserRequest request = new ValidateUserRequest();
        request.setName(body.getUSER_ID());
        try {
            ValidateUserResponse execute = client.execute(request);
            String openId;
            if ("200".equals(execute.getHttpCode())) {//表示用户不存在
                //创建用户
                CreateUserRequest createUserRequest = new CreateUserRequest();
                createUserRequest.setName(body.getUSER_ID());
                createUserRequest.setPassword(MD5Utils.getMD5("123456").toUpperCase());
                CreateUserResponse createResponse = client.execute(createUserRequest);
                UserInfoResponse userInfoResponse = GsonUtil.fromObjectJson(gson.toJson(createResponse), "result", "userModel", UserInfoResponse.class);
                AuthUserBean t = new AuthUserBean();
                t.setUserAccount(body.getUSER_ID());
                t.setUserOpenId(userInfoResponse.getOpenId());
                t.setUserAccesskey(userInfoResponse.getAccessKey());
                t.setUserSecretkey(userInfoResponse.getSecretKey());
                authUserService.addUserInfo(t);
                openId = userInfoResponse.getOpenId();
                TokenModel tokenModel = getTokenModel(token,openId, body.getUSER_ID());
                baseResponse.setResult(tokenModel);
            } else {

                UserInfoResponse userInfoResponse = JSONObject.parseObject(gson.toJson(execute.getResult()), UserInfoResponse.class);
                //用户存在UUIMS
                openId = userInfoResponse.getOpenId();
                //但是产品中不存在
                AuthUserBean authUserBean = new AuthUserBean();
                authUserBean.setUserAccount(body.getUSER_ID());
                List<AuthUserBean> userByCondition = authUserService.getUserByCondition(authUserBean);
                if(CollectionUtils.isEmpty(userByCondition)){
                    AuthUserBean t = new AuthUserBean();
                    t.setUserAccount(body.getUSER_ID());
                    t.setUserOpenId(userInfoResponse.getOpenId());
                    t.setUserAccesskey(userInfoResponse.getAccessKey());
                    t.setUserSecretkey(userInfoResponse.getSecretKey());
                    authUserService.addUserInfo(t);
                }
                TokenModel tokenModel = getTokenModel(token,openId, body.getUSER_ID());
                baseResponse.setResult(tokenModel);
            }



//            if(userRequestModel.getEquipmentType().equals("Phone")){
//                redisClientTemplate.setex(loginResponse.getUserModel().getOpenId() + "Phone", 86400, token);
//            }else if (userRequestModel.getEquipmentType().equals("Wechat")) {
//                redisClientTemplate.setex(loginResponse.getUserModel().getOpenId() + "Wechat", 86400, token);
//            }else{
//            }

        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(),ResponseCode.SERVICE_ERROR.toString());
        }

        return baseResponse;
    }

    private TokenModel getTokenModel(String token, String openId,String userAccount) throws Exception {
        TokenModel tokenModel=new TokenModel();
        AuthUserBean authUserBean = new AuthUserBean();
        authUserBean.setUserAccount(userAccount);
        authUserBean = authUserService.getUserByCondition(authUserBean).get(0);
        tokenModel.setCode(authUserBean.getUserId()+"");
        tokenModel.setToken(token);
        tokenModel.setExpires("86400");
        tokenModel.setOpenId(openId);
        AuthUserVo authUserVo = new AuthUserVo();
        BeanUtils.copyProperties(authUserBean,authUserVo);
        tokenModel.setAuthUserVo(authUserVo);
        redisClientTemplate.setex(openId + "Web", 86400, token);
        return tokenModel;
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
