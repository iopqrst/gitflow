package com.bskcare.ch.dao.impl;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.MessageRecordDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.MessageRecord;

@Repository
@SuppressWarnings("unchecked")
public class MessageRecordDaoImpl extends BaseDaoImpl<MessageRecord> implements
		MessageRecordDao {

	public PageObject<MessageRecord> findMessageRecord(QueryInfo queryInfo,
			MessageRecord messageRecord) {
		String hql = " from MessageRecord m where 1 = 1 ";
		ArrayList args = new ArrayList();
		if (null != messageRecord) {
			if (null != messageRecord.getMrId()) {
				hql += " and m.mId = ? ";
				args.add(messageRecord.getMrId());
			}
			if (null != messageRecord.getClientId()) {
				hql += " and m.clientId = ? ";
				args.add(messageRecord.getClientId());
			}
			if (null != messageRecord.getStatus()) {
				hql += " and m.status = ? ";
				args.add(messageRecord.getStatus());
			}
			hql += " order by m.mrId desc ";
		}
		return this.queryObjects(hql, args.toArray(), queryInfo);
	}

}
