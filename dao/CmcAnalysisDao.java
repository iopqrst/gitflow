package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.CmcAnalysis;

public interface CmcAnalysisDao extends BaseDao<CmcAnalysis>{
	 
	/**
	 * 根据体质名称查询体质分析信息
	 */
	public CmcAnalysis selectCmcAnalysisById(int id);
	
	public CmcAnalysis selectCmcAnalysisByName(String name);
	
	/**
	 * 修改中医体质分析
	 */
	public void updateCmcAnalysis(CmcAnalysis cmcAnalysis);
	
	public List<CmcAnalysis> selectCmcAnalysis();
	
}
