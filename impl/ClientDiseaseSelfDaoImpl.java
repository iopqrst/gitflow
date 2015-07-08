package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.ClientDiseaseSelfDao;
import com.bskcare.ch.vo.client.ClientDiseaseSelf;


@Repository
@SuppressWarnings("unchecked")
public class ClientDiseaseSelfDaoImpl extends BaseDaoImpl<ClientDiseaseSelf> implements ClientDiseaseSelfDao{
	
	public List<Object> queryDiseaseSelf(Integer clientId){
		String sql = "select {m.*},n.name name from ((select * from t_client_disease_self where " +
				"clientId=?) m left join rpt_disease n on m.disease = n.id)";
		List args = new ArrayList();
		args.add(clientId);
		Map entities = new LinkedHashMap();
		entities.put("m", ClientDiseaseSelf.class);
		Map scalars =  new LinkedHashMap();
		scalars.put("name", StandardBasicTypes.STRING);
		
		return executeNativeQuery(sql, entities, scalars, args.toArray(), null);
	}

	public void saveDiseaseSelf(ClientDiseaseSelf clientDiseaseSelf) {
		this.add(clientDiseaseSelf);
	}

	public void updateDiseaseSelf(ClientDiseaseSelf clientDiseaseSelf) {
		this.update(clientDiseaseSelf);
	}

	public List<ClientDiseaseSelf> queryDiseaseSelfByClientId(Integer clientId) {
		String hql="from ClientDiseaseSelf where clientId = ?";
		return this.executeFind(hql, clientId);
	}
	

	public void deleteDiseaseByCid(Integer cid) {
		String sql="delete from  t_client_disease_self where clientId = ?";
		this.deleteBySql(sql, cid);
	}
	
	public ClientDiseaseSelf queryDiseaseSelf(Integer clientId,String disease){
		String hql = "from ClientDiseaseSelf where clientId = ? and disease = ?";
		List args = new ArrayList();
		args.add(clientId);
		args.add(disease);
		List<ClientDiseaseSelf> lst = executeFind(hql, args.toArray());
		ClientDiseaseSelf diseaseSelf = null;
		if(!CollectionUtils.isEmpty(lst)){
			diseaseSelf = lst.get(0);
		}
		return diseaseSelf;
	}
	
	public void updateDiseaseSelf(String newDisease,String disease,Integer clientId){
		String sql = "update t_client_disease_self set disease = ? where clientId = ? and disease = ?";
		List args = new ArrayList();
		args.add(newDisease);
		args.add(clientId);
		args.add(disease);
		updateBySql(sql, args.toArray());
	}
	
	public void deleteDiseaseSelf(Integer clientId,String disease){
		String sql = "delete from t_client_disease_self where clientId = ? and disease = ?";
		List args = new ArrayList();
		args.add(clientId);
		args.add(disease);
		deleteBySql(sql, args.toArray());
	}

	public ClientDiseaseSelf quertDiseaseSelfOther(Integer cid) {
		String hql = "from ClientDiseaseSelf where clientId = ? and type = ?";
		List args = new ArrayList();
		args.add(cid);
		args.add(ClientDiseaseSelf.DISEASE_NO);
		List<ClientDiseaseSelf> lst = executeFind(hql, args.toArray());
		ClientDiseaseSelf diseaseSelf = null;
		if(!CollectionUtils.isEmpty(lst)){
			diseaseSelf = lst.get(0);
		}
		return diseaseSelf;
	}

	public List queryInitDiseaseSelfByClientId(Integer clientId) {
		String sql="SELECT c1.id as id , c1.clientId as clientId , c1.disease as disease , c1.diagTime as diagTime , c1.type as type , d1.spell as pell from t_client_disease_self c1  LEFT JOIN rpt_disease d1 on c1.disease = d1.id where  c1.clientId = ?";
		return this.executeNativeQuery(sql, clientId);
	}

	public void deleteOtherDiseaseByCid(Integer cid) {
		String sql = "delete from t_client_disease_self where clientId = ? and type = ?";
		List args = new ArrayList();
		args.add(cid);
		args.add(ClientDiseaseSelf.DISEASE_NO);
		deleteBySql(sql, args.toArray());
	}
}
