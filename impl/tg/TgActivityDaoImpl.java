package com.bskcare.ch.dao.impl.tg;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.tg.TgActivityDao;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.tg.TgActivity;

@Repository
@SuppressWarnings("unchecked")
public class TgActivityDaoImpl extends BaseDaoImpl<TgActivity> implements TgActivityDao{
	
	public TgActivity queryTgActivity(TgActivity tgActivity){
		String hql = "from TgActivity where 1 = 1";
		List args = new ArrayList();
		if(tgActivity != null){
			if(!StringUtils.isEmpty(tgActivity.getMobile())){
				hql += " and mobile = ?";
				args.add(tgActivity.getMobile());
			}
		}
		
		List<TgActivity> lst = executeFind(hql, args.toArray());
		if(!CollectionUtils.isEmpty(lst)){
			return lst.get(0);
		}
		
		return null;
	}
}
