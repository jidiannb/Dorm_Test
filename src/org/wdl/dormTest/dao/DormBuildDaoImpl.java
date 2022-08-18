package org.wdl.dormTest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.wdl.dormTest.bean.DormBuild;
import org.wdl.dormTest.util.ConnectionFactory;

public class DormBuildDaoImpl implements DormBuildDao {
	public DormBuild findByName(String name) {
//		获取链接(数据库地址 用户名 密码)
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
//			准备sql语句
			String sql = "select * from tb_dormBuild where name = ?";
//			获取集装箱或者说是车
			preparedStatement = connection.prepareStatement(sql);
//			索引从1开始
			preparedStatement.setString(1, name);
//			执行sql语句,获取封装后的结果,将结果封装在ResultSet
			rs = preparedStatement.executeQuery();
			
//			查询结果包括表头信息,所以指针要向下移,看是否有下一个数据
//			如果有数据,就进入循环体,封装该数据
			while(rs.next()) {
				DormBuild build	= new DormBuild();
				build.setId(rs.getInt("id"));
				build.setDisabled(rs.getInt("disabled"));
				build.setName(rs.getString("name"));
				build.setRemark(rs.getString("remark"));
				
				return build; 
			}
			
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, rs);
		}
		return null;
	}
	
	@Override
	public void save(DormBuild build) {
//		这是一个增加语句的运用
//		1.获取链接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		try {
//		2.准备SQL语句
		String sql = "INSERT INTO tb_dormbuild(NAME,remark,disabled) VALUES(?,?,?)";
//		3.获取集装箱或者说是车
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, build.getName());
		preparedStatement.setString(2,build.getRemark());
		preparedStatement.setInt(3, build.getDisabled());
		
//		4.执行SQL,更新
		preparedStatement.executeUpdate();
		
		}catch (SQLException e) {
		e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, null);
		}
	}

	/**
	 *
	 */
	@Override
	public List<DormBuild> find() {
		// TODO Auto-generated method stub
//		1.获取链接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;		
		ResultSet rs = null;
		try {
//			2.准备SQL语句
			String sql = "select * from tb_dormbuild";
			
//			3.准备集装箱或者说是车🚗
			preparedStatement = connection.prepareStatement(sql);
			
//			4.执行sql语句,获取执行后的结果并封装在ResultSet
			 rs = preparedStatement.executeQuery();
			List<DormBuild> builds = new ArrayList<DormBuild>();
			while(rs.next()) {
				DormBuild build = new DormBuild();
				build.setId(rs.getInt("id"));
				build.setName(rs.getString("name"));
				build.setRemark(rs.getString("remark"));
				build.setDisabled(rs.getInt("disabled"));
				
				builds.add(build);
			}
			System.out.println("66");
			return builds;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, rs);
		}
		return null;
	}

	@Override
	public DormBuild findById(Integer id) {
//		1.获取链接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
//			2.准备Sql语句
			String sql = "select * from tb_dormbuild where id = ? and disabled = 0";
//			3.获取集装箱或者说是车
			preparedStatement = connection.prepareStatement(sql);
//			4.索引从1开始
			preparedStatement.setInt(1, id);
			
//			执行sql
			 rs =  preparedStatement.executeQuery();
			
			while(rs.next()) {
				DormBuild build = new DormBuild();
				build.setDisabled(rs.getInt("disabled"));
				build.setId(rs.getInt("id"));
				build.setName(rs.getString("name"));
				build.setRemark(rs.getString("remark"));
				
				return build;
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, rs);
		}
		return null;
	}

	@Override
	public void saveManagerAndBuild(Integer userId, String[] dormBuildIds) {
		// TODO Auto-generated method stub
//		1.获取链接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		try {
//			2.准备Sql语句
			String sql = "INSERT INTO tb_manage_dormbuild(USER_ID,DormBuild_id) VAlue(?,?)";
//			3.获取集装箱或者说是车
			preparedStatement = connection.prepareStatement(sql);
			for(String dormBuildId : dormBuildIds) {
		 		preparedStatement.setInt(1, userId);
				preparedStatement.setInt(2, Integer.parseInt(dormBuildId));
				
//				将sql语句添加到批处理
				preparedStatement.addBatch();
			}
//			执行批处理
			preparedStatement.executeBatch();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, null);
		}
	}

	@Override
	public List<DormBuild> findByUserId(Integer id) {
		// TODO Auto-generated method stub
//		获取链接(数据库地址 用户名 密码)
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
//			准备sql语句
			String sql = "SELECT * FROM tb_manage_dormbuild" + 
					" LEFT JOIN tb_dormbuild ON tb_dormbuild.`id` = tb_manage_dormbuild.`dormBuild_id` " + 
					" WHERE user_id = ? ";
//			获取集装箱或者说是车
			preparedStatement = connection.prepareStatement(sql);
//			索引从1开始
			preparedStatement.setInt(1, id);;
//			执行sql语句,获取封装后的结果,将结果封装在ResultSet
			rs = preparedStatement.executeQuery();
			
//			查询结果包括表头信息,所以指针要向下移,看是否有下一个数据
//			如果有数据,就进入循环体,封装该数据
			List<DormBuild> builds = new ArrayList<>();
			while(rs.next()) {
				DormBuild build	= new DormBuild();
				build.setId(rs.getInt("id"));
				build.setDisabled(rs.getInt("disabled"));
				build.setName(rs.getString("name"));
				build.setRemark(rs.getString("remark"));
				
				builds.add(build);
			}
			return builds; 
			
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, rs);
		}
		return null;
	}

	
}






















