package com.bskcare.ch.vo;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 专家团队
 */
@Entity
@Table(name = "t_bsk_expert")
public class BskExpert implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 排序 首选 默认值是10
	 */
	public static final int BSK_EXPERT_DEFAULT = 10;

	/**
	 * 专家启用
	 */
	public static final int BSK_EXPERT_NORMAL = 0;

	/**
	 * 专家不启用
	 */
	public static final int BSK_EXPERT_NOTNORMAL = 1;

	/**
	 * 大夫
	 */
	public static final int ROLE_DOCTOR = 1;

	/**
	 * 健康管理师
	 */
	public static final int ROLE_HEATH_MANAGER = 2;
	
	/**
	 * 特邀专家
	 */
	public static final int ROLE_GUEST_MANAGER = 3;

	private Integer id;
	private String name;
	private Integer age;
	private String qq;
	private String mobile;//2014年5月7日10:40:03  tq坐席号
	private String duty;
	/**
	 * 专家肖像
	 */
	private String portrait;
	private String introduction;
	private Integer userId;
	private int status;

	/**
	 * 专长领域
	 */
	private String expertise;

	/**
	 * 满意度
	 */
	private double satisfaction;

	/**
	 * 预约次数
	 */
	private int subscribeCount;

	/**
	 * 可用（预约）时间
	 */
	private String availableTime;

	/**
	 * 专家角色1.大夫 2.健康管理师
	 */
	private int role;

	/**
	 * 排序 首选 默认值是10
	 */
	private int sort;

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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	@Lob
	@Basic(fetch = FetchType.LAZY)
	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getExpertise() {
		return expertise;
	}

	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}

	public double getSatisfaction() {
		return satisfaction;
	}

	public void setSatisfaction(double satisfaction) {
		this.satisfaction = satisfaction;
	}

	public int getSubscribeCount() {
		return subscribeCount;
	}

	public void setSubscribeCount(int subscribeCount) {
		this.subscribeCount = subscribeCount;
	}

	public String getAvailableTime() {
		return availableTime;
	}

	public void setAvailableTime(String availableTime) {
		this.availableTime = availableTime;
	}


	@Override
	public String toString() {
		return "BskExpert [age=" + age + ", availableTime=" + availableTime
				+  ", duty=" + duty + ", expertise="
				+ expertise + ", id=" + id + ", introduction=" + introduction
				+ ", mobile=" + mobile + ", name=" + name + ", portrait="
				+ portrait + ", qq=" + qq + ", role=" + role
				+ ", satisfaction=" + satisfaction + ", sort=" + sort
				+ ", status=" + status + ", subscribeCount=" + subscribeCount
				+ ", userId=" + userId + "]";
	}

}
