package com.bskcare.ch.dao.impl.online;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.bo.OnlineRecoredExtend;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.online.OnlineRecoredDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.ClientLocation;
import com.bskcare.ch.vo.online.OnlineRecored;

@Repository
@SuppressWarnings("unchecked")
public class OnlineRecoredDaoImpl extends BaseDaoImpl<OnlineRecored> implements
		OnlineRecoredDao {

	public PageObject<OnlineRecored> queryChatRecords(OnlineRecored or, QueryInfo queryInfo,
			QueryCondition qc) {
		String sql = "select * from t_online_records where 1 = 1";
		
		ArrayList args = new ArrayList();
		if(null != or) {
			if(!StringUtils.isEmpty(or.getSender())) {
				sql += " and (sender = ? or receiver = ?)";
				args.add(or.getSender());
				args.add(or.getSender());
			}
			
			if(!StringUtils.isEmpty(or.getReceiver())) {
				sql += " and (sender = ? or receiver = ?)";
				args.add(or.getReceiver());
				args.add(or.getReceiver());
			}
			
			if(!StringUtils.isEmpty(or.getConsultationId())) {
				sql += " and consultationId = ?";
				args.add(or.getConsultationId());
			}
			
			sql += " and soft = ?";
			args.add(or.getSoft());
			
		}
		
		sql += " order by createTime desc";
		
		return queryObjectsBySql(sql, null, null, args.toArray(), queryInfo, OnlineRecored.class);
	}

	public List<OnlineRecoredExtend> queryFiftyChat(String userId, int soft) {
		ArrayList args = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT c.id, c. NAME cName, c.mobile FROM ");
		sql.append("(SELECT DISTINCT (SUBSTRING(sender, 3, LENGTH(sender))) clientId FROM t_online_records WHERE 1 = 1 ");
		if(!StringUtils.isEmpty(userId)) {
			sql.append(" AND receiver=? ");
			args.add(userId);
		}
		sql.append(" AND soft = ?");
		args.add(soft);
		sql.append(" ORDER BY createTime DESC ) oe LEFT JOIN ");
		sql.append(" t_clientinfo c ON ");
		sql.append(" oe.clientId = c.id LIMIT 50 ");
		return executeNativeQuery(sql.toString(), null, null, args.toArray(), OnlineRecoredExtend.class);
	}
	
	
	public PageObject dongDongQueryFiftyChat(String clientId,QueryInfo queryInfo) {
		ArrayList args = new ArrayList();
		
		String asql = "SELECT c.id, c. NAME, c.mobile,c.gender FROM (SELECT DISTINCT (SUBSTRING(sender, 3, LENGTH(sender))) " +
				"clientId FROM t_online_records WHERE type = ? and soft = ?";
		
		args.add(OnlineRecored.TYPE_CLIENT_TO_CLIENT);
		args.add(Constant.SOFT_YUN_PLANT); //东东更健康在软件查询值查询自己的信息，不查询其他软件信息
		if(!StringUtils.isEmpty(clientId)) {
			asql +=" AND receiver=? ";
			args.add(clientId);
		}
		
		asql += " ORDER BY createTime DESC ) oe LEFT JOIN t_clientinfo c ON  oe.clientId = c.id LIMIT 50 ";
		
		String csql = "select clientId,location,longitude,latitude,id,complain,liveness,goodReputation from t_client_location";
		
		String sql = "select {n.*},m.name,m.mobile,m.gender from ("+asql+") m join ("+csql+") n on m.id = n.clientId";
		
		Map entities = new LinkedHashMap();
		entities.put("n", ClientLocation.class);
		
		Map scalars = new LinkedHashMap();
		scalars.put("name", StandardBasicTypes.STRING);
		scalars.put("gender", StandardBasicTypes.INTEGER);
		scalars.put("mobile", StandardBasicTypes.STRING);
		
		return queryObjectsBySql(sql, entities, scalars, args.toArray(), queryInfo, null);
	}

	
	
	public PageObject<OnlineRecored> dongDongQueryChatMsg(OnlineRecored online,QueryInfo queryInfo,
			QueryCondition qc){
		String hql = "from OnlineRecored where type = ?";
		List args = new ArrayList();
		args.add(OnlineRecored.TYPE_CLIENT_TO_CLIENT);
		if(!StringUtils.isEmpty(online.getSender())&&!StringUtils.isEmpty(online.getReceiver())){
			hql += "or (sender = ? and receiver = ?)";
			args.add(online.getSender());
			args.add(online.getReceiver());
			
			hql += " or (sender = ? and receiver = ?)";
			args.add(online.getReceiver());
			args.add(online.getSender());
			
			hql += " and soft = ?";
			args.add(online.getSoft());
		}
		
		hql += " order by createTime desc";
		
		return queryObjectsBySql(hql, null, null, args.toArray(), queryInfo, OnlineRecored.class);
	}

	public List<OnlineRecored> queryOnlineRecored(OnlineRecored online) {
		
		String select = "from OnlineRecored ";
		
		List args = new ArrayList();
		String where =" where 1=1 ";
		if(online != null){
			if(online.getType() != -1){
				where +=" and type = ? ";
				args.add(online.getType());
			}
			if(online.getReceiver()!=null){
				where +=" and receiver = ?";
				args.add(online.getReceiver());
			}
			if(online.getStatus() != -1){
				where +=" and status = ? ";
				args.add(online.getStatus());
			}
		}
		where += " and soft != 4"; //因为就医160的cid 和 uid 采用字符串形式
		List<OnlineRecored> list = executeFind(select+where, args.toArray());
		return list;
	}

	public int queryOnlineRecoredCount(OnlineRecored online) {
		
		String sql = "select count(id) from t_online_records where 1=1";
		List args = new ArrayList();
		if(online != null){
			if(online.getType() != -1){
				sql +=" and type = ? ";
				args.add(online.getType());
			}
			if(online.getReceiver()!=null){
				sql +=" and receiver = ?";
				args.add(online.getReceiver());
			}
			if(online.getStatus() != -1){
				sql +=" and status = ? ";
				args.add(online.getStatus());
			}
		}
		List list = executeNativeQuery(sql, args.toArray());
		if(!CollectionUtils.isEmpty(list)){
			return Integer.parseInt(list.get(0)+"");
		}
		return 0;
	}
	public void updateOnlineRecored(OnlineRecored online) {
		String sql = "update t_online_records set status = " + OnlineRecored.STATUS_READED;
		List args = new ArrayList();
		sql+=" where 1=1 ";
		if(online != null){
			if(online.getType() != -1){
				sql +=" and type = ? ";
				args.add(online.getType());
			}
			if(online.getReceiver()!=null){
				sql +=" and receiver = ?";
				args.add(online.getReceiver());
			}
			if(online.getStatus() != -1){
				sql +=" and status = ? ";
				args.add(online.getStatus());
			}
		}
		updateBySql(sql, args.toArray());
	}
	
}
