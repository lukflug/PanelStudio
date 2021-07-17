package com.lukflug.panelstudio.setting;

import java.util.stream.Stream;

/**
 * Interface representing a single setting.
 * @author lukflug
 */
public interface ISetting<T> extends ILabeled {
	public T getSettingState();
	
	public Class<T> getSettingClass();
	
	/**
	 * Returns sub settings.
	 * @return sub-settings
	 */
	public default Stream<ISetting<?>> getSubSettings() {
		return null;
	}
}
