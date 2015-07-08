package com.bskcare.ch.vo.rpt;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 营养餐 
 */
@Entity
@Table(name = "rpt_nutritious_meals")
public class RptNutritiousMeals implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String breakfast;
	private String zaojia;
	private String lunch;
	private String wujia;
	private String dinner;
	private String wanjia;
	private String taboo;
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBreakfast() {
		return breakfast;
	}
	public void setBreakfast(String breakfast) {
		this.breakfast = breakfast;
	}
	public String getZaojia() {
		return zaojia;
	}
	public void setZaojia(String zaojia) {
		this.zaojia = zaojia;
	}
	public String getLunch() {
		return lunch;
	}
	public void setLunch(String lunch) {
		this.lunch = lunch;
	}
	public String getWujia() {
		return wujia;
	}
	public void setWujia(String wujia) {
		this.wujia = wujia;
	}
	public String getDinner() {
		return dinner;
	}
	public void setDinner(String dinner) {
		this.dinner = dinner;
	}
	public String getWanjia() {
		return wanjia;
	}
	public void setWanjia(String wanjia) {
		this.wanjia = wanjia;
	}
	public String getTaboo() {
		return taboo;
	}
	public void setTaboo(String taboo) {
		this.taboo = taboo;
	}

	@Override
	public String toString() {
		return "RptNutritiousMeals [breakfast=" + breakfast + ", dinner="
				+ dinner + ", id=" + id + ", lunch=" + lunch + ", taboo="
				+ taboo + ", wanjia=" + wanjia + ", wujia=" + wujia
				+ ", zaojia=" + zaojia + "]";
	}
}
