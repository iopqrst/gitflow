package com.bskcare.ch.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.CmcAnalysisDao;
import com.bskcare.ch.vo.CmcAnalysis;

@Repository("cmcAnalysisDao")
@SuppressWarnings("unchecked")
public class CmcAnalysisDaoImpl extends BaseDaoImpl<CmcAnalysis> implements
		CmcAnalysisDao {

	public CmcAnalysis selectCmcAnalysisById(int id) {
		String hql = "from CmcAnalysis where id=?";
		List<CmcAnalysis> lstAnalysis = executeFind(hql,id);
		if (!CollectionUtils.isEmpty(lstAnalysis)) {
			return executeFind(hql, id).get(0);
		}else{
			return null;
		}
	}

	public List<CmcAnalysis> selectCmcAnalysis() {
		String hql = "from CmcAnalysis";
		return executeFind(hql);
	}

	public CmcAnalysis selectCmcAnalysisByName(String name) {
		String hql = "from CmcAnalysis where name=?";
		List<CmcAnalysis> lstAnalysis = executeFind(hql,name);
		if (!CollectionUtils.isEmpty(lstAnalysis)) {
			return lstAnalysis.get(0);
		}else{
			return null;
		}
	}

	public void updateCmcAnalysis(CmcAnalysis cmcAnalysis) {
		update(cmcAnalysis);
	}
}
