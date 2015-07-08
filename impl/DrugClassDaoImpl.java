package com.bskcare.ch.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.DrugClassDao;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.search.DrugClass;
@Repository
public class DrugClassDaoImpl extends BaseDaoImpl<DrugClass> implements DrugClassDao {
         public List<DrugClass> queryAll(){
        	 
        	 String hql="from DrugClass";
        	 return this.executeFind(hql);
         }

		public List<DrugClass> queryByCbm(String cbm) {
			String hql="from DrugClass where 1=1 and cbm like '%"+cbm+"%'";
			
			return this.executeFind(hql);
		}
		public List<DrugClass> queryByOne(String cbm) {
			String hql="from DrugClass where 1=1 and cbm like '"+cbm+"'";
			
			return this.executeFind(hql);
		}

		public List<DrugClass> queryMenuByT(String t) {
			if(!StringUtils.isEmpty(t)){
				String sql = null ;
				if(t.equals("1")){
					sql = "select * from t_drug_class t where t.dfl = 1 and t.ifljb = 2" ;
				}else if(t.equals("2")){
					sql = "select * from t_drug_class t where t.dfl = 2 and t.ifljb = 2 AND t.cbm like 'c%'" ;
				}else if(t.equals("3")){
					sql = "select * from t_drug_class t where t.dfl = 5 and t.ifljb = 2 " ;
				}else if(t.equals("4")){
					sql = "select * from t_drug_class t where  t.ifljb = 2 and t.cbm like 'z%'" ;
				}else if(t.equals("5")){
					sql = "select * from t_drug_class t where (t.dfl = 3 or t.dfl = 4) and t.ifljb = 2 and t.cbm not like 'z%'" ;
				}
				List<DrugClass> list = this.executeNativeQuery(sql,null,DrugClass.class) ;
				
				return list;
			}
			return null;
		}
}
