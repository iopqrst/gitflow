package com.bskcare.ch.dao.score;

import java.util.Date;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.bo.ScoreRecordExtend;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.score.ScoreModule;
import com.bskcare.ch.vo.score.ScoreRecord;

@SuppressWarnings("unchecked")
public interface ScoreRecordDao extends BaseDao<ScoreRecord> {

	public int queryScoresByModule(ScoreModule sm, Integer clientId, Date beginDate, Date endDate) ;
	
	/**
	 * 查询客户积分记录信息
	 */
	public PageObject<ScoreRecordExtend> queryClientScoreRecord(Integer cid, int category, QueryInfo queryInfo);
	
	public int queryScoreRecordCount(Integer moduleId, Date beginTime, Date endTime, Integer cid);
	
	public PageObject queryClientScoreRecord(ScoreRecord scoreRecord, QueryInfo queryInfo, QueryCondition qc);
}
