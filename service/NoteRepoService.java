package com.bskcare.ch.service;

import java.util.List;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.NoteRepo;

public interface NoteRepoService {

	/**
	 * 获取已存在关键字
	 */
	public String queryTag();
	
	/**
	 * 保存
	 */
	public NoteRepo add(NoteRepo noteRepo);
	
	/**
	 * 根据对象属性查询所有数据
	 */
	public PageObject<NoteRepo> queryList(NoteRepo noteRepo,QueryInfo queryInfo);
	
	/**
	 * 删除
	 */
	public void delete(Integer id);
	
	/**
	 * 根据id查询对象
	 */
	public NoteRepo findById(Integer id);
	
	/**
	 * 修改
	 */
	public void update(NoteRepo noteRepo);
	
	/**
	 * 短信群发
	 */
	public List<NoteRepo> queryMassTextList(NoteRepo noteRepo);
}
