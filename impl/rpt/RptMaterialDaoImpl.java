package com.bskcare.ch.dao.impl.rpt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.rpt.RptMaterialDao;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.rpt.RptMaterial;

@Repository
@SuppressWarnings("unchecked")
public class RptMaterialDaoImpl extends BaseDaoImpl<RptMaterial> implements
		RptMaterialDao {

	public List<RptMaterial> queryMaterialByKeywords(RptMaterial rm) {
		return queryMaterialByKeywords(rm, null, 0);
	}

	public List<RptMaterial> queryMaterialByKeywords(RptMaterial rm,
			String join, int index) {
		// System.out.println(rm.getTag());
		if (StringUtils.isEmpty(join)) {
			join = "and";
		}
		
		StringBuffer hql = new StringBuffer("from RptMaterial where 1 = 1 ");
		ArrayList args = new ArrayList();
		
		if (!StringUtils.isEmpty(rm.getNode())) {
			hql.append(" and node = ?");
			args.add(rm.getNode());
		}
		
		if (!StringUtils.isEmpty(rm.getTag())) {
			String[] keywords = rm.getTag().split(Constant.RPT_TAG_SPLIT);
			hql.append(" and (");
			for (int i = 0; i < keywords.length; i++) {
				if (join.equals("and")) {
					hql.append(" tag like ? ");
					args.add("%" + keywords[i] + "%");
				} else if (join.equals("or")) {
					hql.append(" tag = ? ");
					args.add(keywords[i]);
				}
				if (keywords.length - 1 != i) {
					hql.append(" " + join + " ");
				}
			}
			hql.append(")");
		} else {
			hql.append(" and (tag is null or tag = '')");
		}
		
		List<RptMaterial> list = executeFind(hql.toString(), args.toArray());
		
		if (CollectionUtils.isEmpty(list) && index == 0) {
			return queryMaterialByKeywords(rm, "or", 1);
		}
		
		return list;
	}

}
