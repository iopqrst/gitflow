package com.bskcare.ch.vo.rpt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 食物 
 */
@Entity
@Table(name = "rpt_food")
public class RptFood implements Serializable{

	private static final long serialVersionUID = 1L;

	/**食物类型 主食:1; 蔬菜:2; 奶:3; 肉蛋:4; 豆类:5; 油:6; 水果:7;坚果:8; 等等**/
	public static final int TYPE_GULEI = 1;
	/**食物类型 主食:1; 蔬菜:2; 奶:3; 肉蛋:4; 豆类:5; 油:6; 水果:7;坚果:8; 等等**/
	public static final int TYPE_SHUCAI = 2;
	/**食物类型 主食:1; 蔬菜:2; 奶:3; 肉蛋:4; 豆类:5; 油:6; 水果:7;坚果:8; 等等**/
	public static final int TYPE_MILK = 3;
	/**食物类型 主食:1; 蔬菜:2; 奶:3; 肉蛋:4; 豆类:5; 油:6; 水果:7;坚果:8; 等等**/
	public static final int TYPE_ROUDAN = 4;
	/**食物类型 主食:1; 蔬菜:2; 奶:3; 肉蛋:4; 豆类:5; 油:6; 水果:7;坚果:8; 等等**/
	public static final int TYPE_BEAN = 5;
	/**食物类型 主食:1; 蔬菜:2; 奶:3; 肉蛋:4; 豆类:5; 油:6; 水果:7;坚果:8; 等等**/
	public static final int TYPE_OIL = 6;
	/**食物类型 主食:1; 蔬菜:2; 奶:3; 肉蛋:4; 豆类:5; 油:6; 水果:7;坚果:8; 等等**/
	public static final int TYPE_FRUIT = 7;
	/**食物类型 主食:1; 蔬菜:2; 奶:3; 肉蛋:4; 豆类:5; 油:6; 水果:7;坚果:8; 等等**/
	public static final int TYPE_NUT = 8;
	
	/**
	 * “1”表示多吃，-1表示少吃，0表示普通
	 * 表示在春、夏、秋、冬,以及早餐、早餐加餐、
	 * 午餐、午餐加餐、晚餐、晚餐加餐饮食多少
	 */
	public static final int EATING_MORE = 1;
	public static final int EATING_NORMAL = 0;
	public static final int EATING_LESS = -1;
	
	private Integer id;
	/**食物名称**/
	private String name;
	/**食物类型 主食:1; 蔬菜:2; 奶:3; 肉蛋:4; 豆类:5; 油:6; 水果:7;坚果:8; 等等**/
	private Integer type;
	/**春季**/
	private Integer spring;
	/**夏季**/
	private Integer summer;
	/**秋季**/
	private Integer autumn;
	/**冬季**/
	private Integer winter;
	/**早餐**/
	private Integer breakfast;
	/**早餐加餐**/
	private Integer zaojia;
	/**午餐**/
	private Integer lunch;
	/**午餐加餐**/
	private Integer wujia;
	/**晚餐**/
	private Integer dinner;
	/**晚餐加餐**/
	private Integer wanjia;
	/**管理员或医师**/
	private Integer userId;
	/**创建时间**/
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getSpring() {
		return spring;
	}
	public void setSpring(Integer spring) {
		this.spring = spring;
	}
	public Integer getSummer() {
		return summer;
	}
	public void setSummer(Integer summer) {
		this.summer = summer;
	}
	public Integer getAutumn() {
		return autumn;
	}
	public void setAutumn(Integer autumn) {
		this.autumn = autumn;
	}
	public Integer getWinter() {
		return winter;
	}
	public void setWinter(Integer winter) {
		this.winter = winter;
	}
	public Integer getBreakfast() {
		return breakfast;
	}
	public void setBreakfast(Integer breakfast) {
		this.breakfast = breakfast;
	}
	public Integer getZaojia() {
		return zaojia;
	}
	public void setZaojia(Integer zaojia) {
		this.zaojia = zaojia;
	}
	public Integer getLunch() {
		return lunch;
	}
	public void setLunch(Integer lunch) {
		this.lunch = lunch;
	}
	public Integer getWujia() {
		return wujia;
	}
	public void setWujia(Integer wujia) {
		this.wujia = wujia;
	}
	public Integer getDinner() {
		return dinner;
	}
	public void setDinner(Integer dinner) {
		this.dinner = dinner;
	}
	public Integer getWanjia() {
		return wanjia;
	}
	public void setWanjia(Integer wanjia) {
		this.wanjia = wanjia;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Override
	public String toString() {
		return "RptFood [autumn=" + autumn + ", breakfast=" + breakfast
				+ ", createTime=" + createTime + ", dinner=" + dinner + ", id="
				+ id + ", lunch=" + lunch + ", name=" + name + ", spring="
				+ spring + ", summer=" + summer + ", type=" + type
				+ ", userId=" + userId + ", wanjia=" + wanjia + ", winter="
				+ winter + ", wujia=" + wujia + ", zaojia=" + zaojia + "]";
	}
	
}
