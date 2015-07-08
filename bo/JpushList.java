package com.bskcare.ch.bo;

import java.util.Date;

public class JpushList {
	private static final long serialVersionUID = 1L;
	/**
	 * 类型  1:心电图 2：血氧  3：血压4：血糖  5:餐后2h血糖 6：体温
	 */
	public static final int TYPE_ECG = 1;
	/**
	 * 类型  1:心电图 2：血氧  3：血压4：血糖  5:餐后2h血糖 6：体温
	 */
	public static final int TYPE_OXYGEN = 2;
	/**
	 * 类型  1:心电图 2：血氧  3：血压4：血糖  5:餐后2h血糖 6：体温
	 */
	public static final int TYPE_PRESSURE = 3;
	/**
	 * 类型  1:心电图 2：血氧  3：血压4：血糖  5:餐后2h血糖 6：体温
	 */
	public static final int TYPE_SUGAR = 4;
	/**
	 * 类型  1:心电图 2：血氧  3：血压4：血糖  5:餐后2h血糖 6：体温
	 */
	public static final int TYPE_SUGAR_2H = 5;
	/**
	 * 类型  1:心电图 2：血氧  3：血压4：血糖  5:餐后2h血糖 6：体温
	 */
	public static final int TYPE_TEMPERATURE = 6;

	private String moblie; //我的手机号
	
	private String content; // 内容
	
	private Date testdate; //测试时间
	
	private Integer type; // 类型 
	

	public String getMoblie() {
		return moblie;
	}

	public void setMoblie(String moblie) {
		this.moblie = moblie;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	

	public Date getTestdate() {
		return testdate;
	}

	public void setTestdate(Date testdate) {
		this.testdate = testdate;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}	
	
}
