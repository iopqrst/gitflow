package com.bskcare.ch.service;

import java.util.List;

import com.bskcare.ch.vo.CmcAnalysis;

public interface CmcAnalysisService {

	/**
	 * 根据体质名称查询体质分析
	 */
	public CmcAnalysis selectCmcAnalysisById(int id);

	/**
	 * 修改中医体质分析
	 */
	public void updateCmcAnalysis(CmcAnalysis cmcAnalysis);

	public List<CmcAnalysis> selectCmcAftercares();

}
