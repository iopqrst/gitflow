package com.bskcare.ch.bo;

import java.util.Date;


public class ClientLocationExtend {
	
	private Integer clientId;
	/**经度**/
	private double longitude; 
	/**纬度**/
	private double latitude;
	/**所在区域**/
	private String location;
	
	private String name;
	private Integer gender;
	/**昵称**/
	private String nickName;
	/**头像**/
	private String headPortrait;
	private String mobile;

	/****/
	private double distanceTotal;
	private Date testDate;
	
	
	
	public Date getTestDate() {
		return testDate;
	}
	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}
	public double getDistanceTotal() {
		return distanceTotal;
	}
	public void setDistanceTotal(double distanceTotal) {
		this.distanceTotal = distanceTotal;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getHeadPortrait() {
		return headPortrait;
	}
	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
}
