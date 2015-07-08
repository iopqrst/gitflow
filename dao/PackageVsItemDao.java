package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.PackageVsItem;

/**
 * 产品套餐对应项目的中间表
 * @author houzhiqing
 */
public interface PackageVsItemDao extends BaseDao<PackageVsItem>{
	
	/**
	 * 创建套餐时添加套餐对应项目的中间表
	 * @return 影响条数
	 */
	public PackageVsItem add(PackageVsItem pvi);
	
	public List<PackageVsItem> executeFind(Integer packageId);

	public int deleteByPackageId(Integer id);
}
