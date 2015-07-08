package com.bskcare.ch.tg;
import java.util.List;
import org.springframework.util.CollectionUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.tg.TgCookedFood;


@SuppressWarnings("unchecked")
public class ModulePicker3 extends AbstractObjectPicker{
	

	public ModulePicker3() {
		super(true); //有鸡蛋
	}
	
	public TgMeals picker(PickerCondition pc) {
		System.out.println("---------ModulePicker3, getCalories = "
				+ pc.getCalories() + ", getClientId = " + pc.getClientId()
				+ ", isHasGruel = " + pc.isHasGruel() + ", isHasEgg = " + pc.isHasEggs());
		
		if(pc != null){
			GetFoodQuantity foodQuantity = new GetFoodQuantity();
			//早餐
			String zaocan = "";
			//午餐
			String wucan = "";
			//晚餐
			String wancan = "";
			
			List<TgCookedFood> lstCookedFood = pc.getCookedFoodDao().queryCookedFood(pc.getDisease());
			
			//早、午、晚主食的信息
			List<String> lstZhushi = foodQuantity.getZhushi(pc, 3, lstCookedFood);
			if(!CollectionUtils.isEmpty(lstZhushi)&&lstZhushi.size()==3){
				//早餐主食
				String zaocanZhushi = lstZhushi.get(0);
				//午餐主食
				String wucanZhushi = lstZhushi.get(1);
				//晚餐主食
				String wancanZhushi = lstZhushi.get(2);
				//早餐除主食以外其他的信息
				String zaocanOther = foodQuantity.getZaocanOther(pc, 3, lstCookedFood);
				if(!StringUtils.isEmpty(zaocanZhushi)){
					zaocan += zaocanZhushi;
				}
				if(!StringUtils.isEmpty(zaocanOther)){
					zaocan += "，"+zaocanOther;
				}
				//午餐除主食的信息
				String wucanOther = foodQuantity.getWucanOther(pc, zaocan, 3, lstCookedFood);
				if(!StringUtils.isEmpty(wucanZhushi)){
					wucan += wucanZhushi;
				}
				if(!StringUtils.isEmpty(wucanOther)){
					wucan += "，"+wucanOther;
				}
				//晚餐除主食以外的信息
				String wancanOther = foodQuantity.getWancanOther(pc, lstCookedFood);
				if(!StringUtils.isEmpty(wancanZhushi)){
					wancan += wancanZhushi;
				}
				if(!StringUtils.isEmpty(wancanOther)){
					wancan += "，"+wancanOther;
				}
			}
			
			//早加餐
			String zaojia = foodQuantity.getZaojia(pc, lstCookedFood);
			//午加餐
			String wujia = foodQuantity.getWujia(pc, lstCookedFood);
			//晚加餐
			String wanjia = foodQuantity.getWanjia(pc, lstCookedFood);

			TgMeals meals = new TgMeals(zaocan, zaojia, wucan, wujia, wancan,
					wanjia);
			return meals;
			
		}
		return null;
	}

	public Class picksTo() {
		return ModulePicker3.class;
	}

}
