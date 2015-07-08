package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.PhysicalDetailDao;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.client.PhysicalDetail;

@Repository
@SuppressWarnings("unchecked")
public class PhysicalDetailDaoImpl extends BaseDaoImpl<PhysicalDetail>
		implements PhysicalDetailDao {

	public List<PhysicalDetail> findDetail(PhysicalDetail pd) {
		StringBuffer hql = new StringBuffer(
				"from PhysicalDetail pd where 1 = 1");
		ArrayList args = new ArrayList();
		if (pd != null) {
			if (!StringUtils.isEmpty(pd.getName())) {
				hql.append("and pd.name like ?");
				args.add("%" + pd.getName().trim() + "%");
			}
			if (pd.getIsCommon() != -1) {
				hql.append("and pd.isCommon= ?");
				args.add(pd.getIsCommon());
			}
		}

		return executeFind(hql.toString(), args.toArray());
	}

	public List<PhysicalDetail> queryDetailByClientId(Integer clientId) {
		String hql = "from PhysicalDetail pd where "
				+ " exists(from PhysicalItem pi where pi.clientId = ? and pi.pdId = pd.pdId) "
				+ " order by pd.isCommon desc, pd.pdId asc";
		return executeFind(hql.toString(), new Object[] { clientId });
	}

	public Integer findMinPdIdByClientId(Integer clientId){
		String sql = "select min(t2.pdId) from t_client_physical t1 , t_physical_item t2 where t1.id = t2.physicalId and t1.clientId = ?";
		Object pdId = findUniqueResultByNativeQuery(sql, clientId);
		if(pdId != null){
			return Integer.parseInt(pdId.toString());
		}else{
			return null;
		}
	}
}
