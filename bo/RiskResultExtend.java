package com.bskcare.ch.bo;

public class RiskResultExtend {
	
	private String name;     	// 姓名
	private Integer gender;  	// 性别
	private String birthday; 	// 出生日期
	private double weight;	 	// 体重 kg
	private double height;   	// 身高 cm
	private double waistline;	// 腰围
	private String breech; // 臀围
	private Integer physicalType;
	private int isSport;		// 每周是否运动 1为是 0为否
	private int sleep;			// 睡眠状况 1充足 2不足 3睡眠
	private int isFhistory;		// 家族史糖尿病 1为是 0为否
	private int bloodPressure;	// 血压是否正常 1为正常 0为高血压
	private int lipids;			// 血脂状况 1为正常 0为不正常
	private int gestation;		// 妊娠 1为有 0为没有
	private String complications;// 合并症
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getWaistline() {
		return waistline;
	}
	public void setWaistline(double waistline) {
		this.waistline = waistline;
	}
	public String getBreech() {
		return breech;
	}
	public void setBreech(String breech) {
		this.breech = breech;
	}
	public Integer getPhysicalType() {
		return physicalType;
	}
	public void setPhysicalType(Integer physicalType) {
		this.physicalType = physicalType;
	}
	public int getIsSport() {
		return isSport;
	}
	public void setIsSport(int isSport) {
		this.isSport = isSport;
	}
	public int getSleep() {
		return sleep;
	}
	public void setSleep(int sleep) {
		this.sleep = sleep;
	}
	public int getIsFhistory() {
		return isFhistory;
	}
	public void setIsFhistory(int isFhistory) {
		this.isFhistory = isFhistory;
	}
	public int getBloodPressure() {
		return bloodPressure;
	}
	public void setBloodPressure(int bloodPressure) {
		this.bloodPressure = bloodPressure;
	}
	public int getLipids() {
		return lipids;
	}
	public void setLipids(int lipids) {
		this.lipids = lipids;
	}
	public int getGestation() {
		return gestation;
	}
	public void setGestation(int gestation) {
		this.gestation = gestation;
	}
	public String getComplications() {
		return complications;
	}
	public void setComplications(String complications) {
		this.complications = complications;
	}
}
