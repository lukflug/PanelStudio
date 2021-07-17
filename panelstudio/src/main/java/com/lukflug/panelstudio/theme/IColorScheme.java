package com.lukflug.panelstudio.theme;

import java.awt.Color;

/**
 * Interface to provide a means for GUI methods to access color setting set by the user.
 * @author lukflug
 */
public interface IColorScheme {
	/**
	 * Called by theme to create a color setting.
	 * @param theme the theme in question
	 * @param name the setting name
	 * @param description the setting description
	 * @param hasAlpha whether this setting should enable alpha
	 * @param allowsRainbow whether this setting should enable rainbows
	 * @param color the default color
	 * @param rainbow whether rainbow is enabled by default
	 */
	public void createSetting (ITheme theme, String name, String description, boolean hasAlpha, boolean allowsRainbow, Color color, boolean rainbow);
	
	/**
	 * Get value of color setting.
	 * @param name the setting name
	 * @return the current value for that setting
	 */
	public Color getColor (String name);
}
