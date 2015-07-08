package com.bskcare.ch.dao;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.MessageRecord;

public interface MessageRecordDao extends BaseDao<MessageRecord>{

	/**
	 * 站内信息记录分页
	 * @param queryInfo
	 * @param messageRecord
	 */
	public PageObject<MessageRecord> findMessageRecord(QueryInfo queryInfo,
			MessageRecord messageRecord);
	
}
