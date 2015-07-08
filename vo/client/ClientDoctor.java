package com.bskcare.ch.vo.client;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 医生
 */
@Entity
@Table(name = "t_case_doctor")
public class ClientDoctor implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer docId;// 医生编号
	private int docCode;// 专家编号
	private int secId;// 科室ID
	private int hisId;// 医院ID
	private String docName;// 专家名字
	private int docSex;// 性别
	private String docTitle;// 职称
	private String docMobile;// 手机
	private String docTel;// 工作电话
	private String docRemark;// 详细介绍
	private String docTags;// 专长
	private Date docBeginTime;// 出诊时间
	private Date createTime;// 录入时间
	private int userId;// 录入人ID
	private String docPhoto;// 专家头像
	private int docType;// 1预约(专家)2知识和预约 3知识

	@Id
	@GeneratedValue
	public Integer getDocId() {
		return docId;
	}

	public void setDocId(Integer docId) {
		this.docId = docId;
	}

	public int getDocCode() {
		return docCode;
	}

	public void setDocCode(int docCode) {
		this.docCode = docCode;
	}

	public int getSecId() {
		return secId;
	}

	public void setSecId(int secId) {
		this.secId = secId;
	}

	public int getHisId() {
		return hisId;
	}

	public void setHisId(int hisId) {
		this.hisId = hisId;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public int getDocSex() {
		return docSex;
	}

	public void setDocSex(int docSex) {
		this.docSex = docSex;
	}

	public String getDocTitle() {
		return docTitle;
	}

	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}

	public String getDocMobile() {
		return docMobile;
	}

	public void setDocMobile(String docMobile) {
		this.docMobile = docMobile;
	}

	public String getDocTel() {
		return docTel;
	}

	public void setDocTel(String docTel) {
		this.docTel = docTel;
	}

	public String getDocRemark() {
		return docRemark;
	}

	public void setDocRemark(String docRemark) {
		this.docRemark = docRemark;
	}

	public String getDocTags() {
		return docTags;
	}

	public void setDocTags(String docTags) {
		this.docTags = docTags;
	}

	public Date getDocBeginTime() {
		return docBeginTime;
	}

	public void setDocBeginTime(Date docBeginTime) {
		this.docBeginTime = docBeginTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getDocPhoto() {
		return docPhoto;
	}

	public void setDocPhoto(String docPhoto) {
		this.docPhoto = docPhoto;
	}

	public int getDocType() {
		return docType;
	}

	public void setDocType(int docType) {
		this.docType = docType;
	}

	@Override
	public String toString() {
		return "ClientDoctor [createTime=" + createTime + ", docBeginTime="
				+ docBeginTime + ", docCode=" + docCode + ", docId=" + docId
				+ ", docMobile=" + docMobile + ", docName=" + docName
				+ ", docPhoto=" + docPhoto + ", docRemark=" + docRemark
				+ ", docSex=" + docSex + ", docTags=" + docTags + ", docTel="
				+ docTel + ", docTitle=" + docTitle + ", docType=" + docType
				+ ", hisId=" + hisId + ", secId=" + secId + ", userId="
				+ userId + "]";
	}
}
