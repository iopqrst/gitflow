package com.bskcare.ch.service.rpt;

import com.bskcare.ch.vo.rpt.RptBaseInfo;
import com.bskcare.ch.vo.rpt.RptDiet;

public interface AutoGenerateRptService {
	
	/**
	 * 生成运动报告
	 */
	public RptBaseInfo generateRpt(Integer clientId) ;
	/**
	 * 修改运动膳食数据
	 */
	public void updateSportDietData(Integer unSportDietId, RptDiet diet);
	
	/**
	 * 根据体力活动基数修改相应的热卡
	 * @param sportDietId 运动膳食数据Id
	 * @param unSportDietId 非运动膳食数据Id
	 * @param cardinality 基数
	 */
	public void updateDietCardinality(Integer sportDietId, Integer unSportDietId, int cardinality) ;
	/**
	 * 根据客户id判断客户信息是否能够生成报告
	 * @return 返回判断结果，如果不能生成报告，返回必填的属性值，如果能生成报告，返回success 
	 */
	public String verifyClientInfoForRpt(Integer clientId,Integer rptType);
}
