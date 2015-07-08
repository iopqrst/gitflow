package com.bskcare.ch.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.PackageVsItemDao;
import com.bskcare.ch.vo.PackageVsItem;

/**
 * 套餐项目信息
 * @author houzhiqing
 */
@Repository
public class PackageVsItemDaoImpl extends BaseDaoImpl<PackageVsItem> implements PackageVsItemDao {

	public List<PackageVsItem> executeFind(Integer packageId) {
		String hql = "from PackageVsItem where packageId = ? order by id asc";
		return executeFind(hql, packageId);
	}

	public int deleteByPackageId(Integer id) {
		String hql = "delete from PackageVsItem where packageId = ?";
		return updateByHql(hql, id);
	}
	
}
