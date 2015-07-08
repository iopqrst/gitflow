package com.bskcare.ch.dao.impl.tg;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.tg.TgFoodDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.tg.TgFood;

@Repository
@SuppressWarnings("unchecked")
public class TgFoodDaoImpl extends BaseDaoImpl<TgFood> implements TgFoodDao {

	public PageObject queryTgFood(TgFood tgFood, QueryInfo queryInfo) {
		List args = new ArrayList();
		String hql = "from TgFood where 1 = 1";
		if (tgFood != null) {
			if (!StringUtils.isEmpty(tgFood.getName())) {
				hql += " and name like ?";
				args.add("%" + tgFood.getName().trim() + "%");
			}
			if (tgFood.getType() != null && tgFood.getType() != 0) {
				hql += " and type = ?";
				args.add(tgFood.getType());
			}
		}
		hql += " order by id desc";
		return queryObjects(hql, args.toArray(), queryInfo);
	}

	public int findByName(String name) {
		String hql = "from TgFood where 1=1 and name=? ";
		List list = executeFind(hql, new Object[] { name });
		if (!CollectionUtils.isEmpty(list)) {
			return 1;
		} else {
			return 0;
		}
	}

	public List<TgFood> queryFoodByType(Integer type) {
		String hql = "from TgFood where type = ? ";
		return executeFind(hql, type);
	}

}
