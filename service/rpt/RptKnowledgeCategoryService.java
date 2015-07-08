package com.bskcare.ch.service.rpt;

import java.util.List;

import com.bskcare.ch.vo.rpt.RptKnowledgeCategory;

public interface RptKnowledgeCategoryService {
	public String queryKnowledgeCategory(RptKnowledgeCategory kc);

	public List<RptKnowledgeCategory> queryCategoryList(RptKnowledgeCategory kc);

	public RptKnowledgeCategory add(RptKnowledgeCategory kr);
}
