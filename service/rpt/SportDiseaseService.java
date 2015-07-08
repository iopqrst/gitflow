package com.bskcare.ch.service.rpt;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.rpt.SportDisease;

public interface SportDiseaseService {

	/**
	 * 增加
	 */
	public SportDisease save(SportDisease sportDisease);
	
	/**
	 * 根军运动疾病等信息查询适合该病情的运动信息(大处方设置在sport中)
	 * @param sd
	 */
	public SportDisease querySportDiseaseForRpt(SportDisease sd);
	/**
	 * 查询所有运动疾病数据
	 */
	public PageObject<SportDisease> querySportDiseaseList(SportDisease disease,QueryInfo queryInfo);
	/**
	 * 查找单个对象
	 */
	public SportDisease findDiseaseById(Integer id);
	/**
	 * 修改
	 */
	public void update(SportDisease sportDisease);
	/**
	 * 删除
	 */
	public void delete(Integer id);
}
