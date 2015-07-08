package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.vo.search.XyVo;

public interface XyVoDao {
	List<XyVo> queryByCbm(String cbm);

	List<XyVo> queryByIdMsg(String id);

	
}
