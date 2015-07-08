package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.UnusualDataDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.UnusualData;
import com.bskcare.ch.vo.UnusualDataExtends;
@Repository
@SuppressWarnings("unchecked")
public class UnusualDataDaoImpl extends BaseDaoImpl<UnusualData> implements UnusualDataDao{

	public PageObject<UnusualDataExtends> queryUnusualDataList(UnusualDataExtends dataExtends,QueryCondition queryCondition,
			QueryInfo queryInfo) {
		ArrayList args = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select mud.*, c.`name`, c.gender, c.age, c.mobile, c.type levelId, c.areaId, c.bazzaarGrade,"
				+ " c.createTime as regTime from m_unusual_data mud " +
				" left join t_clientinfo c on mud.clientId = c.id " +
				" WHERE c.id is not NULL AND c.STATUS = ? ");
		
		args.add(Constant.STATUS_NORMAL);
		
		if(null != dataExtends) {
			if(!StringUtils.isEmpty(dataExtends.getName())) {
				sql.append(" and c.name like ? ");
				args.add("%" + dataExtends.getName() + "%");
			}
			if(!StringUtils.isEmpty(dataExtends.getMobile())) {
				sql.append(" and c.mobile like ? ");
				args.add("%" + dataExtends.getMobile().trim() + "%");
			}
			if(null != dataExtends.getGender() && -1 != dataExtends.getGender()) {
				sql.append(" and c.gender=? ");
				args.add(dataExtends.getGender());
			}
			if(null != dataExtends.getAge() && dataExtends.getAge() >= 0) {
				sql.append(" and c.age=? ");
				args.add(dataExtends.getAge());
			}
			if(!StringUtils.isEmpty(dataExtends.getLevelId())) {
				sql.append(" and c.type=? ");
				args.add(dataExtends.getLevelId());
			}
			if(null != dataExtends.getClientId()) {
				sql.append(" and c.id = ? ");
				args.add(dataExtends.getClientId());
			}
		}
		
		if(queryCondition != null){
			if(!StringUtils.isEmpty(queryCondition.getAreaChain())) {
				String[] areaChains = queryCondition.getAreaChain().split("#");
				sql.append(" and ( ");
				
				if(areaChains.length > 1){
					for (int i = 0; i < areaChains.length; i++) {
						sql.append(" c.areaChain like ? ");
						args.add(areaChains[i]+"%");
						if(i != areaChains.length - 1) {
							sql.append(" or");
						}
					}
				} else {
					sql.append(" c.areaChain like ? ");
					args.add(areaChains[0]+"%");
				}
				sql.append(")");
			}
			
			if (!StringUtils.isEmpty(queryCondition.getBazzaarGradeQuery())) {
				String bazz = queryCondition.getBazzaarGradeQuery();
				String[] bazzStr = bazz.split(",");
				sql.append(" and (");
				if (bazzStr.length > 1) {
					for (int i = 0; i < bazzStr.length; i++) {
						sql.append(" c.bazzaarGrade = ?");

						args.add(bazzStr[i]);

						if (i != bazzStr.length - 1) {
							sql.append(" or");
						}
					}
				} else {
					sql.append(" c.bazzaarGrade = ?");
					args.add(bazzStr[0]);
				}
				sql.append(")");
			}
		}
		
		if(null != dataExtends) {
			if(null != dataExtends.getStatus() && -1 != dataExtends.getStatus()) {
				sql.append(" and mud.status=? ");
				args.add(dataExtends.getStatus());
			}
			if(null != dataExtends.getType() && -1 != dataExtends.getType()) {
				sql.append(" and mud.type=? ");
				args.add(dataExtends.getType());
			}
		}
		if(queryCondition != null){
			if(queryCondition.getBeginTime() != null){
				sql.append(" and mud.uploadTime >= ? ");
				args.add(queryCondition.getBeginTime());
			}
			if(queryCondition.getEndTime() != null){
				sql.append(" and mud.uploadTime <= ? ");
				args.add(queryCondition.getEndTime());
			}
		}
		sql.append(" order by mud.id desc");
		return this.queryObjectsBySql(sql.toString(), null, null, args.toArray(), queryInfo, UnusualDataExtends.class);
	}

	public List<UnusualData> queryRecentOnceDataList(Integer clientId) {
		StringBuffer sql = new StringBuffer();
		ArrayList args = new ArrayList();
		sql.append("select * from (select * from `m_unusual_data` where 1=1 ");
		if(null != clientId) {
			sql.append(" and clientId=? ");
			args.add(clientId);
		}
		sql.append(" order by type asc,uploadTime desc) mud group by type ");
		return this.executeNativeQuery(sql.toString(), null, null, args.toArray(), UnusualData.class);
	}

	public void flagUnusualData(Integer dataId, Integer type, Integer clientId,
			int handled) {
		String sql = "update m_unusual_data set status = ?" +
				" where dataId = ? and clientId = ? and type = ?";
		
		this.updateBySql(sql, new Object[]{handled, dataId, clientId, type});
		
	}

	public int disposeByDayTime(int type, Integer clientId, Date testDate) {
		StringBuffer sql = new StringBuffer("UPDATE m_unusual_data SET status = ? where " +
				" clientId = ? and status = ? and type = ? and testTime <= ? and testTime >= ?");

		ArrayList args = new ArrayList();
		args.add(UnusualData.HANDLED); //已处理状态
		args.add(clientId) ;
		args.add(UnusualData.UNHANDLE);
		args.add(type);
		args.add(testDate) ;
		args.add(DateUtils.getDateByType(testDate, "yyyy-MM-dd")) ;
		
		return this.updateBySql(sql.toString(),args.toArray()) ;
	}

	
	public int queryUnusualDataList(UnusualDataExtends dataExtends, String areaChain) {
		ArrayList args = new ArrayList();
		StringBuffer sql = new StringBuffer();

		sql.append("select count(*) from t_clientinfo m inner join m_unusual_data n on m.id = n.clientId ");
		sql.append(" where m.status = ? ");
		
		args.add(Constant.STATUS_NORMAL);
		
		if(!StringUtils.isEmpty(areaChain)) {
			String[] areaChains = areaChain.split("#");
			sql.append(" and ( ");
			
			if(areaChains.length > 1){
				for (int i = 0; i < areaChains.length; i++) {
					sql.append(" m.areaChain like ? ");
					args.add(areaChains[i]+"%");
					if(i != areaChains.length - 1) {
						sql.append(" or");
					}
				}
			} else {
				sql.append(" m.areaChain like ? ");
				args.add(areaChains[0]+"%");
			}
			sql.append(")");
		}
		
		if(null != dataExtends) {
			if(null != dataExtends.getStatus() && -1 != dataExtends.getStatus()) {
				sql.append(" and n.status=? ");
				args.add(dataExtends.getStatus());
			}
			if(null != dataExtends.getType() && -1 != dataExtends.getType()) {
				sql.append(" and n.type=? ");
				args.add(dataExtends.getType());
			}
		}
		Object obj = findUniqueResultByNativeQuery(sql.toString(), args.toArray());
		if(obj != null && !obj.equals("")){
			return Integer.parseInt(obj.toString());
		}
		return 0;
	}
	
}
