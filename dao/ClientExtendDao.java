package com.bskcare.ch.dao;

import java.util.Date;
import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.bo.ClientExtendExtend;
import com.bskcare.ch.bo.ClientRegEval;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ClientExtend;
import com.bskcare.ch.vo.ClientInfo;

@SuppressWarnings("unchecked")
public interface ClientExtendDao extends BaseDao<ClientExtend> {
	/** 根据clientId查询是否有客户上传数据的信息 **/
	public ClientExtend queryLastTimeByClientId(ClientExtend cx);

	/** 根据clientId修改指定字段的信息 **/
	public void updateLastTime(Integer clientId, String type);

	/** 注册来源统计 **/
	public List queryStatisticList(String timeType);
	
	public List queryRegSource(ClientInfo clientInfo, QueryCondition queryCondition, String areaChain);
	/**
	 * 查询用户邀请码是否存在
	 * @param initiativeinvite
	 * @return
	 */
	public int queryInviteCount(String initiativeinvite);
	/**
	 * 查询用户邀请码是否存在
	 * @param initiativeinvite
	 * @return
	 */
	public void updateInviteCount(Integer cid , String initiativeinvite);
	
	/**
	 * 修改总分值或金币数
	 * @param score 分值 
	 * @param category 参见ScoreConstant 
	 * @param clientId
	 * @return
	 */
	public int updateScoreAndCoins(int score, int category, Integer clientId);

	/**
	 * 标记用户状态
	 * @param id
	 * @param i
	 * @return
	 */
	public int markClientFlag(Integer clientId, int flag);
	
	public int updateScoreInfo(ClientExtend clientExtend);
	
	public List<ClientRegEval> queryUploadMonitoringBack(Integer cid, Date beginDate, Date endDate,
			Date beginTime, Date endTime);
	
	/**
	 * 
	 * @param balance 当前收入、支出金额
	 * @param clientId
	 * @param type  0：收入 1：支出
	 * @return
	 */
	public int updateClientBalance(double balance, Integer clientId, int type);
	
	public List<ClientExtend> queryClientExtend();
	
	public PageObject<ClientExtendExtend> queryClientExtend(QueryCondition qc, QueryInfo queryInfo);
	
	public void updateClientExtend(int type);

}
