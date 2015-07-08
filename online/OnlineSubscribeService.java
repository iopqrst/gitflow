package com.bskcare.ch.service.online;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.online.OnlineSubscribe;

/**
 * 在线预约
 */
@SuppressWarnings("unchecked")
public interface OnlineSubscribeService {

	void add(OnlineSubscribe osub);

	String queryStringOfSubscribe(OnlineSubscribe osub, QueryInfo queryInfo);
	
	PageObject<Object> querySubscribeInfo(OnlineSubscribe osub, QueryInfo queryInfo);
	
	/**
	 * 根据后台登录的管理员id查询用户预约信息
	 */
	public PageObject querySubscribeByUserId(OnlineSubscribe osub,
			QueryCondition query, QueryInfo queryInfo);
	

	/**
	 * 根据id处理用户预约
	 * @return
	 */
	public void dealSubById(OnlineSubscribe sub);
}
