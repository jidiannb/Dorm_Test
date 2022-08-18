package org.wdl.dormTest.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wdl.dormTest.bean.DormBuild;
import org.wdl.dormTest.service.DormBuildService;
import org.wdl.dormTest.service.DormBuildServiceImpl;

/**
 * Servlet implementation class DormbuildServlet
 */
@WebServlet("/dormBuild.action")
public class DormbuildServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DormbuildServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("======dormBuild.action======");
		request.setCharacterEncoding("utf-8");
		 
		String action = request.getParameter("action");
		System.out.println("action"+action);
		
		DormBuildService dormBuildService = new DormBuildServiceImpl();
		
		if(action != null & action.equals("list")) {
//			通过 request.getParameter获取的都是String类型
			String id = request.getParameter("id");
			
			List<DormBuild> builds = new ArrayList<DormBuild>();
			if(id == null || id.equals("")) {
//				点击左侧宿舍楼管理,查询宿舍楼信息,跳转到宿舍楼页面
				builds = dormBuildService.find();
				System.out.println("builds:"+builds);
				request.setAttribute("builds",builds);
			}else if(id != null && !id.equals("")) {
//				点击搜索按钮搜索宿舍楼,根据宿舍楼id查询宿舍楼信息
				DormBuild build = dormBuildService.findById(Integer.parseInt(id));
				builds.add(build);
			}
//			查询所有宿舍楼,在select中遍历
			List<DormBuild> buildSelects = dormBuildService.find();
			request.setAttribute("buildSelects", buildSelects);
			request.setAttribute("id", id);
			
			System.out.println("builds:"+builds);
			request.setAttribute("builds",builds);
			request.setAttribute("mainRight", "/WEB-INF/jsp/dormBuildList.jsp");
			request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
			
		}else if(action != null & action.equals("preAdd")) {
//			跳转到宿舍楼添加页面
			request.setAttribute("mainRight", "/WEB-INF/jsp/dormBuildAddOrUpdate.jsp");
			request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
			
		}else if(action != null & action.equals("save")) {
//			保存数据
			String name = request.getParameter("name");
			String remark = request.getParameter("remark");
			System.out.println("name:"+name 	+"remark:"+remark);
			
//			名字不可重复,从数据库查询当前用户输入的宿舍楼名字是否存在
			DormBuild dormBuild = dormBuildService.findByName(name);
			System.out.println("dormBuild:"+dormBuild);
			
			if(dormBuild != null) {
				//当前用户输入的宿舍楼名字已存在
//				表示跳转到宿舍楼添加页面
				request.setAttribute("error", "当前宿舍楼已经存在,请重新输入!");
				request.setAttribute("mainRight","/WEB-INF/jsp/dormBuildAddOrUpdate.jsp");
				request.getRequestDispatcher("WEB-INF/jsp/main.jsp").forward(request, response);
			}else {
				
				//当前用户输入的宿舍楼名字不存在,保存用户信息到数据库
				DormBuild build = new DormBuild();
				build.setName(name);
				build.setRemark(remark);
				build.setDisabled(0);
				dormBuildService.save(build);
				request.setAttribute("mainRight", "/WEB-INF/jsp/dormBuildList.jsp");
				request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
				
			}
		}	
		
	}
}
