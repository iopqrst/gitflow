package com.bskcare.ch.vo.search;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_drug_zcy")
public class ZcyVo {
	@Id
	@GeneratedValue
	private Integer id;//主键
	
	private String cypbm;//数据库唯一标识
	
	private String cypmc;//药品名
	
	private String cpym;//字母缩写
	//以下为药品信息
	private String cywzc;
	
	private String cgnzz;
	
	private String cfj;
	
	private String clcyy;
	
	private String cyldl;
	
	private String  cblfy;
	
	private String  czysx;
	
	private String cyfyl;
    
	private String cgg;
	
	private String cckwx;
	
	private String czlly;
	//以上为药品信息
	//一下为无用字段
	private Integer bsh;
	
	private String ccxzh;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCypbm() {
		return cypbm;
	}

	public void setCypbm(String cypbm) {
		this.cypbm = cypbm;
	}

	public String getCypmc() {
		return cypmc;
	}

	public void setCypmc(String cypmc) {
		this.cypmc = cypmc;
	}

	public String getCpym() {
		return cpym;
	}

	public void setCpym(String cpym) {
		this.cpym = cpym;
	}

	public String getCywzc() {
		return cywzc;
	}

	public void setCywzc(String cywzc) {
		this.cywzc = cywzc;
	}

	public String getCgnzz() {
		return cgnzz;
	}

	public void setCgnzz(String cgnzz) {
		this.cgnzz = cgnzz;
	}

	public String getCfj() {
		return cfj;
	}

	public void setCfj(String cfj) {
		this.cfj = cfj;
	}

	public String getClcyy() {
		return clcyy;
	}

	public void setClcyy(String clcyy) {
		this.clcyy = clcyy;
	}

	public String getCyldl() {
		return cyldl;
	}

	public void setCyldl(String cyldl) {
		this.cyldl = cyldl;
	}

	public String getCblfy() {
		return cblfy;
	}

	public void setCblfy(String cblfy) {
		this.cblfy = cblfy;
	}

	public String getCzysx() {
		return czysx;
	}

	public void setCzysx(String czysx) {
		this.czysx = czysx;
	}

	public String getCyfyl() {
		return cyfyl;
	}

	public void setCyfyl(String cyfyl) {
		this.cyfyl = cyfyl;
	}

	public String getCgg() {
		return cgg;
	}

	public void setCgg(String cgg) {
		this.cgg = cgg;
	}

	public String getCckwx() {
		return cckwx;
	}

	public void setCckwx(String cckwx) {
		this.cckwx = cckwx;
	}

	public String getCzlly() {
		return czlly;
	}

	public void setCzlly(String czlly) {
		this.czlly = czlly;
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

	
	
}
