package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.client.ClientMedicalHistory;

/**
 * 用户病史
 * 
 * @author houzhiqing
 */
public interface ClientMedicalHistoryDao extends BaseDao<ClientMedicalHistory> {
	
	public ClientMedicalHistory getClientMedicalHistory(Integer clientId);
	
	/**
	 * 全查
	 */
	public List<ClientMedicalHistory> queryAll();
	
	public void updateClientHealth(Integer clientId,Integer isHasMedical);
	
}
