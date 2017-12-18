/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.eedu.diagnosis.manager.filter;

import com.eedu.auth.beans.AuthResourceBean;
import com.eedu.auth.beans.AuthRoleBean;
import com.eedu.auth.beans.AuthUserBean;
import com.eedu.auth.beans.AuthUserManagerBean;
import com.eedu.auth.beans.enums.UserConstants;
import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


/**
 * @author Wallace chu
 *
 */
public class AccessFilter implements Filter {

	private final Logger logger = org.slf4j.LoggerFactory.getLogger(AccessFilter.class);

	/** (non-Javadoc)
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/** (non-Javadoc)
	 * @see Filter#doFilter(ServletRequest,
	 * ServletResponse, FilterChain)
	 * @param servletRequest servletRequest
	 * @param servletResponse servletResponse
	 * @param filterChain filterChain
	 * @throws IOException IOException
	 * @throws ServletException ServletException
	 */
	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
	                     FilterChain filterChain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		//请求的URI
		String uri = request.getRequestURI();

		//Referer从哪个页面链接过来的
		String referer = request.getHeader("Referer");
		logger.info("AccessFilter-getRequestURI:" + request.getRequestURI());
		logger.info("AccessFilter-referer:" + referer);

		HttpSession session = request.getSession();

		//用户已经登录
		if (session.getAttribute(UserConstants.LOGIN_ID) != null ) {
			//有权限的菜单
			List<AuthResourceBean> userResource = (List<AuthResourceBean>)session.getAttribute(UserConstants.LOGIN_MENUS);
			//用户角色
			List<AuthRoleBean> roleBeanList = (List<AuthRoleBean>)session.getAttribute(UserConstants.LOGIN_ROLES);
			AuthUserManagerBean userManagerBean = new AuthUserManagerBean();
			AuthUserBean userBean = new AuthUserBean();
			if(session.getAttribute(UserConstants.LOGIN_USER) == null){
				userManagerBean = (AuthUserManagerBean)session.getAttribute(UserConstants.LOGIN_USER_MANAGER);
				BeanUtils.copyProperties(userBean,userManagerBean);
			}else{
				userBean = (AuthUserBean)session.getAttribute(UserConstants.LOGIN_USER);
			}

			String userId = (String)session.getAttribute(UserConstants.LOGIN_ID);
			//非管理员越权过滤
			if (null != userResource) {
				logger.info("AccessFilter：用户" + userBean.getUserName() + "已登录");
				//判读是否有权限
				boolean hasRight = false;
				for (AuthResourceBean resourceBean : userResource) {
					if (null != resourceBean.getResourceUrl() && uri.indexOf(resourceBean.getResourceUrl()) > -1) {
						hasRight = true;
					}
				}
				//无权限提示
				if (!hasRight) {
					String path = request.getContextPath();
//					response
//					response.sendRedirect(path + "/message.do?message=" + URLEncoder.encode("操作未授权，请联系系统管理员!", "UTF-8"));
					return;
				}
			}
			return;
		}
	}

	/** (non-Javadoc)
	 * @see Filter#init(FilterConfig)
	 * @param arg0 arg0
	 * @throws ServletException ServletException
	 */
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}

}
