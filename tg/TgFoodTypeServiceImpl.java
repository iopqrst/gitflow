package com.bskcare.ch.service.impl.tg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.tg.TgFoodTypeDao;
import com.bskcare.ch.service.tg.TgFoodTypeService;
import com.bskcare.ch.vo.tg.TgFoodType;

@Service
public class TgFoodTypeServiceImpl implements TgFoodTypeService{

	@Autowired
	private TgFoodTypeDao foodTypeDao;
	
	public List<TgFoodType> queryFoodType(){
		return foodTypeDao.queryFoodType();
	}
}
