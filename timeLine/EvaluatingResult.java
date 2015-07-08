package com.bskcare.ch.vo.timeLine;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 评测结果实体，包含评测结果results
 * 
 * @author
 */
@Entity
@Table(name = "tg_evaluating_result")
public class EvaluatingResult {

	/**
	 * 对应软件（0: 表示 云健康管家, 1: 管血糖 2：管血压)
	 * 
	 * 相应常量请参考：Constant.SOFT_YUN_PLANT ....
	 */
	private Integer id;
	private String results; // 评测结果，json字符串。可转换成RiskResultBean的对象
	private Date testDate; // 测试时间
	private int softType; // 软件类型
	private Integer clientId; // 用户id

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getSoftType() {
		return softType;
	}

	public void setSoftType(int softType) {
		this.softType = softType;
	}

	public String getResults() {
		return results;
	}

	public void setResults(String results) {
		this.results = results;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public Date getTestDate() {
		return testDate;
	}

	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}

}
