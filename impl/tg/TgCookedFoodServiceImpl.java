package com.bskcare.ch.service.impl.tg;

import java.text.DecimalFormat;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bskcare.ch.bo.RiskResultBean;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.tg.TgCookedFoodDao;
import com.bskcare.ch.dao.timeLine.TimeLineTaskDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.tg.TgCookedFoodService;
import com.bskcare.ch.tg.PickerCondition;
import com.bskcare.ch.tg.PickerUtils;
import com.bskcare.ch.tg.TgMeals;
import com.bskcare.ch.util.LogFormat;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.tg.TgCookedFood;

@Service
@SuppressWarnings("unchecked")
public class TgCookedFoodServiceImpl implements TgCookedFoodService {

	@Autowired
	private TgCookedFoodDao cookedFoodDao;
	@Autowired
	private TimeLineTaskDao lineTaskDao;

	private transient final Logger log = Logger.getLogger(getClass());

	public TgCookedFood addTgCookedFood(TgCookedFood cookedFood) {
		if (cookedFood != null) {
			return cookedFoodDao.add(cookedFood);
		}
		return null;
	}

	//FIXME 命名
	public void UpdateTgCookedFood(TgCookedFood cookedFood) {
		if (cookedFood != null) {
			cookedFoodDao.update(cookedFood);
		}
	}

	public TgCookedFood queryTgCookedFoodById(Integer id) {
		if (id != null) {
			return cookedFoodDao.load(id);
		}
		return null;
	}

	public PageObject queryTgCookedFood(TgCookedFood cookedFood,
			QueryInfo queryInfo) {
		return cookedFoodDao.queryTgCookedFood(cookedFood, queryInfo);
	}

	// 参数： clientId：客户id calories：热量 disease：app的来源是糖尿病 还是高血压
	public TgMeals getMealsInfo(Integer clientId, double calories, int softType,
			String hebingDisease, RiskResultBean risk) {
		
		log.info(LogFormat.b("开始获取食谱， clientId = " + clientId + ", cal = " + calories 
				+ ", softType = " + softType) + ", hebing = " + hebingDisease);
		
		PickerCondition pc = new PickerCondition();
		pc.setCalories(calories);
		pc.setClientId(clientId);
		
		if (softType == Constant.SOFT_GUAN_XUE_TANG) {
			pc.setDisease("tangniaobing");
		} else if (softType == Constant.SOFT_GUAN_XUE_YA) {
			pc.setDisease("gaoxueya");
		}
		
		if (!StringUtils.isEmpty(hebingDisease)) {
			pc.setHebingDisease(hebingDisease);
		}

		pc.setCookedFoodDao(cookedFoodDao);
		pc.setLineTaskDao(lineTaskDao);
		pc.setRisk(risk);
		
		TgMeals meals = PickerUtils.getTgMealssss(pc);
		meals = addQuantity(meals, pc);//添加量。
//		meals = addTitle(meals);//添加提示语句。
		
		if (meals != null) {
			return meals;
		} else {
			log.info(LogFormat.f("获取食谱结束，没有获取到任何食谱信息"));
		}
		
		return null;
	}

	public TgMeals addQuantity(TgMeals meals, PickerCondition pc) {
		if(meals==null) {
			log.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			log.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			log.error(">>>>>>>>>>>>>>>>>>>这个用户生成推荐食物失败，---->" + (null != pc ? pc.getClientId() : "pc is null"));
			log.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			log.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			return null;
		}
		
		DecimalFormat df = new DecimalFormat("#.00");
		double calories = pc.getCalories()/3;
		
		String zhushi = "主食：谷类"+df.format((pc.getCalories()*pc.getZhushixishu()-180)/4/3*5/4)+"克，";
		zhushi += "约馒头"+df.format((pc.getCalories()*pc.getZhushixishu()-180)/3/4*5/4*1.5/50)+"两，";
		zhushi += "约米饭"+df.format((pc.getCalories()*pc.getZhushixishu()-180)/3/4*5/4*2.6/50)+"两，";
		zhushi += "约面条"+df.format((pc.getCalories()*pc.getZhushixishu()-180)/3/4*5/4*4/3/50)+"两。\n";
		
		//早餐
		String zaocan = "\n早餐热卡总量"+df.format(calories)+"千卡\n"+ zhushi+"蔬菜：150克左右，\n" +
				"蛋类："+df.format(pc.getCalories()*pc.getDouxishu()/4*3/5/4*60/9)+"克," +
				"或豆腐丝"+df.format(pc.getCalories()*pc.getDouxishu()/4*3/5/4*50/9)+"克。";
		meals.setZaocan(zaocan+"\n推荐样板：" + meals.getZaocan());
		
		//午餐
		String wucan = "\n午餐热卡总量"+df.format(calories)+"千卡\n"+ zhushi+"蔬菜：200克左右，\n" +
				"豆制品：肉类"+df.format(pc.getCalories()*pc.getRouxishu()/4*3/5/4*50/9)+"克," +
				"或豆腐"+df.format(pc.getCalories()*pc.getDouxishu()/4*3/5/4*100/9)+"克。";
		meals.setWucan(wucan+"\n推荐样板：" + meals.getWucan());
		
		//晚餐
		String wancan = "\n晚餐热卡总量"+df.format(calories)+"千卡\n"+ zhushi + "蔬菜：150克左右，\n" +
				"豆制品：豆干"+df.format(pc.getCalories()*pc.getDouxishu()/4*3/5/4*50/9)+"克," +
				"或瘦肉"+df.format(pc.getCalories()*pc.getRouxishu()/4*3/5/4*50/9)+"克。";
		meals.setWancan(wancan+"\n推荐样板：" + meals.getWancan());
		return meals;
	}
	public TgMeals addTitle(TgMeals meals){
		meals.setZaocan("高质量早餐可以预防常见于上午10点左右的低血糖反应，甚至对于稳定早餐后血糖甚至全天的血糖都有帮助。\n"+meals.getZaocan());
		meals.setZaojia("合理加餐，非但不会加重病情，还能起到平稳血糖的功效。\n"+meals.getZaojia());
		meals.setWucan("午餐合理搭配不仅使下午的血糖更稳定，释放缓慢，大脑中的糖来源更持久，帮助稳定餐后血糖。\n"+meals.getWucan());
		meals.setWujia("一杯牛奶强壮一个民族，钙吸收率高，也是优质蛋白最好来源。\n"+meals.getWujia());
		meals.setWancan("不要因为白天工作忙，无暇严格饮食，一到晚餐就很丰盛。这会加重病情。\n"+meals.getWancan());
		return meals;
	}
	public List<TgCookedFood> queryCookByName(String name) {
		return cookedFoodDao.queryCookByName(name);
	}

	public void delete(Integer id) {
		cookedFoodDao.delete(id);
	}
}
