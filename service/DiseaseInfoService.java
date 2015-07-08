package com.bskcare.ch.service;

import java.util.List;

import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.search.DiseaseInfo;

public interface DiseaseInfoService {
	List<DiseaseInfo> queryCnr(String cbm);
	
	List<DiseaseInfo> queryByStr(String str);
	
	List<DiseaseInfo> queryCmc(String str);
	
	

	String queryByPage(String str, QueryInfo queryInfo);


	String queryMsgAndriod(String cbm);
}
