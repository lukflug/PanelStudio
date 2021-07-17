package com.lukflug.panelstudio.theme;

import java.awt.Color;

import com.lukflug.panelstudio.base.IInterface;

/**
 * Base class for themes.
 * @author lukflug
 */
public abstract class ThemeBase implements ITheme {
	/**
	 * The color scheme.
	 */
	protected final IColorScheme scheme;
	/**
	 * The overridden main color.
	 */
	private Color overrideColor=null;
	
	/**
	 * Constructor.
	 * @param scheme the color scheme to use
	 */
	public ThemeBase (IColorScheme scheme) {
		this.scheme=scheme;
	}
	
	@Override
	public void loadAssets (IInterface inter) {
	}
	
	@Override
	public void overrideMainColor (Color color) {
		overrideColor=color;
	}
	
	@Override
	public void restoreMainColor() {
		overrideColor=null;
	}
	
	/**
	 * Get overridden color.
	 * @param color the default color
	 * @return the main color
	 */
	protected Color getColor (Color color) {
		if (overrideColor==null) return color;
		else return overrideColor;
	}
}
