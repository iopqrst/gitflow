package com.bskcare.ch.vo.client;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户最近一次体检信息
 * 
 */
@Entity
@Table(name = "t_client_latest_physical")
public class ClientLatestPhy implements Serializable{

	private static final long serialVersionUID = 8530096944458151179L;
	
	private Integer id;
	private Integer clientId;
	/** 身高*/
	private String height; // 身高
	/** 体重*/
	private String weight; // 体重
	/** 臀围*/
	private String breech; // 臀围
	/** 腰围*/
	private String waist; // 腰围
	/** 心率*/
	private String heartRate;// 心率
	/** 血压收缩压*/
	private String sbp; // 血压收缩压
	/** 血压舒张压*/
	private String dbp; // 血压舒张压
	/** 空腹血糖*/
	private String glu; // 空腹血糖
	/** 总胆固醇*/
	private String tc; // 总胆固醇
	/** 甘油三脂*/
	private String tlc; // 甘油三脂
	/** 高密度脂蛋白胆固醇*/
	private String hdl; // 高密度脂蛋白胆固醇
	/** 低密度脂蛋白胆固醇*/
	private String ldl; // 低密度脂蛋白胆固醇
	/** 血清谷丙转氨酶*/
	private String sgpt; // 血清谷丙转氨酶
	/** 血清谷草转氨酶*/
	private String sgot; // 血清谷草转氨酶
	/** 白蛋白*/
	private String alb; // 白蛋白
	/** 总胆红素*/
	private String tbil; // 总胆红素
	/** 结合胆红素*/
	private String dbil; // 结合胆红素
	/** 血清肌酐*/
	private String scre; // 血清肌酐
	/** 身高*/
	private String bun; // 血尿素氮
	/** 血钾浓度*/
	private String bk; // 血钾浓度
	/** 血钠浓度*/
	private String natrium; // 血钠浓度
	/** 体检时间*/
	private Date physicalTime; // 体检时间
	private Date createTime; // 创建时间

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getBreech() {
		return breech;
	}

	public void setBreech(String breech) {
		this.breech = breech;
	}

	public String getWaist() {
		return waist;
	}

	public void setWaist(String waist) {
		this.waist = waist;
	}

	public String getSbp() {
		return sbp;
	}

	public void setSbp(String sbp) {
		this.sbp = sbp;
	}

	public String getDbp() {
		return dbp;
	}

	public void setDbp(String dbp) {
		this.dbp = dbp;
	}

	public String getGlu() {
		return glu;
	}

	public void setGlu(String glu) {
		this.glu = glu;
	}

	public String getTc() {
		return tc;
	}

	public void setTc(String tc) {
		this.tc = tc;
	}

	public String getTlc() {
		return tlc;
	}

	public void setTlc(String tlc) {
		this.tlc = tlc;
	}

	public String getHdl() {
		return hdl;
	}

	public void setHdl(String hdl) {
		this.hdl = hdl;
	}

	public String getLdl() {
		return ldl;
	}

	public void setLdl(String ldl) {
		this.ldl = ldl;
	}

	public String getSgpt() {
		return sgpt;
	}

	public void setSgpt(String sgpt) {
		this.sgpt = sgpt;
	}

	public String getSgot() {
		return sgot;
	}

	public void setSgot(String sgot) {
		this.sgot = sgot;
	}

	public String getAlb() {
		return alb;
	}

	public void setAlb(String alb) {
		this.alb = alb;
	}

	public String getTbil() {
		return tbil;
	}

	public void setTbil(String tbil) {
		this.tbil = tbil;
	}

	public String getDbil() {
		return dbil;
	}

	public void setDbil(String dbil) {
		this.dbil = dbil;
	}

	public String getScre() {
		return scre;
	}

	public void setScre(String scre) {
		this.scre = scre;
	}

	public String getBun() {
		return bun;
	}

	public void setBun(String bun) {
		this.bun = bun;
	}

	public String getBk() {
		return bk;
	}

	public void setBk(String bk) {
		this.bk = bk;
	}

	public String getNatrium() {
		return natrium;
	}

	public void setNatrium(String natrium) {
		this.natrium = natrium;
	}

	public Date getPhysicalTime() {
		return physicalTime;
	}

	public void setPhysicalTime(Date physicalTime) {
		this.physicalTime = physicalTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getHeartRate() {
		return heartRate;
	}

	public void setHeartRate(String heartRate) {
		this.heartRate = heartRate;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "ClientLatestPhy [alb=" + alb + ", bk=" + bk + ", breech="
				+ breech + ", bun=" + bun + ", clientId=" + clientId
				+ ", createTime=" + createTime + ", dbil=" + dbil + ", dbp="
				+ dbp + ", glu=" + glu + ", hdl=" + hdl + ", heartRate="
				+ heartRate + ", height=" + height + ", id=" + id + ", ldl="
				+ ldl + ", natrium=" + natrium + ", physicalTime="
				+ physicalTime + ", sbp=" + sbp + ", scre=" + scre + ", sgot="
				+ sgot + ", sgpt=" + sgpt + ", tbil=" + tbil + ", tc=" + tc
				+ ", tlc=" + tlc + ", waist=" + waist + ", weight=" + weight
				+ "]";
	}

}
