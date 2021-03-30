package com.lukflug.panelstudio.mc12;

import org.lwjgl.input.Keyboard;

import com.lukflug.panelstudio.container.GUI;
import com.lukflug.panelstudio.hud.HUDGUI;

public abstract class MinecraftHUDGUI extends MinecraftGUI {
	private boolean guiOpened=false;
	
	@Override
	public void enterGUI() {
		if (!getHUDGUI().getGUIVisibility().isOn()) getHUDGUI().getGUIVisibility().toggle();
		if (!getHUDGUI().getHUDVisibility().isOn()) getHUDGUI().getHUDVisibility().toggle();
		super.enterGUI();
	}
	
	@Override
	public void exitGUI() {
		if (getHUDGUI().getGUIVisibility().isOn()) getHUDGUI().getGUIVisibility().toggle();
		if (getHUDGUI().getHUDVisibility().isOn()) getHUDGUI().getHUDVisibility().toggle();
		super.exitGUI();
	}

	/**
	 * Open the HUD editor.
	 */
	public void enterHUDEditor() {
		if (getHUDGUI().getGUIVisibility().isOn()) getHUDGUI().getGUIVisibility().toggle();
		if (!getHUDGUI().getHUDVisibility().isOn()) getHUDGUI().getHUDVisibility().toggle();
		super.enterGUI();
	}
	
	@Override
	public void initGui() {
	}
	
	@Override
	public void onGuiClosed() {
	}
	
	@Override
	protected void renderGUI() {
		if (!guiOpened) getGUI().enter();
		guiOpened=true;
		super.renderGUI();
	}
	
	/**
	 * Render function to be called when the GUI is closed to render the HUD.
	 */
	public void render() {
		if (!getHUDGUI().getGUIVisibility().isOn() && !getHUDGUI().getHUDVisibility().isOn()) renderGUI();
	}
	
	/**
	 * Key event function to be called when the GUI is closed.
	 * @param scancode the key scancode
	 */
	public void handleKeyEvent (int scancode) {
		if (scancode!=Keyboard.KEY_ESCAPE && !getHUDGUI().getGUIVisibility().isOn() && !getHUDGUI().getGUIVisibility().isOn()) getHUDGUI().handleKey(scancode);
	}
	
	/**
	 * Get the {@link HUDClickGUI} to be rendered.
	 * @return current ClickGUI
	 */
	protected abstract HUDGUI getHUDGUI();

	@Override
	protected final GUI getGUI() {
		return getHUDGUI();
	}
}
