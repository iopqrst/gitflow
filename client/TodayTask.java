package com.bskcare.ch.vo.client;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 今日任务
 */
@Entity
@Table(name = "t_today_task")
@SuppressWarnings("unused")
public class TodayTask implements Serializable {

	public static final long serialVersionUID = 1L;
	/**
	 * 启用
	 */
	public static final int STATUS_YES = 0;
	/**
	 * 禁用
	 */
	private static final int STATUS_NO = 1;

	private Integer id;
	private Integer clientId;// 当前用户id
	private Integer userId;// 管理员id
	private String task;// 任务内容
	private Integer status;// 任务状态
	private Date createTime;// 创建时间

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
