package com.bskcare.ch.vo.timeLine;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tg_timeline_sport")
public class TimeLineSport {

	public static final int STAUTS_OFF = 0;
	public static final int STAUTS_ON = 1;
	/**
	 * 正常
	 */
	public static final int overweight_ON = 1;
	/**
	 * 超重
	 */
	public static final int overweight_YES = 2;

	private Integer id;
	private String result;// 评测结果
	private Integer complication;// 并发症
	private String sportType;// 运动类型
	private Integer suitCalorie;// 适合消耗的热量
	private Integer maxCalorie;// 最大消耗热量
	private Integer softType;// 软件类型
	private Integer stauts;// 是否启用
	private String taskTime;// 任务提示时间
	private Integer overweight;// 是否超重

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Integer getComplication() {
		return complication;
	}

	public void setComplication(Integer complication) {
		this.complication = complication;
	}

	public String getSportType() {
		return sportType;
	}

	public void setSportType(String sportType) {
		this.sportType = sportType;
	}

	public Integer getSuitCalorie() {
		return suitCalorie;
	}

	public void setSuitCalorie(Integer suitCalorie) {
		this.suitCalorie = suitCalorie;
	}

	public Integer getMaxCalorie() {
		return maxCalorie;
	}

	public void setMaxCalorie(Integer maxCalorie) {
		this.maxCalorie = maxCalorie;
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

	public String getTaskTime() {
		return taskTime;
	}

	public void setTaskTime(String taskTime) {
		this.taskTime = taskTime;
	}

	public Integer getOverweight() {
		return overweight;
	}

	public void setOverweight(Integer overweight) {
		this.overweight = overweight;
	}

}
