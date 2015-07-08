package com.bskcare.ch.dao.impl.rpt;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.rpt.RptKnowledgeCategoryDao;
import com.bskcare.ch.vo.rpt.RptKnowledgeCategory;

@Repository
@SuppressWarnings("unchecked")
public class RptKnowledgeCategoryDaoImpl extends BaseDaoImpl<RptKnowledgeCategory>
		implements RptKnowledgeCategoryDao {

	public List<RptKnowledgeCategory> queryKnowledgeCategory(RptKnowledgeCategory kr) {
		StringBuffer hql = new StringBuffer("from RptKnowledgeCategory r where r.status = ?");
		
		ArrayList args = new ArrayList();
		args.add(Constant.STATUS_NORMAL);
		
		if(null != kr) {
			if(null != kr.getParentId()) {
				hql.append(" and r.parentId = ?");
				args.add(kr.getParentId());
			}
			
			if(!StringUtils.isEmpty(kr.getName())) {
				hql.append(" and r.name like ?");
				args.add("%" + kr.getName() + "%");
			}
		}
		
		return executeFind(hql.toString(), args.toArray());
	}
	
}
