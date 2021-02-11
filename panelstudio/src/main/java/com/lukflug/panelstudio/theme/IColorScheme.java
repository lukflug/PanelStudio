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
	 * @param setting the setting name
	 * @param allowsRainbow whether this setting should enable rainbows
	 * @param hasAlpha whether this setting should enable alpha
	 */
	public void createSetting (ITheme theme, String setting, boolean allowsRainbow, boolean hasAlpha);
	
	/**
	 * Get value of color setting.
	 * @param setting the setting name
	 * @return the current value for that setting
	 */
	public Color getColor (String setting);
}
