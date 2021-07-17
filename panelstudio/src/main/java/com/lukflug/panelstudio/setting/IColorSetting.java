package com.lukflug.panelstudio.setting;

import java.awt.Color;

/**
 * Setting representing a color.
 * @author lukflug
 */
public interface IColorSetting extends ISetting<Color> {
	/**
	 * Get the current value for the color setting.
	 * @return the current color
	 */
	public Color getValue();
	
	/**
	 * Set the non-rainbow color.
	 * @param value the value
	 */
	public void setValue (Color value);
	
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
	public default boolean hasAlpha() {
		return false;
	}
	
	/**
	 * Returns whether setting has rainbow option.
	 * @return whether setting allows rainbow
	 */
	public default boolean allowsRainbow() {
		return true;
	}
	
	/**
	 * Returns true for HSB model, false for RGB model
	 * @return returns whether HSB model should be used
	 */
	public default boolean hasHSBModel() {
		return false;
	}
	
	@Override
	public default Color getSettingState() {
		return getValue();
	}
	
	@Override
	public default Class<Color> getSettingClass() {
		return Color.class;
	}
}
