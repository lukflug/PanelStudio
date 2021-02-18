package com.lukflug.panelstudio.theme;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.base.IInterface;

/**
 * Recreates the appearance of GameSense 2.2.0.
 * @author lukflug
 */
public class GameSenseTheme extends ThemeBase {
	public GameSenseTheme (IColorScheme scheme, int height, int border, int scroll) {
		super(scheme);
		scheme.createSetting(this,"Title Color","The color for panel titles.",false,true,new Color(255,0,0),false);
		scheme.createSetting(this,"Outline Color","The color for panel outlines.",false,true,new Color(255,0,0),false);
		scheme.createSetting(this,"Enabled Color","The main color for enabled components.",true,true,new Color(255,0,0,150),false);
		scheme.createSetting(this,"Disabled Color","The main color for disabled modules.",false,true,new Color(0,0,0),false);
		scheme.createSetting(this,"Settings Color","The background color for settings.",false,true,new Color(32,32,32),false);
		scheme.createSetting(this,"Font Color","The main color for text.",false,true,new Color(255,255,255),false);
		descriptionRenderer=new IDescriptionRenderer() {
			@Override
			public void renderDescription(IInterface inter, Point pos, String text) {
			}
		};
		titleRenderer=new IButtonRenderer<Void>() {
			@Override
			public void renderButton(Context context, String title, boolean hasFocus, Void state) {
				context.getInterface().drawString(context.getRect().getLocation(),title,getFontColor(hasFocus));
			}

			@Override
			public int getDefaultHeight() {
				return height+2*border;
			}
		};
		panelTitleRenderer=new IButtonRenderer<Void>() {
			@Override
			public void renderButton(Context context, String title, boolean hasFocus, Void state) {
				context.getInterface().drawString(context.getRect().getLocation(),title,getFontColor(hasFocus));
			}

			@Override
			public int getDefaultHeight() {
				return height+2*border;
			}
		};
		panelRenderer=new IPanelRenderer() {
			@Override
			public void renderPanelOverlay(Context context, boolean focus, boolean active) {
			}
		};
		panelPanelRenderer=new IPanelRenderer() {
			@Override
			public void renderPanelOverlay(Context context, boolean focus, boolean active) {
			}
		};
		containerRenderer=new IContainerRenderer() {
			@Override
			public void renderBackground (Context context, boolean focus) {
			}
		};
		panelContainerRenderer=new IContainerRenderer() {
			@Override
			public void renderBackground (Context context, boolean focus) {
			}
		};
		scrollRenderer=new IScrollBarRenderer() {
			@Override
			public int renderScrollBar(Context context, boolean focus, boolean active, boolean horizontal, int height, int position) {
				return position;
			}

			@Override
			public int getThickness() {
				return scroll;
			}
		};
		cornerRenderer=new IEmptySpaceRenderer() {
			@Override
			public void renderSpace(Context context, boolean focus) {
				
			}
		};
		toggleButtonRenderer=new IButtonRenderer<IBoolean>() {
			@Override
			public void renderButton(Context context, String title, boolean hasFocus, IBoolean state) {
				context.getInterface().drawString(context.getRect().getLocation(),title,getFontColor(hasFocus));
			}

			@Override
			public int getDefaultHeight() {
				return height+2*border;
			}
		};
		checkMarkRenderer=new IButtonRenderer<IBoolean>() {
			@Override
			public void renderButton(Context context, String title, boolean hasFocus, IBoolean state) {
				context.getInterface().drawString(context.getRect().getLocation(),title,getFontColor(hasFocus));
			}

			@Override
			public int getDefaultHeight() {
				return height+2*border;
			}
		};
		cycleButtonRenderer=new IButtonRenderer<String>() {
			@Override
			public void renderButton(Context context, String title, boolean hasFocus, String state) {
				context.getInterface().drawString(context.getRect().getLocation(),title,getFontColor(hasFocus));
			}

			@Override
			public int getDefaultHeight() {
				return height+2*border;
			}
		};
		keybindRenderer=new IButtonRenderer<String>() {
			@Override
			public void renderButton(Context context, String title, boolean hasFocus, String state) {
				context.getInterface().drawString(context.getRect().getLocation(),title,getFontColor(hasFocus));
			}

			@Override
			public int getDefaultHeight() {
				return height+2*border;
			}
		};
		sliderRenderer=new ISliderRenderer() {
			@Override
			public void renderSlider(Context context, String title, String state, boolean hasFocus, double value) {
				context.getInterface().drawString(context.getRect().getLocation(),title,getFontColor(hasFocus));
			}

			@Override
			public int getDefaultHeight() {
				return height+2*border;
			}

			@Override
			public Rectangle getSlideArea(Context context) {
				return context.getRect();
			}
		};
	}

	@Override
	public Color getMainColor(boolean focus, boolean active) {
		if (active) return scheme.getColor("Enabled Color");
		else return ITheme.combineColors(scheme.getColor("Disabled Color"),scheme.getColor("Enabled Color"));
	}

	@Override
	public Color getBackgroundColor(boolean focus) {
		return ITheme.combineColors(scheme.getColor("Settings Color"),scheme.getColor("Enabled Color"));
	}

	@Override
	public Color getFontColor(boolean focus) {
		return scheme.getColor("Font Color");
	}
}
