package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.search.DiseaseInfo;

public interface DiseaseInfoDao {
	List<DiseaseInfo> queryCnr(String cbm);
	
	List<DiseaseInfo> queryCmc(String str);
	
	List<DiseaseInfo> queryByStr(String str);
	
	PageObject<DiseaseInfo> queryByPage(String str,QueryInfo queryInfo);
}
