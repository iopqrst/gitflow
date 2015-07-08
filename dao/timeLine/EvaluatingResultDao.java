package com.bskcare.ch.dao.timeLine;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.timeLine.EvaluatingResult;

public interface EvaluatingResultDao extends BaseDao<EvaluatingResult> {
	/**
	 * 获得所有用户最后一次测试结果
	 * 
	 * @return
	 */
	public List<EvaluatingResult> getList();
	
	/**
	 * 条件查询
	 */
	public List<EvaluatingResult> queryResultsByClientId(EvaluatingResult er);
	
	public EvaluatingResult queryLastEval(EvaluatingResult er);
	
	public List<EvaluatingResult> queryEvalNoCreateRpt();
}
