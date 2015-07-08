package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.Constitution;

public interface ConstitutionDao extends BaseDao<Constitution> {

	/**
	 * 向中医体质辨识表中添加信息
	 */
	public Constitution addConstitution(Constitution constitution);

	/**
	 * 根据用户id查询所有的体质建检测信息
	 */
	public PageObject selectConstitution(int clientId, QueryInfo queryInfo);

	/**
	 * 根据用户id和检测的id查看问卷详细信息
	 */
	public Constitution selectConstitutionById(int clientId, int id);

	public List<Constitution> findCmcBycId(Integer clientId);
	
	/**
	 * 处理用户提交中医体质辨识
	 */
	public void dealCmc(Integer id);
	
	/**
	 *根据用户id查询用户最后一次测试中医体质辨识的主体质信息 
	 */
	public Object findLatestCmcByClientId(Integer clientId);
	
}
