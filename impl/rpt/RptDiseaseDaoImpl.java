package com.bskcare.ch.dao.impl.rpt;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.rpt.RptDiseaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.RptDisease;

@Repository
public class RptDiseaseDaoImpl extends BaseDaoImpl<RptDisease> implements
		RptDiseaseDao {

	public List<RptDisease> queryDisease(String medical) {
		String hql = "from RptDisease where 1 = 1";
		if (!StringUtils.isEmpty(medical)) {
			String[] mms = medical.split(Constant.RPT_TAG_SPLIT);
			String real = "";
			for (int i = 0; i < mms.length; i++) {
				real += "'" + mms[i] + "'";
				if (i != mms.length - 1) {
					real += ",";
				}
			}

			hql += " and name in (" + real + ")";
			return this.executeFind(hql);
		} else {
			return null;
		}
	}

	public List<RptDisease> queryAllDisease() {
		String hql = "from RptDisease order by sort asc";
		return executeFind(hql);
	}

	/**
	 * 分页查询疾病列表
	 */
	public PageObject<RptDisease> querypagerObject(QueryInfo queryInfo,
			RptDisease disease) {
		String hql = "from RptDisease";
		return queryObjects(hql, null, queryInfo);
	}

	/**
	 * 添加疾病
	 */
	public void addDisease(RptDisease disease) {
		this.add(disease);
	}

	public RptDisease loadDisease(Integer integer) {
		return this.load(integer);
	}

	public void updateDisease(RptDisease disease) {
		this.update(disease);
	}

	public void deleteDisease(Integer integer) {
		this.delete(integer);

	}

	@SuppressWarnings("unchecked")
	public List<RptDisease> queryAllDisease(RptDisease disease) {
		String hql = "from RptDisease where 1 = 1 ";
		List args = new ArrayList();
		if (disease != null) {
			if (disease.getName() != null) {
				hql += " and name = ? ";
				args.add(disease.getName());
			}
			if (disease.getSpell() != null) {
				hql += " and spell = ? ";
				args.add(disease.getSpell());
			}
			if (disease.getIsFamily() != null) {
				hql += " and isFamily = ? ";
				args.add(disease.getIsFamily());
			}
			if (disease.getIsSelf() != null) {
				hql += " and isSelf = ? ";
				args.add(disease.getIsSelf());
			}
		}
		return executeFind(hql, args.toArray());
	}

	public String getIdByDiseaseName(String diseaseName) {
		String hql = "from RptDisease where name=?";
		Object[] objects = { diseaseName };
		List<RptDisease> list = executeFind(hql, objects);
		RptDisease disease = new RptDisease();
		if (!CollectionUtils.isEmpty(list)) {
			disease = list.get(0);
		}
		return disease.getId() == null ? "" : String.valueOf(disease.getId());
	}

	public List<RptDisease> queryRptDiseaseSelf() {
		String hql = "from RptDisease where isSelf = 0";
		return executeFind(hql);
	}

	public List<RptDisease> queryRptDiseaseFamily() {
		String hql = "from RptDisease where isFamily = 0";
		return executeFind(hql);
	}



}
