package com.bskcare.ch.bo;

import com.bskcare.ch.vo.FollowUpVisits;

/**
 * 随访记录扩展类
 */
public class FollowUpVisitsExtends extends FollowUpVisits {
	private static final long serialVersionUID = 1L;
	/**
	 * 管理员名称
	 */
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
