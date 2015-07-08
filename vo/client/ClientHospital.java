package com.bskcare.ch.vo.client;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 医院
 */
@Entity
@Table(name = "t_case_hospital")
public class ClientHospital implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer hisId;// 医院id
	private int areaId;// 区域ID
	private String hisName;// 医院名称
	private String telephone;// 联系电话
	private String address;// 地址
	private String url;// 网站
	private String remark;// 描述
	private String tags;// 医院特色
	private int status;// 是否启用(0 启用 1：没有启用

	@Id
	@GeneratedValue
	public Integer getHisId() {
		return hisId;
	}

	public void setHisId(Integer hisId) {
		this.hisId = hisId;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public String getHisName() {
		return hisName;
	}

	public void setHisName(String hisName) {
		this.hisName = hisName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ClientHospital [address=" + address + ", areaId=" + areaId
				+ ", hisId=" + hisId + ", hisName=" + hisName + ", remark="
				+ remark + ", status=" + status + ", tags=" + tags
				+ ", telephone=" + telephone + ", url=" + url + "]";
	}

}
