package com.bskcare.ch.dao.impl.rpt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.rpt.RptFoodDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.rpt.RptFood;

@SuppressWarnings("unchecked")
@Repository
public class RptFoodDaoImpl extends BaseDaoImpl<RptFood> implements RptFoodDao {

	public List<String> queryRptFoodName(RptFood food, String queryCondition) {
		String sql = "select name from rpt_food where 1 = ?";

		ArrayList args = new ArrayList();
		args.add(1);
		if (!StringUtils.isEmpty(queryCondition)) {
			sql += queryCondition;
		}

		if (null != food) {
			if (null != food.getSpring()) {
				sql += " and spring = ?";
				args.add(food.getSpring());
			}
			if (null != food.getSummer()) {
				sql += " and summer = ?";
				args.add(food.getSummer());
			}
			if (null != food.getAutumn()) {
				sql += " and autumn = ?";
				args.add(food.getAutumn());
			}
			if (null != food.getWinter()) {
				sql += " and winter = ?";
				args.add(food.getWinter());
			}
			if (null != food.getBreakfast()) {
				sql += " and breakfast = ?";
				args.add(food.getBreakfast());
			}
			if (null != food.getZaojia()) {
				sql += " and zaojia = ?";
				args.add(food.getZaojia());
			}
			if (null != food.getLunch()) {
				sql += " and lunch = ?";
				args.add(food.getLunch());
			}
			if (null != food.getWujia()) {
				sql += " and wujia = ?";
				args.add(food.getWujia());
			}
			if (null != food.getDinner()) {
				sql += " and dinner = ?";
				args.add(food.getDinner());
			}
			if (null != food.getWanjia()) {
				sql += " and wanjia = ?";
				args.add(food.getWanjia());
			}
			if (null != food.getType()) {
				sql += " and type = ?";
				args.add(food.getType());
			}
		}
		sql += " ORDER BY RAND() LIMIT ?";
		args.add(new Random().nextInt(10) + 5);

		List<String> list = executeNativeQuery(sql, args.toArray());
		return list;
	}

	public PageObject<RptFood> findFoodPage(RptFood food, QueryInfo queryInfo) {
		List args = new ArrayList();
		String hql = "from RptFood where 1=1";
		if (null != food) {
			if (!StringUtils.isEmpty(food.getName())) {
				hql += " and name like ?";
				args.add("%" + food.getName().trim() + "%");
			}
			if (null != food.getType()) {
				hql += " and type = ?";
				args.add(food.getType());
			}
		}
		hql += " order by id desc";
		return queryObjects(hql, args.toArray(), queryInfo);
	}

	public void addFood(String fields, String values) {
		String sql = "insert into rpt_food (" + fields + ") values(" + values
				+ ")";
		updateBySql(sql);
	}

	public void updateFoodById(String fields, Integer id) {
		String sql = "update rpt_food set " + fields + " where id = ?";
		updateBySql(sql, id);
	}

	public List findFoodById(Integer id) {
		String sql = "select * from rpt_food where id = ?";
		SQLQuery query = this.getSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter(0, id);
		List<Map> mapList = query.list();
		return mapList;
	}

	public void addProperty(String property) {
		String sql = "alter table rpt_food add " + property + " int(11)";
		updateBySql(sql);
	}

	public void delProperty(String property) {
		String sql = "alter table rpt_food drop column " + property;
		updateBySql(sql);

	}

}
