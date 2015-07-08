package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.vo.search.Zy1Vo;

public interface Zy1VoDao {
	List<Zy1Vo> queryByCbm(String cbm);

	List<Zy1Vo> queryByIdMsg(String id);
}
