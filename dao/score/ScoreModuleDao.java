package com.bskcare.ch.dao.score;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;

import com.bskcare.ch.vo.score.ScoreModule;

public interface ScoreModuleDao extends BaseDao<ScoreModule> {
	
	public List<ScoreModule> queryScoreModule();
}
