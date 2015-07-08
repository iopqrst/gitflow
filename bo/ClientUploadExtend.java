package com.bskcare.ch.bo;

import com.bskcare.ch.vo.client.ClientUpload;

/***
 * 门诊记录扩展对象
 * 
 */
public class ClientUploadExtend extends ClientUpload {

	private static final long serialVersionUID = 1L;

	private String clientName; // 客户名称
	private Integer gender; // 性别 男：0 女：1
	private String mobile; // 手机
	private Integer age;// 年龄
	private String areaName;// 地区名称
	private Integer uploadId;// 上传文件编号
	private Integer uploadStatus;// 上传文件状态
	private Integer areaId;

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getUploadId() {
		return uploadId;
	}

	public void setUploadId(Integer uploadId) {
		this.uploadId = uploadId;
	}

	public Integer getUploadStatus() {
		return uploadStatus;
	}

	public void setUploadStatus(Integer uploadStatus) {
		this.uploadStatus = uploadStatus;
	}

	@Override
	public String toString() {
		return "ClientUploadExtend [age=" + age + ", areaId=" + areaId
				+ ", areaName=" + areaName + ", clientName=" + clientName
				+ ", gender=" + gender + ", mobile=" + mobile + ", uploadId="
				+ uploadId + ", uploadStatus=" + uploadStatus + "]";
	}

}
