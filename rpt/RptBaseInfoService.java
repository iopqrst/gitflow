package com.bskcare.ch.service.rpt;

import java.util.Date;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.rpt.RptAuditRecord;
import com.bskcare.ch.vo.rpt.RptBaseInfo;

@SuppressWarnings("unchecked")
public interface RptBaseInfoService {
	
	/**
	 * 根据用户id查询健康报告中用户的基本信息
	 * @param clientId
	 * @return
	 */
	public RptBaseInfo addRptBaseInfoByClientId(Integer clientId,String subTime);
	
	/**
	 * 根据健康报告id查询健康报告基本信息
	 */
	public RptBaseInfo findRptBaseInfo(Integer rptId);
	
	/**
	 * 根据健康报告id（rptId）修改问卷状况说明和健康评估信息
	 */
	public int updateRptBaseByField(String field,String content, Integer rptId);
	
	
	/**
	 * 根据后台管理端登陆的用户id查询健康报告的信息
	 */
	public PageObject findAutoRptByUserId(String areaChain,QueryInfo info, RptBaseInfo rpt,Integer userId,QueryCondition queryCondition ,Date beginTime ,Date endTime);
	
	/**
	 * 根据用户id和状态查询健康报告
	 */
	public String findRptListToJson(RptBaseInfo rptBaseInfo,Integer clientId,QueryInfo queryInfo);
	public String findRptListToJson(Integer cid,QueryInfo info,Integer type,String status);
	

	/**
	 * 根据健康报告id点击阅读之后把阅读的状态改为1
	 */
	public int updateRptRead(Integer rptId);
	
	/**
	 * 根据健康报告id改变健康报告状态
	 */
	public void updateRptStatus(String content,String field ,Integer rptId,RptAuditRecord audit);
	
	/**
	 *判断是否是最后一个审核
	 */
	public int latestAudit(String field,Integer rptId);
	
	
	public int updateRptByField(String field,Integer content, Integer rptId);
	/**
	 * 根据用户编号查询最近一次健康报告
	 */
	public String findRptBaseInfoByClientId(Integer clientId);
	
	/**
	 * 根据用户id查询已提交给用户的健康报告列表
	 */
	public PageObject findRptList(RptBaseInfo rptBaseInfo,Integer clientId,QueryInfo queryInfo);
	
	/**根据用户id查询最近一侧健康报告的信息*/
	public RptBaseInfo queryLatestRptBaseInfo(Integer clientId);
	
	/**根据健康报告id删除健康报告**/
	public void deleteRptById(Integer rptId);
	
	public void deleteRptByIdRel(Integer rptId);
	/**
	 *根据用户id，报告类型状态查询报告列表 ,type 1.完整报告，2.10天简易报告  3.30天简易报告
	 */
	public PageObject findRptListByUserId(Integer cid,QueryInfo info,Integer type,String status);
}
