package com.bskcare.ch.service;

import java.util.List;

import com.bskcare.ch.vo.search.Zy1Vo;

public interface Zy1VoService {
	public List<Zy1Vo> queryByCbm(String cbm) ;
	public List<Zy1Vo> queryByIdMsg(String id);
}
