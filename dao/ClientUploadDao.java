package com.bskcare.ch.dao;


import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.bo.ClientUploadExtend;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.client.ClientUpload;

public interface ClientUploadDao extends BaseDao<ClientUpload>{
	
	/**
	 * 查询客户上传的文件
	 */
	public PageObject<ClientUpload> findClientUpload(ClientUpload clientUpload,QueryInfo queryInfo);
	
	/**
	 * 添加客户上传文件信息
	 */
	public void addClientUpload(ClientUpload clientUpload);

	/**
	 * 根据体检数据id和客户id查询
	 */
	public ClientUpload findClinetUploadById(Integer id,Integer clientId);
	/**
	 * 根据用户姓名和状态查询
	 * @param queryInfo 
	 */
	public PageObject<ClientUploadExtend> findClientUploadFiles(String areaChain,QueryInfo queryInfo,
			ClientUploadExtend c);
	
	public int findClientUploadFiles(String areaChain, ClientUploadExtend c);
}
