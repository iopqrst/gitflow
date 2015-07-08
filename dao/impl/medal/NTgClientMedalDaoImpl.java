package com.bskcare.ch.dao.impl.medal;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.medal.NTgClientMedalDao;
import com.bskcare.ch.vo.medal.NTgClientMedal;
import com.bskcare.ch.vo.medal.NTgMedalTemp;

@Repository
@SuppressWarnings("unchecked")
public class NTgClientMedalDaoImpl extends BaseDaoImpl<NTgClientMedal>
		implements NTgClientMedalDao {

	public NTgClientMedal queryClientMedal(Integer clientId) {
		String sql = "select * from ntg_client_medal where clientId = ? limit 1";
		List args = new ArrayList();
		args.add(clientId);
		List<NTgClientMedal> lst = executeNativeQuery(sql, args.toArray(),
				NTgClientMedal.class);
		if (!CollectionUtils.isEmpty(lst)) {
			return lst.get(0);
		}
		return null;
	}

	public int insertMedalTemp(NTgMedalTemp medalTemp) {
		String sql = "insert into ntg_medal_temp (clientId,type,score, level) value(?,?,?,?)";
		ArrayList args = new ArrayList();
		args.add(medalTemp.getClientId());
		args.add(medalTemp.getType());
		args.add(medalTemp.getScore());
		args.add(medalTemp.getLevel());
		return updateBySql(sql, args.toArray());
	}
	
	public List<NTgMedalTemp> queryAllTempMedal(Integer clientId) {
		String sql = "select * from ntg_medal_temp where clientId = ?";
		return (List<NTgMedalTemp>) executeNativeQuery(sql, new Object[]{clientId}, NTgMedalTemp.class);
	}
}
