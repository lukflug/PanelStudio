package com.lukflug.panelstudio.setting;

/**
 * A setting representing an enumeration.
 * @author lukflug
 */
public interface IEnumSetting extends ISetting {
	/**
	 * Cycle through the values of the enumeration.
	 */
	public void increment();
	
	/**
	 * Get the current value.
	 * @return the name of the current enum value
	 */
	public String getValueName();
}
