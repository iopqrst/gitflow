package com.bskcare.ch.bo;

/** 风险评估 
*	血糖高管评测题答案实体
*/

public class RiskResultBean {

	private int gender;// 0男 1女
	private double weight;// 体重 kg
	private double height;// 身高 cm
	private int age;// 年龄
	private double waistline;// 腰围
	private int physicalType;// 工作类型 0为默认 1：极轻体力 2：轻体力 3：中等体力劳动 4：重体力劳动
	private int isSport;// 每周是否运动 1为是 0为否
	private int isEat;// 是否吃足够水果 1为是 0为否
	private int sleep;// 睡眠状况 1充足 2不足 3睡眠
	private int isFhistory;// 家族史糖尿病 1为是 0为否
	private int sugarValue;// 空腹血糖值 1:3.9-5.6mmol/L 2:5.6-6.1mmol/L 3:6.1-7.0mmol/L
	// 4:≥7.0mmol/L
	private int bloodPressure;// 血压是否正常 1为正常 0为高血压
	private int lipids;// 血脂状况 1为正常 0为不正常
	private int diseaseYears;// 发病年龄 1:小于25岁 2:25-35岁 3:大于35岁 4:未患病
	private int gad;// GAD 1:阳性 2:阴性 3:不知道
	private int gestation;// 妊娠 1为有 0为没有
	private int isShistory;// 自己患病史 1为有 0为无
	private String complications;// 合并症
	private String result;// 风险评估结果
	private String birthday;
	private float probability; //发病概率
	
	
	
	public float getProbability() {
		return probability;
	}

	public void setProbability(float probability) {
		this.probability = probability;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}


	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getPhysicalType() {
		return physicalType;
	}

	public void setPhysicalType(int physicalType) {
		this.physicalType = physicalType;
	}

	public int getIsSport() {
		return isSport;
	}

	public void setIsSport(int isSport) {
		this.isSport = isSport;
	}

	public int getIsEat() {
		return isEat;
	}

	public void setIsEat(int isEat) {
		this.isEat = isEat;
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

	public int getSugarValue() {
		return sugarValue;
	}

	public void setSugarValue(int sugarValue) {
		this.sugarValue = sugarValue;
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

	public int getDiseaseYears() {
		return diseaseYears;
	}

	public void setDiseaseYears(int diseaseYears) {
		this.diseaseYears = diseaseYears;
	}

	public int getGad() {
		return gad;
	}

	public void setGad(int gad) {
		this.gad = gad;
	}

	public int getGestation() {
		return gestation;
	}

	public void setGestation(int gestation) {
		this.gestation = gestation;
	}

	public int getIsShistory() {
		return isShistory;
	}

	public void setIsShistory(int isShistory) {
		this.isShistory = isShistory;
	}

	public String getComplications() {
		return complications;
	}

	public void setComplications(String complications) {
		this.complications = complications;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
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

}
