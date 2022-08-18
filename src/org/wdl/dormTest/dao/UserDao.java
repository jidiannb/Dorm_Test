package org.wdl.dormTest.dao;

import java.sql.SQLException;
import java.util.List;

import org.wdl.dormTest.bean.User;

public interface UserDao {

	 User findByStuCodeAndPass(String stuCode, String password);

	String findmanagerStuCodeMax();

	Integer saveManager(User user);

	List<User> findManager(String sql);

	User findById(int id);
}
