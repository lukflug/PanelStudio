package com.lukflug.examplemod8forge.setting;

import java.util.Arrays;

import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.setting.IEnumSetting;
import com.lukflug.panelstudio.setting.ILabeled;

public class EnumSetting<E extends Enum<E>> extends Setting<E> implements IEnumSetting {
	private final Class<E> settingClass;
	private final ILabeled[] array;
	
	public EnumSetting (String displayName, String configName, String description, IBoolean visible, E value, Class<E> settingClass) {
		super(displayName,configName,description,visible,value);
		this.settingClass=settingClass;
		array=Arrays.stream(settingClass.getEnumConstants()).map(v->{
			return new ILabeled() {
				@Override
				public String getDisplayName() {
					return v.toString();
				}
			};
		}).toArray(ILabeled[]::new);
	}

	@Override
	public void increment() {
		E[] array=settingClass.getEnumConstants();
		int index=getValue().ordinal()+1;
		if (index>=array.length) index=0;
		setValue(array[index]);
	}

	@Override
	public void decrement() {
		E[] array=settingClass.getEnumConstants();
		int index=getValue().ordinal()-1;
		if (index<0) index=array.length-1;
		setValue(array[index]);
	}

	@Override
	public String getValueName() {
		return getValue().toString();
	}
	
	@Override
	public int getValueIndex() {
		return getValue().ordinal();
	}

	@Override
	public void setValueIndex(int index) {
		setValue(settingClass.getEnumConstants()[index]);
	}

	@Override
	public ILabeled[] getAllowedValues() {
		return array;
	}
}
