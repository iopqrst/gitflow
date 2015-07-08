package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.bo.CmcTaskExtend;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.CmcTask;

@SuppressWarnings("unchecked")
public interface CmcTaskDao extends BaseDao<CmcTask>{
	
	/**
	 * 查询某个区域管理员所在区域所有的用户中医体质任务信息
	 */
	public PageObject findCmcTaskExtend(CmcTaskExtend taskExtend, QueryInfo queryInfo,String areaChain);
	
	/**
	 * 处理中医体质任务
	 */
	public void dealTask(Integer id);
		
	/**
	 * 根据id查询某个中医体质辨识建议信息
	 */
	public CmcTask findCmcTaskById(Integer id);
	
	
	/**
	 * 根据用户id和中医体质问卷报告id查询某个中医体质辨识建议信息
	 */
	public CmcTask findCmcTaskByIds(Integer clientId,Integer ceId);
	
	public int findCmcTaskExtend(CmcTaskExtend taskExtend,String areaChain);
	
}
