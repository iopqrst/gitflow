package com.bskcare.ch.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.bskcare.ch.bo.ManageLogExtend;
import com.bskcare.ch.dao.ManageLogDao;
import com.bskcare.ch.service.ManageLogService;
import com.bskcare.ch.vo.ManageLog;

@Service
@SuppressWarnings("unchecked")
public class ManageLogServiceImpl implements ManageLogService{
	
	@Autowired
	private ManageLogDao logInfoDao;
	
	public void addLog(ManageLog mlog) {
		logInfoDao.addLog(mlog);
	}

	public List queryLogByClientId(Integer clientId){
		if(clientId != null){
			return logInfoDao.queryLogByClientId(clientId);
		}
		return null;
	}
	
	public String ajaxQueryLogByClientId(ManageLog log){
		JSONObject json = new JSONObject();
		if(log != null){
			JSONArray ja = new JSONArray();
			List<ManageLogExtend> list = logInfoDao.queryManageLog(log);
			if(!CollectionUtils.isEmpty(list)){
				for (ManageLogExtend manageLogExtend : list) {
					JSONObject jo = new JSONObject();
					if(manageLogExtend != null){
						jo.accumulate("content", manageLogExtend.getContent());
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						jo.put("operateDate", sdf.format(manageLogExtend.getOperateDate()));
						jo.accumulate("name", manageLogExtend.getName());
						jo.accumulate("userName", manageLogExtend.getUserName());
						jo.accumulate("content", manageLogExtend.getContent());
						ja.add(jo);
					}
				}
				json.put("list", ja);
			}else{
				json.put("list", "");
			}
		}
		return json.toString();
	}
	
	
	public List<ManageLogExtend> queryManageLog(ManageLog log){
		return logInfoDao.queryManageLog(log);
	}
	
}
