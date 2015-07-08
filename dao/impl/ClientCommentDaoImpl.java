package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.ClientCommentDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.ClientComment;

@Repository
@SuppressWarnings("unchecked")
public class ClientCommentDaoImpl extends BaseDaoImpl<ClientComment> implements ClientCommentDao{

	
	public List<ClientComment> queryClinetComment(ClientComment comment){
		String hql = "from ClientComment where 1=1";
		List args = new ArrayList();
		
		if(comment != null){
			if(comment.getClientId() != null){
				hql += " and clientId = ?";
				args.add(comment.getClientId());
			}
			if(comment.getLevel() == ClientComment.LEVEL_SPECIAL){
				hql += " and level = ?";
				args.add(comment.getLevel());
			}
		}
		hql += "order by createTime desc";
		return executeFind(hql, args.toArray());
	}
	
	
	public List queryClinetCommentUserName(Integer clientId,ClientComment comment){
		String sql = "select {t1.*},t2.name userName from t_client_comment t1 left join t_userinfo t2 " +
				"on t1.userId = t2.id where 1=1";
		List args = new ArrayList();
		
		if(comment != null){
			if(comment.getClientId() != null){
				sql += " and clientId = ?";
				args.add(comment.getClientId());
			}
			sql += " and level = ?";
			args.add(comment.getLevel());
			int level = comment.getLevel();
			if(level == 1){
				sql += " order by t1.createTime desc limit 1";
			}else{
				sql += " order by t1.createTime desc limit 20";
			}
			
		}else{
			sql += " and clientId = ?";
			args.add(clientId);
			sql += " order by t1.createTime desc limit 20";
		}

		Map map = new HashedMap();
		map.put("t1", ClientComment.class);
		Map scalar = new HashedMap();
		scalar.put("userName", StandardBasicTypes.STRING);
		
		return executeNativeQuery(sql, map, scalar, args.toArray(), null);
	}
	
	
	public PageObject queryClientCommentUserInfo(ClientComment comment,QueryInfo queryInfo){
		List args = new ArrayList();
		String asql = "select t.id,t.title,t.`comment`,t.createTime,t.`level`,t.userId,t.clientId from t_client_comment t where 1=1";
		if(comment != null){
			if(comment.getClientId() != null){
				asql += " and t.clientId = ?";
				args.add(comment.getClientId());
			}
			if (!StringUtils.isEmpty(comment.getTitle())) {
				asql += " and t.title like ?";
				args.add("%" + comment.getTitle() + "%");
			}
			if (!StringUtils.isEmpty(comment.getComment())) {
				asql += " and t.comment like ?";
				args.add("%" + comment.getComment() + "%");
			}
			if (null != comment.getCreateTime()) {
				asql += " and t.createTime like ?";
				args.add("%" + DateUtils.formatDate(DateUtils.DATE_PATTERN, comment.getCreateTime()) + "%");
			}
			asql += " and t.level = ?";
			args.add(comment.getLevel());
		}
		
		String csql = "select t1.id userId,t1.name userName from t_userinfo t1 ";
		
		String sql = "select {m.*},n.userName from ("+asql+") m left join ("+csql+") n on m.userId = n.userId";
		
		Map entities = new HashedMap();
		entities.put("m", ClientComment.class);
		Map scalars = new HashedMap();
		scalars.put("userName", StandardBasicTypes.STRING);
		
		return queryObjectsBySql(sql, entities, scalars, args.toArray(), queryInfo, null);
	}
}
