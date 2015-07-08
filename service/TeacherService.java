package com.bskcare.ch.service;

import java.util.List;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.Teacher;

public interface TeacherService {

	public void add(Teacher u);

	public void update(Teacher u);

	public void delete(int id);

	public Teacher load(int id);

	public List<Teacher> executeFind(Teacher t);
	
	public PageObject<Teacher> queryObjects(Teacher t, QueryInfo queryInfo);
	
	public String queryStringObjects(Teacher t, QueryInfo queryInfo);
	
	/**
	 *  事务测试
	 */
	public void testTransaction();
	public void testTransaction2();
	public void testTransaction3();
	
	public void updateBysql(Teacher t) ;
	
	public void schemaTable() ;
}
