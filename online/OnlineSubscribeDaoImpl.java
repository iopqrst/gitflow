package com.bskcare.ch.dao.impl.online;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.online.OnlineSubscribeDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.BskExpert;
import com.bskcare.ch.vo.online.OnlineSubscribe;

@Repository
@SuppressWarnings("unchecked")
public class OnlineSubscribeDaoImpl extends BaseDaoImpl<OnlineSubscribe>
		implements OnlineSubscribeDao {

	public PageObject<Object> querySubscribe(OnlineSubscribe sub,
			QueryInfo queryInfo) {

		String sql = "select {os.*},{b.*} from t_online_subscribe os left join t_bsk_expert b on b.id = os.expertId where b.status = ?";
		ArrayList args = new ArrayList();
		args.add(Constant.STATUS_NORMAL);
		if (null != sub) {
			if (-1 != sub.getStatus()) {
				sql += " and os.status = ?";
				args.add(sub.getStatus());
			}

			if (null != sub.getClientId()) {
				sql += " and os.clientId = ?";
				args.add(sub.getClientId());
			}

			if (!StringUtils.isEmpty(sub.getMobile())) {
				sql += " and os.mobile = ?";
				args.add(sub.getMobile());
			}

			if (null != sub.getExpertId()) {
				sql += " and os.expertId = ?";
				args.add(sub.getExpertId());
			}
		}

		sql += " order by os.createTime desc";

		Map entities = new HashMap();
		entities.put("os", OnlineSubscribe.class);
		entities.put("b", BskExpert.class);

		return this.queryObjectsBySql(sql, args.toArray(), entities, queryInfo);
	}

	public Object querySubCount(Integer expertId) {
		String hql = "select count(o.id) from OnlineSubscribe o where 1 = 1 ";
		ArrayList args = new ArrayList();
		if (null != expertId) {
			hql += " and o.expertId = ?";
			args.add(expertId);
		}

		return this.findUniqueResult(hql, args.toArray());
	}

	public PageObject querySubscribeByUserId(OnlineSubscribe osub,
			QueryCondition query, QueryInfo queryInfo) {
		List args = new ArrayList();
		String asql = "select t2.id from t_userinfo t1 left join t_bsk_expert t2 "
				+ " on t1.id = t2.userId where 1=1";

		if (query != null && query.getUserId() != null) {
			asql += " and t1.id = ?";
			args.add(query.getUserId());
		}

		String csql = "select t1.id subId,t1.expertId," +
				" t1.subTime,t1.createTime,t1.remark,t1.status,t1.clientId,t1.finishTime, " +
				" t2.name clientName,t2.mobile mobile,t2.id" +
				" from t_online_subscribe t1 inner join t_clientinfo t2 on t1.clientId = t2.id where 1 = 1";
		
		if(osub != null){
			int status = osub.getStatus();
			if(status != -1){
				csql += " and t1.status = ?";
				args.add(status);
			}
		}
		if (query != null) {
			if (!StringUtils.isEmpty(query.getClientName())) {
				csql += " and t2.name like ?";
				args.add("%" + query.getClientName().trim() + "%");
			}
			if (!StringUtils.isEmpty(query.getMobile())) {
				csql += " and t2.mobile like ?";
				args.add("%" + query.getMobile().trim() + "%");
			}
		}

		String sql = "select n.subId id,n.clientId clientId,n.clientName clientName,n.mobile mobile," +
				"n.subTime subTime,n.createTime createTime," +
				"n.remark remark,n.status status,n.finishTime finishTime" +
				" from ("+ asql + ") m join (" + csql + ") n ON m.id = n.expertid";
		
		sql += " order by n.subTime";
		
		Map scalars = new LinkedHashMap();
		scalars.put("id", StandardBasicTypes.INTEGER);
		scalars.put("clientId", StandardBasicTypes.INTEGER);
		scalars.put("clientName", StandardBasicTypes.STRING);
		scalars.put("mobile", StandardBasicTypes.STRING);
		scalars.put("subTime", StandardBasicTypes.TIMESTAMP);
		scalars.put("createTime", StandardBasicTypes.TIMESTAMP);
		scalars.put("remark", StandardBasicTypes.STRING);
		scalars.put("status", StandardBasicTypes.INTEGER);
		scalars.put("finishTime", StandardBasicTypes.TIMESTAMP);
		
		
		return queryObjectsBySql(sql, null, scalars, args.toArray(), queryInfo ,null);
	}
	
	
	public int querySubscribeByUserId(OnlineSubscribe osub, QueryCondition query) {
		List args = new ArrayList();
		String asql = "select t2.id from t_userinfo t1 left join t_bsk_expert t2 "
				+ " on t1.id = t2.userId where 1=1";

		if (query != null && query.getUserId() != null) {
			asql += " and t1.id = ?";
			args.add(query.getUserId());
		}

		String csql = "select h.*,c.* from(select t1.id subId,t1.expertId," +
				"t1.subTime,t1.createTime,t1.remark,t1.status,t1.clientId,t1.finishTime " +
				"from t_online_subscribe t1 where 1=1";
		
		if(osub != null){
			int status = osub.getStatus();
			if(status != -1){
				csql += " and t1.status = ?";
				args.add(status);
			}
		}
		
		csql += " order by t1.subTime) h";
	
		csql += " join (select t2.name clientName,t2.mobile mobile,t2.id from t_clientinfo t2 where 1=1";

		if (query != null) {
			if (!StringUtils.isEmpty(query.getClientName())) {
				csql += " and t2.name like ?";
				args.add("%" + query.getClientName().trim() + "%");
			}
			if (!StringUtils.isEmpty(query.getMobile())) {
				csql += " and t2.mobile like ?";
				args.add("%" + query.getMobile().trim() + "%");
			}
		}

		csql += ") c on h.clientid = c.id";

		String sql = "select count(*) "+
				" from ("+ asql + ") m join (" + csql + ") n ON m.id = n.expertid";
		
		Object obj = findUniqueResultByNativeQuery(sql, args.toArray());
		if(obj != null && !obj.equals("")){
			return Integer.parseInt(obj.toString());
		}
		return 0;
	}
}
