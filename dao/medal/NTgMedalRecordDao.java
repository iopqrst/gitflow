package com.bskcare.ch.dao.medal;

import java.util.Date;
import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.bo.NTgMedalRecordExtend;
import com.bskcare.ch.vo.medal.NTgMedalRecord;
import com.bskcare.ch.vo.medal.NTgMedalRule;

public interface NTgMedalRecordDao extends BaseDao<NTgMedalRecord>{
	
	public int queryScoresByMedal(NTgMedalRule medal, Integer clientId, Date beginDate, Date endDate);

	public List<NTgMedalRecord> queryClientMedalRecord(Integer clientId, Integer medalId);
	
	public List<NTgMedalRecordExtend> queryClientMedalScore(Integer clientId, Integer medalId);
}
