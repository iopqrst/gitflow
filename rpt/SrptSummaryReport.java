package com.bskcare.ch.vo.rpt;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 建议报告详情
 */
@Entity
@Table(name = "srpt_summary_report")
public class SrptSummaryReport implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer srptId; // 建议报告基本信息id
	private Integer clientId; // 用户Id
	private String intro; // 身体状况简介
	private String singleData;// 监测数据说明(简单方案中）
	private String allData; // 完整方案中的数据监测值说明
	private String eatingMore;// 多吃
	private String eatingLess;// 少吃
	private String sportOpenings;// 运动方面开篇
	private String sportType;// 运动方式(完整报告中）
	private String bestSportType;// 最佳运动方式（简要报告中）
	private String sportTimeSlot;// 运动时间带（最佳运动时间）
	private String sportIntensity;// 运动强度
	private String sportPlanning;// 运动时间长短
	private String keeping;// 心理原则-应保持的
	private String avoid;// 心理原则-应避免的
	private String shortTip;// 温馨提示（精简的）
	private String totalTip;// 温馨提示（全）
	private String dietPrinciple;// 日常原则
	private String target;// 阶段目标
	private String principle; //饮食原则

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSrptId() {
		return srptId;
	}

	public void setSrptId(Integer srptId) {
		this.srptId = srptId;
	}

	public String getIntro() {
		return intro;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getSingleData() {
		return singleData;
	}

	public void setSingleData(String singleData) {
		this.singleData = singleData;
	}

	public String getAllData() {
		return allData;
	}

	public void setAllData(String allData) {
		this.allData = allData;
	}

	public String getEatingMore() {
		return eatingMore;
	}

	public void setEatingMore(String eatingMore) {
		this.eatingMore = eatingMore;
	}

	public String getEatingLess() {
		return eatingLess;
	}

	public void setEatingLess(String eatingLess) {
		this.eatingLess = eatingLess;
	}

	public String getSportOpenings() {
		return sportOpenings;
	}

	public void setSportOpenings(String sportOpenings) {
		this.sportOpenings = sportOpenings;
	}

	public String getSportType() {
		return sportType;
	}

	public void setSportType(String sportType) {
		this.sportType = sportType;
	}

	public String getBestSportType() {
		return bestSportType;
	}

	public void setBestSportType(String bestSportType) {
		this.bestSportType = bestSportType;
	}

	public String getSportTimeSlot() {
		return sportTimeSlot;
	}

	public void setSportTimeSlot(String sportTimeSlot) {
		this.sportTimeSlot = sportTimeSlot;
	}

	public String getSportIntensity() {
		return sportIntensity;
	}

	public void setSportIntensity(String sportIntensity) {
		this.sportIntensity = sportIntensity;
	}

	public String getKeeping() {
		return keeping;
	}

	public void setKeeping(String keeping) {
		this.keeping = keeping;
	}

	public String getAvoid() {
		return avoid;
	}

	public void setAvoid(String avoid) {
		this.avoid = avoid;
	}

	public String getShortTip() {
		return shortTip;
	}

	public void setShortTip(String shortTip) {
		this.shortTip = shortTip;
	}

	public String getTotalTip() {
		return totalTip;
	}

	public void setTotalTip(String totalTip) {
		this.totalTip = totalTip;
	}

	public String getSportPlanning() {
		return sportPlanning;
	}

	public void setSportPlanning(String sportPlanning) {
		this.sportPlanning = sportPlanning;
	}

	public String getDietPrinciple() {
		return dietPrinciple;
	}

	public void setDietPrinciple(String dietPrinciple) {
		this.dietPrinciple = dietPrinciple;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	public String getPrinciple() {
		return principle;
	}

	public void setPrinciple(String principle) {
		this.principle = principle;
	}

	@Override
	public String toString() {
		return "SrptSummaryReport [allData=" + allData + ", avoid=" + avoid
				+ ", bestSportType=" + bestSportType + ", dietPrinciple="
				+ dietPrinciple + ", eatingLess=" + eatingLess
				+ ", eatingMore=" + eatingMore + ", id=" + id + ", intro="
				+ intro + ", keeping=" + keeping + ", shortTip=" + shortTip
				+ ", singleData=" + singleData + ", sportIntensity="
				+ sportIntensity + ", sportOpenings=" + sportOpenings
				+ ", sportTimeSlot=" + sportTimeSlot + ", sportType="
				+ sportType + ", srptId=" + srptId + ", target=" + target
				+ ", totalTip=" + totalTip + "]";
	}

}
