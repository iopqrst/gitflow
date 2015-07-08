package com.bskcare.ch.dao.tg;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.tg.TgRecommendCalorie;

public interface TgRecommendCalorieDao extends BaseDao<TgRecommendCalorie>{
	
	/**
	 * 根据用户id和时间查询是否有数据
	 */
	public TgRecommendCalorie queryRecommendCalorie(Integer cid);
	
}
