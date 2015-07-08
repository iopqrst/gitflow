package com.bskcare.ch.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.PhysicalSuggestionDao;
import com.bskcare.ch.vo.client.PhysicalSuggestion;

@Repository
public class PhysicalSuggestionDaoImpl extends BaseDaoImpl<PhysicalSuggestion>
		implements PhysicalSuggestionDao {

	public PhysicalSuggestion findSuggestionById(Integer clientId, Integer pdId) {
		String hql = "from PhysicalSuggestion where pdId=? and clientId=?";
		Object[] obj = { pdId, clientId };
		List<PhysicalSuggestion> lst = executeFind(hql, obj);
		if (!CollectionUtils.isEmpty(lst))  {
			return lst.get(0);
		} else {
			return null;
		}
	}

	public List<PhysicalSuggestion> findSuggestionBycId(Integer clientId) {
		if (null != clientId) {
			String hql = "from PhysicalSuggestion where clientId=?";
			List<PhysicalSuggestion> lstSuggestion = executeFind(hql, clientId);
			if (!CollectionUtils.isEmpty(lstSuggestion))  {
				return lstSuggestion;
			}
		}
		return null;
	}

}
