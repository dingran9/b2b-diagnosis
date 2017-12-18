package com.eedu.diagnosis.protal.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.common.service.redisService.RedisClientTemplate;
import com.eedu.diagnosis.protal.filter.BodyReaderHttpServletRequestWrapper;
import com.eedu.diagnosis.protal.filter.HttpHelper;
import com.eedu.diagnosis.protal.responsecode.BaseResponse;
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

            //校验signature签名，获取body体里的参数
            ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(request);
            String body = HttpHelper.getBodyString(requestWrapper);
           // String body = bodys.replaceAll("\\\\", "");

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
            String openId = request.getHeader("openId");
            String token = request.getHeader("token");
            if (!StringUtils.isBlank(openId) && !StringUtils.isBlank(token)) {
                String wechat = redisClientTemplate.get(openId+"Wechat");
                String webToken = redisClientTemplate.get(openId+"Web");
                String phoneToken=redisClientTemplate.get(openId+"Phone");
                if(StringUtils.isBlank(webToken) && StringUtils.isBlank(wechat) && StringUtils.isBlank(phoneToken)){
                    baseResponse.setMessage("token过期，请重新登录");
                }
                if(token.equals(webToken)){
                    //更新web端token的有效时间
                    redisClientTemplate.expire(openId+"Web",EXPIRE_DURATION_TWO_HOUR);
                    return true;
                }else if(token.equals(phoneToken)){
                    //更新移动端token的有效时间
                    redisClientTemplate.expire(openId+"Phone",EXPIRE_DURATION_WEEK);
                    return true;
                }else if(token.equals(wechat)){
                    //更新移动端token的有效时间
                    redisClientTemplate.expire(openId+"Wechat",EXPIRE_DURATION_WEEK);
                    return true;
                }else {
                    log.info("localToken===>" + token);
                    log.info("openId===>" + openId);
                    log.info("redisToken===>" + wechat+"-----"+webToken+"-----"+phoneToken);
                    baseResponse.setHttpCode("600002");
                    baseResponse.setMessage("账号已在别处登录，请重新登录。");
                }
            } else {
                log.info("localToken===>" + token);
                log.info("openId===>" + openId);
                baseResponse.setHttpCode("600003");
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
            baseResponse.setHttpCode("500");
            try {
                response.getWriter().print(JSONObject.toJSONString(baseResponse));
            } catch (IOException e1) {
                log.info("TokenVerificationInterceptor error:{}", e1);
            }
            return false;
        }
    }
}
