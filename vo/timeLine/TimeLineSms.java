package com.bskcare.ch.vo.timeLine;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *  时间轴推送消息
 * @author Administrator
 *
 */
@Entity
@Table(name = "tg_timeline_sms")
public class TimeLineSms {
	
	/**
	 * 姓名替换符
	 */
	public static final String TCSHSHELL_NAME = "{name}";
	/**
	 * 日期替换符
	 */
	public static final String TCSHSHELL_DATE = "{date}";

	private Integer id;	
	private Integer clientType;  //卡类别
	private String content;	//推送内容
	private Integer iscycle;	//是否循环 1 循环   2  不循环
	private Integer cycle;	//第几天或者多少天一个循环  
	private String taskTime;	//时间轴提示时间
	private int rest;	//是否提醒 默认0 1，表示正常推送。 2表示周五周六的任务推辞道周日发送，
	private int softType; //软件类型
	private Date createDate; //创建时间
	private String title; //标题
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getIscycle() {
		return iscycle;
	}

	public void setIscycle(Integer iscycle) {
		this.iscycle = iscycle;
	}

	public Integer getCycle() {
		return cycle;
	}

	public void setCycle(Integer cycle) {
		this.cycle = cycle;
	}

	public String getTaskTime() {
		return taskTime;
	}

	public void setTaskTime(String taskTime) {
		this.taskTime = taskTime;
	}

	public int getRest() {
		return rest;
	}

	public void setRest(int rest) {
		this.rest = rest;
	}

	public int getSoftType() {
		return softType;
	}

	public void setSoftType(int softType) {
		this.softType = softType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
}
