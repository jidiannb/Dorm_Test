package org.wdl.dormTest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.wdl.dormTest.bean.User;
import org.wdl.dormTest.util.ConnectionFactory;

public class UserDaoImpl implements UserDao {

	@Override
	public User findByStuCodeAndPass(String stuCode, String password){
//		获取链接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
//			准备SQL语句
			String sql = "select * from tb_user where stu_code = ? and password = ?"; 
//			准备车车
			preparedStatement = connection.prepareStatement(sql);
//			
			preparedStatement.setString(1, stuCode);
			preparedStatement.setString(2, password);
//			执行SQL语句,查询的结果封装在ResultSet
			rs = preparedStatement.executeQuery();
			while(rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setCreateUserId(rs.getInt("create_user_id"));
				user.setDisabled(rs.getInt("disabled"));
				user.setDormBuildId((rs.getInt("dormBuildId")));
				user.setDormCode((rs.getString("dorm_Code")));
				user.setName(rs.getString("name"));
//				user.setStuCode(rs.getString("name"));
				user.setPassWord(rs.getString("passWord"));
				user.setRoleId(rs.getInt("role_id"));
				user.setSex(rs.getString("sex"));
				user.setStuCode(rs.getString("stu_code"));
				user.setTel(rs.getString("tel"));
			
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, rs);
		}
			
		return null;

	}

	@Override
	public String findmanagerStuCodeMax() {
		// TODO Auto-generated method stub
//		1.获取链接(数据库地址 用户名 密码)
		Connection connection  = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		try {
//			2.准备SQL语句
//			IFNULL(参数1,参数2)函数,第一个有则返回第一个,第一个没有则返回第二个
			String sql = "select IFNULL(MAX(stu_code),20221023)+1 FROM tb_user WHERE role_id =1";
//			3.获取车车
			preparedStatement = connection.prepareStatement(sql);
//			4.执行SQL,
			rs = preparedStatement.executeQuery();
			
			rs.next();
			return rs.getString(1);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, rs);
		}
		return null;
	}

	@Override
	public Integer saveManager(User user) {
		// TODO Auto-generated method stub
//		1.获取链接(数据库地址 用户名 密码)
		Connection connection  = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		try {
//			2.准备SQL语句
//			IFNULL(参数1,参数2)函数,第一个有则返回第一个,第一个没有则返回第二个
			String sql = "INSERT INTO tb_user(NAME,PASSWORD,stu_code,sex,tel,role_id,create_user_id,disabled) VALUE(?,?,?,?,?,?,?,?)";
//			3.获取车车,Statement.RETURM_GENERATED_KEYS指定返回生成的主键
			preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			 
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getPassWord());
			preparedStatement.setString(3, user.getStuCode());
			preparedStatement.setString(4, user.getSex());
			preparedStatement.setString(5, user.getTel());
			preparedStatement.setInt(6,user.getRoleId());
			preparedStatement.setInt(7,user.getCreateUserId());
			preparedStatement.setInt(8,user.getDisabled());
			
//			4.执行SQL,
			preparedStatement.executeUpdate();
			
			rs = preparedStatement.getGeneratedKeys();
			rs.next();
			return rs.getInt(1);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, rs);
		}
		
		return null;
	}

	@Override
	public List<User> findManager(String sql) {
//		1.获取链接(数据库地址 用户名 密码)
		Connection connection  = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		try {
//			3.获取车车,Statement.RETURM_GENERATED_KEYS指定返回生成的主键
			preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			
//			4.执行SQL,
			rs = preparedStatement.executeQuery();  
			List<User> users = new ArrayList<User>();
			while(rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setCreateUserId(rs.getInt("create_user_id"));
				user.setDisabled(rs.getInt("disabled"));
 				user.setName(rs.getString("name"));
				user.setPassWord(rs.getString("passWord"));
				user.setRoleId(rs.getInt("role_id"));
				user.setSex(rs.getString("sex"));
				user.setStuCode(rs.getString("stu_code"));
				user.setTel(rs.getString("tel"));
				
				users.add(user);
			}
			
			return users;
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, rs);
		}
		
		return null;	
	}
	@Override
	public User findById(int id) {
		// TODO Auto-generated method stub
//		获取链接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
//			准备SQL语句
			String sql = "select * from tb_user where id = ?"; 
//			准备车车
			preparedStatement = connection.prepareStatement(sql);
//			
			preparedStatement.setInt(1, id);
			
//			执行SQL语句,查询的结果封装在ResultSet
			rs = preparedStatement.executeQuery();
			while(rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setCreateUserId(rs.getInt("create_user_id"));
				user.setDisabled(rs.getInt("disabled"));
				user.setName(rs.getString("name"));
				user.setPassWord(rs.getString("passWord"));
				user.setRoleId(rs.getInt("role_id"));
				user.setSex(rs.getString("sex"));
				user.setStuCode(rs.getString("stu_code"));
				user.setTel(rs.getString("tel"));
			
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, rs);
		}
		
		return null;
	}
}















