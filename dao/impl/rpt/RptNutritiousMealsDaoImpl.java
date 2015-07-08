package com.bskcare.ch.dao.impl.rpt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.rpt.RptNutritiousMealsDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.rpt.RptNutritiousMeals;

@Repository
@SuppressWarnings("unchecked")
public class RptNutritiousMealsDaoImpl extends BaseDaoImpl<RptNutritiousMeals>
		implements RptNutritiousMealsDao {

	/**
	 * 随机查询出一个营养套餐根据疾病
	 */
	public RptNutritiousMeals findNutritiousByDisease(RptNutritiousMeals meals,
			String queryCondition) {
		List args = new ArrayList();
		String sql = "select breakfast,zaojia,lunch,wujia,dinner,wanjia,taboo from rpt_nutritious_meals where 1= ?";
		args.add(1);

		if (!StringUtils.isEmpty(queryCondition)) {
			sql += queryCondition;
		}
		sql += " order by RAND() limit 1";
		List<RptNutritiousMeals> list = executeNativeQuery(sql, null, null,
				args.toArray(), RptNutritiousMeals.class);

		RptNutritiousMeals rnm = null;
		if (!CollectionUtils.isEmpty(list)) {
			rnm = list.get(0);
		}
		return rnm;
	}

	public int updateMealsBySingleField(String field, String content,
			Integer mealsId) {
		String sql = "update rpt_nutritious_meals " + field
				+ " = ? where id = ?";
		return updateBySql(sql, new Object[] { content, mealsId });
	}

	public void updateMeals(RptNutritiousMeals meals) {
		update(meals);
	}

	public PageObject findMealsPage(RptNutritiousMeals meals,
			QueryInfo queryInfo) {
		String hql = "from RptNutritiousMeals order by id desc";
		return queryObjects(hql, null, queryInfo);
	}

	public void addMeals(String fields, String values) {
		String sql = "insert into rpt_nutritious_meals (" + fields
				+ ") values(" + values + ")";
		updateBySql(sql);
	}

	public List findMealsById(Integer id) {
		String sql = "select * from rpt_nutritious_meals where id = ?";
		SQLQuery query = this.getSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter(0, id);
		List<Map> mapList = query.list();
		return mapList;
	}

	public void updateMealsById(String fields, Integer id) {
		String sql = "update rpt_nutritious_meals set " + fields
				+ " where id = ?";
		updateBySql(sql, id);
	}

	public void addProperty(String property) {
		String sql = "alter table rpt_nutritious_meals add " + property
				+ " int(11)";
		updateBySql(sql);
	}

	public void delProperty(String property) {
		String nutritiousSql = "alter table rpt_nutritious_meals drop column "
				+ property;
		updateBySql(nutritiousSql);

	}
}
