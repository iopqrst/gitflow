package com.bskcare.ch.service;

import net.sf.json.JSONObject;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.HealthLevel;

public interface HealthLevelService {
	/**
	 * 保存健康等级，添加或修改
	 * @param healthLevel
	 */
	void saveOrUpdateHL(HealthLevel healthLevel);
	/**
	 * 获得等级列表
	 * @param queryInfo
	 * @return
	 */
	PageObject<HealthLevel> getHealhtLevelList(QueryInfo queryInfo,HealthLevel level);
	/**
	 * 获得健康信息
	 * @param healthLevelId
	 * @return
	 */
	HealthLevel load(int healthLevelId);
	/**
	 * 删除健康等级
	 */
	void delHL(int healthLevelId);
	/**
	 * 根据用户得分，获得健康等级信息
	 */
	HealthLevel getHealthLevelByHealthIndex(Integer HealthIndex);
	
	/**
	 * 根据用户提供的信息，获得健康评估
	 */
	public JSONObject getHealthEvaluate(ClientInfo clientInfo);
}
