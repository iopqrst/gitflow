package com.bskcare.ch.vo.tg;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tg_food")
public class TgFood implements Serializable{

	private static final long serialVersionUID = 1L;

	/**食物类型 1：菜*/
	public static final int TYPE_CAI = 1;
	/**食物类型 2：鱼*/
	public static final int TYPE_FISH = 2;
	/**食物类型 3：虾*/
	public static final int TYPE_XIA = 3;
	/**食物类型 4：蛋*/
	public static final int TYPE_EGG = 4;
	/**食物类型 5：豆制品*/
	public static final int TYPE_DOU = 5;
	/**食物类型 6：主食（米）*/
	public static final int TYPE_ZHISHI_MI = 6;
	/**食物类型 7：主食（薯）*/
	public static final int TYPE_ZHISHI_SHU = 7;
	/**食物类型 8：馒头*/
	public static final int TYPE_ZHISHI_NOODLES = 8;
	/**食物类型 9：面条*/
	public static final int TYPE_ZHISHI_MANTOU = 9;
	/**食物类型 10：薯类*/
	public static final int TYPE_SHU = 10;
	/**食物类型 11：畜肉 */
	public static final int TYPE_XUROU = 11;
	/**食物类型 12：果蔬*/
	public static final int TYPE_VEGETABLE = 12;
	/**食物类型 13：水果*/
	public static final int TYPE_FRUIT = 13;
	/**食物类型 14：奶*/
	public static final int TYPE_MILK = 14;
	/**食物类型 15：豆浆*/
	public static final int TYPE_DOUJIANG = 15;
	/**食物类型 16：豆腐脑*/
	public static final int TYPE_DOUFUNAO = 16;
	/**食物类型 17：主食米（粗）*/
	public static final int TYPE_MI_CU = 17;
	/**食物类型 18：主食薯（粗）*/
	public static final int TYPE_SHU_CU = 18;
	/**食物类型 19：馒头（粗）*/
	public static final int TYPE_NOODLES_CU = 19;
	/**食物类型 20：面条（粗）*/
	public static final int TYPE_MANTOU_CU = 20;
	
	private Integer id;
	private String name;
	/**类型（1：菜 2：鱼 3：虾 4：蛋 5：豆制品 6：主食（米） 7：主食（薯）8：主食（面） 9：薯类 10：畜肉 11：果蔬 12：水果 13：奶 14：豆浆 15：豆腐脑）*/
	private Integer type;
	
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
}
