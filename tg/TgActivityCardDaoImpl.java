package com.bskcare.ch.dao.impl.tg;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.tg.TgActivityCardDao;
import com.bskcare.ch.vo.tg.TgActivityCard;

@Repository
@SuppressWarnings("unchecked")
public class TgActivityCardDaoImpl extends BaseDaoImpl<TgActivityCard> implements TgActivityCardDao{

	public TgActivityCard queryTgActivityCard(){
		String sql = "select * from tg_activity_card where status = ? " +
				"order by id asc limit 1";
		List args = new ArrayList();
		args.add(TgActivityCard.STATUS_NO_SEND);
		
		List<TgActivityCard> lst = executeNativeQuery(sql, args.toArray(), TgActivityCard.class);
		if(!CollectionUtils.isEmpty(lst)){
			return lst.get(0);
		}
		return null;
	}
}
