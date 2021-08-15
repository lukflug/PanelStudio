package com.lukflug.panelstudio.setting;

import java.util.stream.Stream;

/**
 * Interface representing a single setting.
 * @author lukflug
 */
public interface ISetting<T> extends ILabeled {
	/**
	 * Get the current setting value.
	 * @return the setting state
	 */
	public T getSettingState();
	
	/**
	 * Returns the class object of corresponding to the type returned by {@link #getSettingState()}.
	 * @return the settings class
	 */
	public Class<T> getSettingClass();
	
	/**
	 * Returns sub settings.
	 * @return sub-settings
	 */
	public default Stream<ISetting<?>> getSubSettings() {
		return null;
	}
}
