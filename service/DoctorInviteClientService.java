package com.bskcare.ch.service;

public interface DoctorInviteClientService {

	/**
	 * 添加医生邀请客户
	 */
	public String saveDoctorInviteClient(Integer doctorId, String lstClient,
			String inviteCode, String docName);
	
	
	public String updateClientName(String mobile, Integer doctorId, String name);
}
