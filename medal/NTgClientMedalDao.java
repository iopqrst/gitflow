package com.bskcare.ch.dao.medal;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.medal.NTgClientMedal;
import com.bskcare.ch.vo.medal.NTgMedalTemp;

public interface NTgClientMedalDao extends BaseDao<NTgClientMedal>{
	
	/**
	 * 根据客户id查询勋章信息
	 */
	public NTgClientMedal queryClientMedal(Integer clientId);
	
	/**
	 * 临时办法
	 * @param medalTemp
	 * @return
	 */
	public int insertMedalTemp(NTgMedalTemp medalTemp);
	
	public List<NTgMedalTemp> queryAllTempMedal(Integer clientId) ;
}
