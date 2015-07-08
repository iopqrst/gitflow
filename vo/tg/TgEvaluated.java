package com.bskcare.ch.vo.tg;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tg_evaluated")
public class TgEvaluated implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long mobile;

	@Id
	
	public Long getMobile() {
		return mobile;
	}

	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}
}
