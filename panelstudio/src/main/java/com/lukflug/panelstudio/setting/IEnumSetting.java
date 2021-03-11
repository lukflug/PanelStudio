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
	
	/**
	 * Get the current value.
	 * @return the name of the current enum value
	 */
	public String getValueName();
	
	@Override
	public default String getSettingState() {
		return getValueName();
	}
	
	@Override
	public default Class<String> getSettingClass() {
		return String.class;
	}
}
