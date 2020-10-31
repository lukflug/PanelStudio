package com.lukflug.clickgui.settings;

/**
 * Setting representing an adjustable number.
 * @author lukflug
 * @param <T> the number type
 */
public interface NumberSetting<T extends Number & Comparable<T>> extends Setting<T> {
	/**
	 * Get the number as double.
	 * @param value the number
	 */
	public void fromDouble (double value);
}
