package com.bskcare.ch.vo.rpt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 运动
 */
@Entity
@Table(name = "rpt_sport")
public class RptSport implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	/**
	 * 报告id
	 */
	private Integer rptId;
	/**
	 * 用户id
	 */
	private Integer clientId;
	/**
	 * 开篇总结:何先生76岁，血压平...
	 */
	private String openings;
	/**
	 * 运动种类
	 */
	private String sportType;
	/**
	 * 运动强度
	 */
	private String sportIntensity;
	/**
	 * 运动时间/运动计划
	 */
	private String sportPlanning;

	/**
	 * 运动时间（min）
	 */
	private Double sportTime;
	/**
	 * 运动时间带
	 */
	private String sportTimeSlot;
	/**
	 * 运动频率
	 */
	private String sportFrequency;
	/**
	 * 每天建议
	 */
	private String dailySuggestion;
	/**
	 * 针对处方
	 */
	private String prescription;
	/**
	 * 建议部分总结
	 */
	private String summary;

	private Date createTime;

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRptId() {
		return rptId;
	}

	public void setRptId(Integer rptId) {
		this.rptId = rptId;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getOpenings() {
		return openings;
	}

	public void setOpenings(String openings) {
		this.openings = openings;
	}

	public String getSportType() {
		return sportType;
	}

	public void setSportType(String sportType) {
		this.sportType = sportType;
	}

	public String getSportIntensity() {
		return sportIntensity;
	}

	public void setSportIntensity(String sportIntensity) {
		this.sportIntensity = sportIntensity;
	}

	public String getSportTimeSlot() {
		return sportTimeSlot;
	}

	public void setSportTimeSlot(String sportTimeSlot) {
		this.sportTimeSlot = sportTimeSlot;
	}

	public String getSportFrequency() {
		return sportFrequency;
	}

	public void setSportFrequency(String sportFrequency) {
		this.sportFrequency = sportFrequency;
	}

	public String getDailySuggestion() {
		return dailySuggestion;
	}

	public void setDailySuggestion(String dailySuggestion) {
		this.dailySuggestion = dailySuggestion;
	}

	public Double getSportTime() {
		return sportTime;
	}

	public void setSportTime(Double sportTime) {
		this.sportTime = sportTime;
	}

	public String getPrescription() {
		return prescription;
	}

	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getSportPlanning() {
		return sportPlanning;
	}

	public void setSportPlanning(String sportPlanning) {
		this.sportPlanning = sportPlanning;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "RptSport [clientId=" + clientId + ", createTime=" + createTime
				+ ", dailySuggestion=" + dailySuggestion + ", id=" + id
				+ ", openings=" + openings + ", prescription=" + prescription
				+ ", rptId=" + rptId + ", sportFrequency=" + sportFrequency
				+ ", sportIntensity=" + sportIntensity + ", sportPlanning="
				+ sportPlanning + ", sportTimeSlot=" + sportTimeSlot
				+ ", sportType=" + sportType + ", summary=" + summary + "]";
	}

}
