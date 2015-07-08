package com.bskcare.ch.dao;

import java.util.Date;
import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.UnusualData;
import com.bskcare.ch.vo.UnusualDataExtends;

public interface UnusualDataDao extends BaseDao<UnusualData>{

	/**
	 * 不同区域的不同用户异常数据列表
	 */
	public PageObject<UnusualDataExtends> queryUnusualDataList(UnusualDataExtends dataExtends,QueryCondition queryCondition,QueryInfo queryInfo);
	/**
	 * 查询用户最近一周的异常数据
	 */
	public List<UnusualData> queryRecentOnceDataList(Integer clientId);
	
	/**
	 * 仅仅表示异常数据的状态，不对原始数据进行操作，
	 * 因为在操作该数据前已经修改了原始数据的状态。
	 * @param dataId 原始数据Id
	 * @param clientId
	 * @param handled 状态,0为未处理,1为已处理
	 * @param type 1：血压 2：血氧 3：心电 4：空腹血糖 5：餐后2小时血糖
	 */
	public void flagUnusualData(Integer dataId, Integer type, Integer clientId,
			int handled);
	
	public int disposeByDayTime(int type, Integer clientId, Date testDate);
	
	public int queryUnusualDataList(UnusualDataExtends dataExtends, String areaChain);
}
