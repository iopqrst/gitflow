package com.bskcare.ch.service.msport;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.msport.MSportActivity;

import com.bskcare.ch.vo.msport.MSportActivity;

public interface MSportActivityService {
	/**
	 * 查询所有活动的信息
	 */
	public String queryActivityInfo(MSportActivity activity, QueryInfo queryInfo);
	
	/**
	 * 根据活动id查询活动人的信息
	 */
	public String queryActivityClient(MSportActivity activity, QueryInfo queryInfo);
	public MSportActivity load(Integer aid);

	public MSportActivity add(MSportActivity ma);

	public void update(MSportActivity ma);

	/**
	 * 请求我创建的活动
	 */
	public String selfCreatedActivity(Integer cid);

	/**
	 * 请求我加入的活动
	 */
	public String seselfJoinedActivity(Integer cid);

	/**
	 * 申请加入活动
	 */
	public String applyJoinActivity(Integer cid, Integer aid);
	
	/**
	 * 查询是否已加入活动
	 */
	public int isJoinActivity(Integer cid, Integer aid);
	
	public String queryAllActivityInfo(MSportActivity activity, QueryInfo queryInfo);
	
	public String joinActivity(MSportActivity activity);
	
	public String queryActivityClientInfo(MSportActivity activity, QueryInfo queryInfo);
}
