package com.bskcare.ch.dao.tg;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.tg.TgEvaluated;

public interface TgEvaluatedDao extends BaseDao<TgEvaluated>{
	
	public TgEvaluated queryTgEvaluated(Long mobile);
}
