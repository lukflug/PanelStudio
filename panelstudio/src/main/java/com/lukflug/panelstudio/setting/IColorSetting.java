package com.lukflug.panelstudio.setting;

import java.awt.Color;

/**
 * Setting representing a color.
 * @author lukflug
 */
public interface IColorSetting extends ISetting {
	/**
	 * Get the current value for the color setting.
	 * @return the current color
	 */
	Color getValue();
	
	/**
	 * Set the non-rainbow color.
	 * @param value the value
	 */
	void setValue (Color value);
	
	/**
	 * Get the color, ignoring the rainbow.
	 * @return the color ignoring the rainbow
	 */
	public Color getColor();
	
	/**
	 * Check if rainbow is enabled.
	 * @return set, if the rainbow is enabled
	 */
	public boolean getRainbow();
	
	/**
	 * Enable or disable the rainbow.
	 * @param rainbow set, if rainbow should be enabled
	 */
	public void setRainbow (boolean rainbow);
	
	/**
	 * Returns whether setting should have alpha slider
	 * @return whether alpha is enabled
	 */
	public boolean hasAlpha();
	
	/**
	 * Returns whether setting has rainbow option.
	 * @return whether setting allows rainbow
	 */
	public boolean allowsRainbow();
}
