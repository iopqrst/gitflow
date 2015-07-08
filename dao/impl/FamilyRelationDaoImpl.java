package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.bo.FamilyName;
import com.bskcare.ch.dao.FamilyRelationDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.FamilyRelation;
import com.bskcare.ch.vo.order.OrderProduct;
@Repository
public class FamilyRelationDaoImpl extends BaseDaoImpl<FamilyRelation> implements FamilyRelationDao{

	public PageObject getFamilyList(FamilyRelation familyRelation, QueryInfo queryInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select {tf.*},{tt.*},{tp.*} from t_client_family_relation tf ") ;
		sql.append(" LEFT JOIN  t_clientinfo c on c.id = tf.clientId  ") ;
		sql.append(" LEFT JOIN  t_clientinfo tt on tf.familyId = tt.id  ") ;
		sql.append(" LEFT JOIN  t_order_product tp on tf.familyId = tp.clientId and tp.expiresTime = (SELECT MAX(expiresTime) from t_order_product WHERE clientId = tf.clientId) ") ;
		
		sql.append(" where 1 = 1 ") ;		
		ArrayList args = new ArrayList();
		//通过ClientId
		if(familyRelation.getClientId()!=null){
			sql.append(" and c.id = ? and tt.id is not null ") ;		
			args.add(familyRelation.getClientId()) ;
		}else{
			//查询出全部的人
			sql.append(" and tf.clientId is not null") ;
		}
		//通过具体的  亲情中心id 查找数据
		if(familyRelation.getId()!=null){
			sql.append(" and tf.id = ?  ") ;
			args.add(familyRelation.getId()) ;
		}
		
		Map entities = new LinkedHashMap();
		entities.put("tt", ClientInfo.class);
		entities.put("tf", FamilyRelation.class);
		entities.put("tp", OrderProduct.class);
				
		return  this.queryObjectsBySql(sql.toString(),args.toArray(),entities,queryInfo) ;
	}
	public FamilyRelation addFamilyRelation(FamilyRelation familyRelation) {
		return this.add(familyRelation) ;
	}

	public void updateFamily(FamilyRelation familyRelation) {
		this.update(familyRelation) ;
	}

	public void deleteFamily(FamilyRelation familyRelation) {
		this.delete(familyRelation) ;
	}

	public List<FamilyName> getFamilyByClientInfo(ClientInfo clientInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select cf.name,cf.mobile,cf.id from t_client_family_relation uu " +
				"LEFT JOIN t_clientinfo cf on cf.id = uu.familyId" +
				" where uu.clientId = ? and cf.status = 0 and uu.shortMessage = 1") ;

		ArrayList args = new ArrayList();
		args.add(clientInfo.getId()) ;
		return this.executeNativeQuery(sql.toString(), args.toArray(), FamilyName.class);
		
	}

	public List<FamilyName> getFamilyByfamilyId(ClientInfo clientInfo) {
		if(clientInfo != null && clientInfo.getId() != null){
			StringBuffer sql = new StringBuffer();
			sql.append(" select cf.name,cf.mobile,cf.id from t_client_family_relation uu LEFT JOIN t_clientinfo cf on cf.id = uu.clientId where uu.familyId = ? ") ;
	
			ArrayList args = new ArrayList();
			args.add(clientInfo.getId()) ;
			return this.executeNativeQuery(sql.toString(), args.toArray(), FamilyName.class);
		}
		return null;
	}
	
}
