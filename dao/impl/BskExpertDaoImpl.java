package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.bo.BskExpertExtend;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.BskExpertDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.BskExpert;

@Repository
@SuppressWarnings("unchecked")
public class BskExpertDaoImpl extends BaseDaoImpl<BskExpert> implements
		BskExpertDao {

	public PageObject findBskExpert(BskExpert expert, QueryInfo queryInfo) {
		ArrayList args = new ArrayList();
		String hql = "from BskExpert where 1=1";
		if (expert != null) {
			if (null != expert.getId()) {
				hql += " and id = ?";
				args.add(expert.getId());
			}
			if (!StringUtils.isEmpty(expert.getName())) {
				hql += " and name like ?";
				args.add("%" + expert.getName().trim() + "%");
			}

			if (!StringUtils.isEmpty(expert.getMobile())) {
				hql += " and mobile like ?";
				args.add("%" + expert.getMobile().trim() + "%");
			}
			
			if (!StringUtils.isEmpty(expert.getQq())) {
				hql += " and qq like ?";
				args.add("%" + expert.getQq().trim() + "%");
			}
		}
		if(null == expert){
			hql += "and status = ?";
			args.add(BskExpert.BSK_EXPERT_NORMAL);
		}
		
		hql+="order by sort,id desc";

		return queryObjects(hql, args.toArray(), queryInfo);
	}
	
	public List<BskExpert> findBskExpertAll(){
		String hql = "from BskExpert where status = ? order by sort desc, id asc";
		return executeFind(hql, BskExpert.BSK_EXPERT_NORMAL);
	}

	/**
	 * 根据用户所在的区域查询该区域的医生和健康管理师
	 */
	public List<BskExpert> findBskExpertByclientAreaId(String userIds){
		String sql="select t1.* from t_bsk_expert t1,t_userinfo t2 where t1.status=? and t1.userId = t2.id";
		ArrayList args = new ArrayList();
		args.add(BskExpert.BSK_EXPERT_NORMAL);
		if(!StringUtils.isEmpty(userIds)){
			sql += " and t1.userId in ("+userIds+")";
		}
		sql+=" order by t1.id desc";
		return executeNativeQuery(sql ,args.toArray() ,BskExpert.class);
	}

	public BskExpert queryExpertByUserId(Integer userId) {
		String hql = "from BskExpert where userId = ? and status = ?";
		List<BskExpert> list = executeFind(hql, new Object[]{userId, Constant.STATUS_NORMAL});
		if(null != list && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	public List<BskExpert> queryExpertByUserIds(String userIds) {
		String hql = "from BskExpert where status = ? ";
		if(!StringUtils.isEmpty(userIds)){
			hql += " and userId in ("+userIds+")";
		}
		List<BskExpert> list = executeFind(hql, new Object[]{ Constant.STATUS_NORMAL});
		return list;
	}
	
	public List<BskExpertExtend> queryExpertByUids(String userIds) {
		if(StringUtils.isEmpty(userIds))  return null;
		String sexpert = "SELECT * FROM t_bsk_expert WHERE status = 0 and userId IN ("+userIds+")";
		String sevalution = "SELECT t1.expertId,t1.total,t2.hp FROM (select count(id) total,expertId FROM tg_expert_evalution group by expertId) t1 " +
								" LEFT JOIN (select count(id) hp,expertId FROM tg_expert_evalution " +
								" WHERE score >= 3 GROUP BY expertId) t2 " +
								" ON t1.expertId = t2.expertId";
		
		String sql = "SELECT m.*, IFNULL(n.total,0) AS total, IFNULL(n.hp,0) AS hp FROM (" + sexpert + ") m" +
				" LEFT JOIN (" + sevalution +") n ON m.id = n.expertId ";
		
		return executeNativeQuery(sql, null, BskExpertExtend.class);
	}
	
	/**
	 * 在线咨询部分修改满意度
	 */
	public int updateOnlineInfo(BskExpert be) {
		String hql = "update BskExpert be set be.satisfaction = ? ,"
				+ " be.subscribeCount = ? where be.id = ?";

		return updateByHql(hql, new Object[] { be.getSatisfaction(),
				be.getSubscribeCount(), be.getId() });
	}

	
	public PageObject queryBskExpert(String type, QueryInfo queryInfo){
		String hql = "from BskExpert where status = ?";
		List args = new ArrayList();
		args.add(BskExpert.BSK_EXPERT_NORMAL);
		if(!StringUtils.isEmpty(type)){
			if(type.equals("zixun")){
				hql += " and role != ?";
				args.add(BskExpert.ROLE_GUEST_MANAGER);
			}else{
				hql += " and role in("+BskExpert.ROLE_DOCTOR+","+BskExpert.ROLE_HEATH_MANAGER+","+BskExpert.ROLE_GUEST_MANAGER+")";
			}
		}
		hql += "order by sort desc";
		return queryObjects(hql, args.toArray(), queryInfo);
	}

	public BskExpert queryExpertByMobile(String mobile) {
		String hql = "from BskExpert where mobile = ? and status = ?";
		List<BskExpert> list = executeFind(hql, new Object[]{mobile, Constant.STATUS_NORMAL});
		if(null != list && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public int addEvalution(Integer cid, int serviceScore, int medicalScore,
			Integer expertId) {
		String sql = "insert into tg_expert_evalution(clientId," +
				" expertId, score, type, createTime) value (?,?,?,?,?),(?,?,?,?,?)";
		
		ArrayList args = new ArrayList();
		Date d = new Date();
		
		args.add(cid);
		args.add(expertId);
		args.add(serviceScore);
		args.add(BskExpertExtend.TYPE_OF_SERVICE);
		args.add(d);
		
		args.add(cid);
		args.add(expertId);
		args.add(medicalScore);
		args.add(BskExpertExtend.TYPE_OF_MEDICAL);
		args.add(d);
		
		return this.updateBySql(sql, args.toArray());
	}

}
