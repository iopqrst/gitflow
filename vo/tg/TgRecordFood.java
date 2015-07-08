package com.bskcare.ch.vo.tg;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tg_record_food")
public class TgRecordFood implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer clientId;
	/**
	 * 食物id
	 */
	private String foodId;
	/**
	 * 消耗热量
	 */
	private int calorie;
	/**
	 * 餐次
	 */
	private Integer canci;
	private Date createTime;
	
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
	public String getFoodId() {
		return foodId;
	}
	public void setFoodId(String foodId) {
		this.foodId = foodId;
	}
	public int getCalorie() {
		return calorie;
	}
	public void setCalorie(int calorie) {
		this.calorie = calorie;
	}
	
	public Integer getCanci() {
		return canci;
	}
	public void setCanci(Integer canci) {
		this.canci = canci;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
