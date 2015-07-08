package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.MessageFlowDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.MessageFlow;
import com.bskcare.ch.vo.MessagePush;

@Repository
@SuppressWarnings(value = { "rawtypes", "unchecked" })
public class MessageFlowDaoImpl extends BaseDaoImpl<MessageFlow> implements
		MessageFlowDao {

	public PageObject<MessageFlow> queryMessageFlow(QueryInfo queryInfo,
			MessageFlow flow) {
		String hql = "from MessageFlow where 1 = 1";

		ArrayList args = new ArrayList();

		if (null != flow) {
			if (!StringUtils.isEmpty(flow.getTaskTitle())) {
				hql += " and taskTitle like ?";
				args.add("%" + flow.getTaskTitle() + "%");
			}
		}

		hql += " order by id asc";
		return this.queryPagerObjects(hql, args.toArray(), queryInfo);
	}

	public List<MessageFlow> getMessageFlowList() {
		String hql = "from MessageFlow where 1 = 1";
		return this.executeFind(hql);
	}

	public int createMessagePush(String sqldata) {
		return updateBySql(sqldata);
	}

	public int queryUnReadMessages(Integer clientId) {
		String sql = "select count(*) from t_message_push where clientId = ? and status = 0";
		Object obj = this.findUniqueResultByNativeQuery(sql, clientId);

		int count = 0;
		if (obj != null && !obj.equals("")) {
			count = Integer.parseInt(obj.toString());
		}
		return count;
	}

	public List<MessagePush> queryMessages(Integer clientId) {
		String sql = "select * from t_message_push where clientId = ? order by sendTime desc";
		List<MessagePush> list = this.executeNativeQuery(sql,
				new Object[] { clientId }, MessagePush.class);
		return list;
	}

	public int updateMsgStatus(int status, Integer clientId, Integer id) {
		String sql = "update t_message_push set status = ? where clientId = ? and id = ?";
		return this.updateBySql(sql, new Object[] { status ,clientId, id });
	}

	public int updateMsgStatus(int status, Integer clientId) {
		String sql = "update t_message_push set status = ? where clientId = ?";
		return this.updateBySql(sql, new Object[] { status ,clientId});
	}
	
}
