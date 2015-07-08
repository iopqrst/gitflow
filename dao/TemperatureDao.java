package com.bskcare.ch.dao;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.Temperature;

@SuppressWarnings("unchecked")
public interface TemperatureDao extends BaseDao<Temperature>{
	
	/**查询某个用户的体温信息**/
	public PageObject queryTemperatureList(Temperature temperature,QueryInfo queryInfo,QueryCondition queryCondition);
}
