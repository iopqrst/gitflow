package com.bskcare.ch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.TodayTaskDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.TodayTaskService;
import com.bskcare.ch.vo.client.TodayTask;

@Service
public class TodayTaskServiceImpl implements TodayTaskService {
	@Autowired
	private TodayTaskDao taskDao;

	public TodayTask add(TodayTask task) {
		return taskDao.add(task);
	}

	public void delete(TodayTask task) {
		taskDao.delete(task);
	}

	public TodayTask findById(Integer id) {
		return taskDao.load(id);
	}

	public PageObject<TodayTask> findCp(Integer clientId, QueryInfo queryInfo) {
		return taskDao.findCp(clientId, queryInfo);
	}

	public PageObject<TodayTask> findMp(Integer clientId, QueryInfo queryInfo) {
		return taskDao.findMp(clientId, queryInfo);
	}

	public void update(TodayTask task) {
		taskDao.update(task);
	}

}
