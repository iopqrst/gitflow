package com.bskcare.ch.service.tg;

import com.bskcare.ch.bo.RiskResultBean;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.tg.TgRptBaseInfo;

public interface TgRptBaseInfoService {
	
	/**
	 * 根据客户评测结果信息生成糖尿病评测结果报告
	 * @param clientId 客户id
	 * @param riskResult  评测结果
	 * @param evalId  评测id
	 * @return
	 */
	public String createTgRptBaseInfo(Integer clientId, RiskResultBean riskResult, Integer evalId);
	
	public TgRptBaseInfo queryTgRptBaseInfo(TgRptBaseInfo tgRpt);
	
	/**
	 * 查询用户最后一次评测的评测报告信息
	 * @param clientId
	 * @return
	 */
	public TgRptBaseInfo queryLatestTgRptInfo(TgRptBaseInfo tgRpt);
	
	public String queryTgRpt(TgRptBaseInfo tgRpt, Integer softType, QueryInfo queryInfo);
	
	public void createTgRptBaseInfo();
}
