package com.bskcare.ch.service;

import com.bskcare.ch.vo.UserSeat;

public interface UserSeatService {
	
	/**
	 * 根据管理员id查询管理员对应坐席信息
	 */
	public UserSeat queryUserSeat(Integer userId);
}
