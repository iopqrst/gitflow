package com.bskcare.ch.dao.impl.score;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.score.SignInDao;
import com.bskcare.ch.vo.score.SignIn;

@Repository
@SuppressWarnings("unchecked")
public class SignInDaoImpl extends BaseDaoImpl<SignIn> implements SignInDao {

	public SignIn queryLatestRecord(Integer clientId) {
		StringBuffer sql = new StringBuffer(
				"select * from s_sign_in where clientId = ? "
						+ " order by id desc limit 1");
		ArrayList args = new ArrayList();

		args.add(clientId);
		List<SignIn> list = this.executeNativeQuery(sql.toString(),
				args.toArray(), SignIn.class);

		SignIn signIn = null;
		if (null != list && list.size() > 0) {
			signIn = list.get(0);
		}
		return signIn;
	}

	public int queryTodayRecord(Integer clientId) {
		String sql = "select count(*) from s_sign_in where clientId = ?" +
				" and createTime > DATE_FORMAT(now(),'%Y-%m-%d')";
		ArrayList args = new ArrayList();

		args.add(clientId);
		Object obj = this.findUniqueResultByNativeQuery(sql.toString(), args
				.toArray());
		return Integer.parseInt(obj+"");
	}
}
