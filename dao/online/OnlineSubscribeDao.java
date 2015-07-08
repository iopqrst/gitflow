package com.bskcare.ch.dao.online;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.online.OnlineSubscribe;

@SuppressWarnings("unchecked")
public interface OnlineSubscribeDao extends BaseDao<OnlineSubscribe> {

	PageObject<Object> querySubscribe(OnlineSubscribe osub, QueryInfo queryInfo);

	Object querySubCount(Integer expertId);

	/**
	 * 根据后台登录的管理员id查询用户预约信息
	 */
	public PageObject querySubscribeByUserId(OnlineSubscribe osub,
			QueryCondition query, QueryInfo queryInfo);
	
	public int querySubscribeByUserId(OnlineSubscribe osub, QueryCondition query);

}
