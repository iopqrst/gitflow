package com.bskcare.ch.vo.search;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="t_drug_xy")
public class XyVo {
	@Id
	@GeneratedValue
	private Integer id;//主键
	
	private String cypbm;//唯一标识
	
	private String cypmc;// 药品名
	
	private String cpym; // 字母缩写
	//一下为药品信息字段直接 顺序写就可以                    
	private String csyz;// 适应症
	
	private String cyxx;
	
	private String cydx;
	
	private String cblfy;
	
	private String cjjz;
	
	private String  czysx;
	
	private String  cxhzy;
	
	private String cgysm;
    
	private String cyfyl;
	
	private String czjygg;
	
	private String czlly;
	//以上为药品信息
	private Integer bsh;//无用字段
	
	private String ccxzh;//唯一表示药品名 字母缩写   搜索用

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

	public String getCsyz() {
		return csyz;
	}

	public void setCsyz(String csyz) {
		this.csyz = csyz;
	}

	public String getCyxx() {
		return cyxx;
	}

	public void setCyxx(String cyxx) {
		this.cyxx = cyxx;
	}

	public String getCydx() {
		return cydx;
	}

	public void setCydx(String cydx) {
		this.cydx = cydx;
	}

	public String getCblfy() {
		return cblfy;
	}

	public void setCblfy(String cblfy) {
		this.cblfy = cblfy;
	}

	public String getCjjz() {
		return cjjz;
	}

	public void setCjjz(String cjjz) {
		this.cjjz = cjjz;
	}

	public String getCzysx() {
		return czysx;
	}

	public void setCzysx(String czysx) {
		this.czysx = czysx;
	}

	public String getCxhzy() {
		return cxhzy;
	}

	public void setCxhzy(String cxhzy) {
		this.cxhzy = cxhzy;
	}

	public String getCgysm() {
		return cgysm;
	}

	public void setCgysm(String cgysm) {
		this.cgysm = cgysm;
	}

	public String getCyfyl() {
		return cyfyl;
	}

	public void setCyfyl(String cyfyl) {
		this.cyfyl = cyfyl;
	}

	public String getCzjygg() {
		return czjygg;
	}

	public void setCzjygg(String czjygg) {
		this.czjygg = czjygg;
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
