package com.bskcare.ch.service;

import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.SearchDiseaseVo;

public interface SearchService {
	/**
	 * 分页 查询 药品 信息 
	 * @param con 条件 
	 * @param sss 对应 结果 
	 * @param queryInfo 分页 参数 
	 * @return
	 */
	public String getSssList(String con,SearchDiseaseVo searchDiseaseVo,QueryInfo queryInfo);

}
