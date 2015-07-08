package com.bskcare.ch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.UserSeatDao;
import com.bskcare.ch.service.UserSeatService;
import com.bskcare.ch.vo.UserSeat;

@Service
public class UserSeatServiceImpl implements UserSeatService{
	
	@Autowired
	private UserSeatDao userSeatDao;
	
	/**
	 * 根据管理员id查询管理员对应坐席信息
	 */
	public UserSeat queryUserSeat(Integer userId){
		UserSeat userSeat = new UserSeat();
		userSeat.setUserId(userId);
		
		return userSeatDao.queryUserSeat(userSeat);
	}
}
