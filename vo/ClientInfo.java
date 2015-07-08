package com.bskcare.ch.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Administrator
 * 
 */
@Entity
@Table(name = "t_clientinfo")
public class ClientInfo implements Serializable {

	/**
	 * 用户被删除状态(status=1)
	 */
	public static final int STATUS_DELETE = 1;

	/**
	 * 用户是正常的状态(status=0)
	 */
	public static final int STATUS_NORMAL = 0;
	
	/**
	 * 用户密码正确
	 */
	public static final int PWD_RINGT = 0;
	
	/**
	 * 用户密码错误
	 */
	public static final int PWD_WRONG = 1;

	/**
	 * 用户是被冻结状态(status=2)
	 */
	public static final int STATUS_FREEZE = 2;

	/**
	 * vip用户(type="1")
	 */
	public static final String TYPE_VIP = "1";

	/**
	 * 亲情账号(type=2)
	 */
	public static final String TYPE_FAMILY = "2";

	/**
	 * 普通用户（type=3）
	 */
	public static final String TYPE_EXPERIENCE = "3";

	/**
	 * email存在
	 */
	public static final int E_EXIT = 1;

	/**
	 * email不存在
	 */
	public static final int E_NOTEXIT = 0;

	/**
	 * 手机号存在
	 */
	public static final int M_EXIT = 1;

	/**
	 * 手机号不存在
	 */
	public static final int M_NOTEXIT = 0;
	/**
	 * 验证码正确
	 */
	public static final int D_TRUE = 1;

	/**
	 * 验证码不正确
	 */
	public static final int D_NOTRUE = 0;
	
	/**
	 * 验证码与手机号码不相符
	 */
	public static final int D_NOMATCH = 2;
	
	/**
	 * 用户邀请码的开头
	 */
	public static final String CLIENT_INVITE = "9";
	/**
	 * 医生邀请码的开头
	 */
	public static final String DOC_INVITE = "1";
	
	/**
	 * 第三方合作公司， 移动
	 */
	public static final int COMPANY_USER_CMCC = 2; 

	private static final long serialVersionUID = 1L;
	private Integer id;// ID
	private String clientCode;// 客户ID
	private String account;// 用户账户
	private String password;// 用户密码
	private String name;// 用户姓名
	private String mobile;// 移动电话
	private String email;// 电子邮件
	private String vipCard;// vip卡号
	private String idCards; // 身份ID
	private Date birthday; // 生日
	private String birthPlace; // 籍贯
	private Integer gender; // 性别 男：0 女：1

	private Integer age;// 年龄
	private String ethnic;// 民族
	private String faith;// 信仰
	/** 血型： 1：A 2:B 3:O 4:AB 5:不详 */
	private Integer bloodType;
	/** RH阴性 1:是 2：否 3：不详 */
	private Integer rh;
	private String address;// 联系方式
	private String phone;// 住址电话
	/***
	 * 收入 1 ： < 1000 2 ： 1000 -2000 3 : 2000 -3000 4 ：3000-4500 5： 4500-6000 6：
	 * 6000-10000 7： > 10000
	 */
	private Integer income; // 收入
	private String usualAddress; // 常住地址
	private String postcode; // 邮政编码
	/**
	 * 婚姻状况 1:未婚 2：已婚 3：丧偶 4：离异 5：其他
	 */
	private Integer marriage;// 婚姻状况
	/**
	 * 教育程度 1：小学以下，2：小学，3：初中，4：高中或中专，5：大专或本科，6：研究生及以上
	 */
	private Integer culture;// 教育程度

	/**
	 *职业:1:工人 2: 干部 3:科技 4:金融 5:商业 6:教师 7: 医生 8: 学生 9:家务 10: 退休 11:待业 12:其他
	 */
	private String profession; // 职业

	private String workUnits; // 工作单位
	private String duty; // 职务
	private String unitsPhone; // 单位电话
	/**
	 * 医疗费用: 1 : 公费 2 ：社会保险 3 ：自费 4 ：其他
	 */
	private Integer medicalExpenses;//
	private Date createTime;// 创建时间或注册时间
	private Integer areaId;// 用户归属区域
	private String areaChain;// 区域链

	private String type;// 用户类型： 1 会员用户 2： 亲情账号 3：体验用户 4：团体用户
	private int integral;// 用户积分
	private Integer levelId; // 用户等级（拥有最高产品的等级）
	private int status; // 用户状态：0正常 1：删除 2：冻结
	private String  constellation ;    //星座
	private String  chineseZodiac ;    //生肖

	private Integer healthIndex; //健康指数
	private double finishPercent;// 档案完成率
	
	private Integer bazzaarGrade; // 市场评分
	//'-1':'全部','0':'黑户','1':'排斥','2':'较排斥','3':'一般','4':'有希望','5':'潜在客户','6':'过期用户','7':'糖尿病患者'
	
	private Integer principalId;//负责人id
	private String nickName;
	private String headPortrait;
	
	private String availableProduct; //客户购买可用产品id 
	
	// 第三方用户信息 
	private String uid;  //第三方用户id
	private Integer compSource;  // 第三方公司标识， 1：乐语用户 2：移动用户
	
	

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "age")
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getVipCard() {
		return vipCard;
	}

	public void setVipCard(String vipCard) {
		this.vipCard = vipCard;
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

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getCulture() {
		return culture;
	}

	public void setCulture(Integer culture) {
		this.culture = culture;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getIntegral() {
		return integral;
	}

	public void setIntegral(int integral) {
		this.integral = integral;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getIdCards() {
		return idCards;
	}

	public void setIdCards(String idCards) {
		this.idCards = idCards;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getEthnic() {
		return ethnic;
	}

	public void setEthnic(String ethnic) {
		this.ethnic = ethnic;
	}

	public String getFaith() {
		return faith;
	}

	public void setFaith(String faith) {
		this.faith = faith;
	}

	public Integer getBloodType() {
		return bloodType;
	}

	public void setBloodType(Integer bloodType) {
		this.bloodType = bloodType;
	}

	public Integer getRh() {
		return rh;
	}

	public void setRh(Integer rh) {
		this.rh = rh;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getIncome() {
		return income;
	}

	public void setIncome(Integer income) {
		this.income = income;
	}

	public String getUsualAddress() {
		return usualAddress;
	}

	public void setUsualAddress(String usualAddress) {
		this.usualAddress = usualAddress;
	}

	public Integer getMarriage() {
		return marriage;
	}

	public void setMarriage(Integer marriage) {
		this.marriage = marriage;
	}

	public String getWorkUnits() {
		return workUnits;
	}

	public void setWorkUnits(String workUnits) {
		this.workUnits = workUnits;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getUnitsPhone() {
		return unitsPhone;
	}

	public void setUnitsPhone(String unitsPhone) {
		this.unitsPhone = unitsPhone;
	}

	public Integer getMedicalExpenses() {
		return medicalExpenses;
	}

	public void setMedicalExpenses(Integer medicalExpenses) {
		this.medicalExpenses = medicalExpenses;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public Integer getLevelId() {
		return levelId;
	}

	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}

	public String getConstellation() {
		return constellation;
	}

	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}

	public String getChineseZodiac() {
		return chineseZodiac;
	}

	public void setChineseZodiac(String chineseZodiac) {
		this.chineseZodiac = chineseZodiac;
	}

	public Integer getHealthIndex() {
		return healthIndex;
	}

	public void setHealthIndex(Integer healthIndex) {
		this.healthIndex = healthIndex;
	}

	public double getFinishPercent() {
		return finishPercent;
	}

	public void setFinishPercent(double finishPercent) {
		this.finishPercent = finishPercent;
	}
	
	public String getAreaChain() {
		return areaChain;
	}

	public void setAreaChain(String areaChain) {
		this.areaChain = areaChain;
	}
	

	public Integer getBazzaarGrade() {
		return bazzaarGrade;
	}

	public void setBazzaarGrade(Integer bazzaarGrade) {
		this.bazzaarGrade = bazzaarGrade;
	}
	
	public Integer getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(Integer principalId) {
		this.principalId = principalId;
	}
	
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeadPortrait() {
		return headPortrait;
	}

	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}

	public String getAvailableProduct() {
		return availableProduct;
	}

	public void setAvailableProduct(String availableProduct) {
		this.availableProduct = availableProduct;
	}
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Integer getCompSource() {
		return compSource;
	}

	public void setCompSource(Integer compSource) {
		this.compSource = compSource;
	}

	@Override
	public String toString() {
		return "ClientInfo [account=" + account + ", address=" + address
				+ ", age=" + age + ", areaChain=" + areaChain + ", areaId="
				+ areaId + ", availableProduct=" + availableProduct
				+ ", bazzaarGrade=" + bazzaarGrade + ", birthPlace="
				+ birthPlace + ", birthday=" + birthday + ", bloodType="
				+ bloodType + ", chineseZodiac=" + chineseZodiac
				+ ", clientCode=" + clientCode + ", constellation="
				+ constellation + ", createTime=" + createTime + ", culture="
				+ culture + ", duty=" + duty + ", email=" + email + ", ethnic="
				+ ethnic + ", faith=" + faith + ", finishPercent="
				+ finishPercent + ", gender=" + gender + ", headPortrait="
				+ headPortrait + ", healthIndex=" + healthIndex + ", id=" + id
				+ ", idCards=" + idCards + ", income=" + income + ", integral="
				+ integral + ", levelId=" + levelId + ", marriage=" + marriage
				+ ", medicalExpenses=" + medicalExpenses + ", mobile=" + mobile
				+ ", name=" + name + ", nickName=" + nickName + ", password="
				+ password + ", phone=" + phone + ", postcode=" + postcode
				+ ", principalId=" + principalId + ", profession=" + profession
				+ ", rh=" + rh + ", status=" + status + ", type=" + type
				+ ", unitsPhone=" + unitsPhone + ", usualAddress="
				+ usualAddress + ", vipCard=" + vipCard + ", workUnits="
				+ workUnits + "]";
	}

}
