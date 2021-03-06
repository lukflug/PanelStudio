package com.lukflug.panelstudio.theme;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.base.IInterface;

/**
 * Theme corresponding to the appearance GameSense 2.0 and 2.1 had.
 * The parameter gradient in the constructor determines, if it appears like GameSense 2.0-2.1.1 (false) or like GameSense 2.1.2-2.1.5 (true).
 * @author lukflug
 */
public class ClearTheme extends ThemeBase {
	protected final boolean gradient;
	protected final int height,border,space;
	protected final String separator;
	
	public ClearTheme (IColorScheme scheme, boolean gradient, int height, int border, int space, String separator) {
		super(scheme);
		this.gradient=gradient;
		this.height=height;
		this.border=border;
		this.space=space;
		this.separator=separator;
		scheme.createSetting(this,"Title Color","The color for panel titles.",false,true,new Color(90,145,240),false);
		scheme.createSetting(this,"Enabled Color","The main color for enabled components.",false,true,new Color(90,145,240),false);
		scheme.createSetting(this,"Background Color","The background color.",true,true,new Color(195,195,195,200),false);
		scheme.createSetting(this,"Font Color","The main color for text.",false,true,new Color(255,255,255),false);
	}
	
	protected void renderOverlay (Context context) {
		Color color=context.isHovered()?new Color(0,0,0,64):new Color(0,0,0,0);
		context.getInterface().fillRect(context.getRect(),color,color,color,color);
	}

	@Override
	public IDescriptionRenderer getDescriptionRenderer() {
		return new IDescriptionRenderer() {
			@Override
			public void renderDescription(IInterface inter, Point pos, String text) {
				Rectangle rect=new Rectangle(pos,new Dimension(inter.getFontWidth(height,text)+2,height+2));
				Color color=getBackgroundColor(true);
				inter.fillRect(rect,color,color,color,color);
				inter.drawString(new Point(pos.x+1,pos.y+1),height,text,getFontColor(true));
			}
		};
	}

	@Override
	public IContainerRenderer getContainerRenderer(int level) {
		return new IContainerRenderer() {
			@Override
			public void renderBackground (Context context, boolean focus) {
				if (level==0) {
					Color color=getBackgroundColor(focus);
					context.getInterface().fillRect(context.getRect(),color,color,color,color);
				}
			}
			
			@Override
			public int getBorder() {
				return space;
			}
			
			@Override
			public int getTop() {
				return space;
			}
		};
	}

	@Override
	public <T> IPanelRenderer<T> getPanelRenderer(Class<T> type, int level) {
		return new IPanelRenderer<T>() {
			@Override
			public void renderPanelOverlay(Context context, boolean focus, T state) {
			}
		};
	}

	@Override
	public <T> IScrollBarRenderer<T> getScrollBarRenderer(Class<T> type, int level) {
		return new IScrollBarRenderer<T>() {
			@Override
			public int renderScrollBar (Context context, boolean focus, T state, boolean horizontal, int height, int position) {
				return position;
			}

			@Override
			public int getThickness() {
				return 0;
			}
		};
	}

	@Override
	public <T> IEmptySpaceRenderer<T> getEmptySpaceRenderer(Class<T> type, int level) {
		return new IEmptySpaceRenderer<T>() {
			@Override
			public void renderSpace(Context context, boolean focus, T state) {
			}
		};
	}

	@Override
	public <T> IButtonRenderer<T> getButtonRenderer(Class<T> type, int level, boolean container) {
		return new IButtonRenderer<T>() {
			@Override
			public void renderButton(Context context, String title, boolean focus, T state) {
				Color color=getFontColor(focus);
				Color colorA=getMainColor(focus,true),colorB=gradient?getBackgroundColor(focus):colorA;
				if (container && level==0) context.getInterface().fillRect(context.getRect(),colorA,colorA,colorB,colorB);
				if (type==IBoolean.class && ((IBoolean)state).isOn()==true) color=getMainColor(focus,true);
				if (level!=0) renderOverlay(context);
				if (type==String.class) context.getInterface().drawString(new Point(context.getRect().x+border,context.getRect().y+border),height,title+separator+state,color);
				else context.getInterface().drawString(new Point(context.getRect().x+border,context.getRect().y+border),height,title,color);
			}

			@Override
			public int getDefaultHeight() {
				return getBaseHeight();
			}
		};
	}

	@Override
	public IButtonRenderer<IBoolean> getCheckMarkRenderer(int level, boolean container) {
		return getButtonRenderer(IBoolean.class,level,container);
	}

	@Override
	public IButtonRenderer<String> getKeybindRenderer(int level, boolean container) {
		return new IButtonRenderer<String>() {
			@Override
			public void renderButton(Context context, String title, boolean focus, String state) {
				Color color=getFontColor(focus);
				if (focus) color=getMainColor(focus,true);
				if (level!=0) renderOverlay(context);
				context.getInterface().drawString(new Point(context.getRect().x+border,context.getRect().y+border),height,title+separator+state,color);
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
				Color color=getFontColor(focus);
				Color colorA=getMainColor(focus,true);
				Rectangle rect=getSlideArea(context);
				int divider=(int)(rect.width*value);
				context.getInterface().fillRect(new Rectangle(rect.x,rect.y,divider,rect.height),colorA,colorA,colorA,colorA);
				if (level!=0) renderOverlay(context);
				context.getInterface().drawString(new Point(context.getRect().x+border,context.getRect().y+border),height,title+separator+state,color);
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
