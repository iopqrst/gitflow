package com.bskcare.ch.dao.rpt;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.RptDisease;

public interface RptDiseaseDao extends BaseDao<RptDisease> {

	public List<RptDisease> queryDisease(String medical);

	public List<RptDisease> queryAllDisease();

	public List<RptDisease> queryAllDisease(RptDisease disease);

	public PageObject<RptDisease> querypagerObject(QueryInfo queryInfo,
			RptDisease disease);

	/**
	 * 添加疾病，并添加食物字段，营养膳食字段
	 * 
	 * @param disease
	 */
	public void addDisease(RptDisease disease);

	/**
	 * 根据id获得疾病对象
	 */
	public RptDisease loadDisease(Integer id);

	/**
	 * 更新对象
	 */
	public void updateDisease(RptDisease disease);

	/**
	 * 删除疾病对象
	 */
	public void deleteDisease(Integer id);

	/**
	 * 根据疾病名称查询疾病编号
	 */
	public String getIdByDiseaseName(String diseaseName);
	/**
	 * 查询所有个人疾病
	 * @return
	 */
	public List<RptDisease> queryRptDiseaseSelf();
	/**
	 * 返回所有家族疾病
	 */
	public List<RptDisease> queryRptDiseaseFamily();
}
