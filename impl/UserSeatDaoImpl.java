package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.UserSeatDao;
import com.bskcare.ch.vo.UserSeat;

@Repository
@SuppressWarnings("unchecked")
public class UserSeatDaoImpl extends BaseDaoImpl<UserSeat> implements UserSeatDao{

	public UserSeat queryUserSeat(UserSeat userSeat){
		String hql = "from UserSeat where 1 = 1";
		List args = new ArrayList();
		if(userSeat != null){
			hql += " and userId = ?";
			args.add(userSeat.getUserId());
		}
		
		List<UserSeat> lst = executeFind(hql, args.toArray());
		if(!CollectionUtils.isEmpty(lst)){
			return lst.get(0);
		}
		return null;
	}
}
