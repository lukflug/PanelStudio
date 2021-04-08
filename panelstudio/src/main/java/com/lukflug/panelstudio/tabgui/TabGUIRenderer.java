package com.lukflug.panelstudio.tabgui;

import com.lukflug.panelstudio.base.Context;

public interface TabGUIRenderer<T> {
	public void renderTab (Context context, int amount, double tabState);
	
	public void renderItem (Context context, int amount, double tabState, int index, String title, T itemState);
	
	public int getTabHeight (int amount);
}
