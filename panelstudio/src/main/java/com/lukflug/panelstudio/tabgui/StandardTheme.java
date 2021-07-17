package com.lukflug.panelstudio.tabgui;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.popup.IPopupPositioner;
import com.lukflug.panelstudio.popup.PanelPositioner;
import com.lukflug.panelstudio.theme.IColorScheme;

public class StandardTheme implements ITabGUITheme {
	protected final IColorScheme scheme;
	protected int width,height,padding;
	protected IPopupPositioner positioner;
	protected RendererBase<Void> parentRenderer;
	protected RendererBase<Boolean> childRenderer;
	
	public StandardTheme (IColorScheme scheme, int width, int height, int padding, int distance) {
		this.scheme=scheme;
		this.width=width;
		this.height=height;
		this.padding=padding;
		positioner=new PanelPositioner(new Point(distance,0));
		scheme.createSetting(null,"Selected Color","The color for the selected tab element.",false,true,new Color(0,0,255),false);
		scheme.createSetting(null,"Background Color","The color for the tab background.",true,true,new Color(32,32,32,128),false);
		scheme.createSetting(null,"Outline Color","The color for the tab outline.",false,true,new Color(0,0,0),false);
		scheme.createSetting(null,"Font Color","The main color for the text.",false,true,new Color(255,255,255),false);
		scheme.createSetting(null,"Enabled Color","The color for enabled text.",false,true,new Color(255,0,0),false);
		parentRenderer=new RendererBase<Void>() {
			@Override
			protected Color getFontColor (Void itemState) {
				return scheme.getColor("Font Color");
			}
		};
		childRenderer=new RendererBase<Boolean>() {
			@Override
			protected Color getFontColor (Boolean itemState) {
				if (itemState) return scheme.getColor("Enabled Color");
				else return scheme.getColor("Font Color");
			}
		};
	}

	@Override
	public int getTabWidth() {
		return width;
	}

	@Override
	public IPopupPositioner getPositioner() {
		return positioner;
	}
	
	@Override
	public ITabGUIRenderer<Void> getParentRenderer() {
		return parentRenderer;
	}

	@Override
	public ITabGUIRenderer<Boolean> getChildRenderer() {
		return childRenderer;
	}
	
	
	protected abstract class RendererBase<T> implements ITabGUIRenderer<T> {
		@Override
		public void renderTab(Context context, int amount, double tabState) {
			Color color=scheme.getColor("Selected Color");
			Color fill=scheme.getColor("Background Color");
			Color border=scheme.getColor("Outline Color");
			context.getInterface().fillRect(context.getRect(),fill,fill,fill,fill);
			context.getInterface().fillRect(getItemRect(context.getInterface(),context.getRect(),amount,tabState),color,color,color,color);
			context.getInterface().drawRect(getItemRect(context.getInterface(),context.getRect(),amount,tabState),border,border,border,border);
			context.getInterface().drawRect(context.getRect(),border,border,border,border);
		}

		@Override
		public void renderItem (Context context, int amount, double tabState, int index, String title, T itemState) {
			context.getInterface().drawString(new Point(context.getPos().x+padding,context.getPos().y+context.getSize().height*index/amount+padding),height,title,getFontColor(itemState));
		}

		@Override
		public int getTabHeight (int amount) {
			return (height+2*padding)*amount;
		}

		@Override
		public Rectangle getItemRect (IInterface inter, Rectangle rect, int amount, double tabState) {
			return new Rectangle(rect.x,rect.y+(int)Math.round(rect.height*tabState/amount),rect.width,height+2*padding);
		}
		
		protected abstract Color getFontColor (T itemState);
	}
}
