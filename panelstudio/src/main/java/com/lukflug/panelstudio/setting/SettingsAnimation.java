package com.lukflug.panelstudio.setting;

import java.util.function.Supplier;

import com.lukflug.panelstudio.base.Animation;

/**
 * Implementation of {@link Animation} using a supplier.
 * @author lukflug
 */
public class SettingsAnimation extends Animation {
	/**
	 * Setting to be used for {@link #getSpeed()}.
	 */
	protected final Supplier<Integer> speed;

	/**
	 * Constructor.
	 * @param speed speed setting
	 */
	public SettingsAnimation (Supplier<Integer> speed, Supplier<Long> time) {
		super(time);
		this.speed=speed;
	}
	
	@Override
	protected int getSpeed() {
		return (int)speed.get();
	}
}
