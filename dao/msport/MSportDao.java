package com.bskcare.ch.dao.msport;
import java.util.Date;
import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.bo.ClientLocationExtend;
import com.bskcare.ch.vo.msport.MSport;

public interface MSportDao extends BaseDao<MSport> {

	public MSport querySport(Integer clientId, Date testDate);
	
	/**
	 * 根据用户id查询获取上传的历史数据的总和
	 */
	public List<MSport> querySportByCid(Integer cid);
	
	/**
	 * 根据用户id和时间以月为单位，每次请求一个月的数据
	 */
	//public List<MSport> querySportMonth(Integer cid,int year,int month);
	
	public List<MSport> querySportMonth(Integer cid,String testDate);
	
	/**
	 * 根据用户id查询结果集
	 */
	public List<MSport> getTodayUploadDateTimeList(Integer clientId);
	
	/**
	 * 根据用户id查询所有的运动的各种睡眠质量总值
	 * @return
	 */
	public Object queryTotalSport(Integer clientId);
	
	
	/**根据用户id和测试日期查询上个月用户的动动信息**/
	public List<Object> querySportSort(String type);
	
	public List<MSport> querySportByType(Integer cid,String type);
	
	/**查询约跑成就榜**/
	public List<Object> queryRunSport(int runCount);
	
	/**查询约跑所有的跑友成就榜**/
	public List<ClientLocationExtend> queryAllRunSport(int runCount);
	
	public List<Object> queryMsportClient(Integer clientId,String type);
	
	public List<Object> queryClientMsport(Integer clientId,String type,Date startDate,Date endDate);
	
	/**
	 * 查询上周和本周睡眠情况信息
	 * @param clientId
	 * @param startDate 开始时间
	 * @param endDate  结束时间
	 * @return
	 */
	public List<Object> queryMsportSleepSum(Integer clientId,Date startDate, Date endDate);
	
}
