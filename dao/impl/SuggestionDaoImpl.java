package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.bo.SuggestionExtend;
import com.bskcare.ch.dao.SuggestionDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.Suggestion;

@Repository
@SuppressWarnings("unchecked")
public class SuggestionDaoImpl extends BaseDaoImpl<Suggestion> implements
		SuggestionDao {

	public PageObject<Suggestion> queryClientSuggestion(Suggestion su,
			QueryInfo queryInfo) {
		String hql = "from Suggestion where 1=1 ";
		List args = new ArrayList();
		if (null != su) {
			if (!StringUtils.isEmpty(su.getSender())) {
				hql += " and (sender = ? or receiver = ?) ";
				args.add(su.getSender());
				args.add(su.getSender());
			}
			if (-1 != su.getSoft() && StringUtils.isNumber(su.getSoft() + "")) {
				hql += " and soft =? ";
				args.add(su.getSoft());
			}
		}
		hql += " order by createTime desc";
		return queryObjects(hql, args.toArray(), queryInfo);
	}

	public PageObject<SuggestionExtend> queryList(Suggestion su,QueryInfo queryInfo, ClientInfo clientInfo) {
		List args = new ArrayList();
		String sql = "SELECT tci.name clientName, tci.mobile, tsg.* FROM";
		sql += " ( SELECT sg.*, substring(sg.sender, 3) ";
		sql += " AS clientId FROM (select * from t_suggestion ORDER BY createTime desc) sg where 1=1 " ;
		if (null != su) {
			if (-1 != su.getStatus()) {
				sql += " and sg.status=? ";
				args.add(su.getStatus());
			}
			if (-1 != su.getSoft()) {
				sql += " and sg.soft=? ";
				args.add(su.getSoft());
			}
		}
		sql += " and sg.type="+ Suggestion.TYPE_SEND_TO_DOC +" and sg.sender not like 'd_%' ";
		sql += " GROUP BY sg.sender,sg.soft ORDER BY sg.createTime DESC) tsg  ";
		sql += " JOIN (select id,name,mobile from t_clientinfo where 1 = 1";
		
		if(clientInfo != null){
			if (!StringUtils.isEmpty(clientInfo.getName())) {
				sql += " and name like ?";
				args.add("%" + clientInfo.getName().trim() + "%");
			}

			if (!StringUtils.isEmpty(clientInfo.getMobile())) {
				sql += " and mobile = ?";
				args.add(clientInfo.getMobile().trim());
			}
		}
		
		sql += ") tci ON tsg.clientId = tci.id where tsg.clientId is not NULL";
		return queryObjectsBySql(sql, null, null, args.toArray(), queryInfo, SuggestionExtend.class);
	}

	public void updateBysql(Suggestion su) {
		String sql = "update t_suggestion set status=?,receiver=? where sender=? and soft=? and status !="+Suggestion.STATUS_READED;
		Object[] args = {su.getStatus(),su.getReceiver(),su.getSender(),su.getSoft()};
		updateBySql(sql, args);
	}

	public PageObject<SuggestionExtend> queryDetailSuggestList(Suggestion su,
			QueryInfo queryInfo) {
		List args = new ArrayList();
		String sql = "SELECT tbe.name ename,tbe.role role,tsg.* FROM ";
		sql += " ( SELECT *, substring(sender, 3) AS eId FROM t_suggestion where 1=1 ";
		if (null != su) {
			if (!StringUtils.isEmpty(su.getSender())) {
				sql += " and (sender = ? or receiver = ?) ";
				args.add(su.getSender());
				args.add(su.getSender());
			}
			if (-1 != su.getSoft() && StringUtils.isNumber(su.getSoft() + "")) {
				sql += " and soft =? ";
				args.add(su.getSoft());
			}
		}
		sql += " order by createTime desc";
		sql += " ) tsg LEFT JOIN t_bsk_expert tbe ON tsg.eId = tbe.userId";
		Map scalars = new LinkedHashMap();
		scalars.put("ename", StandardBasicTypes.STRING);
		scalars.put("role", StandardBasicTypes.INTEGER);
		scalars.put("eId", StandardBasicTypes.STRING);
		scalars.put("id", StandardBasicTypes.INTEGER);
		scalars.put("suggestion", StandardBasicTypes.STRING);
		scalars.put("sender", StandardBasicTypes.STRING);
		scalars.put("receiver", StandardBasicTypes.STRING);
		scalars.put("status", StandardBasicTypes.INTEGER);
		scalars.put("type", StandardBasicTypes.INTEGER);
		scalars.put("soft", StandardBasicTypes.INTEGER);
		scalars.put("createTime", StandardBasicTypes.TIMESTAMP);

		return queryObjectsBySql(sql, null, scalars, args.toArray(), queryInfo, SuggestionExtend.class);
	}

}
