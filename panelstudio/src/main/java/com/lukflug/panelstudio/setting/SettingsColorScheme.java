package com.lukflug.panelstudio.setting;

import java.awt.Color;

import com.lukflug.panelstudio.theme.IColorScheme;

/**
 * Implementation of {@link IColorScheme} using {@link IColorSetting} and {@link INumberSetting}.
 * @author lukflug
 */
public class SettingsColorScheme implements IColorScheme {
	/**
	 * Setting to be used for {@link #getActiveColor()}.
	 */
	protected final IColorSetting activeColor;
	/**
	 * Setting to be used for {@link #getInactiveColor()}.
	 */
	protected final IColorSetting inactiveColor;
	/**
	 * Setting to be used for {@link #getBackgroundColor()}.
	 */
	protected final IColorSetting backgroundColor;
	/**
	 * Setting to be used for {@link #getOutlineColor()}.
	 */
	protected final IColorSetting outlineColor;
	/**
	 * Setting to be used for {@link #getFontColor()}.
	 */
	protected final IColorSetting fontColor;
	/**
	 * Setting to be used for {@link #getOpacity()}.
	 */
	protected final INumberSetting opacity;
	
	/**
	 * Constructor.
	 * @param activeColor active color setting
	 * @param inactiveColor inactive color setting
	 * @param backgroundColor background color setting
	 * @param outlineColor outline color setting
	 * @param fontColor font color setting
	 * @param opacity opacity setting
	 */
	public SettingsColorScheme (IColorSetting activeColor, IColorSetting inactiveColor, IColorSetting backgroundColor, IColorSetting outlineColor, IColorSetting fontColor, INumberSetting opacity) {
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
