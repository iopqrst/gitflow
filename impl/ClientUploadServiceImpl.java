package com.bskcare.ch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.bo.ClientUploadExtend;
import com.bskcare.ch.dao.ClientUploadDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.ClientUploadService;
import com.bskcare.ch.vo.client.ClientUpload;

@Service
public class ClientUploadServiceImpl implements ClientUploadService {

	@Autowired
	private ClientUploadDao clientUploadDao;

	/**
	 * 查询客户上传的文件
	 */
	public PageObject<ClientUpload> findClientUpload(ClientUpload clientUpload,
			QueryInfo queryInfo) {
		return clientUploadDao.findClientUpload(clientUpload, queryInfo);
	}

	public void addClientUpload(ClientUpload clientUpload) {
		clientUploadDao.add(clientUpload);
	}

	public ClientUpload findClinetUploadById(Integer id, Integer clientId) {
		return clientUploadDao.findClinetUploadById(id, clientId);
	}
	
	/**
	 * 不同地区的用户上传文件的状态
	 */
	public PageObject<ClientUploadExtend> findClientUploadFiles(String areaChain,QueryInfo queryInfo,
			ClientUploadExtend c) {
		return clientUploadDao.findClientUploadFiles(areaChain,queryInfo,c);
	}
	
	/**
	 * 查询对象
	 */
	public ClientUpload findById(Integer id) {
		return clientUploadDao.load(id);
	}
	
	/**
	 * 修改
	 */
	public void dealWithTask(ClientUpload clientUpload) {
		clientUploadDao.update(clientUpload);
	}

}
