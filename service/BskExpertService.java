package com.bskcare.ch.service;

import java.util.List;

import com.bskcare.ch.bo.BskExpertExtend;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.BskExpert;

@SuppressWarnings("unchecked")
public interface BskExpertService {

	/**
	 * 查询所有的专家团队的信息
	 */
	public PageObject findBskExpert(BskExpert expert, QueryInfo queryInfo);

	/**
	 * 添加专家团队
	 */
	public BskExpert addBskExport(BskExpert exprt);

	/**
	 * 修改专家团队信息
	 */
	public void updateBskExport(BskExpert exprt);

	/**
	 * 根据id查询某个专家的信息
	 */
	public BskExpert findBskExpertById(Integer id);

	/**
	 * 根据id删除摸个专家
	 */
	public void deleteBskExpert(Integer id);


	/**
	 * 查询某一区域的健康管理师和专家
	 */
	public List<BskExpert> queryExpertByAreaId(Integer areaId);

	/**
	 * 查询状态为启用所有的专家团队的信息
	 * 
	 * @return
	 */
	public List<BskExpert> findBskExpertAll();
	
	public PageObject queryBskExpert(String type, QueryInfo queryInfo);
	/**
	 * 根据userid查询专家的信息
	 */
	public List<BskExpert> findBskExpertByuserId(String id);
	/**
	 * 根据手机号（tq号）查询专家信息
	 * @param mobile手机号（tq）
	 * @return
	 */
	public BskExpert quertBskExpertByMobile(String mobile);
	
	/**
	 * 该接口会返回专家团队信息及<b>糖尿病<b>高管评论总数和好评数
	 * @param userIds
	 * @return
	 */
	public List<BskExpertExtend> queryExpertExtendByAreaId(Integer areaId);
	
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
