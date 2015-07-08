package com.bskcare.ch.dao;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.HealthLevel;

public interface HealthLevelDao {
	/**
	 * 添加健康分级
	 * @param healthLevel
	 */
	void saveOrUpdateHL(HealthLevel healthLevel);
	/**
	 * 获得健康分级列表
	 * @param queryInfo
	 * @return
	 */
	PageObject<HealthLevel> getHealhtLevelList(QueryInfo queryInfo,HealthLevel level);
	/**
	 * 获得健康分级
	 * @return
	 */
	HealthLevel getHealthLevel(int id);
	/**
	 * 删除
	 */
	void delHL(int id);
	/**
	 * 根据用户得分，获得健康等级信息
	 */
	HealthLevel getHealthLevelByHealthIndex(Integer HealthIndex);
}
