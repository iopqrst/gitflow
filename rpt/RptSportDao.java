package com.bskcare.ch.dao.rpt;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.rpt.RptSport;

public interface RptSportDao extends BaseDao<RptSport>{
	
	/**
	 * 根据用户id和健康报告id查询运动信息
	 */
	public RptSport findRptSport(Integer rptId, Integer clientId);

	/**
	 * 根据字段修改内容值
	 * @param field 字段名称
	 * @param id 数据id
	 */
	public int updateSportByFields(String field, Integer id);
	
	/**根据健康报告id删除信息**/
	public void deleteSportByRptId(Integer rptId);
}
