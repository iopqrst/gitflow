package com.bskcare.ch.vo.client;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 家族疾病史
 */
@Entity
@Table(name = "t_client_disease_family")
public class ClientDiseaseFamily {

	/**
	 * 家属类型,FAMILY_FA为父亲
	 */
	public final static int FAMILY_FA = 0;
	/**
	 * 家属类型,FAMILY_MO为母亲
	 */
	public final static int FAMILY_MO = 1;
	/**
	 * 家属类型,FAMILY_OTHER为其他家属
	 */
	public final static int FAMILY_OTHER = 3;
	/**
	 * 家属类型,DISEASE_CHECKED_YES为用户选中疾病
	 */
	public final static int DISEASE_CHECKED_YES = 0;
	/**
	 * 家属类型,DISEASE_CHECKED_NO为用户未选中疾病(其他疾病)
	 */
	public final static int DISEASE_CHECKED_NO = 1;
	
	private Integer id;
	private Integer clientId;// 用户编号
	private int familyType;// 家族类型, 0：父亲  1：母亲   3：其他家属 
	private String disease;// 疾病编号或疾病名称
	private Date diagTime;// 疾病诊断时间
	private Integer diagAge;// 诊断年龄
	private int type;// 类型,0为选中疾病,1为其他疾病

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

	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

	public Date getDiagTime() {
		return diagTime;
	}

	public void setDiagTime(Date diagTime) {
		this.diagTime = diagTime;
	}

	public Integer getDiagAge() {
		return diagAge;
	}

	public void setDiagAge(Integer diagAge) {
		this.diagAge = diagAge;
	}

	public int getFamilyType() {
		return familyType;
	}

	public void setFamilyType(int familyType) {
		this.familyType = familyType;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "ClientDiseaseFamily [clientId=" + clientId + ", diagAge="
				+ diagAge + ", diagTime=" + diagTime + ", disease=" + disease
				+ ", familyType=" + familyType + ", id=" + id + ", type="
				+ type + "]";
	}
}
