package com.bskcare.ch.dao;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.bo.SuggestionExtend;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.Suggestion;

public interface SuggestionDao extends BaseDao<Suggestion> {

	/** 查询客户的意见反馈信息 */
	public PageObject<Suggestion> queryClientSuggestion(Suggestion su,
			QueryInfo queryInfo);
	
	/** 查询客户意见反馈列表 **/
	public PageObject<SuggestionExtend> queryList(Suggestion su,QueryInfo queryInfo, ClientInfo clientInfo);
	
	/** 修改 **/
	public void updateBysql(Suggestion su);
	
	/** 查询客户的意见反馈详细信息 */
	public PageObject<SuggestionExtend> queryDetailSuggestList(Suggestion su,
			QueryInfo queryInfo);
}
