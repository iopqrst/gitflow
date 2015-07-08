package com.bskcare.ch.dao.timeLine;

import java.util.Date;
import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.timeLine.TimeLineTask;

public interface TimeLineTaskDao extends BaseDao<TimeLineTask> {
	/**
	 * 返回对应时间轴列表
	 * @param task
	 * @return
	 */
	public List<TimeLineTask> queryTimeLineTask(TimeLineTask task);
	
	/**
	 * 把一直显示状态的任务从临时状态改成关闭
	 */
	public void updateTaskReset();

	/**
	 * 返回没有登录的用户的固定时间轴
	 * 
	 * @return
	 */
	public List<TimeLineTask> queryListToNoLogin();
	/**
	 * 查询用户时间轴 
	 * @param cid id
	 * @param day 0昨天，1今天，2明天
	 * @param evalStatus 5，查询用户第一天时间轴  ，6：查询登录未上传评测的用户的时间轴
	 * @return
	 */
	public List<TimeLineTask> queryList(Integer cid, Integer day,int evalStatus);
	/**
	 * 分页查询固定任务，页面展示用
	 * @param lineTask
	 * @param info
	 * @return
	 */
	public PageObject<TimeLineTask> queryListToNoLogin(TimeLineTask lineTask,
			QueryInfo info);

	/**
	 * 查询用户食物一周内出现次数
	 * 
	 * @param cid
	 * @param meal
	 *            餐次
	 * @param food
	 *            食物
	 * @return
	 */
	public int queryCountFood(Integer cid, Integer meal, String food);

	/**
	 * 完成任务
	 * 
	 * @param cid
	 * @param taskId
	 */
	public void overTask(Integer cid, Integer taskId, Integer stauts);
	/**
	 * 返回任务完成数量
	 * @param cid
	// * @param week	0本周，1上周,null全部任务
	 * @param status  饮食任务：1符合和基本符合次数   2 ： 不符合次数（没点不算）
	 * @param type	1 饮食任务  , 2血糖任务, 3血压任务,null 全部任务
	 * @return
	 */
	public int dietCount(Integer cid,Date beginDate,Date endDate,Integer stauts,Integer type);
	/**
	 * 返回最近几月的的饮食符合情况
	 * @param cid
	 * @param mo 月数
	 * @return
	 */
	public List getDietIdentical(Integer cid,Integer mo);
	/**
	 * 返回任务完成数量
	 * @param cid
	 * @param status  1,已完成的任务 ，null全部任务
	 * @return
	 */
	public int todayCount(Integer cid,Integer stauts);
	/**
	 * 删除用户某个类型的任务 用户id或任务类型为空不执行
	 * @param cid  用户id   *必填
	 * @param conType 任务类型 *必填
	 * @param begindate 开始时间
	 * @param endDate 结束时间
	 */
	public void deleteTask(Integer cid , Date beginDate,Date endDate,Integer conType);
	/**
	 * 删除今天天时间轴
	 * @param cid
	 * @param conType  不删除的任务类型        11 服药提醒
	 * @param source 1 不删除服药提醒，推送，天气预报,不删除医生和用户手动添加的，为用户重新生成时间轴时使用（上传评测、升级vip时调用）
	 *  2  不删除医生和用户手动添加的,用户定时生成时间轴的时候调用
	 * @return 影响条数
	 */
	public int deleteTaskByUpevaluating(Integer cid,Integer conType,Integer source);
	/**
	 * 获得所有用户自定义添加的任务
	 * @param cid
	 * @param softType
	 * @return
	 */
	public List<TimeLineTask> getClientCustomsTask(Integer cid,Integer softType);
	/**
	 * 删除用户自定义的时间轴任务
	 * @param cid
	 * @param taskId
	 * @return
	 */
	public void deleteClientCustomsTask(Integer cid,Integer taskId);
	/**
	 * 查询用户今日某个任务类型任务的数量
	 * @param cid
	 * @return
	 */
	public int queryTaskIsHas(Integer cid,Integer conType);
	/**
	 * 返回时间段每天的任务完成率
	 * @param cid
	 * @param stauts 状态  null 查询所有任务数量列表  2 查询已完成任务数量列表
	 * @return
	 */
	public List getTaskIdentical(Integer cid,Date beginDate,Date enddateDate,Integer stauts);

	/**
	 * 查询全局的任务
	 * @param conType 类型
	 */
	public List<TimeLineTask> queryGlobalTask(Integer conType);
}
