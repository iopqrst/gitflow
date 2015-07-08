package com.bskcare.ch.vo.medal;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ntg_client_medal")
public class NTgMedalTemp implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer clientId;
	private int type;
	private int score;
	private int level;

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public NTgMedalTemp() {
	}

	public NTgMedalTemp(Integer clientId, int type, int score, int level) {
		this.clientId = clientId;
		this.type = type;
		this.score = score;
		this.level = level;
	}

}
