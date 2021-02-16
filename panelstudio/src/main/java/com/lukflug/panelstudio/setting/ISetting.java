package com.lukflug.panelstudio.setting;

import java.util.stream.Stream;

/**
 * Interface representing a single setting.
 * @author lukflug
 */
public interface ISetting extends ILabeled {
	/**
	 * Returns sub settings.
	 * @return sub-settings
	 */
	public default Stream<ISetting> getSubSettings() {
		return null;
	}
}
