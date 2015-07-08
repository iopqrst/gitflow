package com.bskcare.ch.dao.tg;

import java.util.Date;
import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.tg.TgRecordFood;

public interface TgRecordFoodDao extends BaseDao<TgRecordFood>{

	/**
	 * 添加饮食记录
	 */
	public TgRecordFood queryRecordFood(TgRecordFood recordFood, Date date);
	
	public List<TgRecordFood> queryClientRecordFood(Integer cid, Date date);
	
	public List<Object> queryClientRecord(Integer clientId, Date startDate, Date endDate);
}
