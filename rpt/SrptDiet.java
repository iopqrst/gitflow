package com.bskcare.ch.vo.rpt;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 建议报告饮食部分
 */
@Entity
@Table(name = "srpt_diet")
public class SrptDiet implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer srptId; // 建议报告基本信息id
	private Integer clientId; // 用户Id
	private double oil;
	private double salt;// 盐
	private double milk;// 牛奶
	private double bean;// 豆类
	private double yudan;// 鱼蛋
	private double vegetables;
	private double gulei;// 谷类
	private String water;
	private double fruit;  //水果
	private double calorie;// 热量

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

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public double getOil() {
		return oil;
	}

	public void setOil(double oil) {
		this.oil = oil;
	}

	public double getSalt() {
		return salt;
	}

	public void setSalt(double salt) {
		this.salt = salt;
	}

	public double getMilk() {
		return milk;
	}

	public void setMilk(double milk) {
		this.milk = milk;
	}

	public double getBean() {
		return bean;
	}

	public void setBean(double bean) {
		this.bean = bean;
	}

	public double getYudan() {
		return yudan;
	}

	public void setYudan(double yudan) {
		this.yudan = yudan;
	}

	public double getVegetables() {
		return vegetables;
	}

	public void setVegetables(double vegetables) {
		this.vegetables = vegetables;
	}

	public double getGulei() {
		return gulei;
	}

	public void setGulei(double gulei) {
		this.gulei = gulei;
	}

	public String getWater() {
		return water;
	}

	public void setWater(String water) {
		this.water = water;
	}

	public double getCalorie() {
		return calorie;
	}

	public void setCalorie(double calorie) {
		this.calorie = calorie;
	}
	public double getFruit() {
		return fruit;
	}

	public void setFruit(double fruit) {
		this.fruit = fruit;
	}

	@Override
	public String toString() {
		return "SrptDiet [bean=" + bean + ", calorie=" + calorie
				+ ", clientId=" + clientId + ", gulei=" + gulei + ", id=" + id
				+ ", milk=" + milk + ", oil=" + oil + ", salt=" + salt
				+ ", srptId=" + srptId + ", vegetables=" + vegetables
				+ ", water=" + water + ", yudan=" + yudan + "]";
	}

}
