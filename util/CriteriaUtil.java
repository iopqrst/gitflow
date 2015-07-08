package com.bskcare.ch.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CriteriaUtil {
	
	
	private static CriteriaUtil criteriaUtil = null;
	
	private CriteriaUtil(){};
	
	public static CriteriaUtil getCriteriaUtil(){
		if(criteriaUtil == null){
			synchronized (CriteriaUtil.class) {
				criteriaUtil = new CriteriaUtil();
			}
		}
		return criteriaUtil;
	}
	
	
	private Map<String,String> initMap(){
		Map<String,String> map = new HashMap<String, String>();
		map.put("高血压", "您低盐低脂饮食，适当活动，控制好血压。");
		map.put("高尿酸血症", "常与肥胖、糖代谢紊乱、高血压、动脉粥样硬化和冠心病等聚集发生，建议治疗相关疾病。");
		map.put("冠心病", "您调脂抗凝治疗，避免情绪激动。");
		map.put("脑卒中", "您控制好血压；加强康复锻炼；避免褥疮、下肢静脉血栓、坠积性肺炎等并发症的发生。");
		map.put("动脉粥样硬化", "您坚持抗凝治疗，控制血脂血压。");
		map.put("胃炎", "您避免服用损害胃粘膜的药物如消炎痛、激素等，积极治疗幽门螺旋菌感染。");
		map.put("甲亢", "您避免精神诱因、细菌感染、过度刺激等诱发因素，定期复查甲状腺功能。");
		map.put("心律失常", "您避免紧张情绪，避免饮浓茶、咖啡，保证睡眠，定期监测心电图。");
		map.put("肾病", "您优质蛋白饮食，定期复查肾功。");
		map.put("甲减", "您按时服药，定期检查甲状腺功能。");
		map.put("低血压", "您适量增加饮水，变换体位时动作应缓慢，防止血压突然下降。");
		map.put("慢支炎", "戒烟，增强体质，预防感冒，坚持家庭氧疗。");
		map.put("肺气肿", "戒烟，增强体质，预防感冒，坚持家庭氧疗。");
		map.put("哮喘", "远离花粉、尘螨、冷空气等过敏原。");
		map.put("其他呼吸系统疾病", "您注意保暖，预防感冒，坚持治疗，定期复查肺功能。");
		map.put("痛风", "服用排尿酸药物及抑制尿酸生成药物，使血尿酸控制在正常水平。");
		map.put("高脂血症", "您规律的体力锻炼，控制体重，低脂饮食，按时降脂药物治疗，定期复查血脂。");
		map.put("前列腺疾病", "您保持清洁，避免受寒，避免饮酒及辛辣食物，及时药物治疗泌尿系统感染。");
		map.put("其他骨关节病", "您避免长时间关节活动，减轻关节承重，注意关节保暖，坚持补钙，防止骨质疏松。");
		map.put("贫血", "您适当补充铁剂，加强营养，对因治疗，定期复查血常规。");
		map.put("低血糖", "您规律饮食，外出时随身携带糖块，避免低血糖发生，定期监测血糖。");
		map.put("风湿性关节炎", "您注意劳逸结合，预防和控制感染，遵医嘱个体化抗风湿治疗。");
		map.put("类风湿性关节炎", "您适当的关节功能锻炼，适当增强免疫力，遵医嘱个体治疗。");
		map.put("妇科疾病", "您注意个人卫生，遵医嘱个体化治疗，定期妇科检查。");
		map.put("便秘", "您多饮水，高纤维饮食，积极锻炼身体，养成良好的排便习惯。");
		return map;
	}
	
	
	public String criteria(String disease){
		String criteria = "";
		Map<String,String> map = initMap();
		Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();  
        while (iterator.hasNext()) {  
            Map.Entry<String, String> entry = iterator.next();  
            if(entry.getKey().equals(disease)){
            	System.out.println("key = " + entry.getKey() + " and value = " + entry.getValue()); 
            	criteria = entry.getValue();
            }
        }  
		return criteria;
	}
	
	
	public static void main(String[] args) {
		String str1 = new String();
		String str2 = new String();
		
		str1 += "a";
		System.out.println(str1 == str2);
		
		String str3 = "a";
		String str4 = "a";
		
		System.out.println(str3 == str4);
		
		String str5 = new String("a");
		String str6 = new String("a");
		
		str1 += "a";
		System.out.println(str5 == str6);
	}
}
