package com.bskcare.ch.vo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "t_client_family_relation")
public class FamilyRelation implements Serializable {
	private static final long serialVersionUID = -70571478472359104L;
	
	private Integer id ;
	
	private Integer clientId ;
	
	private Integer familyId ;
	
	private String familyRelation ;
	
	private Integer shortMessage ;
	
	private String familyName ;
	
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

	public Integer getFamilyId() {
		return familyId;
	}

	public void setFamilyId(Integer familyId) {
		this.familyId = familyId;
	}

	public String getFamilyRelation() {
		return familyRelation;
	}

	public void setFamilyRelation(String familyRelation) {
		this.familyRelation = familyRelation;
	}

	public Integer getShortMessage() {
		return shortMessage;
	}

	public void setShortMessage(Integer shortMessage) {
		this.shortMessage = shortMessage;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	
	
	
}
