package com.bskcare.ch.service;

import java.util.List;

import com.bskcare.ch.vo.search.DiseaseClass;

public interface DiseaseClassService {
     List<DiseaseClass> queryDiseaseClassAll();
     
     List<DiseaseClass> queryById(Integer id);
     //andriod接口查看药品分类
	 String queryByIdAndriod(Integer bid);

	 
	 List<DiseaseClass> queryOneMenuAll(int length);

}
