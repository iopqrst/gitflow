package com.bskcare.ch.bo;

import java.util.Date;

public class ClientInfoSport {
	/**用户id*/
	private Integer id;
	/**姓名*/
	private String name;
	/**年龄*/
	private Integer age;
	/**性别*/
	private Integer gender; 
	/**电话*/
	private String mobile;
	/**身高*/
	private String height; 
	/**体重*/
	private String weight;
	/**步长*/
	private double stepWidth;
	/**昵称*/
	private String nickName;  
	/**头像*/
	private String headPortrait;
	/**出生日期**/
	private Date birthday;
	
	
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
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
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
	public double getStepWidth() {
		return stepWidth;
	}
	public void setStepWidth(double stepWidth) {
		this.stepWidth = stepWidth;
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
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}
