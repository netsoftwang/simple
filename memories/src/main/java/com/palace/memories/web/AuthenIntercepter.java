package com.palace.memories.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.palace.memories.auth.UserService;
import com.palace.memories.utils.CS;
import com.palace.memories.utils.PA;

public class AuthenIntercepter implements HandlerInterceptor {

 
 
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpServletRequest req = (HttpServletRequest) request;
		String uri =  req.getRequestURI();
		Map<String,Object> map = (Map<String, Object>) req.getAttribute(PA.strSessionId);
		int r = UserService.checkURI(MapUtils.getLongValue(map, PA.lUserId),uri);
		if(r == UserService.authen_pass) {
			 return true;
		}
		//TODO 权限不足
		request.getRequestDispatcher(CS.errorPage).forward(request, response);

		return false;
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
