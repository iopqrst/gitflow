package com.bskcare.ch.vo.client;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 科室
 */
@Entity
@Table(name = "t_case_section")
public class ClientSection implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer secId;// 科室编号
	private int hisId;// 医院ID
	private String secName;// 科室名称
	private String secTel;// 联系电话
	private String secAddress;// 联系地址
	private String secRemark;// 描述
	private String secTags;// 特色标签
	private int secStatus;// 是否启用,0 启用 ,1：没有启用

	@Id
	@GeneratedValue
	public Integer getSecId() {
		return secId;
	}

	public void setSecId(Integer secId) {
		this.secId = secId;
	}

	public int getHisId() {
		return hisId;
	}

	public void setHisId(int hisId) {
		this.hisId = hisId;
	}

	public String getSecName() {
		return secName;
	}

	public void setSecName(String secName) {
		this.secName = secName;
	}

	public String getSecTel() {
		return secTel;
	}

	public void setSecTel(String secTel) {
		this.secTel = secTel;
	}

	public String getSecAddress() {
		return secAddress;
	}

	public void setSecAddress(String secAddress) {
		this.secAddress = secAddress;
	}

	public String getSecRemark() {
		return secRemark;
	}

	public void setSecRemark(String secRemark) {
		this.secRemark = secRemark;
	}

	public String getSecTags() {
		return secTags;
	}

	public void setSecTags(String secTags) {
		this.secTags = secTags;
	}

	public int getSecStatus() {
		return secStatus;
	}

	public void setSecStatus(int secStatus) {
		this.secStatus = secStatus;
	}

	@Override
	public String toString() {
		return "ClientSection [hisId=" + hisId + ", secAddress=" + secAddress
				+ ", secId=" + secId + ", secName=" + secName + ", secRemark="
				+ secRemark + ", secStatus=" + secStatus + ", secTags="
				+ secTags + ", secTel=" + secTel + "]";
	}
}
