package com.practice.pj.util;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.java.Log;

@Log
@SuppressWarnings("deprecation")
public class SessionInterceptor extends HandlerInterceptorAdapter {
		@Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
				throws Exception {
			log.info("preHandle()");
			
			HttpSession session = request.getSession();
			//세션에 로그인 정보(mb)가 없으면 첫페이지로 이동
			if(session.getAttribute("mb") == null) {
				log.info("인터셉트!");
				
				response.sendRedirect("./");
				return false;
			}
			
			return true;
		}
		
		@Override
		public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
				ModelAndView modelAndView) throws Exception {
			log.info("postHandle()");

			if(request.getProtocol().equals("HTTP/1.1")) {
				response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			}else{
				response.setHeader("Pragma","no-cache");
			}
			
			response.setDateHeader("Expires", 0); 
		}
}
