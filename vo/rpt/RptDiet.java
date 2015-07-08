package com.bskcare.ch.vo.rpt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 饮食
 */
@Entity
@Table(name = "rpt_diet")
public class RptDiet implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** 运动部分膳食报告:1 , 非运动部分膳食报告:0*/
	public static final int TYPE_UNSPORT = 0;
	/** 运动部分膳食报告:1 , 非运动部分膳食报告:0*/
	public static final int TYPE_SPORT = 1;

	private Integer id;
	private Integer rptId;
	private Integer clientId;
	
	/**
	 * 日常饮食原则
	 */
	private String dietPrinciple;
	/**体力活动热卡计算基数*/
	private int cardinality;
	/** 每日所需热卡量 **/
	private double calorieOfDay;
	/** 每日所需蔬菜量 **/
	private double vegetableOfDay;
	/** 每日所需水果梁量 **/
	private double fruitOfDay;
	/** 每日所需谷类量 **/
	private double guleiOfDay;
	/** 每日所需肉蛋类量 **/
	private double roudanOfDay;
	/** 每日所需牛奶量 **/
	private double milkOfDay;
	/** 每日所需豆类量 **/
	private double beanOfDay;
	/** 每日所需坚果量 **/
	private double nutOfDay;
	/** 每日所需油量 **/
	private double oilOfDay;
	/** 每日所需水量 **/
	private String waterOfDay;
	/** 早餐所需谷物的量 **/
	private double gulei7;
	/** 早餐建议谷类 **/
	private String gulei7Detail;
	/** 早餐所需蔬菜的量 **/
	private double vegetable7;
	/** 早餐蔬菜说明 **/
	private String vegetable7Detail;
	/** 早餐所需蛋类的量 **/
	private double egg7;
	/** 早餐所需蛋类说明 **/
	private String egg7Detail;
	/** 早餐所需水果 **/
	private double fruit10;
	/** 早餐水果说明 **/
	private String fruit10Detail;
	/** 早餐所需牛奶 **/
	private double milk10;
	/** 早餐牛奶说明 **/
	private String milk10Detail;
	/** 早餐所需谷类 **/
	private double gulei12;
	/** 早餐谷类说明 **/
	private String gulei12Detail;
	/** 早餐所需蔬菜 **/
	private double vegetable12;
	/** 早餐蔬菜说明 **/
	private String vegetable12Detail;
	/** 早餐所需肉蛋 **/
	private double roudan12;
	/** 早餐肉蛋说明 **/
	private String roudan12Detail;
	/** 早餐所需水果 **/
	private double fruit15;
	/** 早餐水果说明 **/
	private String fruit15Detail;
	/** 早餐所需谷类 **/
	private double gulei18;
	/** 早餐谷类说明 **/
	private String gulei18Detail;
	/** 早餐所需蔬菜 **/
	private double vegetable18;
	/** 早餐蔬菜说明 **/
	private String vegetable18Detail;
	/** 早餐所需豆类 **/
	private double bean18;
	/** 早餐豆类说明 **/
	private String bean18Detail;
	/** 早餐所需牛奶 **/
	private double milk19;
	/** 早餐牛奶说明 **/
	private String milk19Detail;
	/** 早餐 **/
	private String breakfast;
	/** 间餐 **/
	private String jiancan;
	/** 午餐 **/
	private String lunch;
	/** 加餐 **/
	private String jiacan;
	/** 晚餐 **/
	private String dinner;
	/** 睡前加餐 **/
	private String shuiqianjiacan;
	/** 药膳 **/
	private String yaoshan;
	/** 药膳功能 **/
	private String yaoshangongneng;
	/** 不适合食物 **/
	private String unsuitable;
	/** 膳食报告类型 0：非运动部分报告 1：运动部分报告*/
	private int type;
	/** 创建时间 */
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

	public double getCalorieOfDay() {
		return calorieOfDay;
	}

	public void setCalorieOfDay(double calorieOfDay) {
		this.calorieOfDay = calorieOfDay;
	}

	public double getVegetableOfDay() {
		return vegetableOfDay;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setVegetableOfDay(double vegetableOfDay) {
		this.vegetableOfDay = vegetableOfDay;
	}

	public double getFruitOfDay() {
		return fruitOfDay;
	}

	public void setFruitOfDay(double fruitOfDay) {
		this.fruitOfDay = fruitOfDay;
	}

	public double getGuleiOfDay() {
		return guleiOfDay;
	}

	public void setGuleiOfDay(double guleiOfDay) {
		this.guleiOfDay = guleiOfDay;
	}

	public double getRoudanOfDay() {
		return roudanOfDay;
	}

	public void setRoudanOfDay(double roudanOfDay) {
		this.roudanOfDay = roudanOfDay;
	}

	public double getMilkOfDay() {
		return milkOfDay;
	}

	public void setMilkOfDay(double milkOfDay) {
		this.milkOfDay = milkOfDay;
	}

	public double getBeanOfDay() {
		return beanOfDay;
	}

	public void setBeanOfDay(double beanOfDay) {
		this.beanOfDay = beanOfDay;
	}

	public double getNutOfDay() {
		return nutOfDay;
	}

	public void setNutOfDay(double nutOfDay) {
		this.nutOfDay = nutOfDay;
	}

	public double getOilOfDay() {
		return oilOfDay;
	}

	public void setOilOfDay(double oilOfDay) {
		this.oilOfDay = oilOfDay;
	}

	public String getWaterOfDay() {
		return waterOfDay;
	}

	public void setWaterOfDay(String waterOfDay) {
		this.waterOfDay = waterOfDay;
	}

	public double getGulei7() {
		return gulei7;
	}

	public void setGulei7(double gulei7) {
		this.gulei7 = gulei7;
	}

	public String getGulei7Detail() {
		return gulei7Detail;
	}

	public void setGulei7Detail(String gulei7Detail) {
		this.gulei7Detail = gulei7Detail;
	}

	public double getVegetable7() {
		return vegetable7;
	}

	public void setVegetable7(double vegetable7) {
		this.vegetable7 = vegetable7;
	}

	public String getVegetable7Detail() {
		return vegetable7Detail;
	}

	public void setVegetable7Detail(String vegetable7Detail) {
		this.vegetable7Detail = vegetable7Detail;
	}

	public double getEgg7() {
		return egg7;
	}

	public void setEgg7(double egg7) {
		this.egg7 = egg7;
	}

	public String getEgg7Detail() {
		return egg7Detail;
	}

	public void setEgg7Detail(String egg7Detail) {
		this.egg7Detail = egg7Detail;
	}

	public double getFruit10() {
		return fruit10;
	}

	public void setFruit10(double fruit10) {
		this.fruit10 = fruit10;
	}

	public String getFruit10Detail() {
		return fruit10Detail;
	}

	public void setFruit10Detail(String fruit10Detail) {
		this.fruit10Detail = fruit10Detail;
	}

	public double getMilk10() {
		return milk10;
	}

	public void setMilk10(double milk10) {
		this.milk10 = milk10;
	}

	public String getMilk10Detail() {
		return milk10Detail;
	}

	public void setMilk10Detail(String milk10Detail) {
		this.milk10Detail = milk10Detail;
	}

	public double getGulei12() {
		return gulei12;
	}

	public void setGulei12(double gulei12) {
		this.gulei12 = gulei12;
	}

	public String getGulei12Detail() {
		return gulei12Detail;
	}

	public void setGulei12Detail(String gulei12Detail) {
		this.gulei12Detail = gulei12Detail;
	}

	public double getVegetable12() {
		return vegetable12;
	}

	public void setVegetable12(double vegetable12) {
		this.vegetable12 = vegetable12;
	}

	public String getVegetable12Detail() {
		return vegetable12Detail;
	}

	public void setVegetable12Detail(String vegetable12Detail) {
		this.vegetable12Detail = vegetable12Detail;
	}

	public double getRoudan12() {
		return roudan12;
	}

	public void setRoudan12(double roudan12) {
		this.roudan12 = roudan12;
	}

	public String getRoudan12Detail() {
		return roudan12Detail;
	}

	public void setRoudan12Detail(String roudan12Detail) {
		this.roudan12Detail = roudan12Detail;
	}

	public double getFruit15() {
		return fruit15;
	}

	public void setFruit15(double fruit15) {
		this.fruit15 = fruit15;
	}

	public String getFruit15Detail() {
		return fruit15Detail;
	}

	public void setFruit15Detail(String fruit15Detail) {
		this.fruit15Detail = fruit15Detail;
	}

	public double getGulei18() {
		return gulei18;
	}

	public void setGulei18(double gulei18) {
		this.gulei18 = gulei18;
	}

	public String getGulei18Detail() {
		return gulei18Detail;
	}

	public void setGulei18Detail(String gulei18Detail) {
		this.gulei18Detail = gulei18Detail;
	}

	public double getVegetable18() {
		return vegetable18;
	}

	public int getCardinality() {
		return cardinality;
	}

	public void setCardinality(int cardinality) {
		this.cardinality = cardinality;
	}

	public void setVegetable18(double vegetable18) {
		this.vegetable18 = vegetable18;
	}

	public String getVegetable18Detail() {
		return vegetable18Detail;
	}

	public void setVegetable18Detail(String vegetable18Detail) {
		this.vegetable18Detail = vegetable18Detail;
	}

	public double getBean18() {
		return bean18;
	}

	public void setBean18(double bean18) {
		this.bean18 = bean18;
	}

	public String getBean18Detail() {
		return bean18Detail;
	}

	public void setBean18Detail(String bean18Detail) {
		this.bean18Detail = bean18Detail;
	}

	public double getMilk19() {
		return milk19;
	}

	public void setMilk19(double milk19) {
		this.milk19 = milk19;
	}

	public String getMilk19Detail() {
		return milk19Detail;
	}

	public void setMilk19Detail(String milk19Detail) {
		this.milk19Detail = milk19Detail;
	}

	public String getBreakfast() {
		return breakfast;
	}

	public void setBreakfast(String breakfast) {
		this.breakfast = breakfast;
	}

	public String getJiancan() {
		return jiancan;
	}

	public void setJiancan(String jiancan) {
		this.jiancan = jiancan;
	}

	public String getLunch() {
		return lunch;
	}

	public void setLunch(String lunch) {
		this.lunch = lunch;
	}

	public String getJiacan() {
		return jiacan;
	}

	public void setJiacan(String jiacan) {
		this.jiacan = jiacan;
	}

	public String getDinner() {
		return dinner;
	}

	public void setDinner(String dinner) {
		this.dinner = dinner;
	}

	public String getShuiqianjiacan() {
		return shuiqianjiacan;
	}

	public void setShuiqianjiacan(String shuiqianjiacan) {
		this.shuiqianjiacan = shuiqianjiacan;
	}

	public String getYaoshan() {
		return yaoshan;
	}

	public void setYaoshan(String yaoshan) {
		this.yaoshan = yaoshan;
	}

	public String getYaoshangongneng() {
		return yaoshangongneng;
	}

	public void setYaoshangongneng(String yaoshangongneng) {
		this.yaoshangongneng = yaoshangongneng;
	}

	public String getUnsuitable() {
		return unsuitable;
	}

	public void setUnsuitable(String unsuitable) {
		this.unsuitable = unsuitable;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDietPrinciple() {
		return dietPrinciple;
	}

	public void setDietPrinciple(String dietPrinciple) {
		this.dietPrinciple = dietPrinciple;
	}

	
	@Override
	public String toString() {
		return "Diet [bean18=" + bean18 + ", bean18Detail=" + bean18Detail
				+ ", beanOfDay=" + beanOfDay + ", breakfast=" + breakfast
				+ ", calorieOfDay=" + calorieOfDay + ", createTime="
				+ createTime + ", dinner=" + dinner + ", egg7=" + egg7
				+ ", egg7Detail=" + egg7Detail + ", fruit10=" + fruit10
				+ ", fruit10Detail=" + fruit10Detail + ", fruit15=" + fruit15
				+ ", fruit15Detail=" + fruit15Detail + ", fruitOfDay="
				+ fruitOfDay + ", gulei12=" + gulei12 + ", gulei12Detail="
				+ gulei12Detail + ", gulei18=" + gulei18 + ", gulei18Detail="
				+ gulei18Detail + ", gulei7=" + gulei7 + ", gulei7Detail="
				+ gulei7Detail + ", guleiOfDay=" + guleiOfDay + ", id=" + id
				+ ", jiacan=" + jiacan + ", jiancan=" + jiancan + ", lunch="
				+ lunch + ", milk10=" + milk10 + ", milk10Detail="
				+ milk10Detail + ", milk19=" + milk19 + ", milk19Detail="
				+ milk19Detail + ", milkOfDay=" + milkOfDay + ", nutOfDay="
				+ nutOfDay + ", oilOfDay=" + oilOfDay + ", roudan12="
				+ roudan12 + ", roudan12Detail=" + roudan12Detail
				+ ", roudanOfDay=" + roudanOfDay + ", rptId=" + rptId
				+ ", shuiqianjiacan=" + shuiqianjiacan + ", unsuitable="
				+ unsuitable + ", vegetable12=" + vegetable12
				+ ", vegetable12Detail=" + vegetable12Detail + ", vegetable18="
				+ vegetable18 + ", vegetable18Detail=" + vegetable18Detail
				+ ", vegetable7=" + vegetable7 + ", vegetable7Detail="
				+ vegetable7Detail + ", vegetableOfDay=" + vegetableOfDay
				+ ", waterOfDay=" + waterOfDay + ", yaoshan=" + yaoshan
				+ ", yaoshangongneng=" + yaoshangongneng + "]";
	}

}
