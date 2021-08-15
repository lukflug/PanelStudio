package com.lukflug.panelstudio.setting;

import java.util.Arrays;

/**
 * A setting representing an enumeration.
 * @author lukflug
 */
public interface IEnumSetting extends ISetting<String> {
	/**
	 * Cycle through the values of the enumeration.
	 */
	public void increment();
	
	/**
	 * Cycle through the values of the enumeration in inverse order
	 */
	public void decrement();
	
	/**
	 * Get the current value.
	 * @return the name of the current enum value
	 */
	public String getValueName();
	
	/**
	 * Get a sequential number of the current enum state.
	 * @return the index of the current value
	 */
	public default int getValueIndex() {
		ILabeled stuff[]=getAllowedValues();
		String compare=getValueName();
		for (int i=0;i<stuff.length;i++) {
			if (stuff[i].getDisplayName().equals(compare)) return i; 
		}
		return -1;
	}
	
	/**
	 * Set the current enum state by sequential number per {@link #getValueIndex()}.
	 * @param index the new value index
	 */
	public void setValueIndex (int index);
	
	/**
	 * Get a list of allowed enum states.
	 * @return list of enum values
	 */
	public ILabeled[] getAllowedValues();
	
	@Override
	public default String getSettingState() {
		return getValueName();
	}
	
	@Override
	public default Class<String> getSettingClass() {
		return String.class;
	}
	
	/**
	 * Get a list of enum values that are visible.
	 * @param setting the enum setting in question
	 * @return list of visible enum values
	 */
	public static ILabeled[] getVisibleValues (IEnumSetting setting) {
		return Arrays.stream(setting.getAllowedValues()).filter(value->value.isVisible().isOn()).toArray(ILabeled[]::new);
	}
}
