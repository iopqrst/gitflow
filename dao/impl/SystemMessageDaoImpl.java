package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.bo.SystemMessageExtend;
import com.bskcare.ch.dao.SystemMessageDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.SystemMessage;
@Repository
@SuppressWarnings("unchecked")
public class SystemMessageDaoImpl extends BaseDaoImpl<SystemMessage> implements SystemMessageDao{

	public PageObject<SystemMessage> findMessage(QueryInfo info,
			SystemMessage message) {
		String hql = " from SystemMessage s where 1 = 1 and s.status !="+SystemMessage.MESSAGE_DELETE+" ";
		ArrayList args = new ArrayList();
		if(null != message) {
			if(!StringUtils.isEmpty(message.getTitle())) {
				hql += " and s.title= ? ";
				args.add(message.getTitle());
			}
			if(null != message.getCreateTime()) {
				hql += " and s.createtime= ? ";
				args.add(message.getCreateTime());
			}
			if(null  != message.getType()) {
				hql += " and s.type=? ";
				args.add(message.getType());
			}
			hql += " order by s.smId desc ";
		}
		return this.queryObjects(hql, args.toArray(), info);
	}

	public PageObject<SystemMessageExtend> findMessageByClientId(QueryInfo info,ClientInfo clientInfo,SystemMessageExtend message) {
		StringBuffer sql = new StringBuffer();
		ArrayList args = new ArrayList();
		sql.append(" SELECT t1.*,t2.mrId mrId,t2.status mStatus FROM ");
		sql.append(" ( SELECT * FROM t_system_message s WHERE ");
		sql.append(" s.`status` = "+SystemMessage.MESSAGE_STATUS_FINISH+" and s.type = "+SystemMessage.MESSAGE_TYPE_IN+") t1 ");
		sql.append(" INNER JOIN ");
		sql.append(" ( SELECT * FROM t_message_record ) t2 ");
		sql.append(" ON t1.smId = t2.smId WHERE 1 = 1 ");
		sql.append(" and t2.status != "+SystemMessage.MESSAGE_STATUS_FINISH+" ");
		if(null != clientInfo) {
			if(null != clientInfo.getId()) {
				sql.append(" and t2.clientId = ? ");
				sql.append(" or t2.clientId is NULL ");
				args.add(clientInfo.getId());
			}
		}
		if(null != message) {
			if(!StringUtils.isEmpty(message.getTitle())) {
				sql.append(" and t1.title like ? ");
				args.add("%" + message.getTitle().trim() + "%");
			}
			if(null != message.getCreateTime()) {
				sql.append(" and t1.createTime like ?");
				args.add("%" + DateUtils.format(message.getCreateTime()) + "%");
			}
			if(null != message.getmStatus()) {
				sql.append(" and t2.status = ? ");
				args.add(message.getmStatus());
			}
		}
		return this.queryObjectsBySql(sql.toString(), null, null, args.toArray(), info, SystemMessageExtend.class);
	}

	public Integer findNoReadMessageCount(ClientInfo clientInfo,SystemMessageExtend message) {
		StringBuffer sql = new StringBuffer();
		ArrayList args = new ArrayList();
		sql.append(" SELECT t1.*,t2.mrId mrId,t2.status mStatus FROM ");
		sql.append(" ( SELECT * FROM t_system_message s WHERE ");
		sql.append(" s.`status` = "+SystemMessage.MESSAGE_STATUS_FINISH+" and s.type = "+SystemMessage.MESSAGE_TYPE_IN+") t1 ");
		sql.append(" INNER JOIN ");
		sql.append(" ( SELECT * FROM t_message_record ) t2 ");
		sql.append(" ON t1.smId = t2.smId WHERE 1 = 1 ");
		sql.append(" and t2.status != "+SystemMessage.MESSAGE_STATUS_FINISH+" ");
		if(null != clientInfo) {
			if(null != clientInfo.getId()) {
				sql.append(" and t2.clientId = ? ");
				sql.append(" or t2.clientId is null ");
				args.add(clientInfo.getId());
			}
		}
		if(null != message) {
			if(!StringUtils.isEmpty(message.getTitle())) {
				sql.append(" and t1.title like ? ");
				args.add("%" + message.getTitle().trim() + "%");
			}
			if(null != message.getCreateTime()) {
				sql.append(" and t1.createTime like ?");
				args.add("%" + DateUtils.format(message.getCreateTime()) + "%");
			}
			if(null != message.getmStatus()) {
				sql.append(" and t2.status = ? ");
				args.add(message.getmStatus());
			}
		}
		List<SystemMessageExtend> list = this.executeNativeQuery(sql.toString(), null, null, args.toArray(), SystemMessageExtend.class);
		int i = 0;
		for (SystemMessageExtend s : list) {
			if(s.getmStatus()==0) {
				i++;
			}
		}
		return i;
	}

	public PageObject<SystemMessage> findBulletin(QueryInfo queryInfo) {
		String sql = "select * from t_system_message m where m.type = "+SystemMessage.MESSAGE_TYPE_CONSULT+" ";
		sql += " order by m.createTime desc ";
		return this.queryObjectsBySql(sql, null, null, null, queryInfo, SystemMessage.class);
	} 

}
