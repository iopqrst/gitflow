package com.bskcare.ch.dao;

import java.util.Date;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ClientWeight;

@SuppressWarnings("unchecked")
public interface ClientWeightDao extends BaseDao<ClientWeight>{
	
	/**根据用户id和上传日期查询当天是否上传数据*/
	public ClientWeight queryWeightClient(ClientWeight weight);
	
	/**获取客户的体重信息*/
	public PageObject queryWeightByClientId(Integer clientId, Date startDate, Date endDate, QueryInfo queryInfo,int month);
}
