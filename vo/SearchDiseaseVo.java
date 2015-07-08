package com.bskcare.ch.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_searchDiseaseVo")
public class SearchDiseaseVo {
   private String tab;//表标识
   @Id
   @GeneratedValue
   private Integer id;//主键
   private String a;//药品名
   //一下为药品信息
   private String b;
   private String c;
   private String d;
   private String e;
   private String f;
   private String g;
   private String h;
   private String i;
   private String j;
   private String k;
   private String l;
   private String m;
   private String n;
   private String o;
   private String p;
	public String getTab() {
		return tab;
	}
	public void setTab(String tab) {
		this.tab = tab;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getA() {
		return a;
	}
	public void setA(String a) {
		this.a = a;
	}
	public String getB() {
		return b;
	}
	public void setB(String b) {
		this.b = b;
	}
	public String getC() {
		return c;
	}
	public void setC(String c) {
		this.c = c;
	}
	public String getD() {
		return d;
	}
	public void setD(String d) {
		this.d = d;
	}
	public String getE() {
		return e;
	}
	public void setE(String e) {
		this.e = e;
	}
	public String getF() {
		return f;
	}
	public void setF(String f) {
		this.f = f;
	}
	public String getG() {
		return g;
	}
public void setG(String g) {
	this.g = g;
}
public String getH() {
	return h;
}
public void setH(String h) {
	this.h = h;
}
public String getI() {
	return i;
}
public void setI(String i) {
	this.i = i;
}
public String getJ() {
	return j;
}
public void setJ(String j) {
	this.j = j;
}
public String getK() {
	return k;
}
public void setK(String k) {
	this.k = k;
}
public String getL() {
	return l;
}
public void setL(String l) {
	this.l = l;
}
public String getM() {
	return m;
}
public void setM(String m) {
	this.m = m;
}
public String getN() {
	return n;
}
public void setN(String n) {
	this.n = n;
}
public String getO() {
	return o;
}
public void setO(String o) {
	this.o = o;
}
public String getP() {
	return p;
}
public void setP(String p) {
	this.p = p;
}
   
   
}
