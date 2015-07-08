package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.NoteRepo;

public interface NoteRepoDao extends BaseDao<NoteRepo>{

	/**
	 * 获取当前已存在关键字
	 */
	public List<String> queryTag();
	
	/**
	 * 根据对象属性查询所有数据
	 */
	public PageObject<NoteRepo> queryList(NoteRepo noteRepo,QueryInfo queryInfo);
	
	/**
	 * 短信群发
	 */
	public List<NoteRepo> queryMassTextList(NoteRepo noteRepo);
}
