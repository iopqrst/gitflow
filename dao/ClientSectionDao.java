package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.client.ClientSection;

public interface ClientSectionDao extends BaseDao<ClientSection> {
	/**
	 * 科室对象
	 * @param id
	 * @return
	 */
	public List<ClientSection> find(Integer id);
}
