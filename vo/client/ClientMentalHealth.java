package com.bskcare.ch.vo.client;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户心理健康
 * 
 */
@Entity
@Table(name = "t_client_mental_health")
public class ClientMentalHealth implements Serializable {

	private static final long serialVersionUID = 8530096944458151179L;

	private Integer id;
	private Integer clientId;
	/** 我很快乐（1-完全不符合；2-比较不符合；3-一般；4-比较符合；5-完全符合；） */
	private int happy;
	/** 我对未来充满希望（1-完全不符合；2-比较不符合；3-一般；4-比较符合；5-完全符合；） */
	private int hope;
	/** 即使家人或朋友帮助，我也不能摆脱忧伤（1-完全不符合；2-比较不符合；3-一般；4-比较符合；5-完全符合；） */
	private int melancholy;
	/** 我感觉孤独（1-完全不符合；2-比较不符合；3-一般；4-比较符合；5-完全符合；） */
	private int longly;
	/** 我经常感觉压抑或沮丧（1-完全不符合；2-比较不符合；3-一般；4-比较符合；5-完全符合；） */
	private int depress;
	/** 我容易情绪激动（1-完全不符合；2-比较不符合；3-一般；4-比较符合；5-完全符合；） */
	private int excite;
	/** 我生活很紧张（1-完全不符合；2-比较不符合；3-一般；4-比较符合；5-完全符合；） */
	private int nerves;
	/** 生活重大事件: 1 : 失业 2 : 车祸 3 ：离婚 4 ：父亲去世 5 ：母亲去世 6 : 儿子去世 7 ： 女儿去世 8 ：其他 */
	private String event;
	/** 生活重大事件其他内容 */
	private String eventSupply;
	/** 厨房排风设施: 1 : 无 2 ： 油烟机 3 ：换气窗 4 ： 烟囱 */
	private int pfEquipment;
	/** 燃料类型： 1：液化气 2：煤 3:天然气 4:沼气 5：柴火 6：其他 */
	private int fuel;
	/** 饮用水：1：自来水 2：经净化过滤的水 3：井水 4：河湖水 5：塘水 6：其他 */
	private int water;
	/** 厕所： 1：卫生厕所 2：一格或二格粪池式 3：马桶 4：露天粪坑 5：简易棚厕 */
	private int toilet;
	/** 禽畜栏：1：单设 2：室内 3：室外 */
	private int pet;
	private Date createTime; // 创建时间

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public int getHappy() {
		return happy;
	}

	public void setHappy(int happy) {
		this.happy = happy;
	}

	public int getHope() {
		return hope;
	}

	public void setHope(int hope) {
		this.hope = hope;
	}

	public int getMelancholy() {
		return melancholy;
	}

	public void setMelancholy(int melancholy) {
		this.melancholy = melancholy;
	}

	public int getLongly() {
		return longly;
	}

	public void setLongly(int longly) {
		this.longly = longly;
	}

	public int getDepress() {
		return depress;
	}

	public void setDepress(int depress) {
		this.depress = depress;
	}

	public int getExcite() {
		return excite;
	}

	public void setExcite(int excite) {
		this.excite = excite;
	}

	public int getNerves() {
		return nerves;
	}

	public void setNerves(int nerves) {
		this.nerves = nerves;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getEventSupply() {
		return eventSupply;
	}

	public void setEventSupply(String eventSupply) {
		this.eventSupply = eventSupply;
	}

	public int getPfEquipment() {
		return pfEquipment;
	}

	public void setPfEquipment(int pfEquipment) {
		this.pfEquipment = pfEquipment;
	}

	public int getFuel() {
		return fuel;
	}

	public void setFuel(int fuel) {
		this.fuel = fuel;
	}

	public int getWater() {
		return water;
	}

	public void setWater(int water) {
		this.water = water;
	}

	public int getToilet() {
		return toilet;
	}

	public void setToilet(int toilet) {
		this.toilet = toilet;
	}

	public int getPet() {
		return pet;
	}

	public void setPet(int pet) {
		this.pet = pet;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "ClientMentalHealth [clientId=" + clientId + ", createTime="
				+ createTime + ", depress=" + depress + ", event=" + event
				+ ", eventSupply=" + eventSupply + ", excite=" + excite
				+ ", fuel=" + fuel + ", happy=" + happy + ", hope=" + hope
				+ ", id=" + id + ", longly=" + longly + ", melancholy="
				+ melancholy + ", nerves=" + nerves + ", pet=" + pet
				+ ", pfEquipment=" + pfEquipment + ", toilet=" + toilet
				+ ", water=" + water + "]";
	}

}
