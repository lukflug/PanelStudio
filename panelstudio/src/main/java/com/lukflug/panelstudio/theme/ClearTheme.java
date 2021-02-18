package com.lukflug.panelstudio.theme;

import java.awt.Color;

/**
 * Theme corresponding to the appearance GameSense 2.0 and 2.1 had.
 * The parameter gradient in the constructor determines, if it appears like GameSense 2.0-2.1.1 (false) or like GameSense 2.1.2-2.1.5 (true).
 * @author lukflug
 */
public class ClearTheme extends ThemeBase {
	protected final boolean gradient;
	
	public ClearTheme (IColorScheme scheme, boolean gradient, int height, int border) {
		super(scheme);
		this.gradient=gradient;
	}

	@Override
	public Color getMainColor(boolean focus, boolean active) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color getBackgroundColor(boolean focus) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color getFontColor(boolean focus) {
		// TODO Auto-generated method stub
		return null;
	}
}
