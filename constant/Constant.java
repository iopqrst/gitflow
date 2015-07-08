package com.bskcare.ch.constant;

import net.sf.json.JSONArray;

import com.bskcare.ch.util.SystemConfig;

/**
 * 常量信息
 * @author houzhiqing
 */
public class Constant {

	/**
	 * 正常状态
	 */
	public static final int STATUS_NORMAL = 0;
	
	/**
	 * 非正常状态: 删除或冻结
	 */
	public static final int STATUS_UNNORMAL = 1;
	
	/**
	 * 保存前端用户SESSION
	 */
	public static final String CLIENT_USER = "session_of_client_user";
	
	/**
	 * 保存后台管理端SESSION
	 */
	public static final String MANAGE_USER = "session_of_manage_user";
	/**
	 * 保存后台管理端SESSION
	 */
	public static final String MANAGE_CRM_USER = "session_of_manage_crm_user";
	/**
	 * 保存后台管理端SESSION
	 */
	public static final String ANDROID_USER = "android_ClientInfo";
	/**
	 * 管理员所有functions
	 */
	public static final String MANAGE_USER_FUNCTION = "manager_user_functions";
	
	/** 管理员角色对应菜单*/
	public static final String MANAGE_USER_MENU = "manager_user_menu";
	
	/** 管理员角色对应权限*/
	public static final String MANAGE_USER_PREMISSION = "manager_user_permission";
	
	/** 管理员角色*/
	public static final String MANAGE_USER_ROLE = "manager_user_role";
	
	/**
	 * 保存后台登录角色的地区链
	 */
	public static final String MANAGE_USER_AREAS="session_of_manage_user_area";
	
	/**
	 * 用户代管人员列表
	 */
	public static final String MANAGE_AGENT_USER="session_of_manage_agent_user";
	
	/**
	 * 每天最大允许生成产品卡数量
	 */
	public static final int MAX_PRODUCT_QUANLITY = Integer.parseInt(SystemConfig.getString("max_product_quanlity"));
	
	/**
	 * 没有可用服务项目
	 */
	public static final int NO_AVAILABLE_SERVICE = 0;
	
	/**
	 * 可用服务项目（/扣除消费成功）
	 */
	public static final int EXPENSE_SERVICE_SUC = 1;
	
	/**
	 * 可用服务项目扣除消费失败
	 */
	public static final int EXPENSE_SERVICE_FAIL = 2;
	
	/**
	 * 服务过期了
	 */
	public static final int SERVICE_EXPIRE = 3;
	
	/**
	 * 没有购买过任何产品服务
	 */
	public static final int NO_SELLING_SERVICE = 4;

	/**
	 * 中医体质服务条目
	 */
	public static final String ITEM_CMC = "item_cmc";
	
	/**
	 * 亲情号码绑定健康提醒
	 */
	public static final String ITEM_FAMILY_SMS = "item_family_sms";
	
	/**
	 * 数据监测项目
	 */
	public static final String ITEM_MONITORING = "item_monitoring";

	/**
	 * android接口： 非法访问
	 */
	public static final int INTERFACE_FORBIDDEN = -4;
	/**
	 * android接口： 参数格式不正确
	 */
	public static final int INTERFACE_PARAM_ERROR = -3;
	/**
	 * android接口： cid与手机号不匹配(android接口： 成功)
	 */
	public static final int INTERFACE_NOT_MATCH = -2;
	
	/**
	 * android接口： 手机号或cid为空
	 */
	public static final int INTERFACE_CM_EMPTY = -1;
	
	/**
	 * android接口： 失败
	 */
	public static final int INTERFACE_FAIL = 0;
	
	/**
	 * android接口： 成功
	 */
	public static final int INTERFACE_SUCC = 1;
	
	/**
	 * 健康报告：血压结论节点
	 */
	public static final String RPT_NODE_PRESSURE_CON = "A_001";
	/**
	 * 健康报告：血压风险节点
	 */
	public static final String RPT_NODE_PRESSURE_HAZARDS = "A_002";
	/**
	 * 健康报告：血糖结论节点
	 */
	public static final String RPT_NODE_SUGAR_CON = "B_001";
	/**
	 * 健康报告：血糖风险节点
	 */
	public static final String RPT_NODE_SUGAR_HAZARDS = "B_002";
	/**
	 * 健康报告：血糖2h结论节点
	 */
	public static final String RPT_NODE_SUGAR2_CON = "C_001";
	/**
	 * 健康报告：血糖2h风险节点
	 */
	public static final String RPT_NODE_SUGAR2_HAZARDS = "C_002";
	/**
	 * 健康报告：血氧结论节点
	 */
	public static final String RPT_NODE_SPO2_CON = "D_001";
	/**
	 * 健康报告：血氧风险节点
	 */
	public static final String RPT_NODE_SPO2_HAZARDS = "D_002";
	/**
	 * 健康报告：心电结论节点
	 */
	public static final String RPT_NODE_ECG_CON = "E_001";
	/**
	 * 健康报告：心电风险节点
	 */
	public static final String RPT_NODE_ECG_HAZARDS = "E_002";
	
	/**
	 * 健康报告：营养运动干预执行情况
	 */
	public static final String RPT_SPORT_INTERVENE = "F_001";
	/**
	 * 健康报告：健康评估
	 */
	public static final String RPT_HEALTH_ASSESSMENT = "G_001";
	
	/**
	 * 健康报告：关键字分割符(,)
	 */
	public static final String RPT_TAG_SPLIT = ",";
	
	
	public static final String SESSION_OF_CHAT = "session_of_chat";
	/**
	 * 聊天tag前缀
	 */
	public static final String CHAT_PREFIX = "chat";
	
	/**
	 * 产品当前环境 (正式环境)
	 */
	public static final String ENVIRONMENT_PRODUCT = "product";
	
	/**
	 * 产品当前环境 (开发环境)
	 */
	public static final String ENVIRONMENT_DEVELOPMENT = "development";
	
	public static final String CHARSET_UTF8 = "UTF-8";
//	
//	/**
//	 * 生成建议报告按照天生成还是按照月生成 0:天 1：月
//	 */
//	public static final int GENERATE_SRPT_TYPE_DAY = 0;
//	/**
//	 * 生成建议报告按照天生成还是按照月生成 0:天 1：月
//	 */
//	public static final int GENERATE_SRPT_TYPE_MONTH = 1;
	/**
	 * 生产简易报告，10天小报告 天数
	 */
	public static final int GENERATE_SRPT_TEN_DAY = 10;
	/**
	 * 生产简易报告，10天小报告 类型
	 */
	public static final int GENERATE_SRPT_TYPE_DAY = 2;
	/**
	 * 生产简易报告，30天小报告 月数
	 */
	public static final int GENERATE_SRPT_ONE_MONTH = 1;
	/**
	 * 生产简易报告，30天小报告 类型
	 */
	public static final int GENERATE_SRPT_TYPE_MONTH = 3;
	//TODO 继续添加
	
	/**对应软件（0: 表示 云健康管家 或者动动更健康, 1: 管血糖  2:管血压)*/ 
	public static final int SOFT_YUN_PLANT = 0;
	/**对应软件（0: 表示 云健康管家 或者动动更健康, 1: 管血糖  2:管血压) */
	public static final int SOFT_GUAN_XUE_TANG = 1;
	/**对应软件（0: 表示 云健康管家 或者动动更健康, 1: 管血糖  2:管血压) */
	public static final int SOFT_GUAN_XUE_YA = 2;
	/**为了推广创建了一个临时软件就为，出此下策，流氓*/
	public static final int SOFT_RUABISH = 3;
	/**对160提供接口*/
	public static final int SOFT_160 = 4;
	
	/**
	 * 产品信息
	 */
	public static final String MANAGE_PRODUCT_INFO = "session_of_product";
	
	public static final String AREANAMELIST = "areaName_of_areaId";
}
