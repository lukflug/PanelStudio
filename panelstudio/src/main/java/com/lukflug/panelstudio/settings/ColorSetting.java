package com.lukflug.panelstudio.settings;

import java.awt.Color;

/**
 * Setting representing a color.
 * @author lukflug
 */
public interface ColorSetting {
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
}
