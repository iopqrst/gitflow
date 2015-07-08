package com.bskcare.ch.vo.client;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_physical_detail")
public class PhysicalDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer pdId;
	private String code;
	private String name;
	private String up;
	private String down;
	private String content;
	private int flag;
	private Integer userId;
	private Date createTime;
	private int isCommon;
	private String units;
	private String verifyUp;
	private String verifyDown;

	/**
	 * isCommon=1 在页面显示
	 */
	public static final int ISCOMMON_NORMAL = 1;
	/**
	 * isCommon=0 在弹出框显示
	 */
	public static final int ISCOMMON_NOTNORMAL = 0;

	@Id
	@GeneratedValue
	public Integer getPdId() {
		return pdId;
	}

	public void setPdId(Integer pdId) {
		this.pdId = pdId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUp() {
		return up;
	}

	public void setUp(String up) {
		this.up = up;
	}

	public String getDown() {
		return down;
	}

	public void setDown(String down) {
		this.down = down;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getIsCommon() {
		return isCommon;
	}

	public void setIsCommon(int isCommon) {
		this.isCommon = isCommon;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public String getVerifyUp() {
		return verifyUp;
	}

	public void setVerifyUp(String verifyUp) {
		this.verifyUp = verifyUp;
	}

	public String getVerifyDown() {
		return verifyDown;
	}

	public void setVerifyDown(String verifyDown) {
		this.verifyDown = verifyDown;
	}

	@Override
	public String toString() {
		return "PhysicalDetail [code=" + code + ", content=" + content
				+ ", createTime=" + createTime + ", down=" + down + ", flag="
				+ flag + ", isCommon=" + isCommon + ", name=" + name
				+ ", pdId=" + pdId + ", units=" + units + ", up=" + up
				+ ", userId=" + userId + ", verifyDown=" + verifyDown
				+ ", verifyUp=" + verifyUp + "]";
	}

}
