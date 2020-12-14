package com.lukflug.panelstudio.theme;

import java.awt.Color;

import com.lukflug.panelstudio.settings.ColorSetting;
import com.lukflug.panelstudio.settings.NumberSetting;

/**
 * Implementation of {@link ColorScheme} using {@link ColorSetting} and {@link NumberSetting}.
 * @author lukflug
 */
public class SettingsColorScheme implements ColorScheme {
	/**
	 * Setting to be used for {@link #getActiveColor()}.
	 */
	protected final ColorSetting activeColor;
	/**
	 * Setting to be used for {@link #getInactiveColor()}.
	 */
	protected final ColorSetting inactiveColor;
	/**
	 * Setting to be used for {@link #getBackgroundColor()}.
	 */
	protected final ColorSetting backgroundColor;
	/**
	 * Setting to be used for {@link #getOutlineColor()}.
	 */
	protected final ColorSetting outlineColor;
	/**
	 * Setting to be used for {@link #getFontColor()}.
	 */
	protected final ColorSetting fontColor;
	/**
	 * Setting to be used for {@link #getOpacity()}.
	 */
	protected final NumberSetting opacity;
	
	/**
	 * Constructor.
	 * @param activeColor active color setting
	 * @param inactiveColor inactive color setting
	 * @param backgroundColor background color setting
	 * @param outlineColor outline color setting
	 * @param fontColor font color setting
	 * @param opacity opacity setting
	 */
	public SettingsColorScheme (ColorSetting activeColor, ColorSetting inactiveColor, ColorSetting backgroundColor, ColorSetting outlineColor, ColorSetting fontColor, NumberSetting opacity) {
		this.activeColor=activeColor;
		this.inactiveColor=inactiveColor;
		this.backgroundColor=backgroundColor;
		this.outlineColor=outlineColor;
		this.fontColor=fontColor;
		this.opacity=opacity;
	}
	
	@Override
	public Color getActiveColor() {
		return activeColor.getValue();
	}

	@Override
	public Color getInactiveColor() {
		return inactiveColor.getValue();
	}

	@Override
	public Color getBackgroundColor() {
		return backgroundColor.getValue();
	}

	@Override
	public Color getOutlineColor() {
		return outlineColor.getValue();
	}

	@Override
	public Color getFontColor() {
		return fontColor.getValue();
	}

	@Override
	public int getOpacity() {
		return (int)opacity.getNumber();
	}

}
