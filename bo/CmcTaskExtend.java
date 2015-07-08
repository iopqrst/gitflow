package com.bskcare.ch.bo;

import com.bskcare.ch.vo.CmcTask;

public class CmcTaskExtend extends CmcTask {

	private static final long serialVersionUID = 1L;

	private String mainconstitution;
	private String clientName;
	private String mobile;
	private Integer age;
	private Integer gender;
	private Integer areaId;
	private String areaName;
	private Integer clientId;

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getMainconstitution() {
		return mainconstitution;
	}

	public void setMainconstitution(String mainconstitution) {
		this.mainconstitution = mainconstitution;
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

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
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

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

}
