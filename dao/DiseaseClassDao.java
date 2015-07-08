package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.vo.search.DiseaseClass;

public interface DiseaseClassDao {
   List<DiseaseClass> queryClassAll();
   
   List<DiseaseClass> queryById(Integer id);

   /**
    * 根据T参数获得  二级菜单
    * @param t
    * @return
    */
   List<DiseaseClass> queryMenuByT(String t);

   
   List<DiseaseClass> queryOneMenuAll(int length);

}
