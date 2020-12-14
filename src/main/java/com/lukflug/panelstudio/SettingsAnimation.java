package com.lukflug.panelstudio;

import com.lukflug.panelstudio.settings.NumberSetting;

/**
 * Implementation of {@link Animation} using {@link NumberSetting}.
 * @author lukflug
 */
public class SettingsAnimation extends Animation {
	/**
	 * Setting to be used for {@link #getSpeed()}.
	 */
	protected final NumberSetting speed;

	/**
	 * Constructor.
	 * @param speed speed setting
	 */
	public SettingsAnimation (NumberSetting speed) {
		this.speed=speed;
	}
	
	@Override
	protected int getSpeed() {
		return (int)speed.getNumber();
	}
}
