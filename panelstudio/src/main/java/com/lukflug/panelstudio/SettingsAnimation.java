package com.lukflug.panelstudio;

import com.lukflug.panelstudio.settings.INumberSetting;

/**
 * Implementation of {@link Animation} using {@link INumberSetting}.
 * @author lukflug
 */
public class SettingsAnimation extends Animation {
	/**
	 * Setting to be used for {@link #getSpeed()}.
	 */
	protected final INumberSetting speed;

	/**
	 * Constructor.
	 * @param speed speed setting
	 */
	public SettingsAnimation (INumberSetting speed) {
		this.speed=speed;
	}
	
	@Override
	protected int getSpeed() {
		return (int)speed.getNumber();
	}
}
