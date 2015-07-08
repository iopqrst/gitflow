package com.bskcare.ch.tg;

public abstract class AbstractObjectPicker implements Picker {

	/** 是否有鸡蛋 */
	public boolean hasEgg = false;
	/** 是否有粥 */
	public boolean hasGruel = false;
	
	public AbstractObjectPicker(){}
	
	public AbstractObjectPicker(boolean hasEgg){
		this.hasEgg = hasEgg;
	}
	
	public AbstractObjectPicker(boolean hasEgg, boolean hasGruel){
		this.hasEgg = hasEgg;
		this.hasGruel = hasGruel;
	}
	
	public abstract TgMeals picker(PickerCondition pc);

	public abstract Class picksTo();

	public boolean isHasEgg() {
		return hasEgg;
	}

	public void setHasEgg(boolean hasEgg) {
		this.hasEgg = hasEgg;
	}

	public boolean isHasGruel() {
		return hasGruel;
	}

	public void setHasGruel(boolean hasGruel) {
		this.hasGruel = hasGruel;
	}


}
