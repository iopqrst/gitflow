package com.bskcare.ch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.MessageRecordDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.MessageRecordService;
import com.bskcare.ch.vo.MessageRecord;
@Service
public class MessageRecordServiceImpl implements MessageRecordService{
	
	@Autowired
	private MessageRecordDao messageRecordDao;

	public MessageRecord save(MessageRecord messageRecord) {
		return messageRecordDao.add(messageRecord);
	}

	public PageObject<MessageRecord> findMessageRecord(QueryInfo queryInfo,
			MessageRecord messageRecord) {
		return messageRecordDao.findMessageRecord(queryInfo,messageRecord);
	}

	public void updateMessageRecord(MessageRecord messageRecord) {
		messageRecordDao.update(messageRecord);
	}

	public MessageRecord findMessageRecordById(MessageRecord messageRecord) {
		return messageRecordDao.load(messageRecord.getMrId());
	}

}
