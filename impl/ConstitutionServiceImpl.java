package com.bskcare.ch.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.CmcAftercareDao;
import com.bskcare.ch.dao.CmcAnalysisDao;
import com.bskcare.ch.dao.CmcTaskDao;
import com.bskcare.ch.dao.ConstitutionDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.ConstitutionService;
import com.bskcare.ch.service.OrderServiceItemService;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.util.MoneyFormatUtil;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.util.SystemConfig;
import com.bskcare.ch.vo.CmcAftercare;
import com.bskcare.ch.vo.CmcAnalysis;
import com.bskcare.ch.vo.CmcTask;
import com.bskcare.ch.vo.Constitution;
import com.bskcare.ch.vo.order.ServiceExpense;

@Service("constitutionService")
@SuppressWarnings("unchecked")
public class ConstitutionServiceImpl implements ConstitutionService {

	@Autowired
	private ConstitutionDao constitutionDao;
	@Autowired
	private CmcAftercareDao cmcAftercareDao;
	@Autowired
	private CmcAnalysisDao cmcAnalysisDao;
	@Autowired
	private CmcTaskDao cmcTaskDao;
	@Autowired
	private OrderServiceItemService serviceItemService;

	public Constitution addConstitution(int cid, String param, String answer) {

		String mainConstitution = ""; // 主体质
		String probablyConstitution = ""; // 兼有体质
		String tendencyConstitution = ""; // 倾向体质
		Constitution cons = null;
		if (!StringUtils.isEmpty(param)) {
			Map<String, String> categoryMap = new HashMap<String, String>();
			categoryMap = JsonUtils.getMap4Json(param);

			// 平和质
			double pinghe = MoneyFormatUtil.formatDouble(((Double
					.parseDouble(categoryMap.get("pinghe"))) - 8)
					/ (8 * 4) * 100);
			// 气虚质
			double qixu = MoneyFormatUtil.formatDouble(((Double
					.parseDouble(categoryMap.get("qixu"))) - 8)
					/ (8 * 4) * 100);
			// 阳虚质
			double yangxu = MoneyFormatUtil.formatDouble(((Double
					.parseDouble(categoryMap.get("yangxu"))) - 7)
					/ (7 * 4) * 100);
			// 阴虚质
			double yinxu = MoneyFormatUtil.formatDouble(((Double
					.parseDouble(categoryMap.get("yinxu"))) - 8)
					/ (8 * 4) * 100);
			// 痰湿质
			double tanshi = MoneyFormatUtil.formatDouble(((Double
					.parseDouble(categoryMap.get("tanshi"))) - 8)
					/ (8 * 4) * 100);
			// 湿热质
			double shire = MoneyFormatUtil.formatDouble(((Double
					.parseDouble(categoryMap.get("shire"))) - 6)
					/ (6 * 4) * 100);
			// 血瘀质
			double xueyu = MoneyFormatUtil.formatDouble(((Double
					.parseDouble(categoryMap.get("xueyu"))) - 7)
					/ (7 * 4) * 100);
			// 气郁质
			double qiyu = MoneyFormatUtil.formatDouble(((Double
					.parseDouble(categoryMap.get("qiyu"))) - 7)
					/ (7 * 4) * 100);
			// 特禀质
			double tebing = MoneyFormatUtil.formatDouble(((Double
					.parseDouble(categoryMap.get("tebing"))) - 7)
					/ (7 * 4) * 100);

			String[] keys = new String[8];
			Double[] values = new Double[8];
			keys[0] = "气虚质";
			values[0] = qixu;

			keys[1] = "阳虚质";
			values[1] = yangxu;

			keys[2] = "阴虚质";
			values[2] = yinxu;

			keys[3] = "痰湿质";
			values[3] = tanshi;

			keys[4] = "湿热质";
			values[4] = shire;

			keys[5] = "血瘀质";
			values[5] = xueyu;

			keys[6] = "气郁质";
			values[6] = qiyu;

			keys[7] = "特禀质";
			values[7] = tebing;

			if (pinghe >= 60) {
				int f = 0; // '是'和平质标识
				int f2 = 0; // '基本是'和平质标识
				for (int i = 0; i < 8; i++) {
					if (values[i] < 30) {
						f++;
					} else if (values[i] < 40) {
						f2++;
					} else {
						break; // 8种体质之一大于40分都不能评定为平和质
					}
				}
				if (f == 8) {
					mainConstitution = "平和质";
				} else if ((f + f2) == 8) {
					mainConstitution = "平和质";
				} else {
					// 8种体质以最高得为主体质.
					// 降序排序
					quickSort(keys, values, 0, 7);

					// 倾向体质
					StringBuilder sb_Tendency = new StringBuilder("");
					// 兼有体质
					StringBuilder sb_Probably = new StringBuilder("");
					for (int i = 0; i < 8; i++) {
						// 主体质
						if (i == 0 && values[i] >= 40) {
							mainConstitution = keys[i];
						}
						// 兼有体质
						if (i != 0 && values[i] >= 40) {
							sb_Probably.append(keys[i]);
							sb_Probably.append(",");
						}
						// 倾向体质
						if (30 <= values[i] && values[i] < 40) {
							sb_Tendency.append(keys[i]);
							sb_Tendency.append(",");
						}
					}
					// 兼有体质
					if (sb_Probably.toString().length() > 0) {
						probablyConstitution = sb_Probably.substring(0,
								sb_Probably.toString().length() - 1);
					}
					if (sb_Tendency.toString().length() > 0) {
						// 倾向体质
						tendencyConstitution = sb_Tendency.substring(0,
								sb_Tendency.toString().length() - 1);
					}
				}
			} else {
				// 8种体质以最高得为主体质.
				// 降序排序
				quickSort(keys, values, 0, 7);

				// 倾向体质
				StringBuilder sb_Tendency = new StringBuilder("");
				// 兼有体质
				StringBuilder sb_Probably = new StringBuilder("");
				for (int i = 0; i < 8; i++) {
					// 主体质
					if (i == 0 && values[i] >= 40) {
						mainConstitution = keys[i];
					}
					// 兼有体质
					if (i != 0 && values[i] >= 40) {
						sb_Probably.append(keys[i]);
						sb_Probably.append(",");
					}
					// 倾向体质
					if (30 <= values[i] && values[i] < 40) {
						sb_Tendency.append(keys[i]);
						sb_Tendency.append(",");
					}
				}
				// 兼有体质
				if (sb_Probably.toString().length() > 0) {
					probablyConstitution = sb_Probably.substring(0, sb_Probably
							.toString().length() - 1);
				}
				if (sb_Tendency.toString().length() > 0) {
					// 倾向体质
					tendencyConstitution = sb_Tendency.substring(0, sb_Tendency
							.toString().length() - 1);
				}
			}
			cons = new Constitution();
			cons.setAnswer(answer);
			cons.setClientId(cid);
			cons.setMainConstitution(mainConstitution);
			cons.setPinghe(pinghe);
			cons.setProbablyConstitution(probablyConstitution);
			cons.setQixu(qixu);
			cons.setTestTime(new Date());
			cons.setYangxu(yangxu);
			cons.setYinxu(yinxu);
			cons.setTanshi(tanshi);
			cons.setShire(shire);
			cons.setXueyu(xueyu);
			cons.setQiyu(qiyu);
			cons.setTebing(tebing);
			cons.setTendencyConstitution(tendencyConstitution);
			cons = constitutionDao.addConstitution(cons);
			
			String cmcItem = Constant.ITEM_CMC;
			int result = serviceItemService.expenseService(cmcItem, cons.getClientId(), 1, ServiceExpense.NEED_RECORD);
			if( Constant.EXPENSE_SERVICE_SUC == result) {
				CmcTask cmcTask = new CmcTask();
				cmcTask.setClientId(cons.getClientId());
				cmcTask.setCeId(cons.getId());
				cmcTask.setCreateTime(cons.getTestTime());
				cmcTask.setStatus(CmcTask.CMC_TASK_NOT_DEAL);
				cmcTaskDao.add(cmcTask);
			}

		}

		return cons;
	}
	
	private void quickSort(String[] keys, Double[] values, int first, int end) {

		int i = first, j = end;

		double temp = values[first];
		String keyTemp = keys[first];
		while (i < j) {
			while (i < j && values[j] <= temp) {
				j--;
			}
			values[i] = values[j];
			keys[i] = keys[j];
			while (i < j && values[i] >= temp) {
				i++;
			}
			values[j] = values[i];
			keys[j] = keys[i];
		}
		values[i] = temp;
		keys[i] = keyTemp;
		if (first < i - 1) {
			quickSort(keys, values, first, i - 1);
		}
		if (end > i + 1) {
			quickSort(keys, values, i + 1, end);

		}

	}

	/**
	 * 查询所有的体质建检测信息
	 */
	public String selectConstitution(int clientId, QueryInfo queryInfo) {
		JSONObject jo = new JSONObject();
		PageObject<Constitution> poCons = constitutionDao.selectConstitution(
				clientId, queryInfo);
		List<Constitution> lstCons = poCons.getDatas();
		jo.put("total", poCons.getTotalRecord());
		jo.put("lstCons", JsonUtils.getJONSArray4JavaList(lstCons));
		return jo.toString();

	}

	/**
	 * 根据用户id和检测的id查看问卷详细信息
	 */
	public Constitution selectConstitutionById(int clientId, int id) {
		return constitutionDao.selectConstitutionById(clientId, id);
	}

	public Constitution selectConstitutionByIds(int clientId, int id) {
		return constitutionDao.selectConstitutionById(clientId, id);

	}
	
	public CmcAnalysis selectCmcAnalysisByName(String name){
		return cmcAnalysisDao.selectCmcAnalysisByName(name);
	}
	
	public CmcAftercare selectAfterCareByName(String name){
		return cmcAftercareDao.selectAfterCareByName(name);
	}

	public PageObject selectConstitutionBycId(Integer clientId, QueryInfo queryInfo){
		return constitutionDao.selectConstitution(clientId, queryInfo);
	}
	
	public List<Constitution> findCmcBycId(Integer clientId){
		List<Constitution> lstCmc = constitutionDao.findCmcBycId(clientId);
		if(lstCmc!=null){
			return lstCmc;
		}else{
			return null;
		}
	}
	
}
