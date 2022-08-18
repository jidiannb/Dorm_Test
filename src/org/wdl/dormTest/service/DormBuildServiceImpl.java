package org.wdl.dormTest.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.wdl.dormTest.bean.DormBuild;
import org.wdl.dormTest.bean.User;
import org.wdl.dormTest.dao.DormBuildDao;
import org.wdl.dormTest.dao.DormBuildDaoImpl;
import org.wdl.dormTest.util.ConnectionFactory;

public class DormBuildServiceImpl implements DormBuildService{

	private DormBuildDao dormBuildDao = new DormBuildDaoImpl();
	@Override
	public DormBuild findByName(String name) {
		return dormBuildDao.findByName(name);
	}

	@Override
	public void save(DormBuild build) {
		dormBuildDao.save(build);
	}

	@Override
	public List<DormBuild> find() {
		return dormBuildDao.find();
	}

	@Override
	public DormBuild findById(Integer id) {
		// TODO Auto-generated method stub
		return dormBuildDao.findById(id);
	}

	@Override
	public List<DormBuild> findByUserId(Integer id) {
		// TODO Auto-generated method stub
		return dormBuildDao.findByUserId(id);
	}
}



































