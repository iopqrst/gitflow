package com.bskcare.ch.dao.impl.tg;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.tg.TgCookedFoodDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.tg.TgCookedFood;

@Repository
@SuppressWarnings("unchecked")
public class TgCookedFoodDaoImpl extends BaseDaoImpl<TgCookedFood> implements
		TgCookedFoodDao {

	public TgCookedFood queryCookedFood(TgCookedFood cookedFood,
			String contain, String cjoin, String noContain, String njoin,
			String disease) {
		List args = new ArrayList();
		String sql = "select * from tg_cooked_food where 1 = 1";

		if (cookedFood != null) {
			if (cookedFood.getType() != null) {
				sql += " and type = ?";
				args.add(cookedFood.getType());
			}

			if (!StringUtils.isEmpty(cookedFood.getCanci())) {
				sql += " and canci like ?";
				args.add("%" + cookedFood.getCanci() + "%");
			}
		}

		if (!StringUtils.isEmpty(contain)) {
			String[] foodTypes = contain.split("、");
			sql += " and (";
			if (foodTypes.length > 1) {
				for (int i = 0; i < foodTypes.length; i++) {
					sql += " foodType like ?";
					args.add("%" + foodTypes[i] + "%");
					if (i != foodTypes.length - 1) {
						sql += " " + cjoin + "";
					}
				}
			} else {
				sql += " foodType like ?";
				args.add("%" + foodTypes[0] + "%");
			}
			sql += ")";
		}

		if (!StringUtils.isEmpty(noContain)) {
			String[] foodTypes = noContain.split("、");
			sql += " and (";
			if (foodTypes.length > 1) {
				for (int i = 0; i < foodTypes.length; i++) {
					sql += " foodType not like ?";
					args.add("%" + foodTypes[i] + "%");
					if (i != foodTypes.length - 1) {
						sql += " " + njoin + "";
					}
				}
			} else {
				sql += " foodType not like ?";
				args.add("%" + foodTypes[0] + "%");
			}
			sql += ")";
		}

		if (!StringUtils.isEmpty(disease)) {
			sql += " and " + disease + " = 1";
		}

		sql += " order by RAND() limit 1";

		List<TgCookedFood> lst = executeNativeQuery(sql, args.toArray(),
				TgCookedFood.class);
		if (!CollectionUtils.isEmpty(lst)) {
			return lst.get(0);
		}
		return null;
	}

	public List<TgCookedFood> queryTgCookedFood(String foodType, String join) {
		List args = new ArrayList();
		String sql = "select * from tg_cooked_food where 1 = 1";

		if (!StringUtils.isEmpty(foodType)) {
			String[] foodTypes = foodType.split("#");
			sql += " and (";

			if (foodTypes.length > 1) {
				for (int i = 0; i < foodTypes.length; i++) {
					sql += " foodType like ?";
					args.add("%" + foodTypes[i] + "%");
					if (i != foodTypes.length - 1) {
						sql += " " + join + "";
					}
				}
			} else {
				sql += " foodType like ?";
				args.add("%" + foodTypes[0] + "%");
			}
			sql += ")";
		}
		return executeNativeQuery(sql, args.toArray());
	}

	public TgCookedFood queryCookedFood(String foodType, String join) {
		List args = new ArrayList();
		String sql = "select * from tg_cooked_food where 1 = 1";
		if (!StringUtils.isEmpty(foodType)) {
			String[] foodTypes = foodType.split("#");
			sql += " and (";
			if (foodTypes.length > 1) {
				for (int i = 0; i < foodTypes.length; i++) {
					sql += " foodType like ?";
					args.add("%" + foodTypes[i] + "%");
					if (i != foodTypes.length - 1) {
						sql += " " + join + "";
					}
				}
			} else {
				sql += " foodType like ?";
				args.add("%" + foodTypes[0] + "%");
			}
			sql += ")";
		}
		sql += " order by RAND() limit 1";

		List<TgCookedFood> lst = executeNativeQuery(sql, args.toArray());
		if (!CollectionUtils.isEmpty(lst)) {
			return lst.get(0);
		}
		return null;
	}

	public TgCookedFood queryCookedFood(Integer type, String contain,
			String cjoin, String noContain, String njoin, String disease) {
		List args = new ArrayList();
		String sql = "select * from tg_cooked_food where 1 = 1";

		if (type != null) {
			sql += " and type = ?";
			args.add(type);
		}

		if (!StringUtils.isEmpty(contain)) {
			String[] foodTypes = contain.split("#");
			sql += " and (";
			if (foodTypes.length > 1) {
				for (int i = 0; i < foodTypes.length; i++) {
					sql += " foodType like ?";
					args.add("%" + foodTypes[i] + "%");
					if (i != foodTypes.length - 1) {
						sql += " " + cjoin + "";
					}
				}
			} else {
				sql += " foodType like ?";
				args.add("%" + foodTypes[0] + "%");
			}
			sql += ")";
		}

		if (!StringUtils.isEmpty(noContain)) {
			String[] foodTypes = noContain.split("#");
			sql += " and (";
			if (foodTypes.length > 1) {
				for (int i = 0; i < foodTypes.length; i++) {
					sql += " foodType like ?";
					args.add("%" + foodTypes[i] + "%");
					if (i != foodTypes.length - 1) {
						sql += " " + njoin + "";
					}
				}
			} else {
				sql += " foodType like ?";
				args.add("%" + foodTypes[0] + "%");
			}
			sql += ")";
		}

		if (!StringUtils.isEmpty(disease)) {
			sql += "" + disease + "in (0,1)";
		}

		sql += " order by RAND() limit 1";

		List<TgCookedFood> lst = executeNativeQuery(sql, args.toArray());
		if (!CollectionUtils.isEmpty(lst)) {
			return lst.get(0);
		}
		return null;
	}

	public PageObject queryTgCookedFood(TgCookedFood cookedFood,
			QueryInfo queryInfo) {
		List args = new ArrayList();
		String hql = "from TgCookedFood where 1 = 1 ";
		if (cookedFood != null) {
			if (!StringUtils.isEmpty(cookedFood.getName())) {
				hql += " and name like ?";
				args.add(cookedFood.getName().trim());
			}
			if (cookedFood.getType() != null && cookedFood.getType() != 0) {
				hql += " and type = ?";
				args.add(cookedFood.getType());
			}
		}
		hql += " order by id desc";

		return queryObjects(hql, args.toArray(), queryInfo);
	}

	public List<TgCookedFood> queryCookByName(String name) {
		String hql = "from TgCookedFood where name=?";
		return executeFind(hql, name);
	}
	
	public List<TgCookedFood> queryCookedFood(String disease){
		String sql = "select * from tg_cooked_food where 1 = 1";
		if(!StringUtils.isEmpty(disease)){
			sql += " and "+disease+" = 1";
		}
		sql += " order by RAND()";
		return executeNativeQuery(sql, null, TgCookedFood.class);
	}
}
