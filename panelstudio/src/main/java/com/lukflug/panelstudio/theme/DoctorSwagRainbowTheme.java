package com.lukflug.panelstudio.theme;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.function.Supplier;

import com.lukflug.panelstudio.Context;

/**
 * Theme made on commission for doctor swag#2624.
 * @author lukflug
 */
public class DoctorSwagRainbowTheme implements Theme {
	protected ColorScheme scheme;
	protected Renderer componentRenderer,containerRenderer,panelRenderer;
	protected Supplier<Boolean> ignoreDisabled,buttonRainbow;
	
	public DoctorSwagRainbowTheme (ColorScheme scheme, int height, Supplier<Boolean> ignoreDisabled, Supplier<Boolean> buttonRainbow) {
		this.scheme=scheme;
		panelRenderer=new ComponentRenderer(0,height,0);
		containerRenderer=new ComponentRenderer(1,height,0);
		componentRenderer=new ComponentRenderer(2,height,0);
		this.ignoreDisabled=ignoreDisabled;
		this.buttonRainbow=buttonRainbow;
	}
	
	@Override
	public Renderer getPanelRenderer() {
		return panelRenderer;
	}

	@Override
	public Renderer getContainerRenderer() {
		return containerRenderer;
	}

	@Override
	public Renderer getComponentRenderer() {
		return componentRenderer;
	}

	
	protected class ComponentRenderer extends RendererBase {
		protected final int level;
		
		public ComponentRenderer (int level, int height, int border) {
			super(height+2*border,border,0,0,0);
			this.level=level;
		}
		
		@Override
		public void renderTitle(Context context, String text, boolean focus, boolean active, boolean open) {
			super.renderTitle(context,text,focus,active,open);
			if (level!=0) {
				Color color=getFontColor(active);
				Point p1,p2,p3;
				if (open) {
					p3=new Point(context.getPos().x+context.getSize().width-3,							context.getPos().y+context.getSize().height/4);
					p2=new Point(context.getPos().x+context.getSize().width-context.getSize().height/2,	context.getPos().y+context.getSize().height*3/4);
					p1=new Point(context.getPos().x+context.getSize().width-context.getSize().height+3,	context.getPos().y+context.getSize().height/4);
				} else  {
					p3=new Point(context.getPos().x+context.getSize().width-context.getSize().height*3/4,context.getPos().y+3);
					p2=new Point(context.getPos().x+context.getSize().width-context.getSize().height/4,  context.getPos().y+context.getSize().height/2);
					p1=new Point(context.getPos().x+context.getSize().width-context.getSize().height*3/4,context.getPos().y+context.getSize().height-3);
				}
				context.getInterface().drawLine(p1,p2,color,color);
				context.getInterface().drawLine(p2,p3,color,color);
			}
			if (level==0 && open) {
				Color color=getFontColor(focus);
				context.getInterface().fillRect(new Rectangle(context.getRect().x,context.getRect().y+context.getRect().height-1,context.getRect().width,1),color,color,color,color);
			}
		}

		@Override
		public void renderRect (Context context, String text, boolean focus, boolean active, Rectangle rectangle, boolean overlay) {
			if ((level==0 || !active) && (level!=1 || !ignoreDisabled.get())) {
				Color color=getBackgroundColor(focus);
				context.getInterface().fillRect(new Rectangle(rectangle.x,rectangle.y,context.getRect().x+context.getRect().width-rectangle.x,rectangle.height),color,color,color,color);
			}
			if (level!=0 && overlay) {
				Color overlayColor;
				if (context.isHovered()) {
					overlayColor=new Color(0,0,0,64);
				} else {
					overlayColor=new Color(0,0,0,0);
				}
				context.getInterface().fillRect(context.getRect(),overlayColor,overlayColor,overlayColor,overlayColor);
			}
			Point stringPos=new Point(rectangle.getLocation());
			stringPos.translate(0,getOffset());
			if (level==0) stringPos=new Point(rectangle.x+rectangle.width/2-context.getInterface().getFontWidth(text)/2,rectangle.y+getOffset());
			if (level==2 && overlay) context.getInterface().drawString(stringPos,"> "+text,getFontColor(focus));
			else context.getInterface().drawString(stringPos,text,getFontColor(focus));
		}

		@Override
		public void renderBackground (Context context, boolean focus) {
			if (level==0) {
				if (buttonRainbow.get()) {
					Rectangle rect=context.getRect();
					for (int current=rect.y;current<rect.y+rect.height;current+=height) {
						int height=Math.min(this.height,rect.height+rect.y-current);
						renderRainbowRect(new Rectangle(rect.x,current,rect.width,height),context,focus);
					}
				} else renderRainbowRect(context.getRect(),context,focus);
			}
		}

		@Override
		public void renderBorder (Context context, boolean focus, boolean active, boolean open) {
		}

		@Override
		public Color getMainColor (boolean focus, boolean active) {
			if (active) return getColorScheme().getActiveColor();
			return new Color(0,0,0,0);
		}

		@Override
		public Color getBackgroundColor (boolean focus) {
			Color color=getColorScheme().getBackgroundColor();
			return new Color(color.getRed(),color.getGreen(),color.getBlue());
		}

		@Override
		public ColorScheme getDefaultColorScheme() {
			return DoctorSwagRainbowTheme.this.scheme;
		}
		
		protected void renderRainbowRect (Rectangle rect, Context context, boolean focus) {
			Color source=getMainColor(focus,true);
			float[] hsb=Color.RGBtoHSB(source.getRed(),source.getGreen(),source.getBlue(),null);
			float currentHue=hsb[0];
			float targetHue=hsb[0];
			if (getColorScheme().getOpacity()!=0) targetHue+=rect.height/(float)getColorScheme().getOpacity();
			else context.getInterface().fillRect(rect,source,source,source,source);
			while (currentHue<targetHue) {
				float nextHue=(float)(Math.floor(currentHue*6)+1)/6;
				if (nextHue>targetHue) nextHue=targetHue;
				Color colorA=Color.getHSBColor(currentHue,hsb[1],hsb[2]);
				Color colorB=Color.getHSBColor(nextHue,hsb[1],hsb[2]);
				int top=(int)Math.round((currentHue-hsb[0])*getColorScheme().getOpacity());
				int bottom=(int)Math.round((nextHue-hsb[0])*getColorScheme().getOpacity());
				context.getInterface().fillRect(new Rectangle(rect.x,rect.y+top,rect.width,bottom-top),colorA,colorA,colorB,colorB);
				currentHue=nextHue;
			}
		}
	}
}
