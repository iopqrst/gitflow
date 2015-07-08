package com.bskcare.ch.vo.score;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 积分模块
 */
@Entity
@Table(name = "s_score_module")
public class ScoreModule implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	/**
	 * 模块名称
	 */
	private String module;
	/**
	 * 具体分值
	 */
	private int score;
	
	/**
	 * 对应类型 (0、首次  1、每日首次 2、每次 3、需自定义)
	 */
	private int type;
	/**
	 * 上限分值
	 */
	private Integer limit;
	/**
	 * 类别：1、金币 2：积分
	 */
	private int category;
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "ScoreModule [category=" + category + ", id=" + id + ", limit="
				+ limit + ", module=" + module + ", score=" + score + ", type="
				+ type + "]";
	}

}
