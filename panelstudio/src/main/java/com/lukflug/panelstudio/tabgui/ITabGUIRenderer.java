package com.lukflug.panelstudio.tabgui;

import java.awt.Rectangle;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;

public interface ITabGUIRenderer<T> {
	public void renderTab (Context context, int amount, double tabState);
	
	public void renderItem (Context context, int amount, double tabState, int index, String title, T itemState);
	
	public int getTabHeight (int amount);
	
	public Rectangle getItemRect (IInterface inter, Rectangle rect, int amount, double tabState);
}
