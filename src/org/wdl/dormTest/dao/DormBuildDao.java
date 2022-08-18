package org.wdl.dormTest.dao;

import java.util.List;

import org.wdl.dormTest.bean.DormBuild;

public interface DormBuildDao {
	
	DormBuild findById(Integer id);
	
	public DormBuild findByName(String name);

	public void save(DormBuild build);

	public List<DormBuild> find();

	void saveManagerAndBuild(Integer userId, String[] dormBuildIds);

	List<DormBuild> findByUserId(Integer id);

}
