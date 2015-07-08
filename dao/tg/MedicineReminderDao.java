package com.bskcare.ch.dao.tg;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.tg.MedicineReminder;

public interface MedicineReminderDao extends BaseDao<MedicineReminder> {

	/** 删除以前用户数据 **/
	public void delete(Integer cid);
	/**
	 * 查询所有用户所有的服药提醒，添加服药提醒任务
	 * @return
	 */
	public List<MedicineReminder> getMedicineReminders(Integer cid);
	
	/**
	 * 查询某个用户最后一个时间点上传的数据
	 */
	public List<MedicineReminder> queryLastRecord(Integer cid);
	
	/**
	 * 根据用户id 和 服药提醒 id 删除
	 */
	//public int deleteById(Integer cid, Integer rmId);
}
