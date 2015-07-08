package com.bskcare.ch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.CmcAnalysisDao;
import com.bskcare.ch.service.CmcAnalysisService;
import com.bskcare.ch.vo.CmcAnalysis;

@Service("cmcAnalysisService")
@SuppressWarnings("unchecked")
public class CmcAnalysisServiceImpl implements CmcAnalysisService {

	@Autowired
	private CmcAnalysisDao cmcAnalysisDao;

	public CmcAnalysis selectCmcAnalysisById(int id) {
		return cmcAnalysisDao.selectCmcAnalysisById(id);
	}

	/**
	 * 修改中医体质分析
	 */
	public void updateCmcAnalysis(CmcAnalysis cmcAnalysis) {
		cmcAnalysisDao.updateCmcAnalysis(cmcAnalysis);
	}

	public List<CmcAnalysis> selectCmcAftercares() {
		return cmcAnalysisDao.selectCmcAnalysis();
	}

}
