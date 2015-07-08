package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.CmcAftercare;


public interface CmcAftercareDao extends BaseDao<CmcAftercare>{
	/**
	 * **根据体质名称查询体质调养的信息
	 */
	public CmcAftercare selectAfterCareById(int id);
	
	public CmcAftercare selectAfterCareByName(String name);
	
	/**
	 * 根据id修改中医体质调养信息
	 */
	public void updateCmcAftercare(CmcAftercare cmcAftercare);
	
	public List<CmcAftercare> selectCmcAftercare();
	
}
