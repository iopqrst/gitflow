package com.bskcare.ch.vo.msport;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="m_sport_plan")
public class MSportPlan implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer clientId;
	
	/**计划距离*/
	private double distance;
	/**计划消耗卡路里*/
	private double calorie;
	/**计划步数*/
	private int steps;
	/**步长*/
	private double stepWidth;
	/**stepWidthSet*/
	private int stepWidthSet;
	/**计划天数*/
	private int planDay;
	/**天数标识0代表选择的月份，1代表手动输入的天数*/
	private int planDaySet;
	/**需要消耗的热量*/
	private double needCalorie;
	
	private Date createTime;
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public double getCalorie() {
		return calorie;
	}
	public void setCalorie(double calorie) {
		this.calorie = calorie;
	}
	public int getSteps() {
		return steps;
	}
	public void setSteps(int steps) {
		this.steps = steps;
	}
	public double getStepWidth() {
		return stepWidth;
	}
	public void setStepWidth(double stepWidth) {
		this.stepWidth = stepWidth;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	
	public int getStepWidthSet() {
		return stepWidthSet;
	}
	public void setStepWidthSet(int stepWidthSet) {
		this.stepWidthSet = stepWidthSet;
	}
	public int getPlanDay() {
		return planDay;
	}
	public void setPlanDay(int planDay) {
		this.planDay = planDay;
	}
	public int getPlanDaySet() {
		return planDaySet;
	}
	public void setPlanDaySet(int planDaySet) {
		this.planDaySet = planDaySet;
	}
	
	public double getNeedCalorie() {
		return needCalorie;
	}
	public void setNeedCalorie(double needCalorie) {
		this.needCalorie = needCalorie;
	}
	
	@Override
	public String toString() {
		return "MSportPlan [calorie=" + calorie + ", clientId=" + clientId
				+ ", createTime=" + createTime + ", distance=" + distance
				+ ", id=" + id + ", stepWidth="
				+ stepWidth + ", steps=" + steps + "]";
	}
}
