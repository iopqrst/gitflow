package com.bskcare.ch.service;

import java.util.List;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.UnusualData;
import com.bskcare.ch.vo.UnusualDataExtends;

public interface UnusualDataService {

	/**
	 * 按血压、血氧、血糖、心电分别保存异常数据
	 */
	public void saveAbnormalData(UnusualData unusualData);
	/**
	 * 不同区域的不同用户异常数据列表
	 */
	public PageObject<UnusualDataExtends> queryUnusualDataList(UnusualDataExtends dataExtends,QueryCondition queryCondition,QueryInfo queryInfo);
	/**
	 * 根据id找到对象
	 */
	public UnusualData findUnustalDataById(Integer id);
	/**
	 * 更新
	 */
	public void updateStatus(UnusualData unusualData);
	/**
	 * 查询用户最近一周的异常数据
	 */
	public List<UnusualData> queryRecentOnceDataList(Integer clientId);
	
	/**
	 * 标记异常数据为"已处理" 
	 * <b>修改异常数据中时同时也会找到对应的原始数据，将原始数据的状态也修改掉</b>
	 */
	public void updateUnusualData(UnusualData ud);
	
	/**
	 * 仅仅表示异常数据的状态，不对原始数据进行操作，
	 * 因为在操作该数据前已经修改了原始数据的状态。
	 * @param dataId 原始数据Id
	 * @param clientId
	 * @param handled
	 * @param type 1：血压 2：血氧 3：心电 4：空腹血糖 5：餐后2小时血糖
	 */
	public void flagUnusualData(Integer dataId, Integer type,Integer clientId, int handled);
}
