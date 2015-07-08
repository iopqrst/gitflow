package com.bskcare.ch.vo.medal;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ntg_medal_module")
public class NTgMedalModule implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public static final int MEDAL_HARD = 1;	//最努力
	public static final int MEDAL_VALID = 2; //最有效
	public static final int MEDAL_HEART = 3; // 最热心
	
	private Integer id;
	private String name;
	private Date createTime;
	
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
