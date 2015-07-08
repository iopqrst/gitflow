package com.bskcare.ch.dao.impl.score;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.score.ScoreModuleDao;
import com.bskcare.ch.vo.score.ScoreModule;

@Repository
public class ScoreModuleDaoImpl extends BaseDaoImpl<ScoreModule> implements
		ScoreModuleDao {
	
	public List<ScoreModule> queryScoreModule(){
		String hql = " from ScoreModule";
		return executeFind(hql);
	}
}
