package com.bskcare.ch.service.impl.rpt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.dao.rpt.SrptDietPrincipleDao;
import com.bskcare.ch.dao.rpt.SrptLifePrincipleDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.rpt.SrptDietPrincipleService;
import com.bskcare.ch.util.ArrayUtils;
import com.bskcare.ch.util.CombinationUtil;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.rpt.SrptDietPrinciple;
import com.bskcare.ch.vo.rpt.SrptLifePrinciple;

@Service
@SuppressWarnings("unchecked")
public class SrptDietPrincipleServiceImpl implements SrptDietPrincipleService {

	@Autowired
	private SrptDietPrincipleDao dietPrincipleDao;
	@Autowired
	private SrptLifePrincipleDao lifePrincipleDao;

	public SrptDietPrinciple addRptDietPriciple(
			SrptDietPrinciple dietPrinciple, String intro, String detail) {
		SrptDietPrinciple diet = null;
		if (dietPrinciple != null && !StringUtils.isEmpty(intro)
				&& !StringUtils.isEmpty(detail)) {
			String[] intros = intro.split(",");
			String[] details = detail.split(",");
			List<Integer> lstLife = new ArrayList<Integer>();
			if ((intros != null && intros.length > 0)
					&& (details != null && details.length > 0)) {
				for (int i = 0; i < intros.length; i++) {
					SrptLifePrinciple life = new SrptLifePrinciple();
					life.setIntro(intros[i]);
					life.setDetail(details[i]);
					SrptLifePrinciple lifePrinciple = lifePrincipleDao
							.add(life);
					// 添加之后得到的id
					if (lifePrinciple != null) {
						lstLife.add(lifePrinciple.getId());
					}
				}
			}
			if (!CollectionUtils.isEmpty(lstLife)) {
				String lifePrinciple = ArrayUtils.join(lstLife.toArray(), ",");
				dietPrinciple.setLifePrinciple(lifePrinciple);
				diet = dietPrincipleDao.add(dietPrinciple);
			}
		} else if (dietPrinciple != null) {
			diet = dietPrincipleDao.add(dietPrinciple);
		}
		return diet;
	}

	public PageObject queryDietPrinciple(SrptDietPrinciple dietPrinciple,
			QueryInfo queryInfo) {
		return dietPrincipleDao.queryDietPrinciple(dietPrinciple, queryInfo);
	}

	public SrptDietPrinciple queryDietPrincipleById(Integer id) {
		return dietPrincipleDao.load(id);
	}

	public void deleteDietPrinciple(Integer id) {
		dietPrincipleDao.delete(id);
	}

	public void updateDietPrinciple(SrptDietPrinciple dietPrinciple,
			String intro, String detail, String lifeInfo) {
		if (dietPrinciple != null) {
			if (!StringUtils.isEmpty(intro) && !StringUtils.isEmpty(detail)
					&& !StringUtils.isEmpty(lifeInfo)) {
				String[] details = detail.split(",");
				String[] intros = intro.split(",");
				String[] lifeInfos = lifeInfo.split(",");
				List<Integer> lstLife = new ArrayList<Integer>();
				for (int i = 0; i < lifeInfos.length; i++) {
					SrptLifePrinciple lifePrin = new SrptLifePrinciple();
					Integer id = Integer.parseInt(lifeInfos[i]);
					lifePrin.setId(id);
					lifePrin.setIntro(intros[i]);
					lifePrin.setDetail(details[i]);
					lifePrincipleDao.update(lifePrin);
					lstLife.add(id);
				}

				for (int i = lifeInfos.length; i < intros.length; i++) {
					SrptLifePrinciple life = new SrptLifePrinciple();
					life.setIntro(intros[i]);
					life.setDetail(details[i]);
					SrptLifePrinciple lifePrinciple = lifePrincipleDao
							.add(life);
					if (lifePrinciple != null) {
						lstLife.add(lifePrinciple.getId());
					}
				}
				if (!CollectionUtils.isEmpty(lstLife)) {
					String lifePrinciple = ArrayUtils.join(lstLife.toArray(),
							",");
					dietPrinciple.setLifePrinciple(lifePrinciple);
					dietPrincipleDao.update(dietPrinciple);
				}
			} else if (!StringUtils.isEmpty(intro)
					&& !StringUtils.isEmpty(detail)) {
				String[] intros = intro.split(",");
				String[] details = detail.split(",");
				List<Integer> lstLife = new ArrayList<Integer>();
				if ((intros != null && intros.length > 0)
						&& (details != null && details.length > 0)) {
					for (int i = 0; i < intros.length; i++) {
						SrptLifePrinciple life = new SrptLifePrinciple();
						life.setIntro(intros[i]);
						life.setDetail(details[i]);
						SrptLifePrinciple lifePrinciple = lifePrincipleDao
								.add(life);
						if (lifePrinciple != null) {
							lstLife.add(lifePrinciple.getId());
						}
					}
				}
				if (!CollectionUtils.isEmpty(lstLife)) {
					String lifePrinciple = ArrayUtils.join(lstLife.toArray(),
							",");
					dietPrinciple.setLifePrinciple(lifePrinciple);
					dietPrincipleDao.update(dietPrinciple);
				}
			} else {
				dietPrincipleDao.update(dietPrinciple);
			}
		}
	}

	public List<SrptDietPrinciple> queryDietPrincipleByDisease(
			String diseaseName) {
		return dietPrincipleDao.queryDietPrincipleByDisease(diseaseName);
	}

	public SrptDietPrinciple queryDietPrincipleByName(String diseaseName) {
		return dietPrincipleDao.queryDietPrincipleByName(diseaseName);
	}

	/**
	 * 用法：String diseaseStr = "肝病,肿瘤,脑血栓,肥胖,甲亢";
	 * 程序根据传入字符串diseaseStr内容，先组合，再模糊查询得到一个集合， 最后过滤组成新的N+1或多个单病种集合，N表示复合病，1表示单病种
	 */
	public List<SrptDietPrinciple> queryComplexDisease(String diseaseStr) {
		
		if(StringUtils.isEmpty(diseaseStr)){
			diseaseStr = "正常";
		}
		
		// 判断疾病是否重复，是否空
		String []temp = diseaseStr.split(",");
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < temp.length; i++) {
			if (!StringUtils.isEmpty(temp[i]) && !list.contains(temp[i])) {
				list.add(temp[i]);
			} 
		}
		String diseaseName = "";
		for (String str : list) {
			diseaseName += str + ",";
		}
		List<SrptDietPrinciple> sdpList = getDisese(diseaseName);
		for (SrptDietPrinciple scp : sdpList) {
			System.out.println(scp.getDisease());
		}
		return sdpList;
	}

	public List<SrptDietPrinciple> getDisese(String diseaseStr) {
		List<String> multiList = null; // 复合病组合
		List<SrptDietPrinciple> principleList = null;// 模糊匹配复合病集合
		List<SrptDietPrinciple> pcpList = new ArrayList<SrptDietPrinciple>();// 过滤后的潜在复合病集合
		boolean cnFlag = true; // 是否要向下查询
		String dn[] = diseaseStr.split(",");// 疾病数组
		CombinationUtil diseaseUtil = new CombinationUtil();
		if (null != dn && dn.length > 1) {
			for (int i = dn.length; i > 1; i--) {
				if (cnFlag) {
					multiList = diseaseUtil.combine(dn, i);// 计算复合病集合
				}
				if (!CollectionUtils.isEmpty(multiList) && cnFlag) {
					for (String str : multiList) {// 循环查询模糊匹配复合病集合
						if (cnFlag) {
							principleList = dietPrincipleDao
									.queryDietPrincipleByDisease(str);
							if (!CollectionUtils.isEmpty(principleList)) {
								for (SrptDietPrinciple p : principleList) {
									if (cnFlag) {// 过滤重复复合病
										// 过滤错误的复合病
										if (p.getDisease().split(",").length == str
												.split(",").length) {
											pcpList.add(p);
											// 计算出单病种或复合病
											String[] temp = p.getDisease()
													.split(",");
											List<String> tempList = new ArrayList<String>();
											for (int m = 0; m < temp.length; m++) {
												tempList.add(temp[m]);
											}
											String remainDisease = "";
											for (int j = 0; j < dn.length; j++) {
												if (!tempList.contains(dn[j])) {
													remainDisease += dn[j].trim() + ",";
												}
											}
											// 查询剩余病复合病情况
											if (!StringUtils
													.isEmpty(remainDisease)) {
												// 将剩余1个以上疾病递归查询
												List<SrptDietPrinciple> sdpList = getDisese(remainDisease);
												if (!CollectionUtils
														.isEmpty(sdpList)) {
													for (SrptDietPrinciple sdiet : sdpList) {
														pcpList.add(sdiet);
													}
												}
											}
											cnFlag = false;
										}
									}
								}
							} else {
								cnFlag = true;
							}
						}
					}
				}
			}
		} else {
			// N多复合病+1个单种病情况
			SrptDietPrinciple sdp = dietPrincipleDao
					.queryDietPrincipleByName(dn[0]);
			if (null != sdp) {
				pcpList.add(sdp);
			}
		}
		// 没有复合病情况，查询所有单病种
		if (CollectionUtils.isEmpty(pcpList)) {
			for (int i = 0; i < dn.length; i++) {
				SrptDietPrinciple sdp = dietPrincipleDao
						.queryDietPrincipleByName(dn[i].trim());
				if (null != sdp) {
					pcpList.add(sdp);
				}
			}
		}
		return pcpList;
	}

}
