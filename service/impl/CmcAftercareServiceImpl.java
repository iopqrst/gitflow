package com.bskcare.ch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.CmcAftercareDao;
import com.bskcare.ch.service.CmcAftercareService;
import com.bskcare.ch.vo.CmcAftercare;

@Service("cmcAftercareService")
@SuppressWarnings("unchecked")
public class CmcAftercareServiceImpl implements CmcAftercareService {

	@Autowired
	private CmcAftercareDao cmcAftercareDao;

	public CmcAftercare selectAfterCareById(int id) {

		return cmcAftercareDao.selectAfterCareById(id);
	}

	/**
	 * 根据id修改中医体质调养信息
	 */
	public void updateCmcAftercare(CmcAftercare cmcAftercare) {
		cmcAftercareDao.updateCmcAftercare(cmcAftercare);
	}

	public List<CmcAftercare> selectCmcAftercare() {
		return cmcAftercareDao.selectCmcAftercare();
	}

}
