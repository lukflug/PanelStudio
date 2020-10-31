package com.lukflug.panelstudio.settings;

import java.awt.Color;

/**
 * Setting representing a color.
 * @author lukflug
 */
public interface ColorSetting extends Setting<Color> {
	/**
	 * Get the color, ignoring the rainbow.
	 * @return the color of the color setting
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
