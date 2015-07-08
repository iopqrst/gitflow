package com.bskcare.ch.bo;

public class ClientHobbyExtend {
	/**
	 * 饮食： 1： 三餐不规律 2： 常不吃早饭 3： 常暴饮暴食 4：嗜咸 5：嗜甜 6：嗜油炸 7：嗜辣 8：嗜素食 9:嗜肉
	 */
	private String diet;
	/**不喜欢吃的食物*/
	private String notEat;
	/**早餐*/
	private String breakfast;
	/**中餐*/
	private String lunch;
	/**晚餐*/
	private String dinner;
	/**加餐次数*/
	private String jiacanCount;
	/**加餐类型*/
	private String jiacanType;
	/** 锻炼方式 */
	private String sportType;
	/** 运动时长*/
	private String sportTime;
	/**运动时间带*/
	private String sportTimeZone;
	
	
	public String getDiet() {
		return diet;
	}
	public void setDiet(String diet) {
		this.diet = diet;
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
	public String getLunch() {
		return lunch;
	}
	public void setLunch(String lunch) {
		this.lunch = lunch;
	}
	public String getDinner() {
		return dinner;
	}
	public void setDinner(String dinner) {
		this.dinner = dinner;
	}
	public String getJiacanCount() {
		return jiacanCount;
	}
	public void setJiacanCount(String jiacanCount) {
		this.jiacanCount = jiacanCount;
	}
	public String getJiacanType() {
		return jiacanType;
	}
	public void setJiacanType(String jiacanType) {
		this.jiacanType = jiacanType;
	}
	public String getSportType() {
		return sportType;
	}
	public void setSportType(String sportType) {
		this.sportType = sportType;
	}
	public String getSportTime() {
		return sportTime;
	}
	public void setSportTime(String sportTime) {
		this.sportTime = sportTime;
	}
	public String getSportTimeZone() {
		return sportTimeZone;
	}
	public void setSportTimeZone(String sportTimeZone) {
		this.sportTimeZone = sportTimeZone;
	}
	
}
