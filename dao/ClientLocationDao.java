package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ClientLocation;

@SuppressWarnings("unchecked")
public interface ClientLocationDao extends BaseDao<ClientLocation>{
	
	/**
	 * 根据用户id查询用户信息
	 */
	public ClientLocation queryLocationByClientId(Integer clientId);
	
	/**
	 * 根据经纬度获取周围列表
	 */
	public PageObject queryClientByLonLat(Integer clientId,double minLat,double maxLat,double minLng,double maxLng,QueryInfo queryInfo);

	/**约跑评论**/
	public int evaluateByClient(Integer clientId,String type);
	
	/**获取约跑的好友信息**/
	public PageObject querySportFriend(Integer cid,QueryInfo queryInfo);
	
	/**查询附近的跑友的运动排名**/
	public List<Object> querySortLonLog(double minLat,double maxLat,double minLng,double maxLng);
}
