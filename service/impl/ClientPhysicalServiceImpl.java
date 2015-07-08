package com.bskcare.ch.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.dao.ClientPhysicalDao;
import com.bskcare.ch.dao.PhysicalItemDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.ClientPhysicalService;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.client.ClientPhysical;
import com.bskcare.ch.vo.client.PhysicalItem;

@Service
public class ClientPhysicalServiceImpl implements ClientPhysicalService {

	@Autowired
	private ClientPhysicalDao clientPhysicalDao;
	@Autowired
	private PhysicalItemDao physicalItemDao;

	public PageObject<ClientPhysical> findClientPhysical(Integer clientId,
			QueryInfo queryInfo) {
		return clientPhysicalDao.findClientPhysical(clientId, queryInfo);
	}

	public void addClientPhysical(ClientPhysical physical, String details) {
		physical = clientPhysicalDao.add(physical);
		List<PhysicalItem> lstPhysicalItem = new ArrayList<PhysicalItem>();
		if (!StringUtils.isEmpty(details)) {

			lstPhysicalItem = JsonUtils.getList4Json(details,
					PhysicalItem.class);
			for (PhysicalItem item : lstPhysicalItem) {
				item.setPhysicalId(physical.getId());
				item.setPdId(item.getPdId());
				item.setResult(item.getResult());
				item.setClientId(physical.getClientId());
				item.setType(item.getType());
				item.setPhysicalTime(physical.getPhysicalTime());
				physicalItemDao.addPhysicalItem(item);
			}
		}
	}

	/**
	 * 根据体检id和客户id查询体检信息
	 */
	public ClientPhysical findClientPhysicalById(Integer id, Integer clientId) {
		return clientPhysicalDao.findClientPhysicalById(id, clientId);
	}

	public ClientPhysical findPhysicalItemById(Integer clientId,Integer physicalId){
		List<ClientPhysical> lstPhysical = clientPhysicalDao.queryPhysical(clientId);
		if (!CollectionUtils.isEmpty(lstPhysical)) {
			 for(int i=0;i<lstPhysical.size();i++){
				 if(lstPhysical.get(i).getId()==physicalId){
					 return lstPhysical.get(i);
				 }
			 }
		}
		return null;
	}

}
