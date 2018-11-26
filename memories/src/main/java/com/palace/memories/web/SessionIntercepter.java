package com.palace.memories.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.palace.memories.auth.UserService;
import com.palace.memories.utils.CS;
import com.palace.memories.utils.PA;

public class SessionIntercepter implements HandlerInterceptor {
	private static Logger log = LoggerFactory.getLogger(SessionIntercepter.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String obj = request.getParameter(CS.trans);
		String uri = request.getRequestURI();
		if(uri.equals(CS.loginPage)) {
			try {
				String strUserId = request.getParameter("strUserName");
				String strPass = request.getParameter("strPassword");
				String strType = request.getParameter("strType");
				if(UserService.doLogin(Long.parseLong(strUserId.trim()),strPass.trim(),strType.trim()) == UserService.USER_OK) {
					return true;
				}
				request.getRequestDispatcher(CS.errorPage).forward(request, response);
			} catch (Exception e) {
				log.warn("",e);
				request.getRequestDispatcher(CS.errorPage).forward(request, response);
			}
			return false;
		}
		if(StringUtils.isBlank(obj) ) {
			try {
				request.getRequestDispatcher(CS.loginPage).forward(request, response);
			} catch (Exception e) {
				log.warn("",e);
				request.getRequestDispatcher(CS.errorPage).forward(request, response);
			}
			return false;
		}
		Map<String,Object> map = (Map<String, Object>) JSONObject.parse(obj);
		request.setAttribute(PA.strSessionId,map);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
