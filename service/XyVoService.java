package com.bskcare.ch.service;

import java.util.List;

import com.bskcare.ch.vo.search.XyVo;

public interface XyVoService {
	/**
	 * 疾病分类
	 * @param cbm
	 * @return
	 */
	public List<XyVo> queryByCbm(String cbm);

	/**
	 * 查询疾病信息
	 * @param id
	 * @return
	 */
	public List<XyVo> queryByIdMsg(String id);
}
