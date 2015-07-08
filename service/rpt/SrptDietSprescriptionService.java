package com.bskcare.ch.service.rpt;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.rpt.SrptDietSprescription;

public interface SrptDietSprescriptionService {

	/**
	 * 增加
	 */
	public void add(SrptDietSprescription dietSprescription);

	/**
	 * 删除
	 */
	public void delete(Integer id);

	/**
	 * 修改
	 */
	public void update(SrptDietSprescription dietSprescription);

	/**
	 * 查询单个对象
	 */
	public SrptDietSprescription findById(Integer id);

	/**
	 * 全查
	 */
	public PageObject<SrptDietSprescription> queryAll(SrptDietSprescription dietSprescription,QueryInfo queryInfo);
}
