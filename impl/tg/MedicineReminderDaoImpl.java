package com.bskcare.ch.dao.impl.tg;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.tg.MedicineReminderDao;
import com.bskcare.ch.vo.tg.MedicineReminder;

@Repository
public class MedicineReminderDaoImpl extends BaseDaoImpl<MedicineReminder>
		implements MedicineReminderDao {

	public void delete(Integer cid) {
		String sql = "delete from tg_medicine_reminder where clientId=?";
		deleteBySql(sql, cid);
	}

	public int deleteById(Integer cid, Integer rmId) {
		String sql = "delete from tg_medicine_reminder where clientId=? and id = ?";
		return deleteBySql(sql, new Object[] { cid, rmId });
	}

	public List<MedicineReminder> getMedicineReminders(Integer cid) {
		String hql = " from MedicineReminder where 1 = 1";
		Object[] args = null;
		if (null != cid) {
			hql += " and clientId = ?";
			args = new Object[] { cid };
		}
		hql += " order by alertTime";
		return executeFind(hql, args);
	}

	public List<MedicineReminder> queryLastRecord(Integer cid) {
		String hql = " from MedicineReminder where clientId = ? order by alertTime";
		return executeFind(hql, cid);
	}
}
