package com.bskcare.ch.vo.timeLine;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户自定时间轴时间（部分）
 * @author Administrator
 *
 */
@Entity
@Table(name = "tg_timeline_rule_custom")
public class TimeLineRuleCustom {
	private Integer id;
	private Integer clientId; //用户id
	private Integer conType; // 任务类型
	private String taskTime; // 任务推送时间字符串，格式时:分
	private Integer status;	//状态 1 启用 2 禁用 3 用户选择不使用
	private Integer softType; //软件类型
	/**
	 * 启用
	 */
	public static final Integer STAUTS_STAUTS = 1;
	/**
	 * 禁用
	 */
	public static final Integer STAUTS_FORBIDDEN = 2;
	/**
	 * 用户选择不使用
	 */
	public static final Integer STAUTS_CALLOFF = 3;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
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
	public Integer getConType() {
		return conType;
	}
	public void setConType(Integer conType) {
		this.conType = conType;
	}
	public String getTaskTime() {
		return taskTime;
	}
	public void setTaskTime(String taskTime) {
		this.taskTime = taskTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getSoftType() {
		return softType;
	}
	public void setSoftType(Integer softType) {
		this.softType = softType;
	}
	
	
	
}
