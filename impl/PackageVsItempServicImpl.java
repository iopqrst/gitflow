package com.bskcare.ch.service.impl;

import java.util.List;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.PackageVsItemDao;
import com.bskcare.ch.service.PackageVsItemService;
import com.bskcare.ch.vo.PackageVsItem;

/**
 * 产品套餐对应项目
 * @author houzhiqing
 *
 */
@Service("packageVsItemService")
public class PackageVsItempServicImpl implements PackageVsItemService {
	
	protected transient final Logger log = Logger.getLogger(PackageVsItempServicImpl.class);
	
	@Autowired
	private PackageVsItemDao packageVsItemDao;

	public List<PackageVsItem> executeFind(Integer packageId) {
		return packageVsItemDao.executeFind(packageId);
	}
	
}
