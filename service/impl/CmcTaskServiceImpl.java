package com.bskcare.ch.service.impl;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.bo.CmcTaskExtend;
import com.bskcare.ch.dao.CmcTaskDao;
import com.bskcare.ch.dao.ConstitutionDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.CmcTaskService;
import com.bskcare.ch.vo.CmcTask;

@Service
@SuppressWarnings("unchecked")
public class CmcTaskServiceImpl implements CmcTaskService{
	
	@Autowired
	private CmcTaskDao cmcTaskDao;
	
	@Autowired
	private ConstitutionDao constitutionDao;
	
	public PageObject findCmcTaskExtend(CmcTaskExtend taskExtend , QueryInfo queryInfo,String areaChain){
		return cmcTaskDao.findCmcTaskExtend(taskExtend, queryInfo,areaChain);
	}
	
	public void dealCmc(Integer cmcId ,Integer taskId){
	
		cmcTaskDao.dealTask(taskId);
		constitutionDao.dealCmc(cmcId);
	}
	

	public void updateCmcTask(CmcTask cmcTask){
		if (null != cmcTask) {
			CmcTask ct = cmcTaskDao.findCmcTaskById(cmcTask.getId());
			if (null != ct) {
				BeanUtils.copyProperties(cmcTask, ct, new String[] {"clientId", "ceId", "createTime"});
				ct.setAdviceTime(new Date());
				cmcTaskDao.update(ct);
			}
		}
	}
	
	/**
	 * 根据id查询某个中医体质辨识建议信息
	 */
	public CmcTask findCmcTaskById(Integer id){
		return cmcTaskDao.findCmcTaskById(id);
	}
	
	public CmcTask findCmcTaskByIds(Integer clientId,Integer ceId){
		return cmcTaskDao.findCmcTaskByIds(clientId, ceId);
	}
	
}
