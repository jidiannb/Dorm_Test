	package org.wdl.dormTest.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.wdl.dormTest.util.CookieUtil;
/**
 * Servlet implementation class LoginOutServlet
 */
@WebServlet("/loginOut.action")
public class LoginOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginOutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @throws IOException 
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("=====退出=====");
		
//		清除保存在session中的信息
		request.getSession().removeAttribute("session_user");
		
//		清除保存在cookie中的信息
		Cookie cookie = CookieUtil.getCookieByName(request, "cookie_name_pass");
//		根据名字查找保存当前项目的学号和密码
		if(cookie != null) {
//			设置当前cookie的有效时间  为0,马上失效
//			-1表示为仅在本浏览器窗口以及本窗口打开的子窗口内有效，关闭窗口后该Cookie即失效
			cookie.setMaxAge(0);
//			设置当前cookie的作用域,作用域
			cookie.setPath(request.getContextPath());
//			做完需要将cookie响应出去
			response.addCookie(cookie);
		}
		response.sendRedirect("index.jsp");
			
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
