package com.bskcare.ch.tg;

@SuppressWarnings("unchecked")
public class ModulePicker6 extends AbstractObjectPicker{

	public ModulePicker6() {
		super(true); //没鸡蛋
	}
	
	public TgMeals picker(PickerCondition pc) {
		System.out.println("---------ModulePicker6, getCalories = "
				+ pc.getCalories() + ", getClientId = " + pc.getClientId()
				+ ", isHasGruel = " + pc.isHasGruel() + ", isHasEgg = " + pc.isHasEggs());
		return null;
	}

	@Override
	public Class picksTo() {
		return ModulePicker6.class;
	}

}
