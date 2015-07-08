package com.bskcare.ch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.ClientLinkDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.ClientLinkService;
import com.bskcare.ch.vo.client.ClientLink;

@Service
public class ClientLinkServiceImpl implements ClientLinkService {

	@Autowired
	private ClientLinkDao clientLinkDao;

	public void addClientLink(ClientLink link) {
		clientLinkDao.add(link);
	}

	public ClientLink findLinkByClientLinkId(Integer id, Integer clientId) {
		return clientLinkDao.findLinkByClientLinkId(id, clientId);
	}

	public PageObject<ClientLink> findClientLink(Integer clientId,
			QueryInfo queryInfo) {
		return clientLinkDao.findClientLink(clientId, queryInfo);
	}

	public void deleteClientLink(Integer id, Integer clientId) {
		clientLinkDao.deleteClientLink(id, clientId);
	}

	public void updateClientLink(ClientLink clientLink) {
		clientLinkDao.update(clientLink);
	}
}
