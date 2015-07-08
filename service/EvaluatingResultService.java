package com.bskcare.ch.service;

import net.sf.json.JSONObject;

import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.timeLine.EvaluatingResult;

public interface EvaluatingResultService {
	/**
	 * 保存评测结果
	 * 
	 * @param result
	 */
	void saveOrUpdate(EvaluatingResult result);

	/**
	 * 查询用户是否参与软件评测
	 */
	public int evaluateType(int evalType, ClientInfo qci);
	/**
	 * 返回用户是否能进行评测
	 * @param cid
	 * @param softType
	 * @return type：1  用户的前三次评估
				type：2  用户评估超过三次，但本月可评估
				type：3  用户评估超过三次，并且本月已经评估过

	 */
	public JSONObject whetherCanEvaluate(Integer cid , int softType);
	
	public String queryEvaluatingResult(Integer cid , int softType);
}
