package com.bskcare.ch.dao;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.SearchDiseaseVo;

public interface SearchDao {
	public PageObject getSssList(String con,SearchDiseaseVo sss,QueryInfo queryInfo);
}
