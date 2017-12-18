package com.eedu.diagnosis.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ParamsFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(ParamsFilter.class);

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            if (RequestMethod.POST.toString().equalsIgnoreCase(httpServletRequest.getMethod())) {
                // 防止流读取一次后就没有了, 所以需要将流继续写出去
                ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(httpServletRequest);
                String body = HttpHelper.getBodyString(requestWrapper);
                logger.debug("请求uri：--------->" + ((HttpServletRequest) request).getRequestURI());
                logger.debug("请求方地址：------->"+ request.getRemoteAddr());
                logger.debug("请求参数：--------->"+body);
                filterChain.doFilter(requestWrapper, response);
            }else{
            	filterChain.doFilter(request, response);
            }
	}
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
