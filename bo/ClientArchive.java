package com.bskcare.ch.bo;

import java.util.Date;

public class ClientArchive {
	/** 用户id */
	private Integer clientId;// 用户id
	/** 姓名 */
	private String name;
	/** 性别 男：0 女：1 */
	private String gender; // 性别 男：0 女：1
	/** 身高 */
	private String height; // 身高
	/** 体重 */
	private String weight; // 体重
	/** 臀围 */
	private String breech; // 臀围
	/** 腰围 */
	private String waist; // 腰围
	/** 心率 */
	private String heartRate;// 心率
	/** 生日 */
	private Date birthday; // 生日
	/**
	 * 体力活动 1：极轻体力活动 2：轻体力活动 3：中等体力活动 4：重体力活动
	 */
	private int physicalType;

	/**
	 * 过敏 1：花粉，2：粉尘，3：螨虫，4：奶类，5：海鲜类，6：牛羊肉，7：水果类，8：化妆品，9：冷空气，0：其他，
	 */
	private String Allergy;
	/**
	 * 其他过敏
	 */
	private String AllergyOther;

	/**
	 * 希望最先解决的健康问题
	 */
	private String hopeSolveHealth;
	/**
	 * 希望最先解决的其他健康问题
	 */
	private String precedenceDiseaseOther;
	/* 生活方式 */
	/**
	 * 生活嗜好 不良嗜好 1： 三餐不规律 2： 常不吃早饭 3： 常暴饮暴食 4：嗜咸 5：嗜甜 6：嗜油炸 7：嗜辣 8：嗜素食 9：嗜肉
	 * 10：抽烟 11：喝酒
	 */
	private String badHabits;
	/**
	 * 忌口食物 1：大米类2： 薯类3： 杂粮类4 ：畜禽肉类及制品5： 鱼及水产品6： 蛋类及制品7： 奶及制品 8：干豆及豆制品类 9：坚果
	 * 10：蔬菜11： 水果
	 */
	private String avoidCertainFood;
	/**
	 * 食物类型 1：粥类2： 米饭类3： 面食类4： 肉类5： 果蔬类6： 豆及豆制品类7： 奶类8： 蛋类0： 其他
	 */
	/** 早餐 **/
	private String breakfast;
	/** 早餐其他 **/
	private String breakfastOther;
	/** 中餐 **/
	private String lunch;
	/** 中餐其他 **/
	private String lunchOther;
	/** 晚餐 **/
	private String dinner;
	/** 晚餐其他 **/
	private String dinnerOther;
	/** 加餐次数 0：不加餐 1:'1次/天',2:'2次/天',3:'3次/天' 4 3次以上 **/
	private int jiacanCount;
	/**
	 * 加餐类型 1 坚果类2: 牛奶3: 酸奶4: 豆浆5: 水果6: 蒸薯类7: 饼干8: 面包9: 豆类
	 * **/
	private String jiacanType;

	/** 睡眠 1:充足（6-8小时） 2:不足 3:严重失眠 */
	private int sleep;

	/** 每周锻炼次数 */
	private int sportCount;
	/** 每次锻炼时间: 1: 少于30分钟 2：30-60分钟 3:60分钟以上 */
	private int sportTime;
	/** 锻炼方式: */
	private String sportType;
	/** 锻炼方式补充 */
	private String sportSupply;

	/** 运动时间带 **/
	private String sportTimeZone;
	/** 运动时间带其他 **/
	private String sportZoneOther;
	/** 运动习惯 **/
	private String sportHabit;
	private String sportHabitOther;

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getBreech() {
		return breech;
	}

	public void setBreech(String breech) {
		this.breech = breech;
	}

	public String getWaist() {
		return waist;
	}

	public void setWaist(String waist) {
		this.waist = waist;
	}

	public int getPhysicalType() {
		return physicalType;
	}

	public void setPhysicalType(int physicalType) {
		this.physicalType = physicalType;
	}

	public String getAllergy() {
		return Allergy;
	}

	public void setAllergy(String allergy) {
		Allergy = allergy;
	}

	public String getAllergyOther() {
		return AllergyOther;
	}

	public void setAllergyOther(String allergyOther) {
		AllergyOther = allergyOther;
	}

	public String getPrecedenceDiseaseOther() {
		return precedenceDiseaseOther;
	}

	public void setPrecedenceDiseaseOther(String precedenceDiseaseOther) {
		this.precedenceDiseaseOther = precedenceDiseaseOther;
	}

	public String getBadHabits() {
		return badHabits;
	}

	public void setBadHabits(String badHabits) {
		this.badHabits = badHabits;
	}

	public String getAvoidCertainFood() {
		return avoidCertainFood;
	}

	public void setAvoidCertainFood(String avoidCertainFood) {
		this.avoidCertainFood = avoidCertainFood;
	}

	public String getBreakfast() {
		return breakfast;
	}

	public void setBreakfast(String breakfast) {
		this.breakfast = breakfast;
	}

	public String getBreakfastOther() {
		return breakfastOther;
	}

	public void setBreakfastOther(String breakfastOther) {
		this.breakfastOther = breakfastOther;
	}

	public String getLunch() {
		return lunch;
	}

	public void setLunch(String lunch) {
		this.lunch = lunch;
	}

	public String getLunchOther() {
		return lunchOther;
	}

	public void setLunchOther(String lunchOther) {
		this.lunchOther = lunchOther;
	}

	public String getDinner() {
		return dinner;
	}

	public void setDinner(String dinner) {
		this.dinner = dinner;
	}

	public String getDinnerOther() {
		return dinnerOther;
	}

	public void setDinnerOther(String dinnerOther) {
		this.dinnerOther = dinnerOther;
	}

	public int getJiacanCount() {
		return jiacanCount;
	}

	public void setJiacanCount(int jiacanCount) {
		this.jiacanCount = jiacanCount;
	}

	public String getJiacanType() {
		return jiacanType;
	}

	public void setJiacanType(String jiacanType) {
		this.jiacanType = jiacanType;
	}

	public int getSleep() {
		return sleep;
	}

	public void setSleep(int sleep) {
		this.sleep = sleep;
	}

	public int getSportCount() {
		return sportCount;
	}

	public void setSportCount(Integer sportCount) {
		this.sportCount = sportCount;
	}

	public int getSportTime() {
		return sportTime;
	}

	public void setSportTime(int sportTime) {
		this.sportTime = sportTime;
	}

	public String getSportType() {
		return sportType;
	}

	public void setSportType(String sportType) {
		this.sportType = sportType;
	}

	public String getSportSupply() {
		return sportSupply;
	}

	public void setSportSupply(String sportSupply) {
		this.sportSupply = sportSupply;
	}

	public String getSportTimeZone() {
		return sportTimeZone;
	}

	public void setSportTimeZone(String sportTimeZone) {
		this.sportTimeZone = sportTimeZone;
	}

	public String getSportZoneOther() {
		return sportZoneOther;
	}

	public void setSportZoneOther(String sportZoneOther) {
		this.sportZoneOther = sportZoneOther;
	}

	public String getSportHabit() {
		return sportHabit;
	}

	public void setSportHabit(String sportHabit) {
		this.sportHabit = sportHabit;
	}

	public String getSportHabitOther() {
		return sportHabitOther;
	}

	public void setSportHabitOther(String sportHabitOther) {
		this.sportHabitOther = sportHabitOther;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeartRate() {
		return heartRate;
	}

	public void setHeartRate(String heartRate) {
		this.heartRate = heartRate;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getHopeSolveHealth() {
		return hopeSolveHealth;
	}

	public void setHopeSolveHealth(String hopeSolveHealth) {
		this.hopeSolveHealth = hopeSolveHealth;
	}

}
