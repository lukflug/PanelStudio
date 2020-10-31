package com.lukflug.clickgui.settings;

/**
 * Base interface representing a setting.
 * @author lukflug
 * @param <T> the type of the setting
 */
public interface Setting<T> {
	/**
	 * Get the current value for the setting.
	 * @return the current value
	 */
	T getValue();
	
	/**
	 * Update the value for the setting.
	 * @param value the value
	 */
	void setValue (T value);
}
