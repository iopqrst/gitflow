package com.bskcare.ch.dao.rpt;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.rpt.SportPrescription;

@SuppressWarnings("unchecked")
public interface SportPrescriptionDao extends BaseDao<SportPrescription> {

	public List<SportPrescription> querySportPrescription(SportPrescription sp,
			String ids);

	/**
	 * 查询所有的大处方和小处方
	 */
	public List<SportPrescription> findPrescription();
	
	/**
	 * 查询所有的大处方小处方分页
	 */
	public PageObject findPrescriptionPage(SportPrescription pres,QueryInfo queryInfo);

	public String findPresByName(String name);
}
