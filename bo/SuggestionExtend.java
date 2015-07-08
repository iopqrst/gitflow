package com.bskcare.ch.bo;

import com.bskcare.ch.vo.Suggestion;

/**
 * 个人意见扩展类
 */
public class SuggestionExtend extends Suggestion {

	private static final long serialVersionUID = 1L;
	private String clientName;
	private String mobile;
	private String clientId;
	private String ename;
	private String eId;
	private Integer role;

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

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String geteId() {
		return eId;
	}

	public void seteId(String eId) {
		this.eId = eId;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

}
