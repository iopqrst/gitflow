package com.bskcare.ch.dao.impl.msport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.msport.MsportCommentDao;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.vo.msport.MsportComment;


@Repository
@SuppressWarnings("unchecked")
public class MsportCommentDaoImpl extends BaseDaoImpl<MsportComment> implements MsportCommentDao{
	
	public int queryCommentById(MsportComment comment){
		int count = 0;
		String sql = "select count(id) from m_sport_comment where clientId = ? and friendId = ? and createTime = ?";
		List args = new ArrayList();
		args.add(comment.getClientId());
		args.add(comment.getFriendId());
		String date = DateUtils.formatDate("yyyy-MM-dd", comment.getCreateTime());
		args.add(date);
		Object obj = findUniqueResultByNativeQuery(sql, args.toArray());
		if(obj != null){
			count = Integer.parseInt(obj.toString());
		}
		return count;
	}
}
