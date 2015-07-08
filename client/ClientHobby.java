package com.bskcare.ch.vo.client;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户习惯嗜好
 */
@Entity
@Table(name = "t_client_hobby")
public class ClientHobby implements Serializable {

	private static final long serialVersionUID = -70571478472359104L;

	private Integer id;
	private Integer clientId;
	/** 吸烟状况1：是 2:否 */
	private int smoke;
	/** 日均吸烟量（支） */
	private Integer average;
	/** 开始吸烟的年龄（岁） */
	private Integer sage;
	/** 吸烟年限 */
	private Integer syear;
	/** 烟类型：1： 香烟 2：雪茄 3：烟斗 4：其他 */
	private String cigaret;
	/** 是否复吸： 1：是 2：否 */
	private int reSmoking;
	/** 被动吸烟 1：是 2：否 */
	private int shSmoke;
	/** 被动吸烟年限 */
	private Integer shAge;
	/** 被动吸烟 : 天/周 */
	private Integer shDay;
	/** 被动吸烟年限: 分钟/天 */
	private Integer shMinuite;
	/** 是否喝酒(1-是 ;2-否；) */
	private int drink;
	/** 开始喝酒年龄 */
	private Integer dage;
	/** 白酒折合成50°每天多少两 */
	private String white;
	/** 啤酒 毫升/天 */
	private String beer;
	/** 红酒 毫升/天 */
	private String red;
	/** 工作情况: 1 : 紧张 2 ：清闲 */
	private int working;
	/** 睡眠: 1: 充足 （6-8） 2： 不足 3： 严重失眠 */
	private int sleeping;
	/**
	 * 饮食： 1： 三餐不规律 2： 常不吃早饭 3： 常暴饮暴食 4：嗜咸 5：嗜甜 6：嗜油炸 7：嗜辣 8：嗜素食 9:嗜肉
	 */
	private String diet;
	/** 每周锻炼次数 */
	private int sportCount;
	/** 每周锻炼时间: 1: 少于30分钟 2：30-60分钟 3:60分钟以上 */
	private int sportTime;
	/** 锻炼方式: 1 : 跑步 2 : 游泳 3 ： 球类 4 ： 太极拳 5 ： 其他（填写） */
	private String sportType;
	/** 锻炼方式补充 */
	private String sportSupply;

	/**
	 * 体力活动 1：极轻体力活动 2：轻体力活动 3：中等体力活动 4：重体力活动
	 */
	private int physicalType;
	/**创建时间*/
	private Date createTime;
	
	/**不喜欢吃的食物**/
	private String notEat;
	
	/**早餐**/
	private String breakfast;
	/**早餐其他**/
	private String breakfastOther;
	/**中餐**/
	private String lunch;
	/**中餐其他**/
	private String lunchOther;
	/**晚餐**/
	private String dinner;
	/**晚餐其他**/
	private String dinnerOther;
	/**加餐次数**/
	private int jiacanCount;//0 无加餐， 1 一次/天 ，2 两次/天，3 三次/天 ， 4 >三次/天
	/**加餐类型**/
	private String jiacanType;
	/**运动时间带**/
	private String sportTimeZone;
	/**运动时间带其他**/
	private String sportZoneOther;
	/****/
	private String sportHabit;
	private String sportHabitOther;
	

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getSmoke() {
		return smoke;
	}

	public void setSmoke(int smoke) {
		this.smoke = smoke;
	}

	public Integer getAverage() {
		return average;
	}

	public void setAverage(Integer average) {
		this.average = average;
	}

	public Integer getSage() {
		return sage;
	}

	public void setSage(Integer sage) {
		this.sage = sage;
	}

	public int getShSmoke() {
		return shSmoke;
	}

	public void setShSmoke(int shSmoke) {
		this.shSmoke = shSmoke;
	}

	public Integer getShAge() {
		return shAge;
	}

	public void setShAge(Integer shAge) {
		this.shAge = shAge;
	}

	public Integer getShDay() {
		return shDay;
	}

	public void setShDay(Integer shDay) {
		this.shDay = shDay;
	}

	public Integer getShMinuite() {
		return shMinuite;
	}

	public void setShMinuite(Integer shMinuite) {
		this.shMinuite = shMinuite;
	}

	public int getDrink() {
		return drink;
	}

	public void setDrink(int drink) {
		this.drink = drink;
	}

	public Integer getDage() {
		return dage;
	}

	public void setDage(Integer dage) {
		this.dage = dage;
	}

	@Column(length = 10)
	public String getWhite() {
		return white;
	}

	public void setWhite(String white) {
		this.white = white;
	}

	@Column(length = 10)
	public String getBeer() {
		return beer;
	}

	public void setBeer(String beer) {
		this.beer = beer;
	}

	@Column(length = 10)
	public String getRed() {
		return red;
	}

	public void setRed(String red) {
		this.red = red;
	}

	public int getWorking() {
		return working;
	}

	public void setWorking(int working) {
		this.working = working;
	}

	public int getSleeping() {
		return sleeping;
	}

	public void setSleeping(int sleeping) {
		this.sleeping = sleeping;
	}

	@Column(length = 25)
	public String getDiet() {
		return diet;
	}

	public void setDiet(String diet) {
		this.diet = diet;
	}

	public int getSportCount() {
		return sportCount;
	}

	public void setSportCount(int sportCount) {
		this.sportCount = sportCount;
	}

	public int getSportTime() {
		return sportTime;
	}

	public void setSportTime(int sportTime) {
		this.sportTime = sportTime;
	}

	@Column(length = 25)
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

	@Column(length = 25)
	public String getCigaret() {
		return cigaret;
	}

	public void setCigaret(String cigaret) {
		this.cigaret = cigaret;
	}

	public int getReSmoking() {
		return reSmoking;
	}

	public void setReSmoking(int reSmoking) {
		this.reSmoking = reSmoking;
	}

	public Integer getSyear() {
		return syear;
	}

	public int getPhysicalType() {
		return physicalType;
	}

	public void setPhysicalType(int physicalType) {
		this.physicalType = physicalType;
	}

	public void setSyear(Integer syear) {
		this.syear = syear;
	}
	
	public String getNotEat() {
		return notEat;
	}

	public void setNotEat(String notEat) {
		this.notEat = notEat;
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

	@Override
	public String toString() {
		return "ClientHobby [average=" + average + ", beer=" + beer
				+ ", cigaret=" + cigaret + ", clientId=" + clientId
				+ ", createTime=" + createTime + ", dage=" + dage + ", diet="
				+ diet + ", drink=" + drink + ", id=" + id + ", physicalType="
				+ physicalType + ", reSmoking=" + reSmoking + ", red=" + red
				+ ", sage=" + sage + ", shAge=" + shAge + ", shDay=" + shDay
				+ ", shMinuite=" + shMinuite + ", shSmoke=" + shSmoke
				+ ", sleeping=" + sleeping + ", smoke=" + smoke
				+ ", sportCount=" + sportCount + ", sportSupply=" + sportSupply
				+ ", sportTime=" + sportTime + ", sportType=" + sportType
				+ ", syear=" + syear + ", white=" + white + ", working="
				+ working + "]";
	}

}
