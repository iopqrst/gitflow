package com.bskcare.ch.dao.online;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.online.OnlineSatisfaction;

public interface OnlineSatisfactionDao extends BaseDao<OnlineSatisfaction>{

	public Object queryScore(Integer userId);
	public Object queryScore(Integer expertId, Integer score);
	
}
