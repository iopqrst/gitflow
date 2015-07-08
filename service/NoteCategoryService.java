package com.bskcare.ch.service;

import com.bskcare.ch.vo.NoteCategory;

public interface NoteCategoryService {

	/**
	 * 根据对象查询
	 */
	public String queryList(NoteCategory noteCategory);
	
	public String queryListForZtree();
	
	/**添加短信库内容**/
	public String addNodeCategory(NoteCategory nc);
	
	/**删除短信库内容**/
	public String deleteNodeCategory(NoteCategory nc);
	
	/**根据名称查询**/
	public String queryNoteByName(NoteCategory noteCategory);
	
	public String updateNoteCategory(NoteCategory nc);
		
	
}
