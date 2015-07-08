package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.Teacher;

/**
 * 
 * @author Administrator
 * 
 */
public interface TeacherDao extends BaseDao<Teacher>{
	public List<Teacher> executeFind(Teacher t);
	public PageObject<Teacher> queryObjects(Teacher t, QueryInfo info);
	public void sadd(Integer id);
	public void supdate(Integer id);
	public void sdelete(Integer id);
	public void strans(Integer id);
	
	public void schemaTable();
}
