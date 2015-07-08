package com.bskcare.ch.vo.rpt;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rpt_sport_disease")
public class SportDisease implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 年龄段（1：青年2：中年 3：老年） */
	public static final int AGE_YOUNG = 1;
	/** 年龄段（1：青年2：中年 3：老年） */
	public static final int AGE_MIDDLE = 2;
	/** 年龄段（1：青年2：中年 3：老年） */
	public static final int AGE_OLD = 3;
	/** 1：轻度 2：中度 3：重度 */
	public static final int DEGREE_MILD = 1;
	/** 1：轻度 2：中度 3：重度 */
	public static final int DEGREE_MEZZO = 2;
	/** 1：轻度 2：中度 3：重度 */
	public static final int DEGREE_SERIOUS = 3;

	private Integer id;
	/** 疾病名称 */
	private String diseaseName;
	/** 适合的年龄 */
	private Integer ageBracket;
	/** 疾病程度 1：2：3 */
	private Integer degree;
	/** 运动种类 */
	private String sportType;
	/****/
	private String bestSuitSport;
	/** 运动计划/运动时间 */
	private String sportPlanning;
	/** 运动时间（min）*/
	private Double sportTime;
	/** 运动时间带 */
	private String sportTimeSlot;
	/** 运动频率 */
	private String sportFrequency;
	/** 备注 */
	private String comment;

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDiseaseName() {
		return diseaseName;
	}

	public void setDiseaseName(String diseaseName) {
		this.diseaseName = diseaseName;
	}

	public Integer getAgeBracket() {
		return ageBracket;
	}

	public void setAgeBracket(Integer ageBracket) {
		this.ageBracket = ageBracket;
	}

	public Integer getDegree() {
		return degree;
	}

	public void setDegree(Integer degree) {
		this.degree = degree;
	}

	public String getSportPlanning() {
		return sportPlanning;
	}

	public void setSportPlanning(String sportPlanning) {
		this.sportPlanning = sportPlanning;
	}

	public String getSportFrequency() {
		return sportFrequency;
	}

	public void setSportFrequency(String sportFrequency) {
		this.sportFrequency = sportFrequency;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getSportType() {
		return sportType;
	}

	public Double getSportTime() {
		return sportTime;
	}

	public void setSportTime(Double sportTime) {
		this.sportTime = sportTime;
	}

	public void setSportType(String sportType) {
		this.sportType = sportType;
	}

	public String getSportTimeSlot() {
		return sportTimeSlot;
	}

	public void setSportTimeSlot(String sportTimeSlot) {
		this.sportTimeSlot = sportTimeSlot;
	}
	public String getBestSuitSport() {
		return bestSuitSport;
	}

	public void setBestSuitSport(String bestSuitSport) {
		this.bestSuitSport = bestSuitSport;
	}
	public Integer getAgeBracketByAge(Integer age) {
		if (null == age)
			return null;
		if (age >= 18 && age <= 40) {
			return AGE_YOUNG;
		} else if (age >= 41 && age <= 65) {
			return AGE_MIDDLE;
		} else if (age >= 66) {
			return AGE_OLD;
		}
		return null;
	}

	@Override
	public String toString() {
		return "SportDisease [ageBracket=" + ageBracket + ", comment="
				+ comment + ", degree=" + degree + ", diseaseName="
				+ diseaseName + ", id=" + id + ", sportFrequency="
				+ sportFrequency + ", sportPlanning=" + sportPlanning
				+ ", sportTimeSlot=" + sportTimeSlot + ", sportType="
				+ sportType + "]";
	}

}
