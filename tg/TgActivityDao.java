package com.bskcare.ch.dao.tg;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.tg.TgActivity;

public interface TgActivityDao extends BaseDao<TgActivity>{
	
	public TgActivity queryTgActivity(TgActivity tgActivity);
	
}
