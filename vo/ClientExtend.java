package com.bskcare.ch.vo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/** 保存注册用户的来源 **/
@Entity
@Table(name = "t_client_extend")
public class ClientExtend {

	private Integer id;
	private Integer clientId;
	/** 最后一次上传数据时间 **/
	private Date lastFollowTime;
	/** 最后一次上次监测数据的时间 **/
	private Date lastUploadDateTime;
	/** 注册来源 **/
	private String source;
	/** 版本号 **/
	private String version;
	private Date regTime;
	private String initiativeinvite ;//主动邀请
	private String passivityinvite ;// 被动邀请
	
	private int totalScore; //总积分
	private int remainScore; //剩余积分
	private int totalCoins; //总金币
	private int remainCoins; //剩余金币
	// 千人推广1
	private int flag; //用户的标记, 千人推广时添加的，用来标记参加千人推广的老用户，也许活动过了就没用了，但是可以用该字段继续标记其他
	
	private int reportScore;
	private int dietScore;
	private int doctorScore;
	private int sportScore;
	private double balance;
	
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

	public Date getLastFollowTime() {
		return lastFollowTime;
	}

	public void setLastFollowTime(Date lastFollowTime) {
		this.lastFollowTime = lastFollowTime;
	}

	public Date getLastUploadDateTime() {
		return lastUploadDateTime;
	}

	public void setLastUploadDateTime(Date lastUploadDateTime) {
		this.lastUploadDateTime = lastUploadDateTime;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	public String getInitiativeinvite() {
		return initiativeinvite;
	}

	public void setInitiativeinvite(String initiativeinvite) {
		this.initiativeinvite = initiativeinvite;
	}

	public String getPassivityinvite() {
		return passivityinvite;
	}

	public void setPassivityinvite(String passivityinvite) {
		this.passivityinvite = passivityinvite;
	}
	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public int getRemainScore() {
		return remainScore;
	}

	public void setRemainScore(int remainScore) {
		this.remainScore = remainScore;
	}

	public int getTotalCoins() {
		return totalCoins;
	}

	public void setTotalCoins(int totalCoins) {
		this.totalCoins = totalCoins;
	}

	public int getRemainCoins() {
		return remainCoins;
	}

	public void setRemainCoins(int remainCoins) {
		this.remainCoins = remainCoins;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getReportScore() {
		return reportScore;
	}

	public void setReportScore(int reportScore) {
		this.reportScore = reportScore;
	}

	public int getDietScore() {
		return dietScore;
	}

	public void setDietScore(int dietScore) {
		this.dietScore = dietScore;
	}

	public int getDoctorScore() {
		return doctorScore;
	}

	public void setDoctorScore(int doctorScore) {
		this.doctorScore = doctorScore;
	}

	public int getSportScore() {
		return sportScore;
	}

	public void setSportScore(int sportScore) {
		this.sportScore = sportScore;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
}
