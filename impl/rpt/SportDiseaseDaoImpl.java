package com.bskcare.ch.dao.impl.rpt;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.rpt.SportDiseaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.rpt.SportDisease;

@Repository
@SuppressWarnings("unchecked")
public class SportDiseaseDaoImpl extends BaseDaoImpl<SportDisease> implements
		SportDiseaseDao {

	public List<SportDisease> querySportDisease(SportDisease sd) {
		StringBuffer hql = new StringBuffer("from SportDisease s where 1=1");
		
		ArrayList args = new ArrayList();
		if(null != sd) {
			if(!StringUtils.isEmpty(sd.getDiseaseName())) {
				hql.append(" and (");
				
				String[] keywords = sd.getDiseaseName().split(Constant.RPT_TAG_SPLIT);
				for (int i = 0; i < keywords.length; i++) {
					hql.append(" s.diseaseName like ? ");
					args.add("%" + keywords[i] + "%");
					if (keywords.length - 1 != i) {
						hql.append(" or ");
					}
				}
				hql.append(")");
			}
			if(null != sd.getAgeBracket()) {
				hql.append(" and s.ageBracket = ?");
				args.add(sd.getAgeBracket());
			}
			if(null != sd.getDegree()) {
				hql.append(" and s.degree = ?");
				args.add(sd.getDegree());
			}
		}
		
		return executeFind(hql.toString(), args.toArray());
		
	}

	public PageObject<SportDisease> querySportDiseaseList(SportDisease disease,QueryInfo queryInfo) {
		String hql = "from SportDisease where 1=1";
		List args = new ArrayList();
		if(disease != null){
			if(!StringUtils.isEmpty(disease.getDiseaseName())){
				hql += " and diseaseName like ?";
				args.add("%"+disease.getDiseaseName().trim()+"%");
			}
			if(null != disease.getAgeBracket()){
				hql += " and ageBracket = ?";
				args.add(disease.getAgeBracket());
			}
		}
		return this.queryObjects(hql, args.toArray(), queryInfo);
	}
	
}
