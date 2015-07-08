package com.bskcare.ch.dao.impl.rpt;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.bo.RptQueryObject;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.rpt.SrptBaseInfoDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.AreaInfo;
import com.bskcare.ch.vo.rpt.RptBaseInfo;
import com.bskcare.ch.vo.rpt.SrptBaseInfo;

@Repository
@SuppressWarnings("unchecked")
public class SrptBaseInfoDaoImpl extends BaseDaoImpl<SrptBaseInfo> implements
		SrptBaseInfoDao {

	public PageObject<SrptBaseInfo> queryAll(String areaChain,
			SrptBaseInfo srptBaseInfo, QueryInfo queryInfo) {
		ArrayList args = new ArrayList();
		/**
		 * 0: 自动生成 1：草稿 2：已提交 3：已删除
		 */
		String csql = "SELECT t1.id, t1.areaId, t2.age, t2.gender," +
				" t2.`name` AS userName, t2.mobile, t2.status, t2.createTime, " +
				" t2.id AS rptId,t2.type as srptType FROM t_clientinfo t1 LEFT JOIN srpt_baseinfo t2" +
				" ON t1.id = t2.clientId WHERE t2.id IS NOT NULL and t1.status = ?";
		args.add(Constant.STATUS_NORMAL);

		if (null != srptBaseInfo) {
			if (!StringUtils.isEmpty(srptBaseInfo.getName())) {
				csql += " and t2.name like ?";
				args.add("%" + srptBaseInfo.getName().trim() + "%");
			}
			if (!StringUtils.isEmpty(srptBaseInfo.getMobile())) {
				csql += " and t2.mobile like ?";
				args.add("%" + srptBaseInfo.getMobile().trim() + "%");
			}
			if(srptBaseInfo.getStatus() != -1){
				csql += " and t2.status = ?";
				args.add(srptBaseInfo.getStatus());
			}
		}

		if (!StringUtils.isEmpty(areaChain)) {
			String[] areaChains = areaChain.split("#");

			csql += " and (";

			if (areaChains.length > 1) {
				for (int i = 0; i < areaChains.length; i++) {
					csql += " t1.areaChain like ?";
					args.add(areaChains[i] + "%");
					if (i != areaChains.length - 1) {
						csql += " or";
					}
				}
			} else {
				csql += " t1.areaChain like ?";
				args.add(areaChains[0] + "%");
			}
			csql += ")";
		}

		csql += "  and t2.status != ?";
		args.add(RptBaseInfo.RPT_STATUS_DELETE);

		String asql = "select t3.id,t3.`name` as areaName from t_areainfo t3"
				+ " where t3.`status` = ?";

		args.add(AreaInfo.AREA_NORMAL);
		String sql = "select m.*, n.areaName from (" + csql + ") m , (" + asql
				+ ") n where m.areaId = n.id order by m.createTime desc";
		return this.queryObjectsBySql(sql, null, null, args.toArray(),
				queryInfo, RptQueryObject.class);
	}
	
	public void updateStatus(Integer id) {
		String hql = "update SrptBaseInfo set status=? where id=? ";
		Object[] obj = {SrptBaseInfo.RPT_STATUS_DELETE,id}; 
		this.updateByHql(hql, obj);
	}
	
	public int auditSrptBaseInfo(Integer id,int status){
		String hql = "update SrptBaseInfo set status = ? where id= ?";
		List args = new ArrayList();
		args.add(status);
		args.add(id);
		return updateByHql(hql, args.toArray());
	}
	
	public SrptBaseInfo queryLatestSrpt(Integer clientId){
		String sql = "select * from srpt_baseinfo where clientId = ? order by createTime desc limit 1";
		List args = new ArrayList();
		args.add(clientId);
		List<SrptBaseInfo> lst = executeNativeQuery(sql, args.toArray(), SrptBaseInfo.class);
		if(!CollectionUtils.isEmpty(lst)){
			return lst.get(0);
		}
		return null;
	}

	public void updateSrptBaseInfo(SrptBaseInfo baseInfo) {
		this.update(baseInfo);
	}
}
