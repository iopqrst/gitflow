package com.bskcare.ch.service.timeLine;

import com.bskcare.ch.bo.RiskResultBean;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.timeLine.EvaluatingResult;
import com.bskcare.ch.vo.timeLine.TimeLineSport;

public interface TimeLineSportService {
	/**
	 * 获得运动信息 分页
	 * 
	 * @param lineSport
	 * @param queryInfo
	 * @return
	 */
	public PageObject<TimeLineSport> getPageObject(TimeLineSport lineSport,
			QueryInfo queryInfo);

	/**
	 * 添加分页
	 * 
	 * @param lineSport
	 */
	public void add(TimeLineSport lineSport);

	/**
	 * 根据评测结果获得用户的运动信息
	 * 
	 * @param evaluatingResult
	 * @return
	 */
	public String getTimeLineConBySport(RiskResultBean bean, TimeLineSport timeLineSport, Integer clientId);

	/**
	 * 删除运动库
	 * 
	 * @param id
	 */
	public void delete(Integer id);

	/**
	 * 获得运动库
	 * 
	 * @param id
	 */
	public TimeLineSport getLineSportById(Integer id);
	
	/**
	 * 根据评测结果获得用户的运动信息
	 */
	public TimeLineSport getLineSportByEvalResult(RiskResultBean bean, int soft);
	
	public String getTimeLineSportType(String sportType,Integer cid);
	
}
