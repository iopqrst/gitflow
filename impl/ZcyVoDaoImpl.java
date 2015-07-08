package com.bskcare.ch.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.ZcyVoDao;
import com.bskcare.ch.vo.search.ZcyVo;
@Repository("zcyVoDao")
public class ZcyVoDaoImpl extends BaseDaoImpl<ZcyVo> implements ZcyVoDao {

	public List<ZcyVo> queryByCbm(String cbm) {
		// TODO Auto-generated method stub
		String hql="from ZcyVo where 1=1 and cypbm like'%"+cbm+"%'";
		return this.executeFind(hql);
	}

	public List<ZcyVo> queryByIdMsg(String id) {
		// TODO Auto-generated method stub
		String hql="from ZcyVo where 1=1 and id=?";
		return this.executeFind(hql, Integer.parseInt(id));
	}

}
