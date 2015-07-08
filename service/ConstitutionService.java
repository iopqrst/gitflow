package com.bskcare.ch.service;

import java.util.List;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.CmcAftercare;
import com.bskcare.ch.vo.CmcAnalysis;
import com.bskcare.ch.vo.Constitution;

@SuppressWarnings("unchecked")
public interface ConstitutionService {
	/**
	 * 添加中医体质辨识表
	 * 
	 * @param constitution
	 */
	public Constitution addConstitution(int cid, String param, String answer);

	/**
	 * 根据用户id查询所有的体质建检测信息
	 */
	public String selectConstitution(int clientId, QueryInfo queryInfo);

	/**
	 * 根据用户id和检测的id查看问卷详细信息
	 */
	public Constitution selectConstitutionById(int clientId, int id);

	/**
	 * 根据用户id和检测id来查看检测结果信息
	 * 
	 * @param clientId
	 * @param id
	 * @return
	 */
	public Constitution selectConstitutionByIds(int clientId, int id);

	/**
	 * 根据体质名称 查询体质分析信息
	 */
	public CmcAnalysis selectCmcAnalysisByName(String name);

	/**
	 * 根据体质名称 查询体质调养方案信息
	 */
	public CmcAftercare selectAfterCareByName(String name);

	public PageObject selectConstitutionBycId(Integer clientId,
			QueryInfo queryInfo);

	public List<Constitution> findCmcBycId(Integer clientId);

}
