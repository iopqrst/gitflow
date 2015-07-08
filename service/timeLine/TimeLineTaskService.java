package com.bskcare.ch.service.timeLine;

import java.util.Date;
import java.util.List;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.tg.MedicineReminder;
import com.bskcare.ch.vo.timeLine.TimeLineTask;

public interface TimeLineTaskService {
	/**
	 * 返回用户的时间轴
	 * 
	 * @param cid
	 *            用户id
	 * @param day
	 *            0昨天 1 今天
	 * @return
	 */

	public String getTimeLineTask(Integer cid, Integer day);

	/**
	 * 返回没有登录的用户的固定时间轴
	 * 
	 * @return
	 */
	public List<TimeLineTask> queryListToNoLogin();

	/**
	 * 获得用户时间轴
	 * 
	 * @return
	 */
	public List<TimeLineTask> queryList(Integer cid, Integer day);

	public PageObject<TimeLineTask> queryListToNoLogin(TimeLineTask lineTask,
			QueryInfo info);

	/**
	 * 添加固定任务
	 * 
	 * @param lineTask
	 */
	public void addFixTask(TimeLineTask lineTask);

	/**
	 * 完成任务
	 * 
	 * @param cid
	 * @param taskId
	 */
	public void overTask(Integer cid, Integer taskId, Integer stauts);

	/**
	 * 饮食完成率
	 * 
	 * @param cid
	 * @param type
	 *            1:饮食任务 ：所有任务
	 * @return
	 */
	public String getDietTaskFillRate(Integer cid, Integer type);

	/**
	 * 饮食任务完成率
	 * 
	 * @param cid
	 * @return
	 */
	public int getTaskFillRate(Integer cid);

	/**
	 * 近三个月的饮食完成情况
	 * 
	 * @param cid
	 * @return
	 */
	public String getDietIdentical(Integer cid);

	/**
	 * 获得上周和本周测血糖任务的完成率
	 * 
	 * @param cid
	 * @return
	 */
	public String getBDTaskFillRate(Integer cid);

	/**
	 * 删除固定任务
	 * 
	 * @param id
	 */
	public void del(Integer id);

	/**
	 * 删除固定任务
	 * 
	 * @param id
	 */
	public void update(TimeLineTask lineTask);

	/**
	 * 获得任务
	 * 
	 * @param id
	 */
	public TimeLineTask getLineTaskById(Integer id);

	/**
	 * 删除固定任务
	 * 
	 * @param id
	 */
	public void updateFixTask(TimeLineTask lineTask);

	/**
	 * 更新用户新上传的服药提醒
	 * 
	 * @param cid
	 *            用户id
	 * @param ja
	 *            用户此次上传的服药提醒 ，有时间和药名
	 */
	public void updateClienttMedicineReminder(Integer cid,
			List<MedicineReminder> list);

	/**
	 * 根据用户上传的服药提醒，为用户添加服药提醒
	 * 
	 * @param reminder
	 * @return -1,-2：失败，0成功（小于0失败，大于等于0成功）
	 */
	public int addMedicineReminderTask(MedicineReminder reminder, Date date);

	/**
	 * 删除用户明天除服药提醒所有任务，用户用户做完风险评估重新生成第二天时间轴
	 * 
	 * @param cid
	 */
	public void deleteTaskByUpEvaluating(Integer cid);

	/**
	 * 添加任务
	 * 
	 * @param lineTask
	 */
	public void addClientCustomsTask(String tanks, Integer cid);

	/**
	 * 获得所有用户自定义添加的任务
	 * 
	 * @param cid
	 * @param softType
	 * @return
	 */
	public String getClientCustomsTask(Integer cid, Integer softType);

	/**
	 * 删除用户自定义的时间轴任务
	 * 
	 * @param cid
	 * @param taskId
	 * @return
	 */
	public void deleteClientCustomsTask(Integer cid, Integer taskId);

	/**
	 * 
	 * @param timeLineTask
	 * @return
	 */
	public void add(TimeLineTask timeLineTask);

	/**
	 * 查询用户今天有没有天气预报的提示 如果没有的话，根据经纬度查询天气，保存到时间轴
	 * 
	 * @param cid
	 *            cid
	 * @param longitude
	 *            精度
	 * @param latitude
	 *            纬度
	 * @param soft
	 *            软件
	 * @return 返回添加是否成功，如果添加成功返回2，失败返回3（接口用到，返回2时移动端刷新时间轴）
	 */
	public int addWeatherTask(Integer cid, double longitude, double latitude,
			Integer soft, String city, java.util.Map<String, String> map);

	/**
	 * 返回某个月的的任务完成率
	 * 
	 * @param cid
	 * @param mo
	 *            传入一个时间，查询当期月的信息
	 * @param stauts
	 *            状态 1 查询所有任务数量列表 2 查询已完成任务数量列表
	 * @return 返回任务完成率的列表
	 */
	public String getTaskIdentical(Integer cid, Date mo);

	public String getLineTaskWeather(String location,String city);
	
	/**
	 * 在时间轴上添加任务
	 * @param title 资讯标题
	 * @param id 资讯id
	 * @param push 是否推送信息
	 */
	public void addZiXunTask(String title, String id, boolean push) ;
}
