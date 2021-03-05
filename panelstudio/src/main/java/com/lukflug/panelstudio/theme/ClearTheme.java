package com.lukflug.panelstudio.theme;

import java.awt.Color;

import com.lukflug.panelstudio.base.IBoolean;

/**
 * Theme corresponding to the appearance GameSense 2.0 and 2.1 had.
 * The parameter gradient in the constructor determines, if it appears like GameSense 2.0-2.1.1 (false) or like GameSense 2.1.2-2.1.5 (true).
 * @author lukflug
 */
public class ClearTheme extends ThemeBase {
	protected final boolean gradient;
	protected final int height,border;
	protected final String separator;
	
	public ClearTheme (IColorScheme scheme, boolean gradient, int height, int border, String separator) {
		super(scheme);
		this.gradient=gradient;
		this.height=height;
		this.border=border;
		this.separator=separator;
		scheme.createSetting(this,"Title Color","The color for panel titles.",false,true,new Color(90,145,240),false);
		scheme.createSetting(this,"Enabled Color","The main color for enabled components.",false,true,new Color(90,145,240),false);
		scheme.createSetting(this,"Background Color","The background color.",true,true,new Color(195,195,195,200),false);
		scheme.createSetting(this,"Font Color","The main color for text.",false,true,new Color(255,255,255),false);
	}

	@Override
	public IDescriptionRenderer getDescriptionRenderer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IContainerRenderer getContainerRenderer(int level) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> IPanelRenderer<T> getPanelRenderer(Class<T> type, int level) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> IScrollBarRenderer<T> getScrollBarRenderer(Class<T> type, int level) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> IEmptySpaceRenderer<T> getEmptySpaceRenderer(Class<T> type, int level) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> IButtonRenderer<T> getButtonRenderer(Class<T> type, int level, boolean container) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IButtonRenderer<IBoolean> getCheckMarkRenderer(int level, boolean container) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IButtonRenderer<String> getKeybindRenderer(int level, boolean container) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ISliderRenderer getSliderRenderer(int level, boolean container) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getBaseHeight() {
		return height+2;
	}

	@Override
	public Color getMainColor(boolean focus, boolean active) {
		if (active) return scheme.getColor("Enabled Color");
		else return new Color(0,0,0,0);
	}

	@Override
	public Color getBackgroundColor(boolean focus) {
		return scheme.getColor("Background Color");
	}

	@Override
	public Color getFontColor(boolean focus) {
		return scheme.getColor("Font Color");
	}
}
