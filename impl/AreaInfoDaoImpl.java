package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.AreaInfoDao;
import com.bskcare.ch.vo.AreaInfo;

@Repository("areaInfoDao")
@SuppressWarnings("unchecked")
public class AreaInfoDaoImpl extends BaseDaoImpl<AreaInfo> implements
		AreaInfoDao {

	public List<AreaInfo> executeFind(AreaInfo areaInfo) {
		List args = new ArrayList();
		String hql = "from AreaInfo areaInfo where status=? ";
		args.add(AreaInfo.AREA_NORMAL);
		if(areaInfo != null){
			if(areaInfo.getParentId()!=null){
				hql +=" and parentId = ? ";
				args.add(areaInfo.getParentId());
			}
		}	
		hql += "order by id";
		List<AreaInfo> list = executeFind(hql, args.toArray());
		return list;

	}

	public void deleteById(int id) {
		String hql = "update AreaInfo set status=? where id=?";
		Object[] obj = { AreaInfo.AREA_NOTNORMAL, id };
		updateByHql(hql, obj);
	}

	public void updateByIdName(int id, String name) {
		String hql = "update AreaInfo set name=? where id=?";
		Object[] obj = { name, id };
		updateByHql(hql, obj);
	}

	public void addAreaInfo(AreaInfo areaInfo) {
		add(areaInfo);
	}

	public int findMaxId() {
		int id = -1;
		String hql = "select max(id) from AreaInfo";
		Object obj = findUniqueResult(hql);
		if (null != obj) {
			id = Integer.parseInt(obj.toString());
		}
		return id;
	}

	public void updateAreaChain(AreaInfo areaInfo) {
		String hql = "update t_areainfo set areaChain=? where id=? ";
		Object[] obj = { areaInfo.getAreaChain(), areaInfo.getId() };
		updateBySql(hql, obj);
	}

	
	public void updateAreaInfo(AreaInfo areaInfo) {
		String hql = "update t_areainfo set parentId = ?,areaChain=? where id=? ";
		Object[] obj = {areaInfo.getParentId(),areaInfo.getAreaChain(),areaInfo.getId() };
		updateBySql(hql, obj);
	}
	
	public List<AreaInfo> getAdminArea(List list) {
		StringBuffer sql = new StringBuffer();
		ArrayList args = new ArrayList();
		sql.append("select * from t_areainfo where 1=1 ");
		sql.append(" and (");
		if(list.size() > 1) {
			System.out.println(list.size());
			for(int i = 0; i< list.size(); i++) {
				sql.append(" areachain like ?");
				args.add(list.get(i) + "%");
				if(i != list.size()-1 ) {
					sql.append(" or ");
				}
			}
		} else {
			sql.append(" areachain like ?");
			args.add(list.get(0) + "%");
		}
		sql.append(") and status="+Constant.STATUS_NORMAL);
		return executeNativeQuery(sql.toString(), null, null, args.toArray(), AreaInfo.class);
	}

}
