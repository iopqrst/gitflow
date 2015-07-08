package com.bskcare.ch.service.impl;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.TeacherDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.ProductLevelService;
import com.bskcare.ch.service.TeacherService;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.vo.ProductLevel;
import com.bskcare.ch.vo.Teacher;

@Service
@SuppressWarnings("unchecked")
public class TeacherServiceImpl implements TeacherService {
	@Autowired
	private TeacherDao teacherDao;
	@Autowired
	private ProductLevelService plService;

	@Resource
	public void setTeacherDao(TeacherDao teacherDao) {
		this.teacherDao = teacherDao;
	}

	public void add(Teacher t) {
		teacherDao.add(t);
	}

	public void update(Teacher t) {
		teacherDao.update(t);
	}

	public void delete(int id) {
		teacherDao.delete(id);
	}

	public Teacher load(int id) {
		return teacherDao.load(id);
	}

	public List<Teacher> executeFind(Teacher t) {
		return teacherDao.executeFind(t);
	}
	
	public PageObject<Teacher> queryObjects(Teacher t, QueryInfo info) {
		return teacherDao.queryObjects(t,info);
	}
	
	public String queryStringObjects(Teacher t, QueryInfo info) {
		PageObject<Teacher> po = teacherDao.queryObjects(t,info);
		List<Teacher> list = po.getDatas();
		
		JSONObject jo = new JSONObject();
		jo.put("total", po.getTotalRecord());
		jo.put("teacherList", JsonUtils.getJsonString4JavaList(list));
		
		System.out.println("------------------" + jo.toString());
		return jo.toString();
	}
	

	/**
	 * 事务测试，其中删除操作存在错误，整个事务无法成功提交
	 */
	public void testTransaction() {

		Teacher t = new Teacher();
		t.setName("name411111后铭刻");
		t.setAge("23");
		t.setNickname("nickname3");
		
		ProductLevel pl = new ProductLevel();
		pl.setCreateTime(new Date());
		pl.setName("1234");
		
		plService.add(pl);

		teacherDao.add(t);

		Teacher t2 = teacherDao.load(1);
		t2.setAge("32");

		teacherDao.update(t2);

		teacherDao.delete(8);

	}
	
	public void testTransaction2() {
//		public void sadd(Integer id);
//		public void supdate(Integer id);
//		public void sdelete(Integer id);
		
		teacherDao.sadd(null);
		teacherDao.supdate(1);
		teacherDao.sdelete(1000);
	}
	
	public void testTransaction3() {
		teacherDao.strans(1);
	}
	
	

	
	public void updateBysql(Teacher t) {
		String hql = "update Teacher set name = ? , age = ? where id = ?";
		Object[] args = new Object[]{t.getName(),t.getAge(),t.getId()};
		teacherDao.updateByHql(hql, args);
	}
	
	public void schemaTable() {
		teacherDao.schemaTable();
	}

}
