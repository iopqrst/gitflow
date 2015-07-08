package com.bskcare.ch.vo.rpt;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="rpt_material")
public class RptMaterial implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	/**
	 * 素材内容
	 */
	private String content;
	/**
	 * 关键字，以逗号或分号分隔
	 */
	private String tag;
	/**
	 * 位置节点
	 */
	private String node;
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getNode() {
		return node;
	}
	public void setNode(String node) {
		this.node = node;
	}
	
	@Override
	public String toString() {
		return "RptMaterial [content=" + content + ", id=" + id + ", node="
				+ node + ", tag=" + tag + "]";
	}
	
}
