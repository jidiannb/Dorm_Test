package org.wdl.dormTest.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.Soundbank;

import org.wdl.dormTest.bean.DormBuild;
import org.wdl.dormTest.bean.User;
import org.wdl.dormTest.service.DormBuildService;
import org.wdl.dormTest.service.DormBuildServiceImpl;
import org.wdl.dormTest.service.UserService;
import org.wdl.dormTest.service.UserServiceImpl;

/**
 * Servlet implementation class DormManagerServlet
 */
@WebServlet("/dormManager.action")
public class DormManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DormManagerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("==========dormManager.action==========");
//    	在Tomcat8.0中解决post请求乱码问题
    	request.setCharacterEncoding("utf-8" );
    	String action = request.getParameter("action");
//    	宿舍管理员id
    	String id = request.getParameter("id");
    	 	   	
    	UserService userService = new UserServiceImpl();
    	DormBuildService buildService = new DormBuildServiceImpl();
    	   	
//		查询所有的宿舍楼并保存,以便在前端展示
		List<DormBuild> builds = buildService.find();
		request.setAttribute("builds",builds);
    	if(action != null & action.equals("list")) {
//		宿舍管理员查询
    	String searchType = request.getParameter("searchType");
    	String keyword = request.getParameter("keyword");
    	System.out.println("searchType:"+searchType+"	keyword:"+keyword);
    	
//    	查询宿舍管理员
    	List<User> users = userService.findManager(searchType,keyword);
    	
    	request.setAttribute("searchType",searchType);
    	request.setAttribute("keyword", keyword);
    	request.setAttribute("users", users);
    	request.setAttribute("mainRight","dormManagerList.jsp");
    	request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
    	 
    	}else if(action != null & action.equals("preAdd")){
//    		跳转到宿舍管理员页面 

    		request.setAttribute("mainRight","dormManagerAddOrUpdate.jsp");
    		request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);	
    	}else if(action != null & action.equals("save")) {
    		System.out.println("========保存宿舍管理员========");
    		String name = request.getParameter("name");
    		String passWord = request.getParameter("passWord");
    		String sex = request.getParameter("sex");
    		String tel = request.getParameter("tel");
//    		获取复选框中用户选择的宿舍楼
    		String[] dormBuildIds = request.getParameterValues("dormBuildId");
    		System.out.println("name:"+name+"  password:"+passWord+"  sex:"+sex+"  tel:"+tel+"  doemBuildIds:"+Arrays.toString(dormBuildIds));
    		
    		User user = new User(name,passWord,sex,tel,null,1);
    		user.setDisabled(0);
    		
//    		当前登录用户的id
    		User user2 = (User) request.getSession().getAttribute("session_user");
    		user.setCreateUserId(user2.getId());
    		
    		userService.saveManager(user,dormBuildIds);
    		
//    		跳转到宿舍管理员列表,查看所有的宿舍管理员
//    		重定向,请求链断开,不能在下一个servlet中或jsp中获取保存在request中的参数
    		response.sendRedirect(getServletContext().getContextPath()+"/dormManager.action?action=list");	
    	}else if(action != null & action.equals("preUpdate")) {
//    		跳转到修改宿舍管理员页面
    		
//    		根据宿舍管理员ID,获取宿舍管理员
    		User user = userService.findById(Integer.parseInt(id));
//    		根据宿舍管理员ID获取其管理的楼栋
    		List<DormBuild> userBuilds = buildService.findByUserId(user.getId());
    		user.setDormBuilds(userBuilds);
    		System.out.println("user:"+user);
    		
    		System.out.println("userBuilds:"+ userBuilds);
    		
    		List<Integer> userBuildids = new ArrayList<>();
    		
//    		遍历当前宿舍管理员管理的所有宿舍,获取当前宿舍管理员所管理的所有宿舍ID
			  for (DormBuild userBuild : userBuilds) {
				  userBuildids.add(userBuild.getId());
			  }
			  
			  for(int i = 0;i < userBuildids.size();i++) {
				  int x = userBuildids.get(i);
				  System.out.println("x: "+x);
			  }
			 
    		request.setAttribute("userBuildids", userBuildids);
    		request.setAttribute("user", user);
    		request.setAttribute("mainRight","dormManagerAddOrUpdate.jsp");
    		request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);	
    	}
	}
}




















