package com.bskcare.ch.vo;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 任务类
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_task_list")
public class TaskList {

	/**
	 * 任务类型1：非紧急任务
	 */
	public static final int TASK_TYPE_URGENCY = 1;
	/**
	 * 任务类型2：紧急任务
	 */
	public static final int TASK_TYPE_NONURGENCY = 2;
	/**
	 * 任务类型 3: 定时发送的任务
	 */
	public static final int TASK_TYPE_DINGSHI = 3;
	/**
	 * 任务类型 4:任务类型倒计时任务
	 */
	public static final Integer TASK_TYPE_COUNTDOWN= 4;
	/**
	 * 任务类型 5：健康报告任务
	 */
	public static final int TASK_TYPE_REPORT = 5;
	/**
	 * 任务状态 0:未完成任务
	 */
	public static final int TASK_STATUS_OFF = 0;
	/**
	 * 任务状态 1: 已完成的的任务
	 */
	public static final int STATUS_STATUS_OVER = 1;
	/**
	 * 任务状态 4: 超时完成的的任务
	 */
	public static final int STATUS_STATUS_OUT = 4;
	
	
	/**
	 *  任务状态未处理
	 *  STATUS = 0
	 */
	public static final Integer TASK_STATUS_UNTREATED = 0;
	/**
	 * 任务状态已处理
	 * STATUS = 1
	 */
	public static final Integer TASK_STATUS_PROCESSED= 1;
	/**
	 * 任务状态超时完成
	 * STATUS = 4
	 */
	public static final Integer TASK_STATUS_OUTTIMEDISPOSE= 4;
	
	
	private Integer tlid;               //任務id
	private Integer clientId;        //客户id
	private String tparticulars;    //任务详情
	private Integer type;               //任务类型 1 非紧急2 紧急3定时4计时  5健康报告任务
	private Date countDown;         //倒计时
	private Integer creationUser;       //创建人ID
	private Date creationTime;      //创建时间
	private Integer receiveUser;        //接受人ID
	private Integer status;             //进程状态（0：未处理， 1：已处理 )
	private String record;          //任务记录
	private Date accomplishTime;    //完成时间
	private Integer accomplishUser;     //完成人ID
	private Date timingSend;        //定时任务发送时间
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tlid")
	public Integer getTlid() {
		return tlid;
	}

	public void setTlid(Integer tlid) {
		this.tlid = tlid;
	}
	

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public String getTparticulars() {
		return tparticulars;
	}

	public void setTparticulars(String tparticulars) {
		this.tparticulars = tparticulars;
	}

	public Integer getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getCountDown() {
		return countDown;
	}

	public void setCountDown(Date countDown) {
		this.countDown = countDown;
	}

	public Integer getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(int creationUser) {
		this.creationUser = creationUser;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Integer getReceiveUser() {
		return receiveUser;
	}

	public void setReceiveUser(int receiveUser) {
		this.receiveUser = receiveUser;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public Date getAccomplishTime() {
		return accomplishTime;
	}

	public void setAccomplishTime(Date accomplishTime) {
		this.accomplishTime = accomplishTime;
	}

	public Integer getAccomplishUser() {
		return accomplishUser;
	}

	public void setAccomplishUser(Integer accomplishUser) {
		this.accomplishUser = accomplishUser;
	}

	public Date getTimingSend() {
		return timingSend;
	}

	public void setTimingSend(Date timingSend) {
		this.timingSend = timingSend;
	}

}
