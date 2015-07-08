package com.bskcare.ch.service.impl.msport;

import java.util.Date;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.BloodSugarTargetDao;
import com.bskcare.ch.service.BloodSugarTargetService;
import com.bskcare.ch.vo.BloodSugarTarget;
@Service
public class BloodSugarTargetServiceImpl implements BloodSugarTargetService {

	@Autowired
	private BloodSugarTargetDao bloodSugarTargetDao;

	public BloodSugarTarget get(int id) {
		return bloodSugarTargetDao.load(id);
	}

	public BloodSugarTarget quertTarget(BloodSugarTarget target) {
		return bloodSugarTargetDao.quertTarget(target);
	}

	public String saveOrUpdate(BloodSugarTarget target) {
		BloodSugarTarget target2 = bloodSugarTargetDao.quertTarget(target);
		if(target2 == null){
			target.setCreateTime(new Date());
			target2 = bloodSugarTargetDao.add(target);
		}else{
			target2.setClientId(target.getClientId());
			target2.setFbgMax(target.getFbgMax());
			target2.setFbgMin(target.getFbgMin());
			target2.setPbgMax(target.getPbgMax());
			target2.setPbgMin(target.getPbgMin());
			bloodSugarTargetDao.update(target2);
		}
		JSONObject jo = new JSONObject();
		if(target2!=null&&target2.getId()!=null){
			jo.put("code", Constant.INTERFACE_SUCC);
			jo.put("msg", "保存成功");
			jo.put("data", "");
		}else{
			jo.put("code", Constant.INTERFACE_FAIL);
			jo.put("msg", "保存失败，请稍后重试。");
			jo.put("data", "");
		}
		return jo.toString();
	}

}
