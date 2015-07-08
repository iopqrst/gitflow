package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.ClientLatestPhyDao;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.client.ClientLatestPhy;

@Repository
@SuppressWarnings("unchecked")
public class ClientLatestPhyDaoImpl extends BaseDaoImpl<ClientLatestPhy>
		implements ClientLatestPhyDao {

	public ClientLatestPhy getClientLastestPhy(Integer clientId) {
		String hql = "from ClientLatestPhy where clientId = ?";
		List<ClientLatestPhy> list = executeFind(hql, clientId);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	
	public void updateClientLatestPhy(ClientLatestPhy phy){
		String sql = "update t_client_latest_physical set height = ? ,weight = ? where clientId = ?";
		List args = new ArrayList();
		args.add(phy.getHeight());
		args.add(phy.getWeight());
		args.add(phy.getClientId());
		updateBySql(sql, args.toArray());
	}
	
	
	public void updateLatestPhy(String data, int type, Integer cid){
		if(!StringUtils.isEmpty(data) && type != 0 && cid != null){
			List args = new ArrayList();
			String hql = "update ClientLatestPhy";
			if(type == 5){
				hql += " set height = ?";
				args.add(data);
			}else if(type == 6){
				hql += " set weight = ?";
				args.add(data);
			}
			hql += " where clientId = ?";
			args.add(cid);
			
			updateByHql(hql, args.toArray());
		}
	}
}
