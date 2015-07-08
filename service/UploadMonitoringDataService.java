package com.bskcare.ch.service;

import com.bskcare.ch.bo.UploadED;
import com.bskcare.ch.vo.ClientInfo;

/**
 * 上传监测数据(该类仅仅为上传数据及相关的方法，其他绕道）
 */
public interface UploadMonitoringDataService {
	
	/**
	 * 上传血压信息
	 * @param userInfo
	 * @param msg
	 */
	public String uploadBloodPressure(ClientInfo clientInfo, String msg, String ip, String version);
	
	/**
	 * 上传血氧信息
	 * @param userInfo
	 * @param msg
	 */
	public String uploadBloodOxygen(ClientInfo clientInfo,String msg, String ip);
	
	/***
	 * 上传血糖信息
	 * @param userInfo
	 * @param msg
	 */
	public String uploadBloodSugar(ClientInfo clientInfo, String msg, String ip, String version);
	
	/**
	 * 手机上传心电图文件
	 * @param request
	 * @param upfileContentType 
	 * @param upfileFileName 
	 * @param upfile 
	 * @param userInfo 
	 */
	public String uploadElectrocardiogram(ClientInfo clientInfo,UploadED uploadED, String ip);

	/**
	 * 一次上传所有信息
	 * @param clientInfo
	 * @param br
	 * @param bo
	 * @param bs
	 * @param jo 
	 * @param uploadED 
	 */
	public String uploadAllMessage(ClientInfo clientInfo, String br, String bo,
			String bs, UploadED uploadED, String tp, String ip);
	
	/**
	 * 上传体温
	 */
	public String uploadTemperature(ClientInfo ci, String msg, String ip);
}
