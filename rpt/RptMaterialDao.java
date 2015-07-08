package com.bskcare.ch.dao.rpt;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.rpt.RptMaterial;

public interface RptMaterialDao extends BaseDao<RptMaterial>{
	
	public List<RptMaterial> queryMaterialByKeywords(RptMaterial rm) ;
}
