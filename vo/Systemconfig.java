package com.bskcare.ch.vo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 系统配置
 */
@Entity
@Table(name = "system_config")
public class Systemconfig implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String key;
	private String value;

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Systemconfig [id=" + id + ", key=" + key + ", value=" + value
				+ "]";
	}

}
