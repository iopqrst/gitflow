//package com.bskcare.ch.dao.impl;
//
//import java.util.List;
//
//import org.springframework.stereotype.Repository;
//
//import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
//import com.bskcare.ch.dao.RegSourceDao;
//import com.bskcare.ch.util.StringUtils;
//import com.bskcare.ch.vo.RegSource;
//
//@Repository
//@SuppressWarnings("unchecked")
//public class RegSourceDaoImpl extends BaseDaoImpl<RegSource> implements
//		RegSourceDao {
//
//	public List queryStatisticList(String timeType) {
//		String mSql = "select rs.source,count(*) count from ( ";
//		String cSql = "select * from t_reg_source where 1=1 ";
//		if (!StringUtils.isBlank(timeType)) {
//			if (timeType.equals("1")) {
//				cSql += " and date(date_format(createTime,'%Y-%m-%d'))=date(now()) ";// 今天
//			}
//			if (timeType.equals("2")) {
//				cSql += " and WEEK(date_format(createTime,'%Y-%m-%d'), 1) = YEARWEEK(now()) ";// 本周
//			}
//			if (timeType.equals("3")) {
//				cSql += " and DATE_FORMAT(createTime, '%Y%m') = DATE_FORMAT(CURDATE() ,'%Y%m') ";// 当月
//			}
//			if (timeType.equals("4")) {
//				cSql += " and YEAR(createTime)=YEAR(NOW()) ";// 当年
//			}
//		}
//		mSql += cSql + ") rs ";
//		mSql += " group by rs.source";
//		return executeNativeQuery(mSql);
//	}
//
//}
