package com.bskcare.ch.service.impl.tg;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.tg.MedicineReminderDao;
import com.bskcare.ch.service.score.ScoreRecordService;
import com.bskcare.ch.service.tg.MedicineReminderService;
import com.bskcare.ch.service.timeLine.TimeLineTaskService;
import com.bskcare.ch.vo.tg.MedicineReminder;

@Service
public class MedicineReminderServiceImpl implements MedicineReminderService {

	@Autowired
	private MedicineReminderDao reminderDao;
	@Autowired
	private TimeLineTaskService taskService;
	@Autowired
	private ScoreRecordService scoreService;
	
	public String add(List<MedicineReminder> list, Integer cid) {
		JSONObject jo = new JSONObject();
		// 删除以前用户服药提醒数据
		reminderDao.delete(cid);
		// 按提醒时间分类用户上传的服药提醒
		List<MedicineReminder> remlist = new ArrayList<MedicineReminder>();
		boolean bool = true;
		for (int i = 0; i < list.size(); i++) {
			if (i == 0) {
				remlist.add(list.get(i));
				continue;
			}
			for (int j = 0; j < remlist.size(); j++) {
				if (list.get(i).getAlertTime().equals(
						remlist.get(j).getAlertTime())) {
					remlist.get(j).setDrugName(
							remlist.get(j).getDrugName() + "，"
									+ list.get(i).getDrugName());
					bool = false;
				}
			}
			if (bool) {
				remlist.add(list.get(i));
			}
			bool = true;
		}

		taskService.updateClienttMedicineReminder(cid, remlist);
		if (!CollectionUtils.isEmpty(remlist)) {
			// 新增用户服药提醒数据
			for (MedicineReminder mr : remlist) {
				mr.setClientId(cid);
				MedicineReminder mrobj = reminderDao.add(mr);
				if (null != mrobj) {
					jo.put("msg", "新增服药提醒成功");
					jo.put("code", Constant.INTERFACE_SUCC);
				} else {
					jo.put("msg", "新增服药提醒失败");
					jo.put("code", Constant.INTERFACE_FAIL);
				}
			}
		} else {
			jo.put("code", Constant.INTERFACE_SUCC);
			jo.put("msg", "清空所有服药提醒");
		}
		jo.put("data", "");
		return jo.toString();
	}

	public void delete(Integer cid) {
		reminderDao.delete(cid);
	}

	public List<MedicineReminder> queryRemindersByCId(Integer cid) {
		return reminderDao.getMedicineReminders(cid);
	}
	
//	public String deleteById(Integer cid, Integer rmId) {
//		
//		JSONObject jo = new JSONObject();
//		if(null == cid || null == rmId) {
//			jo.accumulate("code", Constant.INTERFACE_PARAM_ERROR);
//			jo.accumulate("msg", "参数信息不正确");
//		} else {
//			int count = reminderDao.deleteById(cid, rmId);
//			if(count > 0) {
//				jo.accumulate("code", Constant.INTERFACE_SUCC);
//				jo.accumulate("msg", "删除成功");
//			} else {
//				jo.accumulate("code", Constant.INTERFACE_FAIL);
//				jo.accumulate("msg", "删除失败");
//			}
//		}
//		jo.accumulate("data", "");
//		
//		return jo.toString();
//	}
//
//	@Deprecated
//	public String newAdd(List<MedicineReminder> list, Integer cid) {
//		JSONObject jo = new JSONObject();
//		
//		if(!CollectionUtils.isEmpty(list)) {
//			Map<String, MedicineReminder> map = new HashMap<String, MedicineReminder>();
//			
//			for(MedicineReminder mr : list) {
//				MedicineReminder mrExist = map.get(mr.getAlertTime());
//				if(null != mrExist) {//存在，重写
//					mrExist.setDrugName(mrExist.getDrugName() + "，" + mr.getDrugName());
//					mrExist.setReminder(mr.getReminder()); //覆盖之前的铃声
//					
//					map.put(mr.getAlertTime(), mrExist);
//				} else { //不存在添加
//					map.put(mr.getAlertTime(), mr);
//				}
//			}
//			
//			//数据库中存在的服药提醒
//			List<MedicineReminder> remlist = reminderDao.getMedicineReminders(cid);
//			
//			Set<String> set = map.keySet();
//			
//			for (Iterator iter = set.iterator(); iter.hasNext();) {
//				String key = (String) iter.next();
//				
//				// list 存在map 的key 覆盖之前
//				MedicineReminder ermFormMap = map.get(key); //数据库中是否存在map中的值
//
//				boolean needCreate = true;
//				for(MedicineReminder dbExistErm : remlist) {
//					if(key.equals(dbExistErm.getAlertTime())) {
//						
//						dbExistErm.setDrugName(dbExistErm.getDrugName() + "，" + ermFormMap.getDrugName());
//						dbExistErm.setReminder(ermFormMap.getReminder());
//						
//						reminderDao.update(dbExistErm);
//						needCreate = false;
//						break;
//					}
//				}
//				
//				if(needCreate) {
//					ermFormMap.setClientId(cid);
//					reminderDao.add(ermFormMap);
//					//jo.put("data", "{id:"+  +"}");
//				}
//			}
//		} else {
//			jo.put("code", Constant.INTERFACE_PARAM_ERROR);
//			jo.put("msg", "参数信息不正确");
//			return jo.toString();
//		}
//		
//		List<MedicineReminder> newRecords = reminderDao.getMedicineReminders(cid);
//		taskService.updateClienttMedicineReminder(cid, newRecords); //更新时间轴上的服药提醒
//		
//		jo.put("code", Constant.INTERFACE_SUCC);
//		jo.put("msg", "服药提醒添加成功");
//		jo.put("data", "");
//		
//		return jo.toString();
//	}
//	
//	@Deprecated
//	public String update(MedicineReminder reminder) {
//		JSONObject jo = new JSONObject();
//		if(null != reminder && null != reminder.getId() && null != reminder.getClientId()) {
//			
//			//数据库中存在的服药提醒
//			List<MedicineReminder> dblist = reminderDao.getMedicineReminders(reminder.getClientId());
//			
//			boolean hadUpdate = false; //是否已修改
//			//循环查看是否存在相同时间节点
//			for(MedicineReminder mr : dblist) {
//				//除自身之外的记录
//				if(!mr.getId().equals(reminder.getId())) {
//					if(mr.getAlertTime().equals(reminder.getAlertTime())) { //修改后的时间与其他相同了
//						//1. 删除现在的
//						reminderDao.deleteById(reminder.getClientId(), reminder.getId());
//						//2. 把新的覆盖到与之相同时间节点的提醒上
//						mr.setDrugName(mr.getDrugName() + "，" + reminder.getDrugName());
//						mr.setReminder(reminder.getReminder());
//						
//						reminderDao.update(mr);
//						hadUpdate = true;
//						break;
//					}
//				}
//			}
//
//			if(!hadUpdate) { 
//				//与其他记录时间不相同，直接修改
//				MedicineReminder mrdb = reminderDao.load(reminder.getId());
//				mrdb.setDrugName(reminder.getDrugName());
//				mrdb.setReminder(reminder.getReminder());
//				mrdb.setAlertTime(reminder.getAlertTime());
//				
//				reminderDao.update(mrdb);
//			}
//			
//			jo.accumulate("code", Constant.INTERFACE_SUCC);
//			jo.accumulate("msg", "服药提醒修改成功");
//		} else {
//			jo.accumulate("code", Constant.INTERFACE_PARAM_ERROR);
//			jo.accumulate("msg", "参数信息不正确");
//		}
//		jo.accumulate("data", "");
//		return jo.toString();
//	}

}
