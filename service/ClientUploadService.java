package com.bskcare.ch.service;

import com.bskcare.ch.bo.ClientUploadExtend;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.client.ClientUpload;

public interface ClientUploadService{
	/**
	 * 查询客户上传的文件
	 */
	public PageObject<ClientUpload> findClientUpload(ClientUpload clientUpload,QueryInfo queryInfo);
	
	public void addClientUpload(ClientUpload clientUpload);
	
	/**
	 * 根据体检数据id和客户id查询
	 */
	public ClientUpload findClinetUploadById(Integer id,Integer clientId);
	
	/**
	 * 不同地区的用户上传文件的状态
	 */
	public PageObject<ClientUploadExtend> findClientUploadFiles(String areaChain,QueryInfo queryInfo,
			ClientUploadExtend c);
	/**
	 * 查询单个对象
	 */
	public ClientUpload findById(Integer id);
	/**
	 * 修改
	 */
	public void dealWithTask(ClientUpload clientUpload);
}
