package com.bskcare.ch.constant;

/**
 * 积分系统常量
 */
public class ScoreConstant {

	/*** 1、金币 2、积分 */
	public static final int SCORE_CATEGORY_COIN = 1;
	/*** 1、金币 2、积分 */
	public static final int SCORE_CATEGORY_SCORE = 2;

	/**
	 * 积分规则(0:首次 1、每日首次 2、每次 3、需自定义)
	 */
	public static final int MODULE_TYPE_FIRST = 0;
	/**
	 * 积分规则(0:首次 1、每日首次 2、每次 3、需自定义)
	 */
	public static final int MODULE_TYPE_EVERY_DAY_FIRST = 1;
	/**
	 * 积分规则(0:首次 1、每日首次 2、每次 3、需自定义)
	 */
	public static final int MODULE_TYPE_EVERYTIME = 2;
	/**
	 * 积分规则(0:首次 1、每日首次 2、每次 3、需自定义)
	 */
	public static final int MODULE_TYPE_SELFDEFINE = 3;

	/**收入 ：1， 支出：2*/
	public static final int TYPE_OF_RECORD_INCOME = 1;
	/**收入 ：1， 支出：2*/
	public static final int TYPE_OF_RECORD_PAY = 2;
	
	
	
	
	
	//---------------------模块常量定义开始------------------
	/**
	 * 添加昵称
	 */
	public static final int MODULE_NICKNAME = 1;
	/**
	 * 头像
	 */
	public static final int MODULE_HEADER = 2;
	/**
	 * 昵称 + 头像
	 */
	public static final int MODULE_NICKNAME_VS_HEADER = 3;
	/**
	 * 签到
	 */
	public static final int MODULE_SIGN_IN = 4;
	/**
	 * 风险评估
	 */
	public static final int MODULE_EVALUARE = 5;
	/**
	 * 上传血糖值积分
	 */
	public static final int MODULE_BLOODSUGAR_JIFEN = 6;
	/**
	 * 上次血糖值金币
	 */
	public static final int MODULE_BLOODSUGAR_JINBI = 21;
	/**
	 * 记录食谱
	 */
	public static final int MODULE_FOOD = 7;
	/**
	 * 记录运动
	 */
	public static final int MODULE_SPORT = 8;
	/**
	 * 记录吃药
	 */
	public static final int MODULE_DRUY = 9;
	/**
	 * 推荐病友积分
	 */
	public static final int MODULE_DISEASE_JIFEN = 14; 
	/**
	 * 推荐病友金币
	 */
	public static final int MODULE_DISEASE_JINBI = 23;
	/**
	 * 推荐医生积分
	 */
	public static final int MODULE_DOCTER_JIFEN = 15;
	/**
	 * 推荐医生金币
	 */
	public static final int MODULE_DOCTER_JINBI = 24;
	/**
	 * 关注医生
	 */
	public static final int MODULE_FOCUSE_DOCTER = 17;
	/**
	 * 评价医生
	 */
	public static final int MODULE_EVAL_DOCTER = 18;
	/**
	 * 测血压
	 */
	public static final int MODULE_BLOOD_PRESSURE = 19;
	
	/**
	 * 消费行为
	 */
	public static final int MODULE_EXPENSE = 20;
	//... 
	//---------------------模块常量定义结束------------------
	
}
