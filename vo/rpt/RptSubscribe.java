package com.bskcare.ch.vo.rpt;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rpt_subscribe")
public class RptSubscribe implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 未生成报告 */
	public static final int FLAG_UNGENERATE = 0;
	/** 已经生成报告 */
	public static final int FLAG_GENERATED = 1;

	private Integer id;
	private Integer omId;
	private Integer clientId;
	/** 预约生成报告时间 */
	private String subscribeTime;
	/** 实际生成报告时间 */
	private Date generateTime;
	/** 是否生成报告表示：0未生成 1：已经生成 */
	private int flag;

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOmId() {
		return omId;
	}

	public void setOmId(Integer omId) {
		this.omId = omId;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(String subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public Date getGenerateTime() {
		return generateTime;
	}

	public void setGenerateTime(Date generateTime) {
		this.generateTime = generateTime;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "RptSubscribe [clientId=" + clientId + ", flag=" + flag
				+ ", generateTime=" + generateTime + ", id=" + id + ", omId="
				+ omId + ", subscribeTime=" + subscribeTime + "]";
	}
}
