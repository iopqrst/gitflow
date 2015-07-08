package com.bskcare.ch.vo.client;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户病史
 */
@Entity
@Table(name = "t_client_health")
public class ClientMedicalHistory implements Serializable {

	private static final long serialVersionUID = 8530096944458151179L;
	public static final Integer CLIENT_MEDICAL_HISTORY_YES=1;
	public static final Integer CLIENT_MEDICAL_HISTORY_NO=2;
	private Integer id;
	private Integer clientId;
	/** 是否患高血压 1： 是 2：否 */
	private String hasGxy;
	/** 高血压的诊断时间 */
	private Date gxyDiagTime;
	/**
	 * 是否患冠心病/心绞痛/心肌梗塞 1： 是 2：否
	 */
	private String hasGxb;
	/** 冠心病诊断时间 */
	private Date gxbDiagTime;
	/**
	 * 是否患有脑中风 1： 是 2：否
	 */
	private String hasNzf;
	/** 脑中风的诊断时间 */
	private Date nzfDiagTime;
	/**
	 * 是否患Ⅰ型糖尿病 1： 是 2：否
	 */
	private String hasTnbI;
	/** Ⅰ型糖尿病的时间（年-月） */
	private Date tnbIDiagTime;
	/**
	 * 是否患Ⅱ型糖尿病 1： 是 2：否
	 */
	private String hasTnbII;
	/** Ⅱ型糖尿病的诊断时间（年-月） */
	private Date tnbIIiagTime;
	/**
	 * 是否患有恶性肿瘤 1： 是 2：否
	 */
	private String hasExzl;
	/** 恶性肿瘤诊断时间 */
	private Date exzlDiagTime;
	/**
	 * 是否患有高脂血症 1： 是 2：否
	 */
	private String hasGxz;
	/** 高脂血症诊断时间 */
	private Date gxzDiagTime;
	/** 是否患有慢支 */
	private String hasMz;
	/** 慢支诊断时间 */
	private Date mzDiagTime;
	/** 是否患有哮喘 */
	private String hasXc;
	/** 哮喘诊断时间 */
	private Date xcDiagTime;
	/** 是否患有肺气肿 */
	private String hasFqz;
	/** 肺气肿诊断时间 */
	private Date fqzDiagTime;
	/** 是否患有肺结核 */
	private String hasFjh;
	/** 肺结核诊断时间 */
	private Date fjhDiagTime;
	/** 其他疾病 */
	private String other;
	/** 其他疾病诊断时间 */
	private Date otherDialogTime;
	/**
	 * 残疾 1: 视力残疾 2：听力残疾 3：语言残疾 4：肢体残疾 5：智力残疾 6：精神残疾 7：其他残疾
	 */
	private String deformity;
	/**
	 * 吸入式过敏 1：选中
	 */
	private String xrAllergy;
	/** 吸入式过敏原 */
	private String xrAllergen;
	/**
	 * 食入式过敏 1：选中
	 */
	private String srAllergy;
	/**
	 * 食入式过敏原
	 */
	private String srAllergen;
	/**
	 * 接触式过敏 1：选中
	 */
	private String jcAllergy;
	/** 接触式过敏原 */
	private String jcAllergen;
	/**
	 * 注射式过敏 1：选中
	 */
	private String zsAllergy;
	/** 注射式过敏原 */
	private String zsAllergen;
	/**
	 * 自身组织抗原过敏 1：选中
	 */
	private String selfAllergy;
	/** 自身组织抗原过敏原 */
	private String selfAllergen;
	/** 其他过敏原 */
	private String allergenOther;
	/** 月经史 */
	private String menstrual;
	/**
	 * 生育史： 1： 顺产 2：剖腹产 3：自然流产 4：人工流产 5：其他
	 */
	private String bear;
	/** 其他生育史 */
	private String bearSupply;
	/** 手术史 */
	private Integer surgery;
	/** 手术详情 */
	private String surgeryDetail;
	/**
	 * 外伤史 1：有 2：无（可有详细描述）
	 */
	private Integer trauma;
	/** 外伤史详情 */
	private String detail;
	/**
	 * 输血史 1：有 2：无 （可有详细描述） 如：1，去年的夏天…
	 */
	private Integer transfusionOfBlood;
	/** 输血史描述 */
	private String supply;

	private Date createTime;

	/** 是否有患病史 1：有 2：无 */
	private Integer isHasMedical;

	/** 是否有过敏史 1：有 2：无 */
	private Integer isHasAllergy;
	/** 有无家族病史 1：有 2： 无 */
	private Integer isHasFamilyHealth;
	/** 最希望解决的健康问题 **/
	private String hopeSolveHealth;

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

	public Date getGxyDiagTime() {
		return gxyDiagTime;
	}

	public void setGxyDiagTime(Date gxyDiagTime) {
		this.gxyDiagTime = gxyDiagTime;
	}

	public Date getGxbDiagTime() {
		return gxbDiagTime;
	}

	public void setGxbDiagTime(Date gxbDiagTime) {
		this.gxbDiagTime = gxbDiagTime;
	}

	public Date getNzfDiagTime() {
		return nzfDiagTime;
	}

	public void setNzfDiagTime(Date nzfDiagTime) {
		this.nzfDiagTime = nzfDiagTime;
	}

	public Date getTnbIDiagTime() {
		return tnbIDiagTime;
	}

	public void setTnbIDiagTime(Date tnbIDiagTime) {
		this.tnbIDiagTime = tnbIDiagTime;
	}

	public Date getTnbIIiagTime() {
		return tnbIIiagTime;
	}

	public void setTnbIIiagTime(Date tnbIIiagTime) {
		this.tnbIIiagTime = tnbIIiagTime;
	}

	public Date getExzlDiagTime() {
		return exzlDiagTime;
	}

	public void setExzlDiagTime(Date exzlDiagTime) {
		this.exzlDiagTime = exzlDiagTime;
	}

	public Date getGxzDiagTime() {
		return gxzDiagTime;
	}

	public void setGxzDiagTime(Date gxzDiagTime) {
		this.gxzDiagTime = gxzDiagTime;
	}

	public Date getMzDiagTime() {
		return mzDiagTime;
	}

	public void setMzDiagTime(Date mzDiagTime) {
		this.mzDiagTime = mzDiagTime;
	}

	public Date getXcDiagTime() {
		return xcDiagTime;
	}

	public void setXcDiagTime(Date xcDiagTime) {
		this.xcDiagTime = xcDiagTime;
	}

	public Date getFqzDiagTime() {
		return fqzDiagTime;
	}

	public void setFqzDiagTime(Date fqzDiagTime) {
		this.fqzDiagTime = fqzDiagTime;
	}

	public Date getFjhDiagTime() {
		return fjhDiagTime;
	}

	public void setFjhDiagTime(Date fjhDiagTime) {
		this.fjhDiagTime = fjhDiagTime;
	}

	public Date getOtherDialogTime() {
		return otherDialogTime;
	}

	public void setOtherDialogTime(Date otherDialogTime) {
		this.otherDialogTime = otherDialogTime;
	}

	@Column(length = 32)
	public String getDeformity() {
		return deformity;
	}

	public void setDeformity(String deformity) {
		this.deformity = deformity;
	}

	public String getSrAllergen() {
		return srAllergen;
	}

	public void setSrAllergen(String srAllergen) {
		this.srAllergen = srAllergen;
	}

	public String getJcAllergen() {
		return jcAllergen;
	}

	public void setJcAllergen(String jcAllergen) {
		this.jcAllergen = jcAllergen;
	}

	public String getZsAllergen() {
		return zsAllergen;
	}

	public void setZsAllergen(String zsAllergen) {
		this.zsAllergen = zsAllergen;
	}

	public String getSelfAllergen() {
		return selfAllergen;
	}

	public void setSelfAllergen(String selfAllergen) {
		this.selfAllergen = selfAllergen;
	}

	public String getMenstrual() {
		return menstrual;
	}

	public void setMenstrual(String menstrual) {
		this.menstrual = menstrual;
	}

	public String getBear() {
		return bear;
	}

	public void setBear(String bear) {
		this.bear = bear;
	}

	public String getXrAllergen() {
		return xrAllergen;
	}

	public void setXrAllergen(String xrAllergen) {
		this.xrAllergen = xrAllergen;
	}

	public String getBearSupply() {
		return bearSupply;
	}

	public void setBearSupply(String bearSupply) {
		this.bearSupply = bearSupply;
	}

	public Integer getSurgery() {
		return surgery;
	}

	public void setSurgery(Integer surgery) {
		this.surgery = surgery;
	}

	public String getSurgeryDetail() {
		return surgeryDetail;
	}

	public void setSurgeryDetail(String surgeryDetail) {
		this.surgeryDetail = surgeryDetail;
	}

	public Integer getTrauma() {
		return trauma;
	}

	public void setTrauma(Integer trauma) {
		this.trauma = trauma;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Integer getTransfusionOfBlood() {
		return transfusionOfBlood;
	}

	public void setTransfusionOfBlood(Integer transfusionOfBlood) {
		this.transfusionOfBlood = transfusionOfBlood;
	}

	public String getSupply() {
		return supply;
	}

	public void setSupply(String supply) {
		this.supply = supply;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getHasGxy() {
		return hasGxy;
	}

	public void setHasGxy(String hasGxy) {
		this.hasGxy = hasGxy;
	}

	public String getHasGxb() {
		return hasGxb;
	}

	public void setHasGxb(String hasGxb) {
		this.hasGxb = hasGxb;
	}

	public String getHasNzf() {
		return hasNzf;
	}

	public void setHasNzf(String hasNzf) {
		this.hasNzf = hasNzf;
	}

	public String getHasTnbI() {
		return hasTnbI;
	}

	public void setHasTnbI(String hasTnbI) {
		this.hasTnbI = hasTnbI;
	}

	public String getHasTnbII() {
		return hasTnbII;
	}

	public void setHasTnbII(String hasTnbII) {
		this.hasTnbII = hasTnbII;
	}

	public String getHasExzl() {
		return hasExzl;
	}

	public void setHasExzl(String hasExzl) {
		this.hasExzl = hasExzl;
	}

	public String getHasGxz() {
		return hasGxz;
	}

	public void setHasGxz(String hasGxz) {
		this.hasGxz = hasGxz;
	}

	public String getHasMz() {
		return hasMz;
	}

	public void setHasMz(String hasMz) {
		this.hasMz = hasMz;
	}

	public String getHasXc() {
		return hasXc;
	}

	public void setHasXc(String hasXc) {
		this.hasXc = hasXc;
	}

	public String getHasFqz() {
		return hasFqz;
	}

	public void setHasFqz(String hasFqz) {
		this.hasFqz = hasFqz;
	}

	public String getHasFjh() {
		return hasFjh;
	}

	public void setHasFjh(String hasFjh) {
		this.hasFjh = hasFjh;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getXrAllergy() {
		return xrAllergy;
	}

	public void setXrAllergy(String xrAllergy) {
		this.xrAllergy = xrAllergy;
	}

	public String getSrAllergy() {
		return srAllergy;
	}

	public void setSrAllergy(String srAllergy) {
		this.srAllergy = srAllergy;
	}

	public String getJcAllergy() {
		return jcAllergy;
	}

	public void setJcAllergy(String jcAllergy) {
		this.jcAllergy = jcAllergy;
	}

	public String getZsAllergy() {
		return zsAllergy;
	}

	public void setZsAllergy(String zsAllergy) {
		this.zsAllergy = zsAllergy;
	}

	public String getSelfAllergy() {
		return selfAllergy;
	}

	public void setSelfAllergy(String selfAllergy) {
		this.selfAllergy = selfAllergy;
	}

	public Integer getIsHasMedical() {
		return isHasMedical;
	}

	public void setIsHasMedical(Integer isHasMedical) {
		this.isHasMedical = isHasMedical;
	}

	public Integer getIsHasAllergy() {
		return isHasAllergy;
	}

	public void setIsHasAllergy(Integer isHasAllergy) {
		this.isHasAllergy = isHasAllergy;
	}

	public Integer getIsHasFamilyHealth() {
		return isHasFamilyHealth;
	}

	public void setIsHasFamilyHealth(Integer isHasFamilyHealth) {
		this.isHasFamilyHealth = isHasFamilyHealth;
	}

	public String getHopeSolveHealth() {
		return hopeSolveHealth;
	}

	public void setHopeSolveHealth(String hopeSolveHealth) {
		this.hopeSolveHealth = hopeSolveHealth;
	}

	public String getAllergenOther() {
		return allergenOther;
	}

	public void setAllergenOther(String allergenOther) {
		this.allergenOther = allergenOther;
	}

	@Override
	public String toString() {
		return "ClientMedicalHistory [bear=" + bear + ", bearSupply="
				+ bearSupply + ", clientId=" + clientId + ", createTime="
				+ createTime + ", deformity=" + deformity + ", detail="
				+ detail + ", exzlDiagTime=" + exzlDiagTime + ", fjhDiagTime="
				+ fjhDiagTime + ", fqzDiagTime=" + fqzDiagTime
				+ ", gxbDiagTime=" + gxbDiagTime + ", gxyDiagTime="
				+ gxyDiagTime + ", gxzDiagTime=" + gxzDiagTime + ", hasExzl="
				+ hasExzl + ", hasFjh=" + hasFjh + ", hasFqz=" + hasFqz
				+ ", hasGxb=" + hasGxb + ", hasGxy=" + hasGxy + ", hasGxz="
				+ hasGxz + ", hasMz=" + hasMz + ", hasNzf=" + hasNzf
				+ ", hasTnbI=" + hasTnbI + ", hasTnbII=" + hasTnbII
				+ ", hasXc=" + hasXc + ", id=" + id + ", jcAllergen="
				+ jcAllergen + ", menstrual=" + menstrual + ", mzDiagTime="
				+ mzDiagTime + ", nzfDiagTime=" + nzfDiagTime + ", other="
				+ other + ", otherDialogTime=" + otherDialogTime
				+ ", selfAllergen=" + selfAllergen + ", selfAllergy="
				+ selfAllergy + ", srAllergen=" + srAllergen + ", srAllergy="
				+ srAllergy + ", supply=" + supply + ", surgery=" + surgery
				+ ", surgeryDetail=" + surgeryDetail + ", tnbIDiagTime="
				+ tnbIDiagTime + ", tnbIIiagTime=" + tnbIIiagTime
				+ ", transfusionOfBlood=" + transfusionOfBlood + ", trauma="
				+ trauma + ", xcDiagTime=" + xcDiagTime + ", xrAllergen="
				+ xrAllergen + ", xrAllergy=" + xrAllergy + ", zsAllergen="
				+ zsAllergen + ", zsAllergy=" + zsAllergy + "]";
	}

}
