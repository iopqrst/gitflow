package com.bskcare.ch.dao.msport;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.bo.MsportActivityExtend;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.msport.MSportActivity;

public interface MSportActivityDao extends BaseDao<MSportActivity> {
	
	/**
	 * 查询所有活动的信息
	 */
	public PageObject<MsportActivityExtend> queryActivityInfo(MSportActivity activity, QueryInfo queryInfo);
	
	/**
	 * 根据活动id查询活动人的信息
	 */
	public PageObject<Object> queryActivityClient(MSportActivity activity, QueryInfo queryInfo);

	/**
	 * 请求我创建的活动
	 */
	public List<MSportActivity> selfCreatedActivity(Integer cid);

	/**
	 * 根据活动i的查询创建活动人的信息
	 */
	public ClientInfo queryClientByaid(MSportActivity activity);

	/**
	 * 请求我加入的活动
	 */
	public List<MSportActivity> seselfJoinedActivity(Integer cid);

	/**
	 * 申请加入活动
	 */
	public int applyJoinActivity(Integer cid, Integer aid);

	/**
	 * 查询是否已加入活动
	 */
	public int isJoinActivity(Integer cid, Integer aid);
	
	public MSportActivity queryActivityByPwd(MSportActivity activity);

}
