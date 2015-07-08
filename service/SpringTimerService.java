package com.bskcare.ch.service;

/**
 * 定时任务管理类
 *
 */
public interface SpringTimerService {

	/**
	 * 每日更新用户年龄
	 */
	public String updateClientBirthdayToAge();
	
	/**
	 * 更新用户等级
	 */
	public String updateClientInfoLevel();
	
	/***
	 * 用户产品服务到期提醒
	 * <p>将即将到期的用户数据存放到中</p> 
	 */
	public int createServiceRemindRecord();
	
}
