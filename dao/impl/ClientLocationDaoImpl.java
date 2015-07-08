package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.ClientLocationDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ClientLocation;
import com.google.gson.JsonObject;

@Repository
@SuppressWarnings("unchecked")
public class ClientLocationDaoImpl extends BaseDaoImpl<ClientLocation> implements ClientLocationDao{
	
	public ClientLocation queryLocationByClientId(Integer clientId){
		String hql = "from ClientLocation where clientId = ?";
		List<ClientLocation> lst = executeFind(hql,clientId);
		ClientLocation location = null;
		if(!CollectionUtils.isEmpty(lst)){
			location = lst.get(0);
		}
		return location;
	}
	
	public PageObject queryClientByLonLat(Integer clientId,double minLat,double maxLat,double minLng,double maxLng,QueryInfo queryInfo){
		String asql = "select id,clientId,location,longitude,latitude,liveness,goodReputation,complain from t_client_location " +
				"where (latitude>=? and longitude>=?) and (latitude<=? and longitude<=?) and clientId != ?";
		
		List args = new ArrayList();
		args.add(minLat);
		args.add(maxLat);
		args.add(minLng);
		args.add(maxLng);
		args.add(clientId);
		
//		String csql = "select name,id,gender,mobile,nickName,headPortrait from t_clientinfo";
		
		String sql = "select {m.*},n.name,n.gender,n.mobile,n.nickName,n.headPortrait from ("+asql+") m left join t_clientinfo n on m.clientId = n.id";
		
		Map entities = new LinkedHashMap();
		entities.put("m", ClientLocation.class);
		
		Map scalars = new LinkedHashMap();
		scalars.put("name", StandardBasicTypes.STRING);
		scalars.put("gender", StandardBasicTypes.INTEGER);
		scalars.put("mobile", StandardBasicTypes.STRING);
		scalars.put("nickName", StandardBasicTypes.STRING);
		scalars.put("headPortrait", StandardBasicTypes.STRING);
		
		return queryObjectsBySql(sql, entities, scalars, args.toArray(), queryInfo, null);
	}
	
	
	public int evaluateByClient(Integer clientId,String type){
		ClientLocation location = this.queryLocationByClientId(clientId);
		if(location !=  null){
			String sql = "update t_client_location set "+type+" = ? where clientId = ?";
			List args = new ArrayList();
			if(type.equals("goodReputation")){
				args.add(location.getGoodReputation()+1);
			}
			if(type.equals("complain")){
				args.add(location.getComplain()+1);
			}
			args.add(clientId);
			return updateBySql(sql, args.toArray());
		}else{
			return 0;
		}
	}
	
	public PageObject querySportFriend(Integer cid,QueryInfo queryInfo){
		String csql = "select m.friendId clientId,n.id,n.location,n.longitude,n.latitude,n.liveness,n.goodReputation,n.complain " +
				"from (select * from m_sport_friend where clientId = ?) m left join " +
				"(select * from t_client_location) n on m.friendId = n.clientId";
		List args = new ArrayList();
		args.add(cid);
		String asql = "select * from t_clientinfo";
		
		String sql = "select {m.*},n.name,n.gender,n.mobile,n.nickName,n.headPortrait " +
				"from ("+csql+") m left join ("+asql+") n on m.clientId = n.id";
		
		Map entities = new LinkedHashMap();
		entities.put("m", ClientLocation.class);
		
		Map scalars = new LinkedHashMap();
		scalars.put("name", StandardBasicTypes.STRING);
		scalars.put("gender", StandardBasicTypes.INTEGER);
		scalars.put("mobile", StandardBasicTypes.STRING);
		scalars.put("nickName", StandardBasicTypes.STRING);
		scalars.put("headPortrait", StandardBasicTypes.STRING);
		
		return queryObjectsBySql(sql, entities, scalars, args.toArray(), queryInfo, null);
	}
	
	/**查询附近的跑友的运动排名**/
	public List<Object> querySortLonLog(double minLat,double maxLat,double minLng,double maxLng){
		List args = new ArrayList();
		String sql = "select n.clientId clientId,sum(n.distance) distanceTotal from " +
				" (select * from t_client_location where (latitude>=? and longitude>=?) and (latitude<=? and longitude<=?)) m" +
				" join m_sport n on m.clientId = n.clientId " +
				" group by m.clientId " +
				" order by distanceTotal desc";
		args.add(minLat);
		args.add(maxLat);
		args.add(minLng);
		args.add(maxLng);
				
		Map scalars = new LinkedHashMap();
		scalars.put("clientId", StandardBasicTypes.INTEGER);
		scalars.put("distanceTotal", StandardBasicTypes.STRING);
		
		return executeNativeQuery(sql, null, scalars, args.toArray(), null);
	}
	
}
