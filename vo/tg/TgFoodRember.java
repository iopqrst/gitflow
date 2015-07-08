package com.bskcare.ch.vo.tg;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tg_food_rember")
public class TgFoodRember implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	/**
	 * 食物名称
	 */
	private String foodName;
	/**
	 * 消耗卡路里
	 */
	private int calorie;
	/**
	 * 图片地址
	 */
	private String picUrl;
	/**
	 * 食物类型  1：山西菜  2：素斋菜  3：上海菜  4：四川菜   5：福建菜   6：湖北菜   7：广州菜   8：浙江菜   
	 */
	private Integer type;
	/**
	 * 适合餐次  1：早餐  2：早加餐  3：午餐  4：午加餐   5：晚餐  6：晚加餐
	 */
	private String canci;  
	/**
	 * 是否适合糖尿病  0：适合   1：不适合
	 */
	private int tangniaobing;
	/**
	 * 是否适合高血压  0：适合   1：不适合
	 */
	private int gaoxueya;
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFoodName() {
		return foodName;
	}
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}
	public int getCalorie() {
		return calorie;
	}
	public void setCalorie(int calorie) {
		this.calorie = calorie;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getCanci() {
		return canci;
	}
	public void setCanci(String canci) {
		this.canci = canci;
	}
	public int getTangniaobing() {
		return tangniaobing;
	}
	public void setTangniaobing(int tangniaobing) {
		this.tangniaobing = tangniaobing;
	}
	public int getGaoxueya() {
		return gaoxueya;
	}
	public void setGaoxueya(int gaoxueya) {
		this.gaoxueya = gaoxueya;
	}
	
}
