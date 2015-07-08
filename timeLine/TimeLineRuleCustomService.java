package com.bskcare.ch.service.timeLine;

import com.bskcare.ch.vo.timeLine.TimeLineRuleCustom;

public interface TimeLineRuleCustomService {
	/**
	 * 更新自定义任务
	 * @param cid
	 * @param ruleCustom
	 */
	public void updateRuleCustom(Integer cid, String ruleCustom);
	/**
	 * 获得自定义任务
	 * @param cid
	 * @param softType
	 * @return
	 */
	public String getRuleCustom(Integer cid,Integer softType);
}
