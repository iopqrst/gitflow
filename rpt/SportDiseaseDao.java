package com.bskcare.ch.dao.rpt;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.rpt.SportDisease;

public interface SportDiseaseDao extends BaseDao<SportDisease> {
	public List<SportDisease> querySportDisease(SportDisease sd);
	public PageObject<SportDisease> querySportDiseaseList(SportDisease disease,QueryInfo queryInfo);
}
