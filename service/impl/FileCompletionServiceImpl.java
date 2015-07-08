package com.bskcare.ch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.ClientDietDao;
import com.bskcare.ch.dao.ClientFamilyHistoryDao;
import com.bskcare.ch.dao.ClientHobbyDao;
import com.bskcare.ch.dao.ClientInfoDao;
import com.bskcare.ch.dao.ClientLatestPhyDao;
import com.bskcare.ch.dao.ClientMedicalHistoryDao;
import com.bskcare.ch.dao.ClientMentalHealthDao;
import com.bskcare.ch.service.FileCompletionService;
import com.bskcare.ch.util.MoneyFormatUtil;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.client.ClientDiet;
import com.bskcare.ch.vo.client.ClientFamilyHistory;
import com.bskcare.ch.vo.client.ClientHobby;
import com.bskcare.ch.vo.client.ClientLatestPhy;
import com.bskcare.ch.vo.client.ClientMedicalHistory;
import com.bskcare.ch.vo.client.ClientMentalHealth;

@Service
public class FileCompletionServiceImpl implements FileCompletionService{
	@Autowired
	private ClientInfoDao clientUserDao;
	
	@Autowired
	private ClientLatestPhyDao latestPhyDao;
	
	@Autowired
	private ClientDietDao clientDietDao;
	
	@Autowired
	private ClientHobbyDao clientHobbyDao;
	
	@Autowired
	private ClientMedicalHistoryDao medicalHistoryDao;
	
	@Autowired
	private ClientFamilyHistoryDao familyHistoryDao;
	
	@Autowired
	private ClientMentalHealthDao mentalHealthDao;

	@Autowired
	private ClientInfoDao clientInfoDao;
	
	/**
	 * 用户基本档案的完成率
	 */
	public double findClientBaseCompletion(Integer clientId){
		double clientBase = 0;
		ClientInfo clientInfo = new ClientInfo();
		clientInfo = clientUserDao.load(clientId);
		if(clientInfo != null){
//			if(!StringUtils.isEmpty(clientInfo.getClientCode())){
//				clientBase++;
//			}
			if(!StringUtils.isEmpty(clientInfo.getName())){
				clientBase++;
			}
			if(clientInfo.getGender()!= null){
				clientBase++;
			}
			if(clientInfo.getBirthday()!=null){
				clientBase++;
			}
			
			if(!StringUtils.isEmpty(clientInfo.getIdCards())){
				clientBase++;
			}
			
			if(!StringUtils.isEmpty(clientInfo.getBirthPlace())){
				clientBase++;
			}
			if(!StringUtils.isEmpty(clientInfo.getUsualAddress())){
				clientBase++;
			}
			if(!StringUtils.isEmpty(clientInfo.getEthnic())){
				clientBase++;
			}
			if(!StringUtils.isEmpty(clientInfo.getFaith())){
				clientBase++;
			}
			if(clientInfo.getBloodType()!=null){
				clientBase++;
			}
			
			if(clientInfo.getRh()!=null){
				clientBase++;
			}
			if(clientInfo.getCulture() != null){
				clientBase++;
			}
			if(!StringUtils.isEmpty(clientInfo.getDuty())){
				clientBase++;
			}
			if(clientInfo.getIncome() != null){
				clientBase++;
			}
			if(clientInfo.getMarriage() != null){
				clientBase++;
			}
			if(!StringUtils.isEmpty(clientInfo.getWorkUnits())){
				clientBase++;
			}
			if(!StringUtils.isEmpty(clientInfo.getUnitsPhone())){
				clientBase++;
			}
			if(!StringUtils.isEmpty(clientInfo.getAddress())){
				clientBase++;
			}
			if(!StringUtils.isEmpty(clientInfo.getPhone())){
				clientBase++;
			}
			if(!StringUtils.isEmpty(clientInfo.getMobile())){
				clientBase++;
			}
			if(clientInfo.getMedicalExpenses() != null){
				clientBase++;
			}
			if(!StringUtils.isEmpty(clientInfo.getProfession())){
				clientBase++;
			}
		}
		return clientBase;
	}
	
	
	/**
	 * 用户最后一次体检记录的档案完成率
	 */
	public double findLatestPhyCompletion(Integer clientId){
		ClientLatestPhy clientLatestPhy = new ClientLatestPhy();
		clientLatestPhy= latestPhyDao.getClientLastestPhy(clientId);
		double latestPhy = 0;
		if(clientLatestPhy != null){
			if(!StringUtils.isEmpty(clientLatestPhy.getAlb())){
				++latestPhy;
			}
			if(!StringUtils.isEmpty(clientLatestPhy.getBk())){
				++latestPhy;
			}
			if(!StringUtils.isEmpty(clientLatestPhy.getBreech())){
				++latestPhy;
			}
			if(!StringUtils.isEmpty(clientLatestPhy.getBun())){
				++latestPhy;
			}
			if(!StringUtils.isEmpty(clientLatestPhy.getDbil())){
				++latestPhy;
			}
			if(!StringUtils.isEmpty(clientLatestPhy.getDbp())){
				++latestPhy;
			}
			if(!StringUtils.isEmpty(clientLatestPhy.getGlu())){
				++latestPhy;
			}
			if(!StringUtils.isEmpty(clientLatestPhy.getHdl())){
				++latestPhy;
			}
			if(!StringUtils.isEmpty(clientLatestPhy.getHeight())){
				++latestPhy;
			}
			if(!StringUtils.isEmpty(clientLatestPhy.getLdl())){
				++latestPhy;
			}
			if(!StringUtils.isEmpty(clientLatestPhy.getNatrium())){
				++latestPhy;
			}
			if(!StringUtils.isEmpty(clientLatestPhy.getSbp())){
				++latestPhy;
			}
			if(!StringUtils.isEmpty(clientLatestPhy.getScre())){
				++latestPhy;
			}
			if(!StringUtils.isEmpty(clientLatestPhy.getSgot())){
				++latestPhy;
			}
			if(!StringUtils.isEmpty(clientLatestPhy.getSgpt())){
				++latestPhy;
			}
			if(!StringUtils.isEmpty(clientLatestPhy.getTbil())){
				++latestPhy;
			}
			if(!StringUtils.isEmpty(clientLatestPhy.getTc())){
				++latestPhy;
			}
			if(!StringUtils.isEmpty(clientLatestPhy.getTlc())){
				++latestPhy;
			}
			if(!StringUtils.isEmpty(clientLatestPhy.getWaist())){
				++latestPhy;
			}
			if(!StringUtils.isEmpty(clientLatestPhy.getWeight())){
				++latestPhy;
			}
			if(clientLatestPhy.getPhysicalTime()!=null){
				++latestPhy;
			}
		}
		return latestPhy;
	}
	
	
	/**
	 * 用户饮食生活惯档案完成率
	 */
	public double findClientHobbyCompletion(Integer clientId){
		double hobbyCompletion = 0;
		ClientHobby hobby = clientHobbyDao.getClientHobby(clientId);
		if(hobby != null){
			if(hobby.getSmoke() != 0){
				hobbyCompletion++;
			}
			if(hobby.getDrink() != 0){
				hobbyCompletion++;
			}
			if(hobby.getWorking() != 0){
				hobbyCompletion++;
			}
			if(hobby.getSleeping() != 0){
				hobbyCompletion++;
			}
			if(!StringUtils.isEmpty(hobby.getDiet())){
				hobbyCompletion++;
			}
			if(hobby.getSportTime()!=0||!StringUtils.isEmpty(hobby.getSportSupply())||!StringUtils.isEmpty(hobby.getSportType())){
				hobbyCompletion++;
			}
			if(hobby.getPhysicalType() != 0){
				hobbyCompletion++;
			}
			
		}
		return hobbyCompletion;
	}
	
	
	/**
	 * 用户既往健康档案完成率
	 */
	public double findClientHealthComplaction(Integer clientId){
		double healthComplaction = 0;
		ClientMedicalHistory medical = medicalHistoryDao.getClientMedicalHistory(clientId);
		ClientFamilyHistory family = familyHistoryDao.getClientFamilyHistory(clientId);
		
		ClientInfo clientInfo = clientInfoDao.load(clientId);
		
		if(medical != null){
			if(medical.getIsHasMedical() != null){
				healthComplaction++;
			}
			if(medical.getIsHasMedical() != null){
				healthComplaction++;
			}
			if(!StringUtils.isEmpty(medical.getDeformity())){
				healthComplaction++;
			}
			
			
			if(clientInfo != null){
				if(clientInfo.getGender() != null && clientInfo.getGender() ==1){
					if(!StringUtils.isEmpty(medical.getMenstrual())){
						healthComplaction++;
					}
				}
				if(clientInfo.getGender() == null || clientInfo.getGender() ==0){
					healthComplaction++;
				}
			}
			
			if(clientInfo != null){
				if(clientInfo.getGender() != null && clientInfo.getGender() ==1){
					if(!StringUtils.isEmpty(medical.getBear())){
						healthComplaction++;
					}
				}
				if(clientInfo.getGender() == null || clientInfo.getGender() ==0){
					healthComplaction++;
				}
			}
			
			if(!StringUtils.isEmpty(medical.getSurgeryDetail())||medical.getSurgery()!=null){
				healthComplaction++;
			}
			if(!StringUtils.isEmpty(medical.getDetail())||medical.getTrauma()!=null){
				healthComplaction++;
			}
			if(!StringUtils.isEmpty(medical.getSupply())||medical.getTransfusionOfBlood()!=null){
				healthComplaction++;
			}
		}
		
		if(family != null){
			if(family.getIsHasFamilyHealth() != null){
				healthComplaction++;
			}
		}
		return healthComplaction;
	}
	
	/**
	 * 用户心理健康健康档案完成率
	 */
	public double findClientMentalComplation(Integer clientId){
		double mentalComplaction = 0;
		ClientMentalHealth mentalHealth = mentalHealthDao.getClientMentalHealth(clientId);
		if(mentalHealth != null){
			if(mentalHealth.getDepress()!=0){
				mentalComplaction++;
			}
			if(!StringUtils.isEmpty(mentalHealth.getEvent())||!StringUtils.isEmpty(mentalHealth.getEventSupply())){
				mentalComplaction++;
			}
			if(mentalHealth.getExcite()!=0){
				mentalComplaction++;
			}
			if(mentalHealth.getFuel()!=0){
				mentalComplaction++;
			}
			if(mentalHealth.getHappy()!=0){
				mentalComplaction++;
			}
			if(mentalHealth.getHope()!=0){
				mentalComplaction++;
			}
			if(mentalHealth.getLongly()!=0){
				mentalComplaction++;
			}
			if(mentalHealth.getMelancholy()!=0){
				mentalComplaction++;
			}
			if(mentalHealth.getNerves()!=0){
				mentalComplaction++;
			}
			if(mentalHealth.getPet()!=0){
				mentalComplaction++;
			}
			if(mentalHealth.getPfEquipment()!=0){
				mentalComplaction++;
			}
			if(mentalHealth.getToilet()!=0){
				mentalComplaction++;
			}
			if(mentalHealth.getWater()!=0){
				mentalComplaction++;
			}
		}
		return mentalComplaction;
	}
	
	/**
	 * 用户饮食习惯档案完成率
	 */
	public double findClientDietCompletion(Integer clientId){
		double dietCompletion = 0;
		ClientDiet clientDiet = clientDietDao.getClientDiet(clientId);
		if(clientDiet!=null){
			String content = clientDiet.getContent();
			if(!StringUtils.isEmpty(content)){
				int h = 0;
				for(int i = 0;i<content.length();i++){
					if (content.indexOf(":", h) != -1) {
						h = content.indexOf(":", h) + 1;
						dietCompletion++;
					}
				}
			}
		}
		return dietCompletion;
	}
	
	/**
	 * 用户健康档案完成率
	 */
	public double findClientFIleCompletion(Integer clientId){
		double healthComplation = this.findClientHealthComplaction(clientId);
		double mentalComplation = this.findClientMentalComplation(clientId);
		double clientBaseComplation = this.findClientBaseCompletion(clientId);
		double latestPhyCompletion = this.findLatestPhyCompletion(clientId);
		double hobbyCompletion = this.findClientHobbyCompletion(clientId);
		double dietCompletion = this.findClientDietCompletion(clientId);
		double fileCompletioned = healthComplation+mentalComplation+clientBaseComplation+latestPhyCompletion+hobbyCompletion+dietCompletion/2;
		double allFileCompletion = 95;
		double fileCompletion = (fileCompletioned/allFileCompletion*100);
		return fileCompletion;
	}

	/**
	 * 更新用户档案完成率
	 */
	public void updateCompletion(Integer clientId) {
		double finishPercent = findClientFIleCompletion(clientId);
		clientUserDao.updateFinishPercent(clientId, MoneyFormatUtil.formatDouble(finishPercent));
	}
}
