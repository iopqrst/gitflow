package com.bskcare.ch.constant;

/**
 * 日志对应类别
 */
public class LogType {

	/**
	 * 客户注册成功
	 */
	public static final Integer CLIENT_REGISTED = 1;

	/**
	 * 客户登陆
	 */
	public static final Integer CLIENT_LOGIN = 2;

	/**
	 * 客户退出
	 */
	public static final Integer CLIENT_LOGOUT = 3;

	/**
	 * 客户升级VIP
	 */
	public static final Integer CLIENT_UPGRADE_VIP = 4;

	/**
	 * 客户上传血压数据
	 */
	public static final Integer CLIENT_UPLOAD_PRESS = 5;

	/**
	 * 客户上传空腹血糖数据
	 */
	public static final Integer CLIENT_UPLOAD_SUGAR = 6;

	/**
	 * 客户上传餐后2h血糖数据
	 */
	public static final Integer CLIENT_UPLOAD_SUGAR2H = 7;

	/**
	 * 客户上传血氧数据
	 */
	public static final Integer CLIENT_UPLOAD_OXYGEN = 8;

	/**
	 * 客户上传心电
	 */
	public static final Integer CLIENT_UPLOAD_ECG = 9;

	/**
	 * 客户一体机上传数据
	 */
	public static final Integer CLIENT_UPLOAD_ALL = 10;

	/**
	 * 添加亲情账号信息
	 */
	public static final Integer CLIENT_FAMILY_ADD = 11;

	/**
	 * 修改亲情账号信息
	 */
	public static final Integer CLIENT_FAMILY_MODIFY = 12;

	/**
	 * 删除亲情账号信息
	 */
	public static final Integer CLIENT_FAMILY_DELETE = 13;

	/**
	 * 管理员登陆
	 */
	public static final Integer USER_LOGIN = 14;

	/**
	 * 管理员退出
	 */
	public static final Integer USER_LOGINOUT = 15;

	/**
	 * 短信发送
	 */
	public static final Integer USER_SEND_SMS = 16;

	/**
	 * 短信取消
	 */
	public static final Integer USER_CANCLE_SMS = 17;

	/**
	 * 短信修改
	 */
	public static final Integer USER_MODIFY_SMS = 18;

	/**
	 * 专家团队-添加
	 */
	public static final Integer USER_EXPERT_ADD = 19;

	/**
	 * 专家团队-修改
	 */
	public static final Integer USER_EXPERT_MODIFY = 20;

	/**
	 * 专家团队-删除
	 */
	public static final Integer USER_EXPERT_DEL = 21;

	/**
	 * 分配负责人
	 */
	public static final Integer PRINCIPAL_ASSIGN = 22;

	/**
	 * 报告生成
	 */
	public static final Integer REPORT_GENERATE = 23;

	/**
	 * 报告修改
	 */
	public static final Integer REPORT_MODIFY = 24;

	/**
	 * 报告删除
	 */
	public static final Integer REPORT_DELETE = 25;

	/**
	 * 上传文件处理
	 */
	public static final Integer UPLOAD_FILE_DEAL = 26;

	/**
	 * 下载文件
	 */
	public static final Integer DOWNLOAD_FILE = 27;
	/**
	 * 中医体质调养方案-修改
	 */
	public static final Integer CMC_AFTERCARE_MODIFY = 28;

	/**
	 * 随访记录-添加
	 */
	public static final Integer FOLLOW_UP_VISITS_ADD = 29;
	/**
	 * 随访记录-修改
	 */
	public static final Integer FOLLOW_UP_VISITS_MODIFY = 30;
	/**
	 * 随访记录-处理
	 */
	public static final Integer FOLLOW_UP_VISITS_DEAL = 31;
	/**
	 * 随访记录-删除
	 */
	public static final Integer FOLLOW_UP_VISITS_DELETE = 32;
	/**
	 * 产品卡生成
	 */
	public static final Integer PRODUCT_CARD_GENERATE = 33;
	/**
	 * 产品卡分配
	 */
	public static final Integer PRODUCT_CARD_ASSIGN = 34;

	/**
	 * 产品项目-创建
	 */
	public static final Integer PRODUCT_ITEM_CREATE = 35;
	/**
	 * 产品项目-修改
	 */
	public static final Integer PRODUCT_ITEM_MODIFY = 36;
	/**
	 * 产品项目-删除
	 */
	public static final Integer PRODUCT_ITEM_DELETE = 37;
	/**
	 * 产品套餐-增加
	 */
	public static final Integer PRODUCT_PACKAGE_ADD = 38;
	/**
	 * 产品套餐-修改
	 */
	public static final Integer PRODUCT_PACKAGE_MODIFY = 39;
	/**
	 * 产品套餐-删除
	 */
	public static final Integer PRODUCT_PACKAGE_DELETE = 40;
	/**
	 * 站内信息-增加
	 */
	public static final Integer SYSTEM_MESSAGE_ADD = 41;
	/**
	 * 站内信息-修改
	 */
	public static final Integer SYSTEM_MESSAGE_MODIFY = 42;
	/**
	 * 站内信息-删除
	 */
	public static final Integer SYSTEM_MESSAGE_DELETE = 43;
	/**
	 * 今日任务-增加
	 */
	public static final Integer TODAY_TASK_ADD = 44;
	/**
	 * 今日任务-修改
	 */
	public static final Integer TODAY_TASK_MODIFY = 45;
	/**
	 * 今日任务-删除
	 */
	public static final Integer TODAY_TASK_DELETE = 46;
	
	/**
	 * 处理上传数据
	 */
	public static final Integer HANDLER_UPLOAD_DATA = 47;
	
	/**
	 * 处理上传异常数据
	 */
	public static final Integer HANDLER_EXCEPTION_DATA = 48;
	/**
	 * 新建任务
	 */
	public static final Integer CREATE_TASK = 49;
	/**
	 * 转发任务
	 */
	public static final Integer TRANSPOND_TASK = 50;
	/**
	 * 处理任务
	 */
	public static final Integer HANDLER_TASK = 51;
	/**
	 * 添加任务流程
	 */
	public static final Integer CREATE_TASK_FLOW = 52;
	/**
	 * 删除任务流程
	 */
	public static final Integer DELETE_TASK_FLOW = 53;
	/**
	 * 删除任务
	 */
	public static final Integer DELETE_TASK = 54;
	/**
	 * 查看任务详情
	 */
	public static final Integer CHECK_TASK_COMMENT = 55;
	/**
	 * 添加电话随访
	 */
	public static final Integer CREATE_MOBILE_FOLLOWUPVISITS = 56;
	/**
	 * 添加信息随访
	 */
	public static final Integer CREATE_msm_FOLLOWUPVISITS = 57;
	
	/**
	 * 添加健康等级
	 */
	public static final Integer ADD_HEALTH_LEVEL = 58;
	/**
	 * 修改健康等级
	 */
	public static final Integer UPDATE_HEALTH_LEVEL = 59;
	/**
	 * 删除健康等级
	 */
	public static final Integer DELETE_HEALTH_LEVEL = 60;

	/**
	 * 上传体温数据
	 */
	public static final Integer CLIENT_UPLOAD_TEMPERATURE = 61;
	
	/**
	 * 上传早餐前血糖
	 */
	public static final Integer CLIENT_UPLOAD_SUGAR_ZAOCAN_BEFORE = 62;
	/**
	 * 上传早餐后血糖
	 */
	public static final Integer CLIENT_UPLOAD_SUGAR_ZAOCAN_AFTER = 63;
	/**
	 * 上传午餐前血糖
	 */
	public static final Integer CLIENT_UPLOAD_SUGAR_WUCAN_BEFORE = 64;
	
	/**
	 * 上传午餐后血糖
	 */
	public static final Integer CLIENT_UPLOAD_SUGAR_WUCAN_AFTER = 65;
	/**
	 * 上传晚餐前血糖
	 */
	public static final Integer CLIENT_UPLOAD_SUGAR_WANCAN_BEFORE = 66;
	/**
	 * 上传晚餐后血糖
	 */
	public static final Integer CLIENT_UPLOAD_SUGAR_WANCAN_AFTER = 67;
	/**
	 * 上传睡前血糖
	 */
	public static final Integer CLIENT_UPLOAD_SUGAR_BEFORE_SLEEP = 68;
	/**
	 * 上传凌晨血糖
	 */
	public static final Integer CLIENT_UPLOAD_SUGAR_LINGCHEN = 69;
}
