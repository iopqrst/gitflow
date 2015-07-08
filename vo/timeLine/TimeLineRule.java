package com.bskcare.ch.vo.timeLine;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 时间轴规则
 */

@Entity
@Table(name = "tg_timeline_rule")
public class TimeLineRule {
	/**
	 * 早餐
	 */
	public static final int CONTYPE_BREAKFAST = 1;
	/**
	 * 午餐
	 */
	public static final int CONTYPE_LUNCH = 2;
	/**
	 * 晚餐
	 */
	public static final int CONTYPE_DINNER = 3;
	/**
	 * 早加餐
	 */
	public static final int CONTYPE_BREAKFASTJIA = 4;
	/**
	 * 午加餐
	 */
	public static final int CONTYPE_LUNCHJIA = 5;
//	/**
//	 * 晚加餐
//	 */
//	public static final int CONTYPE_DINNERJIA = 6;
	/**
	 * 注册
	 */
	public static final int CONTYPE_LOGIN = 7;
	/**
	 * 风险评估
	 */
	public static final int CONTYPE_EVALUATING = 8;
	/**
	 * 购买服务
	 */
	public static final int CONTYPE_PAY = 9;
	/**
	 * 活动新闻，公告
	 */
	public static final int CONTYPE_NEW = 10;
	/**
	 * 测血糖
	 */
	public static final int CONTYPE_TESTBG = 11;
	/**
	 * 服药提醒
	 */
	public static final int CONTYPE_EATMEDICINE = 12;
	/**
	 * 报告提醒
	 */
	public static final int CONTYPE_RPTREMIND = 13;
	/**
	 * 生活习惯提醒
	 */
	public static final int CONTYPE_HABIT = 14;
	/**
	 * 测血压
	 */
	public static final int CONTYPE_TESTBP = 15;
	/**
	 * 运动
	 */
	public static final int CONTYPE_SPORT = 16;
	/**
	 * 起床
	 */
	public static final int CONTYPE_GETUP = 17;
	/**
	 * 午睡
	 */
	public static final int CONTYPE_SIESTA = 21;
	/**
	 * 睡觉
	 */
	public static final int CONTYPE_SLEEP = 20;
	
	/**
	 * 糖糖咨询
	 */
	public static final int CONTYPE_TT_ZIXUN = 23;
	/**
	 * 评测报告
	 */
	public static final int CONTYPE_ASSESSRPT = 25;
	/**
	 * 用户自己添加的任务
	 */
	public static final int CONTYPE_USERCUSTOM = 26;
	/**
	 * 医生添加的任务
	 */
	public static final int CONTYPE_DOCCUSTOM = 27;
	/**
	 * 天气预报
	 */
	public static final int CONTYPE_WEATHER = 28;
	/**
	 * 定时推送（根据任务）
	 */
	public static final int CONTYPE_SMS = 29;

	// 时间轴点击动作
	/**
	 * 无动作
	 */
	public static final int OPENMODE_NO = 0;
	/**
	 * 直接跳转
	 */
	public static final int OPENMODE_DIRECTSKIP = 1;
	/**
	 * 打开内容
	 */
	public static final int OPENMODE_OPENCONTENT = 2;
	/**
	 * 打开内容并跳转
	 */
	public static final int OPENMODE_OPENCONTENTSKIP = 3;
	/**
	 * 饮食有评价
	 */
	public static final int OPENMODE_REPAST = 4;
	// 跳转目标
	/**
	 * 无动作
	 */
	public static final int SKIPGOAL_NO = 0;
	/**
	 * 跳转到注册
	 */
	public static final int SKIPGOAL_LOGIN = 1;
	/**
	 * 跳转到风险评测
	 */
	public static final int SKIPGOAL_EVALUATING = 2;
	/**
	 * 跳转到测血糖
	 */
	public static final int SKIPGOAL_TESTBG = 3;
	/**
	 * 跳转到升级vip
	 * 
	 */
	public static final int SKIPGOAL_PAYSERVICE = 4;
	/**
	 * 跳转到个人信息
	 */
	public static final int SKIPGOAL_ARCHIVE = 5;
	/**
	 * 跳转到url
	 */
	public static final int SKIPGOAL_URL = 6;
	/**
	 * 跳转到服务列表
	 */
	public static final int SKIPGOAL_SERVICELIST = 8;
	/**
	 * 跳转到糖糖资讯
	 */
	public static final int SKIPGOAL_TT_ZIXUN = 13;
	/**
	 * 跳转到服务列表
	 */
	public static final int SKIPGOAL_TGRPT = 14;

	/**
	 * 日常任务
	 */
	public static final int CYCLETYPE_EVERYDAY = 1;
	/**
	 * 周期性任务(从用户第一次测试开始)
	 */
	public static final int CYCLETYPE_CYCLICITY_TEST = 2;
	/**
	 * 只执行一次
	 */
	public static final int CYCLETYPE_SOLE = 3;
	/**
	 * 周期性任务(从用户购买服务)
	 */
	public static final int CYCLETYPE_CYCLICITY_SERVICE = 4;
	/**
	 * 有提醒
	 */
	public static final int ISALERT_YES = 1;
	/**
	 * 无提醒
	 */
	public static final int ISALERT_NO = 2;

	/**
	 * 启用
	 */
	public static final int STAUTS_YES = 1;
	/**
	 * 禁用
	 */
	public static final int STAUTS_NO = 2;

	/**
	 * 购买服务用户
	 */
	public static final int ISPAY_YES = 1;
	/**
	 * 未购买服务用户
	 */
	public static final int ISPAY_NO = 2;
	/**
	 * 购买和未购买服务用户
	 */
	public static final int ISPAY_ALL = 3;
	/**
	 * 付费用户正常生成内容，非付费用户发出升级提示
	 */
	public static final int ISPAY_FREE_HINT = 4;
	/**
	 * 表示所有的评测结果的用户
	 */
	public static final String RESULT_ALL = "0";
	/**
	 * 表示所有的评测结果的用户
	 */
	public static final int COMPLICATION_ALL = 11;

	private Integer id; // id
	private Integer conType;// 任务类型
	private Integer isAlert;// 是否提醒 1有提醒  2 无提醒
	private Integer isPay;// 是否需要购买服务 1付费用户  ， 2 未付费用户， 3 全部用户
	private String taskTime;// 任务时间点 
	private Integer cycleType;// 任务周期类型 1日常任务2周期性任务(从用户第一次测试开始)3只执行一次4周期性任务(从用户购买服务)
	private Integer cycle;// 任务周期
	private String result;// 评测结果
	private Date createTime;// 创建时间
	private Integer softType;// 软件类型 (0: 表示 云健康管家 或者动动更健康, 1: 管血糖 2: 管血压 常量详情请参考：Constant.SOFT_YUN_PLANT ....)
	private Integer stauts;// 状态 1启用 2禁用
	private Integer openMode;// 打开方式		0:无动作1:'直接跳转',2:'打开内容',3:'打开内容并跳转',4:'饮食有评价'
	private Integer skipGoal;// 跳转目标		0:无动作1：'跳转到注册',2:'跳转到风险评测',3:'跳转到测血糖',4:'跳转到购买服务',5:'跳转到个人信息'，6:’跳转到url’,7：'跳到测血压,8:'跳到设备与服务的服务页面',9:'跳到设备与服务的设备详情',10:'跳到软件详情',11:'跳到管血糖'12：‘健康报告’，13：“糖糖咨询”，14：“评测报告”
	private int complication;// 合并症

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getConType() {
		return conType;
	}

	public void setConType(Integer conType) {
		this.conType = conType;
	}

	public Integer getIsAlert() {
		return isAlert;
	}

	public void setIsAlert(Integer isAlert) {
		this.isAlert = isAlert;
	}

	public String getTaskTime() {
		return taskTime;
	}

	public void setTaskTime(String taskTime) {
		this.taskTime = taskTime;
	}

	public Integer getCycleType() {
		return cycleType;
	}

	public void setCycleType(Integer cycleType) {
		this.cycleType = cycleType;
	}

	public Integer getCycle() {
		return cycle;
	}

	public void setCycle(Integer cycle) {
		this.cycle = cycle;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getSoftType() {
		return softType;
	}

	public void setSoftType(Integer softType) {
		this.softType = softType;
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

	public Integer getIsPay() {
		return isPay;
	}

	public void setIsPay(Integer isPay) {
		this.isPay = isPay;
	}

	public int getComplication() {
		return complication;
	}

	public void setComplication(int complication) {
		this.complication = complication;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Integer getSkipGoal() {
		return skipGoal;
	}

	public void setSkipGoal(Integer skipGoal) {
		this.skipGoal = skipGoal;
	}

}
