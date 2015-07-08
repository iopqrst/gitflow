package com.bskcare.ch.dao.impl.tg;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.tg.TgRecordFoodDao;
import com.bskcare.ch.vo.tg.TgRecordFood;

@Repository
@SuppressWarnings("unchecked")
public class TgRecordFoodDaoImpl extends BaseDaoImpl<TgRecordFood> implements TgRecordFoodDao{
	
	/**
	 * 添加
	 */
	public TgRecordFood queryRecordFood(TgRecordFood recordFood, Date date){
		List args = new ArrayList();
		String hql = "from TgRecordFood where 1 = 1";
		if(recordFood != null){
			if(recordFood.getClientId() != null){
				hql += " and clientId = ?";
				args.add(recordFood.getClientId());
			}
			if(recordFood.getCanci() != null){
				hql += " and canci = ?";
				args.add(recordFood.getCanci());
			}
			if(date != null){
				hql += " and createTime = ?";
				args.add(date);
			}
		
			List<TgRecordFood> lst = executeFind(hql, args.toArray());
			if(!CollectionUtils.isEmpty(lst)){
				return lst.get(0);
			}
			return null;
		}
		
		return null;
	}
	
	
	/**
	 * 添加
	 */
	public List<TgRecordFood> queryClientRecordFood(Integer cid, Date date){
		List args = new ArrayList();
		String hql = "from TgRecordFood where 1 = 1";
		if(cid != null){
			hql += " and clientId = ?";
			args.add(cid);
		}
		if(date != null){
			hql += " and createTime = ?";
			args.add(date);
		}
		List<TgRecordFood> lst = executeFind(hql, args.toArray());
		return lst;
	}
	
	
	public List<Object> queryClientRecord(Integer clientId, Date startDate, Date endDate){
		
		String sql = "select sum(calorie) calorie, createTime from tg_record_food where clientId = ? and" +
		" createTime >= ? and createTime <= ?" +
		" order by createTime asc";
		
		List args = new ArrayList();
		args.add(clientId);
		args.add(startDate);
		args.add(endDate);
		
		Map scalars = new LinkedHashMap();
		scalars.put("calorie", StandardBasicTypes.DOUBLE);
		scalars.put("createTime", StandardBasicTypes.TIMESTAMP);
		return executeNativeQuery(sql, null, scalars, args.toArray(), null);
	}
}
