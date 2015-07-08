package com.bskcare.ch.service.rpt;

import java.util.List;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.rpt.SportPrescription;

@SuppressWarnings("unchecked")
public interface SportPrescriptionService {
	/**
	 * 增加
	 */
	public SportPrescription save(SportPrescription prescription);
	
	public SportPrescription querySinglePrescript(SportPrescription sp) ;
	
	public List<SportPrescription> queryPrescript(SportPrescription sp) ;
	
	public List<SportPrescription> queryPrescript(SportPrescription sp, String ids);
	/**
	 * 查询所有的大处方和小处方
	 */
	public String findPrescription();
	
	/**
	 * 查询所有的处方信息分页显示
	 */
	public PageObject findPrescriptionPage(SportPrescription pres,QueryInfo queryInfo);
	
	/**
	 * 根据id查询处方的信息
	 */
	public SportPrescription findPrescriptionById(Integer id);
	
	/**
	 * 根据id删除处方
	 */
	public void deletePrescriptionById(Integer id);
	
	/**
	 * 修改处方信息
	 */
	public void updatePrescription(SportPrescription pres);
	
	/**
	 * 添加处方信息
	 */
	public void addPrescription(SportPrescription pres);
	
	public String findPresByName(String name);
}
