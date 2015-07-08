package com.bskcare.ch.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.ZyVoDao;
import com.bskcare.ch.vo.search.ZyVo;
@Repository("zyVoDao")
public class ZyVoDaoImpl extends BaseDaoImpl<ZyVo> implements ZyVoDao {

	public List<ZyVo> queryByCbm(String cbm) {
		// TODO Auto-generated method stub
		String hql="from ZyVo where 1=1 and cypbm like'%"+cbm+"%'";
		return this.executeFind(hql);
	}

	public List<ZyVo> queryByIdMsg(String id) {
		// TODO Auto-generated method stub
		String hql="from ZyVo where 1=1 and id=?";
		return this.executeFind(hql, Integer.parseInt(id));
	}
      
}
