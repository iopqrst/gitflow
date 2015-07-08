package com.bskcare.ch.service.online;

import java.util.List;

import com.bskcare.ch.bo.OnlineRecoredExtend;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.online.OnlineRecored;

public interface OnlineRecoredService {
	
	/**
	 * 给医生发送信息
	 */
	int sendMsgToDoc(OnlineRecored or);

	/**
	 * 给用户发送消息
	 */
	int sendMsgToClient(OnlineRecored or, String pushTag, String pushTitle, String pushContent);
	
	/**
	 * 向客户端推送信息
	 */
	public int dongDongSendMsgToClient(OnlineRecored or, String tag, String pushTitle, String pushContent);

	/**
	 * 查询聊天记录(我们自己的软件）
	 */
	public String queryChatRecords(OnlineRecored or, QueryInfo qi, QueryCondition qc);
	
	/**
	 * 查询聊天记录(就医160）
	 */
	public String queryChatRecords160(OnlineRecored or, QueryInfo qi, QueryCondition qc);
	
	/**
	 * 查询当前聊天状态中前50个用户
	 */
	public List<OnlineRecoredExtend> queryFiftyChat(String userId, int soft);
	
	/**
	 * 动动查询当前聊天状态中前50个用户
	 */
	public String dongDongQueryFiftyChat(String clientId,QueryInfo queryInfo);
	
	/**
	 * 动动查询用户的聊天记录
	 * @param sender  登陆用户id
	 * @param receiver  聊天用户id
	 * @return
	 */
	public String dongDongQueryChatMsg(OnlineRecored or, QueryInfo qi, QueryCondition qc) ;
	
	/**
	 * 约跑添加好友向客户端推送信息
	 */
	public int sportAddFriend(String tag, String pushTitle, String pushContent);
	/**
	 * 查询聊天记录
	 * @param recored
	 * @return
	 */
	public List<OnlineRecored> queryOnlineRecored(OnlineRecored recored); 
	/**
	 * 查询聊天记录条数
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
