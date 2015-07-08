package com.bskcare.ch.dao;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.UserSeat;

public interface UserSeatDao extends BaseDao<UserSeat>{
	/**
	 * 根据管理员id查询管理员对应坐席信息
	 */
	public UserSeat queryUserSeat(UserSeat userSeat);
}
