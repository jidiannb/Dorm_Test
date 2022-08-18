package org.wdl.dormTest.service;

import java.sql.SQLException;
import java.util.List;

import org.wdl.dormTest.bean.User;

public interface UserService {

	User findByStuCodeAndPass(String stuCode, String password);

	void saveManager(User user, String[] dormBuildIds);

	List<User> findManager(String searchType, String keyword);

	User findById(int id);


}
