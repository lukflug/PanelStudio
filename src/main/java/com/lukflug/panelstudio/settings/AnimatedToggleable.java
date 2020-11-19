package com.lukflug.panelstudio.settings;

import com.lukflug.panelstudio.Animation;

/**
 * Animation that is also a toggle.
 * @author lukflug
 */
public final class AnimatedToggleable implements Toggleable {
	/**
	 * The toggleable.
	 */
	private final Toggleable toggle;
	/**
	 * The animation.
	 */
	private final Animation animation;
	
	/**
	 * Constructor.
	 * @param toggle the toggleable
	 * @param animation the animation
	 */
	public AnimatedToggleable (Toggleable toggle, Animation animation) {
		this.toggle=toggle;
		this.animation=animation;
		if (toggle.isOn()) animation.initValue(1);
		else animation.initValue(0);
	}
	
	/**
	 * Toggle the toggle.
	 */
	@Override
	public void toggle() {
		toggle.toggle();
		if (toggle.isOn()) animation.setValue(1);
		else animation.setValue(0);
	}

	@Override
	public boolean isOn() {
		return toggle.isOn();
	}
	
	public double getValue() {
		if (animation.getTarget()!=(toggle.isOn()?1:0)) {
			if (toggle.isOn()) animation.setValue(1);
			else animation.setValue(0);
		}
		return animation.getValue();
	}
}
