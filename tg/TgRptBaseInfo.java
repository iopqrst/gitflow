package com.bskcare.ch.vo.tg;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tg_rpt_baseinfo")
public class TgRptBaseInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer evalId;
	private Integer clientId;
	/**
	 * 客户姓名
	 */
	private String name;
	/**
	 * 发病概率
	 */
	private double probability;
	/**
	 * 上次测试发病概率
	 */
	private double lastProbability;
	/**
	 * 同龄人正常人发病概率
	 */
	private double ageProbability;
	/**
	 * 是否有风险因素
	 */
	private int isRisk;
	/**
	 * 评测结果信息
	 */
	private String resultInfo;
	/**
	 * 年龄信息
	 */
	private String ageInfo;
	/**
	 * 体重信息
	 */
	private String weightInfo;
	/**
	 * 运动信息
	 */
	private String sportInfo;
	/**
	 * 糖尿病家族病史信息
	 */
	private String bsFamilyInfo;
	/**
	 * 睡眠信息
	 */
	private String sleepInfo;
	/**
	 * 血压、血脂标题
	 */
	private String lipidsTitle;
	/**
	 * 血压、血脂信息
	 */
	private String lipids;
	/**
	 *合并症信息
	 */
	private String compliation;
	/**
	 * 评测结果
	 */
	private String result;
	/**
	 * 上次的评测结果
	 */
	private String lastResult;
	/**
	 * 近期目标
	 */
	private String target;
	/**
	 * 远期目标
	 */
	private String longTarget;
	/**
	 * 可控因素
	 */
	private String risks;
	/**
	 * 不可控因素
	 */
	private String unrisks;
	/**
	 * 目标完成情况
	 */
	private String targetFinish;
	/**
	 * 目标没有完成的情况
	 */
	private String targetNoFinish;
	private int number;
	private Date createTime;
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getEvalId() {
		return evalId;
	}
	public void setEvalId(Integer evalId) {
		this.evalId = evalId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public double getProbability() {
		return probability;
	}
	public void setProbability(double probability) {
		this.probability = probability;
	}
	public double getLastProbability() {
		return lastProbability;
	}
	public void setLastProbability(double lastProbability) {
		this.lastProbability = lastProbability;
	}
	public double getAgeProbability() {
		return ageProbability;
	}
	public void setAgeProbability(double ageProbability) {
		this.ageProbability = ageProbability;
	}
	public int getIsRisk() {
		return isRisk;
	}
	public void setIsRisk(int isRisk) {
		this.isRisk = isRisk;
	}
	public String getResultInfo() {
		return resultInfo;
	}
	public void setResultInfo(String resultInfo) {
		this.resultInfo = resultInfo;
	}
	public String getAgeInfo() {
		return ageInfo;
	}
	public void setAgeInfo(String ageInfo) {
		this.ageInfo = ageInfo;
	}
	public String getWeightInfo() {
		return weightInfo;
	}
	public void setWeightInfo(String weightInfo) {
		this.weightInfo = weightInfo;
	}
	public String getSportInfo() {
		return sportInfo;
	}
	public void setSportInfo(String sportInfo) {
		this.sportInfo = sportInfo;
	}
	public String getBsFamilyInfo() {
		return bsFamilyInfo;
	}
	public void setBsFamilyInfo(String bsFamilyInfo) {
		this.bsFamilyInfo = bsFamilyInfo;
	}
	public String getSleepInfo() {
		return sleepInfo;
	}
	public void setSleepInfo(String sleepInfo) {
		this.sleepInfo = sleepInfo;
	}
	public String getLipids() {
		return lipids;
	}
	public void setLipids(String lipids) {
		this.lipids = lipids;
	}
	public String getCompliation() {
		return compliation;
	}
	public void setCompliation(String compliation) {
		this.compliation = compliation;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getLongTarget() {
		return longTarget;
	}
	public void setLongTarget(String longTarget) {
		this.longTarget = longTarget;
	}
	public String getRisks() {
		return risks;
	}
	public void setRisks(String risks) {
		this.risks = risks;
	}
	public String getUnrisks() {
		return unrisks;
	}
	public void setUnrisks(String unrisks) {
		this.unrisks = unrisks;
	}
	public String getTargetFinish() {
		return targetFinish;
	}
	public void setTargetFinish(String targetFinish) {
		this.targetFinish = targetFinish;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
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
	public String getLastResult() {
		return lastResult;
	}
	public void setLastResult(String lastResult) {
		this.lastResult = lastResult;
	}
	public String getLipidsTitle() {
		return lipidsTitle;
	}
	public void setLipidsTitle(String lipidsTitle) {
		this.lipidsTitle = lipidsTitle;
	}
	public String getTargetNoFinish() {
		return targetNoFinish;
	}
	public void setTargetNoFinish(String targetNoFinish) {
		this.targetNoFinish = targetNoFinish;
	}
}
