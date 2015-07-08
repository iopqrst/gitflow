package com.bskcare.ch.vo.rpt;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 检测数据
 */
@Entity
@Table(name = "rpt_monitoring_data")
public class RptMonitoringData implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 1:血压 2:血氧 3:空腹血糖 4:餐后2h血糖  5：心电 */
	public static int TYPE_PRESSURE = 1;
	/** 1:血压 2:血氧 3:空腹血糖 4:餐后2h血糖  5：心电 */
	public static int TYPE_SPO2 = 2;
	/** 1:血压 2:血氧 3:空腹血糖 4:餐后2h血糖  5：心电 */
	public static int TYPE_SUGAR = 3;
	/** 1:血压 2:血氧 3:空腹血糖 4:餐后2h血糖  5：心电 */
	public static int TYPE_SUGAR2H = 4;
	/** 1:血压 2:血氧 3:空腹血糖 4:餐后2h血糖  5：心电 */
	public static int TYPE_ECG = 5;

	/** 报告状态 0：自动创建 1：草稿 2：审核通过（展示给用户） 3：删除 */
	public static int STATUS_AUTO = 0;
	/** 报告状态 0：自动创建 1：草稿 2：审核通过（展示给用户） 3：删除 */
	public static int STATUS_DRAFT = 1;
	/** 报告状态 0：自动创建 1：草稿 2：审核通过（展示给用户） 3：删除 */
	public static int STATUS_AUDITED = 2;
	/** 报告状态 0：自动创建 1：草稿 2：审核通过（展示给用户） 3：删除 */
	public static int STATUS_DELETE = 3;

	private Integer mid;
	private Integer rptId;
	private Integer clientId;
	/**
	 * 1:血压 2:空腹血糖 3:餐后2h血糖 4:血氧 5：心电
	 */
	private int type;
	/**
	 * 图片数据1
	 */
	private String pic1;
	/**
	 * 图片数据2
	 */
	private String pic2;
	/**
	 * 图片数据3
	 */
	private String pic3;
	/**
	 * 默认图片数据
	 */
	private String defaultPic;
	/**
	 * 结论
	 */
	private String conclusion;
	/**
	 * 危险因素
	 */
	private String hazards;

	private Date createTime;
	/**
	 * 报告状态 0：自动创建 1：草稿 2：审核通过（展示给用户） 3：删除
	 */
	private int status;
	/**
	 * 统计数据
	 */
	private String statistics ;
	
	@Id
	@GeneratedValue
	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public Integer getRptId() {
		return rptId;
	}

	public void setRptId(Integer rptId) {
		this.rptId = rptId;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Lob
	@Basic(fetch = FetchType.LAZY)
	public String getPic1() {
		return pic1;
	}

	public void setPic1(String pic1) {
		this.pic1 = pic1;
	}

	@Lob
	@Basic(fetch = FetchType.LAZY)
	public String getPic2() {
		return pic2;
	}

	public void setPic2(String pic2) {
		this.pic2 = pic2;
	}

	@Lob
	@Basic(fetch = FetchType.LAZY)
	public String getPic3() {
		return pic3;
	}

	public void setPic3(String pic3) {
		this.pic3 = pic3;
	}

	public String getDefaultPic() {
		return defaultPic;
	}

	public void setDefaultPic(String defaultPic) {
		this.defaultPic = defaultPic;
	}

	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}

	public String getHazards() {
		return hazards;
	}

	public void setHazards(String hazards) {
		this.hazards = hazards;
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

	public String getStatistics() {
		return statistics;
	}

	public void setStatistics(String statistics) {
		this.statistics = statistics;
	}

	@Override
	public String toString() {
		return "RptMonitoringData [clientId=" + clientId + ", conclusion="
				+ conclusion + ", createTime=" + createTime + ", defaultPic="
				+ defaultPic + ", hazards=" + hazards + ", mid=" + mid
				+ ", pic1=" + pic1 + ", pic2=" + pic2 + ", pic3=" + pic3
				+ ", rptId=" + rptId + ", status=" + status + ", type=" + type
				+ "]";
	}

}
