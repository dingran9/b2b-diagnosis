package com.eedu.diagnosis.manager.filter;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ParamsFilter implements Filter{
    private final Logger logger = LoggerFactory.getLogger(ParamsFilter.class);

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (RequestMethod.POST.toString().equalsIgnoreCase(httpServletRequest.getMethod())) {
            //防止文件上传httpServletRequest中的文件流被转换成字符串
            if(null != httpServletRequest.getContentType() && httpServletRequest.getContentType().startsWith(ContentType.MULTIPART_FORM_DATA.getMimeType())){
                filterChain.doFilter(httpServletRequest, response);
            }else{
                // 防止流读取一次后就没有了, 所以需要将流继续写出去
                ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(httpServletRequest);
                String body = HttpHelper.getBodyString(requestWrapper);
                logger.debug("请求uri：--------->" + ((HttpServletRequest) request).getRequestURI());
                logger.debug("请求方地址：------->"+ getIpAddress(httpServletRequest));
                logger.debug("请求参数：--------->"+body);
                filterChain.doFilter(requestWrapper, response);
            }
        }else if (RequestMethod.GET.toString().equalsIgnoreCase(httpServletRequest.getMethod())){
            logger.info("请求uri：--------->" + ((HttpServletRequest) request).getRequestURI());
            logger.info("请求方地址：------->"+ getIpAddress(httpServletRequest));
            logger.info("请求参数：------->"+ JSONObject.toJSONString(request.getParameterMap()));
            filterChain.doFilter(request, response);
        }else{
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub

    }

    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
