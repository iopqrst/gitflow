package com.bskcare.ch.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 产品
 * 
 * @author houzhiqing
 * 
 */
@Entity
@Table(name = "t_product")
public class Product implements Serializable {

	private static final long serialVersionUID = -70571478472359104L;
	
	private static Map<Integer, String> productMap = new HashMap<Integer, String>() ;
	
	static {
		productMap.put(10010, "月度VIP卡");
		productMap.put(10012, "季度VIP卡");
		productMap.put(10011, "年度VIP卡");
	}

	/**
	 * 个人类型产品
	 */
	public static final int TYPE_OF_PERSON = 1;

	/**
	 * 团体类型产品
	 */
	public static final int TYPE_OF_GROUP = 2;
	
	/**
	 * 产品类别：健康产品类别=1
	 */
	public static final int CATEGORY_HEALTH_PRODUCT = 1;
	
	public static final int ISSHOW_YES = 0;
	public static final int ISSHOW_NO = 1;

	private Integer id;

	/**
	 * 产品code
	 */
	private String code;
	private String name;
	private Date createTime;
	
	/**
	 * 最后修改时间
	 */
	private Date modifyTime;
	/**
	 * 现价
	 */
	private Double currentPrice;

	/**
	 * 原价
	 */
	private Double costPrice;

	/**
	 * 服务周期
	 */
	private int cycle;

	/**
	 * 产品简介
	 */
	private String introduction;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 类型1：团体，2：个人
	 */
	private int type;

	/**
	 * 产品等级
	 */
	private Integer levelId;

	/**
	 * 图片地址
	 */
	private String imageUrl;

	/**
	 * 产品分为： 健康产品和干预产品两个大类型， 干预产品又分为： 口服保健、外用保健、医疗保健
	 */
	private int category;

	/**
	 * 产品状态： 0 正常， 1 删除
	 */
	private int status;

	/**
	 * 创建人
	 */
	private Integer creator;
	/**
	 * 是否显示
	 */
	private int isShow;

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public Double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}

	public int getCycle() {
		return cycle;
	}

	public void setCycle(int cycle) {
		this.cycle = cycle;
	}

	@Lob
	@Basic(fetch = FetchType.LAZY) 
	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	@Lob
	@Basic(fetch = FetchType.LAZY) 
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Integer getLevelId() {
		return levelId;
	}

	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}
	
	public int getIsShow() {
		return isShow;
	}

	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", code=" + code + ", name=" + name
				+ ", createTime=" + createTime + ", modifyTime=" + modifyTime
				+ ", currentPrice=" + currentPrice + ", costPrice=" + costPrice
				+ ", cycle=" + cycle + ", introduction=" + introduction
				+ ", description=" + description + ", type=" + type
				+ ", levelId=" + levelId + ", imageUrl=" + imageUrl
				+ ", category=" + category + ",status = " + status + "]";
	}
	
	public static String getProductName(Integer productId) {
		if(null == productId) return "";
		return null == productMap.get(productId) ? "" : productMap.get(productId); 
	}

}
