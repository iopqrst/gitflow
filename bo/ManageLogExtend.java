package com.bskcare.ch.bo;

import com.bskcare.ch.vo.ManageLog;

public class ManageLogExtend extends ManageLog{

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
