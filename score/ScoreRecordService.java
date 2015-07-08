package com.bskcare.ch.service.score;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.score.ScoreRecord;

@SuppressWarnings("unchecked")
public interface ScoreRecordService {

	public String addPointsAndCoins(Integer moduleId, Integer clientId);
	
	/**
	 * 添加积分、金币
	 * 
	 * @param moduleId
	 *            具体模块id
	 * @param clientId
	 *            用户id
	 * @param score
	 *            分值
	 * @param category
	 *            （1、金币 2、积分）
	 * @return 添加结果 > 0 执行成功
	 */
	public int saveToDB(Integer moduleId, Integer clientId, int score,
			int category);
	
	
	/**
	 * 同时添加积分、金币
	 * @param moduleId  积分模块id
	 * @param coinIdModuleId  金币模块id
	 * @param cid  客户id
	 */
	public String addScoreCoin(Integer moduleId, Integer coinIdModuleId, Integer cid);
	
	/**
	 * 添加购买服务金币
	 * @param cid 客户id
	 * @param money   服务付款
	 */
	public String addExpenseScore(Integer cid, int money);
	
	/**
	 * 查询客户积分信息
	 */
	public String queryClientScoreRecord(Integer clientId, QueryInfo queryInfo, Integer pagerNo);
	
	public PageObject queryClientScoreRecord(ScoreRecord scoreRecord, QueryInfo queryInfo, QueryCondition qc);
	
}
