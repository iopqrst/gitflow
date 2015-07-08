package com.bskcare.ch.vo.search;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_drug_zy1")
public class Zy1Vo {
	
	@Id
	@GeneratedValue
	private Integer id;//主键
	
	private String clb;//父标识
	
	private String cypmc;// 药品名称
	//以下为药品信息
	private String cgnzz;
	
	private String cypmc1;
	
	private String cxw;
	
	private String cszd;
	
	private String chxcf;
	
	private String ccz;
	
	private String  cly;
	
	private String czwxt1;
	
	private String czwxt2;
	
	private String clbmc;
	
	private String cywmc;
	private String cfz;
	
	
	//以上为药品信息
	private Integer bsh;
	
	private String ccxzh;
    
	private String cypbm;
	
	private String cpym;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClb() {
		return clb;
	}

	public void setClb(String clb) {
		this.clb = clb;
	}

	public String getCypmc() {
		return cypmc;
	}

	public void setCypmc(String cypmc) {
		this.cypmc = cypmc;
	}

	public String getCgnzz() {
		return cgnzz;
	}

	public void setCgnzz(String cgnzz) {
		this.cgnzz = cgnzz;
	}

	public String getCypmc1() {
		return cypmc1;
	}

	public void setCypmc1(String cypmc1) {
		this.cypmc1 = cypmc1;
	}

	public String getCxw() {
		return cxw;
	}

	public void setCxw(String cxw) {
		this.cxw = cxw;
	}

	public String getCszd() {
		return cszd;
	}

	public void setCszd(String cszd) {
		this.cszd = cszd;
	}

	public String getChxcf() {
		return chxcf;
	}

	public void setChxcf(String chxcf) {
		this.chxcf = chxcf;
	}

	public String getCcz() {
		return ccz;
	}

	public void setCcz(String ccz) {
		this.ccz = ccz;
	}

	public String getCly() {
		return cly;
	}

	public void setCly(String cly) {
		this.cly = cly;
	}

	public String getCzwxt1() {
		return czwxt1;
	}

	public void setCzwxt1(String czwxt1) {
		this.czwxt1 = czwxt1;
	}

	public String getCzwxt2() {
		return czwxt2;
	}

	public void setCzwxt2(String czwxt2) {
		this.czwxt2 = czwxt2;
	}

	public String getClbmc() {
		return clbmc;
	}

	public void setClbmc(String clbmc) {
		this.clbmc = clbmc;
	}

	public String getCywmc() {
		return cywmc;
	}

	public void setCywmc(String cywmc) {
		this.cywmc = cywmc;
	}

	public String getCfz() {
		return cfz;
	}

	public void setCfz(String cfz) {
		this.cfz = cfz;
	}

	public Integer getBsh() {
		return bsh;
	}

	public void setBsh(Integer bsh) {
		this.bsh = bsh;
	}

	public String getCcxzh() {
		return ccxzh;
	}

	public void setCcxzh(String ccxzh) {
		this.ccxzh = ccxzh;
	}

	public String getCypbm() {
		return cypbm;
	}

	public void setCypbm(String cypbm) {
		this.cypbm = cypbm;
	}

	public String getCpym() {
		return cpym;
	}

	public void setCpym(String cpym) {
		this.cpym = cpym;
	}
	
}
