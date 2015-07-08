package com.bskcare.ch.dao.tg;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.tg.TgPopularizeActivity;

public interface TgPopularizeActivityDao extends BaseDao<TgPopularizeActivity>{
	
	/**
	 *   根据客户id查询信息
	 */
	public TgPopularizeActivity queryPopularizeByClientId(Integer clientId);
}
