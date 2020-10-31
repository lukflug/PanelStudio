package com.lukflug.clickgui.settings;

/**
 * A setting representing an enumeration.
 * @author lukflug
 * @param <T> the enumeration in question
 */
public interface EnumSetting<T extends Enum<T>> extends Setting<T> {
	/**
	 * Cycle through the values of the enumeration.
	 */
	public void increment();
}
