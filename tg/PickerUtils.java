package com.bskcare.ch.tg;
import org.apache.log4j.Logger;
import com.bskcare.ch.util.StringUtils;

public class PickerUtils {

	private static final PickerRegistry pickerRegistry = new PickerRegistry();
	
	private static final Logger log = Logger.getLogger(PickerUtils.class);
	
	static {
		registerStandardPicker(pickerRegistry);
	}
	
	public static void registerStandardPicker(PickerRegistry pickerRegistry) {
		pickerRegistry.clear();

		pickerRegistry.registerPicker(new ModulePicker1());
		pickerRegistry.registerPicker(new ModulePicker2());
		pickerRegistry.registerPicker(new ModulePicker3());
		pickerRegistry.registerPicker(new ModulePicker4());
		pickerRegistry.registerPicker(new ModulePicker5());
	}

	public static PickerRegistry getPickerRegistry() {
		return pickerRegistry;
	}
	
	/**
	 * 挑选一个合适的食物
	 */
	public static TgMeals getTgMealssss(PickerCondition pc) {
		
		// 1. 判断是什么并合症
		//1.1 如果是对鸡蛋有限制的要去查询最近一周蛋的情况
			//1.1.1 可以有鸡蛋 ： 任意选
			//1.1.2 不能有鸡蛋 ： 任何餐次都不能不能选有鸡蛋的
			//1.1.3 判断最近一周是否有粥
				// 有 ： 不选有粥的模式  --> 传入有粥的条件（只能选汤）
				// 没有：任何模式
		
		if(null == pc || null == pc.getClientId() ||  pc.getCalories() <= 0 ) {
			return null;
		}
		
		//String diseases = getMergingDisease(pc.getClientId());
		
		//根据疾病给计算熟食的各个系数赋值
		setXishuByDisease(pc);
		
		int eatingEggsCount = 0;
		//假如1为不能吃鸡蛋的并合症（该并合症一周最多可吃3个鸡蛋），其他都可以吃鸡蛋
		boolean eatingEggs = false;
//		boolean drinkingGruel = true;
		
		// 判断什么并合症，并且根据并合症判断鸡蛋的量
		if(pc.getEggEvery()==0 
				&& (eatingEggsCount = queryEatingEggsCount(pc.getClientId(), pc)) >= 3){ 

			log.info("最近一周吃了" + eatingEggsCount + "鸡蛋,不能再吃鸡蛋了！！");
			System.out.println("最近一周吃了" + eatingEggsCount + "鸡蛋,不能再吃鸡蛋了！！");
			//不能选有鸡蛋的模式
			//只有模式4没有鸡蛋，其他的模式都有鸡蛋
		} else {
			//可以选有鸡蛋的模式
			log.info("最近一周没吃多少鸡蛋，还可以再吃！！");
			System.out.println("最近一周没吃多少鸡蛋，还可以再吃！！");
			eatingEggs = true;
		}
		
		//判断最近一周是否有粥
/*		int queryDrinkingGruelCount = queryDrinkingGruelCount(pc.getClientId(), pc);
		if(queryDrinkingGruelCount > 0) {
			log.info("本周已经喝过粥了，本次不能再喝了！！");
			System.out.println("本周已经喝过粥了，本次不能再喝了！！");
			// 不能在喝粥了
			drinkingGruel = false;
		} else {
			log.info("本次可以喝粥");
			System.out.println("本次可以喝粥");
			drinkingGruel =  true;
		}
*/		
		Picker relPic = pickerRegistry.selectPicker(eatingEggs);
		
		if(null == relPic) {
			System.out.println("没有周到是适合的选择器");
			return null;
		}
		
		pc.setHasEggs(eatingEggs);
//		pc.setHasGruel(drinkingGruel);
		TgMeals tm = relPic.picker(pc);
		
		return tm;
	}

	
	/**
	 * 查询某用户最近一周吃蛋的情况
	 * @param clientId
	 * @return 吃蛋的次数
	 */
	private static int queryEatingEggsCount(Integer clientId,PickerCondition pc) {
		//要查询吃鸡蛋的情况 
		int eggCount = pc.getLineTaskDao().queryCountFood(clientId, null, "蛋");
		return eggCount;
	}
	
	/**
	 * 查询某用户最近一周喝粥的情况
	 * @param clientId
	 * @return 喝粥的次数
	 */
	private static int queryDrinkingGruelCount(Integer clientId, PickerCondition pc) {
		//要查询喝粥的情况  
		int zouCount = pc.getLineTaskDao().queryCountFood(clientId, null, "粥");
		return zouCount;
	}
	
	/**
	 * 随机获取一定范围内的随机数
	 * @param n 开始数字（包含）
	 * @param m 结束范围（不包含）
	 * @return
	 */
	public static int getRandom(int n, int m) {
		return (int) (Math.random() * (m - n) + n);
	}
	
	
	public static void setXishuByDisease(PickerCondition pc){
		//合并症
		String disease = pc.getHebingDisease();
		//APP来源判断是糖尿病的合并症还是高血压的合并症
		String source = pc.getDisease();
		
		//糖尿病考虑糖尿病的合并症
		if(!StringUtils.isEmpty(source) && source.equals("tangniaobing")){
			if(!StringUtils.isEmpty(disease)){
				String[] diseases = disease.split("、");
				if(diseases != null && diseases.length > 0){
					if(disease.length() == 1){
						if(disease.equals("0")){//糖尿病中度或重度人群
							pc.setZhushixishu(0.55);
							pc.setRouxishu(0.18);
							pc.setMilkxishu(0.18);
							pc.setDouxishu(0.18);
							pc.setNutsxishu(0.27);
							pc.setEggEvery(1);
						}else if(disease.equals("1")){//糖尿病合并冠心病
							pc.setZhushixishu(0.6);
							pc.setRouxishu(0.18);
							pc.setMilkxishu(0.18);
							pc.setDouxishu(0.18);
							pc.setNutsxishu(0.22);
							pc.setEggEvery(0);
						}else if(disease.equals("2")){//糖尿病合并高血压
							pc.setZhushixishu(0.6);
							pc.setRouxishu(0.18);
							pc.setMilkxishu(0.18);
							pc.setDouxishu(0.18);
							pc.setNutsxishu(0.22);
							pc.setEggEvery(0);
						}else if(disease.equals("3")){//糖尿病合并肾病
							pc.setZhushixishu(0.66);
							pc.setRouxishu(0.08);
							pc.setMilkxishu(0.08);
							pc.setDouxishu(0.08);
							pc.setNutsxishu(0.26);
							pc.setEggEvery(1);
						}else if(disease.equals("4")){//糖尿病合并脑梗
							pc.setZhushixishu(0.6);
							pc.setRouxishu(0.18);
							pc.setMilkxishu(0.18);
							pc.setDouxishu(0.18);
							pc.setNutsxishu(0.22);
							pc.setEggEvery(0);
						}else if(disease.equals("5")){//糖尿病合并视网膜病变
							pc.setZhushixishu(0.6);
							pc.setRouxishu(0.18);
							pc.setMilkxishu(0.18);
							pc.setDouxishu(0.18);
							pc.setNutsxishu(0.22);
							pc.setEggEvery(0);
						}else if(disease.equals("6")){//糖尿病合并下肢静脉栓塞
							pc.setZhushixishu(0.6);
							pc.setRouxishu(0.18);
							pc.setMilkxishu(0.18);
							pc.setDouxishu(0.18);
							pc.setNutsxishu(0.22);
							pc.setEggEvery(0);
						}else if(disease.equals("7")){//糖尿病足
							pc.setZhushixishu(0.6);
							pc.setRouxishu(0.18);
							pc.setMilkxishu(0.18);
							pc.setDouxishu(0.18);
							pc.setNutsxishu(0.22);
							pc.setEggEvery(0);
						}else if(disease.equals("9")){//糖尿病周围神经病变
							pc.setZhushixishu(0.6);
							pc.setRouxishu(0.18);
							pc.setMilkxishu(0.18);
							pc.setDouxishu(0.18);
							pc.setNutsxishu(0.22);
							pc.setEggEvery(0);
						}else if(disease.equals("10")){//糖尿病植物神经病变
							pc.setZhushixishu(0.6);
							pc.setRouxishu(0.18);
							pc.setMilkxishu(0.18);
							pc.setDouxishu(0.18);
							pc.setNutsxishu(0.22);
							pc.setEggEvery(0);
						}else if(disease.equals("11")){//糖尿病血脂紊乱
							pc.setZhushixishu(0.6);
							pc.setRouxishu(0.18);
							pc.setMilkxishu(0.18);
							pc.setDouxishu(0.18);
							pc.setNutsxishu(0.22);
							pc.setEggEvery(0);
						}
					}else{
						if(disease.contains("3")){//如果有多种合并症，并且合并症中有肾病，按肾病合并症
							pc.setZhushixishu(0.66);
							pc.setRouxishu(0.08);
							pc.setMilkxishu(0.08);
							pc.setDouxishu(0.08);
							pc.setNutsxishu(0.26);
							pc.setEggEvery(1);
						}else{//多种合并症
							pc.setZhushixishu(0.58);
							pc.setRouxishu(0.20);
							pc.setMilkxishu(0.20);
							pc.setDouxishu(0.20);
							pc.setNutsxishu(0.22);
							pc.setEggEvery(0);
						}
					}
				}
			}else{
				pc.setZhushixishu(0.6);
				pc.setRouxishu(0.16);
				pc.setMilkxishu(0.16);
				pc.setDouxishu(0.16);
				pc.setNutsxishu(0.24);
				pc.setEggEvery(1);
			}
		}
		
			
			
		//糖尿病考虑高血压的合并症
		if(!StringUtils.isEmpty(source) && source.equals("gaoxueya")){
			if(!StringUtils.isEmpty(disease)){
				String[] diseases = disease.split("、");
				if(disease.length() == 1){
					if(disease.equals("12")){ //没有合并症
						pc.setZhushixishu(0.6);
						pc.setRouxishu(0.15);
						pc.setMilkxishu(0.15);
						pc.setDouxishu(0.15);
						pc.setNutsxishu(0.25);
						pc.setEggEvery(0);
					}else if(diseases.equals("1")){
						pc.setZhushixishu(0.56);
						pc.setRouxishu(0.26);
						pc.setMilkxishu(0.26);
						pc.setDouxishu(0.26);
						pc.setNutsxishu(0.18);
						pc.setEggEvery(0);
					}else if(diseases.equals("2")){
						pc.setZhushixishu(0.66);
						pc.setRouxishu(0.08);
						pc.setMilkxishu(0.08);
						pc.setDouxishu(0.08);
						pc.setNutsxishu(0.26);
						pc.setEggEvery(0);
					}else if(diseases.equals("3")){
						pc.setZhushixishu(0.63);
						pc.setRouxishu(0.15);
						pc.setMilkxishu(0.15);
						pc.setDouxishu(0.15);
						pc.setNutsxishu(0.22);
						pc.setEggEvery(0);
					}else if(diseases.equals("4")){
						pc.setZhushixishu(0.63);
						pc.setRouxishu(0.15);
						pc.setMilkxishu(0.15);
						pc.setDouxishu(0.15);
						pc.setNutsxishu(0.22);
						pc.setEggEvery(0);
					}else if(diseases.equals("5")){
						pc.setZhushixishu(0.63);
						pc.setRouxishu(0.15);
						pc.setMilkxishu(0.15);
						pc.setDouxishu(0.15);
						pc.setNutsxishu(0.22);
						pc.setEggEvery(0);
					}else if(diseases.equals("6")){
						pc.setZhushixishu(0.63);
						pc.setRouxishu(0.15);
						pc.setMilkxishu(0.15);
						pc.setDouxishu(0.15);
						pc.setNutsxishu(0.22);
						pc.setEggEvery(0);
					}else if(diseases.equals("7")){
						pc.setZhushixishu(0.63);
						pc.setRouxishu(0.15);
						pc.setMilkxishu(0.15);
						pc.setDouxishu(0.15);
						pc.setNutsxishu(0.22);
						pc.setEggEvery(0);
					}
				}
			}
		}
	}
}
