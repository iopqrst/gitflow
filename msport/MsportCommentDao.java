package com.bskcare.ch.dao.msport;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.msport.MsportComment;

public interface MsportCommentDao extends BaseDao<MsportComment>{
	
	/**根据用户id和评论人的id查询当天是否评论了某人**/
	public int queryCommentById(MsportComment comment);

}
