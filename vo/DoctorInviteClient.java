package com.bskcare.ch.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_doctor_invite_client")
public class DoctorInviteClient implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 是否是医生邀请客户 0：是  1：否
	 */
	public static final int INVITE_YES = 0;
	public static final int INVITE_NO = 1;
	

	private Integer id;
	/**
	 * 医生姓名
	 */
	private String name;
	/**
	 * 医生手机号码
	 */
	private String mobile;
	/**
	 * 医生id
	 */
	private Integer doctorId;
	private Date createTime;
	/**
	 * 最后一次邀请客户时间
	 */
	private Date lastInviteTime;
	/**
	 * 是否是医生邀请客户 0：是  1：否
	 */
	private int isInvite;
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastInviteTime() {
		return lastInviteTime;
	}
	public void setLastInviteTime(Date lastInviteTime) {
		this.lastInviteTime = lastInviteTime;
	}
	public int getIsInvite() {
		return isInvite;
	}
	public void setIsInvite(int isInvite) {
		this.isInvite = isInvite;
	}
}
