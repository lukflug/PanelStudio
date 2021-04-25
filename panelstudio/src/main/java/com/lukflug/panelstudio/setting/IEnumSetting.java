package com.lukflug.panelstudio.setting;

/**
 * A setting representing an enumeration.
 * @author lukflug
 */
public interface IEnumSetting extends ISetting<String> {
	/**
	 * Cycle through the values of the enumeration.
	 */
	public void increment();
	
	public void decrement();
	
	/**
	 * Get the current value.
	 * @return the name of the current enum value
	 */
	public String getValueName();
	
	public default int getValueIndex() {
		ILabeled stuff[]=getAllowedValues();
		String compare=getValueName();
		for (int i=0;i<stuff.length;i++) {
			if (stuff[i].getDisplayName().equals(compare)) return i; 
		}
		return -1;
	}
	
	public void setValueIndex (int index);
	
	public ILabeled[] getAllowedValues();
	
	@Override
	public default String getSettingState() {
		return getValueName();
	}
	
	@Override
	public default Class<String> getSettingClass() {
		return String.class;
	}
}
