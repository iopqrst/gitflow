package com.bskcare.ch.dao.rpt;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.rpt.SrptDietSprescription;

public interface SrptDietSprescriptionDao extends BaseDao<SrptDietSprescription>{

	/**
	 * 分页查询
	 * @param queryInfo
	 * @return
	 */
	public PageObject<SrptDietSprescription> queryAll(SrptDietSprescription dietSprescription,QueryInfo queryInfo);
	
	public List<SrptDietSprescription> queryDiet(String ids);
}
