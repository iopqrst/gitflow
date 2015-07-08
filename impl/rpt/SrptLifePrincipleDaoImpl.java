package com.bskcare.ch.dao.impl.rpt;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.rpt.SrptLifePrincipleDao;
import com.bskcare.ch.vo.rpt.SrptLifePrinciple;

@Repository
@SuppressWarnings("unchecked")
public class SrptLifePrincipleDaoImpl extends BaseDaoImpl<SrptLifePrinciple> implements SrptLifePrincipleDao{
	
	public SrptLifePrinciple queryLifePrinciple(Integer id){
		String hql = "from SrptLifePrinciple where id = ?";
		List<SrptLifePrinciple> lst = executeFind(hql, id);
		SrptLifePrinciple life = null;
		if(!CollectionUtils.isEmpty(lst)){
			life = lst.get(0);
		}
		return life;
	}
	
	public List<SrptLifePrinciple> queryListLifePriciple(String ids){
		String sql = "select * from srpt_life_principle where id in ("+ids+")";
		return executeNativeQuery(sql,null, SrptLifePrinciple.class);
	}
}
