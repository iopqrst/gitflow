package com.bskcare.ch.service.impl.msport;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.msport.MSportPlanDao;
import com.bskcare.ch.service.msport.MSportPlanService;
import com.bskcare.ch.vo.msport.MSportPlan;

@Service
public class MSportPlanServiceImpl implements MSportPlanService{
	
	@Autowired
	private MSportPlanDao mplanDao;
	
	public void addMSportPlan(MSportPlan mplan){
		
		//添加之前，首先判断是否上传过数据
		if(mplan != null){
			Integer clientId = mplan.getClientId();
			MSportPlan plan = mplanDao.queryMSportPlan(clientId);
			if(plan != null){
				BeanUtils.copyProperties(mplan, plan, new String[]{"id"});
				plan.setCreateTime(new Date());
				mplanDao.update(plan);
			}else{
				mplan.setCreateTime(new Date());
				mplanDao.add(mplan);
			}
		}
	}
	
	public MSportPlan queryMSportPlan(Integer cid){
		return mplanDao.queryMSportPlan(cid);
	}
	
	
	public void addMSportPlanSport(MSportPlan mplan){
		
		//添加之前，首先判断是否上传过数据
		if(mplan != null){
			Integer clientId = mplan.getClientId();
			MSportPlan plan = mplanDao.queryMSportPlan(clientId);
			if(plan != null){
				mplanDao.updateMsportPlan(mplan);
			}else{
				mplan.setCreateTime(new Date());
				mplanDao.add(mplan);
			}
		}
	}
	
	
	public void savePlanDay(Integer clientId,int planDay){
		//添加之前，首先判断是否上传过数据
		MSportPlan plan = mplanDao.queryMSportPlan(clientId);
		if(plan != null){
			plan.setCreateTime(new Date());
			plan.setPlanDay(planDay);
			mplanDao.updatePlanDay(plan);
		}else{
			MSportPlan mplan = new MSportPlan();
			mplan.setClientId(clientId);
			mplan.setCreateTime(new Date());
			mplan.setPlanDay(planDay);
			mplanDao.add(mplan);
		}
	}
	
	
	public void saveStepWidth(Integer clientId,double stepWidth){
		//添加之前，首先判断是否上传过数据
		MSportPlan plan = mplanDao.queryMSportPlan(clientId);
		if(plan != null){
			plan.setCreateTime(new Date());
			plan.setStepWidth(stepWidth);
			mplanDao.updateMsportPlan(plan);
		}else{
			MSportPlan mplan = new MSportPlan();
			mplan.setClientId(clientId);
			mplan.setCreateTime(new Date());
			mplan.setStepWidth(stepWidth);
			mplanDao.add(mplan);
		}
	}
}
