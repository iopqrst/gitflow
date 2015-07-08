package com.bskcare.ch.vo.client;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 门诊记录
 */
@Entity
@Table(name = "t_case_outpatient_records")
public class OutpatientRecords implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer clientId;// 用户id
	private Integer hisId;// 医院Id
	private Integer secId;// 科室Id
	private Integer docId;// 医生(专家)Id
	private String complained;// 主诉
	private String illnessHistory;// 现病史
	private String physicalCheck;// 体格检查
	private String laboratoryTest;// 实验室检查
	private String diagnosis;// 诊断
	private String prescription;// 处方
	private Date clinicTime;// 就诊时间
	private int status;// 0:正常，1：删除（逻辑）
	private String hisName;// 医院名称
	private String secName;// 科室名称
	private String docName;// 医生名称
	private int hisInputType;// 添加医院方式 ,0：从医院表中选择,1：用户手动输入
	private Date createTime;// 创建时间

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getHisId() {
		return hisId;
	}

	public void setHisId(Integer hisId) {
		this.hisId = hisId;
	}

	public Integer getSecId() {
		return secId;
	}

	public void setSecId(Integer secId) {
		this.secId = secId;
	}

	public Integer getDocId() {
		return docId;
	}

	public void setDocId(Integer docId) {
		this.docId = docId;
	}

	public String getComplained() {
		return complained;
	}

	public void setComplained(String complained) {
		this.complained = complained;
	}

	public String getIllnessHistory() {
		return illnessHistory;
	}

	public void setIllnessHistory(String illnessHistory) {
		this.illnessHistory = illnessHistory;
	}

	public String getPhysicalCheck() {
		return physicalCheck;
	}

	public void setPhysicalCheck(String physicalCheck) {
		this.physicalCheck = physicalCheck;
	}

	public String getLaboratoryTest() {
		return laboratoryTest;
	}

	public void setLaboratoryTest(String laboratoryTest) {
		this.laboratoryTest = laboratoryTest;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public String getPrescription() {
		return prescription;
	}

	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}

	public Date getClinicTime() {
		return clinicTime;
	}

	public void setClinicTime(Date clinicTime) {
		this.clinicTime = clinicTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getHisName() {
		return hisName;
	}

	public void setHisName(String hisName) {
		this.hisName = hisName;
	}

	public String getSecName() {
		return secName;
	}

	public void setSecName(String secName) {
		this.secName = secName;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public int getHisInputType() {
		return hisInputType;
	}

	public void setHisInputType(int hisInputType) {
		this.hisInputType = hisInputType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public OutpatientRecords() {
		super();
	}

	@Column(name = "clientId")
	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public OutpatientRecords(Integer clientId, String complained,
			String diagnosis, String prescription, Date clinicTime,
			String hisName, Date createTime) {
		super();
		this.clientId = clientId;
		this.complained = complained;
		this.diagnosis = diagnosis;
		this.prescription = prescription;
		this.clinicTime = clinicTime;
		this.hisName = hisName;
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "OutpatientRecords [clientid=" + clientId + ", clinicTime="
				+ clinicTime + ", complained=" + complained + ", createTime="
				+ createTime + ", diagnosis=" + diagnosis + ", docId=" + docId
				+ ", docName=" + docName + ", hisId=" + hisId
				+ ", hisInputType=" + hisInputType + ", hisName=" + hisName
				+ ", id=" + id + ", illnessHistory=" + illnessHistory
				+ ", laboratoryTest=" + laboratoryTest + ", physicalCheck="
				+ physicalCheck + ", prescription=" + prescription + ", secId="
				+ secId + ", secName=" + secName + ", status=" + status + "]";
	}

}
