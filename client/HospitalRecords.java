package com.bskcare.ch.vo.client;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 住院记录
 */
@Entity
@Table(name = "t_case_hospital_records")
public class HospitalRecords implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private int clientId;// 用户id
	private int hisId;// 医院Id
	private int secId;// 科室Id
	private int docId;// 医生(专家)Id
	private String inDiagnosis;// 入院诊断
	private String inSituation;// 入院情况
	private String inDate;// 入院时间
	private String outDiagnosis;// 出院诊断
	private String outSituation;// 出院情况
	private String outDate;// 出院时间
	private String doctorTold;// 出院医嘱
	private String treatmentProcess;// 诊疗经过
	private String importantCheck;// 住院重要检查
	private String hisName;// 医院名称
	private String secName;// 科室名称
	private String docName;// 医生名称
	private int hisInputType;// 添加医院方式,0：从医院表中选择,1：用户手动输入
	private int status;// 0:正常，1：删除
	private Date createTime;// 创建时间
	private String filePath;// 文件存储路径

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public int getHisId() {
		return hisId;
	}

	public void setHisId(int hisId) {
		this.hisId = hisId;
	}

	public int getSecId() {
		return secId;
	}

	public void setSecId(int secId) {
		this.secId = secId;
	}

	public int getDocId() {
		return docId;
	}

	public void setDocId(int docId) {
		this.docId = docId;
	}

	public String getInDiagnosis() {
		return inDiagnosis;
	}

	public void setInDiagnosis(String inDiagnosis) {
		this.inDiagnosis = inDiagnosis;
	}

	public String getInSituation() {
		return inSituation;
	}

	public void setInSituation(String inSituation) {
		this.inSituation = inSituation;
	}

	public String getInDate() {
		return inDate;
	}

	public void setInDate(String inDate) {
		this.inDate = inDate;
	}

	public String getOutDiagnosis() {
		return outDiagnosis;
	}

	public void setOutDiagnosis(String outDiagnosis) {
		this.outDiagnosis = outDiagnosis;
	}

	public String getOutSituation() {
		return outSituation;
	}

	public void setOutSituation(String outSituation) {
		this.outSituation = outSituation;
	}

	public String getOutDate() {
		return outDate;
	}

	public void setOutDate(String outDate) {
		this.outDate = outDate;
	}

	public String getDoctorTold() {
		return doctorTold;
	}

	public void setDoctorTold(String doctorTold) {
		this.doctorTold = doctorTold;
	}

	public String getTreatmentProcess() {
		return treatmentProcess;
	}

	public void setTreatmentProcess(String treatmentProcess) {
		this.treatmentProcess = treatmentProcess;
	}

	public String getImportantCheck() {
		return importantCheck;
	}

	public void setImportantCheck(String importantCheck) {
		this.importantCheck = importantCheck;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public HospitalRecords() {
		super();
	}

	public HospitalRecords(int clientId, String hisName, String inDiagnosis,
			String inDate, String outDate, String treatmentProcess,
			String docName, Date createTime) {
		super();
		this.clientId = clientId;
		this.hisName = hisName;
		this.inDiagnosis = inDiagnosis;
		this.inDate = inDate;
		this.outDate = outDate;
		this.treatmentProcess = treatmentProcess;
		this.docName = docName;
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "HospitalRecords [clientId=" + clientId + ", createTime="
				+ createTime + ", docId=" + docId + ", docName=" + docName
				+ ", doctorTold=" + doctorTold + ", filePath=" + filePath
				+ ", hisId=" + hisId + ", hisInputType=" + hisInputType
				+ ", hisName=" + hisName + ", id=" + id + ", importantCheck="
				+ importantCheck + ", inDate=" + inDate + ", inDiagnosis="
				+ inDiagnosis + ", inSituation=" + inSituation + ", outDate="
				+ outDate + ", outDiagnosis=" + outDiagnosis
				+ ", outSituation=" + outSituation + ", secId=" + secId
				+ ", secName=" + secName + ", status=" + status
				+ ", treatmentProcess=" + treatmentProcess + "]";
	}
}
