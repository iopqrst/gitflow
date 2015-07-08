package com.bskcare.ch.vo.timeLine;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 时间点任务
 */
@Entity
@Table(name = "tg_timeline_task")
public class TimeLineTask {
	/**
	 * 禁用
	 */
	public static final int TASKTYPE_SHUT = 1;
	/**
	 * 未登录
	 */
	public static final int TASKTYPE_NOLOGIN = 2;
	/**
	 * 已登录用户
	 */
	public static final int TASKTYPE_LOGIN = 4;
	/**
	 * 所有用户
	 */
	public static final int TASKTYPE_ALL = 5;
	/**
	 * 用户登录但是没有评测
	 */
	public static final int TASKTYPE_NOTHAVE = 6;
	/**
	 * 固定任务状态
	 */
	public static final int STAUTS_FIX = 0;
	/**
	 * 未完成
	 */
	public static final int STAUTS_NO = 1;
	/**
	 * 已完成
	 */
	public static final int STAUTS_YES = 2;
	/**
	 * 饮食，不符合
	 */
	public static final int STAUTS_INCONFORMITY = 3;
	/**
	 * 饮食，基本符合
	 */
	public static final int STAUTS_ALMOST = 4;
	/**
	 * 饮食，完全符合
	 */
	public static final int STAUTS_CONFORM = 5;
	
	/**
	 *  一般提醒，返回用户时间轴是查询 对应 日期 和 用户id
	 */
	public static final int RESETTYPE_AUTO = 1;
	/**
	 * 一直显示的任务，查询时 查询对应的 状态和用户id  没有完成的返回
	 */
	public static final int RESETTYPE_TEMPORARY = 2;
	/**
	 * 一直显示的任务完成的临时状态，比如  报告，用户完成后将该类型   2 改成 3 ，当天仍会查询到 ，进行时间轴生成时将值4
	 */
	public static final int RESETTYPE_MANUAL = 3;
	/**
	 * 任务关闭状态，不会查询该类型任务。
	 */
	public static final int RESETTYPE_OFF = 4;
	/**
	 * 昨天
	 */
	public static final int YESTERDAY = -1;
	/**
	 * 今天
	 */
	public static final int TODAY = 0;
	/**
	 * 明天
	 */
	public static final int TOMORROW = 1;
	
	private Integer id;// id
	private Integer clientId; // 用户 (clientId 为空的为全局的任务，不要删除)
	private Date createTime;// 创建时间
	private String title;// 时间轴任务标题
	private String content;// 时间轴任务内容
	private Date taskDate;// 时间轴日期
	private String taskTime;// 时间点
	private Integer isAlert;// 是否提醒
	private Integer softType;// 软件类型
	private Integer taskType;// 任务类型    		1:禁用 2：未登录用户可查询到的任务   4：以登录的用户可查询到的任务  5：所用用户可查询到的任务 
	private Integer stauts;// 状态
	private Integer openMode;// 常量值见timeLineRule
	private Integer skipGoal;//常量值见timeLineRule
	private Integer resetType; //重置方式
	private Integer conType;	// 1:'早餐',2:'午餐',3:'晚餐',4:'早加餐',5:'午加餐',7:'提示注册',8:'风险评估',9:'购买服务',10:'活动新闻，公告',11:'测血糖',12:'服药提醒',13:'报告提醒',14:'生活习惯提醒',15:'测血压',16:'运动任务',17:'起床',20:'睡觉',21:'睡午觉',18:'报告沟通提示（讲解报告前一天）',19:'报告沟通提示（讲解报告当天）',22:'完善个人信息',23:'糖糖咨询',24:'效果总结',25:'风险评估报告',26：'用户自己添加的任务',27：'医生添加的任务',28：'天气预报'
	private String url;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getTaskDate() {
		return taskDate;
	}

	public void setTaskDate(Date taskDate) {
		this.taskDate = taskDate;
	}

	public String getTaskTime() {
		return taskTime;
	}

	public void setTaskTime(String taskTime) {
		this.taskTime = taskTime;
	}

	public Integer getIsAlert() {
		return isAlert;
	}

	public void setIsAlert(Integer isAlert) {
		this.isAlert = isAlert;
	}

	public Integer getSoftType() {
		return softType;
	}

	public void setSoftType(Integer softType) {
		this.softType = softType;
	}

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public Integer getStauts() {
		return stauts;
	}

	public void setStauts(Integer stauts) {
		this.stauts = stauts;
	}

	public Integer getOpenMode() {
		return openMode;
	}

	public void setOpenMode(Integer openMode) {
		this.openMode = openMode;
	}

	public Integer getResetType() {
		return resetType;
	}

	public void setResetType(Integer resetType) {
		this.resetType = resetType;
	}

	public Integer getConType() {
		return conType;
	}

	public void setConType(Integer conType) {
		this.conType = conType;
	}

	public Integer getSkipGoal() {
		return skipGoal;
	}

	public void setSkipGoal(Integer skipGoal) {
		this.skipGoal = skipGoal;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


}
