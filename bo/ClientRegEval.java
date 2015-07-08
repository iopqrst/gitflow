package com.bskcare.ch.bo;

import java.io.Serializable;
import java.util.Date;

public class ClientRegEval implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int count;
	private Date createTime;
	private int regSelfCount;
	private int doctorInviteCount;
	private int clientInviteCount;
	private int otherCount;
	private int type;
	/**
	 * 评估人数
	 */
	private int evalClientCount;
	/**
	 * 咨询人数
	 */
	private int pciClientCount;
	/**
	 * 上传血糖人数
	 */
	private int uploadBloodSugarCount;
	
	/**
	 * 七天平均注册数
	 */
	private int avgClientCount;
	
	/**
	 * 七日平均风险评估人数
	 */
	private int avgEvalCount;
	
	/**
	 * 七日平均咨询人数
	 */
	private int avgPciCount;
	
	/**
	 * 七日平均上传血糖数据人数
	 */
	private int avgUploadBloodSugarCount;
	
	
	public int getAvgClientCount() {
		return avgClientCount;
	}
	public void setAvgClientCount(int avgClientCount) {
		this.avgClientCount = avgClientCount;
	}
	public int getAvgEvalCount() {
		return avgEvalCount;
	}
	public void setAvgEvalCount(int avgEvalCount) {
		this.avgEvalCount = avgEvalCount;
	}
	public int getAvgPciCount() {
		return avgPciCount;
	}
	public void setAvgPciCount(int avgPciCount) {
		this.avgPciCount = avgPciCount;
	}
	public int getAvgUploadBloodSugarCount() {
		return avgUploadBloodSugarCount;
	}
	public void setAvgUploadBloodSugarCount(int avgUploadBloodSugarCount) {
		this.avgUploadBloodSugarCount = avgUploadBloodSugarCount;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getEvalClientCount() {
		return evalClientCount;
	}
	public void setEvalClientCount(int evalClientCount) {
		this.evalClientCount = evalClientCount;
	}
	public int getPciClientCount() {
		return pciClientCount;
	}
	public void setPciClientCount(int pciClientCount) {
		this.pciClientCount = pciClientCount;
	}
	public int getUploadBloodSugarCount() {
		return uploadBloodSugarCount;
	}
	public void setUploadBloodSugarCount(int uploadBloodSugarCount) {
		this.uploadBloodSugarCount = uploadBloodSugarCount;
	}
	public int getRegSelfCount() {
		return regSelfCount;
	}
	public void setRegSelfCount(int regSelfCount) {
		this.regSelfCount = regSelfCount;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getDoctorInviteCount() {
		return doctorInviteCount;
	}
	public void setDoctorInviteCount(int doctorInviteCount) {
		this.doctorInviteCount = doctorInviteCount;
	}
	public int getClientInviteCount() {
		return clientInviteCount;
	}
	public void setClientInviteCount(int clientInviteCount) {
		this.clientInviteCount = clientInviteCount;
	}
	public int getOtherCount() {
		return otherCount;
	}
	public void setOtherCount(int otherCount) {
		this.otherCount = otherCount;
	}
}
