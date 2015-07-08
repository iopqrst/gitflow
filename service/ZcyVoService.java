package com.bskcare.ch.service;

import java.util.List;

import com.bskcare.ch.vo.search.ZcyVo;

public interface ZcyVoService {
	public List<ZcyVo> queryByCbm(String cbm) ;
	public List<ZcyVo> queryByIdMsg(String id);

}
