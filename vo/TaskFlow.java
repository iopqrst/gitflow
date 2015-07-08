package com.bskcare.ch.vo;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 任务流程表
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_task_flow")
public class TaskFlow {

	/**
	 * 用户等级:1 vip(只要是付费用户)
	 */
	public static final int CLIENTTYPE_VIP = 1;
	/**
	 * 用户等级:2 普通用户 未购买服务的用户
	 */
	public static final int CLIENTTYPE_COMMON = 3;
	/**
	 * 用户等级:5 血糖高管 平安卡
	 */
	public static final int CLIENTTYPE_PINGAN = 5;
	/**
	 * 用户等级:6 血糖高管 开心卡
	 */
	public static final int CLIENTTYPE_KAIXIN = 6;
	/**
	 * 是否循环：0 不循环，（从注册开始算）
	 */
	public static final int ISCYCLE_LOGIN_NO = 0;
	/**
	 * 是否循环：1:'是（从注册开始算）
	 */
	public static final int ISCYCLE_LOGIN_YES = 1;
	/**
	 * 是否循环：2:'否（从激活卡开始算）
	 */
	public static final int ISCYCLE_VIP_NO = 2;
	/**
	 * 是否循环：3:'是（从激活卡开始算）
	 */
	public static final int ISCYCLE_VIP_YES = 3;

	private Integer id;
	private Integer clientType; // 用户类型/1 vip 3：普通用户 5：平安卡 6开心卡
	private Integer levelId; // 用户等级
	private Integer healthLevel; // 用户健康等级
	private Integer bazzaarGrade; // 用户市场评分
	private Integer intervals; // 任务发布时间
	private Integer cycle; // 循环周期
	private Integer iscycle; // 是否循环0:'否（从注册开始算）',1:'是（从注册开始算）',2:'否（从激活卡开始算）',3:'是（从激活卡开始算）
	private Integer roleId; // 用户角色id
	private String taskTitle; // 标题
	private String particulars;// 内容

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getClientType() {
		return clientType;
	}

	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}

	public Integer getLevelId() {
		return levelId;
	}

	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}

	public Integer getHealthLevel() {
		return healthLevel;
	}

	public void setHealthLevel(Integer healthLevel) {
		this.healthLevel = healthLevel;
	}

	public Integer getBazzaarGrade() {
		return bazzaarGrade;
	}

	public void setBazzaarGrade(Integer bazzaarGrade) {
		this.bazzaarGrade = bazzaarGrade;
	}

	public Integer getIntervals() {
		return intervals;
	}

	public void setIntervals(Integer intervals) {
		this.intervals = intervals;
	}

	public Integer getCycle() {
		return cycle;
	}

	public void setCycle(Integer cycle) {
		this.cycle = cycle;
	}

	public Integer getIscycle() {
		return iscycle;
	}

	public void setIscycle(Integer iscycle) {
		this.iscycle = iscycle;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getTaskTitle() {
		return taskTitle;
	}

	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}

	public String getParticulars() {
		return particulars;
	}

	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}

	@Override
	public String toString() {
		return "TaskFlow [id=" + id + ", clientType=" + clientType
				+ ", levelId=" + levelId + ", healthLevel=" + healthLevel
				+ ", bazzaarGrade=" + bazzaarGrade + ", intervals=" + intervals
				+ ", cycle=" + cycle + ", iscycle=" + iscycle + ", roleId="
				+ roleId + ", taskTitle=" + taskTitle + ", particulars="
				+ particulars + "]";
	}

}
