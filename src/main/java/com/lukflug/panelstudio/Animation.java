package com.lukflug.panelstudio;

/**
 * Class representing an animation.
 * @author lukflug
 */
public abstract class Animation {
	protected double value;
	protected double lastValue;
	protected long lastTime=System.currentTimeMillis();
	
	public void initValue(double value) {
		this.value=value;
		lastValue=value;
	}
	
	public double getValue() {
		double weight=(System.currentTimeMillis()-lastTime)/(double)getSpeed();
		if (weight>=1) return value;
		else if (weight<=0) return lastValue;
		return value*weight+lastValue*(1-weight);
	}
	
	public void setValue(double value) {
		lastValue=getValue();
		this.value=value;
		lastTime=System.currentTimeMillis();
	}
	
	protected abstract int getSpeed();
}
