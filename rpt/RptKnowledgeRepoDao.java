package com.bskcare.ch.dao.rpt;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.rpt.RptKnowledgeRepo;

public interface RptKnowledgeRepoDao extends BaseDao<RptKnowledgeRepo>{
	
	public List<RptKnowledgeRepo> queryKnowledge(RptKnowledgeRepo kr);
	public List<String> queryTags(String node);
	public PageObject<RptKnowledgeRepo> queryTitleKnowTag(RptKnowledgeRepo kr,QueryInfo queryInfo);
}
