package com.lukflug.panelstudio;

/**
 * Class representing an animation.
 * @author lukflug
 */
public abstract class Animation {
	/**
	 * Current value.
	 */
	protected double value;
	/**
	 * Past value.
	 */
	protected double lastValue;
	/**
	 * Time of last value transition.
	 */
	protected long lastTime=System.currentTimeMillis();
	
	/**
	 * Set a value immediately, without an transition animation.
	 * @param value the new value
	 */
	public void initValue(double value) {
		this.value=value;
		lastValue=value;
	}
	
	/**
	 * The the current value.
	 * @return an interpolated value between {@link #value} and {@link #lastValue} depending on the current time
	 */
	public double getValue() {
		if (getSpeed()==0) return value;
		double weight=(System.currentTimeMillis()-lastTime)/(double)getSpeed();
		if (weight>=1) return value;
		else if (weight<=0) return lastValue;
		return value*weight+lastValue*(1-weight);
	}
	
	/**
	 * Get the target value.
	 * @return the current {@link #value}
	 */
	public double getTarget() {
		return value;
	}
	
	/**
	 * Set the value, with a transition between the old and new value.
	 * @param value the new value
	 */
	public void setValue(double value) {
		lastValue=getValue();
		this.value=value;
		lastTime=System.currentTimeMillis();
	}
	
	/**
	 * Used to obtain the animation speed.
	 * @return time a transition should take in milliseconds
	 */
	protected abstract int getSpeed();
}
