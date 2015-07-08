package com.bskcare.ch.dao.impl.rpt;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.bo.RptQueryObject;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.rpt.RptBaseInfoDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.AreaInfo;
import com.bskcare.ch.vo.rpt.RptBaseInfo;

@Repository
@SuppressWarnings("unchecked")
public class RptBaseInfoDaoImpl extends BaseDaoImpl<RptBaseInfo> implements RptBaseInfoDao{
	
	public int updateRptBaseByField(String field,String content, Integer rptId){
		String sql = "update rpt_baseinfo set "+ field +" = ? where rptId = ?";
		return updateBySql(sql, new Object[]{content,rptId});
	}
	
	public PageObject findAutoRptByUserId(String areaChain,QueryInfo info, 
			RptBaseInfo rpt,Integer userId,QueryCondition queryCondition ,Date beginTime ,Date endTime){
			ArrayList args = new ArrayList();
			/**
			 * 0: 自动生成 1：草稿 2：已提交 3：已删除
			 */
			String csql = "select t1.id,t1.`name` as userName,t1.mobile,t1.areaId,t2.createTime," +
			" t1.type,t1.age,t1.gender,t2.status,t2.docStatus,t2.dietStatus,t2.sportStatus,t2.reading,t2.hmStatus,"+
			"t2.rptId as rptId from t_clientinfo t1 join rpt_baseinfo t2 " +
			" on t1.id = t2.clientId where t1.status = ?";
			args.add(Constant.STATUS_NORMAL);
			
			if (null != rpt) {		
				if (!StringUtils.isEmpty(rpt.getName())) {
					csql += " and t1.name like ?";
					args.add("%" + rpt.getName().trim() + "%");
				}
				if (!StringUtils.isEmpty(rpt.getMobile())) {
					csql += " and t1.mobile like ?";
					args.add("%" + rpt.getMobile().trim() + "%");
				}
				if(null != rpt.getStatus()){
					csql += " and t2.status = ?";
					args.add(rpt.getStatus());
				}
				if(null != beginTime){
					csql += " and t2.createTime > ? ";
					args.add(DateUtils.formatDate(DateUtils.DATE_PATTERN,beginTime));
				}
				if(null != endTime){
					csql += " and  t2.createTime < ?";
					args.add(DateUtils.formatDate(DateUtils.DATE_PATTERN,DateUtils.getAppointDate( endTime, 1)));
				}
			}
			
			if(!StringUtils.isEmpty(areaChain)){
				String[] areaChains = areaChain.split("#");
				
				csql += " and (";
				
				if(areaChains.length>1){
					for (int i = 0; i < areaChains.length; i++) {
						csql += " t1.areaChain like ?";
						args.add(areaChains[i]+"%");
						if(i != areaChains.length-1){
							csql += " or";
						}
					}
				}else{
					csql += " t1.areaChain like ?";
					args.add(areaChains[0]+"%");
				}
				csql += ")";
			}
			
			
			csql += "  and t2.status != ?";
			args.add(RptBaseInfo.RPT_STATUS_DELETE);
			
			String asql = "select t2.id,t2.`name` as areaName from t_areainfo t2" +
					" where t2.`status` = ?";
			
			args.add(AreaInfo.AREA_NORMAL);
//			if(null != queryCondition) {
//				if(!StringUtils.isEmpty(queryCondition.getAreaName())){
//					asql+=" and t2.name like ?";
//					args.add("%"+queryCondition.getAreaName().trim()+"%");
//				}
//			}
			String sql = "select m.*, n.areaName from (" + csql + ") m , (" + asql
			+ ") n where m.areaId = n.id order by m.createTime desc";
			return this.queryObjectsBySql(sql, null, null, args.toArray(),
					info, RptQueryObject.class);
	}
	
	
	public PageObject findRptList(RptBaseInfo rptBaseInfo,Integer clientId,QueryInfo queryInfo) {
		ArrayList args = new ArrayList();
		String sql = "from RptBaseInfo where status = ?";
		args.add(RptBaseInfo.RPT_STATUS_COMMIT);
		if(clientId != null){
			sql+=" and clientId = ?";
			args.add(clientId);
		}
		return queryPagerObjects(sql, args.toArray(), queryInfo);
	} 
	
	public int updateRptRead(Integer rptId){
		String sql = "update rpt_baseinfo set isRead = ? where rptId = ?";
		Object[] obj = {RptBaseInfo.RPT_READ,rptId};
		return updateBySql(sql, obj);
	}
	
	public int updateRptStatus(String field ,Integer rptId){
		String sql = "update rpt_baseinfo set "+field+" = ? where rptId = ?";
		if(field.equals("status")){
			Object[] obj = {RptBaseInfo.RPT_STATUS_COMMIT,rptId};
			return updateBySql(sql, obj);
		}else{
			Object[] obj = {RptBaseInfo.RPT_STATUS_UPDATE,rptId};
			return updateBySql(sql, obj);
		}
	}
		
	public Object latestAudit(String field,Integer rptId){
		String sql = "";
/*		if(field.equals("docStatus")){
			sql = "select count(*) from rpt_baseinfo where rptId = ? and sportStatus = ? and dietStatus = ?";
		}
		if(field.equals("dietStatus")){
			sql = "select count(*) from rpt_baseinfo where rptId = ? and sportStatus = ? and docStatus = ?";
		}
		if(field.equals("sportStatus")){
			sql = "select count(*) from rpt_baseinfo where rptId = ? and dietStatus = ? and docStatus = ?";
		}
*/
		if(field.equals("docStatus")){
			sql = "select count(*) from rpt_baseinfo where rptId = ? and hmStatus = ?";
		}
		if(field.equals("hmStatus")){
			sql = "select count(*) from rpt_baseinfo where rptId = ? and docStatus = ?";
		}
		
		List args = new ArrayList();
		args.add(rptId);
		args.add(RptBaseInfo.RPT_STATUS_UPDATE);
		return findUniqueResultByNativeQuery(sql, args.toArray());	
	}
	
	public int updateRptByField(String field,Integer content, Integer rptId){
		String sql = "update rpt_baseinfo set "+field+" = ? where rptId = ?";
		Object [] obj = {content,rptId};
		return updateBySql(sql, obj);
	}

	public List<RptBaseInfo> findRptBaseInfoByClientId(Integer clientId) {
		String sql = "";
		ArrayList args = new ArrayList();
		sql += "select * from rpt_baseinfo where 1=1 ";
		if(null != clientId) {
			sql += "and clientId=? order by createTime desc ";
			args.add(clientId);
		}
		return executeNativeQuery(sql, null, null, args.toArray(), RptBaseInfo.class);
	}
	
	public RptBaseInfo queryLatestRptBaseInfo(Integer clientId){
		String hql = "from RptBaseInfo where status != ?";
		List args = new ArrayList();
		args.add(RptBaseInfo.RPT_STATUS_DELETE);
		if(clientId != null){
			hql += " and clientId = ?";
			args.add(clientId);
		}
		hql += " order by createTime desc limit 1";
		List<RptBaseInfo> lstRpt = executeFind(hql, args.toArray());
		if(!CollectionUtils.isEmpty(lstRpt)){
			return lstRpt.get(0);
		}
		return null;
	}


	public PageObject findRptListByUserId(Integer cid, QueryInfo info,
			Integer type, String status) {
		info.setOrder(null);
		info.setSort(null);
		String sql = "SELECT li.* FROM ( SELECT ba.rptId AS id, ba.clientId AS clientId, ba.`name` AS `name`, 1 AS type, " +
				"ba.isRead AS isRead, ba.`status` AS `status`, ba.createTime AS createTime , ba.beginTime as beginTime FROM rpt_baseinfo ba UNION SELECT " +
				"sba.id AS id, sba.clientId AS clientId, sba.`name` AS NAME, sba.type AS type, sba.isRead AS isRead, sba.`status` AS `status`, " +
				"sba.createTime AS createTime ,sba.beginTime as beginTime FROM srpt_baseinfo sba ) AS li WHERE li.clientId = ? ";
		List args = new ArrayList();
		args.add(cid);
		if(type!=null){
			if(type==0){
				sql +=" and type != ? ";
				args.add(1);
			}else if(type==1){
				sql +=" and type = ? ";
				args.add(type);
			}
		}
		if(status!=null){
			sql += "and li.status in (?)";
			args.add(status);
		}
		sql+=" ORDER BY li.createTime DESC";
		return this.queryObjectsBySql(sql, args.toArray(), info);
	}
}
