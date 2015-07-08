package com.bskcare.ch.dao.online;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.bo.OnlineRecoredExtend;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.online.OnlineRecored;

@SuppressWarnings("unchecked")
public interface OnlineRecoredDao extends BaseDao<OnlineRecored>{

	PageObject<OnlineRecored> queryChatRecords(OnlineRecored or, QueryInfo qi, QueryCondition qc);
	
	/**
	 * 查询当前聊天状态中前50个用户
	 */
	public List<OnlineRecoredExtend> queryFiftyChat(String userId, int soft);
	
	/**
	 * 动动查询当前聊天状态中前50个用户
	 */
	public PageObject dongDongQueryFiftyChat(String clientId,QueryInfo queryInfo);
	
	/**
	 * 动动查询用户的聊天记录
	 * @param sender  登陆用户id
	 * @param receiver  聊天用户id
	 * @return
	 */
	public PageObject<OnlineRecored> dongDongQueryChatMsg(OnlineRecored online,QueryInfo queryInfo,
			QueryCondition qc);
	/**
	 * 查询聊天记录
	 * @param recored
	 * @return
	 */
	public List<OnlineRecored> queryOnlineRecored(OnlineRecored recored); 
	/**
	 * 查询未读聊天记录条数
	 * @param recored
	 * @return
	 */
	public int queryOnlineRecoredCount(OnlineRecored recored); 
	/**
	 * 更新聊天记录状态
	 * @param recored
	 * @return
	 */
	public void updateOnlineRecored(OnlineRecored recored); 
}
