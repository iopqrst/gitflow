package com.bskcare.ch.vo.search;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_diseaseclass")
public class DiseaseClass {
	@Id
	@GeneratedValue
	private Integer id;
	// 数据库唯一标识
	private String cbm;
	// 疾病名
	private String cmc;
	// 标识
	private Integer isnr;
	// 父id
	private Integer isjid;
	
	
	public Integer getIsjid() {
		return isjid;
	}

	public void setIsjid(Integer isjid) {
		this.isjid = isjid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCbm() {
		return cbm;
	}

	public void setCbm(String cbm) {
		this.cbm = cbm;
	}

	public String getCmc() {
		return cmc;
	}

	public void setCmc(String cmc) {
		this.cmc = cmc;
	}

	public Integer getIsnr() {
		return isnr;
	}

	public void setIsnr(Integer isnr) {
		this.isnr = isnr;
	}

}
