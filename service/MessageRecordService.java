package com.bskcare.ch.service;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.MessageRecord;

public interface MessageRecordService {

	/**
	 * 增加
	 * 
	 * @param messageRecord
	 */
	public MessageRecord save(MessageRecord messageRecord);

	/**
	 * 站内信息记录分页
	 * 
	 * @param queryInfo
	 * @param messageRecord
	 * @return
	 */
	public PageObject<MessageRecord> findMessageRecord(QueryInfo queryInfo,
			MessageRecord messageRecord);

	/**
	 * 修改
	 */
	public void updateMessageRecord(MessageRecord messageRecord);

	/**
	 * 通过Id查找单个对象
	 */
	public MessageRecord findMessageRecordById(MessageRecord messageRecord);

}
