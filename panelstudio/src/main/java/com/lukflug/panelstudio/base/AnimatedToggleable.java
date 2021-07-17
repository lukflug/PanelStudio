package com.lukflug.panelstudio.base;

/**
 * Animation that is also a toggle.
 * @author lukflug
 */
public final class AnimatedToggleable implements IToggleable {
	/**
	 * The toggleable.
	 */
	private final IToggleable toggle;
	/**
	 * The animation.
	 */
	private final Animation animation;
	
	/**
	 * Constructor.
	 * @param toggle the toggleable
	 * @param animation the animation
	 */
	public AnimatedToggleable (IToggleable toggle, Animation animation) {
		if (toggle!=null) this.toggle=toggle;
		else this.toggle=new SimpleToggleable(false);
		if (animation!=null) this.animation=animation;
		else this.animation=new Animation(System::currentTimeMillis) {
			@Override
			protected int getSpeed() {
				return 0;
			}
		};
		if (this.toggle.isOn()) this.animation.initValue(1);
		else this.animation.initValue(0);
	}
	
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
