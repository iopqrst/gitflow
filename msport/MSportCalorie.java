package com.bskcare.ch.vo.msport;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="m_sport_calorie")
public class MSportCalorie implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer mid;
	private String sportType;
	private int sportTime;
	private Date createTime;
	
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMid() {
		return mid;
	}
	public void setMid(Integer mid) {
		this.mid = mid;
	}
	public String getSportType() {
		return sportType;
	}
	public void setSportType(String sportType) {
		this.sportType = sportType;
	}
	public int getSportTime() {
		return sportTime;
	}
	public void setSportTime(int sportTime) {
		this.sportTime = sportTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
