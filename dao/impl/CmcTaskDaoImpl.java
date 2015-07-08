package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.bo.CmcTaskExtend;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.CmcTaskDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.CmcTask;

@Repository
@SuppressWarnings("unchecked")
public class CmcTaskDaoImpl extends BaseDaoImpl<CmcTask> implements CmcTaskDao {

	public PageObject findCmcTaskExtend(CmcTaskExtend taskExtend, QueryInfo queryInfo,String areaChain) {
		ArrayList args = new ArrayList();
		
		String sql = "SELECT t1.`name` as clientName,mobile,age,gender,areaId, t2.* FROM t_clientinfo t1 right join (" +
				" SELECT c1.*, c2.mainconstitution FROM t_cmc_task c1 LEFT JOIN t_cmc_evaluation c2 ON c1.ceId = c2.id) t2 " +
				" on t1.id = t2.clientId" +
				" WHERE t1.status = ?";
		
		args.add(Constant.STATUS_NORMAL);
		
		if(!StringUtils.isEmpty(areaChain)){
			String[] areaChains = areaChain.split("#");
			
			sql += " and (";
			
			if(areaChains.length>1){
				for (int i = 0; i < areaChains.length; i++) {
					sql += " t1.areaChain like ?";
					
					args.add(areaChains[i]+"%");
					
					if(i != areaChains.length-1){
						sql += " or";
					}
				}
			}else{
				sql += " t1.areaChain like ?";
				args.add(areaChains[0]+"%");
			}
			sql += ")";
			
		}
		
		if (taskExtend != null) {
			if (taskExtend.getStatus() != null) {
				sql += " and t2.status = ?";
				args.add(taskExtend.getStatus());
			}
			
			if (!StringUtils.isEmpty(taskExtend.getClientName())) {
				sql += " and t1.name like ?";
				args.add("%" + taskExtend.getClientName().trim() + "%");
			}
		}
		sql += " ORDER BY t2.createTime DESC";
		return queryObjectsBySql(sql, null, null, args.toArray(), queryInfo,
				CmcTaskExtend.class);
	}

	public void dealTask(Integer id) {
		String hql = "update CmcTask set status = ? where id=?";
		Object[] obj = { CmcTask.CMC_TASK_DEAL, id };
		updateByHql(hql, obj);
	}

	public CmcTask findCmcTaskById(Integer id) {
		String hql = "from CmcTask where id=?";
		List<CmcTask> lstTask = executeFind(hql, id);
		if (lstTask != null) {
			return lstTask.get(0);
		} else {
			return null;
		}
	}

	public CmcTask findCmcTaskByIds(Integer clientId, Integer ceId) {
		String hql = "from CmcTask where clientId = ? and ceId = ?";
		Object[] obj = { clientId, ceId };
		List<CmcTask> lstTasks = executeFind(hql, obj);
		if (!CollectionUtils.isEmpty(lstTasks)) {
			return lstTasks.get(0);
		} else {
			return null;
		}
	}

	
	public int findCmcTaskExtend(CmcTaskExtend taskExtend,String areaChain) {
		ArrayList args = new ArrayList();
		
		String sql = "select count(*) from t_clientinfo t1 inner join" +
				" (select c1.*,c2.mainconstitution from t_cmc_task c1 left JOIN t_cmc_evaluation c2 " +
				" on c1.ceId = c2.id where 1=1) t2 " +
				" on t1.id = t2.clientId" +
				" where t1.status = ?";
		args.add(Constant.STATUS_NORMAL);
		
		if(!StringUtils.isEmpty(areaChain)){
			String[] areaChains = areaChain.split("#");
			
			sql += " and (";
			
			if(areaChains.length>1){
				for (int i = 0; i < areaChains.length; i++) {
					sql += " t1.areaChain like ?";
					
					args.add(areaChains[i]+"%");
					
					if(i != areaChains.length-1){
						sql += " or";
					}
				}
			}else{
				sql += " t1.areaChain like ?";
				args.add(areaChains[0]+"%");
			}
			sql += ")";
			
		}

		if (taskExtend != null) {
			if (taskExtend.getStatus() != null) {
				sql += " and t2.status = ?";
				args.add(taskExtend.getStatus());
			}
		}
		
		Object obj = findUniqueResultByNativeQuery(sql, args.toArray());
		if(obj != null && !obj.equals("")){
			return Integer.parseInt(obj.toString());
		}
		return 0;
	}
}
