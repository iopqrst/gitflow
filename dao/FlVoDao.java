package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.vo.search.FlVo;

public interface FlVoDao {
	List<FlVo> queryByCbm(String cbm);
	
	String queryAnyOne(String cbpbm,Integer id);

	List<FlVo> queryByIdMsg(String id);
}
