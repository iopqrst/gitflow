package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.TeacherDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.Teacher;

@Repository("teacherDao")
@SuppressWarnings("unchecked")
public class TeacherDaoImpl extends BaseDaoImpl<Teacher> implements TeacherDao {
	
	public List<Teacher> executeFind(Teacher t) {
		String hql = "from Teacher t where 1 = 1 ";
		ArrayList array = new ArrayList();
		if(null != t) {
			if(!StringUtils.isEmpty(t.getName())) {
				hql += " and t.name like ?";
				array.add("%"+ t.getName().trim() +"%");
			}
			if(!StringUtils.isEmpty(t.getNickname())) {
				hql += " and t.nickname like ?";
				array.add("%"+ t.getNickname().trim() +"%");
			}
		}
		List<Teacher> list = executeFind(hql, array.toArray());
		return list;
	}
	
	public PageObject<Teacher> queryObjects(Teacher t, QueryInfo info) {
		String hql = "from Teacher t where 1 = 1 ";
		ArrayList args = new ArrayList();
		if(null != t) {
			if(!StringUtils.isEmpty(t.getName())) {
				hql += " and t.name like ?";
				args.add("%"+ t.getName().trim() +"%");
			}
			if(!StringUtils.isEmpty(t.getNickname())) {
				hql += " and t.nickname like ?";
				args.add("%"+ t.getNickname().trim() +"%");
			}
		}
		
		return queryObjects(hql, args.toArray(), info);
	}
	
	public void sadd(Integer id){
		String sql = "insert into t_teacher (age,name,nickname) values(23,'hou','hello kity!')" ;
		this.updateBySql(sql);
	}
	
	public void supdate(Integer id){
		String sql = "update t_teacher set name = 'monkey-d-' where id = " + id;
		this.updateBySql(sql);
	}
	
	public void sdelete(Integer id){
		String sql = "delete from t_teacher where sid = " + id ;
		this.updateBySql(sql);
	}
	
	public void strans(Integer id) {
		String sql3 = "create table t_teacher_bak as select * from t_teacher"; 
		String sql0 = "insert into t_teacher (age,name,nickname) values(23,'hou','hello kity!')" ;
		String sql1 = "update t_teacher set name = 'monkey-d-' where id = " + id;
		String sql2 = "delete from t_teacher where sid = " + id ;
		this.updateBySql(sql3);
		this.updateBySql(sql0);
		this.updateBySql(sql1);
		this.updateBySql(sql2);
	}
	
	public void schemaTable() {
		String sql = "select count(*) from information_schema.COLUMNS t where table_schema = 'bskcare'";
		Object obj = this.findUniqueResultByNativeQuery(sql);
		System.out.println(obj);
	}
}
