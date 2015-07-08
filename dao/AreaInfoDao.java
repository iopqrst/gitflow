package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.AreaInfo;

public interface AreaInfoDao extends BaseDao<AreaInfo> {

	/**
	 * 查询所有状态为正常的区域信息
	 * 
	 * @param areaInfo
	 * @return 区域集合
	 */
	public List<AreaInfo> executeFind(AreaInfo areaInfo);

	/**
	 * 根据区域id来更新区域名字
	 * 
	 * @param id
	 *            区域id
	 * @param name
	 *            区域名字
	 */
	public void updateByIdName(int id, String name);

	/**
	 * 添加区域
	 * 
	 * @param areaInfo
	 *            区域实体
	 */
	public void addAreaInfo(AreaInfo areaInfo);

	/**
	 * 根据id删除区域
	 * 
	 * @param id
	 *            区域id
	 */
	public void deleteById(int id);

	public int findMaxId();
	
	/** 
	 * 更新区域链
	 */
	public void updateAreaChain(AreaInfo areaInfo);

	public void updateAreaInfo(AreaInfo areaInfo);
	
	/**
	 * 根据管理员区域链查询数据
	 */
	public List<AreaInfo> getAdminArea(List list);
}
