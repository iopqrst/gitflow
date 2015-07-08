package com.bskcare.ch.vo.client;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户家族病史
 */
@Entity
@Table(name = "t_client_family_health")
public class ClientFamilyHistory implements Serializable {

	private static final long serialVersionUID = 8530096944458151179L;

	private Integer id;
	private Integer clientId;
	/** 父亲是否患有高血压 */
	private String faHasGxy;
	/** 父亲高血压发病年龄 */
	private Integer faGxyAge;
	/** 母亲是否患有高血压 */
	private String moHasGxy;
	/** 母亲高血压发病年龄 */
	private Integer moGxyAge;
	/** 父亲是否患有冠心病 */
	private String faHasGxb;
	/** 父亲冠心病发病年龄 */
	private Integer faGxbAge;
	/** 母亲是否患有冠心病 */
	private String moHasGxb;
	/** 母亲冠心病发病年龄 */
	private Integer moGxbAge;
	/** 父亲是否患有脑血管病 */
	private String faHasNxg;
	/** 父亲脑血管病发病年龄 */
	private Integer faNxgAge;
	/** 母亲是否患有脑血管病 */
	private String moHasNxg;
	/** 母亲脑血管病发病年龄 */
	private Integer moNxgAge;
	/** 父亲是否患有糖尿病 */
	private String faHasTnb;
	/** 父亲糖尿病病发病年龄 */
	private Integer faTnbAge;
	/** 母亲是否患有糖尿病 */
	private String moHasTnb;
	/** 母亲糖尿病发病年龄 */
	private Integer moTnbAge;
	/** 父亲是否患有恶性肿瘤 */
	private String faHasExzl;
	/** 父亲恶性肿瘤病发病年龄 */
	private Integer faExzlAge;
	/** 母亲是否患有恶性肿瘤 */
	private String moHasExzl;
	/** 母亲恶性肿瘤发病年龄 */
	private Integer moExzlAge;
	/** 父亲是否患有精神疾病 */
	private String faHasJsb;
	/** 父亲精神疾病发病年龄 */
	private Integer faJsbAge;
	/** 母亲是否患有精神疾病 */
	private String moHasJsb;
	/** 母亲精神疾病发病年龄 */
	private Integer moJsbAge;
	/** 父亲是否患有结核病 */
	private String faHasJhb;
	/** 父亲结核病发病年龄 */
	private Integer faJhbAge;
	/** 母亲是否患有结核病 */
	private String moHasJhb;
	/** 母亲结核病发病年龄 */
	private Integer moJhbAge;
	/** 父亲是否患有肝炎 */
	private String faHasGy;
	/** 父亲肝炎发病年龄 */
	private Integer faGyAge;
	/** 母亲是否患有肝炎 */
	private String moHasGy;
	/** 母亲肝炎发病年龄 */
	private Integer moGyAge;
	/** 其他疾病 */
	private String other;
	/** 父亲其他疾病 */
	private String faHasOther;
	/** 父亲其他疾病发病年龄 */
	private Integer faOtherAge;
	/** 母亲其他疾病 */
	private String moHasOther;
	/** 母亲其他疾病发病年龄 */
	private Integer moOtherAge;
	private Date createTime;
	
	/**是否有家族病史  1.有  2.无*/
	private Integer isHasFamilyHealth;


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
	
	public String getFaHasGxy() {
		return faHasGxy;
	}

	public void setFaHasGxy(String faHasGxy) {
		this.faHasGxy = faHasGxy;
	}

	public Integer getFaGxyAge() {
		return faGxyAge;
	}

	public void setFaGxyAge(Integer faGxyAge) {
		this.faGxyAge = faGxyAge;
	}

	public String getMoHasGxy() {
		return moHasGxy;
	}

	public void setMoHasGxy(String moHasGxy) {
		this.moHasGxy = moHasGxy;
	}

	public Integer getMoGxyAge() {
		return moGxyAge;
	}

	public void setMoGxyAge(Integer moGxyAge) {
		this.moGxyAge = moGxyAge;
	}

	public String getFaHasGxb() {
		return faHasGxb;
	}

	public void setFaHasGxb(String faHasGxb) {
		this.faHasGxb = faHasGxb;
	}

	public Integer getFaGxbAge() {
		return faGxbAge;
	}

	public void setFaGxbAge(Integer faGxbAge) {
		this.faGxbAge = faGxbAge;
	}

	public String getMoHasGxb() {
		return moHasGxb;
	}

	public void setMoHasGxb(String moHasGxb) {
		this.moHasGxb = moHasGxb;
	}

	public Integer getMoGxbAge() {
		return moGxbAge;
	}

	public void setMoGxbAge(Integer moGxbAge) {
		this.moGxbAge = moGxbAge;
	}

	public String getFaHasNxg() {
		return faHasNxg;
	}

	public void setFaHasNxg(String faHasNxg) {
		this.faHasNxg = faHasNxg;
	}

	public Integer getFaNxgAge() {
		return faNxgAge;
	}

	public void setFaNxgAge(Integer faNxgAge) {
		this.faNxgAge = faNxgAge;
	}

	public String getMoHasNxg() {
		return moHasNxg;
	}

	public void setMoHasNxg(String moHasNxg) {
		this.moHasNxg = moHasNxg;
	}

	public Integer getMoNxgAge() {
		return moNxgAge;
	}

	public void setMoNxgAge(Integer moNxgAge) {
		this.moNxgAge = moNxgAge;
	}

	public String getFaHasTnb() {
		return faHasTnb;
	}

	public void setFaHasTnb(String faHasTnb) {
		this.faHasTnb = faHasTnb;
	}

	public Integer getFaTnbAge() {
		return faTnbAge;
	}

	public void setFaTnbAge(Integer faTnbAge) {
		this.faTnbAge = faTnbAge;
	}

	public String getMoHasTnb() {
		return moHasTnb;
	}

	public void setMoHasTnb(String moHasTnb) {
		this.moHasTnb = moHasTnb;
	}

	public Integer getMoTnbAge() {
		return moTnbAge;
	}

	public void setMoTnbAge(Integer moTnbAge) {
		this.moTnbAge = moTnbAge;
	}

	public String getFaHasExzl() {
		return faHasExzl;
	}

	public void setFaHasExzl(String faHasExzl) {
		this.faHasExzl = faHasExzl;
	}

	public Integer getFaExzlAge() {
		return faExzlAge;
	}

	public void setFaExzlAge(Integer faExzlAge) {
		this.faExzlAge = faExzlAge;
	}

	public String getMoHasExzl() {
		return moHasExzl;
	}

	public void setMoHasExzl(String moHasExzl) {
		this.moHasExzl = moHasExzl;
	}

	public Integer getMoExzlAge() {
		return moExzlAge;
	}

	public void setMoExzlAge(Integer moExzlAge) {
		this.moExzlAge = moExzlAge;
	}

	public String getFaHasJsb() {
		return faHasJsb;
	}

	public void setFaHasJsb(String faHasJsb) {
		this.faHasJsb = faHasJsb;
	}

	public Integer getFaJsbAge() {
		return faJsbAge;
	}

	public void setFaJsbAge(Integer faJsbAge) {
		this.faJsbAge = faJsbAge;
	}

	public String getMoHasJsb() {
		return moHasJsb;
	}

	public void setMoHasJsb(String moHasJsb) {
		this.moHasJsb = moHasJsb;
	}

	public Integer getMoJsbAge() {
		return moJsbAge;
	}

	public void setMoJsbAge(Integer moJsbAge) {
		this.moJsbAge = moJsbAge;
	}

	public String getFaHasJhb() {
		return faHasJhb;
	}

	public void setFaHasJhb(String faHasJhb) {
		this.faHasJhb = faHasJhb;
	}

	public Integer getFaJhbAge() {
		return faJhbAge;
	}

	public void setFaJhbAge(Integer faJhbAge) {
		this.faJhbAge = faJhbAge;
	}

	public String getMoHasJhb() {
		return moHasJhb;
	}

	public void setMoHasJhb(String moHasJhb) {
		this.moHasJhb = moHasJhb;
	}

	public Integer getMoJhbAge() {
		return moJhbAge;
	}

	public void setMoJhbAge(Integer moJhbAge) {
		this.moJhbAge = moJhbAge;
	}

	public String getFaHasGy() {
		return faHasGy;
	}

	public void setFaHasGy(String faHasGy) {
		this.faHasGy = faHasGy;
	}

	public Integer getFaGyAge() {
		return faGyAge;
	}

	public void setFaGyAge(Integer faGyAge) {
		this.faGyAge = faGyAge;
	}

	public String getMoHasGy() {
		return moHasGy;
	}

	public void setMoHasGy(String moHasGy) {
		this.moHasGy = moHasGy;
	}

	public Integer getMoGyAge() {
		return moGyAge;
	}

	public void setMoGyAge(Integer moGyAge) {
		this.moGyAge = moGyAge;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getFaHasOther() {
		return faHasOther;
	}

	public void setFaHasOther(String faHasOther) {
		this.faHasOther = faHasOther;
	}

	public Integer getFaOtherAge() {
		return faOtherAge;
	}

	public void setFaOtherAge(Integer faOtherAge) {
		this.faOtherAge = faOtherAge;
	}

	public String getMoHasOther() {
		return moHasOther;
	}

	public void setMoHasOther(String moHasOther) {
		this.moHasOther = moHasOther;
	}

	public Integer getMoOtherAge() {
		return moOtherAge;
	}

	public void setMoOtherAge(Integer moOtherAge) {
		this.moOtherAge = moOtherAge;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getIsHasFamilyHealth() {
		return isHasFamilyHealth;
	}

	public void setIsHasFamilyHealth(Integer isHasFamilyHealth) {
		this.isHasFamilyHealth = isHasFamilyHealth;
	}

	@Override
	public String toString() {
		return "ClientFamilyHistory [clientId=" + clientId + ", createTime="
				+ createTime + ", faExzlAge=" + faExzlAge + ", faGxbAge="
				+ faGxbAge + ", faGxyAge=" + faGxyAge + ", faGyAge=" + faGyAge
				+ ", faHasExzl=" + faHasExzl + ", faHasGxb=" + faHasGxb
				+ ", faHasGxy=" + faHasGxy + ", faHasGy=" + faHasGy
				+ ", faHasJhb=" + faHasJhb + ", faHasJsb=" + faHasJsb
				+ ", faHasNxg=" + faHasNxg + ", faHasOther=" + faHasOther
				+ ", faHasTnb=" + faHasTnb + ", faJhbAge=" + faJhbAge
				+ ", faJsbAge=" + faJsbAge + ", faNxgAge=" + faNxgAge
				+ ", faOtherAge=" + faOtherAge + ", faTnbAge=" + faTnbAge
				+ ", id=" + id + ", moExzlAge=" + moExzlAge + ", moGxbAge="
				+ moGxbAge + ", moGxyAge=" + moGxyAge + ", moGyAge=" + moGyAge
				+ ", moHasExzl=" + moHasExzl + ", moHasGxb=" + moHasGxb
				+ ", moHasGxy=" + moHasGxy + ", moHasGy=" + moHasGy
				+ ", moHasJhb=" + moHasJhb + ", moHasJsb=" + moHasJsb
				+ ", moHasNxg=" + moHasNxg + ", moHasOther=" + moHasOther
				+ ", moHasTnb=" + moHasTnb + ", moJhbAge=" + moJhbAge
				+ ", moJsbAge=" + moJsbAge + ", moNxgAge=" + moNxgAge
				+ ", moOtherAge=" + moOtherAge + ", moTnbAge=" + moTnbAge
				+ ", other=" + other + "]";
	}

}
