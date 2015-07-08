package com.bskcare.ch.vo.msport;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="m_sport")
public class MSport implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer clientId;
	/**步数**/
	private int steps;
	/**卡路里**/
	private double calorie;
	/**距离**/
	private double distance;
	/**深睡眠时间**/
	private double deepSleep;
	/**浅睡眠时间**/
	private double lightSleep;
	/**极浅睡眠时间**/
	private double lightestSleep;
	/**差睡眠时间**/
	private double poorSleep;
	/**
	 * 计划步数
	 */
	private int stepPlan;
	/**
	 * 测试时间
	 */
	private Date testDate;
	/**
	 * 上传时间
	 */
	private Date uploadDate;
	
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
	public int getSteps() {
		return steps;
	}
	public void setSteps(int steps) {
		this.steps = steps;
	}
	public double getCalorie() {
		return calorie;
	}
	public void setCalorie(double calorie) {
		this.calorie = calorie;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public double getDeepSleep() {
		return deepSleep;
	}
	public void setDeepSleep(double deepSleep) {
		this.deepSleep = deepSleep;
	}
	public double getLightSleep() {
		return lightSleep;
	}
	public void setLightSleep(double lightSleep) {
		this.lightSleep = lightSleep;
	}
	public double getLightestSleep() {
		return lightestSleep;
	}
	public void setLightestSleep(double lightestSleep) {
		this.lightestSleep = lightestSleep;
	}
	public double getPoorSleep() {
		return poorSleep;
	}
	public void setPoorSleep(double poorSleep) {
		this.poorSleep = poorSleep;
	}
	public int getStepPlan() {
		return stepPlan;
	}
	public void setStepPlan(int stepPlan) {
		this.stepPlan = stepPlan;
	}
	public Date getTestDate() {
		return testDate;
	}
	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	@Override
	public String toString() {
		return "MSport [calorie=" + calorie + ", clientId=" + clientId
				+ ", deepSleep=" + deepSleep + ", distance=" + distance
				+ ", id=" + id + ", lightSleep=" + lightSleep
				+ ", lightestSleep=" + lightestSleep + ", poorSleep="
				+ poorSleep + ", steps=" + steps + ", testDate=" + testDate
				+ ", uploadDate=" + uploadDate + "]";
	}
	
}

