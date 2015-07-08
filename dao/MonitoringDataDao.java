package com.bskcare.ch.dao;

import com.bskcare.ch.bo.UploadExtend;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;

public interface MonitoringDataDao {

	public PageObject queryObject(UploadExtend uploadExtend, QueryCondition qc,
			QueryInfo qi);
	
	
	public int queryObject(UploadExtend uploadExtend, QueryCondition qc);
	
}
