package com.lukflug.panelstudio.theme;

import java.awt.Color;
import java.util.stream.Stream;

/**
 * Interface to provide a means for GUI methods to access color setting set by the user.
 * @author lukflug
 */
public interface IColorScheme {
	/**
	 * Called by the theme to provide the list of color settings needed
	 * @param theme the theme object used
	 * @param settings
	 */
	public void createSettings (ITheme theme, Stream<String> settings);
	
	/**
	 * Get value of color setting.
	 * @param setting the setting name
	 * @return the current value for that setting
	 */
	public Color getColor (String setting);
	
	/**
	 * Get value of opacity setting.
	 * @return the current value for opacity
	 */
	public int getOpacity();
}
