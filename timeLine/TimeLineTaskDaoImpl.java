package com.bskcare.ch.dao.impl.timeLine;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.timeLine.TimeLineTaskDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.timeLine.TimeLineRule;
import com.bskcare.ch.vo.timeLine.TimeLineTask;

@SuppressWarnings("unchecked")
@Repository
public class TimeLineTaskDaoImpl extends BaseDaoImpl<TimeLineTask> implements
		TimeLineTaskDao {

	public List<TimeLineTask> queryTimeLineTask(TimeLineTask task) {
		return null;
	}

	public List<TimeLineTask> queryListToNoLogin() {
		String hql = "from TimeLineTask where 1=1 ";
		ArrayList args = new ArrayList();
		// 未登录用户只查询taskType是全部或未登录类型
		hql += "and  taskType = ? order by taskTime asc ";
		args.add(TimeLineTask.TASKTYPE_NOLOGIN);
		return executeFind(hql, args.toArray());
	}

	public PageObject<TimeLineTask> queryListToNoLogin(TimeLineTask lineTask,
			QueryInfo info) {
		//查询未登录用户能看到的任务，做页面显示
		String hql = "from TimeLineTask where clientId is null ";
		ArrayList args = new ArrayList();
		if (lineTask != null) {
			if (lineTask.getTaskType() != null && lineTask.getTaskType() != 0) {
				hql += "and taskType = ?";
				args.add(lineTask.getTaskType());
			} else {
				hql += "and (taskType = ? or taskType = ? or taskType = ?)";
				args.add(TimeLineTask.TASKTYPE_ALL);
				args.add(TimeLineTask.TASKTYPE_NOLOGIN);
				args.add(TimeLineTask.TASKTYPE_NOTHAVE);
			}
			if (lineTask.getSoftType() != null && lineTask.getSoftType() != 0) {
				hql += "and softType = ?";
				args.add(lineTask.getSoftType());
			}
		} else {
			hql += "and (taskType = ? or taskType = ? or taskType = ?)";
			args.add(TimeLineTask.TASKTYPE_ALL);
			args.add(TimeLineTask.TASKTYPE_NOLOGIN);
			args.add(TimeLineTask.TASKTYPE_NOTHAVE);
		}
		return queryObjects(hql, args.toArray(), info);
	}

	public List<TimeLineTask> queryList(Integer cid, Integer day, int evalStatus) {
		String hql = "from TimeLineTask where 1=1 ";
		ArrayList args = new ArrayList();
		if (evalStatus == TimeLineTask.TASKTYPE_NOTHAVE) {
			hql += "and  taskType = ?  ";
			args.add(TimeLineTask.TASKTYPE_NOTHAVE);
		} else {
			hql += " and taskType = ?   ";
			args.add(TimeLineTask.TASKTYPE_ALL);
		}
		hql += " or ( clientId = ? and (( softType is null or softType = ? )and ( resetType  != ? ";
		args.add(cid);
		args.add(Constant.SOFT_GUAN_XUE_TANG);
		args.add(TimeLineTask.RESETTYPE_OFF);
		if (day == null) {
			hql += " and DATEDIFF (taskDate , NOW()) = ? ";
			args.add(TimeLineTask.TODAY);
		} else {
			if (day == 1) { //查询今天
				hql += " and DATEDIFF (taskDate , NOW()) = ? ";
				args.add(TimeLineTask.TODAY);
			} else if (day == 0) {//昨天
				hql += " and DATEDIFF(taskDate , NOW()) = ? ";
				args.add(TimeLineTask.YESTERDAY);
			} else if (day == 2) {//明天
				hql += " and DATEDIFF(taskDate , NOW()) = ? ";
				args.add(TimeLineTask.TOMORROW);
			}

		}
		hql += ") or resetType  = ?  ) ";
		args.add(TimeLineTask.RESETTYPE_TEMPORARY);
		hql += " and(  resetType  = ? or resetType  = ? or resetType  = ? )";
		args.add(TimeLineTask.RESETTYPE_AUTO);
		args.add(TimeLineTask.RESETTYPE_TEMPORARY);
		args.add(TimeLineTask.RESETTYPE_MANUAL);
		hql += ")  ";
		hql += " order by taskTime asc ";
		return executeFind(hql, args.toArray());
	}

	/**
	 * 
	 * @param cid
	 * @param meal餐次
	 * @param food食物
	 * @return 次数
	 */
	public int queryCountFood(Integer cid, Integer meal, String food) {
		ArrayList args = new ArrayList();
		// String sql =
		// "SELECT COUNT(1) from tg_timeline_task where clientId = ? and TO_DAYS(NOW()) - TO_DAYS(taskDate) <= 7 and content like '?'";//查询七天
		String sql = "SELECT COUNT(1) from tg_timeline_task where clientId = ? and YEARWEEK(DATE_ADD(taskDate, INTERVAL -1 DAY)) = YEARWEEK(DATE_ADD(now(), INTERVAL -1 DAY)) and content like ?";
		args.add(cid);
		args.add("%" + food + "%");
		if (meal != null) {
			sql += " and conType = ? ";
			args.add(meal);
		} else {
			sql += " and conType in (1,2,3,4,5) "; // 查询用户一周内是否吃过某种食物
		}
		Object obj = findUniqueResultByNativeQuery(sql, args.toArray());
		if (obj != null) {
			return Integer.parseInt(obj.toString());
		}
		return 0;
	}

	public void overTask(Integer cid, Integer taskId, Integer stauts) {
		ArrayList args = new ArrayList();
		String sql = "";
		if (stauts == null) {
			TimeLineTask task = load(taskId);
			if (task != null) {
				if (task.getConType() != null
						&& task.getConType() == TimeLineRule.CONTYPE_RPTREMIND) {//如果是报告任务
					sql = "update tg_timeline_task set resetType = ? where id = ?  and clientId = ? and conType NOT in (1,2,3) ";// 将报告任务改成临时状态
					args.add(TimeLineTask.RESETTYPE_MANUAL);
				} else {
					sql = "update tg_timeline_task set stauts = ? where id = ?  and clientId = ? and conType NOT in (1,2,3) ";// 其他任务直接改成已完成
					args.add(TimeLineTask.STAUTS_YES);
				}
				args.add(taskId);
				args.add(cid);
			}

		}
		if (stauts != null) { //如果状态不等于null，说明是饮食任务
			sql = "update tg_timeline_task set stauts = ? where id = ?  and clientId = ? and conType in (1,2,3)";// 修改饮食任务符合程度
			args.add(stauts);
			args.add(taskId);
			args.add(cid);
		}
		this.updateBySql(sql, args.toArray());
	}

	public int dietCount(Integer cid, Date beginDate,Date endDate, Integer stauts, Integer type) {
		ArrayList args = new ArrayList();
		String sql = "SELECT COUNT(id) FROM tg_timeline_task where 1 = 1 ";
		if (beginDate != null && endDate != null) {
				sql += " and  taskDate > ? and taskDate < ? ";
				args.add(beginDate);
				args.add(endDate);
		}
		sql += " and clientId = ? ";
		args.add(cid);
		if (type != null) {
			if (type == 1) {// 饮食
				sql += " and conType in (1,2,3)";   //只查询饮食任务
				if (stauts != null && stauts == 1) {
					sql += " and (stauts = ? or stauts = ?) ";  //如果传1 查询所有符合和基本符合的任务
					args.add(TimeLineTask.STAUTS_ALMOST);
					args.add(TimeLineTask.STAUTS_CONFORM);
				} else if (stauts != null && stauts == 2) {
					sql += " and stauts = ?";
					args.add(TimeLineTask.STAUTS_INCONFORMITY);
				}else{
					sql += " and stauts != ? ";
					args.add(TimeLineTask.STAUTS_NO);
				}
			}
			if (type == 2) { // 测血糖
				sql += " and conType = ?";
				args.add(TimeLineRule.CONTYPE_TESTBG);
			}
			if (type == 3) { // 测血压
				sql += " and conType = ?";
				args.add(TimeLineRule.CONTYPE_TESTBP);
			}
		}
		List list = this.executeNativeQuery(sql, args.toArray());
		return Integer.parseInt(list.get(0).toString());
	}

	public List getDietIdentical(Integer cid, Integer mo) {
		String sql = "SELECT COUNT(id), taskDate FROM tg_timeline_task WHERE"
				+ " PERIOD_DIFF( date_format(now(), '%Y%m'), date_format(taskDate, '%Y%m') ) <= ?"
				+ " AND (stauts = ? OR stauts = ?) and clientId = ? GROUP BY date_format(taskDate, '%Y%m%d')";
		ArrayList args = new ArrayList();
		args.add(mo - 1);
		args.add(TimeLineTask.STAUTS_ALMOST);
		args.add(TimeLineTask.STAUTS_CONFORM);
		args.add(cid);
		return this.executeNativeQuery(sql, args.toArray());
	}

	public int todayCount(Integer cid, Integer stauts) {
		ArrayList args = new ArrayList();
		String sql = " SELECT count(id) FROM tg_timeline_task where "
				+ "DATE_FORMAT(taskDate,'%Y-%m-%d')= DATE_FORMAT(NOW(),'%Y-%m-%d') and clientId = ? and openMode IN (1, 3, 4) ";
		args.add(cid);
		if (stauts != null) {
			sql += "and stauts != ?";
			args.add(stauts);
		}
		List list = this.executeNativeQuery(sql, args.toArray());
		return Integer.parseInt(list.get(0).toString());
	}

	public void deleteTask(Integer cid, Date beginDate, Date endDate,
			Integer conType) {
		if (cid == null || conType == null)
			return;
		ArrayList args = new ArrayList();
		String sql = "DELETE from  tg_timeline_task WHERE clientId = ? AND conType = ? ";
		args.add(cid);
		args.add(conType);
		if (beginDate != null) {
			sql += " and taskDate > ? ";
			args.add(beginDate);
		}
		if (endDate != null) {
			sql += " and taskDate < ? ";
			args.add(endDate);
		}
		this.deleteBySql(sql, args.toArray());
	}

	public void updateTaskReset() {
		ArrayList args = new ArrayList();
		String sql = "update tg_timeline_task set resetType = ? where resetType = ?";
		args.add(TimeLineTask.RESETTYPE_OFF);
		args.add(TimeLineTask.RESETTYPE_MANUAL);
		this.updateBySql(sql, args.toArray());
	}
	
	public int deleteTaskByUpevaluating(Integer cid, Integer conType,Integer source) {
		String sql = "DELETE FROM tg_timeline_task where DATEDIFF(taskDate , NOW()) = 0 ";
		ArrayList args = new ArrayList();
		if (cid != null) {
			sql += " and clientId = ? ";
			args.add(cid);
		}
		if (conType != null) {
			sql += " and conType != ? ";
			args.add(conType);
		}
//		if(source!=null&&source == 2){
//			//不删除医生和用户手动添加的
//			sql += " and conType != ? and conType != ? ";
//			args.add(TimeLineRule.CONTYPE_USERCUSTOM);
//			args.add(TimeLineRule.CONTYPE_DOCCUSTOM);
//			
//		}
//		if(source!=null&&source == 1){
//			//不删除医生和用户手动添加的
//			sql += " and conType != ? and conType != ? and conType != ? and conType != ? ";
//			args.add(TimeLineRule.CONTYPE_USERCUSTOM);
//			args.add(TimeLineRule.CONTYPE_DOCCUSTOM);
//			//不删除天气预报
//			args.add(TimeLineRule.CONTYPE_WEATHER);
//			//不删除推送
//			args.add(TimeLineRule.CONTYPE_SMS);
//		}
		if(source!=null&&(source == 2||source == 1)){
			//不删除医生和用户手动添加的
			sql += " and conType != ? and conType != ? ";
			args.add(TimeLineRule.CONTYPE_USERCUSTOM);
			args.add(TimeLineRule.CONTYPE_DOCCUSTOM);
			if(source == 1){
				//不删除天气预报 和 推送
				sql += " and conType != ? and conType != ? ";
				args.add(TimeLineRule.CONTYPE_WEATHER);
				args.add(TimeLineRule.CONTYPE_SMS);
			}
		}
		
		return this.deleteBySql(sql, args.toArray());
	}

	public void deleteClientCustomsTask(Integer cid, Integer taskId) {
		String sql = "DELETE FROM tg_timeline_task where conType = ?";
		ArrayList args = new ArrayList();
		args.add(TimeLineRule.CONTYPE_USERCUSTOM);
		if (cid != null) {
			sql += " and clientId = ? ";
			args.add(cid);
		}
		if (taskId != null) {
			sql += " and id = ? ";
			args.add(taskId);
		}
		this.deleteBySql(sql, args.toArray());
	}

	public List<TimeLineTask> getClientCustomsTask(Integer cid, Integer softType) {
		String hql = "from TimeLineTask where 1=1 ";
		ArrayList args = new ArrayList();
		// 未登录用户只查询taskType是全部或未登录类型
		hql += "and clientId = ? and softType = ? and conType = ? order by taskTime asc ";
		args.add(cid);
		args.add(softType);
		args.add(TimeLineRule.CONTYPE_USERCUSTOM);
		return executeFind(hql, args.toArray());
	}

	public int queryTaskIsHas(Integer cid, Integer conType) {
		ArrayList args = new ArrayList();
		String sql = " SELECT count(id) FROM tg_timeline_task where "
				+ "DATE_FORMAT(taskDate,'%Y-%m-%d')= DATE_FORMAT(NOW(),'%Y-%m-%d') and clientId = ? ";
		args.add(cid);
		if(conType!=null){
			sql+=" and conType = ? ";
			args.add(conType);	
		}
		List list = this.executeNativeQuery(sql, args.toArray());
		return Integer.parseInt(list.get(0).toString());
	}

	public List getTaskIdentical(Integer cid,Date beginDate,Date enddateDate,Integer stauts) {
		ArrayList args = new ArrayList();
		String sql = "SELECT COUNT(id), taskDate FROM tg_timeline_task WHERE"
			+ " taskDate > ? and taskDate < ? ";
		args.add(beginDate);
		args.add(enddateDate);
			if(stauts!=null){
				sql+=" AND (stauts != ?) ";
				args.add(stauts);
			}
			sql += " and clientId = ? and openMode IN (1, 3, 4)  GROUP BY date_format(taskDate, '%Y%m%d')";
	args.add(cid);
	return this.executeNativeQuery(sql, args.toArray());
	}

	public List<TimeLineTask> queryGlobalTask(Integer conType) {
		String hql = "from TimeLineTask where clientId is null" ;
		ArrayList args = new ArrayList();
		if(null != conType) {
			hql += " and conType = ?";
			args.add(conType);
		}
		return executeFind(hql, args.toArray());
	}
}
