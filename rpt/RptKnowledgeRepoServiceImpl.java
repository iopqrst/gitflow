package com.bskcare.ch.service.impl.rpt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.rpt.RptKnowledgeRepoDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.rpt.RptKnowledgeRepoService;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.vo.rpt.RptKnowledgeRepo;

@Service
public class RptKnowledgeRepoServiceImpl implements RptKnowledgeRepoService {

	@Autowired
	private RptKnowledgeRepoDao knowledgeRepoDao;

	public String queryKnowledge(RptKnowledgeRepo kr) {
		List<RptKnowledgeRepo> list = knowledgeRepoDao.queryKnowledge(kr);
		String repo = "";
		if(!CollectionUtils.isEmpty(list)) {
			repo = JsonUtils.getJsonString4JavaListDate(list);
		}
		return repo;
	}

	public RptKnowledgeRepo add(RptKnowledgeRepo kr) {
		return knowledgeRepoDao.add(kr);
	}

	public String queryTags(String node) {
		List<String> list = knowledgeRepoDao.queryTags(node);
		String tags = "";
		Map<String,String> kmap = new HashMap<String, String>(); 
		if(!CollectionUtils.isEmpty(list)) {
			for (String str : list) {
				String[] aTags = str.split(Constant.RPT_TAG_SPLIT);
				for (String ss : aTags) {
					kmap.put(ss, ss);
				}
			}
			
			if(kmap.size() > 0) {
				Set<String> keys = kmap.keySet();
				String temp = "";
				for (String k : keys) {
					int i = 0;
					temp += "{\"name\":\""+k+"\"},";
					i++;
				}
				if(!"".equals(temp) && Constant.RPT_TAG_SPLIT.equals(temp.substring(temp.length() -1 , temp.length()))) {
					temp = temp.substring(0,temp.length()-1);
				}
				tags = "[" + temp +"]";
			}
		}
		return tags;
	}

	public PageObject<RptKnowledgeRepo> queryTitleKnowTag(RptKnowledgeRepo kr,QueryInfo queryInfo) {
		return knowledgeRepoDao.queryTitleKnowTag(kr,queryInfo);
	}

	public RptKnowledgeRepo findById(Integer kid) {
		return knowledgeRepoDao.load(kid);
	}

	public void updateKnowledge(RptKnowledgeRepo repo) {
		knowledgeRepoDao.update(repo);
	}

	public void deleteKnowledge(Integer kid) {
		knowledgeRepoDao.delete(kid);		
	}
}
