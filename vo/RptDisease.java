package com.bskcare.ch.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rpt_disease")
public class RptDisease {
	
	/**
	 * 个人疾病
	 * */
	public static final Integer TYPEISSELF = 1 ;
	/**
	 * 
	 * */
	/**
	 * 家族疾病
	 */
	public static final Integer TYPEISFAMILY = 2 ;
	/**有此项疾病*/
	public static final Integer DISEASE_YES = 0 ;
	/**无此项疾病*/
	public static final Integer DISEASE_NO = 1 ;
	
	private Integer id;
	private String name;
	private String spell;
	private Integer sort;
	private Integer isSelf;
	private Integer isFamily;
	

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpell() {
		return spell;
	}

	public void setSpell(String spell) {
		this.spell = spell;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getIsSelf() {
		return isSelf;
	}

	public void setIsSelf(Integer isSelf) {
		this.isSelf = isSelf;
	}

	public Integer getIsFamily() {
		return isFamily;
	}

	public void setIsFamily(Integer isFamily) {
		this.isFamily = isFamily;
	}

}
