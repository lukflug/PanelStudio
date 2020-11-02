package com.lukflug.panelstudio.settings;

/**
 * Setting representing an adjustable number.
 * @author lukflug
 * @param <T> the number type
 */
public interface NumberSetting {
	/**
	 * Get the number as double.
	 * @param value the number
	 */
	public double getValue();
	
	/**
	 * Set the number.
	 */
	public void setValue (double value);
	
	/**
	 * Get the maximum allowed value for the setting.
	 * @return maximum value
	 */
	public double getMaximumValue();
	
	/**
	 * Get the minimum allowed value for the setting.
	 * @return minimum value
	 */
	public double getMinimumValue();
	

	/**
	 * Get the setting's precision.
	 * @return decimal precision
	 */
	public int getPrecision();
}
