package com.bskcare.ch.dao.tg;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.tg.TgActivityCard;

public interface TgActivityCardDao extends BaseDao<TgActivityCard>{
	
	public TgActivityCard queryTgActivityCard();
}
