package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.ClientDiseaseFamilyDao;
import com.bskcare.ch.vo.client.ClientDiseaseFamily;

@Repository
@SuppressWarnings("unchecked")
public class ClientDiseaseFamilyDaoImpl extends BaseDaoImpl<ClientDiseaseFamily> implements ClientDiseaseFamilyDao{
	
	public List<Object> queryDiseaseFamily(Integer clientId){
		String sql = "select m.*,n.name name from ((select * from t_client_disease_family where " +
				"clientId = ?) m left join rpt_disease n on m.disease = n.id)";
		List args = new ArrayList();
		args.add(clientId);
		Map entities = new LinkedHashMap();
		entities.put("m", ClientDiseaseFamily.class);
		Map scalars = new LinkedHashMap();
		scalars.put("name", StandardBasicTypes.STRING);
		
		return executeNativeQuery(sql, entities, scalars, args.toArray(),  null);
	}

	public void saveDiseaseFamily(ClientDiseaseFamily clientDiseaseFamily) {
		if(clientDiseaseFamily!=null){
			if(clientDiseaseFamily.getDisease()==null)
				clientDiseaseFamily.setDisease("");
			this.add(clientDiseaseFamily);
		}
	}

	public void updateDiseaseFamily(ClientDiseaseFamily clientDiseaseFamily) {
		if(clientDiseaseFamily!=null&&clientDiseaseFamily.getId()!=null)
			this.update(clientDiseaseFamily);
	}

	public List<ClientDiseaseFamily> queryDiseaseFamilyByClientId(
			Integer clientId) {
		String hql="from ClientDiseaseFamily where clientId = ?";
		return this.executeFind(hql, clientId);
	}
	public List<ClientDiseaseFamily> queryDiseaseFamilyByClientId(
			Integer clientId,Integer FamilyType) {
		List args = new ArrayList();
		String hql="from ClientDiseaseFamily where clientId = ? and familyType= ? ";
		args.add(clientId);
		args.add(FamilyType);
		return this.executeFind(hql, args.toArray());
	}

	public void deleteDiseaseByCid(Integer cid) {
		String sql = "delete from t_client_disease_family where clientId = ? ";
		this.deleteBySql(sql, cid);
	}
	
	public ClientDiseaseFamily queryDiseaseFamily(ClientDiseaseFamily diseaseFamily){
		List args = new ArrayList();
		String hql = "from ClientDiseaseFamily where clientId = ? and disease = ? and familyType = ?";
		args.add(diseaseFamily.getClientId());
		args.add(diseaseFamily.getDisease());
		args.add(diseaseFamily.getFamilyType());
		List<ClientDiseaseFamily> lst = executeFind(hql, args.toArray());
		ClientDiseaseFamily family = null;
		if(!CollectionUtils.isEmpty(lst)){
			family = lst.get(0);
		}
		return family;
	}
	
	
	public void updateDiseaseFamily(String newDisease,ClientDiseaseFamily diseaseFamily){
		String sql = "update t_client_disease_family set disease = ? where clientId = ? and disease = ? and familyType = ?";
		List args = new ArrayList();
		args.add(newDisease);
		args.add(diseaseFamily.getClientId());
		args.add(diseaseFamily.getDisease());
		args.add(diseaseFamily.getFamilyType());
		updateBySql(sql, args.toArray());
	}
	
	
	public void deleteDiseaseFamily(ClientDiseaseFamily diseaseFamily){
		String sql = "delete from t_client_disease_family where clientId = ? and disease = ? and familyType = ?";
		List args = new ArrayList();
		args.add(diseaseFamily.getClientId());
		args.add(diseaseFamily.getDisease());
		args.add(diseaseFamily.getFamilyType());
		deleteBySql(sql, args.toArray());
	}

	public void deleteOtherDisease(Integer cid, Integer familyType,Integer type) {
		String sql = "delete from t_client_disease_family where clientId = ? and familyType = ? and type = ?" ;
		List args = new ArrayList();
		args.add(cid);
		args.add(familyType);
		args.add(type);
		deleteBySql(sql, args.toArray());
	}

	public List queryInitDiseaseFamilyByClientId(Integer clientId) {
		String sql="SELECT c.id , c.clientId , c.familyType , c.disease , c.diagTime , c.diagAge , c.type , d.spell from t_client_disease_family c LEFT JOIN rpt_disease d ON d.id = c.disease where c.clientId = ?";
		return this.executeNativeQuery(sql, clientId);
	}

}