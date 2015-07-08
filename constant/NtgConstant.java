package com.bskcare.ch.constant;

/**
 * 新版血糖高管共用常量
 * 
 * @author hzq
 *
 */
public class NtgConstant {

	/******************* 时间轴任务、内容、规则类型定义【开始】 ************************/
	/*** 起床 */
	public static final int CONTYPE_GET_UP = 0;
	
	/*** 早餐 */
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
	public static final int CONTYPE_ZAO_JIA = 4;
	/**
	 * 午加餐
	 */
	public static final int CONTYPE_WU_JIA = 5;
	/**
	 * 晚加餐
	 */
	public static final int CONTYPE_WAN_JIA = 6;
	/**
	 * 测血糖
	 */
	public static final int CONTYPE_TESTBG = 7;
	/**
	 * 风险评估
	 */
	public static final int CONTYPE_EVALUATING = 8;
	/**
	 * 睡觉
	 */
	public static final int CONTYPE_SLEEP = 9;
	/**
	 * 运动
	 */
	public static final int CONTYPE_SPORT = 10;
	
	/**
	 * 初始化
	 */
	public static final int CONSTATUS_INIT =1 ;
	
	/**
	 * 起床
	 */
	public static final int CONSTATUS_ACTIVE =2 ;
	
	/**
	 * 上传
	 */
	public static final int CONSTATUS_UPLOADED = 3 ;
	
	/******************* 时间轴任务、内容、规则类型定义【结束】 ************************/

	/**
	 * ntg_timeline_rule /tg_timeline_rule 合并症字段解释
	 * 
	 * <pre>
	 * <select name="timeLineRule.complication" id="timeLineRule_complication">
	 *     <option value="0">无合并症</option>
	 *     <option value="1">糖尿病冠心病</option>
	 *     <option value="2">高血压</option>
	 *     <option value="3">糖尿病肾病</option>
	 *     <option value="4">糖尿病脑梗</option>
	 *     <option value="5">糖尿病视网膜病变</option>
	 *     <option value="6">下肢静脉栓塞</option>
	 *     <option value="7">糖尿病足</option>
	 *     <option value="8">糖尿病周围神经病变（麻木、蚁走、虫爬、发热、触电、手套样感觉）</option>
	 *     <option value="9">糖尿病植物神经病变（便秘、腹泻、尿潴留和尿失禁、无痛性心肌梗死、体位性低血压、无感觉低血糖史等）</option>
	 *     <option value="10">多种合并症</option>
	 *     <option value="11">全部</option>
	 * </select>
	 * </pre>
	 */

	/**
	 * ntg_timeline_rule /tg_timeline_rule 评测的几种结果
	 * 
	 * <pre>
	 * <select name="timeLineRule.result" id="timeLineRule_result">
	 *   <option value="0">全部</option>
	 *   <option value="疑似1型糖尿病">疑似1型糖尿病</option>
	 *   <option value="疑似2型糖尿病">疑似2型糖尿病</option>
	 *   <option value="疑似妊娠糖尿病">疑似妊娠糖尿病</option>
	 *   <option value="疑似继发性糖尿病">疑似继发性糖尿病</option>
	 *   <option value="疑似糖尿病">疑似糖尿病</option>
	 *   <option value="高危">高危（无糖尿病，风险评估较高）</option>
	 *   <option value="低危">低危（无糖尿病，风险评估较低）</option>
	 * </select>
	 * </pre>
	 */
}
