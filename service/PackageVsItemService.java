package com.bskcare.ch.service;

import java.util.List;

import com.bskcare.ch.vo.PackageVsItem;


/**
 * 产品套餐对应项目
 * @author houzhiqing
 *
 */
public interface PackageVsItemService {

	public List<PackageVsItem> executeFind(Integer packageId);
	
}
