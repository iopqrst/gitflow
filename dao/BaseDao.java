package com.bskcare.ch.base.dao;

import java.util.List;
import java.util.Map;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;

@SuppressWarnings("unchecked")
public interface BaseDao<T> {

	public T add(T t);

	public void update(T t);

	public int updateByHql(String hql);
	
	public int updateByHql(String hql, Object arg);
 
	public int updateByHql(String hql, Object[] args);

	public int updateBySql(String sql);

	public int updateBySql(String sql, Object arg);

	public int updateBySql(String sql, Object[] args);

	public void delete(T t);

	public void delete(int id);

	public int deleteBySql(String sql);

	public int deleteBySql(String sql, Object arg);

	public int deleteBySql(String sql, Object[] args);

	/**
	 * 查询单个对象
	 */
	public T load(int id);

	/**
	 * 查询单个值对象
	 */
	public Object findUniqueResult(String hql);

	public Object findUniqueResult(String hql, Object arg);

	public Object findUniqueResult(String hql, Object[] args);

	public Object findUniqueResultByNativeQuery(String sql);

	public Object findUniqueResultByNativeQuery(String sql, Object arg);

	public Object findUniqueResultByNativeQuery(String sql, Object[] args);

	public Object findUniqueResultByNativeQuery(final String sql, Map entities,
			Map scalars, final Object[] args);

	public List<T> executeFind(String hql);

	public List<T> executeFind(String hql, Object arg);

	public List<T> executeFind(String hql, Object[] args);
	
	public List<T> executeFind(String hql, Object[] args, int limit);
	
	/**
	 * 分页查询
	 * <p>1. 与queryObjects相同</p>
	 * <p>2. 针对非对象，可以返回一个数组</p>
	 */
	public PageObject queryPagerObjects(String hql, Object[] args, QueryInfo queryInfo);
	
	public PageObject<T> queryObjects(String hql, Object[] args, QueryInfo queryInfo);

	/**
	 * 原生SQL查询
	 */
	public List executeNativeQuery(String sql);

	public List executeNativeQuery(String sql, Object arg);

	public List executeNativeQuery(String sql, Object[] args);

	public List executeNativeQuery(String sql, Map entities, Object[] args);
	
	public List executeNativeQuery(String sql, Object[] args, Class aliasBean);

	public List executeNativeQuery(String sql, Map entities, Map scalars,
			Object[] args, Class aliasBean);
	
	/**
	 * 原生SQL分页查询
	 */
	public PageObject queryObjectsBySql(String sql, QueryInfo queryInfo);
	
	public PageObject queryObjectsBySql(String sql, Object arg, QueryInfo queryInfo);
	
	public PageObject queryObjectsBySql(String sql, Object[] args, QueryInfo queryInfo);
	
	public PageObject queryObjectsBySql(String sql, Object[] args, Map entities, QueryInfo queryInfo);
	
	public PageObject queryObjectsBySql(String sql, Map entities, Map scalars,
			Object[] args, QueryInfo queryInfo, Class aliasBean);

	
}
