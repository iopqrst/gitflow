package com.bskcare.ch.vo;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 健康等级表
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_health_level")
public class HealthLevel {
	
	/**
	 * 状态 1:启用的健康等级
	 */
	public static final int HEALTH_LEVEL_TASK_STATUS_ON = 1;
	
	private Integer id;
	private Integer maxGrade;
	private Integer minGrade;
	private String name ;
	private Integer status;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMaxGrade() {
		return maxGrade;
	}
	public void setMaxGrade(Integer maxGrade) {
		this.maxGrade = maxGrade;
	}
	public Integer getMinGrade() {
		return minGrade;
	}
	public void setMinGrade(Integer minGrade) {
		this.minGrade = minGrade;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
}
