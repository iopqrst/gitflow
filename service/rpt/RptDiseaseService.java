package com.bskcare.ch.service.rpt;

import java.util.List;
import java.util.Map;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.RptDisease;

public interface RptDiseaseService {
	/**
	 * 添加信息
	 */

	public RptDisease addRptDisease(RptDisease rptDisease);

	public String getMedicalCondition(String medical, int amount);

	public List<RptDisease> queryDisease(String medical);

	public List<RptDisease> queryAllDisease();
	
	public List<RptDisease> queryAllDisease(RptDisease disease);

	public PageObject<RptDisease> querypagerObject(QueryInfo queryInfo,
			RptDisease disease);

	/**
	 * 添加疾病，同时在食物表和营养膳食表添加字段
	 */
	public int addDiseaseAndFood(RptDisease disease);

	/**
	 * 根据id获得疾病对象
	 */
	public RptDisease loadDisease(Integer integer);

	/**
	 * 更新对象
	 */
	public void updateDisease(RptDisease disease);

	/**
	 * 删除疾病对象
	 */
	public int deleteDisease(Integer integer);
	/**
	 * 根据对象查询
	 */
	
	/**
	 * 根据疾病名称查询疾病编号 
	 */
	public String getIdByDiseaseName(String diseaseName);
	
	public String queryRptDiseaseSelf();

}
