package com.lukflug.panelstudio.tabgui;

import java.awt.Color;

import com.lukflug.panelstudio.theme.IColorScheme;

public class StandardTheme implements ITabGUITheme {
	protected final IColorScheme scheme;
	protected ITabGUIRenderer<Void> parentRenderer;
	protected ITabGUIRenderer<Boolean> childRenderer;
	
	public StandardTheme (IColorScheme scheme) {
		this.scheme=scheme;
		scheme.createSetting(null,"Selected Color","The color for the selected tab element.",false,true,new Color(0,0,255),false);
		scheme.createSetting(null,"Background Color","The color for the tab background.",true,true,new Color(32,32,32,128),false);
		scheme.createSetting(null,"Outline Color","The color for the tab outline.",false,true,new Color(0,0,0),false);
		scheme.createSetting(null,"Font Color","The color for the text.",false,true,new Color(255,255,255),false);
	}
	
	@Override
	public ITabGUIRenderer<Void> getParentRenderer() {
		return parentRenderer;
	}

	@Override
	public ITabGUIRenderer<Boolean> getChildRenderer() {
		return childRenderer;
	}

}
