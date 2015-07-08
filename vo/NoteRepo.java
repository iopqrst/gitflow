package com.bskcare.ch.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 短信
 */
@Entity
@Table(name = "t_note_repo")
public class NoteRepo implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String title;
	/**
	 * 知识库内容
	 */
	private String content;
	private Integer type;
	private Integer category;
	private Integer subCategory;
	private Integer thirdCategory;
	/**
	 * 关键字：多个以逗号分开（英文逗号）
	 */
	private String tag;
	private Date createTime;
	private Integer userId;

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public Integer getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(Integer subCategory) {
		this.subCategory = subCategory;
	}

	public Integer getThirdCategory() {
		return thirdCategory;
	}

	public void setThirdCategory(Integer thirdCategory) {
		this.thirdCategory = thirdCategory;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "NoteRepo [category=" + category + ", content=" + content
				+ ", createTime=" + createTime + ", id=" + id
				+ ", subCategory=" + subCategory + ", tag=" + tag
				+ ", thirdCategory=" + thirdCategory + ", title=" + title
				+ ", type=" + type + ", userId=" + userId + "]";
	}

}
