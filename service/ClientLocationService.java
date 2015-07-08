package com.bskcare.ch.service;

import java.text.ParseException;

import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ClientLocation;
import com.bskcare.ch.vo.msport.MsportComment;

public interface ClientLocationService {
	
	/**
	 * 根据用户提交的经纬度保存
	 */
	public String saveLocation(ClientLocation location) throws Exception;
	
	/**
	 * 根据经纬度获取周围列表
	 */
	public String queryClientByLonLat(ClientLocation location,double distance,QueryInfo queryInfo);
	
	/**查询约跑所有的跑友成就榜**/
	public String queryAllRunSport(Integer cid) throws ParseException;
	
	/**约跑评论**/
	public String evaluateByClient(MsportComment comment,String type);
	
	/**查询约跑好友的信息**/
	public String querySportFriend(ClientLocation location,QueryInfo queryInfo);
}
