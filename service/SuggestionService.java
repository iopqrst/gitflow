package com.bskcare.ch.service;

import com.bskcare.ch.bo.SuggestionExtend;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.Suggestion;

public interface SuggestionService {

	/** 上传意见与反馈信息 */
	public Suggestion add(Suggestion suggestion);

	/** 手机端查询返回意见记录 **/
	public String queryClient(Suggestion su, QueryInfo queryInfo);
	
	/** 查询 **/
	public PageObject<SuggestionExtend> queryList(Suggestion su,QueryInfo queryInfo, ClientInfo clientInfo);
	
	/** 根据sender查询 **/
	public String queryListBySender(Suggestion su,QueryInfo queryInfo);
	
	/** 将用户反馈意见修改为已读状态  并新增回复意见**/
	public String replyClient(Suggestion su,Integer manageId);
}
