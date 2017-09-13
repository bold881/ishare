package com.neo.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.neo.entity.UserInfo;
import com.neo.sevice.UserInfoService;

public class SessionInterceptor implements HandlerInterceptor {
	
	@Autowired
	private UserInfoService userInfoService;

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		Subject currentUser = SecurityUtils.getSubject();
		if(!currentUser.isAuthenticated() && currentUser.isRemembered()) {
			try {
			UserInfo userInfo = (UserInfo) currentUser.getPrincipal();
			userInfo = userInfoService.findByUsername(userInfo.getUsername());
			UsernamePasswordToken token = new UsernamePasswordToken(userInfo.getName(), 
					userInfo.getPassword(), 
					currentUser.isRemembered());
			currentUser.login(token);
			Session session = currentUser.getSession();
			session.setAttribute("currentUser", userInfo);
			session.setTimeout(-1000l);
			} catch (Exception e) {
				response.sendRedirect(request.getContextPath()+"/login");
				return false;
			}
			
			if(!currentUser.isAuthenticated()) {
				response.sendRedirect(request.getContextPath());
				return false;
			}
		} 
	
		return true;
	}

}
