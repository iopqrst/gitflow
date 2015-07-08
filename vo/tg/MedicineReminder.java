package com.bskcare.ch.vo.tg;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 服药提醒
 */
@Entity
@Table(name = "tg_medicine_reminder")
public class MedicineReminder implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer clientId;
	private String alertTime;
	private String drugName;
//	private int reminder; //对应的铃声

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

	public String getAlertTime() {
		return alertTime;
	}

	public void setAlertTime(String alertTime) {
		this.alertTime = alertTime;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

//	public int getReminder() {
//		return reminder;
//	}
//
//	public void setReminder(int reminder) {
//		this.reminder = reminder;
//	}

	@Override
	public String toString() {
		return "MedicineReminder [alertTime=" + alertTime + ", clientId="
				+ clientId + ", drugName=" + drugName + ", id=" + id
				+ ", reminder=" + 0 + "]";
	}

}
