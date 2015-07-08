package com.bskcare.ch.service.impl.rpt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.dao.rpt.RptMaterialDao;
import com.bskcare.ch.service.rpt.RptMaterialService;
import com.bskcare.ch.util.RandomUtils;
import com.bskcare.ch.vo.rpt.RptMaterial;

@Service
public class RptMaterialServiceImpl implements RptMaterialService {

	@Autowired
	private RptMaterialDao materialDao;
	
	public RptMaterial add(RptMaterial m) {
		return materialDao.add(m);
	}
	
	public RptMaterial load(Integer id) {
		return materialDao.load(id);
	}
	
	public RptMaterial queryMaterialByKeywords(RptMaterial rm) {
		List<RptMaterial> list = materialDao.queryMaterialByKeywords(rm);
		if(CollectionUtils.isEmpty(list)) return null;
		int index = RandomUtils.getRandomIndex(list.size());
		RptMaterial material = list.get(index); //随机获到一个对象
		return material;
	}
	
}
