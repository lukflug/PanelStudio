package com.lukflug.panelstudio.mc;

import com.lukflug.panelstudio.ClickGUI;
import com.lukflug.panelstudio.hud.HUDClickGUI;

/**
 * Class designed for GUIs with HUDs.
 * @author lukflug
 */
public abstract class MinecraftHUDGUI extends MinecraftGUI {
	@Override
	public void enterGUI() {
		super.enterGUI();
		if (!getHUDGUI().isOn()) getHUDGUI().toggle();
	}
	
	/**
	 * Render function to be called when the GUI is closed to render the HUD.
	 */
	public void render() {
		if (!getHUDGUI().isOn()) renderGUI();
	}
	
	/**
	 * Key event function to be called when the GUI is closed.
	 * @param scancode the key scancode
	 */
	public void handleKeyEvent (int scancode) {
		if (scancode!=1 && !getHUDGUI().isOn()) getHUDGUI().handleKey(scancode);
	}
	
	@Override
	protected void keyTyped(final char typedChar, final int keyCode) {
		super.keyTyped(typedChar,keyCode);
		if (keyCode==1 && getHUDGUI().isOn()) getHUDGUI().toggle();
	}
	
	/**
	 * Get the {@link HUDClickGUI} to be rendered.
	 * @return current ClickGUI
	 */
	protected abstract HUDClickGUI getHUDGUI();

	@Override
	protected ClickGUI getGUI() {
		return getHUDGUI();
	}
}
