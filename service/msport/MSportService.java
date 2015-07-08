package com.bskcare.ch.service.msport;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.bskcare.ch.vo.msport.MSport;
import com.bskcare.ch.vo.msport.MSportPlan;

public interface MSportService {
	/**
	 *添加
	 */
	public String addMSport(List<MSport> list);
	
	/**
	 * 获取用户上传的历史数据的总和
	 */
	public String querySportTotal(Integer cid) throws ParseException;
	
	/**
	 * 根据用户id和时间以月为单位，每次请求一个月的数据
	 */
	//public String querySportMonth(Integer cid,int year,int month);
	public String querySportMonth(Integer cid,String testDate);
	
	/**
	 * 根据用户id查询所有的运动的各种睡眠质量总值
	 * @return
	 */
	public String queryTotalSport(Integer clientId);
	
	/**
	 * 根据用户id查询某人动动
	 */
	public String dongDongQuerySport(Integer clientId) throws ParseException;
	
	public String querySportYesterday(Integer cid);
	
	public String querySportWeek(Integer cid);
	
	public String querySportLastMonth(Integer cid);
	
	/**运动体质评估部分**/
	public String queryHealthSport(Integer clientId);

	
	public String querySportPlan(Integer clientId);

	/**今日运动成就**/
	public String queryTodaySport(Integer clientId);
	
	/**保存运动时间**/
	public String savePlanDay(MSportPlan plan);
	
	/**保存步长值**/
	public String saveStepWidth(MSportPlan plan);
	
	public String queryMsportClient(Integer clientId,String type);
	
	public String queryClientMsport(Integer clientId, int type, Date testDate);
	
	public String queryMsportSleep(Integer clientId);
	
	public void test();

	public String queryMsportCalorie(MSport msport);
}
