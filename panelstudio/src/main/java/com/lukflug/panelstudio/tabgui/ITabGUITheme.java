package com.lukflug.panelstudio.tabgui;

public interface ITabGUITheme {
	public ITabGUIRenderer<Void> getParentRenderer();
	
	public ITabGUIRenderer<Boolean> getChildRenderer();
}
