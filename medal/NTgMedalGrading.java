package com.bskcare.ch.vo.medal;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ntg_medal_grading")
public class NTgMedalGrading implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer medalId;
	private int level;
	private int score;
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMedalId() {
		return medalId;
	}
	public void setMedalId(Integer medalId) {
		this.medalId = medalId;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
}
