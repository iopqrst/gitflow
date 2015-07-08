package com.bskcare.ch.vo.rpt;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "rpt_knowledge_repo")
public class RptKnowledgeRepo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer kid;
	private String title;
	/**
	 * 知识库内容
	 */
	private String content;
	private Integer category;
	private Integer subCategory;
	private Integer thirdCategory;
	/**
	 * 位置节点
	 */
	private String node;
	/**
	 * 关键字：多个以逗号分开（英文逗号）
	 */
	private String tag;
	private Date createTime;
	private Integer userId;

	@Id
	@GeneratedValue
	public Integer getKid() {
		return kid;
	}

	public void setKid(Integer kid) {
		this.kid = kid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Lob
	@Basic(fetch = FetchType.LAZY)
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

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
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

	@Override
	public String toString() {
		return "RptKnowledgeRepo [category=" + category + ", content="
				+ content + ", createTime=" + createTime + ", kid=" + kid
				+ ", node=" + node + ", subCategory=" + subCategory + ", tag="
				+ tag + ", thirdCategory=" + thirdCategory + ", title=" + title
				+ ", userId=" + userId + "]";
	}
}
