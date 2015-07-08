package com.bskcare.ch.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.XyVoDao;
import com.bskcare.ch.vo.search.XyVo;
@Repository("xyVoDao")
public class XyVoDaoImpl extends BaseDaoImpl implements XyVoDao {

	public List<XyVo> queryByCbm(String cbm) {
		String hql="from XyVo where 1=1 and cypbm like'%"+cbm+"%'";
		//String hql="select id,cypbm from t_drug_xy where 1=1 and cypbm like'%"+cbm+"%'";
		return this.executeFind(hql);
		//return (List<XyVo>) this.executeNativeQuery(hql,null,XyVo.class) ;
	}

	public List<XyVo> queryByIdMsg(String id) {
		// TODO Auto-generated method stub
		String hql="from XyVo where 1=1 and id=?";
		
		return this.executeFind(hql, Integer.parseInt(id));
	}

}
