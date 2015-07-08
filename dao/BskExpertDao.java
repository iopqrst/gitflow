package com.bskcare.ch.dao;

import java.util.List;
import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.bo.BskExpertExtend;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.BskExpert;

@SuppressWarnings("unchecked")
public interface BskExpertDao extends BaseDao<BskExpert>{
	
	/**
	 * 查询状态为启用的所有的专家团队的信息分页
	 */
	public PageObject findBskExpert(BskExpert expert , QueryInfo info);

	/**
	 * 查询角色(或者用户Ids)为医师，健康管理师的   和专家信息
	 * @param roleIds 角色Ids 多个以","分割
	 * @param userIds 管理员用户Ids 多个以","分割
	 * 
	 * @return 返回专家扩展类，该类中包含了用户的角色 
	 */
	public List<BskExpert> findBskExpertByclientAreaId(String userIds);
	
	/**
	 * 查询状态为启用的所有的专家团队的信息 不分页
	 */
	public List<BskExpert> findBskExpertAll();
	
	/**
	 * 在线咨询部分修改满意度
	 */
	public int updateOnlineInfo(BskExpert be);
	
	public BskExpert queryExpertByUserId(Integer userId);
	
	/**
	 * 根据管理员Ids 查询专家信息
	 */
	public List<BskExpert> queryExpertByUserIds(String userIds);
	/**
	  * 根据手机号（tq号）查询专家信息
	 * @param mobile手机号（tq）
	 * @return 返回专家
	 */
	public BskExpert queryExpertByMobile(String mobile);
	
	public PageObject queryBskExpert(String type, QueryInfo queryInfo);
	
	/**
	 * 该接口会返回专家团队信息及<b>糖尿病<b>高管评论总数和好评数
	 * @param userIds
	 * @return
	 */
	public List<BskExpertExtend> queryExpertByUids(String userIds);

	/**
	 * <b>专家团队评分</b>
	 * @param cid 客户id
	 * @param serviceScore 服务评价分数
	 * @param medicalScore 医疗评价分数
	 * @param expertId 专家Id
	 * @return
	 */
	public int addEvalution(Integer cid, int serviceScore, int medicalScore,
			Integer expertId);
}
