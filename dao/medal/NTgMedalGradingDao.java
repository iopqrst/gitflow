package com.bskcare.ch.dao.medal;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.medal.NTgMedalGrading;

public interface NTgMedalGradingDao extends BaseDao<NTgMedalGrading>{
	
	/**
	 * 根据勋章类型查询等级
	 */
	public List<NTgMedalGrading> queryMedalGrading(Integer medalId);
	
	public NTgMedalGrading queryMedalGrading(Integer medalId, Integer level);
}
