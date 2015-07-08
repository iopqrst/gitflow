package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.ConstitutionDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.Constitution;

@Repository("constitutionDao")
@SuppressWarnings("unchecked")
public class ConstitutionDaoImpl extends BaseDaoImpl<Constitution> implements
		ConstitutionDao {

	public Constitution addConstitution(Constitution constitution) {
		return add(constitution);
	}

	public PageObject selectConstitution(int clientId, QueryInfo queryInfo) {
		String hql = "from Constitution where clientId=? order by id desc";
		ArrayList args = new ArrayList();
		args.add(clientId);
		return queryObjects(hql, args.toArray(), queryInfo);

	}

	/**
	 * 根据id查询检测信息
	 */
	public Constitution selectConstitutionById(int clientId, int id) {
		String hql = "from Constitution where clientId=? and id=?";
		Object[] obj = { clientId, id };
		List<Constitution> lst = executeFind(hql, obj);
		if (!CollectionUtils.isEmpty(lst)) {
			return lst.get(0);
		}else{
			return null;
		}
	}
	
	public List<Constitution> findCmcBycId(Integer clientId){
		String hql = "from Constitution where clientId=? order by id desc";
		return executeFind(hql,clientId);
	}
	
	public void dealCmc(Integer id){
		String hql="update Constitution set status = ? where id = ?";
		Object[] obj = {Constitution.CMCDEAL,id};
		updateByHql(hql, obj);
	}
	
	
	public Object findLatestCmcByClientId(Integer clientId){
		String sql="select mainConstitution from t_cmc_evaluation where clientId = ? order by id DESC limit 1 ";
		return findUniqueResultByNativeQuery(sql, clientId);
	}
}

