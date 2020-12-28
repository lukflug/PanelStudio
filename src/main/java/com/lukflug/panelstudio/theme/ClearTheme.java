package com.lukflug.panelstudio.theme;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import com.lukflug.panelstudio.Context;

/**
 * Theme corresponding to the appearance GameSense 2.0 and 2.1 had.
 * The parameter gradient in the constructor determines, if it appears like GameSense 2.0-2.1.1 (false) or like GameSense 2.1.2-2.1.5 (true).
 * @author lukflug
 */
public class ClearTheme implements Theme {
	protected ColorScheme scheme;
	protected Renderer componentRenderer,panelRenderer;
	protected final boolean gradient;
	
	public ClearTheme (ColorScheme scheme, boolean gradient, int height, int border) {
		this.scheme=scheme;
		this.gradient=gradient;
		panelRenderer=new ComponentRenderer(true,height,border);
		componentRenderer=new ComponentRenderer(false,height,border);
	}
	
	@Override
	public Renderer getPanelRenderer() {
		return panelRenderer;
	}

	@Override
	public Renderer getContainerRenderer() {
		return componentRenderer;
	}

	@Override
	public Renderer getComponentRenderer() {
		return componentRenderer;
	}

	
	protected class ComponentRenderer extends RendererBase {
		protected final boolean panel;
		
		public ComponentRenderer (boolean panel, int height, int border) {
			super(height+2*border,border,0,0,0);
			this.panel=panel;
		}
		
		@Override
		public void renderTitle (Context context, String text, boolean focus, boolean active) {
			if (panel) super.renderTitle(context,text,focus,active);
			else {
				Color overlayColor;
				if (context.isHovered()) {
					overlayColor=new Color(0,0,0,64);
				} else {
					overlayColor=new Color(0,0,0,0);
				}
				context.getInterface().fillRect(context.getRect(),overlayColor,overlayColor,overlayColor,overlayColor);
				Color fontColor=getFontColor(focus);
				if (active) fontColor=getMainColor(focus,true);
				Point stringPos=new Point(context.getPos());
				stringPos.translate(0,getOffset());
				context.getInterface().drawString(stringPos,text,fontColor);
			}
		}
		
		@Override
		public void renderTitle(Context context, String text, boolean focus, boolean active, boolean open) {
			super.renderTitle(context,text,focus,active,open);
			if (!panel) {
				Color color=getFontColor(active);
				Point p1,p2,p3;
				if (open) {
					p3=new Point(context.getPos().x+context.getSize().width-2,							context.getPos().y+context.getSize().height/4);
					p2=new Point(context.getPos().x+context.getSize().width-context.getSize().height/2,	context.getPos().y+context.getSize().height*3/4);
					p1=new Point(context.getPos().x+context.getSize().width-context.getSize().height+2,	context.getPos().y+context.getSize().height/4);
				} else  {
					p3=new Point(context.getPos().x+context.getSize().width-context.getSize().height*3/4,context.getPos().y+2);
					p2=new Point(context.getPos().x+context.getSize().width-context.getSize().height/4,  context.getPos().y+context.getSize().height/2);
					p1=new Point(context.getPos().x+context.getSize().width-context.getSize().height*3/4,context.getPos().y+context.getSize().height-2);
				}
				context.getInterface().fillTriangle(p1,p2,p3,color,color,color);
			}
		}

		@Override
		public void renderRect (Context context, String text, boolean focus, boolean active, Rectangle rectangle, boolean overlay) {
			if (panel || active) {
				Color color=getMainColor(focus,true);
				Color color2=getBackgroundColor(focus);
				if (gradient && panel) context.getInterface().fillRect(rectangle,color,color,color2,color2);
				else context.getInterface().fillRect(rectangle,color,color,color,color);
			}
			if (!panel && overlay) {
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
			context.getInterface().drawString(stringPos,text,getFontColor(focus));
		}

		@Override
		public void renderBackground (Context context, boolean focus) {
			if (panel) {
				Color color=getBackgroundColor(focus);
				context.getInterface().fillRect(context.getRect(),color,color,color,color);
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
			return new Color(color.getRed(),color.getGreen(),color.getBlue(),getColorScheme().getOpacity());
		}

		@Override
		public ColorScheme getDefaultColorScheme() {
			return ClearTheme.this.scheme;
		}
	}
}
