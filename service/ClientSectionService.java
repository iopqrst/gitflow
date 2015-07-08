package com.bskcare.ch.service;

import java.util.List;

import com.bskcare.ch.vo.client.ClientSection;

public interface ClientSectionService {
	/**
	 * 增加
	 */
	public ClientSection save(ClientSection c);
	/**
	 * 查询所有科室
	 */
	public List<ClientSection> find(Integer id);

}
