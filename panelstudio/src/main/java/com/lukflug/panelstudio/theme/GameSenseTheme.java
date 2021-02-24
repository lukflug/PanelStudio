package com.lukflug.panelstudio.theme;

import java.awt.Color;
import java.awt.Point;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.base.IInterface;

/**
 * Recreates the appearance of GameSense 2.2.0.
 * @author lukflug
 */
public class GameSenseTheme extends ThemeBase {
	protected final int height,border,scroll;
	
	public GameSenseTheme (IColorScheme scheme, int height, int border, int scroll) {
		super(scheme);
		this.height=height;
		this.border=border;
		this.scroll=scroll;
		scheme.createSetting(this,"Title Color","The color for panel titles.",false,true,new Color(255,0,0),false);
		scheme.createSetting(this,"Outline Color","The color for panel outlines.",false,true,new Color(255,0,0),false);
		scheme.createSetting(this,"Enabled Color","The main color for enabled components.",true,true,new Color(255,0,0,150),false);
		scheme.createSetting(this,"Disabled Color","The main color for disabled modules.",false,true,new Color(0,0,0),false);
		scheme.createSetting(this,"Settings Color","The background color for settings.",false,true,new Color(32,32,32),false);
		scheme.createSetting(this,"Font Color","The main color for text.",false,true,new Color(255,255,255),false);
	}
	
	@Override
	public IDescriptionRenderer getDescriptionRenderer() {
		return new IDescriptionRenderer() {
			@Override
			public void renderDescription(IInterface inter, Point pos, String text) {
			}
		};
	}

	@Override
	public IContainerRenderer getContainerRenderer(int level) {
		return new IContainerRenderer() {
		};
	}
	
	@Override
	public <T> IPanelRenderer<T> getPanelRenderer (Class<T> type, int level) {
		if (type==Void.class) return (context,focus,state)->{};
		return super.getPanelRenderer(type,level);
	}
	
	@Override
	public <T> IScrollBarRenderer<T> getScrollBarRenderer (Class<T> type, int level) {
		if (type==Void.class) return new IScrollBarRenderer<T>() {
			@Override
			public int renderScrollBar(Context context, boolean focus, T state, boolean horizontal, int height, int position) {
				Color color=getMainColor(focus,true);
				context.getInterface().drawRect(context.getRect(),color,color,color,color);
				return position;
			}

			@Override
			public int getThickness() {
				return scroll;
			}
		};
		return super.getScrollBarRenderer(type,level);
	}
	
	@Override
	public <T> IEmptySpaceRenderer<T> getEmptySpaceRenderer (Class<T> type, int level) {
		if (type==Void.class) return (context,focus,state)->{};
		return super.getEmptySpaceRenderer(type,level);
	}
	
	@Override
	public <T> IButtonRenderer<T> getButtonRenderer (Class<T> type, int level, boolean container) {
		if (type==Void.class) return new IButtonRenderer<T>() {
			@Override
			public void renderButton(Context context, String title, boolean focus, T state) {
				Color color=getMainColor(focus,true);
				context.getInterface().drawRect(context.getRect(),color,color,color,color);
				context.getInterface().drawString(context.getRect().getLocation(),title,getFontColor(focus));
			}

			@Override
			public int getDefaultHeight() {
				return getBaseHeight();
			}
		};
		return super.getButtonRenderer(type,level,container);
	}

	@Override
	public IButtonRenderer<IBoolean> getCheckMarkRenderer(int level, boolean container) {
		return new IButtonRenderer<IBoolean>() {
			@Override
			public void renderButton(Context context, String title, boolean focus, IBoolean state) {
				Color color=getMainColor(focus,true);
				context.getInterface().drawRect(context.getRect(),color,color,color,color);
				context.getInterface().drawString(context.getRect().getLocation(),title,getFontColor(focus));
			}

			@Override
			public int getDefaultHeight() {
				return getBaseHeight();
			}
		};
	}

	@Override
	public IButtonRenderer<String> getKeybindRenderer(int level, boolean container) {
		return new IButtonRenderer<String>() {
			@Override
			public void renderButton(Context context, String title, boolean focus, String state) {
				Color color=getMainColor(focus,true);
				context.getInterface().drawRect(context.getRect(),color,color,color,color);
				context.getInterface().drawString(context.getRect().getLocation(),title,getFontColor(focus));
			}

			@Override
			public int getDefaultHeight() {
				return getBaseHeight();
			}
		};
	}

	@Override
	public ISliderRenderer getSliderRenderer(int level, boolean container) {
		return new ISliderRenderer() {
			@Override
			public void renderSlider(Context context, String title, String state, boolean focus, double value) {
				Color color=getMainColor(focus,true);
				context.getInterface().drawRect(context.getRect(),color,color,color,color);
				context.getInterface().drawString(context.getRect().getLocation(),title,getFontColor(focus));
			}

			@Override
			public int getDefaultHeight() {
				return getBaseHeight();
			}
		};
	}

	@Override
	public int getBaseHeight() {
		return height+2*border;
	}

	@Override
	public Color getMainColor(boolean focus, boolean active) {
		if (active) return getColor(scheme.getColor("Enabled Color"));
		else return ITheme.combineColors(scheme.getColor("Disabled Color"),getColor(scheme.getColor("Enabled Color")));
	}

	@Override
	public Color getBackgroundColor(boolean focus) {
		return ITheme.combineColors(scheme.getColor("Settings Color"),getColor(scheme.getColor("Enabled Color")));
	}

	@Override
	public Color getFontColor(boolean focus) {
		return scheme.getColor("Font Color");
	}
}
