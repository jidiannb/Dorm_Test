package org.wdl.dormTest.service;

import java.util.List;

import org.wdl.dormTest.bean.DormBuild;

public interface DormBuildService {

	public DormBuild findByName(String name);

	public void save(DormBuild build);

	public List<DormBuild> find();

	DormBuild findById(Integer id);

	public List<DormBuild> findByUserId(Integer id);


}
