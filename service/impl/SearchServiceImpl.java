package com.bskcare.ch.service.impl;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.SearchDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.SearchService;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.vo.SearchDiseaseVo;

@Service("searchService")
public class SearchServiceImpl implements SearchService {
	@Autowired
	private SearchDao searchDao;

	public String getSssList(String con, SearchDiseaseVo searchDiseaseVo,
			QueryInfo queryInfo) {
		PageObject<SearchDiseaseVo> po = searchDao.getSssList(con,
				searchDiseaseVo, queryInfo);
		JSONObject jo = new JSONObject();
  
		jo.put("total", po.getTotalRecord());
		jo.put("sssList", JsonUtils.getJsonString4JavaList(po.getDatas()));

		return jo.toString();
	}

}
