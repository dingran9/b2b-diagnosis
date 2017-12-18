package com.eedu.diagnosis.manager.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.common.service.redisService.RedisClientTemplate;
import com.eedu.diagnosis.manager.filter.BodyReaderHttpServletRequestWrapper;
import com.eedu.diagnosis.manager.filter.HttpHelper;
import com.eedu.diagnosis.manager.responsecode.BaseResponse;
import com.eedu.diagnosis.manager.responsecode.ResponseCode;
import com.eeduspace.uuims.comm.util.base.encrypt.Digest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * Token校验
 * Created by dqy on 2017/4/6.
 */
public class TokenVerificationInterceptor extends HandlerInterceptorAdapter {
    private final Logger log = LoggerFactory.getLogger(TokenVerificationInterceptor.class);
    private static final int EXPIRE_DURATION_WEEK = 60 * 60 * 24 * 7;//单位 秒
    private static final int EXPIRE_DURATION_TWO_HOUR = 60 * 60 * 2;//单位 秒

    private String requestId;
    @Autowired
    private RedisClientTemplate redisClientTemplate;
    @Value("${pass.urls}")
    private String urls;
    @Value("${signature.key}")
    private String signatureKey;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String uri = request.getRequestURI();
        if (HttpMethod.OPTIONS.matches(request.getMethod())){
            return true;
        }
        if (urls.contains(uri)) return true;
        try {

            // 校验token
            requestId = request.getParameter("requestId");
            BaseResponse baseResponse = new BaseResponse(requestId);
            String userId = request.getHeader("userId");
            String token = request.getHeader("token");

//            校验signature签名，获取body体里的参数
            ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(request);
            String body = HttpHelper.getBodyString(requestWrapper);
            String signature = request.getHeader("signature");
//            if (StringUtils.isBlank(signature)){
//                baseResponse.setMessage("该签名不存在！");
//                baseResponse.setHttpCode("600007");
//                response.setCharacterEncoding("utf-8");
//                response.getWriter().print(JSONObject.toJSONString(baseResponse));
//                return false;
//            }
            if (!StringUtils.isBlank(signature)){
                String md5Digest = "";
                if (RequestMethod.POST.toString().equalsIgnoreCase(request.getMethod())) {
                    md5Digest = Digest.md5Digest(signatureKey + Digest.md5Digest(body));
                } else {
                    md5Digest = Digest.md5Digest(signatureKey);
                }
                log.info("md5===>" + Digest.md5Digest(body));
                if (!URLDecoder.decode(signature, "UTF-8").equals(URLDecoder.decode(md5Digest, "UTF-8"))) {
                    log.info("signature===>" + signature);
                    log.info("md5Digest===>" + md5Digest);
                    baseResponse.setMessage("该账号的签名不正确！");
                    baseResponse.setHttpCode("600004");
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().print(JSONObject.toJSONString(baseResponse));
                    return false;
                }
            }
            if (!StringUtils.isBlank(userId) && !StringUtils.isBlank(token)) {

                String un_userId = redisClientTemplate.get("Unbundled_Teacher_"+userId);
                if(null != un_userId){
                    log.info("userId===>" + userId);
                    log.info("un_userId===>" + un_userId);
                    baseResponse.setHttpCode(ResponseCode.IINCOMPLETE_INFORMATION.httpCode);
                    baseResponse.setMessage("组织信息不全，请重新登录");
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().print(JSONObject.toJSONString(baseResponse));
                    return false;
                }
                String le_userId = redisClientTemplate.get("Leave_Teacher_"+userId);
                if(null != le_userId){
                    log.info("userId===>" + userId);
                    log.info("le_userId===>" + le_userId);
                    baseResponse.setHttpCode(ResponseCode.INVALID_ACCOUNT_NUMBER.httpCode);
                    baseResponse.setMessage("账号无效，请重新登录");
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().print(JSONObject.toJSONString(baseResponse));
                    return false;
                }
                String wechat = redisClientTemplate.get(userId+"Wechat");
                String webToken = redisClientTemplate.get(userId+"Web");
                String phoneToken=redisClientTemplate.get(userId+"Phone");
                String authorityWebToken =redisClientTemplate.get("authority"+userId + "Web");
                String authorityPhoneToken =redisClientTemplate.get("authority"+userId + "Phone");
                if(StringUtils.isBlank(webToken) && StringUtils.isBlank(wechat)
                        && StringUtils.isBlank(phoneToken) && StringUtils.isBlank(authorityWebToken)
                        && StringUtils.isBlank(authorityPhoneToken)){
                    baseResponse.setMessage("token过期，请重新登录");
                }
                if(token.equals(webToken)){
                    //更新web端token的有效时间
                    redisClientTemplate.expire(userId+"Web",EXPIRE_DURATION_TWO_HOUR);
                    return true;
                }else if(token.equals(phoneToken)){
                    //更新移动端token的有效时间
                    redisClientTemplate.expire(userId+"Phone",EXPIRE_DURATION_WEEK);
                    return true;
                }else if(token.equals(wechat)){
                    //更新移动端token的有效时间
                    redisClientTemplate.expire(userId+"Wechat",EXPIRE_DURATION_WEEK);
                    return true;
                }else if (token.equals(authorityWebToken)){
                    //更新教研员和局长web端token的有效时间
                    redisClientTemplate.expire("authority"+userId + "Web",EXPIRE_DURATION_TWO_HOUR);
                    return true;
                }else if (token.equals(authorityPhoneToken)){
                    //更新教研员和局长移动端token的有效时间
                    redisClientTemplate.expire("authority"+userId + "Phone",EXPIRE_DURATION_WEEK);
                    return true;
                }else {
                    log.info("localToken===>" + token);
                    log.info("userId===>" + userId);
                    log.info("redisToken===>" + wechat+"-----"+webToken+"-----"+phoneToken+"-----"+authorityWebToken);
                    baseResponse.setHttpCode(ResponseCode.ACCOUNT_LOGIN_ELSEWHERE.httpCode);
                    baseResponse.setMessage("账号已在别处登录，请重新登录。");
                }
            } else {
                log.info("localToken===>" + token);
                log.info("userId===>" + userId);
                baseResponse.setHttpCode(ResponseCode.TOKEN_EXPIRED.httpCode);
                baseResponse.setMessage("token过期，请重新登录");
            }
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(JSONObject.toJSONString(baseResponse));
            return false;
        } catch (Exception e) {
            log.info("TokenVerificationInterceptor error:{}", e);
            BaseResponse baseResponse = new BaseResponse(requestId);
            response.setCharacterEncoding("utf-8");
            baseResponse.setMessage("服务器异常");
            baseResponse.setHttpCode(ResponseCode.SERVICE_ERROR.httpCode);
            try {
                response.getWriter().print(JSONObject.toJSONString(baseResponse));
            } catch (IOException e1) {
                log.info("TokenVerificationInterceptor error:{}", e1);
            }
            return false;
        }
    }
}
