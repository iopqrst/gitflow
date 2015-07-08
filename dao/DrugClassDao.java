package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.vo.search.DiseaseClass;
import com.bskcare.ch.vo.search.DrugClass;

public interface DrugClassDao {
	
	
	public List<DrugClass> queryAll();
	
	
	public List<DrugClass> queryByCbm(String cbm);


	public List<DrugClass> queryByOne(String cbm);

	/**
	 * 根据T查询出二级菜单
	 * @param aaa
	 * @return
	 */
	public List<DrugClass> queryMenuByT(String aaa);
}
