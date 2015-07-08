package com.bskcare.ch.bo;

import java.io.Serializable;

import com.bskcare.ch.vo.online.OnlineRecored;

/**
 * 在线聊天扩展累
 */
public class OnlineRecoredExtend extends OnlineRecored implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String cName;
	private String mobile;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Override
	public String toString() {
		return "OnlineRecoredExtend [cName=" + cName + ", id=" + id
				+ ", mobile=" + mobile + "]";
	}

}
