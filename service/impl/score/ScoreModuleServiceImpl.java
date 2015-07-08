package com.bskcare.ch.service.impl.score;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.score.ScoreModuleDao;
import com.bskcare.ch.service.score.ScoreModuleService;
import com.bskcare.ch.vo.score.ScoreModule;

@Service
public class ScoreModuleServiceImpl implements ScoreModuleService {
	
	@Autowired
	private ScoreModuleDao scoreModuleDao;
	
	public List<ScoreModule> queryScoreModule(){
		return scoreModuleDao.queryScoreModule();
	}
}
