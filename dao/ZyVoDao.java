package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.vo.search.ZyVo;

public interface ZyVoDao {
	List<ZyVo> queryByCbm(String cbm);


	List<ZyVo> queryByIdMsg(String id);
}
