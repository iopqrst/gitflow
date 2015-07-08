package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.bo.TaskListPage;
import com.bskcare.ch.dao.TaskListDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.TaskList;

@Repository("taskListDao")
@SuppressWarnings("unchecked")
public class TaskListDaoImpl extends BaseDaoImpl<TaskList> implements
		TaskListDao {
	/**
	 * 添加任务
	 */
	public void addTask(TaskList task) {
		if (task != null) {
			if (task.getTlid() == null) {
				this.add(task);
			} else {
				this.update(task);
			}
		}
	}

	/**
	 * 我的任务 status 任务状态，等于0是获得的是已完成的任务，1是未完成
	 */
/*
	public PageObject myTaskList(TaskList task, QueryInfo queryInfo,
			int status, List<String> types, ClientInfo clientInfo,
			QueryCondition condition) {
		List args = new ArrayList();
		String sql = "SELECT t.tlid, c.name as username, c.gender, c.age, c.mobile, p.name, "
				+ " c.bazzaarGrade, c.healthIndex, t.type,t.status, t.creationTime,t.accomplishTime,"
				+ " c.areaId , a.name as areanema ,t.accomplishTime,c.principalId,c.finishPercent,c.id,t.tparticulars, c.availableProduct "
				+ " from t_task_list as t JOIN (select * from t_clientinfo where 1=1";
		if (clientInfo != null) {
			if (!StringUtils.isEmpty(clientInfo.getName())) {
				sql += " and name like ?";
				args.add("%" + clientInfo.getName().trim() + "%");
			}
		}
		sql += " ) as c ON t.clientId=c.id "
				+ " LEFT JOIN t_product_level AS p ON p.id= c.levelId LEFT JOIN t_areainfo as a on c.areaId= a.id "
				+ " WHERE t.type != ? ";

		args.add(TaskList.TASK_TYPE_DINGSHI);
		if (task != null) {
			if (task.getReceiveUser() != null) {
				sql += " and  t.receiveUser= ? ";
				args.add(task.getReceiveUser());
			}
			if (task.getStatus() != null && task.getStatus() != -1) {
				sql += " and t.status=?";
				args.add(task.getStatus());
			}
		}
		if (types != null && types.size() >= 1) {
			sql += " and ( 1!=1 ";
			for (String type : types) {
				sql += " or t.type= ? ";
				args.add(type);
			}
			sql += ")";
		}
		// 未完成任务
		if (status == 1) {
			sql += " AND (t.status != ? and t.status != ?)";
		} else {
			// 已完成任务
			sql += " AND (t.status = ? or t.status = ?)";
		}
		args.add(TaskList.STATUS_STATUS_OVER);
		args.add(TaskList.STATUS_STATUS_OUT);
		// 完成时间筛选
		if (condition != null) {
			if (condition.getBeginTime() != null) {
				sql += " and accomplishTime >= ?";
				args.add(condition.getBeginTime());
			}
			if (condition.getEndTime() != null) {
				sql += " and accomplishTime <= ?";
				args.add(condition.getEndTime());
			}
		}
		System.out.println(sql);
		return this.queryObjectsBySql(sql, args.toArray(), queryInfo);
	}
*/
	
	public PageObject<TaskListPage> myTaskList(TaskList task, QueryInfo queryInfo,
			int status, List<String> types, ClientInfo clientInfo,
			QueryCondition condition) {
		List args = new ArrayList();
		String sql = "SELECT t.tlid, c.name as username, c.gender, c.age, c.mobile, c.nickName,"
				+ " c.bazzaarGrade, c.healthIndex, t.type,t.status, t.creationTime,t.accomplishTime,"
				+ " c.areaId , c.principalId,c.finishPercent,c.id as cid,t.tparticulars, c.availableProduct "
				+ " from t_task_list as t JOIN t_clientinfo c ON t.clientId=c.id WHERE t.type != ?";

		args.add(TaskList.TASK_TYPE_DINGSHI);

		if (clientInfo != null) {
			if (!StringUtils.isEmpty(clientInfo.getName())) {
				sql += " and c.name like ?";
				args.add("%" + clientInfo.getName().trim() + "%");
			}
			if (!StringUtils.isEmpty(clientInfo.getMobile())) {
				sql += " and c.mobile = ?";
				args.add(clientInfo.getMobile());
			}
		}
		
		if(condition != null){
			if(!StringUtils.isEmpty(condition.getBazzaarGradeQuery())){
				String bazz = condition.getBazzaarGradeQuery();
				String[] bazzStr = bazz.split(",");
				sql += " and (";
				if (bazzStr.length > 1) {
					for (int i = 0; i < bazzStr.length; i++) {
						sql += " c.bazzaarGrade = ?";

						args.add(bazzStr[i]);

						if (i != bazzStr.length - 1) {
							sql += " or";
						}
					}
				} else {
					sql += " c.bazzaarGrade = ?";
					args.add(bazzStr[0]);
				}
				sql += ")";
			}
		}

		if (task != null) {
			if (task.getReceiveUser() != null) {
				sql += " and  t.receiveUser= ? ";
				args.add(task.getReceiveUser());
			}
			if (task.getStatus() != null && task.getStatus() != -1) {
				sql += " and t.status=?";
				args.add(task.getStatus());
			}
		}
		if (types != null && types.size() >= 1) {
			sql += " and ( 1!=1 ";
			for (String type : types) {
				sql += " or t.type= ? ";
				args.add(type);
			}
			sql += ")";
		}
		// 未完成任务
		if (status == 1) {
			sql += " AND (t.status != ? and t.status != ?)";
		} else {
			// 已完成任务
			sql += " AND (t.status = ? or t.status = ?)";
		}
		args.add(TaskList.STATUS_STATUS_OVER);
		args.add(TaskList.STATUS_STATUS_OUT);
		// 完成时间筛选
		if (condition != null) {
			if (condition.getBeginTime() != null) {
				sql += " and t.accomplishTime >= ?";
				args.add(condition.getBeginTime());
			}
			if (condition.getEndTime() != null) {
				sql += " and t.accomplishTime <= ?";
				args.add(condition.getEndTime());
			}
		}
		System.out.println(sql);
		Map scalars = new LinkedHashMap();
		scalars.put("tlid", StandardBasicTypes.INTEGER);
		scalars.put("username", StandardBasicTypes.STRING);
		scalars.put("gender", StandardBasicTypes.INTEGER);
		scalars.put("age", StandardBasicTypes.INTEGER);
		scalars.put("mobile", StandardBasicTypes.STRING);
		scalars.put("nickName", StandardBasicTypes.STRING);
		scalars.put("bazzaarGrade", StandardBasicTypes.INTEGER);
		scalars.put("healthIndex", StandardBasicTypes.INTEGER);
		scalars.put("type", StandardBasicTypes.STRING);
		scalars.put("status", StandardBasicTypes.INTEGER);
		scalars.put("creationTime", StandardBasicTypes.TIMESTAMP);
		scalars.put("accomplishTime", StandardBasicTypes.TIMESTAMP);
		scalars.put("areaId", StandardBasicTypes.INTEGER);
		scalars.put("principalId", StandardBasicTypes.INTEGER);
		scalars.put("finishPercent", StandardBasicTypes.STRING);
		scalars.put("cid", StandardBasicTypes.INTEGER);
		scalars.put("tparticulars", StandardBasicTypes.STRING);
		scalars.put("availableProduct", StandardBasicTypes.STRING);
		return queryObjectsBySql(sql, null, scalars, args.toArray(), queryInfo, TaskListPage.class);
	}
	
	
	/**
	 * 我的自建任务列表
	 */
	public PageObject newTaskList(int userId, QueryInfo info) {
/*		String sql = " select t.tlid, c.`name` as username, c.gender, t.creationTime,t.timingSend , u.name "
				+ "FROM (t_clientinfo AS c left join  t_task_list AS t on c.id = t.clientId) left join t_userinfo as u on u.id= t.receiveUser "
				+ "WHERE t.creationUser=? and t.type = ? and t.status = ? ";
*/
		List args = new ArrayList();
		String sql = "select m.tlid,n.name userName,n.gender,m.creationTime,m.timingSend,m.`name` from " +
				"(select m.*,n.`name` from (select tlid,clientId,receiveUser,timingSend,creationTime from t_task_list where 1 = 1";
		
		if(userId != 0){
			sql += " and  creationUser = ?";
			args.add(userId);
		}
		sql += " and creationUser = receiveUser and type = ? and `status` = ?) m left join t_userinfo n on m.receiveUser = n.id) m" +
			   " left join (select id,name,gender from t_clientinfo) n" +
			   " on n.id = m.clientId order by m.creationTime asc";
		
		args.add(TaskList.TASK_TYPE_DINGSHI);
		args.add(TaskList.TASK_STATUS_OFF);
		return queryObjectsBySql(sql, args.toArray(), info);
	}

	/**
	 * 发布自定任务
	 */
	public int releaseSelfTask() {
		List args = new ArrayList();
		String sql = "UPDATE t_task_list set type=? where DATEDIFF(timingSend,NOW())=0 and type = ?";
		args.add(TaskList.TASK_TYPE_URGENCY);
		args.add(TaskList.TASK_TYPE_DINGSHI); //将今日预约的定时任务发送到相应的人身上
		return this.updateBySql(sql, args.toArray());
	}

	/**
	 * 获得任务
	 */
	public PageObject getTaskListById(int id, QueryInfo info) {
		String sql = "SELECT t2. NAME t2name, t1.tparticulars, t1.creationTime, t3.NAME t3name, t4.NAME t4name, t1.record, t1.accomplishTime FROM (( t_task_list t1 LEFT JOIN t_clientinfo t2 ON t2.id = t1.clientId ) LEFT JOIN t_userinfo t3 ON t3.id = t1.creationUser ) LEFT JOIN t_userinfo t4 ON t4.id = t1.receiveUser WHERE tlid = ?";
		return queryObjectsBySql(sql, id, info);
	}

	/**
	 * 根据任务id获得任务
	 */
	public TaskList getTask(int taskid) {
		return load(taskid);
	}

	/**
	 * 根据id获得任务 （历史任务页面展示）
	 */
	/**
	 * 更新任务
	 */
	public void updateTask(TaskList task) {
		this.update(task);
	}

	public void deleteTask(int id) {
		this.delete(id);

	}

	public void updateTaskById(Integer taskId) {
		String sql = "update t_task_list set status = ? where tlid = ?";
		List args = new ArrayList();
		args.add(TaskList.STATUS_STATUS_OVER);
		args.add(taskId);
		updateBySql(sql, args.toArray());
	}

	public int myTaskList(TaskList task, int status, List<String> types,
			ClientInfo clientInfo, QueryCondition condition) {
		List args = new ArrayList();
		String sql = "SELECT count(tlid)"
				+ " from t_task_list as t JOIN (select id, levelId, areaId from t_clientinfo where 1=1";
		if (clientInfo != null) {
			if (!StringUtils.isEmpty(clientInfo.getName())) {
				sql += " and name like ?";
				args.add("%" + clientInfo.getName().trim() + "%");
			}
		}
		sql += " ) as c ON t.clientId=c.id "
				+ " LEFT JOIN t_product_level AS p ON p.id= c.levelId "
				+ " WHERE t.type != ? ";

		args.add(TaskList.TASK_TYPE_DINGSHI);
		if (task != null) {
			if (task.getReceiveUser() != null) {
				sql += " and  t.receiveUser= ? ";
				args.add(task.getReceiveUser());
			}
			if (task.getStatus() != null && task.getStatus() != -1) {
				sql += " and t.status=?";
				args.add(task.getStatus());
			}
		}
		if (types != null && types.size() >= 1) {
			sql += " and ( 1!=1 ";
			for (String type : types) {
				sql += " or t.type= ? ";
				args.add(type);
			}
			sql += ")";
		}
		// 未完成任务
		if (status == 1) {
			sql += " AND (t.status != ? and t.status != ?)";
		} else {
			// 已完成任务
			sql += " AND (t.status = ? or t.status = ?)";
		}
		args.add(TaskList.STATUS_STATUS_OVER);
		args.add(TaskList.STATUS_STATUS_OUT);
		// 完成时间筛选
		if (condition != null) {
			if (condition.getBeginTime() != null) {
				sql += " and accomplishTime >= ?";
				args.add(condition.getBeginTime());
			}
			if (condition.getEndTime() != null) {
				sql += " and accomplishTime <= ?";
				args.add(condition.getEndTime());
			}
		}
		
		Object obj = findUniqueResultByNativeQuery(sql, args.toArray());
		if(obj != null && !obj.equals("")){
			return Integer.parseInt(obj.toString());
		}
		return 0;
	}

}
