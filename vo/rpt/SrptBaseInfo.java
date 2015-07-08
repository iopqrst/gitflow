package com.bskcare.ch.vo.rpt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "srpt_baseinfo")
public class SrptBaseInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 0: 自动生成 1：草稿 2：已提交 3：已删除
	 */
	public static final int RPT_STATUS_AUTO = 0;
	public static final int RPT_STATUS_DRAFT = 1;
	public static final int RPT_STATUS_COMMIT = 2;
	public static final int RPT_STATUS_DELETE = 3;
	public static final int RPT_ISREAD_YES = 1;
	public static final int RPT_ISREAD_NO = 2;
	
	private Integer id;
	/** 用户id **/
	private Integer clientId;
	/** 客户姓名 **/
	private String name;
	/** 客户年龄 **/
	private Integer age;
	/** 客户性别 **/
	private Integer gender;
	/** 客户电话 **/
	private String mobile;
	/** 身高 **/
	private String height;
	/** 体重 **/
	private String weight;
	/** 腰围 **/
	private String breech;
	/** 臀围 **/
	private String waist;
	/** 心率 **/
	private String heartRate;
	/** 职业 **/
	private String profession;
	/** 既往病史 **/
	private String diseaseSelf;
	/** 家族病史 **/
	private String diseaseFamily;
	/** 过敏史 **/
	private String allergyHistroy;
	/** 希望解决的健康问题 **/
	private String hopeSolveHealth;
	/** 饮食习惯 **/
	private String diet;
	/** 不想吃的食物 **/
	private String notEat;
	/** 早餐 **/
	private String breakfast;
	/** 中餐 **/
	private String lunch;
	/** 晚餐 **/
	private String dinner;
	/** 加餐次数 **/
	private Integer jiacanCount;
	/** 加餐类型 **/
	private String jiacanType;
	/** 睡眠 **/
	private Integer sleeping;
	/** 工作情况 **/
	private Integer working;
	/** 是否抽烟 **/
	private Integer smoke;
	/** 是否喝酒 **/
	private Integer drink;
	/** 锻炼方式 **/
	private String sportType;
	/** 锻炼时间 **/
	private Integer sportTime;
	/** 锻炼时间段 **/
	private String sportTimeZone;
	/** 体力活动系数 **/
	private Integer physicalType;
	/** bmi **/
	private String bmi;
	/** 医生姓名 **/
	private String docName;
	/** 健康管理师姓名 **/
	private String hmName;
	private Date createTime;
	private int status;
	private int isRead;
	private int type;
	private Date beginTime;

	/** 啤酒 **/
	private String beer;
	/** 白酒 **/
	private String white;
	/** 红酒 **/
	private String red;
	/** 运动次数 **/
	private Integer sportCount;

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

	public String getHeartRate() {
		return heartRate;
	}

	public void setHeartRate(String heartRate) {
		this.heartRate = heartRate;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getDiseaseSelf() {
		return diseaseSelf;
	}

	public void setDiseaseSelf(String diseaseSelf) {
		this.diseaseSelf = diseaseSelf;
	}

	public String getDiseaseFamily() {
		return diseaseFamily;
	}

	public void setDiseaseFamily(String diseaseFamily) {
		this.diseaseFamily = diseaseFamily;
	}

	public String getAllergyHistroy() {
		return allergyHistroy;
	}

	public void setAllergyHistroy(String allergyHistroy) {
		this.allergyHistroy = allergyHistroy;
	}

	public String getHopeSolveHealth() {
		return hopeSolveHealth;
	}

	public void setHopeSolveHealth(String hopeSolveHealth) {
		this.hopeSolveHealth = hopeSolveHealth;
	}

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

	public Integer getJiacanCount() {
		return jiacanCount;
	}

	public void setJiacanCount(Integer jiacanCount) {
		this.jiacanCount = jiacanCount;
	}

	public String getJiacanType() {
		return jiacanType;
	}

	public void setJiacanType(String jiacanType) {
		this.jiacanType = jiacanType;
	}

	public Integer getSleeping() {
		return sleeping;
	}

	public void setSleeping(Integer sleeping) {
		this.sleeping = sleeping;
	}

	public Integer getWorking() {
		return working;
	}

	public void setWorking(Integer working) {
		this.working = working;
	}

	public Integer getSmoke() {
		return smoke;
	}

	public void setSmoke(Integer smoke) {
		this.smoke = smoke;
	}

	public Integer getDrink() {
		return drink;
	}

	public void setDrink(Integer drink) {
		this.drink = drink;
	}

	public String getSportType() {
		return sportType;
	}

	public void setSportType(String sportType) {
		this.sportType = sportType;
	}

	public Integer getSportTime() {
		return sportTime;
	}

	public void setSportTime(Integer sportTime) {
		this.sportTime = sportTime;
	}

	public String getSportTimeZone() {
		return sportTimeZone;
	}

	public void setSportTimeZone(String sportTimeZone) {
		this.sportTimeZone = sportTimeZone;
	}

	public Integer getPhysicalType() {
		return physicalType;
	}

	public void setPhysicalType(Integer physicalType) {
		this.physicalType = physicalType;
	}

	public String getBmi() {
		return bmi;
	}

	public void setBmi(String bmi) {
		this.bmi = bmi;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getHmName() {
		return hmName;
	}

	public void setHmName(String hmName) {
		this.hmName = hmName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getBeer() {
		return beer;
	}

	public void setBeer(String beer) {
		this.beer = beer;
	}

	public String getWhite() {
		return white;
	}

	public void setWhite(String white) {
		this.white = white;
	}

	public String getRed() {
		return red;
	}

	public void setRed(String red) {
		this.red = red;
	}

	public Integer getSportCount() {
		return sportCount;
	}

	public void setSportCount(Integer sportCount) {
		this.sportCount = sportCount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getIsRead() {
		return isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	
}
