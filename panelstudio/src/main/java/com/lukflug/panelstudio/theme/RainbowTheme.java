package com.lukflug.panelstudio.theme;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.function.IntSupplier;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.setting.ILabeled;

public class RainbowTheme extends ThemeBase {
	protected IBoolean ignoreDisabled,buttonRainbow;
	protected IntSupplier rainbowGradient;
	protected int height,padding;
	protected String separator;
	
	public RainbowTheme (IColorScheme scheme, IBoolean ignoreDisabled, IBoolean buttonRainbow, IntSupplier rainbowGradient, int height, int padding, String separator) {
		super(scheme);
		this.ignoreDisabled=ignoreDisabled;
		this.buttonRainbow=buttonRainbow;
		this.rainbowGradient=rainbowGradient;
		this.height=height;
		this.padding=padding;
		this.separator=separator;
		scheme.createSetting(this,"Title Color","The color for panel titles.",false,true,new Color(64,64,64),false);
		scheme.createSetting(this,"Rainbow Color","The rainbow base color.",false,true,new Color(255,0,0),false);
		scheme.createSetting(this,"Background Color","The main color for disabled components.",false,true,new Color(64,64,64),false);
		scheme.createSetting(this,"Font Color","The main color for text.",false,true,new Color(255,255,255),false);
	}
	
	protected void renderRainbowRect (Rectangle rect, Context context, boolean focus) {
		Color source=getMainColor(focus,true);
		float[] hsb=Color.RGBtoHSB(source.getRed(),source.getGreen(),source.getBlue(),null);
		float currentHue=hsb[0];
		float targetHue=hsb[0];
		if (rainbowGradient.getAsInt()!=0) targetHue+=rect.height/(float)rainbowGradient.getAsInt();
		else context.getInterface().fillRect(rect,source,source,source,source);
		while (currentHue<targetHue) {
			float nextHue=(float)(Math.floor(currentHue*6)+1)/6;
			if (nextHue>targetHue) nextHue=targetHue;
			Color colorA=Color.getHSBColor(currentHue,hsb[1],hsb[2]);
			Color colorB=Color.getHSBColor(nextHue,hsb[1],hsb[2]);
			int top=(int)Math.round((currentHue-hsb[0])*rainbowGradient.getAsInt());
			int bottom=(int)Math.round((nextHue-hsb[0])*rainbowGradient.getAsInt());
			context.getInterface().fillRect(new Rectangle(rect.x,rect.y+top,rect.width,bottom-top),colorA,colorA,colorB,colorB);
			currentHue=nextHue;
		}
	}

	@Override
	public IDescriptionRenderer getDescriptionRenderer() {
		return new IDescriptionRenderer() {
			@Override
			public void renderDescription(IInterface inter, Point pos, String text) {
				// TODO Auto-generated method stub
				
			}
		};
	}

	@Override
	public IContainerRenderer getContainerRenderer(int logicalLevel, int graphicalLevel, boolean horizontal) {
		return new IContainerRenderer() {
			@Override
			public void renderBackground (Context context, boolean focus) {
				if (graphicalLevel==0) renderRainbowRect(context.getRect(),context,focus);
			}
		};
	}

	@Override
	public <T> IPanelRenderer<T> getPanelRenderer(Class<T> type, int logicalLevel, int graphicalLevel) {
		return new IPanelRenderer<T>() {
			@Override
			public void renderPanelOverlay(Context context, boolean focus, T state, boolean open) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void renderTitleOverlay(Context context, boolean focus, T state, boolean open) {
				// TODO Auto-generated method stub
				
			}
		};
	}

	@Override
	public <T> IScrollBarRenderer<T> getScrollBarRenderer(Class<T> type, int logicalLevel, int graphicalLevel) {
		return new IScrollBarRenderer<T>() {
			@Override
			public int renderScrollBar(Context context, boolean focus, T state, boolean horizontal, int height, int position) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getThickness() {
				// TODO Auto-generated method stub
				return 0;
			}
		};
	}

	@Override
	public <T> IEmptySpaceRenderer<T> getEmptySpaceRenderer(Class<T> type, int logicalLevel, int graphicalLevel, boolean container) {
		return new IEmptySpaceRenderer<T>() {
			@Override
			public void renderSpace(Context context, boolean focus, T state) {
				// TODO Auto-generated method stub
				
			}
		};
	}

	@Override
	public <T> IButtonRenderer<T> getButtonRenderer(Class<T> type, int logicalLevel, int graphicalLevel, boolean container) {
		return new IButtonRenderer<T>() {
			@Override
			public void renderButton(Context context, String title, boolean focus, Object state) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public int getDefaultHeight() {
				return getBaseHeight();
			}
		};
	}

	@Override
	public IButtonRenderer<Void> getSmallButtonRenderer(int symbol, int logicalLevel, int graphicalLevel, boolean container) {
		return new IButtonRenderer<Void>() {
			@Override
			public void renderButton(Context context, String title, boolean focus, Void state) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public int getDefaultHeight() {
				return getBaseHeight();
			}
		};
	}

	@Override
	public IButtonRenderer<String> getKeybindRenderer(int logicalLevel, int graphicalLevel, boolean container) {
		return new IButtonRenderer<String>() {
			@Override
			public void renderButton(Context context, String title, boolean focus, String state) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public int getDefaultHeight() {
				return getBaseHeight();
			}
		};
	}

	@Override
	public ISliderRenderer getSliderRenderer(int logicalLevel, int graphicalLevel, boolean container) {
		return new ISliderRenderer() {
			@Override
			public void renderSlider(Context context, String title, String state, boolean focus, double value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public int getDefaultHeight() {
				// TODO Auto-generated method stub
				return 0;
			}
		};
	}

	@Override
	public IRadioRenderer getRadioRenderer(int logicalLevel, int graphicalLevel, boolean container) {
		return new IRadioRenderer() {
			@Override
			public void renderItem(Context context, ILabeled[] items, boolean focus, int target, double state, boolean horizontal) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public int getDefaultHeight(ILabeled[] items, boolean horizontal) {
				// TODO Auto-generated method stub
				return 0;
			}
		};
	}

	@Override
	public IResizeBorderRenderer getResizeRenderer() {
		return new IResizeBorderRenderer() {
			@Override
			public void drawBorder(Context context, boolean focus) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public int getBorder() {
				// TODO Auto-generated method stub
				return 0;
			}
		};
	}

	@Override
	public ITextFieldRenderer getTextRenderer(boolean embed, int logicalLevel, int graphicalLevel, boolean container) {
		return new ITextFieldRenderer() {
			@Override
			public int renderTextField(Context context, String title, boolean focus, String content, int position, int select, int boxPosition, boolean insertMode) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getDefaultHeight() {
				return getBaseHeight();
			}

			@Override
			public Rectangle getTextArea(Context context, String title) {
				// TODO Auto-generated method stub
				return context.getRect();
			}

			@Override
			public int transformToCharPos(Context context, String title, String content, int boxPosition) {
				// TODO Auto-generated method stub
				return 0;
			}
		};
	}

	@Override
	public ISwitchRenderer<Boolean> getToggleSwitchRenderer(int logicalLevel, int graphicalLevel, boolean container) {
		return new ISwitchRenderer<Boolean>() {
			@Override
			public void renderButton(Context context, String title, boolean focus, Boolean state) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public int getDefaultHeight() {
				return getBaseHeight();
			}

			@Override
			public Rectangle getOnField(Context context) {
				// TODO Auto-generated method stub
				Rectangle rect=context.getRect();
				return new Rectangle(rect.x+rect.width-rect.height,rect.y,rect.height,rect.height);
			}

			@Override
			public Rectangle getOffField(Context context) {
				// TODO Auto-generated method stub
				Rectangle rect=context.getRect();
				return new Rectangle(rect.x+rect.width-2*rect.height,rect.y,rect.height,rect.height);
			}
		};
	}

	@Override
	public ISwitchRenderer<String> getCycleSwitchRenderer(int logicalLevel, int graphicalLevel, boolean container) {
		return new ISwitchRenderer<String>() {
			@Override
			public void renderButton(Context context, String title, boolean focus, String state) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public int getDefaultHeight() {
				return getBaseHeight();
			}

			@Override
			public Rectangle getOnField(Context context) {
				// TODO Auto-generated method stub
				Rectangle rect=context.getRect();
				return new Rectangle(rect.x+rect.width-rect.height,rect.y,rect.height,rect.height);
			}

			@Override
			public Rectangle getOffField(Context context) {
				// TODO Auto-generated method stub
				Rectangle rect=context.getRect();
				return new Rectangle(rect.x+rect.width-2*rect.height,rect.y,rect.height,rect.height);
			}
		};
	}

	@Override
	public IColorPickerRenderer getColorPickerRenderer() {
		return new StandardColorPicker() {
			@Override
			public int getPadding() {
				return padding;
			}

			@Override
			public int getBaseHeight() {
				return RainbowTheme.this.getBaseHeight();
			}
		};
	}

	@Override
	public int getBaseHeight() {
		return height+2*padding;
	}

	@Override
	public Color getMainColor(boolean focus, boolean active) {
		if (active) return scheme.getColor("Rainbow Color");
		else return scheme.getColor("Background Color");
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
