package org.wdl.dormTest.service;

import java.util.List;

import javax.sound.midi.Soundbank;

import org.wdl.dormTest.bean.DormBuild;
import org.wdl.dormTest.bean.User;
import org.wdl.dormTest.dao.DormBuildDao;
import org.wdl.dormTest.dao.DormBuildDaoImpl;
import org.wdl.dormTest.dao.UserDao;
import org.wdl.dormTest.dao.UserDaoImpl;

public  class UserServiceImpl implements UserService {
	private UserDao userDao = new UserDaoImpl();
	private DormBuildDao dormBuildDao = new DormBuildDaoImpl();

	@Override
	public User findByStuCodeAndPass(String name, String password){
		// TODO Auto-generated method stub
		return userDao.findByStuCodeAndPass(name, password);
	}

	@Override
	public void saveManager(User user,String[] dormBuildIds) {
//		找到数据库中宿舍管理员最大的学号+1作为当前要保存的宿舍管理员的学号
		String managerStuCodeMax = userDao.findmanagerStuCodeMax();
		System.out.println("managerStuCodeMax:"+managerStuCodeMax);
		user.setStuCode(managerStuCodeMax);
		
		
//		保存宿舍管理员
		Integer userId = userDao.saveManager(user);
		System.out.println("userId:"+userId);
		
//		保存宿舍管理员和宿舍楼的中间表
		dormBuildDao.saveManagerAndBuild(userId,dormBuildIds);
		
	}

	@Override
	public List<User> findManager(String searchType, String keyword) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("SELECT * FROM tb_user WHERE role_id=1 ");
		
		System.out.println("keyword:"+keyword);
		
	
		
		  if(keyword != null && !keyword.equals("")){ 
		  System.out.println("searchType:"+searchType); 
		  // 说明用户是点击搜索按钮进行搜索
		  
		  if("name".equals(searchType)) { 
			  sql.append(" and name like '%"+keyword+"%'"); 
		  	}else if("sex".equals(searchType)) { 
		  		sql.append(" and sex = '"+keyword.trim()+"'"); 
		  	}else if("tel".equals(searchType)){ 
			  sql.append(" and tel = "+keyword.trim());
		  	}
		 }	  		 			
		
		System.out.println("sql:"+sql.toString());
		
//		查询宿舍管理员
		List<User> users = userDao.findManager(sql.toString());
		for(User user : users) {
			List<DormBuild> builds = dormBuildDao.findByUserId(user.getId());
			user.setDormBuilds(builds);
		}
		System.out.println("users"+users);		
		return users;
	
	}

	@Override
	public User findById(int id) {
		// TODO Auto-generated method stub
		return userDao.findById(id);
	}
}


















