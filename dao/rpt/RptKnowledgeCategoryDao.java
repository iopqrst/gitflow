package com.bskcare.ch.dao.rpt;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.rpt.RptKnowledgeCategory;

public interface RptKnowledgeCategoryDao extends BaseDao<RptKnowledgeCategory>{

	public List<RptKnowledgeCategory> queryKnowledgeCategory(RptKnowledgeCategory kr) ;
	
}
