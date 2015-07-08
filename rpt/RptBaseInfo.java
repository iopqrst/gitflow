package com.bskcare.ch.vo.rpt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 健康报告个人基本信息
 */

@Entity
@Table(name = "rpt_baseinfo")
public class RptBaseInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 0: 自动生成 1：草稿 2：已提交 3：已删除
	 */
	public static final int RPT_STATUS_AUTO = 0;
	public static final int RPT_STATUS_DRAFT = 1;
	public static final int RPT_STATUS_COMMIT = 2;
	public static final int RPT_STATUS_DELETE = 3;
	public static final int RPT_TYPE = 1;
	
	/**
	 * status 健康报告状态 0：未提交  1：已提交 
	 */
	public static final int RPT_STATUS_UPDATE = 1;
	public static final int RPT_STATUS_NOT_UPDATE = 0;
	
	
	/**
	 * 用户是否已经阅读健康报告
	 */
	public static final int RPT_NOREAD = 0;
	public static final int RPT_READ = 1;
	
	/**
	 * 是否解读（0：未解读  1：已解读）
	 */
	public static final int RPT_READING = 0;
	public static final int RPT_NO_READING = 1;
	

	private Integer rptId;
	private Integer clientId;
	private String name;
	private Integer gender;
	private String docName;
	private String hmName;
	private Integer age;
	private String height;
	private String weight;
	private String mobile;
	private String bmi;
	/**
	 * 疾病史
	 */
	private String medicalHistory;
	/**
	 * 手术史：有值就填，每值为空
	 */
	private String surgicalHistory;
	/**
	 * 输血史
	 */
	private String transfusionOfBlood;
	/**
	 * 外伤史
	 */
	private String traumaHistory;
	/**
	 * 过敏史
	 */
	private String allergyHistory;
	/**
	 * 家族史
	 */
	private String familyHistory;
	/**
	 * 收缩压
	 */
	private String sbp;
	/**
	 * 舒张压
	 */
	private String dbp;
	/**
	 * 空腹血糖
	 */
	private String glu;
	/**
	 * 总胆固醇
	 */
	private String tc;
	/**
	 * 甘油三酯
	 */
	private String tg;
	/**
	 * 高密度胆固醇
	 */
	private String hdl;
	/**
	 * 低密度胆固醇
	 */
	private String ldl;
	/**
	 * 吸烟状况1：是 2:否
	 */
	private Integer smoke;
	/**
	 * 日均吸烟量（支）
	 */
	private Integer average;
	/**
	 * 吸烟年限
	 */
	private Integer syear;
	/**
	 * 是否喝酒(1-从来不喝；2-喝)
	 */
	private Integer drink;
	/**
	 * 饮酒年限
	 */
	private Integer dyear;
	/**
	 * 啤酒
	 */
	private String white;
	/**
	 * 啤酒
	 */
	private String beer;
	/**
	 * 红酒
	 */
	private String red;

	/**
	 * 工作情况:1 : 紧张 2 ：清闲
	 */
	private int working;

	/**
	 * 睡眠: 1: 充足 （6-8） 2： 不足 3： 严重失眠
	 */
	private int sleeping;
	/**
	 * 饮食：1： 三餐不规律 2： 常不吃早饭 3： 常暴饮暴食 4：嗜咸 5：嗜甜 6：嗜油炸 7：嗜辣 8：嗜素食
	 */
	private String diet;

	/**
	 * 每周运动次数
	 */
	private Integer sportCount;

	/**
	 * 每周运动时间
	 */
	private int sportTime;
	/**
	 * 锻炼方式 ： 1 : 跑步 2 : 游泳 3 ： 球类 4 ： 太极拳 5 ： 其他（填写）
	 */
	private String sportType;
	/**
	 * 其他运动方式
	 */
	private String sportSupply;

	/**
	 * 问卷状况说明
	 */
	private String surveyMsg;
	/**
	 * 健康评估
	 */
	private String healthAssessment;
	/**
	 * 医生审核状态0: 未审核 1：已审核 
	 */
	private Integer docStatus;
	
	/**
	 * 健康管理师审核状态0: 未审核 1：已审核 
	 */
	private int hmStatus;
	
	/**
	 * 饮食审核状态0: 未审核 1：已审核 
	 */
	private Integer dietStatus;
	/**
	 * 运动审核状态0: 未审核 1：已审核 
	 */
	private Integer sportStatus;
	
	/**
	 * 运动审核状态0: 自动生成 1：草稿 2：已提交 3：已删除
	 */
	private Integer status;

	private Date createTime;

	// 是否阅读
	private int isRead;

	// 主体质
	private String mainCmc;

	// 血压平均值
	private String bloodPressureAverage;

	// 心率平均值
	private String heartReatAverage;
	/**
	 * 血氧饱和度平均值
	 */
	private String oxygenAverage;
	
	/**
	 * 体力活动 1：极轻体力活动 2：轻体力活动 3：中等体力活动 4：重体力活动
	 */
	private int physicalType;
	
	//开始时间
	private Date beginTime;
	/**
	 * 是否解读（0：未解读  1：已解读）
	 */
	private int reading;


	@Id
	@GeneratedValue
	public Integer getRptId() {
		return rptId;
	}

	public void setRptId(Integer rptId) {
		this.rptId = rptId;
	}

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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMedicalHistory() {
		return medicalHistory;
	}

	public void setMedicalHistory(String medicalHistory) {
		this.medicalHistory = medicalHistory;
	}

	public String getSurgicalHistory() {
		return surgicalHistory;
	}

	public void setSurgicalHistory(String surgicalHistory) {
		this.surgicalHistory = surgicalHistory;
	}

	public String getTransfusionOfBlood() {
		return transfusionOfBlood;
	}

	public void setTransfusionOfBlood(String transfusionOfBlood) {
		this.transfusionOfBlood = transfusionOfBlood;
	}

	public String getTraumaHistory() {
		return traumaHistory;
	}

	public void setTraumaHistory(String traumaHistory) {
		this.traumaHistory = traumaHistory;
	}

	public String getAllergyHistory() {
		return allergyHistory;
	}

	public void setAllergyHistory(String allergyHistory) {
		this.allergyHistory = allergyHistory;
	}

	public String getFamilyHistory() {
		return familyHistory;
	}

	public void setFamilyHistory(String familyHistory) {
		this.familyHistory = familyHistory;
	}

	public String getSbp() {
		return sbp;
	}

	public void setSbp(String sbp) {
		this.sbp = sbp;
	}

	public String getDbp() {
		return dbp;
	}

	public void setDbp(String dbp) {
		this.dbp = dbp;
	}

	public String getGlu() {
		return glu;
	}

	public void setGlu(String glu) {
		this.glu = glu;
	}

	public String getTc() {
		return tc;
	}

	public void setTc(String tc) {
		this.tc = tc;
	}

	public String getTg() {
		return tg;
	}

	public void setTg(String tg) {
		this.tg = tg;
	}

	public String getBmi() {
		return bmi;
	}

	public void setBmi(String bmi) {
		this.bmi = bmi;
	}

	public String getHdl() {
		return hdl;
	}

	public void setHdl(String hdl) {
		this.hdl = hdl;
	}

	public String getLdl() {
		return ldl;
	}

	public void setLdl(String ldl) {
		this.ldl = ldl;
	}

	public Integer getSmoke() {
		return smoke;
	}

	public void setSmoke(Integer smoke) {
		this.smoke = smoke;
	}

	public Integer getAverage() {
		return average;
	}

	public void setAverage(Integer average) {
		this.average = average;
	}

	public Integer getSyear() {
		return syear;
	}

	public void setSyear(Integer syear) {
		this.syear = syear;
	}

	public Integer getDrink() {
		return drink;
	}

	public void setDrink(Integer drink) {
		this.drink = drink;
	}

	public Integer getDyear() {
		return dyear;
	}

	public void setDyear(Integer dyear) {
		this.dyear = dyear;
	}

	public String getWhite() {
		return white;
	}

	public void setWhite(String white) {
		this.white = white;
	}

	public String getBeer() {
		return beer;
	}

	public void setBeer(String beer) {
		this.beer = beer;
	}

	public String getRed() {
		return red;
	}

	public String getOxygenAverage() {
		return oxygenAverage;
	}

	public void setOxygenAverage(String oxygenAverage) {
		this.oxygenAverage = oxygenAverage;
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

	public String getDiet() {
		return diet;
	}

	public void setDiet(String diet) {
		this.diet = diet;
	}

	public Integer getSportCount() {
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

	@Lob
	@Basic(fetch = FetchType.LAZY)
	public String getSurveyMsg() {
		return surveyMsg;
	}

	public void setSurveyMsg(String surveyMsg) {
		this.surveyMsg = surveyMsg;
	}

	@Lob
	@Basic(fetch = FetchType.LAZY)
	public String getHealthAssessment() {
		return healthAssessment;
	}

	public void setHealthAssessment(String healthAssessment) {
		this.healthAssessment = healthAssessment;
	}




	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public int getIsRead() {
		return isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getMainCmc() {
		return mainCmc;
	}

	public void setMainCmc(String mainCmc) {
		this.mainCmc = mainCmc;
	}

	public String getBloodPressureAverage() {
		return bloodPressureAverage;
	}

	public void setBloodPressureAverage(String bloodPressureAverage) {
		this.bloodPressureAverage = bloodPressureAverage;
	}

	public String getHeartReatAverage() {
		return heartReatAverage;
	}

	public void setHeartReatAverage(String heartReatAverage) {
		this.heartReatAverage = heartReatAverage;
	}

	public Integer getDocStatus() {
		return docStatus;
	}

	public void setDocStatus(Integer docStatus) {
		this.docStatus = docStatus;
	}

	public Integer getDietStatus() {
		return dietStatus;
	}

	public void setDietStatus(Integer dietStatus) {
		this.dietStatus = dietStatus;
	}

	public Integer getSportStatus() {
		return sportStatus;
	}

	public void setSportStatus(Integer sportStatus) {
		this.sportStatus = sportStatus;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public int getPhysicalType() {
		return physicalType;
	}

	public void setPhysicalType(int physicalType) {
		this.physicalType = physicalType;
	}
	
	public Date getBeginTime() {
		return beginTime;
	}
	
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	
	public int getReading() {
		return reading;
	}

	public void setReading(int reading) {
		this.reading = reading;
	}
	
	public int getHmStatus() {
		return hmStatus;
	}

	public void setHmStatus(int hmStatus) {
		this.hmStatus = hmStatus;
	}

	@Override
	public String toString() {
		return "RptBaseInfo [age=" + age + ", allergyHistory=" + allergyHistory
				+ ", average=" + average + ", beer=" + beer
				+ ", bloodPressureAverage=" + bloodPressureAverage + ", bmi="
				+ bmi + ", clientId=" + clientId + ", createTime=" + createTime
				+ ", dbp=" + dbp + ", diet=" + diet + ", dietStatus="
				+ dietStatus + ", docName=" + docName + ", docStatus="
				+ docStatus + ", drink=" + drink + ", dyear=" + dyear
				+ ", familyHistory=" + familyHistory + ", gender=" + gender
				+ ", glu=" + glu + ", hdl=" + hdl + ", healthAssessment="
				+ healthAssessment + ", heartReatAverage=" + heartReatAverage
				+ ", height=" + height + ", hmName=" + hmName + ", isRead="
				+ isRead + ", ldl=" + ldl + ", mainCmc=" + mainCmc
				+ ", medicalHistory=" + medicalHistory + ", mobile=" + mobile
				+ ", name=" + name + ", oxygenAverage=" + oxygenAverage
				+ ", physicalType=" + physicalType + ", red=" + red
				+ ", rptId=" + rptId + ", sbp=" + sbp + ", sleeping="
				+ sleeping + ", smoke=" + smoke + ", sportCount=" + sportCount
				+ ", sportStatus=" + sportStatus + ", sportSupply="
				+ sportSupply + ", sportTime=" + sportTime + ", sportType="
				+ sportType + ", status=" + status + ", surgicalHistory="
				+ surgicalHistory + ", surveyMsg=" + surveyMsg + ", syear="
				+ syear + ", tc=" + tc + ", tg=" + tg + ", transfusionOfBlood="
				+ transfusionOfBlood + ", traumaHistory=" + traumaHistory
				+ ", weight=" + weight + ", white=" + white + ", working="
				+ working + "]";
	}

}
