package org.wdl.dormTest.util;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.wdl.dormTest.bean.User;
import org.wdl.dormTest.service.UserService;
import org.wdl.dormTest.service.UserServiceImpl;

/**
 * 该过滤器,拦截所有以.action结尾的请求
 */
@WebFilter("*.action")
public class LoginFilter implements Filter {

    /**
     * Default constructor. 
     */
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("-------登录拦截器--------");
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpSession session = httpServletRequest.getSession();
		
//		获取登录后保存在session中的用户信息
		User user = (User) session.getAttribute("session_user");
		System.out.println("session user:  "+user);
		UserService userService = new UserServiceImpl();
		
//		判断用户是否登录,
		if(user != null) {
//			如登录就放行,走处理该请求的方法
//			chain.doFilter(request, response);
//			通过User判断登录的用户角色是否有对该请求的访问权限
//			通过request中的HttpServletRequest.getRequestURI();来获取用户发送的请求		
			roleJudgment(user,httpServletRequest,response,chain);
			
		}else {
//			判断cookie中是否有用户信息,账号密码
//			第一步,去遍历所有的cookie,看是否有保存当前的学号和密码
			Cookie cookie = CookieUtil.getCookieByName(httpServletRequest, "cookie_name_pass");
			if(cookie != null) {
//				cookie非空,则保存了当前项目的学号和密码的cookie
				String stuCodePass = cookie.getValue();				
				System.out.println("stuCodePass:"	+stuCodePass);
				String[] stuCodePass2 = stuCodePass.split("#");
				
//				判断用户的学号的密码是否有效
				User user2 = userService.findByStuCodeAndPass(stuCodePass2[0], stuCodePass2[1]);
				
				if(user2 != null) {
//				在浏览器当中的学号和密码有效,自动登录并将用户信息保存在session中
//					通过User判断登录的用户角色是否有对该请求的访问权限
//					通过request中的HttpServletRequest.getRequestURI();来获取用户发送的请求
			
					roleJudgment(user2,httpServletRequest,response,chain);
					
//					httpServletRequest.getSession().setAttribute("session_user",user2);
//					chain.doFilter(request, response);
					
				}else {
//				user2为空,浏览器中的学号和密码无效
					request.setAttribute("error", "请先登录!");
//					如未登录就跳转到登录页面
					request.getRequestDispatcher("/index.jsp").forward(httpServletRequest, response);
				}
			}else { 
					request.setAttribute("error", "请先登录!");
//					如未登录就跳转到登录页面
					request.getRequestDispatcher("/index.jsp").forward(httpServletRequest, response);
			}
		}
	}

	private void roleJudgment(User user, HttpServletRequest httpServletRequest, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
//		获取用户角色id
		Integer roleId = user.getRoleId();
		System.out.println("roleId:"+roleId);
//		获取请求地址
		String  requestUrI = httpServletRequest.getRequestURI();
		
		System.out.println("requestUrI:"+requestUrI);
		System.out.println("roleId:"+roleId);
		
		if((requestUrI.startsWith("/DormTest/dormBuild.action") || requestUrI.startsWith("/DormTest/dormManager.action") )
				&& roleId.equals(0)) {
//			当用户发送的是宿舍楼管理模块或者宿舍管理员模块的请求时,只有在当前用户角色为超级管理员是才放行
			httpServletRequest.getSession().setAttribute("session_user",user);
			chain.doFilter(httpServletRequest, response);
			
		}else if(requestUrI.startsWith("/DormTest/student.action") && !roleId.equals(2)) {
//			当用户发送的请求是学生模块的请求时,只要当前用户不是学生就行
			httpServletRequest.getSession().setAttribute("session_user",user);
			chain.doFilter(httpServletRequest,response);
	
		}else if(requestUrI.startsWith("/DormTest/record.action") || 
				requestUrI.startsWith("/DormTest/password.action") ||
				requestUrI.startsWith("/DormTest/loginOut.action")) {
//			当用户发送的请求是考勤,修改密码,退出模块的请求时,全部放行
			chain.doFilter(httpServletRequest, response);
			httpServletRequest.getSession().setAttribute("session_user",user);

		}else {
			httpServletRequest.getRequestDispatcher("/index.jsp").forward(httpServletRequest, response);
		}	
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
