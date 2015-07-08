package com.bskcare.ch.vo.medal;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ntg_medal_rule")
public class NTgMedalRule implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public static final int RULE_UPLOAD_BLOODPRESSURE = 1;
	public static final int RULE_UPLOAD_SPORT = 2;
	
	public static final int RULE_UPLOAD_BREAKFAST = 3;
	public static final int RULE_UPLOAD_LUNCH = 4;
	public static final int RULE_UPLOAD_DINNER = 5;
	
	public static final int RULE_BLOODSUGAR_GOOD = 6;
	public static final int RULE_BLOODSUGAR_HIGH = 7;
	public static final int RULE_BLOODSUGAR_LOW = 8;
	
	
	public static final int RULE_UPLOAD_SLEEP = 9;
	public static final int RULE_ZAOCAN_BEFORE = 10;
	public static final int RULE_ZAOCAN_AFTER = 11;
	public static final int RULE_WUCAN_BEFORE = 12;
	public static final int RULE_WUCAN_AFTER = 13;
	public static final int RULE_WANCAN_BEFORE = 14;
	public static final int RULE_WANCAN_AFTER = 15;
	public static final int RULE_SLEEP_BEFORE = 16;
	public static final int RULE_LINGCHEN = 17;
	public static final int RULE_INVITE_CLIENT= 18;
	
	
	
	private Integer id;
	private String name;
	private int type;
	private int score;
	private Integer limit;
	private Integer medalId;
	
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getMedalId() {
		return medalId;
	}
	public void setMedalId(Integer medalId) {
		this.medalId = medalId;
	}
}
