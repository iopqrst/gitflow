package com.bskcare.ch.vo.tg;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tg_cooked_food")
public class TgCookedFood implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 适合餐次：1：早餐 */
	public static final String CANCI_BREAKFAST = "1";
	/** 适合餐次：2：中餐 */
	public static final String CANCI_LUNCH = "2";
	/** 适合餐次：3：晚餐 */
	public static final String CANCI_DINNER = "3";
	/** 适合餐次：4：早加餐 */
	public static final String CANCI_ZAOJIA = "4";
	/** 适合餐次：5：午加餐 */
	public static final String CANCI_WUJIA = "5";
	/** 适合餐次：6：晚加餐 */
	public static final String CANCI_WANJIA = "6";

	/** 菜的类型：1：粥 */
	public static final int TYPE_ZOU = 1;
	// /**菜的类型：2：主食（面）*/
	// public static final int TYPE_ZHUSHI_MIAN = 2;
	// /**菜的类型：3：主食（米）*/
	// public static final int TYPE_ZHUSHI_MI = 3;
	// /**菜的类型：4：主食（薯）*/
	// public static final int TYPE_ZHUSHI_SHU = 4;

	/** 菜的类型：2：主食 */
	public static final int TYPE_ZHUSHI = 2;
	/** 菜的类型：3：凉素菜 */
	public static final int TYPE_SUCAI_LIANG = 3;
	/** 菜的类型：4：热素菜 */
	public static final int TYPE_SUCAI_RE = 4;
	/** 菜的类型：5：热荤菜 */
	public static final int TYPE_HUN_RE = 5;
	/** 菜的类型：6：热炒豆制品 */
	public static final int TYPE_DOUZHI = 6;
	/** 菜的类型：7：蛋 */
	public static final int TYPE_DAN = 7;
	/** 菜的类型：8：汤（荤） */
	public static final int TYPE_TANG_HUN = 8;
	/** 菜的类型：9：汤（素） */
	public static final int TYPE_TANG_SU = 9;
	/** 菜的类型：10：奶 */
	public static final int TYPE_NAI = 10;
	/** 菜的类型：11：豆浆 */
	public static final int TYPE_DOUJIANG = 11;
	/** 菜的类型：12：水果 */
	public static final int TYPE_FRUIT = 12;
	/** 菜的类型：13：果蔬 */
	public static final int TYPE_VEGETABLT = 13;
	/** 菜的类型：14：坚果 */
	public static final int TYPE_NUTS = 14;

	private Integer id;
	private String name;
	/**
	 * 类型（1：粥 2：主食（面） 3：凉素菜 4：热素菜 5：热荤菜 6：热炒豆制品 7：蛋 8：汤（荤） 9：汤（素） 10：奶 11：豆浆
	 * 12：水果 13：果蔬 14：坚果）
	 */
	private Integer type;
	/** 适合的餐次（1：早餐 2：中餐 3：晚餐 4：早加 5：午加 6：晚加） */
	private String canci;
	/** 菜的成分 */
	private String food;
	/** 菜中成分的类型 */
	private String foodType;
	/** 是否适合糖尿病（0：否 1：是 ） */
	private int tangniaobing;
	/** 是否适合高血压（0：否 1：是 ） */
	private int gaoxueya;

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getFood() {
		return food;
	}

	public void setFood(String food) {
		this.food = food;
	}

	public String getFoodType() {
		return foodType;
	}

	public void setFoodType(String foodType) {
		this.foodType = foodType;
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
