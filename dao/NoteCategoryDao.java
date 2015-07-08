package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.NoteCategory;

public interface NoteCategoryDao extends BaseDao<NoteCategory>{

	/**
	 * 根据对象属性查询所有数据
	 */
	public List<NoteCategory> queryList(NoteCategory noteCategory);
	
	/**根据名称查询是否已存在**/
	public int queryNoteByName(NoteCategory noteCategory);
	
	public int updateNoteCategory(NoteCategory nc);
}
