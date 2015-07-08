package com.bskcare.ch.service.impl.tg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.tg.TgFoodDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.tg.TgFoodService;
import com.bskcare.ch.vo.tg.TgFood;

@Service
public class TgFoodServiceImpl implements TgFoodService {

	@Autowired
	private TgFoodDao tgFoodDao;

	public TgFood addTgFood(TgFood tgFood) {
		if (tgFood != null) {
			return tgFoodDao.add(tgFood);
		}
		return null;
	}

	public void updateTgFood(TgFood tgFood) {
		if (tgFood != null) {
			tgFoodDao.update(tgFood);
		}
	}

	public TgFood queryTgFoodById(Integer id) {
		if (id != null) {
			return tgFoodDao.load(id);
		}
		return null;
	}

	public PageObject queryTgFood(TgFood tgFood, QueryInfo queryInfo) {
		return tgFoodDao.queryTgFood(tgFood, queryInfo);
	}

	public int findByName(String name) {
		return tgFoodDao.findByName(name);
	}

	public void delete(int id) {
		tgFoodDao.delete(id);
	}

	public List<TgFood> queryFoodByType(Integer type) {
		return tgFoodDao.queryFoodByType(type);
	}
}
