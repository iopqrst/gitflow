package com.bskcare.ch.service;

import java.util.List;

import com.bskcare.ch.vo.CmcAftercare;

public interface CmcAftercareService {
	/**
	 * 根据体质名称查询体质调养的信息
	 * 
	 * @param 体质名称
	 * @return
	 */
	public CmcAftercare selectAfterCareById(int id);

	/**
	 * 根据id修改中医体质调养信息
	 */
	public void updateCmcAftercare(CmcAftercare cmcAftercare);

	public List<CmcAftercare> selectCmcAftercare();

}
