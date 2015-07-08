package com.bskcare.ch.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.PhysicalItemDao;
import com.bskcare.ch.vo.client.PhysicalItem;

@Repository
@SuppressWarnings("unchecked")
public class PhysicalItemDaoImpl extends BaseDaoImpl<PhysicalItem> implements
		PhysicalItemDao {

	public void addPhysicalItem(PhysicalItem physicalItem) {
		add(physicalItem);
	}

	public List<PhysicalItem> findPhysicalItemByid(Integer clientId,
			Integer pdId) {
		String hql = "from PhysicalItem  where clientId=? and pdId=? order by physicalTime";
		Object[] obj = { clientId, pdId };
		List<PhysicalItem> lst = executeFind(hql, obj);
		if (!CollectionUtils.isEmpty(lst)) {
			return lst;
		}
		return null;
	}

	public List<PhysicalItem> findPhysicalItemBycId(Integer clientId) {
		String hql = "from PhysicalItem  where clientId=?";
		Object[] obj = { clientId };
		List<PhysicalItem> lst = executeFind(hql, obj);
		if (!CollectionUtils.isEmpty(lst))  {
			return lst;
		}
		return null;
	}

	
	public List findPhysicalItemBycIdpId(Integer clientId,Integer physicalId){
		String sql = "select {i.*} ,d.name name,d.up up ,d.down down ,d.units units from t_physical_item i , t_physical_detail d where i.clientId=? and i.physicalId=? and d.pdId = i.pdId";
		Object[] obj={clientId,physicalId};
		Map entities = new HashedMap();
		entities.put("i", PhysicalItem.class);
		Map scalars = new HashedMap();
		scalars.put("name", StandardBasicTypes.STRING);
		scalars.put("up", StandardBasicTypes.DOUBLE);
		scalars.put("down", StandardBasicTypes.DOUBLE);
		scalars.put("units", StandardBasicTypes.STRING);
		
		List lst = executeNativeQuery(sql, entities, scalars, obj,null);
		if (!CollectionUtils.isEmpty(lst)) {
			return lst;
		}
		return null;
	}
	
}
