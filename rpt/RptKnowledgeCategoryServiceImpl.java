package com.bskcare.ch.service.impl.rpt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.dao.rpt.RptKnowledgeCategoryDao;
import com.bskcare.ch.service.rpt.RptKnowledgeCategoryService;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.vo.rpt.RptKnowledgeCategory;

@Service
public class RptKnowledgeCategoryServiceImpl implements RptKnowledgeCategoryService {

	@Autowired
	private RptKnowledgeCategoryDao rptCategoryDao;

	public String queryKnowledgeCategory(RptKnowledgeCategory kc) {
		List<RptKnowledgeCategory> list = rptCategoryDao.queryKnowledgeCategory(kc);
		String repo = "";
		if(!CollectionUtils.isEmpty(list)) {
			repo = JsonUtils.getJsonString4JavaListDate(list);
		}
		return repo;
	}
	
	public List<RptKnowledgeCategory> queryCategoryList(RptKnowledgeCategory kc) {
		return rptCategoryDao.queryKnowledgeCategory(kc);
	}

	public RptKnowledgeCategory add(RptKnowledgeCategory kr) {
		return rptCategoryDao.add(kr);
	}
	
	
}
