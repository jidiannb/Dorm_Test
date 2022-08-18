package org.wdl.dormTest.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wdl.dormTest.bean.User;
import org.wdl.dormTest.service.UserService;
import org.wdl.dormTest.service.UserServiceImpl;
import org.wdl.dormTest.util.CookieUtil;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("========登录========");
//		根据输入框标签的name属性值获取学号和密码
		String stuCode = request.getParameter("stuCode");
		String password = request.getParameter("password");
		String remember = request.getParameter("remember");
		System.out.println("stuCode:"+stuCode+"   password:"+password+" 	remember:"+remember);
		
		UserService userService = new UserServiceImpl();
//		查询用户输入的用户名和密码是否正确
		User user =userService.findByStuCodeAndPass(stuCode, password);
		System.out.println("user:"+user);
		
		if (user==null) {
//			user为空则学号或者密码错误,返回登陆界面,并给予提示信息
			request.setAttribute("error", "您输入的信息有误");
//			请求链未断开的的跳转,可以在下一个servlet或jsp中,获取保存在request中的数据
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}else {
//			用户输入的学号和密码正确,登录成功,跳转到主页面
			//保存在session中的数据默认是30分钟有效.保存在session中的数据,在整个项目中都可以获取到
			request.getSession().setAttribute("session_user",user);
			
			if(remember != null && remember.equals("remember-me")) {
//			记住密码一周
				CookieUtil.addCookie("cookie_name_pass",60,request,response, stuCode, password);
			}
			
			System.out.println("========登录成功========");
			request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);

		}
	} 

}
