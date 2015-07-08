package com.bskcare.ch.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 体质表
 * 
 * @author Administrator
 */
@Entity
@Table(name = "t_cmc_evaluation")
public class Constitution implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 未处理
	 */
	public static final int CMCNOTDEAL = 0;
	/**
	 * 已处理
	 */
	public static final int CMCDEAL = 1;
	
	private Integer id;
	private Integer clientId;
	private Date testTime;
	private double pinghe;
	private double qixu;
	private double yangxu;
	private double yinxu;
	private double tanshi;
	private double shire;
	private double xueyu;
	private double qiyu;
	private double tebing;
	/**
	 * 主体质
	 */
	private String mainConstitution;
	/**
	 * 兼有体质
	 */
	private String probablyConstitution;
	/**
	 * 倾向体质
	 */
	private String tendencyConstitution;
	private String answer;
	private int status;

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

	public Date getTestTime() {
		return testTime;
	}

	public void setTestTime(Date testTime) {
		this.testTime = testTime;
	}

	public double getPinghe() {
		return pinghe;
	}

	public void setPinghe(double pinghe) {
		this.pinghe = pinghe;
	}

	public double getQixu() {
		return qixu;
	}

	public void setQixu(double qixu) {
		this.qixu = qixu;
	}

	public double getYangxu() {
		return yangxu;
	}

	public void setYangxu(double yangxu) {
		this.yangxu = yangxu;
	}

	public double getYinxu() {
		return yinxu;
	}

	public void setYinxu(double yinxu) {
		this.yinxu = yinxu;
	}

	public double getTanshi() {
		return tanshi;
	}

	public void setTanshi(double tanshi) {
		this.tanshi = tanshi;
	}

	public double getShire() {
		return shire;
	}

	public void setShire(double shire) {
		this.shire = shire;
	}

	public double getXueyu() {
		return xueyu;
	}

	public void setXueyu(double xueyu) {
		this.xueyu = xueyu;
	}

	public double getQiyu() {
		return qiyu;
	}

	public void setQiyu(double qiyu) {
		this.qiyu = qiyu;
	}

	public double getTebing() {
		return tebing;
	}

	public void setTebing(double tebing) {
		this.tebing = tebing;
	}

	public String getMainConstitution() {
		return mainConstitution;
	}

	public void setMainConstitution(String mainConstitution) {
		this.mainConstitution = mainConstitution;
	}

	public String getProbablyConstitution() {
		return probablyConstitution;
	}

	public void setProbablyConstitution(String probablyConstitution) {
		this.probablyConstitution = probablyConstitution;
	}

	public String getTendencyConstitution() {
		return tendencyConstitution;
	}

	public void setTendencyConstitution(String tendencyConstitution) {
		this.tendencyConstitution = tendencyConstitution;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Constitution [answer=" + answer + ", clientId=" + clientId
				+ ", id=" + id + ", mainConstitution=" + mainConstitution
				+ ", pinghe=" + pinghe + ", probabltConstitution="
				+ probablyConstitution + ", qixu=" + qixu + ", qiyu=" + qiyu
				+ ", shire=" + shire + ", status=" + status + ", tanshi="
				+ tanshi + ", tebing=" + tebing + ", tendencyConstitution="
				+ tendencyConstitution + ", testTime=" + testTime + ", xueyu="
				+ xueyu + ", yangxu=" + yangxu + ", yinxu=" + yinxu + "]";
	}

}
