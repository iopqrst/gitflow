package com.bskcare.ch.dao.score;

import com.bskcare.ch.base.dao.BaseDao;

import com.bskcare.ch.vo.score.SignIn;

@SuppressWarnings("unchecked")
public interface SignInDao extends BaseDao<SignIn> {

	/**
	 * 查询最后一次签到记录
	 * @param clientId
	 * @return
	 */
	public SignIn queryLatestRecord(Integer clientId);
	
	public int queryTodayRecord(Integer clientId);
}
