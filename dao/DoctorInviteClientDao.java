package com.bskcare.ch.dao;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.DoctorInviteClient;

public interface DoctorInviteClientDao extends BaseDao<DoctorInviteClient>{
	
	/**
	 * 根据手机号码，医生id查询
	 */
	public DoctorInviteClient queryDoctorInviteClient(String mobile, Integer doctorId);
}
