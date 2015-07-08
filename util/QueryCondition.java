package com.bskcare.ch.query.util;

import java.util.Date;

/**
 * 查询条件
 * 
 * @author houzhiqing
 * 
 */
public class QueryCondition {

	/**
	 * 查询开始时间
	 */
	private Date beginTime;

	/**
	 * 查询结束时间
	 */
	private Date endTime;

	/**
	 * 区域名称
	 */
	private String areaName;
	/**
	 * 管理员用户Id
	 */
	private Integer userId;
	/**
	 * 客户姓名
	 */
	private String clientName;
	private String mobile;
	/**
	 * 产品名称
	 */
	private String productName;

	/**
	 * 是否需要转换html代码
	 */
	private boolean needConvert = false;
	/**
	 * 地区链
	 */
	private String areaChain;

	/**
	 * 是否查询一周未上传数据的用户
	 */
	private int noUploadWeek;

	/**
	 * 是否有疾病
	 */
	private String hasDisease;

	/**
	 * 注册来源
	 */
	private String regSource;

	private String bazzaarGradeQuery;

	private String flag; // 用户标记

	public String getBazzaarGradeQuery() {
		return bazzaarGradeQuery;
	}

	public void setBazzaarGradeQuery(String bazzaarGradeQuery) {
		this.bazzaarGradeQuery = bazzaarGradeQuery;
	}

	public String getRegSource() {
		return regSource;
	}

	public void setRegSource(String regSource) {
		this.regSource = regSource;
	}

	public String getHasDisease() {
		return hasDisease;
	}

	public void setHasDisease(String hasDisease) {
		this.hasDisease = hasDisease;
	}

	public int getNoUploadWeek() {
		return noUploadWeek;
	}

	public void setNoUploadWeek(int noUploadWeek) {
		this.noUploadWeek = noUploadWeek;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getAreaChain() {
		return areaChain;
	}

	public void setAreaChain(String areaChain) {
		this.areaChain = areaChain;
	}

	public boolean isNeedConvert() {
		return needConvert;
	}

	public void setNeedConvert(boolean needConvert) {
		this.needConvert = needConvert;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "QueryCondition [areaName=" + areaName + ", beginTime="
				+ beginTime + ", clientName=" + clientName + ", endTime="
				+ endTime + ", mobile=" + mobile + ", productName="
				+ productName + ", userId=" + userId + "]";
	}

}
