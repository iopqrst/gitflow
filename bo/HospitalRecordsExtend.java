package com.bskcare.ch.bo;

import com.bskcare.ch.vo.client.HospitalRecords;

/***
 * 门诊记录扩展对象
 * 
 */
public class HospitalRecordsExtend extends HospitalRecords {

	private static final long serialVersionUID = 1L;

	private String hName; // 医院名称
	private String sName; // 科室名称
	private String dName; // 医生

	private String clientName; // 客户名称

	public String gethName() {
		return hName;
	}

	public void sethName(String hName) {
		this.hName = hName;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public String getdName() {
		return dName;
	}

	public void setdName(String dName) {
		this.dName = dName;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	@Override
	public String toString() {
		return "OutpatientRecordsExtend [clientName=" + clientName + ", dName="
				+ dName + ", hName=" + hName + ", sName=" + sName + "]";
	}

}
