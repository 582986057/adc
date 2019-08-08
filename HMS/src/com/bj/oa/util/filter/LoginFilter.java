package com.bj.oa.util.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bj.oa.identity.bean.User;

public class LoginFilter extends HandlerInterceptorAdapter {

	/*
	 * (non-Javadoc)
	 * 该方法的返回值是一个boolean类型 ，如果返回 true表示放行  flase表示不通过，直接跳转至登陆页面即可
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 从session 中获取用户信息
		User user = (User)request.getSession().getAttribute("session_user");
		
		if(user == null) {
			//跳转至登陆页面并给用户提示
			request.setAttribute("message", "您尚未登陆，请在登陆后进行相关操作！");
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
		return false;
		}else {
			return true;
		}
		
		
	}
	
	
}
