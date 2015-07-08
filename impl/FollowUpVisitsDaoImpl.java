package com.bskcare.ch.dao.impl;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.management.Query;

import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.bo.FollowUpVisitsExtends;
import com.bskcare.ch.dao.FollowUpVisitsDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.FollowUpVisits;
import com.bskcare.ch.vo.UserInfo;

@Repository
@SuppressWarnings("unchecked")
public class FollowUpVisitsDaoImpl extends BaseDaoImpl<FollowUpVisits> implements FollowUpVisitsDao {
	
	public FollowUpVisits addFollowUpVisits(FollowUpVisits followUpVisits,UserInfo userInfo) {
		//取出最后随访次数  0和1表示 未随访和已随访的总次数
		String[] status = null ;
		
		status = new String[2] ;
		status[0] = "0" ;
		status[1] = "1" ;
		//状态取消，更改成获得所有随访次数
//		if(followUpVisits.getStatus()==0){
//			status = new String[2] ;
//			status[0] = "0" ;
//			status[1] = "1" ;
//		}else if(followUpVisits.getStatus()==1){
//			//已随访
//			status = new String[1] ;
//			status[0] = "1" ;
//		}
		//查出该状态最后的随访
		FollowUpVisits tmp = this.getLastFollowUpVisitsByStatus(followUpVisits, status) ;
		return this.add(followUpVisits) ;
	}
	
	public PageObject getFollowUpVisitsList(FollowUpVisits followUpVisits,QueryInfo queryInfo) {
		StringBuffer sql = new StringBuffer("select {ui.*},{bp.*} from t_client_followup bp LEFT JOIN  t_userinfo ui on bp.userId = ui.id  ");
		//随访状态取消
		//sql.append(" where bp.clientId = ? and bp.status = ? ") ;
		sql.append(" where bp.clientId = ? and bp.content is not  null and bp.content != '' ") ;
		if(followUpVisits.getStatus()==1){
			//sql.append("  ORDER BY  bp.visitsCount DESC") ;
			queryInfo.setSort("bp.followUpDate") ;
			queryInfo.setOrder("desc") ;
		}
		
		Map entities = new LinkedHashMap();
		entities.put("ui", UserInfo.class);
		entities.put("bp", FollowUpVisits.class);
		
		
		ArrayList args = new ArrayList();		
		args.add(followUpVisits.getClientId()) ;
		//随访状态取消
		//args.add(followUpVisits.getStatus()) ;
		
		return  this.queryObjectsBySql(sql.toString(),args.toArray(),entities,queryInfo) ;
	}

	public void updateVisits(FollowUpVisits followUpVisits,UserInfo userInfo) {
		FollowUpVisits  oldFollowUpVisits = load(followUpVisits.getId()) ;
		
		oldFollowUpVisits.setId(followUpVisits.getId()) ;
		if(followUpVisits.getContent()!=null){
			oldFollowUpVisits.setContent(followUpVisits.getContent()) ;			
		}
		if(followUpVisits.getComment()!=null){
			oldFollowUpVisits.setComment(followUpVisits.getComment()) ;			
		}
		if(followUpVisits.getFollowUpDate()!=null){
			oldFollowUpVisits.setFollowUpDate(followUpVisits.getFollowUpDate()) ;			
		}
		//随访状态取消
		//if(followUpVisits.getStatus()!=null){
		//	oldFollowUpVisits.setStatus(followUpVisits.getStatus()) ;
		//	//处理随访 如果预约时间为空，表示普通处理
		//	if(followUpVisits.getStatus()==1&&followUpVisits.getContent()==null){
		//		//取出最后随访次数
		//		String status[] = new String[1] ;
		//		status[0] = "1" ;
		//		FollowUpVisits tmp = this.getLastFollowUpVisitsByStatus(followUpVisits, status) ;
		//	}			
		//}
		
		this.update(oldFollowUpVisits) ;
	}
	
	public FollowUpVisits getLastFollowUpVisitsByStatus(FollowUpVisits followUpVisits,String[] status){
		String tmp  = StringUtils.StringArrayToString(status, ",") ;

		StringBuffer sql = new StringBuffer(" select * from t_client_followup m where m.clientId = ?  ");
		sql.append(" and m.visitsCount = (SELECT max(visitsCount) from t_client_followup where clientId = m.clientId and `status` in ("+tmp+")) and m.`status` in ("+tmp+")  ") ;
		
		ArrayList args = new ArrayList();
		args.add(followUpVisits.getClientId()) ;
		
		List list = this.executeNativeQuery(sql.toString(),args.toArray(),FollowUpVisits.class) ;
		if(!CollectionUtils.isEmpty(list)){
			return (FollowUpVisits) list.get(0) ;
		}else{
			FollowUpVisits f = new FollowUpVisits() ;
			return f ;			
		}
	}

	public void addFollowUp(FollowUpVisits followUpVisits) {
		this.add(followUpVisits);
		}

	public void updateVisits(FollowUpVisits followUpVisits) {
		this.update(followUpVisits);
		
	}

	public PageObject queryFollowList(FollowUpVisits followUpVisits,
			QueryInfo queryInfo ,QueryCondition condition) {
		ArrayList args = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select c.followUpDate,c.content,c.comment,u.name,c.status,c.id,c.title from (select * from t_client_followup where 1=1 ");
		if(null != followUpVisits) {
			if(followUpVisits.getClientId()!=null){
				sql.append(" and clientId=? ");
				args.add(followUpVisits.getClientId());
			}
			if(followUpVisits.getUserId()!=null&&followUpVisits.getUserId()!=-1){
				sql.append(" and userId = ? ");
				args.add(followUpVisits.getUserId());
			}
			if(followUpVisits.getComment() != null&&!followUpVisits.getComment().equals("")){
				sql.append(" and comment like ? ");
				args.add("%"+followUpVisits.getComment().trim() +"%");
			}
			if(followUpVisits.getTitle() != null&&!followUpVisits.getTitle().equals("")){
				sql.append(" and title like ? ");
				args.add("%"+followUpVisits.getTitle().trim()+"%");
			}
			if(followUpVisits.getContent() != null&&!followUpVisits.getContent().equals("")){
				sql.append(" and content like ? ");
				args.add("%"+followUpVisits.getContent().trim()+"%");
			}
			if(followUpVisits.getStatus()!=null&&followUpVisits.getStatus()!=-1){
				sql.append(" and status = ? ");
				args.add(followUpVisits.getStatus());
			}
		}
		if(null != condition){
			if (condition != null) {
				if (condition.getBeginTime() != null) {
					sql.append(" and followUpDate >= ? ");
					args.add(condition.getBeginTime());
				}
				if (condition.getEndTime() != null) {
					sql.append(" and followUpDate <= ? ");
					args.add(condition.getEndTime());
				}
			}
		}
		sql.append(" ) c INNER JOIN t_userinfo u on c.userId=u.id ");
		
		sql.append(" order by c.followUpDate desc ");
		return this.queryObjectsBySql(sql.toString(), null, null, args.toArray(), queryInfo, FollowUpVisitsExtends.class);
	}

	public PageObject getFollowUpVisitsByTaskId(int taskid , QueryInfo queryInfo) {
		String sql="SELECT t2. NAME, t1.followUpDate, t1.content, t1. COMMENT FROM t_client_followup t1 LEFT JOIN t_userinfo t2 ON t1.userId = t2.id WHERE taskId = ? ORDER BY followUpDate ASC";
		return this.queryObjectsBySql(sql, taskid, queryInfo);
	}
	
	public List queryFollowUpList(Integer clientId,FollowUpVisits follow){
		List args = new ArrayList();
		String csql = "select t1.* from t_client_followup t1 where 1=1";
		if(follow != null){
			if(follow.getClientId() != null){
				csql += " and clientId = ?";
				args.add(follow.getClientId());
			}
			if(follow.getStatus()!=-1){
				csql += " and status = ?";
				args.add(follow.getStatus());
			}
			csql += " order by followUpDate desc";
		}else{
			if(clientId != null){
				csql += " and clientId = ?";
				args.add(clientId);
			}
		}
		
		String asql = "select name,id from t_userinfo";
		
		String sql = "select m.*,n.name userName from ("+csql+") m join ("+asql+") n on m.userId = n.id";
		
		Map entities = new LinkedHashMap();
		entities.put("m", FollowUpVisits.class);
		
		Map scalars = new LinkedHashMap();
		scalars.put("userName", StandardBasicTypes.STRING);
		
		return executeNativeQuery(sql, entities, scalars, args.toArray(), null);
		
	}

	public PageObject getFollowUpUserByClientId(Integer clientId) {
		String sql="SELECT DISTINCT t1.name,t1.id from t_client_followup t2 LEFT JOIN t_userinfo t1 on  t1.id= t2.userId where  t2.clientId = ?";
		return queryObjectsBySql(sql, clientId,null);
	}
	public void deleteFollowUp(Integer id){
		String sql = "delete from t_client_followup where id = ?";
		updateBySql(sql, id);
	}

	public FollowUpVisits getFollowById(Integer id) {
		return this.load(id);
	}
	
	public List<FollowUpVisits> queryFollowUp(FollowUpVisits follow){
		List args = new ArrayList();
		String hql = "from FollowUpVisits where 1 = 1";
		if(follow != null){
			if(follow.getClientId() != null){
				hql += " and clientId = ?";
				args.add(follow.getClientId());
			}
			if(follow.getStatus() != null){
				hql += " and status = ?";
				args.add(follow.getStatus());
			}
		}
		return executeFind(hql, args.toArray());
	}
}	
