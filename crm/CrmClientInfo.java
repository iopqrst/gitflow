package com.bskcare.ch.bo.crm;

import java.util.Date;

public class CrmClientInfo {
	
	/**客户id**/
	private Integer clientId;
	/**会员卡号**/
	private String cardid;
	/**客户姓名**/
	private String name;  
	/**地区id**/
	private Integer areaId;
	/**年龄**/
	private Integer age;
	/**身份证id**/
	private String pid;
	/**性别**/
	private Integer sex; 
	/**生日**/
	private Date birthday;
	/**电话**/
	private String mobile;
	/**地址**/
	private String address;
	/**工作单位**/
	private String workAddress;
	/**手机**/
	private String telephone;
	/**邮箱**/
	private String email;
	/**管理员id**/
	private Integer userid;
	/**区域链**/
	private String flagid;
	/**身份证号**/
	private String idCards;
	/**crmId**/
	private Integer crmId; 
	
	
	public String getFlagid() {
		return flagid;
	}
	public void setFlagid(String flagid) {
		this.flagid = flagid;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	public String getCardid() {
		return cardid;
	}
	public void setCardid(String cardid) {
		this.cardid = cardid;
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
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getWorkAddress() {
		return workAddress;
	}
	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIdCards() {
		return idCards;
	}
	public void setIdCards(String idCards) {
		this.idCards = idCards;
	}
	public Integer getCrmId() {
		return crmId;
	}
	public void setCrmId(Integer crmId) {
		this.crmId = crmId;
	}
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
}
