package com.bskcare.ch.vo.search;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_diseaseinfo")
public class DiseaseInfo {
	@Id
	@GeneratedValue
	private Integer id;//主键
	private String cbm;//唯一标识
	private String cnr;//疾病信息
	private String cmc;//疾病名称
	private String cpym;//疾病名称字母简写

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

	public String getCnr() {
		return cnr;
	}

	public void setCnr(String cnr) {
		this.cnr = cnr;
	}

	public String getCmc() {
		return cmc;
	}

	public void setCmc(String cmc) {
		this.cmc = cmc;
	}

	public String getCpym() {
		return cpym;
	}

	public void setCpym(String cpym) {
		this.cpym = cpym;
	}

}
