package com.bskcare.ch.vo.msport;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "m_sport_activity")
public class MSportActivity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer aid;
	/**
	 * 创建人id(一般为clientId)
	 */
	private Integer creator;
	private String name;
	private String pwd;
	private String intro;
	private String icon;//活动头像路径
	private Date createTime;

	@Id
	@GeneratedValue
	public Integer getAid() {
		return aid;
	}

	public void setAid(Integer aid) {
		this.aid = aid;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}


	@Override
	public String toString() {
		return "MSportActivity [aid=" + aid + ", createTime=" + createTime
				+ ", creator=" + creator + ", icon=" + icon + ", intro="
				+ intro + ", name=" + name + ", pwd=" + pwd + "]";
	}

}
