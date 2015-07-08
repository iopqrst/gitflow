package com.bskcare.ch.service;

import java.util.List;

import com.bskcare.ch.vo.search.DrugClass;

public interface DrugClassService {
	public List<DrugClass> queryAll();
	
	public List<DrugClass> queryByCbm(String cbm);

	public List<DrugClass> queryByOne(String cbm);

	public String queryByIdMsgAndriod(String t, String id);

	public String queryByCbmAndriod(String t, String cbm);

	public String queryMenuByT(String t);
}
