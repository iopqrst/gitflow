package com.bskcare.ch.vo.search;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="t_drug_zy")
public class ZyVo {
	@Id
	@GeneratedValue
	private Integer id;//主键
	
	private String cypbm;//数据库唯一表示
	
	private String cypmc;//药品名称
	
	private String cpym;
	//以下为药品信息
	
	private String cycms;
	
	private String cycxw;
	
	private String cylzy;

	private String cgx;
	
	private String cycgj;
	
	private String czzjb;
	
	private String  czycf;
	
	private String  ckb;
	
	private String clb;
    
	private String cylms;
	
	private String ccdms;
	
	private String cxyp;
	
	private String cycjb;
	
	private String cycgg;

	private String czlly;
	
	private String cycxz;
	//以上为药品信息
	private Integer bsh;
	
	private String ccxzh;
	
	
	
	
	public String getCzlly() {
		return czlly;
	}

	public void setCzlly(String czlly) {
		this.czlly = czlly;
	}

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

	public String getCycms() {
		return cycms;
	}

	public void setCycms(String cycms) {
		this.cycms = cycms;
	}
	public String getCylzy() {
		return cylzy;
	}

	public void setCylzy(String cylzy) {
		this.cylzy = cylzy;
	}

	public String getCycxw() {
		return cycxw;
	}

	public void setCycxw(String cycxw) {
		this.cycxw = cycxw;
	}

	public String getCgx() {
		return cgx;
	}

	public void setCgx(String cgx) {
		this.cgx = cgx;
	}

	public String getCycgj() {
		return cycgj;
	}

	public void setCycgj(String cycgj) {
		this.cycgj = cycgj;
	}

	public String getCzzjb() {
		return czzjb;
	}

	public void setCzzjb(String czzjb) {
		this.czzjb = czzjb;
	}

	public String getCzycf() {
		return czycf;
	}

	public void setCzycf(String czycf) {
		this.czycf = czycf;
	}

	public String getCkb() {
		return ckb;
	}

	public void setCkb(String ckb) {
		this.ckb = ckb;
	}

	public String getClb() {
		return clb;
	}

	public void setClb(String clb) {
		this.clb = clb;
	}

	public String getCylms() {
		return cylms;
	}

	public void setCylms(String cylms) {
		this.cylms = cylms;
	}

	public String getCcdms() {
		return ccdms;
	}

	public void setCcdms(String ccdms) {
		this.ccdms = ccdms;
	}

	public String getCxyp() {
		return cxyp;
	}

	public void setCxyp(String cxyp) {
		this.cxyp = cxyp;
	}

	public String getCycjb() {
		return cycjb;
	}

	public void setCycjb(String cycjb) {
		this.cycjb = cycjb;
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

	public String getCycxz() {
		return cycxz;
	}

	public void setCycxz(String cycxz) {
		this.cycxz = cycxz;
	}

	public String getCycgg() {
		return cycgg;
	}

	public void setCycgg(String cycgg) {
		this.cycgg = cycgg;
	}

	

}
