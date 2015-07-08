package com.bskcare.ch.service;

import java.util.List;

import com.bskcare.ch.vo.search.ZyVo;

public interface ZyVoService {
	public List<ZyVo> queryByCbm(String cbm);
	public List<ZyVo> queryByIdMsg(String id);
}
