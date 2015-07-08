package com.bskcare.ch.vo;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_client_location")
public class ClientLocation implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer clientId;
	/**经度**/
	private double longitude; 
	/**纬度**/
	private double latitude;
	/**所在区域**/
	private String location;
	/**活跃度**/
	private int liveness;
	/**好评数**/
	private int goodReputation;
	/**投诉数**/
	private int complain;
	
	
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
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

	public int getLiveness() {
		return liveness;
	}
	public void setLiveness(int liveness) {
		this.liveness = liveness;
	}
	public int getGoodReputation() {
		return goodReputation;
	}
	public void setGoodReputation(int goodReputation) {
		this.goodReputation = goodReputation;
	}
	public int getComplain() {
		return complain;
	}
	public void setComplain(int complain) {
		this.complain = complain;
	}
}
