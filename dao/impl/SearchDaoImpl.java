package com.bskcare.ch.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.SearchDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.SearchDiseaseVo;
import com.bskcare.ch.vo.search.FlVo;
import com.bskcare.ch.vo.search.XyVo;
import com.bskcare.ch.vo.search.ZcyVo;
import com.bskcare.ch.vo.search.Zy1Vo;
import com.bskcare.ch.vo.search.ZyVo;
@Repository("searchDao")
public class SearchDaoImpl extends BaseDaoImpl<SearchDiseaseVo>  implements SearchDao {


	public PageObject<SearchDiseaseVo> getSssList(String con,SearchDiseaseVo sss,QueryInfo queryInfo) {

		StringBuffer sb=new StringBuffer("");
		sb.append("select {searchDiseaseVo.*} from(select '1' as tab,ID,CYPMC as a,CSYZ as b,CYXX as c,CYDX as d, CBLFY as e,CJJZ as f,CZYSX as g,");
		sb.append("CXHZY as h,CGYSM as i,");
		sb.append("CYFYL as j,CZJYGG as k,'' as l,'' as m,''as n,'' as o,'' as p from t_drug_xy");
		sb.append("  union all  ").append(
				"select '2',ID, CYPMC,CYWZC,CGNZZ,CFJ,CLCYY,CYLDL,CBLFY,CZYSX,CYFYL,CGG,CCKWX,'','','','','' from t_drug_zcy").append(
				"  union all  ").append(
				"select '4',ID, CYPMC,CYCMS,CYCXW,CYLZY,CGX,CYCGJ,CZZJB,CZYCF,CKB,CLB,CYLMS,CCDMS,CXYP,CYCXZ,CYCGG,CYCJB from t_drug_zy").append(
				"  union all  ").append(
				"select '3',ID, CYPMC,CGNZZ,CYPMC1,CXW,CSZD,CHXCF,CCZ,CLY,CZWXT1,CZWXT2,CYWMC,CFZ,'','','','' from t_drug_zy1").append(
				"  union all  ").append(
				"select '5',ID,CYPMB,CFJCC,CFJZC,CFJKJ,CFJGY,CFJZC1,CFJYF,CFJJJ,CFJFJ,CFJHZ,CFJFZ,CFJFZ1,CFJWX,CFJYJ,CFJYY,''").append(
				"from t_drug_fl) searchDiseaseVo").append(
				" where a like '%"+con+"%' or b like '%"+con+"%' or c like '%"+con+"%'").append(
				"or d like '%"+con+"%' or e like '%"+con+"%' or f like '%"+con+"%' or g like '%"+con+"%' or h like '%"+con+"%'").append(
				"or i like '%"+con+"%' or j like '%"+con+"%' or k like '%"+con+"%' or l like '%"+con+"%' or m like '%"+con+"%'").append(
			    "or n like '%"+con+"%' or o like '%"+con+"%' or p like '%"+con+"%'");
		Map entities = new HashMap();
		entities.put("searchDiseaseVo", SearchDiseaseVo.class);
		PageObject<SearchDiseaseVo> page = this.queryObjectsBySql(sb.toString(),null,entities,queryInfo) ;
		return page;
	}

		 
		 
	
	
}
