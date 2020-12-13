package com.lukflug.panelstudio.mc;

import com.lukflug.panelstudio.ClickGUI;
import com.lukflug.panelstudio.hud.HUDClickGUI;

public abstract class MinecraftHUDGUI extends MinecraftGUI {
	public void render() {
		if (!getHUDGUI().isOn()) renderGUI();
	}
	
	public void handleKeyEvent (int scancode) {
    	if (scancode!=1 && !getHUDGUI().isOn()) getHUDGUI().handleKey(scancode);
    }
	
	@Override
	protected void keyTyped(final char typedChar, final int keyCode) {
		if (keyCode==1 && getHUDGUI().isOn()) getHUDGUI().toggle();
	}
	
	protected abstract HUDClickGUI getHUDGUI();

	@Override
	protected ClickGUI getGUI() {
		return getHUDGUI();
	}
}
