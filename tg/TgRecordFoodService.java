package com.bskcare.ch.service.tg;


import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.tg.TgFoodRember;
import com.bskcare.ch.vo.tg.TgRecordFood;

public interface TgRecordFoodService {

	/**
	 * 添加饮食记录
	 */
	public String addRecordFood(TgRecordFood recordFood, String date, Integer cid);
	
	/**
	 * 查询记饮食信息
	 */
	public String queryRecordFood(Integer clientId, String date);
	
	public String queryClientFoodRember(Integer cid, TgFoodRember foodRember, String date, QueryInfo queryInfo);

}
