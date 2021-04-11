package com.lukflug.panelstudio.tabgui;

import com.lukflug.panelstudio.popup.IPopupPositioner;

public interface ITabGUITheme {
	public int getTabWidth();
	
	public IPopupPositioner getPositioner();
	
	public ITabGUIRenderer<Void> getParentRenderer();
	
	public ITabGUIRenderer<Boolean> getChildRenderer();
}
