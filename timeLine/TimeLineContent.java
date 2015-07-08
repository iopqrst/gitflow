package com.bskcare.ch.vo.timeLine;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 时间点任务内容
 */
@Entity
@Table(name = "tg_timeline_content")
public class TimeLineContent {
	/**
	 * 已付费
	 */
	public static final Integer ISPAY_YES = 1;
	/**
	 * 未付费
	 */
	public static final Integer ISPAY_NO = 2;
	/**
	 * 全部
	 */
	public static final Integer ISPAY_ALL = 3;
	/**
	 * 启用
	 */
	public static final Integer STAUTS_STAUTS = 1;
	/**
	 * 禁用
	 */
	public static final Integer STAUTS_FORBIDDEN = 2;
	/**
	 * 待审核
	 */
	public static final Integer STAUTS_WAIT = 3;
	private Integer id;
	private Integer conType; //任务类型 详情
	private Integer softType; // 软件类型
	private String content; //内容
	private Integer stauts;	//1，启用 2， 禁用 3，待审核（很久以前提出的用户自己加内容）
	private Integer isPay;//1付费用户  ， 2 未付费用户， 3 全部用户
	private String title;// 任务标题
	//常量在部分timelinerule中
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getConType() {
		return conType;
	}

	public void setConType(Integer conType) {
		this.conType = conType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getIsPay() {
		return isPay;
	}

	public void setIsPay(Integer isPay) {
		this.isPay = isPay;
	}

	public Integer getSoftType() {
		return softType;
	}

	public void setSoftType(Integer softType) {
		this.softType = softType;
	}

	public Integer getStauts() {
		return stauts;
	}

	public void setStauts(Integer stauts) {
		this.stauts = stauts;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
