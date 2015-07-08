package com.bskcare.ch.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.CmcAftercareDao;
import com.bskcare.ch.vo.CmcAftercare;

@Repository("cmcAftercareDao")
@SuppressWarnings("unchecked")
public class CmcAftercareDaoImpl extends BaseDaoImpl<CmcAftercare> implements
		CmcAftercareDao {

	public CmcAftercare selectAfterCareById(int id) {
		String hql = "from CmcAftercare where id=?";
		return executeFind(hql, id).get(0);

	}

	public CmcAftercare selectAfterCareByName(String name) {
		String hql = "from CmcAftercare where name=?";
		List<CmcAftercare> lstAftercare = executeFind(hql, name);
		if (!CollectionUtils.isEmpty(lstAftercare)) {
			return lstAftercare.get(0);
		} else {
			return null;
		}

	}

	public List<CmcAftercare> selectCmcAftercare() {
		String hql = "from CmcAftercare";
		return executeFind(hql);
	}

	/**
	 * 根据id修改中医体质调养信息
	 */
	public void updateCmcAftercare(CmcAftercare cmcAftercare) {
		update(cmcAftercare);
	}
}
