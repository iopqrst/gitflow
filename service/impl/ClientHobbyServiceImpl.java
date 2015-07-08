package com.bskcare.ch.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.bo.ClientHobbyExtend;
import com.bskcare.ch.dao.ClientHobbyDao;
import com.bskcare.ch.service.ClientHobbyService;
import com.bskcare.ch.util.ArrayUtils;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.client.ClientHobby;

@Service
public class ClientHobbyServiceImpl implements ClientHobbyService {

	@Autowired
	private ClientHobbyDao clientHobbyDao;

	public ClientHobby getClientHobby(Integer clientId) {
		if (null == clientId)
			return null;
		return clientHobbyDao.getClientHobby(clientId);
	}

	public void saveOrUpdate(ClientHobby cd) {
		if (null != cd && null != cd.getClientId()) {
			ClientHobby ch = getClientHobby(cd.getClientId());
			if (null != ch) {//修改用户习惯
				cd.setSportType(sportTypeSole(cd.getSportType(),ch.getSportType()));
				BeanUtils.copyProperties(cd, ch, new String[] { "createTime" });
				//处理用户运动
				clientHobbyDao.update(ch);
			} else {
				cd.setCreateTime(new Date());
				clientHobbyDao.add(cd);
			}
		}
	}
	public String sportTypeSole(String newStr , String oldStr){
		if(newStr==null&&oldStr==null){
			return null;
		}
		
		oldStr = StringUtils.remove(oldStr, "跑步,", "");
		oldStr = StringUtils.remove(oldStr, ",跑步", "");
		oldStr = StringUtils.remove(oldStr, "跑步", "");
		oldStr = StringUtils.remove(oldStr, "游泳,", "");
		oldStr = StringUtils.remove(oldStr, ",游泳", "");
		oldStr = StringUtils.remove(oldStr, "游泳", "");
		oldStr = StringUtils.remove(oldStr, "球类,", "");
		oldStr = StringUtils.remove(oldStr, ",球类", "");
		oldStr = StringUtils.remove(oldStr, "球类", "");
		oldStr = StringUtils.remove(oldStr, "太极拳,", "");
		oldStr = StringUtils.remove(oldStr, ",太极拳", "");
		oldStr = StringUtils.remove(oldStr, "太极拳", "");
		
		if(!StringUtils.isEmpty(newStr)){
			String[] str=newStr.split(",");
			for (String string : str) {
				oldStr = StringUtils.remove(oldStr, string+",", "");
				oldStr = StringUtils.remove(oldStr, ","+string, "");
				oldStr = StringUtils.remove(oldStr, string, "");
			}
		}
		if(StringUtils.hasLength(oldStr)){
			oldStr=oldStr+","+newStr;
		}else{
			oldStr=newStr;
		}
		return oldStr;
	}

	public void saveOrUpdateAndroid(ClientHobby cd) {
		if (null != cd && null != cd.getClientId()) {
			ClientHobby ch = getClientHobby(cd.getClientId());
			if (null != ch) {
				BeanUtils.copyProperties(cd, ch, new String[] {"id","average","syear","cigaret","createTime",
						"reSmoking","shSmoke","shAge","shDay","shMinuite","white","beer","red","working",
						"diet","physicalType"});
				clientHobbyDao.update(ch);
			} else {
				cd.setCreateTime(new Date());
				clientHobbyDao.add(cd);
			}
		}
	}
	
	
	public String queryClientHobby(Integer cid){
		JSONObject jo = new JSONObject();
		if(cid != null){
			ClientHobby hobby = clientHobbyDao.getClientHobby(cid);
			ClientHobbyExtend hobbyExtend = new ClientHobbyExtend();
			if(hobby != null){
				if(!StringUtils.isEmpty(hobby.getDiet())){
					String diet = setDiet(hobby.getDiet());
					hobbyExtend.setDiet(diet);
				}
				if(!StringUtils.isEmpty(hobby.getNotEat())){
					String notEat = setNotEat(hobby.getNotEat());
					hobbyExtend.setNotEat(notEat);
				}
				
				String breakfast = setFood(hobby.getBreakfast(), hobby.getBreakfastOther());
				hobbyExtend.setBreakfast(breakfast);
				
				String lunch = setFood(hobby.getLunch(), hobby.getLunchOther());
				hobbyExtend.setLunch(lunch);
				
				String dinner = setFood(hobby.getDinner(), hobby.getDinnerOther());
				hobbyExtend.setDinner(dinner);
				
				String jiacanCount = setJiacanCount(hobby.getJiacanCount());
				hobbyExtend.setJiacanCount(jiacanCount);
				
				if(!StringUtils.isEmpty(hobby.getJiacanType())){
					String jiacanType = setJiacanType(hobby.getJiacanType());
					hobbyExtend.setJiacanType(jiacanType);
				}
				
				if(!StringUtils.isEmpty(hobby.getSportType())){
					hobbyExtend.setSportType(hobby.getSportType());
				}
				
				String sportTime = setSportTime(hobby.getSportTime());
				hobbyExtend.setSportTime(sportTime);
				
				String sportTimeZone = setSportTimeZone(hobby.getSportTimeZone(), hobby.getSportZoneOther());
				hobbyExtend.setSportTimeZone(sportTimeZone);
			}
			jo.put("code", 1);
			jo.put("msg", "查询成功");
			jo.put("data", JsonUtils.getJsonString4JavaPOJO(hobbyExtend));
		}else{
			jo.put("code", 0);
			jo.put("msg", "参数有误");
			jo.put("data", "");
		}
		return jo.toString();
	}
	
	//生活嗜好
	public String setDiet(String diet){
		String sdiet = "";
		String[] diets = diet.split(",");
		List<String> lstDiet = new ArrayList<String>();
		for (String fdiet : diets) {
			if(fdiet.equals("1")){
				lstDiet.add("三餐不规律");
			}else if(fdiet.equals("2")){
				lstDiet.add("常不吃早饭");
			}else if(fdiet.equals("3")){
				lstDiet.add("常暴饮暴食");
			}else if(fdiet.equals("4")){
				lstDiet.add("嗜咸");
			}else if(fdiet.equals("5")){
				lstDiet.add("嗜甜");
			}else if(fdiet.equals("6")){
				lstDiet.add("嗜油炸");
			}else if(fdiet.equals("7")){
				lstDiet.add("嗜辣");
			}else if(fdiet.equals("8")){
				lstDiet.add("嗜素食");
			}else if(fdiet.equals("9")){
				lstDiet.add("嗜肉");
			}
		}
		
		if(!CollectionUtils.isEmpty(lstDiet)){
			sdiet = ArrayUtils.join(lstDiet.toArray(), ",");
		}
		return sdiet;
	}
	//运动时间带
	public String setSportTimeZone(String timeZone, String timeZoneOther){
		String sportTimeZone = "";
		List<String> lstTimeZone = new ArrayList<String>();
		if(!StringUtils.isEmpty(timeZone)){
			String[] timeZones = timeZone.split(",");
			for (String ftimeZone : timeZones) {
				if(ftimeZone.equals("1")){
					lstTimeZone.add("早上5：00-8：00");
				}else if(ftimeZone.equals("2")){
					lstTimeZone.add("早上8：00-11：00");
				}else if(ftimeZone.equals("3")){
					lstTimeZone.add("下午2：00-6：00");
				}else if(ftimeZone.equals("4")){
					lstTimeZone.add("下午6：00-9：00");
				}
			}
		}
		if(!StringUtils.isEmpty(timeZoneOther)){
			lstTimeZone.add(timeZoneOther);
		}
		if(!CollectionUtils.isEmpty(lstTimeZone)){
			sportTimeZone = ArrayUtils.join(lstTimeZone.toArray(), ",");
		}
		return sportTimeZone;
	}
	
	//不喜欢吃的食物
	public String setNotEat(String notEat){
		String snotEat = "";
		String[] notEats = notEat.split(",");
		List<String> lstNotEat = new ArrayList<String>();
		for (String fnotEat : notEats) {
			if(fnotEat.equals("1")){
				lstNotEat.add("大米类");
			}else if(fnotEat.equals("2")){
				lstNotEat.add("薯类");
			}else if(fnotEat.equals("3")){
				lstNotEat.add("杂粮类");
			}else if(fnotEat.equals("4")){
				lstNotEat.add("畜禽肉类及制品");
			}else if(fnotEat.equals("5")){
				lstNotEat.add("鱼及水产品");
			}else if(fnotEat.equals("6")){
				lstNotEat.add("蛋类及制品");
			}else if(fnotEat.equals("7")){
				lstNotEat.add("奶及制品");
			}else if(fnotEat.equals("8")){
				lstNotEat.add("干豆及豆制品类");
			}else if(fnotEat.equals("9")){
				lstNotEat.add("坚果");
			}else if(fnotEat.equals("10")){
				lstNotEat.add("蔬菜");
			}else if(fnotEat.equals("11")){
				lstNotEat.add("水果");
			}
		}
		
		if(!CollectionUtils.isEmpty(lstNotEat)){
			snotEat = ArrayUtils.join(lstNotEat.toArray(), ",");
		}
		return snotEat;
	}
	
	//各餐吃的食物
	public String setFood(String food, String foodOther){
		String sfood = "";
		List<String> lstFood = new ArrayList<String>();
		if(!StringUtils.isEmpty(food)){
			String[] foods = food.split(",");
			for (String ffood : foods) {
				if(ffood.equals("1")){
					lstFood.add("粥类");
				}else if(ffood.equals("2")){
					lstFood.add("米饭类");
				}else if(ffood.equals("3")){
					lstFood.add("面食类");
				}else if(ffood.equals("4")){
					lstFood.add("肉类");
				}else if(ffood.equals("5")){
					lstFood.add("果蔬类");
				}else if(ffood.equals("6")){
					lstFood.add("豆及豆制品类");
				}else if(ffood.equals("7")){
					lstFood.add("奶类");
				}else if(ffood.equals("8")){
					lstFood.add("蛋类");
				}
			}
		}
		if(!StringUtils.isEmpty(foodOther)){
			lstFood.add(foodOther);
		}
		if(!CollectionUtils.isEmpty(lstFood)){
			sfood = ArrayUtils.join(lstFood.toArray(), ",");
		}
		return sfood;
	}
	
	//加餐次数
	public String setJiacanCount(int jiacanCount){
		String sjiacanCount = "";
		if(jiacanCount == 0){
			sjiacanCount = "无加餐";
		}else if(jiacanCount == 1){
			sjiacanCount = "1次/天";
		}else if(jiacanCount == 2){
			sjiacanCount = "2次/天";
		}else if(jiacanCount == 3){
			sjiacanCount = "3次/天";
		}else if(jiacanCount == 4){
			sjiacanCount = ">3次/天";
		}
		return sjiacanCount;
	}
	
	//加餐类型
	public String setJiacanType(String jiacanType){
		String sjiacanType = "";
		String[] jiacanTypes = jiacanType.split(",");
		List<String> lstType = new ArrayList<String>();
		for (String ftype : jiacanTypes) {
			if(ftype.equals("1")){
				lstType.add("坚果类");
			}else if(ftype.equals("2")){
				lstType.add("牛奶");
			}else if(ftype.equals("3")){
				lstType.add("酸奶");
			}else if(ftype.equals("4")){
				lstType.add("豆浆");
			}else if(ftype.equals("5")){
				lstType.add("水果");
			}else if(ftype.equals("6")){
				lstType.add("蒸薯类");
			}else if(ftype.equals("7")){
				lstType.add("饼干");
			}else if(ftype.equals("8")){
				lstType.add("面包");
			}else if(ftype.equals("9")){
				lstType.add("豆类");
			}
		}
		if(!CollectionUtils.isEmpty(lstType)){
			sjiacanType = ArrayUtils.join(lstType.toArray(), ",");
		}
		return sjiacanType;
	}
	
	//运动时长
	public String setSportTime(int sportTime){
		String ssportTime = "";
		if(sportTime == 1){
			ssportTime = "30分钟以下/次 ";
		}else if(sportTime == 2){
			ssportTime = "30-60分钟/次";
		}else if(sportTime == 3){
			ssportTime = "60分钟以上/次 ";
		}
		return ssportTime;
	}
}
