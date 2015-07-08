package com.bskcare.ch.dao.impl.rpt;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.rpt.RptKnowledgeRepoDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.rpt.RptKnowledgeRepo;

@Repository
@SuppressWarnings("unchecked")
public class RptKnowledgeRepoDaoImpl extends BaseDaoImpl<RptKnowledgeRepo>
		implements RptKnowledgeRepoDao {

	public List<RptKnowledgeRepo> queryKnowledge(RptKnowledgeRepo kr) {
		QueryInfo queryInfo = new QueryInfo();
		queryInfo.setPageOffset(0);
		queryInfo.setPageSize(999999999);
		List<RptKnowledgeRepo> list = queryTitleKnowTag(kr, queryInfo).getDatas();
		return list;
	}

	public List<String> queryTags(String node) {
		String sql = "select DISTINCT(tag) from rpt_knowledge_repo "
				+ " where tag is not null and tag != ''";

		ArrayList args = new ArrayList();
		if (!StringUtils.isEmpty(node)) {
			sql += " and node = ?";
			args.add(node);
		}

		return executeNativeQuery(sql, args.toArray());

	}

	public PageObject<RptKnowledgeRepo> queryTitleKnowTag(RptKnowledgeRepo kr,
			QueryInfo queryInfo) {
		StringBuffer hql = new StringBuffer("from RptKnowledgeRepo r where 1 = 1 ");

		ArrayList args = new ArrayList();

		if (null != kr) {
			if (!StringUtils.isEmpty(kr.getTag())) {
				hql.append(" and (");

				String[] keywords = kr.getTag().split(Constant.RPT_TAG_SPLIT);
				for (int i = 0; i < keywords.length; i++) {
					hql.append(" r.tag like ? ");
					args.add("%" + keywords[i].trim() + "%");

					if (keywords.length - 1 != i) {
						hql.append(" and ");
					}
				}
				hql.append(")");
			}

			if (!StringUtils.isEmpty(kr.getNode())) {
				hql.append(" and r.node = ?");
				args.add(kr.getNode());
			}

			if (!StringUtils.isEmpty(kr.getTitle())) {
				hql.append(" and r.title like ?");
				args.add("%" + kr.getTitle().trim() + "%");
			}

			if (null != kr.getCategory()) {
				hql.append(" and r.category = ?");
				args.add(kr.getCategory());
			}

			if (null != kr.getSubCategory()) {
				hql.append(" and r.subCategory = ?");
				args.add(kr.getSubCategory());
			}

			if (null != kr.getThirdCategory()) {
				hql.append(" and r.thirdCategory = ? ");
				args.add(kr.getThirdCategory());
			}
		}

		return this.queryObjects(hql.toString(), args.toArray(), queryInfo);
	}

}