package com.bskcare.ch.service;

import java.util.List;

import com.bskcare.ch.bo.ClientExtendExtend;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ClientExtend;
import com.bskcare.ch.vo.ClientInfo;

@SuppressWarnings("unchecked")
public interface ClientExtendService {
	/** 根据clientId查询是否有客户上传数据的信息 **/
	public ClientExtend modifyLastTimeByClientId(Integer clientId);
	
	/**根据clientId修改指定字段的信息**/
	public void updateLastTime(Integer clientId,String type);
	
	/**
	 * 添加用户注册来源信息
	 * @param cid
	 * @param source
	 * @param passivityinvite 被邀请码
	 * @return
	 */
	public ClientExtend addRegSource(Integer cid,String source,String passivityinvite);
	
	/**注册来源统计**/
	public List queryStatisticList(String timeType);
	
	public List queryRegSource(ClientInfo clientInfo, QueryCondition queryCondition, String areaChain);
	
	/**
	 * 标记用户类型
	 * @param clientId
	 * @param flag
	 * @return
	 */
	public String markClientFlag(Integer clientId, int flag) ;
	
	public int updateDetailScore(Integer cid, int type);
	
	public void autoChangeScore();
	
	public PageObject<ClientExtendExtend> queryClientExtend(QueryCondition qc, QueryInfo queryInfo);
}
