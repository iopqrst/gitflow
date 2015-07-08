package com.bskcare.ch.vo.search;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_drug_fl")
public class FlVo {
	@Id
	@GeneratedValue
	private Integer id;//主键
	
	private String cypbm;//数据库唯一标识
	
	private String cypmb;//药品名称
	
	private String cpym;
    
	//以下 为药品信息
	private String cfjcc;
	
	private String cfjzc;
	
	private String cfjkj;
	
	private String cfjgy;
	
	private String cfjzc1;
	
	private String cfjyf;
	
	private String cfjjj;
	
	private String cfjfj;
	
	private String  cfjhz;
	
	private String  cfjfz;
	
	private String cfjfz1;
    
	private String cfjwx;
	
	private String cfjyj;
	
	private String cfjyy;
	//以上为药品信息
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

	public String getCypmb() {
		return cypmb;
	}

	public void setCypmb(String cypmb) {
		this.cypmb = cypmb;
	}

	public String getCpym() {
		return cpym;
	}

	public void setCpym(String cpym) {
		this.cpym = cpym;
	}

	public String getCfjcc() {
		return cfjcc;
	}

	public void setCfjcc(String cfjcc) {
		this.cfjcc = cfjcc;
	}

	public String getCfjzc() {
		return cfjzc;
	}

	public void setCfjzc(String cfjzc) {
		this.cfjzc = cfjzc;
	}

	public String getCfjkj() {
		return cfjkj;
	}

	public void setCfjkj(String cfjkj) {
		this.cfjkj = cfjkj;
	}

	public String getCfjgy() {
		return cfjgy;
	}

	public void setCfjgy(String cfjgy) {
		this.cfjgy = cfjgy;
	}

	public String getCfjzc1() {
		return cfjzc1;
	}

	public void setCfjzc1(String cfjzc1) {
		this.cfjzc1 = cfjzc1;
	}

	public String getCfjyf() {
		return cfjyf;
	}

	public void setCfjyf(String cfjyf) {
		this.cfjyf = cfjyf;
	}

	public String getCfjjj() {
		return cfjjj;
	}

	public void setCfjjj(String cfjjj) {
		this.cfjjj = cfjjj;
	}

	public String getCfjfj() {
		return cfjfj;
	}

	public void setCfjfj(String cfjfj) {
		this.cfjfj = cfjfj;
	}

	public String getCfjhz() {
		return cfjhz;
	}

	public void setCfjhz(String cfjhz) {
		this.cfjhz = cfjhz;
	}

	public String getCfjfz() {
		return cfjfz;
	}

	public void setCfjfz(String cfjfz) {
		this.cfjfz = cfjfz;
	}

	public String getCfjfz1() {
		return cfjfz1;
	}

	public void setCfjfz1(String cfjfz1) {
		this.cfjfz1 = cfjfz1;
	}

	public String getCfjwx() {
		return cfjwx;
	}

	public void setCfjwx(String cfjwx) {
		this.cfjwx = cfjwx;
	}

	public String getCfjyj() {
		return cfjyj;
	}

	public void setCfjyj(String cfjyj) {
		this.cfjyj = cfjyj;
	}

	public String getCfjyy() {
		return cfjyy;
	}

	public void setCfjyy(String cfjyy) {
		this.cfjyy = cfjyy;
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
