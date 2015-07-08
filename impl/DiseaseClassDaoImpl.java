package com.bskcare.ch.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.DiseaseClassDao;
import com.bskcare.ch.vo.search.DiseaseClass;

@Repository
public class DiseaseClassDaoImpl extends BaseDaoImpl implements DiseaseClassDao {

	public List<DiseaseClass> queryClassAll() {
		String hql = "from DiseaseClass where 1=1";
		return this.executeFind(hql);
	}

	public List<DiseaseClass> queryById(Integer id) {
		String hql = "from DiseaseClass d where 1=1 and d.isjid=? or d.id= ?";
		Object args[] = { id, id };
		return this.executeFind(hql, args);
	}

	public List<DiseaseClass> queryMenuByT(String t) {
		String sql = null;
		if (t.equals("1")) {
			sql = "select t.id,t.cmc,t.cbm from t_drug_class t where t.dfl = 1 and t.ifljb = 2";
		} else if (t.equals("2")) {
			sql = "select * from t_drug_class t where t.dfl = 2 and t.ifljb = 2 AND t.cbm like 'c%'";
		} else if (t.equals("3")) {
			sql = "select * from t_drug_class t where t.dfl = 5 and t.ifljb = 2 ";
		} else if (t.equals("4")) {
			sql = "select * from t_drug_class t where  t.ifljb = 2 and t.cbm like 'z%'";
		} else if (t.equals("5")) {
			sql = "select * from t_drug_class t where (t.dfl = 3 or t.dfl = 4) and t.ifljb = 2 and t.cbm not like 'z%'";
		}
		List<DiseaseClass> list = this.executeNativeQuery(sql, null,
				DiseaseClass.class);

		return list;
	}

	public List<DiseaseClass> queryOneMenuAll(int length) {
		String hql = "from DiseaseClass d where 1=1 and LENGTH(d.cbm)=? ";
		Object args[] = { length };
		return this.executeFind(hql, args);
	}
}
