package com.bskcare.ch.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.Zy1VoDao;
import com.bskcare.ch.vo.search.Zy1Vo;
@Repository("zy1VoDao")
public class Zy1VoDaoImpl extends BaseDaoImpl<Zy1Vo> implements Zy1VoDao {

	public List<Zy1Vo> queryByCbm(String cbm) {
		// TODO Auto-generated method stub
		String hql="from Zy1Vo where 1=1 and cypbm like'%"+cbm+"%'";
		return this.executeFind(hql);
	}

	public List<Zy1Vo> queryByIdMsg(String id) {
		// TODO Auto-generated method stub
		String hql="from Zy1Vo where 1=1 and id=?";
		return this.executeFind(hql, Integer.parseInt(id));
	}

}
