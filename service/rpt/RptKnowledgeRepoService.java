package com.bskcare.ch.service.rpt;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.rpt.RptKnowledgeRepo;

public interface RptKnowledgeRepoService {
	
	public RptKnowledgeRepo add(RptKnowledgeRepo kr);
	public String queryKnowledge(RptKnowledgeRepo kr);
	public String queryTags(String node);
	public PageObject<RptKnowledgeRepo> queryTitleKnowTag(RptKnowledgeRepo kr,QueryInfo queryInfo);
	public RptKnowledgeRepo findById(Integer kid);
	public void updateKnowledge(RptKnowledgeRepo repo);
	public void deleteKnowledge(Integer kid);
}
