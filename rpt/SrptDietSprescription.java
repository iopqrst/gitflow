package com.bskcare.ch.vo.rpt;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *  
 */
@Entity
@Table(name = "srpt_diet_sprescription")
public class SrptDietSprescription implements Serializable {

	private static final long serialVersionUID = -4856263355534180138L;

	private Integer id;
	private String name;// 处方名称
	private String material;// 材料
	private String colove;// 做法
	private String effect;// 功效
	private String usemethod;// 用法
	private String taboo;// 禁忌、忌服

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getColove() {
		return colove;
	}

	public void setColove(String colove) {
		this.colove = colove;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	public String getUsemethod() {
		return usemethod;
	}

	public void setUsemethod(String usemethod) {
		this.usemethod = usemethod;
	}

	public String getTaboo() {
		return taboo;
	}

	public void setTaboo(String taboo) {
		this.taboo = taboo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "SrptDietSprescription [colove=" + colove + ", effect=" + effect
				+ ", id=" + id + ", material=" + material + ", name=" + name
				+ ", taboo=" + taboo + ", usemethod=" + usemethod + "]";
	}

}
