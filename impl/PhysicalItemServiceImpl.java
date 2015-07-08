package com.bskcare.ch.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.dao.ClientPhysicalDao;
import com.bskcare.ch.dao.PhysicalDetailDao;
import com.bskcare.ch.dao.PhysicalItemDao;
import com.bskcare.ch.dao.PhysicalStandardDao;
import com.bskcare.ch.dao.PhysicalSuggestionDao;
import com.bskcare.ch.service.PhysicalItemService;
import com.bskcare.ch.service.PhysicalStandardService;
import com.bskcare.ch.util.DateJsonValueProcessor;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.vo.client.ClientPhysical;
import com.bskcare.ch.vo.client.PhysicalDetail;
import com.bskcare.ch.vo.client.PhysicalItem;
import com.bskcare.ch.vo.client.PhysicalSuggestion;

@Service
public class PhysicalItemServiceImpl implements PhysicalItemService {
	@Autowired
	private PhysicalItemDao physicalItemDao;
	@Autowired
	private PhysicalDetailDao physicalDetailDao;
	@Autowired
	private ClientPhysicalDao physicalDao;

	@Autowired
	private PhysicalStandardDao physicalStandardDao;

	@Autowired
	private PhysicalStandardService physicalStandardService;

	@Autowired
	private PhysicalSuggestionDao physicalSuggestionDao;

	public List<PhysicalDetail> findDistinctPdId(Integer clientId) {
		return physicalDetailDao.queryDetailByClientId(clientId);
	}

	public String findPhysicalItemByid(Integer clientId, Integer pdId) {
		List<PhysicalItem> lstItem = physicalItemDao.findPhysicalItemByid(
				clientId, pdId);
		if (lstItem==null)
			return "";
		else{
/*
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			for (int i = 0; i < lstItem.size(); i++) {
				sb.append("[");
				sb.append("'" + lstItem.get(i).getPhysicalTime() + "'");
				sb.append(",");
				sb.append(lstItem.get(i).getResult());
				sb.append("]");
				if (i < lstItem.size() - 1) {
					sb.append(",");
				}
			}
			sb.append("]");
			return sb.toString();
*/
			JSONArray ja = new JSONArray();
			for (int i = 0; i < lstItem.size(); i++) {
				JSONObject jo = new JSONObject();
				jo.put("date", DateUtils.formatDate("yyyy-MM-dd", lstItem.get(i).getPhysicalTime()));
				jo.put("val", lstItem.get(i).getResult());
				ja.add(jo.toString());
			}
			
			return ja.toString();
		}
	}

	public String getAnalysisInfo(Integer clientId) {

		List<ClientPhysical> physicalList = physicalDao.queryPhysical(clientId);
		JSONArray phyArray = new JSONArray();
		if (!CollectionUtils.isEmpty(physicalList)) {
			for (ClientPhysical phy : physicalList) {
				JSONObject phyJson = new JSONObject();
				phyJson.put("phyId", phy.getId());
				phyJson.put("phyTime", getPhysicalTime(phy.getPhysicalTime()));
				phyArray.add(phyJson);
			}
		}

		List<PhysicalItem> itemList = physicalItemDao
				.findPhysicalItemBycId(clientId);

		JSONArray itemArray = new JSONArray();
		if (itemList != null) {
			for (PhysicalItem item : itemList) {
				JSONObject itemJson = new JSONObject();
				itemJson.put("phyIdVsPdId", item.getPhysicalId() + "_"
						+ item.getPdId());
				itemJson.put("result", item.getResult());
				itemArray.add(itemJson);
			}
		}

		List<PhysicalDetail> detailList = physicalDetailDao
				.queryDetailByClientId(clientId);

		JSONArray detailArray = new JSONArray();
		if (itemList != null) {
			for (PhysicalDetail detail : detailList) {
				JSONObject detailJson = new JSONObject();
				detailJson.put("pdId", detail.getPdId());
				detailJson.put("dname", detail.getName());
				detailArray.add(detailJson);
			}
		}

		JSONObject jo = new JSONObject();
		jo.put("pTime", phyArray.toString());
		jo.put("items", itemArray.toString());
		jo.put("details", detailArray.toString());

		return jo.toString();
	}

	private String getPhysicalTime(Date physicalTime) {
		if (null == physicalTime)
			return null;
		return DateUtils.formatDate(DateUtils.CNSHORT_DATE_PATTERN,
				physicalTime);
	}

	public List<PhysicalItem> findPhysicalItemByids(Integer clientId,
			Integer pdId) {
		
		List<PhysicalItem> lstItem = physicalItemDao.findPhysicalItemBycId(clientId);
		List<PhysicalItem> lst = new ArrayList<PhysicalItem>();
		if(lstItem!=null){
			for(PhysicalItem item:lstItem){
				if(item.getPdId().equals(pdId)){
					lst.add(item);
				}
			}
		}
		return lst;
	}

	// 最大的显示值
	public double findMaxVlaue(Integer clientId, Integer pdId) {
		double maxStandard = physicalStandardDao.findMaxStandard(pdId);
		List<Double> lstDouble = new ArrayList<Double>();
		//List<PhysicalItem> lst = physicalItemDao.findPhysicalItemByid(clientId,
				//pdId);
		
		List<PhysicalItem> lstItem = physicalItemDao.findPhysicalItemBycId(clientId);
		List<PhysicalItem> lst = new ArrayList<PhysicalItem>();
		if(lstItem!=null){
			for(PhysicalItem item:lstItem){
				if(item.getPdId().equals(pdId)){
					lst.add(item);
				}
			}
		}
		if (!lst.isEmpty()) {
			for (int i = 0; i < lst.size(); i++) {
				lstDouble.add(Double.parseDouble(lst.get(i).getResult()));
			}
			Collections.sort(lstDouble);
			Collections.reverse(lstDouble);
			if (!CollectionUtils.isEmpty(lstDouble)) {
				double maxResult = lstDouble.get(0);
				if (maxStandard > maxResult) {
					return maxStandard;
				} else {
					return maxResult;
				}
			} else {
				return -1;
			}
		} else {
			return -1;
		}

	}

	/**
	 * 个人数据分析
	 */
	public String findAnalysisSuggestion(Integer clientId) {
		// 名称detail 临床意义 detail
		List<PhysicalDetail> lstDetail = physicalDetailDao
				.queryDetailByClientId(clientId);
		// 某个用户拥有所有的建议
		List<PhysicalSuggestion> lstSugg = physicalSuggestionDao
				.findSuggestionBycId(clientId);

		JSONArray detailArray = new JSONArray();
		if (!CollectionUtils.isEmpty(lstDetail)) {
			for (PhysicalDetail detail : lstDetail) {
				if (detail.getFlag() == 0) {
					JSONObject detailJson = new JSONObject();
					if (null != lstSugg) {
						for (PhysicalSuggestion suggestion : lstSugg) {
							if (detail.getPdId() .equals(suggestion.getPdId())) {
								detailJson.put("suggestion", suggestion
										.getSuggestion());
								detailJson.put("id", suggestion.getId());
							}
							
						}
					}
					
					detailJson.put("pdId", detail.getPdId());
					detailJson.put("name", detail.getName());
					detailJson.put("content", detail.getContent());
					
					detailArray.add(detailJson);
				}
			}
			JSONArray otherSuggArray1 = new JSONArray();
			JSONObject suggObj = new JSONObject();
			if (null != lstSugg) {
				for (PhysicalSuggestion sugg : lstSugg) {
					if (sugg.getPdId() == -1) {
						suggObj.put("id", sugg.getId());
						suggObj.put("pdId", sugg.getPdId());
						suggObj.put("suggestion", sugg.getSuggestion());
					}
				}
				otherSuggArray1.add(suggObj);
			}

			JSONArray otherSuggArray2 = new JSONArray();
			JSONObject suggObj2 = new JSONObject();
			if (null != lstSugg) {
				for (PhysicalSuggestion sugg : lstSugg) {
					if (sugg.getPdId() == 0) {
						suggObj2.put("id", sugg.getId());
						suggObj2.put("pdId", sugg.getPdId());
						suggObj2.put("suggestion", sugg.getSuggestion());
					}
				}
				otherSuggArray2.add(suggObj2);
			}

			JSONObject obj = new JSONObject();
			obj.put("details", detailArray.toArray());
			obj.put("otherSugg", otherSuggArray1.toArray());
			obj.put("otherSugg2", otherSuggArray2.toArray());
			return obj.toString();
		} else {
			return "";
		}
	}

	public String getAnalysisCollection(int gendar, Integer clientId) {
		// 查询出用户参与体检指标的指标标准信息
		List<PhysicalDetail> listPhyDetail = physicalDetailDao
				.queryDetailByClientId(clientId);

		JSONObject lastJo = new JSONObject();

		if (!CollectionUtils.isEmpty(listPhyDetail)) {
			JSONArray jArray = new JSONArray();
			for (PhysicalDetail pDetail : listPhyDetail) {
				JSONObject jo = new JSONObject();
				// pDetail
				// 目前个人分析只针对数据类型的做统计，不对文字性（即表格）做判断
				if (pDetail.getFlag() == 0) {
					// 数字类型数据
					Double maxResult = findMaxVlaue(clientId, pDetail.getPdId());
					
					String pysicalItems = findPhysicalItemByid(clientId,
							pDetail.getPdId());
					String standards = physicalStandardService
							.findStandardBypdId(pDetail.getPdId());
					jo.put("pdId", pDetail.getPdId());
					jo.put("detail", JsonUtils.getJsonObject4JavaPOJO(pDetail,
							DateJsonValueProcessor.LONG_DATE_PATTERN));
					jo.put("maxResult", maxResult);
					jo.put("pysicalItems", pysicalItems);
					jo.put("standards", standards);
					jArray.add(jo);

				} else {
					// 文字性数据
				}

			}
			lastJo.put("analysisList", jArray.toString());
		}
		lastJo.put("gendar", gendar);
		return lastJo.toString();
	}

	public List findPhysicalItemBycIdpId(Integer clientId, Integer physicalId) {
		return physicalItemDao.findPhysicalItemBycIdpId(clientId, physicalId);
	}
	
}
