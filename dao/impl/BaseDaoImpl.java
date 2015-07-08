package com.bskcare.ch.base.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.StringUtils;

@Repository("baseDao")
@SuppressWarnings("unchecked")
public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {

	private Class<T> clz;

	private Class<T> getClz() {
		if (clz == null)
			clz = (Class<T>) ((ParameterizedType) this.getClass()
					.getGenericSuperclass()).getActualTypeArguments()[0];
		return clz;
	}

	@Resource
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public T add(T t) {
		getSession().setFlushMode(FlushMode.AUTO);
		this.getHibernateTemplate().save(t);
		getSession().flush();
		return t;
	}

	public void delete(T t) {
		getSession().setFlushMode(FlushMode.AUTO);
		this.getHibernateTemplate().delete(t);
		getSession().flush();
	}

	public void delete(int id) {
		delete(load(id));
	}

	public int deleteBySql(String sql) {
		return deleteBySql(sql, null);
	}

	public int deleteBySql(String sql, Object arg) {
		return deleteBySql(sql, new Object[] { arg });
	}

	public int deleteBySql(String sql, Object[] args) {
		getSession().setFlushMode(FlushMode.AUTO);
		Query query = this.getSession().createSQLQuery(sql);

		if (null != args && args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i, args[i]);
			}
		}

		int count = query.executeUpdate();
		getSession().flush();
		return count;
	}

	public List<T> executeFind(String hql) {
		return executeFind(hql, null);
	}

	public List<T> executeFind(String hql, Object arg) {
		return executeFind(hql, new Object[] { arg });
	}
	
	public List<T> executeFind(String hql, Object[] args) {
		return executeFind(hql, args, 0) ;
	}

	public List<T> executeFind(String hql, Object[] args, int limit) {
		//long start = System.currentTimeMillis();
		Query query = this.getSession().createQuery(hql);
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i, args[i]);
			}
		}
		
		if(limit > 0) {
			query.setFirstResult(0).setMaxResults(limit);
		}
		
		List<T> list = query.list();
		//long end = System.currentTimeMillis();
		//System.out.println("--------------------本次执行共耗时：" + (end - start) );
		return list;
	}

	public PageObject queryPagerObjects(String hql, Object[] args, QueryInfo queryInfo) {
		
		//long start =  System.currentTimeMillis();
		
		PageObject<Object> pages = new PageObject<Object>();

		String countHql = getCountHql(hql);
		
		if(null != queryInfo && !StringUtils.isEmpty(queryInfo.getSort()) 
				&& !"".equals(queryInfo.getSort().trim())) {
			
			hql+=" order by "+ queryInfo.getSort() ;
			if(!StringUtils.isEmpty(queryInfo.getOrder()) 
					&& !"".equals(queryInfo.getOrder())) {
				hql+=" "+ queryInfo.getOrder();
			} else {
				hql+=" " + QueryInfo.ORDER_DESC;
			}
			
		}
		
		Query query = getSession().createQuery(hql);
		Query countQuery = getSession().createQuery(countHql);
		
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i, args[i]);
				countQuery.setParameter(i, args[i]);
			}
		}
		
		if(null != queryInfo && null != queryInfo.getPageOffset() 
				&& null != queryInfo.getPageSize()) {
			query.setFirstResult(queryInfo.getPageOffset())
			 .setMaxResults(queryInfo.getPageSize());
			
			pages.setOffset(queryInfo.getPageOffset());
			pages.setPageSize(queryInfo.getPageSize());
		}
		
		List<Object> datas = query.list();
		
		pages.setDatas(datas);
		Object obj = countQuery.uniqueResult();
		
		int totalRecord = 0;
		if(null != obj) {
			totalRecord = Integer.parseInt(obj+"");
		}
		
		//long end = System.currentTimeMillis();
		//System.out.println("--------------------本次执行共耗时：" + (end - start) );
		//System.out.println("totalRecord =" + totalRecord);
		pages.setTotalRecord(totalRecord);
		return pages;
	}
	
	public PageObject<T> queryObjects(String hql, Object[] args, QueryInfo queryInfo) {
		
		//long start = System.currentTimeMillis();
		
		PageObject<T> pages = new PageObject<T>();

		String countHql = getCountHql(hql);
		
		if(null != queryInfo && !StringUtils.isEmpty(queryInfo.getSort()) 
				&& !"".equals(queryInfo.getSort().trim())) {
			
			hql+=" order by "+ queryInfo.getSort() ;
			if(!StringUtils.isEmpty(queryInfo.getOrder()) 
					&& !"".equals(queryInfo.getOrder())) {
				hql+=" "+ queryInfo.getOrder();
			} else {
				hql+=" " + QueryInfo.ORDER_DESC;
			}
			
		}
		
		Query query = getSession().createQuery(hql);
		Query countQuery = getSession().createQuery(countHql);
		
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i, args[i]);
				countQuery.setParameter(i, args[i]);
			}
		}
		
		if(null != queryInfo && null != queryInfo.getPageOffset() 
				&& null != queryInfo.getPageSize()) {
			query.setFirstResult(queryInfo.getPageOffset())
			 .setMaxResults(queryInfo.getPageSize());
			
			pages.setOffset(queryInfo.getPageOffset());
			pages.setPageSize(queryInfo.getPageSize());
		}
		
		List<T> datas = query.list();
		pages.setDatas(datas);
		Object obj = countQuery.uniqueResult();
		
		int totalRecord = 0;
		if(null != obj) {
			totalRecord = Integer.parseInt(obj+"");
		}
		
		//System.out.println("totalRecord =" + totalRecord);
		pages.setTotalRecord(totalRecord);
		//long end = System.currentTimeMillis();
		//System.out.println("--------------------本次执行共耗时：" + (end - start) );
		return pages;
	}
	
	private String getCountHql(String hql) {
		String str = hql.substring(hql.indexOf("from "));
		str = "select count(*) "+str;
//		str = str.replaceAll("fetch","");
		return str;
	}

	public List executeNativeQuery(String sql) {
		return executeNativeQuery(sql, null, null, null, null);
	}

	public List executeNativeQuery(String sql, Object arg) {
		return executeNativeQuery(sql, null, null, new Object[] { arg }, null);
	}

	public List executeNativeQuery(String sql, Object[] args) {
		return executeNativeQuery(sql, null, null, args, null);
	}

	public List executeNativeQuery(String sql, Map entities, Object[] args) {
		return executeNativeQuery(sql, entities, null, args, null);
	}
	
	public List executeNativeQuery(String sql, Object[] args, Class aliasBean) {
		return executeNativeQuery(sql, null, null, args, aliasBean);
	}

	public List executeNativeQuery(String sql, Map entities, Map scalars,
			Object[] args, Class aliasBean) {
		//long start = System.currentTimeMillis();
		
		SQLQuery query = getSession().createSQLQuery(sql);
		if (entities != null && !entities.isEmpty()) {
			Iterator it = entities.keySet().iterator();
			while (it.hasNext()) {
				String columnAlias = (String) it.next();
				Class clazz = (Class) entities.get(columnAlias);
				query.addEntity(columnAlias, clazz);
			}
		}

		// 该部分在调用时如果采用new HashMap可能会引起顺序颠倒，所以要采用LinkedHashMap
		if (scalars != null && !scalars.isEmpty()) {
			Iterator it = scalars.keySet().iterator();
			while (it.hasNext()) {
				String columnAlias = (String) it.next();
				Type type = (Type) scalars.get(columnAlias);
				query.addScalar(columnAlias, type);
			}
		}

		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i, args[i]);
			}
		}
		
		if(null != aliasBean) {
			query.setResultTransformer(Transformers
		          .aliasToBean(aliasBean));	
		}

		List list = new ArrayList();
		list = query.list();
		
		//long end = System.currentTimeMillis();
		//System.out.println("--------------------本次执行共耗时：" + (end - start) );
		
		return list;
	}

	public Object findUniqueResult(String hql) {
		return findUniqueResult(hql,null);
	}

	public Object findUniqueResult(String hql, Object arg) {
		return findUniqueResult(hql, new Object[] { arg });
	}

	public Object findUniqueResult(String hql, Object[] args) {
		Object t = null;
		Query query = this.getSession().createQuery(hql);

		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i, args[i]);
			}
		}
		t = (Object) query.uniqueResult();
		return t;
	}

	public Object findUniqueResultByNativeQuery(String sql) {
		return findUniqueResultByNativeQuery(sql, null, null, null);
	}

	public Object findUniqueResultByNativeQuery(String sql, Object arg) {
		return findUniqueResultByNativeQuery(sql, null, null,
				new Object[] { arg });
	}

	public Object findUniqueResultByNativeQuery(String sql, Object[] args) {
		return findUniqueResultByNativeQuery(sql, null, null, args);
	}

	public Object findUniqueResultByNativeQuery(String sql, Map entities,
			Map scalars, Object[] args) {
		SQLQuery query = getSession().createSQLQuery(sql);

		if (entities != null && !entities.isEmpty()) {
			Iterator it = entities.keySet().iterator();
			while (it.hasNext()) {
				String columnAlias = (String) it.next();
				Class clazz = (Class) entities.get(columnAlias);
				query.addEntity(columnAlias, clazz);
			}
		}
		// 该部分在调用时如果采用new HashMap可能会引起顺序颠倒，所以要采用LinkedHashMap
		if (scalars != null && !scalars.isEmpty()) {
			Iterator it = scalars.keySet().iterator();
			while (it.hasNext()) {
				String columnAlias = (String) it.next();
				Type type = (Type) scalars.get(columnAlias);
				query.addScalar(columnAlias, type);
			}
		}

		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i, args[i]);
			}
		}

		Object object = query.uniqueResult();
		return object;
	}
	
	public PageObject queryObjectsBySql(String sql, QueryInfo queryInfo){
		return queryObjectsBySql(sql, null, null, null,queryInfo, null);
	}
	
	public PageObject queryObjectsBySql(String sql, Object arg, QueryInfo queryInfo){
		return queryObjectsBySql(sql, null, null, new Object[]{arg},queryInfo, null);
	}
	
	public PageObject queryObjectsBySql(String sql, Object[] args, QueryInfo queryInfo){
		return queryObjectsBySql(sql,null,null,args,queryInfo, null);
	}
	
	public PageObject queryObjectsBySql(String sql, Object[] args, Map entities, QueryInfo queryInfo){
		return queryObjectsBySql(sql,entities,null,args,queryInfo, null);
	}
	
	public PageObject queryObjectsBySql(String sql, Map entities, Map scalars,
			Object[] args, QueryInfo queryInfo, Class aliasBean) {
		
		//long start = System.currentTimeMillis();
		
		PageObject pages = new PageObject<T>();

		String countSql = getCountSql(sql);
		
		if(null != queryInfo && !StringUtils.isEmpty(queryInfo.getSort()) 
				&& !"".equals(queryInfo.getSort().trim())) {
			
			sql+=" order by "+ queryInfo.getSort() ;
			if(!StringUtils.isEmpty(queryInfo.getOrder()) 
					&& !"".equals(queryInfo.getOrder())) {
				sql+=" "+ queryInfo.getOrder();
			} else {
				sql+=" " + QueryInfo.ORDER_DESC;
			}
			
		}
		
		SQLQuery query = getSession().createSQLQuery(sql);
		SQLQuery countQuery = getSession().createSQLQuery(countSql);
		
		if (entities != null && !entities.isEmpty()) {
			Iterator it = entities.keySet().iterator();
			while (it.hasNext()) {
				String columnAlias = (String) it.next();
				Class clazz = (Class) entities.get(columnAlias);
				query.addEntity(columnAlias, clazz);
			}
		}
		// 该部分在调用时如果采用new HashMap可能会引起顺序颠倒，所以要采用LinkedHashMap
		if (scalars != null && !scalars.isEmpty()) {
			Iterator it = scalars.keySet().iterator();
			while (it.hasNext()) {
				String columnAlias = (String) it.next();
				Type type = (Type) scalars.get(columnAlias);
				query.addScalar(columnAlias, type);
			}
		}

		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i, args[i]);
				countQuery.setParameter(i, args[i]);
			}
		}

		if(null != queryInfo && null != queryInfo.getPageOffset() 
				&& null != queryInfo.getPageSize()) {
			query.setFirstResult(queryInfo.getPageOffset())
			 .setMaxResults(queryInfo.getPageSize());
			
			pages.setOffset(queryInfo.getPageOffset());
			pages.setPageSize(queryInfo.getPageSize());
		}
		
		if(null != aliasBean) {
			query.setResultTransformer(Transformers
		          .aliasToBean(aliasBean));	
		}
		
		List<T> datas = query.list();
		pages.setDatas(datas);
		Object obj = countQuery.uniqueResult();
		int totalRecord = 0;
		if(null != obj) {
			totalRecord = Integer.parseInt(obj+"");
		}
//		System.out.println("sql totalRecord =" + totalRecord);
		pages.setTotalRecord(totalRecord);
		//long end = System.currentTimeMillis();
		//System.out.println("--------------------本次执行共耗时：" + (end - start) );
		return pages;
		
	}

	private String getCountSql(String sql) {
		if(!StringUtils.isEmpty(sql)) {
			sql = sql.replace("FROM", "from");
			int first = sql.indexOf("from");
			String sqlSuffix = sql.substring(first);
			sql = "select count(1) a from (select 1 " + sqlSuffix + ") t"; 
		}
		return sql;
	}

	public T load(int id) {
		return this.getHibernateTemplate().get(getClz(), id);
	}

	public void update(T t) {
		getSession().setFlushMode(FlushMode.AUTO);
		this.getHibernateTemplate().update(t);
		getSession().flush();
	}

	public int updateByHql(String hql) {
		return updateByHql(hql,null);
	}

	public int updateByHql(String hql, Object arg) {
		return updateByHql(hql, new Object[] { arg });
	}

	public int updateByHql(String hql, Object[] args) {
		getSession().setFlushMode(FlushMode.AUTO);
		Query query = this.getSession().createQuery(hql);
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i, args[i]);
			}
		}
		int count = query.executeUpdate();
		getSession().flush();
		return count;
	}

	public int updateBySql(String sql) {
		return updateBySql(sql, null);
	}

	public int updateBySql(String sql, Object arg) {
		return updateBySql(sql, new Object[] { arg });
	}

	public int updateBySql(String sql, Object[] args) {
		getSession().setFlushMode(FlushMode.AUTO);
		SQLQuery query = getSession().createSQLQuery(sql);
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i, args[i]);
			}
		}
		int count = query.executeUpdate();
		getSession().flush();
		return count;
	}

}
