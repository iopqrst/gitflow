package com.bskcare.ch.service;

import java.util.List;

import com.bskcare.ch.vo.search.FlVo;

public interface FlVoService {
List<FlVo> queryByCbm(String cbm);


String queryAnyOne(String cypbm,Integer id);


List<FlVo> queryByIdMsg(String id);


}
