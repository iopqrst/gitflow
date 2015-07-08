package com.bskcare.ch.service.rpt;

import com.bskcare.ch.vo.rpt.RptMaterial;

public interface RptMaterialService {

	public RptMaterial add(RptMaterial m);

	public RptMaterial load(Integer id);
	
	/**
	 * 根据素材库的节点和关键字随机查询一个素材接口对象
	 */
	public RptMaterial queryMaterialByKeywords(RptMaterial rm);
}
