package com.bskcare.ch.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bskcare.ch.dao.PhysicalSuggestionDao;
import com.bskcare.ch.service.PhysicalSuggestionService;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.vo.client.PhysicalSuggestion;

@Service
public class PhysicalSuggestionServiceImpl implements PhysicalSuggestionService {

	@Autowired
	private PhysicalSuggestionDao suggestionDao;

	public void saveOrUpdataSuggestion(PhysicalSuggestion phySuggestion,
			Integer userId, String suggestions) {

		List<PhysicalSuggestion> lstSuggestion = new ArrayList<PhysicalSuggestion>();

		if (null != phySuggestion && suggestions != null) {
			lstSuggestion = JsonUtils.getList4Json(suggestions,
					PhysicalSuggestion.class);
			// 根据clientId查询用户拥有所有建议
			List<PhysicalSuggestion> lst = suggestionDao
					.findSuggestionBycId(phySuggestion.getClientId());
			for (PhysicalSuggestion sugg : lstSuggestion) {
				// 查询某个建议的详细信息
				PhysicalSuggestion suggestion = new PhysicalSuggestion();

				suggestion = getSuggestByPdId(lst, sugg.getPdId());
				if (suggestion != null) {
					suggestion.setPdId(sugg.getPdId());
					suggestion.setClientId(phySuggestion.getClientId());
					suggestion.setUserId(userId);
					suggestion.setSuggestion(sugg.getSuggestion());
					suggestion.setCreateTime(new Date());
					suggestionDao.update(suggestion);
				} else {
					sugg.setClientId(phySuggestion.getClientId());
					sugg.setUserId(userId);
					sugg.setCreateTime(new Date());
					suggestionDao.add(sugg);
				}
			}
		}
	}

	private PhysicalSuggestion getSuggestByPdId(List<PhysicalSuggestion> list,
			Integer pdId) {
		if (list != null && null != pdId) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getPdId().equals(pdId)) {
					return list.get(i);
				}
			}
		}
		return null;
	}
}
