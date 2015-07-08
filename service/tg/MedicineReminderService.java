package com.bskcare.ch.service.tg;

import java.util.List;

import com.bskcare.ch.vo.tg.MedicineReminder;

public interface MedicineReminderService {

	/** 
	 * 上传服药提醒
	 * 该操作会先清空所有，然后在添加 
	 */
	public String add(List<MedicineReminder> list,Integer cid);
	
	/**
	 * 新上传服药提醒，不会删除原来，且会将相同时间节点的提醒添加到一个之中
	 */
//	@Deprecated
//	public String newAdd(List<MedicineReminder> list,Integer cid);
	/** 删除以前服药提醒数据 **/
	public void delete(Integer cid);
	
	public List<MedicineReminder> queryRemindersByCId(Integer cid);
	
	/**
	 * 根据用户id 和 服药提醒 id 删除
	 */
//	public String deleteById(Integer cid, Integer rmId);
	
	/**
	 * 修改服药提醒记录
	 * 	如果修改的时间节点已经存在数据库中则会删除当前修改，并把相应的值赋值在相同要修改的节点上
	 */
//	@Deprecated
//	public String update(MedicineReminder reminder);
}
