package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.vo.search.ZcyVo;

public interface ZcyVoDao {
	List<ZcyVo> queryByCbm(String cbm);

	

	List<ZcyVo> queryByIdMsg(String id);
}
